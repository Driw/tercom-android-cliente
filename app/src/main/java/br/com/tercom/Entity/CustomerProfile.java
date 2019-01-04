package br.com.tercom.Entity;

import java.text.ParseException;

public class CustomerProfile extends GenericEntity {
    private int id;
    private String name;
    private int assignmentLevel;
    private static final int MIN_NAME_LEN = 3;
    private static final int MAX_NAME_LEN = 64;

    public CustomerProfile(){
        id = 0;
        name = "";
        assignmentLevel = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws ParseException {
        if(name.length() < MIN_NAME_LEN || name.length() > MAX_NAME_LEN)
            throw new ParseException(String.format("Nome deve possuir de %s a %s caracteres", MIN_NAME_LEN, MAX_NAME_LEN), 0);
        this.name = name;
    }

    public int getAssignmentLevel() {
        return assignmentLevel;
    }

    public void setAssignmentLevel(int assignmentLevel) {
        if(assignmentLevel < Permission.MIN_ASSIGNMENT_LEVEL || assignmentLevel > Permission.MAX_ASSIGNMENT_LEVEL)
            throw new NumberFormatException(String.format("Nível de assinatura deve ser de %s a %s", Permission.MIN_ASSIGNMENT_LEVEL, Permission.MAX_ASSIGNMENT_LEVEL));
        this.assignmentLevel = assignmentLevel;
    }
}
