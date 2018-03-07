package com.azapps.tjob_app.api;

import android.content.Context;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.PerfilEndPoint;
import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.Interfaces.RetrofitableMetodos;
import com.azapps.tjob_app.models.Perfil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public class APIservicePerfil extends APIservice {

    private PerfilEndPoint perfilEndPoint;
    private List<Perfil> perfis;

    public APIservicePerfil(Context context) {
        super(context);
        initEndPoints();
    }

    @Override
    public void initEndPoints() {
        perfilEndPoint = retrofit.create(PerfilEndPoint.class);
        perfis = new ArrayList<>();

    }

    public void postPerfil(final Retrofitable retrofitable, final Perfil perfil){

        Call<Perfil>  call = perfilEndPoint.postPerfil(perfil);

        call.enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                try {
                    perfis.add(response.body());
                    retrofitable.onRetrofitResponse(response.code(), perfis);
                }catch (Exception e){
                    retrofitable.Loading();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);

            }
        });
    }

    public void perfilLogado(final Retrofitable retrofitable){
        Call<List<Perfil>> call = perfilEndPoint.perfilLogado();

        call.enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                try {
                    perfis = response.body();
                    retrofitable.onRetrofitResponse(response.code(), perfis);
                }catch (Exception e){
                    retrofitable.Loading();
                }
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
                call.cancel();
            }
        });
}

    public void getPerfil(final RetrofitableMetodos retrofitableMetodos, long id, final Context context){
        Call<Perfil> call = perfilEndPoint.getPerfil(id);

        call.enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                Toast.makeText(context, " " + response.code(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                    retrofitableMetodos.onRetrofitRetrive(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {

                retrofitableMetodos.onRetrofitFailure(t);
                call.cancel();
            }
        });

    }


}
