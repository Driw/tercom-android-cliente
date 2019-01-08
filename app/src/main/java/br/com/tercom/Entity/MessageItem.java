package br.com.tercom.Entity;

import java.util.ArrayList;
import java.util.Date;

public class MessageItem extends GenericEntity{

    private String message;
    private int idUser;
    private Date messageTime;

    public Date getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
