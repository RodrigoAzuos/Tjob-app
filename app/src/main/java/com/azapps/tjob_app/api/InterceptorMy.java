package com.azapps.tjob_app.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public class InterceptorMy implements Interceptor {

    public  static final String AUTHORIZATION = "Authorization";
    public static String TOKEN = "-1";

    public InterceptorMy(String token) {
        InterceptorMy.TOKEN = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION, TOKEN)
                .build();

        return chain.proceed(request);
    }
}
