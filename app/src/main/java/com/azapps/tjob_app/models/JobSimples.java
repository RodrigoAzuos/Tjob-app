package com.azapps.tjob_app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo-souza on 05/03/18.
 */

public class JobSimples {

//    "id": 1,
//            "status": "aberto",
//            "titulo": "Desenvolvedor java",
//            "descricao": "Projeto Java Web",
//            "criador": 4,
//            "nome_criador": "elane souza",
//            "publico": true,
//            "curtidas": []

    private long id;
    private String status;
    private String titulo;
    private String descricao;
    private long criador;
    @SerializedName("nome_criador")
    private String nomeCriador;
    private boolean publico;
    protected List<Long> curtidas;
    private String cidade;

    public JobSimples() {
        this.curtidas = new ArrayList<Long>();
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getCriador() {
        return criador;
    }

    public void setCriador(long criador) {
        this.criador = criador;
    }

    public String getNomeCriador() {
        return nomeCriador;
    }

    public void setNomeCriador(String nomeCriador) {
        this.nomeCriador = nomeCriador;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public List<Long> getCurtidas() {
        return curtidas;
    }

    public String getStatus() {
        return status;
    }

    public String getCidade() {
        return cidade;
    }
}
