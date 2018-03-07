package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.azapps.tjob_app.Interfaces.Retrofitable;
import com.azapps.tjob_app.R;
import com.azapps.tjob_app.api.APIserviceLogin;
import com.azapps.tjob_app.models.Usuario;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Retrofitable{

    private EditText edtUsuario;
    private EditText edtSenha;
    private TextView txtCadastro;
    private Button btnLogin;
    private Usuario usuario;
    private APIserviceLogin mAPIervice;
    private PrenferencesUltil mPrenferencesUltil;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        setupViews();
    }

    public void setupViews(){
        btnLogin.setOnClickListener(this);
        txtCadastro.setOnClickListener(this);
        mAPIervice = new APIserviceLogin(this);
        mPrenferencesUltil = new PrenferencesUltil(this);

    }

    public void bindView(){

        edtUsuario = findViewById(R.id.loginEdtUsername);
        edtSenha = findViewById(R.id.loginEdtPassword);
        txtCadastro = findViewById(R.id.loginCadastro);
        btnLogin = findViewById(R.id.loginBtnLogin);
        mProgressBar = findViewById(R.id.progress_login);
    }

    public Usuario login(){
            this.usuario = new Usuario();
            this.usuario.setUsername(edtUsuario.getText().toString());
            this.usuario.setPassword(edtSenha.getText().toString());
        return usuario;
    }

    public boolean isValid(){
        return  (!(edtUsuario.getText().toString()).isEmpty()
                || !(edtSenha.getText().toString()).isEmpty());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtnLogin:
                if (isValid()){
                    login();
                    mProgressBar.setVisibility(View.VISIBLE);
                    mAPIervice.getToken(this, this.usuario,this);
                }else{
                    Snackbar snackbar = Snackbar.make(btnLogin, R.string.preecha_os_campos, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
            case R.id.loginCadastro:
                startActivityForResult(new Intent(this,CadastroUsuarioActivity.class), ConstantsUtil.REQUEST_CODE_LOGIN);
                break;

        }
    }

    @Override
    public void onRetrofitFailure(Throwable t) {
        mProgressBar.setVisibility(View.GONE);

        Snackbar snackbar = Snackbar.make(btnLogin, "Sem conexão", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void onLoginresponse(int status, String token) {
        if (status >= 200 && status < 300 ){
            mPrenferencesUltil.storeString(ConstantsUtil.TOKEN, token);
            mProgressBar.setVisibility(View.GONE);
            startActivity(new Intent(this, BottomMenuActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Status: "+ status, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Loading() {
        Toast.makeText(this, R.string.dados_incorretos, Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantsUtil.REQUEST_CODE_LOGIN)
            if (resultCode == RESULT_OK){

                String username = data.getStringExtra(ConstantsUtil.USERNAME_EXTRA);
                String password = data.getStringExtra(ConstantsUtil.PASSWORD_EXTRA);
                Toast.makeText(this, "Request cod", Toast.LENGTH_SHORT).show();

                Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
                edtSenha.setText(password);
                edtUsuario.setText(username);
            }
    }

    @Override
    public void onRetrofitResponse(int status, List list) {

    }
}
