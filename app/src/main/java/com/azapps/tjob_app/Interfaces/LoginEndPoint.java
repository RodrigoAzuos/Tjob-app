package com.azapps.tjob_app.Interfaces;

import com.azapps.tjob_app.models.Usuario;
import com.azapps.tjob_app.modelsApi.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public interface LoginEndPoint {

    @POST("token/")
    Call<Token> getToken(@Body Usuario usuario);

    @POST("users/")
    Call<Usuario> postUser(@Body Usuario usuario);

    @GET("usuario-logado/")
    Call<List<Usuario>> usuarioLogado();

}
