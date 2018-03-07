package com.azapps.tjob_app.Interfaces;

import com.azapps.tjob_app.models.Comentario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

public interface ComentarioEndPoint {

    @GET("job/{id}/comentarios/")
    Call<List<Comentario>> getComentarios(@Path("id") long id);

    @POST("job/{id}/comentarios/")
    Call<Comentario> postComentario(@Path("id") long id, @Body Comentario comentario);
}
