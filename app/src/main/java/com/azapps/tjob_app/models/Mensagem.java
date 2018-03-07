package com.azapps.tjob_app.models;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

//status = models.CharField('Status', max_length=64,choices= MENSAGEM_CHOICES, default= 'enviada', blank=False, null=False)
//        texto = models.CharField('Texto', max_length=256,  blank=False, null=False)
//        chat = models.ForeignKey('Chat', related_name='mensagens', blank=False, null=False)
//        remetente = models.ForeignKey(Perfil, related_name='minhas_mensagens', blank=False, null=False)

public class Mensagem {

    private long id;
    private String texto;
    private long chat;
    private long remetente;

    public Mensagem(String texto, long chat, long remetente) {
        this.texto = texto;
        this.chat = chat;
        this.remetente = remetente;
    }

    public long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getChat() {
        return chat;
    }

    public void setChat(long chat) {
        this.chat = chat;
    }

    public long getRemetente() {
        return remetente;
    }

    public void setRemetente(long remetente) {
        this.remetente = remetente;
    }
}
