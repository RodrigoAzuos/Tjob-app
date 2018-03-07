package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.RetrofitableMetodos;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIserviceJob;
import com.azapps.tjob_app.models.JobSimples;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;
import java.util.zip.Inflater;

public class PostJobActivity extends AppCompatActivity implements RetrofitableMetodos {

    private EditText edtTitulo;
    private EditText edtDescricao;
    private CheckBox mCheckBox;
    private ProgressBar mProgressBar;
    private TextInputLayout tilTitulo;
    private TextInputLayout tilDescricao;
    private APIserviceJob mAPIserviceJob;
    private JobSimples mJobSimples;
    private PrenferencesUltil mPrenferencesUltil;
    private long perfilId;
    private long jobId;
    private String metodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);
        bindView();
        setupViews();
    }

    public void bindView(){

        edtDescricao = findViewById(R.id.edt_cadastro_job_descricao);
        edtTitulo = findViewById(R.id.edt_cadastro_job_titulo);
        mCheckBox = findViewById(R.id.check_cadastro_job);
        mProgressBar = findViewById(R.id.progress_cadastro_job);
        tilDescricao = findViewById(R.id.text_input_cadastro_job_descricao);
        tilTitulo = findViewById(R.id.text_input_cadastro_job_titulo);
    }

    public void setupViews(){
        mAPIserviceJob = new APIserviceJob(this);
        mJobSimples = new JobSimples();
        mPrenferencesUltil = new PrenferencesUltil(this);
        perfilId = mPrenferencesUltil.getStoredLong(ConstantsUtil.PERFIL_ID);

        Intent intent = getIntent();
        jobId = intent.getLongExtra(ConstantsUtil.JOB_ID, -1);
        if (jobId != -1 ){
            //TODO chama pra editar...
        }
    }

    public boolean isValid(){
        return  (!(edtTitulo.getText().toString()).isEmpty()
                || !(edtDescricao.getText().toString()).isEmpty()

        );
    }

    public boolean criarJob(){
        if (isValid()){
            mJobSimples.setTitulo(edtTitulo.getText().toString());
            mJobSimples.setDescricao(edtDescricao.getText().toString());
            mJobSimples.setCriador(perfilId);
            mJobSimples.setPublico(true);
            if (mCheckBox.isChecked()){
                mJobSimples.setPublico(true);
            }
            return true;
        }
        tilTitulo.setError(getString(R.string.preecha_os_campos));
        tilDescricao.setError(getString(R.string.preecha_os_campos));
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvar_cadastro_usuario:
                if (jobId == -1)
                    if (criarJob())
                        mAPIserviceJob.postJob(this,this,mJobSimples);
                        mProgressBar.setVisibility(View.VISIBLE);
                break;
            case  R.id.cancelar_cadastro:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        mProgressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "code: " + status, Toast.LENGTH_SHORT).show();
        if (status >= 200 && status < 300){
            mProgressBar.setVisibility(View.INVISIBLE);
            finish();
        }else{
            Toast.makeText(this, R.string.erro_post, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRetrofitPut(int status, Object object) {

    }

    @Override
    public void onRetrofitRetrive(int status, Object object) {

    }

    @Override
    public void onRetrofitDelete(int status) {

    }

}
