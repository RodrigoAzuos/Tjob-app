package com.azapps.tjob_app.controle;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIservice;
import com.azapps.tjob_app.api.APIserviceLogin;
import com.azapps.tjob_app.models.Usuario;
import com.azapps.tjob_app.ui.BottomMenuActivity;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

public class ControlerUserActivity extends AppCompatActivity implements Retrofitable {

    PrenferencesUltil mPrenferencesUltil;
    ProgressBar mProgressBar;
    APIserviceLogin mApIserviceLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controler_user);

        mApIserviceLogin = new APIserviceLogin(this);
        mProgressBar = findViewById(R.id.progress_controler_usuario);
        mPrenferencesUltil = new PrenferencesUltil(this);
        mApIserviceLogin.getUsuarioLogado(this);


    }


    @Override
    public void onRetrofitResponse(int status, List list) {
        if (status >= 200 && status < 300){
            long usuarioId = ((Usuario) list.get(0)).getId();
            mPrenferencesUltil.storeLong(ConstantsUtil.USUARIO_ID, usuarioId);
            startActivity(new Intent(this, BottomMenuActivity.class));
            finish();
        }

    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        mProgressBar.setVisibility(View.GONE);
        Snackbar snackbar = Snackbar.make(mProgressBar, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        //TODO COLOCAR AÇÃO NA SNECKBAR PARA RECARREGAR A TELA

    }

    @Override
    public void onLoginresponse(int status, String token) {

    }

    @Override
    public void Loading() {

    }
}
