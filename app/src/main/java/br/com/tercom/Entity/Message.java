package br.com.tercom.Entity;

import java.util.ArrayList;
import java.util.Date;


public class Message extends GenericEntity {

    private String subject;
    private Date date;
    private ArrayList<String> messages;
    private String userEmai;

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public ArrayList<String> getMessages() { return messages;}
    public void setMessages(ArrayList<String> messages) { this.messages = messages; }
    public String getUserEmai() { return userEmai; }
    public void setUserEmai(String userEmai) { this.userEmai = userEmai; }

    
}
