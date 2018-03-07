package com.azapps.tjob_app.api;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.azapps.tjob_app.Interfaces.JobEndPoint;
import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.Interfaces.RetrofitableMetodos;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.models.JobDetalhado;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.modelsApi.MensagemApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo-souza on 05/03/18.
 */

public class APIserviceJob extends APIservice {
    JobEndPoint mJobEndPoint;
    List<JobSimples> jobs;


    public APIserviceJob(Context context) {
        super(context);
        initEndPoints();
    }

    @Override
    public void initEndPoints() {
        mJobEndPoint = retrofit.create(JobEndPoint.class);
        jobs = new ArrayList<JobSimples>();
    }

    public void getJobs(final Retrofitable retrofitable){
        Call<List<JobSimples>> call = mJobEndPoint.getJobs();

        call.enqueue(new Callback<List<JobSimples>>() {
            @Override
            public void onResponse(Call<List<JobSimples>> call, Response<List<JobSimples>> response) {
                if (response.isSuccessful()){
                    jobs = response.body();
                    retrofitable.onRetrofitResponse(response.code(), jobs);
                }else{
                    retrofitable.Loading();
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<List<JobSimples>> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
            }
        });

    }

    public void like(final ProgressBar progressBar, final Context context, long id){
        Call<MensagemApi> call = mJobEndPoint.like(id);

        call.enqueue(new Callback<MensagemApi>() {
            @Override
            public void onResponse(Call<MensagemApi> call, Response<MensagemApi> response) {

                Toast.makeText(context, "response: " + response.body().getMensagem(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MensagemApi> call, Throwable t) {
                Toast.makeText(context, "Falha", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void deslike(final ProgressBar progressBar, final Context context, long id){

        Call call = mJobEndPoint.deslike(id);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                //Todo não entra aqui por causa do retorno da api.
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, R.string.deslike_job, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void postJob(final Context context, final RetrofitableMetodos retrofitable, JobSimples jobSimples){
        Call<JobSimples> call = mJobEndPoint.postJob(jobSimples);

        call.enqueue(new Callback<JobSimples>() {
            @Override
            public void onResponse(Call<JobSimples> call, Response<JobSimples> response) {
                if(response.isSuccessful()){
                    retrofitable.onRetrofitCreate(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<JobSimples> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
                call.cancel();
            }
        });
    }

    public void getMeusJobs(final Context context, final Retrofitable retrofitable, long id){
        Call<List<JobSimples>> call = mJobEndPoint.getMeusJobs(id);

        call.enqueue(new Callback<List<JobSimples>>() {
            @Override
            public void onResponse(Call<List<JobSimples>> call, Response<List<JobSimples>> response) {
                if(response.isSuccessful()){
                    retrofitable.onRetrofitResponse(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JobSimples>> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
                call.cancel();
            }
        });

    }

    public void getJobDetalhe(final RetrofitableMetodos retrofitable, long id){
        Call<JobDetalhado> call = mJobEndPoint.getJob(id);

        call.enqueue(new Callback<JobDetalhado>() {
            @Override
            public void onResponse(Call<JobDetalhado> call, Response<JobDetalhado> response) {
                if (response.isSuccessful()){
                    retrofitable.onRetrofitRetrive(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<JobDetalhado> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
            }
        });
    }

    public void deleteJob(final RetrofitableMetodos retrofitableMetodos, long id){
        Call<JobSimples> call = mJobEndPoint.deleteJob(id);

        call.enqueue(new Callback<JobSimples>() {
            @Override
            public void onResponse(Call<JobSimples> call, Response<JobSimples> response) {
                if(response.isSuccessful()){
                    retrofitableMetodos.onRetrofitDelete(response.code());
                }
            }

            @Override
            public void onFailure(Call<JobSimples> call, Throwable t) {
                retrofitableMetodos.onRetrofitFailure(t);
            }
        });
    }

    public void putJob(final RetrofitableMetodos retrofitableMetodos, JobSimples jobSimples, long id){
        Call<JobSimples> call = mJobEndPoint.putJob(id,jobSimples);

        call.enqueue(new Callback<JobSimples>() {
            @Override
            public void onResponse(Call<JobSimples> call, Response<JobSimples> response) {
                retrofitableMetodos.onRetrofitPut(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<JobSimples> call, Throwable t) {
                retrofitableMetodos.onRetrofitFailure(t);
            }
        });

    }

    public void match(final RetrofitableMetodos retrofitableMetodos, long jobId, long perfilId){
        Call<MensagemApi> call = mJobEndPoint.match(jobId, perfilId);

        call.enqueue(new Callback<MensagemApi>() {
            @Override
            public void onResponse(Call<MensagemApi> call, Response<MensagemApi> response) {
                if (response.isSuccessful())
                retrofitableMetodos.onRetrofitCreate(response.code(), response.body());
            }

            @Override
            public void onFailure(Call<MensagemApi> call, Throwable t) {
                retrofitableMetodos.onRetrofitFailure(t);
            }
        });
    }

    public void meusMacthes(final Retrofitable retrofitable, long id){
        Call<List<JobSimples>> call = mJobEndPoint.meusMatches(id);

        call.enqueue(new Callback<List<JobSimples>>() {
            @Override
            public void onResponse(Call<List<JobSimples>> call, Response<List<JobSimples>> response) {
                if(response.isSuccessful()){
                    retrofitable.onRetrofitResponse(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JobSimples>> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
            }
        });


    }

    public void minhasCurtidas(final Retrofitable retrofitable, long id){
        Call<List<JobSimples>> call = mJobEndPoint.minhasCurtidas(id);

        call.enqueue(new Callback<List<JobSimples>>() {
            @Override
            public void onResponse(Call<List<JobSimples>> call, Response<List<JobSimples>> response) {
                if(response.isSuccessful()){
                    retrofitable.onRetrofitResponse(response.code(), response.body());
                }
            }

            @Override
            public void onFailure(Call<List<JobSimples>> call, Throwable t) {
                retrofitable.onRetrofitFailure(t);
            }
        });
    }


}
