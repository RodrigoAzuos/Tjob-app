package com.azapps.tjob_app.models;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

public class Comentario {

    private long id;
    private String descricao;
    private long job;

    public Comentario(String descricao, long job) {
        this.descricao = descricao;
        this.job = job;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}