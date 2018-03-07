package com.azapps.tjob_app.Interfaces;

import com.azapps.tjob_app.models.Perfil;
import com.azapps.tjob_app.models.Usuario;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public interface PerfilEndPoint {

    @POST("perfil/")
    Call<Perfil> postPerfil(@Body Perfil perfil);

    @GET("perfil-logado/")
    Call<List<Perfil>> perfilLogado();

    @GET("perfil/{id}/")
    Call<Perfil> getPerfil(@Path("id") long id);
}
