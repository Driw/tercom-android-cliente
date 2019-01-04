package br.com.tercom.Entity;

import java.text.ParseException;

public class Permission extends GenericEntity {
    private int id;
    private String packet;
    private String action;
    private int assignmentLevel;
    public static final int MAX_ACTION_NAME_LEN = 32;
    public static final int MAX_PACKET_NAME_LEN = 32;
    public static final int MIN_ASSIGNMENT_LEVEL = 0;
    public static final int MAX_ASSIGNMENT_LEVEL = 99;

    public Permission(){
        id = 0;
        packet = "";
        action = "";
        assignmentLevel =  0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) throws ParseException {
        if(packet.length() > MAX_PACKET_NAME_LEN)
            throw new ParseException(String.format("Pacote de permissão deve possuir até %s caracteres", String.valueOf(MAX_PACKET_NAME_LEN)), MAX_PACKET_NAME_LEN + 1);
        this.packet = packet;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) throws ParseException {
        if(action.length() > MAX_ACTION_NAME_LEN)
            throw new ParseException(String.format("Permissão deve possuir até %s caracteres", String.valueOf(MAX_ACTION_NAME_LEN)), MAX_ACTION_NAME_LEN + 1);
        this.action = action;
    }

    public int getAssignmentLevel() {
        return assignmentLevel;
    }

    public void setAssignmentLevel(int assignmentLevel) {
        if(assignmentLevel < MIN_ASSIGNMENT_LEVEL || assignmentLevel > MAX_ASSIGNMENT_LEVEL)
            throw new NumberFormatException(String.format("Nível de assinatura deve ser de %s a %s", MIN_ASSIGNMENT_LEVEL, MAX_ASSIGNMENT_LEVEL));
        this.assignmentLevel = assignmentLevel;
    }
}
