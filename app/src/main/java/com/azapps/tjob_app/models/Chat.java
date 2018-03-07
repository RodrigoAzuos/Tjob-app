package com.azapps.tjob_app.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

//participantes = models.ManyToManyField(Perfil, related_name='chats', blank=False)
public class Chat {
    private long id;
    private List<String> participantes;

    public Chat() {
        this.participantes = new ArrayList<String>();
    }

    public long getId() {
        return id;
    }

    public List<String> getParticipantes() {
        return participantes;
    }

}
