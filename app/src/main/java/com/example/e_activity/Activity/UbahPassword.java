package com.example.e_activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.Models.ResponseModel;
import com.example.e_activity.R;
import com.google.gson.Gson;

import retrofit2.Call;

public class UbahPassword extends AppCompatActivity {
    EditText PassBaru, KonfPassBaru;
    Button btnSimpanPass;
    SharedPreferences mPrefs;
    ModelUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        init();




        btnSimpanPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubah();
            }
        });
    }

    private void ubah() {
        String getPassBaru = PassBaru.getText().toString().trim();
        String getKonfPassBaru = KonfPassBaru.getText().toString().trim();

        if ( getPassBaru.length() < 1 || getKonfPassBaru.length() < 1 ) {
            Toast.makeText(UbahPassword.this, "Isi form dengan benar", Toast.LENGTH_SHORT).show();
        }
        else if(!getPassBaru.equals(getKonfPassBaru)) {
            Toast.makeText(UbahPassword.this, "Password Yang Anda Masukkan Tidak Cocok!", Toast.LENGTH_SHORT).show();
        }
        else {
//            APIRequestData service = APIClient.getClient().create(APIRequestData.class); //menghubungkan class interface ke retrofit
//            Call<ResponseModel> tampilData = service.updatePassword(
//
//            );

        }


    }
    private void init(){
        PassBaru = findViewById(R.id.et_passbaru);
        KonfPassBaru = findViewById(R.id.et_konfpassbaru);
        btnSimpanPass = findViewById(R.id.btnSimpanPass);

        mPrefs = getSharedPreferences("eactivity", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        user = gson.fromJson(json, ModelUser.class);
    }
}