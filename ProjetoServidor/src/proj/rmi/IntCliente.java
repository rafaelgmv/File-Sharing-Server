/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proj.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
/**
 *
 * @author rafae
 */
public interface IntCliente extends Remote {
    Vector<String> listarFicheiros() throws RemoteException;
    byte[] downloadFile(String nomeArquivo) throws RemoteException;
}
