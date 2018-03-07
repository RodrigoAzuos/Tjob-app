package com.azapps.tjob_app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

public class JobDetalhado {

    private long id;
    private String status;
    private String titulo;
    private String descricao;
    private long criador;
    @SerializedName("nome_criador")
    private String nomeCriador;
    private boolean publico;
    private List<Perfil> curtidas;
    private List<Comentario> comentarios;
    private Perfil escolhido;

    public JobDetalhado()  {
        super();
        this.curtidas = new ArrayList<Perfil>();
        this.comentarios = new ArrayList<Comentario>();
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public long getCriador() {
        return criador;
    }

    public String getNomeCriador() {
        return nomeCriador;
    }

    public boolean isPublico() {
        return publico;
    }

    public List<Perfil> getCurtidas() {
        return curtidas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public Perfil getEscolhido() {
        return escolhido;
    }
}
