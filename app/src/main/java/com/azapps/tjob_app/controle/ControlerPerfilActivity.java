package com.azapps.tjob_app.controle;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;

import com.azapps.tjob_app.api.APIservicePerfil;
import com.azapps.tjob_app.models.Perfil;
import com.azapps.tjob_app.ui.BottomMenuActivity;
import com.azapps.tjob_app.ui.CriarPerfilActivity;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ControlerPerfilActivity extends AppCompatActivity implements Retrofitable {

    private APIservicePerfil mAPIservicePerfil;
    private PrenferencesUltil mPrenferencesUltil;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controler_perfil);

        bindView();
        setupView();
    }

    public void setupView(){
        mPrenferencesUltil = new PrenferencesUltil(this);
        mAPIservicePerfil = new APIservicePerfil(this);
        mAPIservicePerfil.perfilLogado(this);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void bindView(){
        mProgressBar = findViewById(R.id.progress_controler_perfil);
    }

    @Override
    public void onRetrofitResponse(int status, List list) {
        if (status >= 200 && status < 300){
            if(list.size() < 1){
                startActivity(new Intent(this, CriarPerfilActivity.class));
                mProgressBar.setVisibility(View.GONE);
                finish();
            }else{
                startActivity(new Intent(this, BottomMenuActivity.class));
                mPrenferencesUltil.storeLong(ConstantsUtil.PERFIL_ID, ((Perfil)list.get(0)).getId());
                mProgressBar.setVisibility(View.GONE);
                finish();
            }
        }
    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar.make(mProgressBar, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void onLoginresponse(int status, String token) {

    }

    @Override
    public void Loading() {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, R.string.falha_na_conexao, Toast.LENGTH_SHORT).show();
    }
}
