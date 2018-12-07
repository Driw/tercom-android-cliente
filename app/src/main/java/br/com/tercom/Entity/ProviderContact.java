package br.com.tercom.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import br.com.tercom.Annotation.BindObject;

@Entity(tableName = "ProviderContact")
public class ProviderContact extends GenericEntity
{
    private int id;

    private String name ;
    
    private String position;
    
    private String email;

    @BindObject()
    private Phone commercial;

    @BindObject()
    private Phone otherphone;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Phone getCommercial() {
        return commercial;
    }

    public void setCommercial(Phone commercial) {
        this.commercial = commercial;
    }

    public Phone getOtherphone() {
        return otherphone;
    }

    public void setOtherphone(Phone otherphone) {
        this.otherphone = otherphone;
    }

}
