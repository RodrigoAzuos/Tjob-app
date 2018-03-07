package com.azapps.tjob_app.api;

import android.content.Context;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.LoginEndPoint;
import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.models.Usuario;
import com.azapps.tjob_app.modelsApi.Token;
import com.azapps.tjob_app.ui.BottomMenuActivity;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public class APIserviceLogin extends APIservice {
    LoginEndPoint loginEndPoint;
    List<Usuario> usuarios;

    public APIserviceLogin(Context context) {
        super(context);
        initEndPoists();
    }

    @Override
    public void initEndPoists() {
        loginEndPoint = retrofit.create(LoginEndPoint.class);
        usuarios = new ArrayList<Usuario>();
    }


    public void getToken(final Retrofitable retrofitable, Usuario user, final Context context){
        Call<Token> call = loginEndPoint.getToken(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                try {
                    retrofitable.onLoginresponse(response.code(), response.body().getToken());
                }catch (Exception e){
                    retrofitable.Loading();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
            }
        });

    }

    public void postUser(final Retrofitable retrofitable, final Usuario usuario, final Context context){
        Call<Usuario> call = loginEndPoint.postUser(usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {
                    usuarios.add(response.body());
                    retrofitable.onRetrofitResponse(response.code(), usuarios);
                }catch (Exception e){
                   retrofitable.Loading();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
                call.cancel();
            }
        });
    }

    public void getUsuarioLogado(final Retrofitable retrofitable){
        Call<List<Usuario>> call = loginEndPoint.usuarioLogado();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                if(response.isSuccessful()){
                    List<Usuario> usuarios = response.body();
                    retrofitable.onRetrofitResponse(response.code(),usuarios);
                }else{
                    retrofitable.Loading();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
            }
        });
    }


}
