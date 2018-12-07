package br.com.tercom.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.tercom.Entity.TercomFuncionario;

@Dao
public interface TercomFuncionarioDAO extends GenericDAO<TercomFuncionario>{

    //TODO("Corrigir Query")
    @Query("SELECT * FROM tercom_funcionario WHERE tercom_funcionario.id = :id")
    TercomFuncionario getFuncionario(int id);

    @Query("SELECT * FROM tercom_funcionario")
    List<TercomFuncionario> getListFuncionario();
}
