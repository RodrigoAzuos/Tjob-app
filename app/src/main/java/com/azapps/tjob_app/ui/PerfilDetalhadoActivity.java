package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.RetrofitableMetodos;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.api.APIservicePerfil;
import com.azapps.tjob_app.models.Perfil;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

public class PerfilDetalhadoActivity extends AppCompatActivity implements View.OnClickListener, RetrofitableMetodos{
    private TextView edtNome;
    private TextView edtTelefone;
    private TextView edtExperiencia;
    private TextView edtPerfilProfissional;
    private TextView edtDataNascimento;
    private Button mButtonSelecionar;
    private ProgressBar mProgressBar;
    private APIservicePerfil mAPIservicePerfil;
    private APIserviceJob mAPIserviceJob;
    private PrenferencesUltil mPrenferencesUltil;
    private long perfilId;
    private long jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_detalhado);
        bindView();
        setupView();
    }

    public void bindView(){
        edtTelefone = findViewById(R.id.perfil_detalhado_telefone);
        edtDataNascimento = findViewById(R.id.perfil_detalhado_data_nascimento);
        edtNome = findViewById(R.id.perfil_detalhado_nome);
        edtExperiencia = findViewById(R.id.perfil_detalhado_experiencia);
        edtPerfilProfissional = findViewById(R.id.perfil_detalhado_perfilprofissional);
        mButtonSelecionar = findViewById(R.id.btn_perfil_detalhado_selecionar);
        mProgressBar = findViewById(R.id.progress_perfil_detalhado);


    }

    public void setupView(){
        mButtonSelecionar.setOnClickListener(this);
        mAPIservicePerfil = new APIservicePerfil(this);
        mPrenferencesUltil = new PrenferencesUltil(this);
        mAPIserviceJob = new APIserviceJob(this);

        Intent intent = getIntent();
        perfilId = intent.getLongExtra(ConstantsUtil.PERFIL_ID, -1);
        jobId = intent.getLongExtra(ConstantsUtil.JOB_ID, -1);
        mProgressBar.setVisibility(View.VISIBLE);
        Toast.makeText(this, "job" + jobId, Toast.LENGTH_SHORT).show();
        mAPIservicePerfil.getPerfil(this, perfilId, this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_perfil_detalhado_selecionar){
            mAPIserviceJob.match(this, jobId, perfilId);
        }
    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        Snackbar snackbar = Snackbar.make(mProgressBar, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRetrofitGet(int status, List list) {

    }

    @Override
    public void onRetrofitCreate(int status, Object object) {

    }

    @Override
    public void onRetrofitPut(int status, Object object) {

    }

    @Override
    public void onRetrofitRetrive(int status, Object object) {
        mProgressBar.setVisibility(View.GONE);
        if (status >= 200 & status < 300){
            Perfil perfil = (Perfil) object;
            edtPerfilProfissional.setText(perfil.getPerfiProfissional());
            edtExperiencia.setText(perfil.getExperiencia());
            edtNome.setText(perfil.getNome());
            edtDataNascimento.setText(perfil.getDataNascimento());
            edtTelefone.setText(perfil.getTelefone());
        }

    }

    @Override
    public void onRetrofitDelete(int status) {

    }
}
