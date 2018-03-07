package com.azapps.tjob_app.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.adapters.JobAdapterPerfil;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

public class MinhasCurtidasActivity extends AppCompatActivity implements Retrofitable {

    private ProgressBar mProgressBar;
    private APIserviceJob mAPIserviceJob;
    private RecyclerView mRecyclerView;
    PrenferencesUltil prenferencesUltil;
    private long perfilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_curtidas);
        bindView();
        setupView();

        Toast.makeText(this, "oi", Toast.LENGTH_SHORT).show();
    }

    public void bindView(){
        mProgressBar = findViewById(R.id.progress_minhas_curtidas);
        mRecyclerView = findViewById(R.id.rv_lista_job_minhas_curtidas);
    }

    public void setupView(){
        mProgressBar.setVisibility(View.VISIBLE);
        mAPIserviceJob = new APIserviceJob(this);
        prenferencesUltil = new PrenferencesUltil(this);
        perfilId = prenferencesUltil.getStoredLong(ConstantsUtil.PERFIL_ID);
        mAPIserviceJob.minhasCurtidas(this,perfilId);
    }

    public void atualizaLista(List<JobSimples> jobs){
        JobAdapterPerfil meusMatchesMinhasCurtidas = new JobAdapterPerfil(jobs, this, mAPIserviceJob, mProgressBar, ConstantsUtil.VERIFICADOR_FALSE);

        mRecyclerView.setAdapter(meusMatchesMinhasCurtidas);
        mRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        linearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onRetrofitResponse(int status, List list) {
        Toast.makeText(this, ""+ status, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
        if(status >= 200 && status < 300){
            atualizaLista(list);
        }

    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        Snackbar snackbar = Snackbar.make(mRecyclerView, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginresponse(int status, String token) {

    }

    @Override
    public void Loading() {

    }
}
