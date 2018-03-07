package com.azapps.tjob_app.Interfaces;

import java.util.List;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

public interface RetrofitableMetodos {

    public void onRetrofitFailure(Throwable t);

    public void onRetrofitGet(int status, List list);

    public void onRetrofitCreate(int status, Object object);

    public void onRetrofitPut(int status, Object object);

    public void onRetrofitRetrive(int status, Object object);

    public void onRetrofitDelete(int status);

}
