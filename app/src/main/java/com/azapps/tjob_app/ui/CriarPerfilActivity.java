package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIservicePerfil;
import com.azapps.tjob_app.models.Endereco;
import com.azapps.tjob_app.models.Perfil;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

public class CriarPerfilActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, Retrofitable {

    EditText edtTelefone;
    EditText edtDatadeNascimento;
    EditText edtPerfilProfissional;
    EditText edtExperienciaProfissional;
    EditText edtGitHub;
    EditText edtLogradoudo;
    EditText edtCep;
    EditText edtCidade;
    EditText edtEstado;
    Spinner spSexo;
    FloatingActionButton fabCheckCadastro;
    ProgressBar mProgressBar;

    TextInputLayout iptTelefone;
    TextInputLayout iptDatadeNascimento;
    TextInputLayout iptcidade;
    TextInputLayout iptLogradouro;
    TextInputLayout iptestado;
    TextInputLayout iptPerfilProfissional;
    TextInputLayout iptSexo;

    private Perfil perfil;
    private Endereco endereco;
    long usuarioId = 0;
    private PrenferencesUltil mPrenferencesUltil;
    APIservicePerfil mApIservicePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_perfil);

        bindViews();
        setupViews();

    }

    public void bindViews(){
        edtTelefone = findViewById(R.id.edt_cadastro_perfil_telefone);
        edtDatadeNascimento = findViewById(R.id.edt_cadastro_perfil_dt_nasc);
        edtPerfilProfissional = findViewById(R.id.edt_cadastro_perfil_profissional);
        edtExperienciaProfissional = findViewById(R.id.edt_cadastro_perfil_experiencia);
        edtGitHub = findViewById(R.id.edt_cadastro_perfil_github);
        edtLogradoudo = findViewById(R.id.edt_cadastro_perfil_logradouro);
        edtCidade = findViewById(R.id.edt_cadastro_perfil_cidade);
        edtEstado = findViewById(R.id.edt_cadastro_perfil_estado);
        edtCep = findViewById(R.id.edt_cadastro_perfil_cep);
        spSexo = findViewById(R.id.sp_cadastro_perfil_sexo);
        fabCheckCadastro = findViewById(R.id.fab_check_cadastro);
        mProgressBar = findViewById(R.id.progress_criar_perfil);

        iptcidade = findViewById(R.id.text_input_cidade);
        iptLogradouro = findViewById(R.id.text_input_logradouro);
        iptDatadeNascimento = findViewById(R.id.text_input_dt_nasc);
        iptestado = findViewById(R.id.text_input_estado);
        iptTelefone = findViewById(R.id.text_input_telefone);
        iptPerfilProfissional = findViewById(R.id.text_input_perfil_profissional);
        iptSexo = findViewById(R.id.text_input_sexo);

    }

    public void setupViews(){
        fabCheckCadastro.setOnClickListener(this);
        edtCidade.setOnClickListener(this);
        spSexo.setOnItemSelectedListener(this);
        mPrenferencesUltil = new PrenferencesUltil(this);
        usuarioId = mPrenferencesUltil.getStoredLong(ConstantsUtil.USUARIO_ID);
        Toast.makeText(this, "perfil id: " + usuarioId, Toast.LENGTH_SHORT).show();

        perfil = new Perfil();
        endereco = new Endereco();
        mApIservicePerfil = new APIservicePerfil(this);

        String [] sexo;
        sexo =  getResources().getStringArray(R.array.item_spinner);


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo.setAdapter(dataAdapter);

    }

    public boolean isValid(){
        if((edtCidade.getText().toString()).isEmpty())
            iptcidade.setError(getString(R.string.campo_obrigatorio));
        if ((edtTelefone.getText().toString()).isEmpty())
            iptTelefone.setError(getString(R.string.campo_obrigatorio));
        if((edtDatadeNascimento.getText().toString()).isEmpty())
            iptDatadeNascimento.setError(getString(R.string.campo_obrigatorio));
        if((edtPerfilProfissional.getText().toString()).isEmpty())
            iptPerfilProfissional.setError(getString(R.string.campo_obrigatorio));
        if((edtLogradoudo.getText().toString()).isEmpty())
            iptLogradouro.setError(getString(R.string.campo_obrigatorio));
        if((edtEstado.getText().toString()).isEmpty())
            iptestado.setError(getString(R.string.campo_obrigatorio));
        if(perfil.getSexo().equals(" "))
            iptSexo.setError(getString(R.string.campo_obrigatorio));



        return  (!(edtCidade.getText().toString()).isEmpty()
                || !(edtTelefone.getText().toString()).isEmpty()
                || !(edtDatadeNascimento.getText().toString()).isEmpty()
                || !(edtPerfilProfissional.getText().toString()).isEmpty()
                || !(edtLogradoudo.getText().toString()).isEmpty()
                || !(edtEstado.getText().toString()).isEmpty()
        );
    }

    public void criarPerfil(){
        if(!edtCep.getText().toString().isEmpty())
            endereco.setCep(Float.parseFloat(edtCep.getText().toString()));

        endereco.setCidade(edtCidade.getText().toString());
        endereco.setEstado(edtEstado.getText().toString());
        endereco.setLogradouro(edtLogradoudo.getText().toString());

        perfil.setEndereco(endereco);
        perfil.setUsuario(usuarioId);
        perfil.setDataNascimento(edtDatadeNascimento.getText().toString());

        perfil.setExperiencia(edtExperienciaProfissional.getText().toString());
        perfil.setGitHub(edtGitHub.getText().toString());
        perfil.setTelefone(edtTelefone.getText().toString());
        perfil.setPerfiProfissional(edtPerfilProfissional.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_check_cadastro:
                if (isValid()) {
                    criarPerfil();
                    mProgressBar.setVisibility(View.VISIBLE);
                    mApIservicePerfil.postPerfil(this, perfil);
                }
                break;
            case R.id.edt_cadastro_perfil_cidade:
                fabCheckCadastro.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String sexo = parent.getItemAtPosition(position).toString();
        if (!sexo.equals("Sexo")){
            if (sexo.equals("Masculino"))
                perfil.setSexo("M");
            else
                perfil.setSexo("F");
        }else{
            perfil.setSexo(" ");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRetrofitResponse(int status, List list) {
        Perfil  perfilResponse = new Perfil();

        if(status >= 200 && status < 300){
            mProgressBar.setVisibility(View.GONE);
            perfilResponse = ((Perfil) list.get(0));
            Toast.makeText(this, R.string.perfil_criado_sucesso, Toast.LENGTH_SHORT).show();
            mPrenferencesUltil.storeLong(ConstantsUtil.PERFIL_ID, perfilResponse.getId());
            startActivity(new Intent(this, BottomMenuActivity.class));
        }else {
            Toast.makeText(this, "Status: " + status, Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        Snackbar snackbar = Snackbar.make(edtEstado, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoginresponse(int status, String token) {

    }

    @Override
    public void Loading() {
        Toast.makeText(this, R.string.dados_incorretos_cadastro_user, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }
}
