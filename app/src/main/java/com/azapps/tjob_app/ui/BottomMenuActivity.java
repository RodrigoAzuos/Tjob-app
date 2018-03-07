package com.azapps.tjob_app.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.azapps.tjob_app.R;
import com.azapps.tjob_app.controle.ControlerPerfilActivity;
import com.azapps.tjob_app.controle.ControlerUserActivity;
import com.azapps.tjob_app.fragments.ChatFragment;
import com.azapps.tjob_app.fragments.HomeFragment;
import com.azapps.tjob_app.fragments.PerfilFragment;
import com.azapps.tjob_app.util.ConstantsUtil;
import com.azapps.tjob_app.util.PrenferencesUltil;

import java.util.zip.Inflater;

public class BottomMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener   {

    PrenferencesUltil mPrenferencesUltil;
    BottomNavigationView mBottomNavigationMenuView;
    FrameLayout mFrameLayout;
    Fragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_menu);
        bindViews();
        setupViews();
    }

    public boolean logado(){
        return this.mPrenferencesUltil.getStoredString(ConstantsUtil.TOKEN) == "-1" ?  false : true;
    }

    public void setupViews(){
        this.mPrenferencesUltil = new  PrenferencesUltil(this);

        if (!logado()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else {
            if (this.mPrenferencesUltil.getStoredLong(ConstantsUtil.USUARIO_ID) == -1){
                Toast.makeText(this, "usuario -1", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ControlerUserActivity.class));
                finish();
            }
            else if (this.mPrenferencesUltil.getStoredLong(ConstantsUtil.PERFIL_ID) == -1){
                startActivity(new Intent(this, ControlerPerfilActivity.class));
                finish();
            }


        }

        mBottomNavigationMenuView.setOnNavigationItemSelectedListener(this);
        mFragment = new HomeFragment();
        inflarFragment(mFragment);

    }

    public void bindViews(){
        mBottomNavigationMenuView = findViewById(R.id.bottom_navigation);
        mFrameLayout = findViewById(R.id.conteiner_fragment);
    }

    public boolean inflarFragment(Fragment fragment){

        if(fragment != null){

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.conteiner_fragment,fragment)
                        .commit();

             return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.bottom_action_jobs:
                mFragment = new HomeFragment();
                break;
            case R.id.bottom_action_chat:
                mFragment = new ChatFragment();
                break;
            case R.id.botttom_action_perfil:
                mFragment = new PerfilFragment();
                break;
        }

        return inflarFragment(mFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sair){
            mPrenferencesUltil.limparPreferencias();
            startActivity(new Intent(this, BottomMenuActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
