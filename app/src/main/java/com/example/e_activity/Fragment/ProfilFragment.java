package com.example.e_activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.e_activity.Activity.Login;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.R;
import com.example.e_activity.Activity.UbahPassword;
import com.google.gson.Gson;

public class ProfilFragment extends Fragment {
    TextView nama, email, jabatan, divisi, ubahPassword;
    SharedPreferences mPrefs;
    ModelUser user;
    Button btnSignOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        nama = view.findViewById(R.id.nama);
        email = view.findViewById(R.id.email);
        divisi = view.findViewById(R.id.divisi);
        jabatan = view.findViewById(R.id.jabatan);
        ubahPassword = view.findViewById(R.id.ubah_password);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        init();

        ubahPassword.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), UbahPassword.class));
            getActivity();
        });

        btnSignOut.setOnClickListener(v -> {
            mPrefs.edit().remove("userKey").apply();
            startActivity(new Intent(getActivity(), Login.class));
            getActivity().finish();
        });
        return view;
    }

    private void init() {
        mPrefs = getContext().getSharedPreferences("eactivity", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        user = gson.fromJson(json, ModelUser.class);
        nama.setText(user.getNama());
        email.setText(user.getEmail());
        divisi.setText(user.getDivisi());
        if(user.getRole() == 1){
            jabatan.setText(R.string.kepaladivisi);
        }else if(user.getRole() == 2){
            jabatan.setText(R.string.pegawaidivisi);
        }
    }
}