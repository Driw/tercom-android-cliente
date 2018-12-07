package br.com.tercom.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.util.Pair;

import java.util.ArrayList;

@Entity(tableName = "Phone")
public class Phone extends GenericEntity
{
    private int id;
    private int ddd;
    private String number;
    private String type;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Pair<String,String>> getCompletePair(){
        ArrayList<Pair<String,String>> arrayPair = new ArrayList<>();
        arrayPair.add(new Pair<>("ddd",String.valueOf(ddd)));
        arrayPair.add(new Pair<>("number",number));
        arrayPair.add(new Pair<>("type",type));

        return arrayPair;

    }

}