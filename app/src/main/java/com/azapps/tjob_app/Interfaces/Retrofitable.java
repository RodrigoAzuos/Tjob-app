package com.azapps.tjob_app.Interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by rodrigo on 10/04/17.
 */

public interface Retrofitable {

//    @Nullable
//    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public void onRetrofitResponse(int status, List list);

    public void onRetrofitFailure(Throwable t);

    public void onLoginresponse(int status, String token);

    public void Loading();

}
