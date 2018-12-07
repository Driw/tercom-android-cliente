package br.com.tercom.DAO;

import android.arch.persistence.room.Query;

import java.util.List;

import br.com.tercom.Entity.Phone;

public interface TelefoneDAO extends GenericDAO<Phone> {

    @Query("SELECT * FROM Phone")
    abstract public List<Phone> getTelefoneList();
}
