/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proj.comum;

import java.io.Serializable;

/**
 *
 * @author rafae
 */
public class Utilizador implements Serializable {
    private String nome;
    private String enderecoIP;
    private int porto;
    private long loginTime;

    public Utilizador(){
    }
    public Utilizador(String nome, String enderecoIP, int porto) {
        this.nome = nome;
        this.enderecoIP = enderecoIP;
        this.porto = porto;
    }
    
    public String getNome() { return nome; }
    public String getEnderecoIP() { return enderecoIP; }
    public int getPorto(){return porto;}
    public long getLoginTime() { return loginTime; }
    public void setLoginTime(long loginTime) { this.loginTime = loginTime; }
    
    @Override
    public String toString() {
        return nome + "," + enderecoIP + "," + porto;
    }
}
