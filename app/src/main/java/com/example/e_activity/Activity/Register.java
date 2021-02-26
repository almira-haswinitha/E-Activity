package com.example.e_activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Models.ResponseUser;
import com.example.e_activity.R;

@SuppressWarnings("ALL")
public class Register extends AppCompatActivity {
    EditText nama,email,password;
    Spinner spinnerDivisi, spinnerJabatan;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        btnRegister.setOnClickListener(v -> {
            if(nama.getText().toString().equalsIgnoreCase("") || email.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(this, "Mohon isi data dengan Lengkap !", Toast.LENGTH_SHORT).show();
            }else{
                APIRequestData apiRequestData = APIClient.getClient().create(APIRequestData.class);
                Call<ResponseUser> createUser = apiRequestData.createUser(nama.getText().toString(), email.getText().toString(), password.getText().toString(), spinnerDivisi.getSelectedItem().toString(), getRoleJabatan(spinnerJabatan.getSelectedItem().toString()));
                createUser.enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if(response.body().getKode() == 201){
                            Toast.makeText(Register.this, response.body().getPesan(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Register.this, Login.class));
                            finish();
                        }else if(response.body().getKode() == 409){
                            Toast.makeText(Register.this, response.body().getPesan(), Toast.LENGTH_LONG).show();
                            Log.d("pesan: ", String.valueOf(response.body().getKode()));
                        }else if(response.body().getKode() == 410){
                            Toast.makeText(Register.this, response.body().getPesan()+"\n"+spinnerDivisi.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            Log.d("pesan: ", String.valueOf(response.body().getKode()));
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    private int getRoleJabatan(String jabatan) {
        int role = 0;

        if(jabatan.equalsIgnoreCase("Kepala Divisi")){
            role = 1;
        }else if(jabatan.equalsIgnoreCase("Pegawai Divisi")){
            role = 2;
        }
        return role;
    }

    private void init() {
        if(getIntent().getStringExtra("email") == null || getIntent().getStringExtra("email").equalsIgnoreCase("")){
            startActivity(new Intent(this, Login.class));
            finish();
        }else {
            if(getIntent().getStringExtra("email").equalsIgnoreCase("admin@eactivity.com")) {
                Log.d("email: ", getIntent().getStringExtra("email"));
                Toast.makeText(this, "Admin LoggedIn", Toast.LENGTH_LONG).show();
            }
        }

        nama = findViewById(R.id.edtTextNama);
        email = findViewById(R.id.edtTextEmail);
        password = findViewById(R.id.edtTextPassword);
        spinnerDivisi = findViewById(R.id.spinnerDivisi);
        spinnerJabatan = findViewById(R.id.spinnerJabatan);
        btnRegister = findViewById(R.id.btnRegister);
        ArrayAdapter<CharSequence> adapterDivisi = ArrayAdapter.createFromResource(this, R.array.array_divisi, android.R.layout.simple_spinner_dropdown_item);
        spinnerDivisi.setAdapter(adapterDivisi);
        ArrayAdapter<CharSequence> adapterJabatan = ArrayAdapter.createFromResource(this, R.array.array_jabatan, android.R.layout.simple_spinner_dropdown_item);
        spinnerJabatan.setAdapter(adapterJabatan);
    }
}