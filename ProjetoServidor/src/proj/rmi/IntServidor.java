/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proj.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import proj.comum.Log;
import proj.comum.Utilizador;


/**
 *
 * @author rafae
 */ 
public interface IntServidor extends Remote {
    Utilizador login(Utilizador utilizador, IntCliente stubCliente) throws RemoteException;
    void logout(Utilizador utilizador) throws RemoteException;
    Vector<Utilizador> getUtilizadoresOnline() throws RemoteException;
    void addLog(Log log) throws RemoteException;
    Vector<String> getLogs(long desde) throws RemoteException;
    IntCliente getCliente(String nome) throws RemoteException;
}
