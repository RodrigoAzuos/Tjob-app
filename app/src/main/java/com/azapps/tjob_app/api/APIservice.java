package com.azapps.tjob_app.api;

import android.content.Context;

import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public abstract class APIservice {

    public static String TAG = APIservice.class.getCanonicalName();

    public static  final String BASE_URL = "https://tjob.herokuapp.com/api/v1/";
//    public static  final String BASE_URL = "http://192.168.0.116:8000/api/v1/";
    public Retrofit retrofit;
    public Interceptor interceptor;
    private PrenferencesUltil prenferencesUltil;

    public APIservice(Context context) {
        prenferencesUltil = new PrenferencesUltil(context);

        this.interceptor = new InterceptorMy("token "+prenferencesUltil.getStoredString(ConstantsUtil.TOKEN));

        OkHttpClient.Builder builderCliente = new OkHttpClient.Builder();
        builderCliente.interceptors().add(this.interceptor );
        OkHttpClient cliente = builderCliente.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        retrofit = builder.baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(cliente)
                .build();
    }

    public abstract void initEndPoists();
}
