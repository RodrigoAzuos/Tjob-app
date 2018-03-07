package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.azapps.tjob_app.Interfaces.RetrofitableMetodos;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.adapters.JobPerfisAdapter;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.models.JobDetalhado;
import com.azapps.tjob_app.models.Perfil;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

public class DetalheJobActivity extends AppCompatActivity implements RetrofitableMetodos{

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private APIserviceJob mApIserviceJob;
    private JobPerfisAdapter mJobPerfisAdapter;
    PrenferencesUltil mPrenferencesUltil;
    private long jobId;
    private long perfilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_job);
        bindView();
        setupView();
    }

    public void bindView(){
        mRecyclerView = findViewById(R.id.rv_lista_job_detalhe_curtidores);
        mProgressBar = findViewById(R.id.progress_detalhe_job_curtidores);
    }

    public void setupView(){
        mApIserviceJob = new APIserviceJob(this);
        mPrenferencesUltil = new PrenferencesUltil(this);

        perfilId = mPrenferencesUltil.getStoredLong(ConstantsUtil.PERFIL_ID);
        Intent intent = getIntent();
        jobId = intent.getLongExtra(ConstantsUtil.JOB_ID, -1);

        if (jobId != -1){
            mApIserviceJob.getJobDetalhe(this, jobId);
            mProgressBar.setVisibility(View.VISIBLE);
        }

    }

    public void atualizaLista(List<Perfil> perfis){

        mJobPerfisAdapter = new JobPerfisAdapter(perfis, this, mApIserviceJob, jobId);
        mRecyclerView.setAdapter(mJobPerfisAdapter);
        mRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        linearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(linearLayoutManager);
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
        mProgressBar.setVisibility(View.INVISIBLE);
        if (status >= 200 && status < 300){
            atualizaLista(((JobDetalhado) object).getCurtidas());

        }
    }

    @Override
    public void onRetrofitDelete(int status) {

    }
}
