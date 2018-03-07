package com.azapps.tjob_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.api.APIserviceLogin;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.ui.ComentarioJobActivity;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

/**
 * Created by rodrigo-souza on 06/03/18.
 */
public class JobAdapterHome extends RecyclerView.Adapter<JobAdapterHome.ViewHolder>{

    private List<JobSimples> jobs;
    private Context mContext;
    private APIserviceJob mAPIserviceJob;
    private ProgressBar mProgressBar;
    private long perfilId;

    public JobAdapterHome(Context context, List<JobSimples> jobs, APIserviceJob apiServiceJob, ProgressBar progressBar) {
        this.mContext = context;
        this.jobs = jobs;
        this.mAPIserviceJob = apiServiceJob;
        this.mProgressBar = progressBar;
        perfilId = new PrenferencesUltil(context).getStoredLong(ConstantsUtil.PERFIL_ID);

    }

    public Context getContext() {
        return mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitulo;
        TextView jobDescricao;
        TextView jobCriador;
        ImageView jobLike;
        ImageView jobDeslike;
        ImageView jobComentario;


        public ViewHolder(View itemView) {
            super(itemView);

            jobTitulo = itemView.findViewById(R.id.layout_titulo_job);
            jobComentario = itemView.findViewById(R.id.layout_comentario_job);
            jobDescricao = itemView.findViewById(R.id.layout_descricao_job);
            jobLike = itemView.findViewById(R.id.layout_like_job);
            jobDeslike = itemView.findViewById(R.id.layout_deslike_job);
            jobCriador = itemView.findViewById(R.id.layout_criador_job);

        }

    }
    @Override
    public JobAdapterHome.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View jobView = inflater.inflate(R.layout.layout_job_lista, parent, false);
        ViewHolder viewHolder = new ViewHolder(jobView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final JobAdapterHome.ViewHolder holder, int position) {

        final JobSimples job = jobs.get(position);
        holder.jobTitulo.setText(job.getTitulo());
        holder.jobDescricao.setText(job.getDescricao());
        holder.jobCriador.setText(job.getNomeCriador());

        for(int i = 0; i < job.getCurtidas().size(); i++)
            if (perfilId == job.getCurtidas().get(i))
                holder.jobLike.setImageResource(R.drawable.ic_action_like_azul);

        holder.jobLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.jobLike.setImageResource(R.drawable.ic_action_like_azul);
                mAPIserviceJob.like(mProgressBar,mContext,job.getId());
            }
        });

        holder.jobDeslike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.jobDeslike.setImageResource(R.drawable.ic_action_deslike_azul);
                holder.jobLike.setImageResource(R.drawable.ic_action_like);
                mAPIserviceJob.deslike(mProgressBar,mContext,job.getId());
            }
        });

        holder.jobComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ComentarioJobActivity.class);
                intent.putExtra(ConstantsUtil.JOB_ID, job.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

}
