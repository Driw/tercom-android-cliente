package br.com.tercom.Entity;

import java.util.ArrayList;
import java.util.Date;


public class Message extends GenericEntity {

    public static final String status1 = "status teste";
    //Definir status como constantes.

    private String subject;
    private Date date;
    private ArrayList<MessageItem> mensagens;
    private String Status;

    public static String getStatus1() {
        return status1;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        Status = status;
    }
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
