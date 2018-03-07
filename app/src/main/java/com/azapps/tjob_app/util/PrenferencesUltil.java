package com.azapps.tjob_app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rodrigo-souza on 04/03/18.
 */

public class PrenferencesUltil {

    private  Context mContext;
    private SharedPreferences mSharedPreferences;

    public PrenferencesUltil(Context context) {
        this.mContext = context;
        this.mSharedPreferences = mContext.getSharedPreferences("Tjob", Context.MODE_PRIVATE);
    }

    public void storeString(String chave, String valor){
        this.mSharedPreferences.edit().putString(chave, valor).commit();
    }

    public String getStoredString(String chave){
        return this.mSharedPreferences.getString(chave, "-1");
    }

    public void storeLong(String chave, long valor){
        this.mSharedPreferences.edit().putLong(chave, valor).commit();
    }

    public Long getStoredLong(String chave){
        return this.mSharedPreferences.getLong(chave, -1);
    }

    public void limparPreferencias(){
        this.mSharedPreferences.edit().clear().commit();
    }

}
