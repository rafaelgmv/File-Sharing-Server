/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projetocliente;

import proj.comum.Utilizador;
import proj.rmi.IntServidor;
import proj.rmi.IntCliente;
import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Vector;
import proj.comum.Log;
import java.rmi.ConnectException;


/**
 *
 * @author rafae
 */
public class ProjetoCliente extends UnicastRemoteObject implements IntCliente {
    

    /**
     * @param args the command line arguments
     */
    
    private IntServidor servidor;
    private Utilizador utilizadorAtual;
    private String pastaPartilha;
    private Vector<Utilizador> utilizadoresOnline = new Vector<>();


    public ProjetoCliente() throws RemoteException {
        super();
    }
    
    public synchronized int conectar(String ip, int porto, String nome) {
         try {    
            Registry registry = LocateRegistry.getRegistry(ip, porto);
            servidor = (IntServidor) registry.lookup("ServidorDeFicheiros");
            
            String meuIP = InetAddress.getLocalHost().getHostAddress();
            IntCliente clienteStub = (IntCliente) this;
            if (nome.length() != 0){
                Utilizador novoUtilizador = new Utilizador(nome, meuIP, porto);
                utilizadorAtual = servidor.login(novoUtilizador, clienteStub);

                if (utilizadorAtual != null ) {  
                    return 1;
                }
                return 0;
            }else {
                return -1;
            }
            
        } catch(ConnectException e){
            System.err.println("Erro na conexão: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
            System.err.println("Erro na conexão: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    public synchronized void desconectar() {
        if (servidor != null && utilizadorAtual != null) {
            try {
                servidor.logout(utilizadorAtual);
            } catch (Exception e) {
                System.err.println("Erro ao desconectar do servidor: " + e.getMessage());
            }
        }
    }
    
    public Vector<Utilizador> getUtilizadoresOnline() {
        try {
            return servidor.getUtilizadoresOnline();
        } catch (RemoteException e) {
            System.err.println("Erro ao obter utilizadores online: " + e.getMessage());
            return new Vector<>();
        }
    }
    
    public synchronized void setPastaPartilha(String path) {
        this.pastaPartilha = path;
    }
    
    public Vector<String> getLogs() {
        try {
            return servidor.getLogs(utilizadorAtual.getLoginTime());
        } catch (Exception e) {
            System.err.println("Erro ao obter logs: " + e.getMessage());
            return new Vector<>();
        }
    }
    
    // escreve log do download
    public void registarTransferencia(String origem, String destino, String ficheiro) {
        try {
            servidor.addLog(new Log(origem + " enviou " + ficheiro + " para " + destino));
        } catch (Exception e) {
            System.err.println("Erro ao registar transferência: " + e.getMessage());
        }
    }
    
    // obtem a lista de ficheiro do utilizador
    public Vector<String> obterListaFicheiros(String nomeUtilizador) {
        try {
            IntCliente cliente = getClienteStub(nomeUtilizador);
            if (cliente != null) {
                return new Vector<>(cliente.listarFicheiros());
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter lista de ficheiros: " + e.getMessage());
        }
        return new Vector<>();
    }
    
    private IntCliente getClienteStub(String nome) {
        try {
            return servidor.getCliente(nome);
        } catch (RemoteException e) {
            System.err.println("Erro ao obter stub do cliente: " + e.getMessage());
            return null;
        }
    }
    
    public synchronized boolean downloadFile(String nomeUtilizador, String nomeFicheiro, File destino) {
        try {
            IntCliente cliente = getClienteStub(nomeUtilizador);
            if (cliente != null) {
                byte[] dados = cliente.downloadFile(nomeFicheiro);
                if (dados != null) {
                    Files.write(destino.toPath(), dados);
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro no download: " + e.getMessage());
        }
        return false;
    }
    
    
    public synchronized Utilizador getUtilizadorAtual() {
        return utilizadorAtual;
    }

    public synchronized String getPastaPartilha() {
        return pastaPartilha;
    }
    
    
    // implementacao dos metodos da interface IntCliente
    @Override
    public Vector<String> listarFicheiros() throws RemoteException {
        if (pastaPartilha == null) return new Vector<> ();
        
        File pasta = new File(pastaPartilha);
        String[] ficheiros = pasta.list();
        
        if(ficheiros != null){
            return new Vector<>(Arrays.asList(ficheiros));
        }else {
            return new Vector<> ();
        }      
    }
    
    @Override
    public byte[] downloadFile(String nomeFicheiro) throws RemoteException {
        if (pastaPartilha == null) return null;
        
        File ficheiro = new File(pastaPartilha, nomeFicheiro);
        if (!ficheiro.exists() || !ficheiro.isFile()) return null;
        
        try {
            return Files.readAllBytes(ficheiro.toPath());
        } catch (IOException e) {
            throw new RemoteException("Erro ao ler ficheiro", e);
        }
    }

}
