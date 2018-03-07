package com.azapps.tjob_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.models.Perfil;
import com.azapps.tjob_app.ui.PerfilDetalhadoActivity;
import com.azapps.tjob_app.util.ConstantsUtil;

import java.util.List;

/**
 * Created by rodrigo-souza on 07/03/18.
 */

public class JobPerfisAdapter extends RecyclerView.Adapter<JobPerfisAdapter.ViewHolder> {

    private List<Perfil> perfis;
    private Context mContext;
    private APIserviceJob mAPIserviceJob;
    private long jobId;

    public JobPerfisAdapter(List<Perfil> perfis, Context mContext, APIserviceJob mAPIserviceJob, long jobId) {
        this.perfis = perfis;
        this.mContext = mContext;
        this.mAPIserviceJob = mAPIserviceJob;
        this.jobId = jobId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPerfilNome;

        public ViewHolder(View itemView) {
            super(itemView);

            txtPerfilNome = itemView.findViewById(R.id.layout_perfil_curtidores_nome);
        }
    }

    @Override
    public JobPerfisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View jobView = inflater.inflate(R.layout.layout_perfil_cutidores_detalhe_job, parent, false);
        JobPerfisAdapter.ViewHolder viewHolder = new JobPerfisAdapter.ViewHolder(jobView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (perfis.size() > 0){

            final Perfil perfil = perfis.get(position);
            holder.txtPerfilNome.setText(perfil.getNome());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PerfilDetalhadoActivity.class);
                    intent.putExtra(ConstantsUtil.JOB_ID, jobId);
                    intent.putExtra(ConstantsUtil.PERFIL_ID, perfil.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return perfis.size();
    }
}
