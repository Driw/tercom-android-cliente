package br.com.tercom.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tercom_funcionario")
public class TercomFuncionario {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cpf")
    private String cpf;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name =  "email")
    private String email;

    @ColumnInfo(name = "senha")
    private String senha;


    //    TODO(Rever como irá ficar nesse caso, que é um atributo do tipo Phone)
//    @ColumnInfo(name = "telefone")
//    private Phone telefone;

//    @ColumnInfo(name = "celular")
//    private Phone celular;

    @ColumnInfo(name = "data_registro")
    private String dataRegistro;

    @ColumnInfo(name = "ativo")
    private boolean ativo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
