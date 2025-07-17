/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projetoservidor;
import proj.comum.Utilizador;
import proj.rmi.IntServidor;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.Vector;
import proj.comum.Log;
import proj.rmi.IntCliente;
/**
 *
 * @author rafae
 */
public class ProjetoServidor extends UnicastRemoteObject implements IntServidor {
    

    private final Vector<Utilizador> utilizadoresOnline = new Vector<>();
    private final Vector<IntCliente> stubsClientes = new Vector<>();
    private final Vector<Log> logs = new Vector<>();

    public ProjetoServidor() throws RemoteException {
        super();
    }
    
    
    @Override
    public synchronized Utilizador login(Utilizador utilizador, IntCliente stubCliente) throws RemoteException {
        
        Iterator<Utilizador> i = utilizadoresOnline.iterator();
        while (i.hasNext()){
            Utilizador u = i.next();
            if(u.getNome().equalsIgnoreCase(utilizador.getNome())){
                System.out.println("Cliente " + utilizador.getNome() + " já está no servidor" );
                return null;
            }
        }
        
        //REF: https://www.javaguides.net/2020/03/convert-localdatetime-to-long-in-java.html
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        utilizador.setLoginTime(zdt.toInstant().toEpochMilli());
        
        utilizadoresOnline.add(utilizador); 
        stubsClientes.add(stubCliente);
        
        
        addLog(new Log(utilizador.getNome() + " entrou no servidor"));
        System.out.println("Cliente " + utilizador.getNome() + " entrou do servidor" );
        return utilizador;
    }

    @Override
    public synchronized void logout(Utilizador utilizador) throws RemoteException {
        
        Iterator<Utilizador> i = utilizadoresOnline.iterator();
        int j = 0;
        
        while (i.hasNext()){
            Utilizador u = i.next();
            if(u.getNome().equals(utilizador.getNome())){
                i.remove();
                stubsClientes.remove(j);
                addLog(new Log(utilizador.getNome() + " saiu no servidor"));
                System.out.println("Cliente " + utilizador.getNome() + " saiu do servidor" );
                break;
            }
            j++;
        }
          
    }

    @Override
    public synchronized Vector<Utilizador> getUtilizadoresOnline() throws RemoteException {
        System.out.println("Utilizadores online " + utilizadoresOnline);
        return new Vector<>(utilizadoresOnline);
    }

    @Override
    public synchronized IntCliente getCliente(String nome) throws RemoteException {
        
        Iterator<Utilizador> i = utilizadoresOnline.iterator();
        int j = 0;

        while (i.hasNext()){
            Utilizador u = i.next();
            if(u.getNome().equals(nome)){
                System.out.print("Cliente " + stubsClientes.get(j) + " devolvido com sucesso");
                return stubsClientes.get(j);
            }
            j++;
        }
        System.out.println("Cliente " + nome + " não encontrado");
        return null;
    }

    @Override
    public synchronized void addLog(Log log) throws RemoteException {
        logs.add(log);
        System.out.println("Log " + log + " adicionado com sucesso");
    }

    @Override
    public synchronized Vector<String> getLogs(long desde) throws RemoteException {
        Vector<String> logsFiltrados = new Vector<>();
        
        Iterator<Log> i = logs.iterator();

        while (i.hasNext()){
            Log l = i.next();
            if(l.getDataLog() >= desde){
                logsFiltrados.add(l.toString());
            }
        }
        System.out.println("Logs " + logsFiltrados);
        return logsFiltrados;
    }

    public static void main(String[] args) {
        try {
            ProjetoServidor servidor = new ProjetoServidor();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("ServidorDeFicheiros", servidor);
            System.out.println("Servidor RMI no porto 1099...");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
