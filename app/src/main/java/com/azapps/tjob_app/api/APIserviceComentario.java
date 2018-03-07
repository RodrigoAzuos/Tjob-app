package com.azapps.tjob_app.api;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.azapps.tjob_app.Interfaces.ComentarioEndPoint;
import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.models.Comentario;

import java.util.List;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

public class APIserviceComentario extends APIservice{
    ComentarioEndPoint mComentarioEndPoint;

    public APIserviceComentario(Context context) {
        super(context);
        initEndPoists();
    }


    @Override
    public void initEndPoists() {
        mComentarioEndPoint = retrofit.create(ComentarioEndPoint.class);
    }

    public void getComentarios(final Retrofitable retrofitable, long id){
        Call<List<Comentario>> call = mComentarioEndPoint.getComentarios(id);

        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()){
                    retrofitable.onRetrofitResponse(response.code(), response.body());
                }else{
                    retrofitable.Loading();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
            }
        });
    }

    public void posComentario(final  Retrofitable retrofitable, long id, Comentario comentario, final Context context){
        Call<Comentario> call = mComentarioEndPoint.postComentario(id,comentario);

        call.enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, R.string.comentario_postado, Toast.LENGTH_SHORT).show();
                    retrofitable.Loading();
                }
            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {
                Toast.makeText(context, R.string.sem_conexao, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
