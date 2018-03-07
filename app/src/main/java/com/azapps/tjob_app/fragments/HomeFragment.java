package com.azapps.tjob_app.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.adapters.JobAdapterHome;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.ui.PostJobActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo-souza on 05/03/18.
 */

public class HomeFragment extends Fragment implements Retrofitable, View.OnClickListener {
    private RecyclerView rcJobs;
    private FloatingActionButton fabJobHome;
    private JobAdapterHome mJobAdapterHome;
    private List<JobSimples> jobs;
    private ProgressBar mProgressBar;
    private APIserviceJob mAPIserviceJob;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        bindViews();
        setupViews();
        mAPIserviceJob.getJobs(this);
        return view;
    }

    public void setupViews(){
        jobs = new ArrayList<JobSimples>();
        mProgressBar.setVisibility(View.VISIBLE);
        mAPIserviceJob =  new APIserviceJob(view.getContext());
        fabJobHome.setOnClickListener(this);
    }

    public void bindViews(){
        rcJobs = view.findViewById(R.id.rv_lista_job_home);
        fabJobHome = view.findViewById(R.id.fab_plus_fragment_home);
        mProgressBar = view.findViewById(R.id.progress_fragment_home);
    }

    public void atualizaLista(List<JobSimples> jobs){

        mJobAdapterHome = new JobAdapterHome(view.getContext(),jobs,  mAPIserviceJob,mProgressBar);

        rcJobs.setAdapter(mJobAdapterHome);
        rcJobs.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL, false);

        linearLayoutManager.scrollToPosition(0);
        rcJobs.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onRetrofitResponse(int status, List list) {
        if(status >= 200 && status < 300){
            mProgressBar.setVisibility(View.GONE);
            atualizaLista(list);

        }
    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        Snackbar snackbar = Snackbar.make(mProgressBar, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_plus_fragment_home)
            startActivity(new Intent(view.getContext(), PostJobActivity.class));
    }
}
