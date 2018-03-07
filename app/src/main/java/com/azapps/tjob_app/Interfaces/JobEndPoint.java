package com.azapps.tjob_app.Interfaces;

import com.azapps.tjob_app.models.JobDetalhado;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.modelsApi.MensagemApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rodrigo-souza on 05/03/18.
 */

public interface JobEndPoint {


    @GET("job/")
    Call<List<JobSimples>> getJobs();

    @GET ("job/{id}/like/")
    Call<MensagemApi> like(@Path("id") long id);

    @GET("job/{id}/deslike/")
    Call<MensagemApi> deslike(@Path("id") long id);

    @POST("job/")
    Call<JobSimples> postJob(@Body JobSimples jobSimples);

    @PUT("job/{id}/")
    Call<JobSimples> putJob(@Path("id")long id, @Body JobSimples jobSimples);

    @GET("job/{id}/")
    Call<JobDetalhado> getJob(@Path("id") long id);

    @GET("job/")
    Call<List<JobSimples>> getMeusJobs(@Query("criador") long id);

    @DELETE("job/{id}/")
    Call<JobSimples> deleteJob(@Path("id") long id);

    @GET("job/{id}/perfil/{pk}")
    Call<MensagemApi> match(@Path("id") long id, @Path("pk") long pk);

    @GET("job/")
    Call<List<JobSimples>> meusMatches(@Query("escolhido") long id);

    @GET("job/")
    Call<List<JobSimples>> minhasCurtidas(@Query("curtidas") long id);

}
