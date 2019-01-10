package br.com.tercom.Entity;

import java.util.ArrayList;
import java.util.Date;


public class Message extends GenericEntity {

    public static final String status = "status teste";
    //Definir status como constantes.

    private String subject;
    private Date date;
    private ArrayList<MessageItem> mensagens;

    public String getStatus() { return status; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public ArrayList<MessageItem> getMensagens() {
        return mensagens;
    }
    public void setMensagens(ArrayList<MessageItem> mensagens) {
        this.mensagens = mensagens;
    }

}
