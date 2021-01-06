package com.example.e_activity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.e_activity.Fragment.ListPegawaiFragment;
import com.example.e_activity.Fragment.ProfilFragment;
import com.example.e_activity.Fragment.StatusFragment;
import com.example.e_activity.Fragment.TugasFragment;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    SharedPreferences mPrefs;
    ModelUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mPrefs = getSharedPreferences("eactivity", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        user = gson.fromJson(json, ModelUser.class);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        getDataUser();
    }

    private void getDataUser() {
//        if(String.valueOf(user.getRole()).equalsIgnoreCase("1")){
//            bottomNavigationView.inflateMenu(R.menu.menu_atasan);
//        }
        if(user.getRole() == 1){
            bottomNavigationView.inflateMenu(R.menu.menu_atasan);
        }
        else if(user.getRole() == 2){
            bottomNavigationView.inflateMenu(R.menu.menu_pegawai);
        }
        bottomNavigationView.setSelectedItemId(R.id.tugas);
    }

    private void switchFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.tugas){
            switchFragment(new TugasFragment());
        }else if(item.getItemId() == R.id.status){
            switchFragment(new StatusFragment());
        }else if(item.getItemId() == R.id.profil){
            switchFragment(new ProfilFragment());
        }if(item.getItemId() == R.id.susunan){
            switchFragment(new ListPegawaiFragment());
        }
        return true;
    }
}