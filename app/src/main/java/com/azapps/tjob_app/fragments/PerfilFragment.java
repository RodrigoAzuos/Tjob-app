package com.azapps.tjob_app.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.adapters.JobAdapterPerfil;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.ui.MeusMatchesActivity;
import com.azapps.tjob_app.ui.MinhasCurtidasActivity;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

/**
 * Created by rodrigo-souza on 05/03/18.
 */

public class PerfilFragment extends Fragment implements View.OnClickListener, Retrofitable{
    private View view;
    private ProgressBar mProgressBar;
    private TextView txtMach;
    private TextView txtJobscurtidos;
    private TextView txtEditarInfo;
    private RecyclerView mRecyclerView;
    private APIserviceJob mAPIserviceJob;
    private JobAdapterPerfil mJobAdapterPerfil;
    PrenferencesUltil mPrenferencesUltil;
    private long perfilId;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, null);
        bindViews();
        setupViews();

        return view;


    }

    public void setupViews(){
        mProgressBar.setVisibility(View.VISIBLE);
        mAPIserviceJob =  new APIserviceJob(view.getContext());
        txtJobscurtidos.setOnClickListener(this);
        txtEditarInfo.setOnClickListener(this);
        txtMach.setOnClickListener(this);
        mPrenferencesUltil = new PrenferencesUltil(view.getContext());
        perfilId = mPrenferencesUltil.getStoredLong(ConstantsUtil.PERFIL_ID);

        if (perfilId != -1){
            mAPIserviceJob.getMeusJobs(view.getContext(),this, perfilId);
        }


    }

    public void bindViews(){
        mRecyclerView = view.findViewById(R.id.rv_lista_job_perfil);
        mProgressBar = view.findViewById(R.id.progress_fragment_perfil);
        txtMach = view.findViewById(R.id.edt_fragment_perfil_macths);
        txtEditarInfo = view.findViewById(R.id.edt_fragment_perfil_editar);
        txtJobscurtidos = view.findViewById(R.id.edt_fragment_perfil_curtidas);

    }

    public void atualizaLista(List<JobSimples> jobs){

        mJobAdapterPerfil = new JobAdapterPerfil(jobs, view.getContext(), mAPIserviceJob, mProgressBar, ConstantsUtil.VERIFICADOR_TRUE);

        mRecyclerView.setAdapter(mJobAdapterPerfil);
        mRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false);

        linearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edt_fragment_perfil_macths:
                startActivity(new Intent(view.getContext(), MeusMatchesActivity.class));
            case R.id.edt_fragment_perfil_editar:
                break;
            case R.id.edt_fragment_perfil_curtidas:
               startActivity(new Intent(view.getContext(), MinhasCurtidasActivity.class));
        }
    }

    @Override
    public void onRetrofitResponse(int status, List list) {
        mProgressBar.setVisibility(View.GONE);
        if(status >= 200 && status < 300){
            atualizaLista(list);
        }
    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        Snackbar snackbar = Snackbar.make(txtMach, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoginresponse(int status, String token) {

    }

    @Override
    public void Loading() {
        Toast.makeText(view.getContext(), R.string.dados_incorretos_cadastro_user, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }
}
