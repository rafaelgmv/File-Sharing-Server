/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proj.comum;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author rafae
 */
public class Log implements Serializable {
    private long dataLog;
    private String mensagem;
    
    public Log(String mensagem){
        this.mensagem = mensagem;
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
        this.dataLog = zdt.toInstant().toEpochMilli();
    }
    
    
    public Long getDataLog() {
        return dataLog;
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        // converter timestamp long para LocalDateTime
        LocalDateTime ldt = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(dataLog), 
            ZoneId.systemDefault()
        );
        
        // formatar para HH:mm:ss
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = ldt.format(formato);
        
        return "[" + horaFormatada + "] " + mensagem;
    }
    
    
}
