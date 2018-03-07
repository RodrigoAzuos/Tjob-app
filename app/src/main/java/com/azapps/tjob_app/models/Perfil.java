package com.azapps.tjob_app.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public class Perfil {

    private long id;
    private String nome;
    private String telefone;
    @SerializedName("data_nascimento")
    private String dataNascimento;
    private String sexo;
    @SerializedName("perfil_profissional")
    private String perfiProfissional;
    private String gitHub;
    private String experiencia;
    private long usuario;
    private Endereco endereco;

    public Perfil() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPerfiProfissional() {
        return perfiProfissional;
    }

    public void setPerfiProfissional(String perfiProfissional) {
        this.perfiProfissional = perfiProfissional;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }




}
