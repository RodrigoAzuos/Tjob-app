package com.azapps.tjob_app.models;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public class Endereco {

    private long id;
    private String logradouro;
    private String estado;
    private String cidade;
    private float cep;

    public Endereco() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Float getCep() {
        return cep;
    }

    public void setCep(Float cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", logradouro='" + logradouro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }
}
