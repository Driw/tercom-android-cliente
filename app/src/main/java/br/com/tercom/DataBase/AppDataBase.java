package br.com.tercom.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.tercom.DAO.TercomFuncionarioDAO;
import br.com.tercom.Entity.TercomFuncionario;

/**
 * Classe utilizada para gerar a interação com o Banco de Dados utilizando o utilitário Room
 */
@Database(entities = TercomFuncionario.class, exportSchema = false, version = 1)
abstract public class AppDataBase extends RoomDatabase
{
    /**
     * Método abstrato para gerar a DAO de TercomFuncionario.
     * @return instância da DAO referente
     */
    abstract public TercomFuncionarioDAO tercomFuncionarioDao();
}
