package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIserviceLogin;
import com.azapps.tjob_app.models.Usuario;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class CadastroUsuarioActivity extends AppCompatActivity  implements View.OnClickListener, Retrofitable{

    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtPrimeiroNome;
    private EditText edtSegundoNome;
    private EditText edtEmail;
    private ImageView imgEyes;
    private APIserviceLogin mAPIserviceLogin;
    private Usuario usuario;
    private ProgressBar mProgressBar;
    private int passwordVerify = ConstantsUtil.TEXT_PASSWORD_ACTIVE;
    private PrenferencesUltil mPrenferencesUltil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        bindView();
        setupViews();

    }

    public void setupViews(){
        imgEyes.setOnClickListener(this);
        mAPIserviceLogin = new APIserviceLogin(this);
        mPrenferencesUltil = new PrenferencesUltil(this);
    }

    public void bindView(){

        edtUsuario = findViewById(R.id.edt_cadastro_username);
        edtSenha = findViewById(R.id.edt_cadastro_senha);
        edtPrimeiroNome = findViewById(R.id.edt_cadastro_primeiro_nome);
        edtSegundoNome = findViewById(R.id.edt_cadastro_segundo_nome);
        edtEmail = findViewById(R.id.edt_cadastro_email);
        imgEyes = findViewById(R.id.img_cadastro_eyes);
        mProgressBar = findViewById(R.id.progress_cadastro);
    }

    public boolean isValid(){
        return  (!(edtUsuario.getText().toString()).isEmpty()
                || !(edtSenha.getText().toString()).isEmpty()
                || !(edtPrimeiroNome.getText().toString()).isEmpty()
                || !(edtSegundoNome.getText().toString()).isEmpty()
                || !(edtEmail.getText().toString()).isEmpty()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu_cadastro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salvar_cadastro_usuario:
                if(isValid()){
                    cadastrarUsuario();
                    mProgressBar.setVisibility(View.VISIBLE);
                    mAPIserviceLogin.postUser(this,this.usuario,this);
                }else{
                    Snackbar snackbar = Snackbar.make(edtSenha, R.string.preecha_os_campos, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
            case  R.id.cancelar_cadastro:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_cadastro_eyes){
            if (passwordVerify == ConstantsUtil.TEXT_PASSWORD_ACTIVE)
            {
                edtSenha.setInputType(InputType.TYPE_CLASS_TEXT);
                passwordVerify = ConstantsUtil.TEXT_PASSWORD_DEACTIVE;
            }
            else{

                edtSenha.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordVerify = ConstantsUtil.TEXT_PASSWORD_ACTIVE;
            }

        }
    }

    public void cadastrarUsuario(){
        usuario = new Usuario();
        usuario.setUsername(edtUsuario.getText().toString());
        usuario.setPassword(edtSenha.getText().toString());
        usuario.setFirst_name(edtPrimeiroNome.getText().toString());
        usuario.setLast_name(edtSegundoNome.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());

    }

    @Override
    public void onRetrofitResponse(int status, List list) {
        Usuario usuarioResponse = new Usuario();
        if (status >= 200 && status < 300){
            mProgressBar.setVisibility(View.GONE);
            usuarioResponse = ((Usuario) list.get(0));
            Toast.makeText(this, R.string.usuario_criado, Toast.LENGTH_SHORT).show();
            mPrenferencesUltil.storeLong(ConstantsUtil.USUARIO_ID, usuarioResponse.getId());
            resultado(usuarioResponse.getUsername(), usuarioResponse.getPassword());
        }
    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        Snackbar snackbar = Snackbar.make(edtSenha, R.string.sem_conexao, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void Loading() {
        Toast.makeText(this, R.string.dados_incorretos_cadastro_user, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }

    public void resultado(String username, String password){
        Intent intent  = new Intent();
        intent.putExtra(ConstantsUtil.USERNAME_EXTRA, username);
        intent.putExtra(ConstantsUtil.PASSWORD_EXTRA, password);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLoginresponse(int status, String token) {

    }
}
