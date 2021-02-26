package com.example.e_activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.Models.ResponseUser;
import com.example.e_activity.R;
import com.google.gson.Gson;

@SuppressWarnings("ALL")
public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button btnLogin;
    SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPrefs = getSharedPreferences("eactivity", Context.MODE_PRIVATE);
        checkLoggedIn();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(this);
    }

    // cek user udah login atau belum
    private void checkLoggedIn() {
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        ModelUser user = gson.fromJson(json, ModelUser.class);
        if(!json.equalsIgnoreCase("")){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("user", user);
            startActivity(i);
            finish();
        }
    }

    private void init() {
        email = findViewById(R.id.edtTextEmail);
        password = findViewById(R.id.edtTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogin){
            if(email.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")){
                Toast.makeText(this, "Mohon isi data dengan Lengkap !", Toast.LENGTH_SHORT).show();
            }
            else{

                 if(email.getText().toString().equalsIgnoreCase("admin@eactivity.com") || password.getText().toString().equalsIgnoreCase("admineactivity")){
                    Intent i = new Intent(this, Register.class);
                    i.putExtra("email", "admin@eactivity.com");
                    i.putExtra("pass ", "admineactivity");
                    startActivity(i);
                }else{
                    APIRequestData apiRequestData = APIClient.getClient().create(APIRequestData.class);
                    Call<ResponseUser> loginUser = apiRequestData.loginUser(email.getText().toString(), password.getText().toString());
                    loginUser.enqueue(new Callback<ResponseUser>() {
                        @Override
                        public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                            if(response.body().getKode() == 200){
                                Toast.makeText(Login.this, response.body().getPesan(), Toast.LENGTH_LONG).show();
                                Gson gson = new Gson();
                                ModelUser user = new ModelUser(response.body().getRole(), response.body().getNama(), response.body().getEmail(), response.body().getPassword(), response.body().getDivisi());
                                String json = gson.toJson(user);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                prefsEditor.putString("userKey",json);
                                prefsEditor.commit();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(Login.this, response.body().getPesan(), Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseUser> call, Throwable t) {
                            Toast.makeText(Login.this, t.fillInStackTrace().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            finish();
        }
    }
}