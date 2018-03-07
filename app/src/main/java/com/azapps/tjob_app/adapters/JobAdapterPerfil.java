package com.azapps.tjob_app.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.RetrofitableMetodos;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.ui.DetalheJobActivity;
import com.azapps.tjob_app.util.ConstantsUtil;

import java.util.List;

/**
 * Created by rodrigo-souza on 06/03/18.
 */

public class JobAdapterPerfil extends RecyclerView.Adapter<JobAdapterPerfil.ViewHolder> implements RetrofitableMetodos {

    private List<JobSimples> jobs;
    private Context mContext;
    private APIserviceJob mAPIserviceJob;
    private ProgressBar mProgressBar;
    private boolean verificador;
    private  JobSimples jobSimplesTemp;
    private  int posTemp;



    public JobAdapterPerfil(List<JobSimples> jobs, Context mContext, APIserviceJob mAPIserviceJob, ProgressBar mProgressBar, boolean verificador) {
        this.jobs = jobs;
        this.mContext = mContext;
        this.mAPIserviceJob = mAPIserviceJob;
        this.mProgressBar = mProgressBar;
        this.verificador = verificador;
    }

    public Context getContext() {
        return mContext;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitulo;
        TextView jobDescricao;
        TextView jobLike;
        TextView jobStatus;



        public ViewHolder(View itemView) {
            super(itemView);

            jobTitulo = itemView.findViewById(R.id.layout_titulo_job_perfil);
            jobDescricao = itemView.findViewById(R.id.layout_descricao_job_perfil);
            jobLike = itemView.findViewById(R.id.layout_curtidas_job_perfil);
            jobStatus = itemView.findViewById(R.id.layout_status_job_perfil);

        }
    }

    @Override
    public JobAdapterPerfil.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View jobView = inflater.inflate(R.layout.layout_job_lista_perfil, parent, false);
        ViewHolder viewHolder = new ViewHolder(jobView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final JobSimples job = jobs.get(position);

        holder.jobTitulo.setText(job.getTitulo());
        holder.jobDescricao.setText(job.getDescricao());
        holder.jobLike.setText(" "+job.getCurtidas().size());
        holder.jobStatus.setText(job.getStatus());

        if (verificador){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetalheJobActivity.class);
                    intent.putExtra(ConstantsUtil.JOB_ID, job.getId());
                    mContext.startActivity(intent);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mContext, holder.itemView);
                    popupMenu.inflate(R.menu.context_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.context_editar:
                                    editar(job, position).show();
                                    break;
                                case R.id.context_remover:
                                    alertDialog(job.getId(),job, position ).show();
                                    break;

                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
        }

    }

    private Dialog alertDialog(final long id, final JobSimples jobSimples, final int pos) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setMessage("Tem certeza que deseja excluir?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jobSimplesTemp = jobSimples;
                        posTemp = pos;
                        mProgressBar.setVisibility(View.VISIBLE);
                        mAPIserviceJob.deleteJob(JobAdapterPerfil.this, id);
//
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return alertDialog.create();
    }

    public void atualizarLista(){
        jobs.remove(jobSimplesTemp);
        notifyItemRemoved(posTemp);
        Toast.makeText(mContext, "Job removido", Toast.LENGTH_SHORT).show();
    }

    public void EditaLista(){
        jobs.set(posTemp, jobSimplesTemp);
        notifyItemChanged(posTemp);
        Toast.makeText(mContext, "Anuncio editado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private Dialog editar(final JobSimples jobSimples, final int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View dialogView = inflater.inflate(R.layout.activity_post_job, null);
        builder.setView(dialogView);

        final EditText edtDescricao = dialogView.findViewById(R.id.edt_cadastro_job_descricao);
        final EditText edtTitulo = dialogView.findViewById(R.id.edt_cadastro_job_titulo);
        final  CheckBox mCheckBox = dialogView.findViewById(R.id.check_cadastro_job);

        //edtCategoria.setText(produto.);
        edtDescricao.setText(jobSimples.getDescricao());
        edtTitulo.setText(jobSimples.getTitulo());
        if(jobSimples.isPublico())
            mCheckBox.setChecked(true);

        builder.setTitle("Editar")
                .setPositiveButton(R.string.confirmar_edicao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            jobSimples.setDescricao(edtDescricao.getText().toString());
                            jobSimples.setTitulo(edtTitulo.getText().toString());
                            jobSimples.setPublico(false);
                            if (mCheckBox.isChecked()){
                                jobSimples.setPublico(true);
                            }

                            jobSimplesTemp = jobSimples;
                            posTemp = pos;
                            mProgressBar.setVisibility(View.VISIBLE);
                            mAPIserviceJob.putJob(JobAdapterPerfil.this, jobSimples, jobSimples.getId());
                    }
                })
                .setNegativeButton(R.string.cancelar_edicao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public int getItemCount() {
        return jobs.size();
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
        mProgressBar.setVisibility(View.VISIBLE);
        if (status >= 200 && status < 300){
            EditaLista();
        }
    }

    @Override
    public void onRetrofitRetrive(int status, Object object) {

    }

    @Override
    public void onRetrofitDelete(int status) {
        mProgressBar.setVisibility(View.VISIBLE);
        Toast.makeText(mContext, "status:"+ status, Toast.LENGTH_SHORT).show();
        if (status >= 200 && status < 300){
            atualizarLista();
        }
    }

}
