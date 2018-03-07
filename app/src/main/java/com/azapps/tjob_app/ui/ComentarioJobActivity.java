package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.adapters.ComentarioAdapter;
import com.azapps.tjob_app.adapters.JobAdapterHome;
import com.azapps.tjob_app.api.APIservice;
import com.azapps.tjob_app.api.APIserviceComentario;
import com.azapps.tjob_app.models.Comentario;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.util.ConstantsUtil;

import java.util.ArrayList;
import java.util.List;

public class ComentarioJobActivity extends AppCompatActivity  implements View.OnClickListener, Retrofitable
{

   private RecyclerView mRecyclerView;
   private EditText etdTextoMensagem;
   private ImageView imgEnviarMensagem;
   private ComentarioAdapter mComentarioAdapter;
   private ProgressBar mProgressBar;
   private APIserviceComentario mApIserviceComentario;
   private long jodId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario_job);
        bindViews();
        setupViews();


    }

    public void bindViews(){
        mRecyclerView = findViewById(R.id.rv_lista_comentario);
        etdTextoMensagem = findViewById(R.id.edt_comentario_activity);
        imgEnviarMensagem = findViewById(R.id.img_postar_comentario);
        mProgressBar = findViewById(R.id.progress_comentario_activity);

    }

    public void setupViews(){
        this.imgEnviarMensagem.setOnClickListener(this);
        mProgressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        jodId = intent.getLongExtra(ConstantsUtil.JOB_ID, -1 );

        mApIserviceComentario = new APIserviceComentario(this);

        if (jodId != -1)
            mApIserviceComentario.getComentarios(this, jodId);
    }

    public void atualizaLista(List<Comentario> comentarios){

        mComentarioAdapter = new ComentarioAdapter(this, comentarios);
        mRecyclerView.setAdapter(mComentarioAdapter);
        mRecyclerView.setHasFixedSize(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        linearLayoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void postComentario(){
        Comentario comentario = new Comentario(etdTextoMensagem.getText().toString(), jodId);
        if(!(etdTextoMensagem.getText().toString().equals(" ")))
            mApIserviceComentario.posComentario(this,jodId,comentario,this);
        etdTextoMensagem.setText(" ");

    }

    @Override
    public void onRetrofitResponse(int status, List list) {
        if( status >= 200 && status < 300){
            if(list.size()>0)
                atualizaLista(list);
            else{
                Toast.makeText(this, R.string.job_sem_comentario, Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);
        }else{
            Toast.makeText(this, "code: "+ status, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, ComentarioJobActivity.class);
        intent.putExtra(ConstantsUtil.JOB_ID, jodId);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_postar_comentario){
            postComentario();
        }
    }
}
