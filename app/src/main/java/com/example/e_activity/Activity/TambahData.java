package com.example.e_activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.Models.ResponseModel;
import com.example.e_activity.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@SuppressWarnings("ALL")
public class TambahData extends AppCompatActivity implements View.OnClickListener {
    TextView tglMulai, tglSelesai, jamMulai, jamSelesai;
    Button btnSimpan;
    EditText namaTugas;
    final Calendar myCalendar = Calendar.getInstance();
    SharedPreferences mPrefs;
    ModelUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        init();
        tglMulai.setOnClickListener(this);
        tglSelesai.setOnClickListener(this);
        jamMulai.setOnClickListener(this);
        jamSelesai.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
    }

    private void init() {
        tglMulai = findViewById(R.id.tv_tglmulai);
        tglSelesai = findViewById(R.id.tv_tglselesai);
        jamMulai = findViewById(R.id.tv_jammulai);
        jamSelesai = findViewById(R.id.tv_jamselesai);
        btnSimpan = findViewById(R.id.btn_simpan);
        namaTugas = findViewById(R.id.et_namatugas);
        mPrefs = getSharedPreferences("eactivity", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        user = gson.fromJson(json, ModelUser.class);
    }

    private final TimePickerDialog.OnTimeSetListener hoursMulai = (view, hourOfDay, minute) -> {
        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        myCalendar.set(Calendar.MINUTE, minute);
        updateJamMulai(hourOfDay, minute);
    };

    private void updateJamMulai(int hourOfDay, int minute) {
        jamMulai.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
    }

    private final TimePickerDialog.OnTimeSetListener hoursSelesai = (view, hourOfDay, minute) -> updateJamSelesai(hourOfDay, minute);

    private void updateJamSelesai(int hourOfDay, int minute) {
        jamSelesai.setText(new StringBuilder().append(hourOfDay).append(":").append(minute));
    }

    private final DatePickerDialog.OnDateSetListener dateMulai = (view, year, month, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        myCalendar.set(Calendar.MONTH, month);
        updateTglMulai();
    };
    private final DatePickerDialog.OnDateSetListener dateSelesai = (view, year, month, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        myCalendar.set(Calendar.MONTH, month);
        updateTglSelesai();
    };
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_tglmulai){
            DatePickerDialog datePickerDialog = new DatePickerDialog(TambahData.this, dateMulai, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }else if(v.getId() == R.id.tv_tglselesai){
            DatePickerDialog datePickerDialog = new DatePickerDialog(TambahData.this, dateSelesai, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }else if(v.getId() == R.id.tv_jammulai){
            TimePickerDialog timePickerDialog = new TimePickerDialog(TambahData.this, hoursMulai, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }else if(v.getId() == R.id.tv_jamselesai){
            TimePickerDialog timePickerDialog = new TimePickerDialog(TambahData.this, hoursSelesai, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        }else if(v.getId() == R.id.btn_simpan){
            if(checkInput()){
                APIRequestData ardData = APIClient.getClient().create(APIRequestData.class); //menghubungkan class interface ke retrofit
                Call<ResponseModel> tampilData = ardData.createTugas(
                        namaTugas.getText().toString(),
                        tglMulai.getText().toString(),
                        tglSelesai.getText().toString(),
                        jamMulai.getText().toString(),
                        jamSelesai.getText().toString(),
                        "pending",
                        user.getEmail(),
                        user.getNama(),
                        user.getDivisi()
                );

                tampilData.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        Toast.makeText(TambahData.this, response.body().getPesan(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(TambahData.this, MainActivity.class));
                        finish();
//                pbData.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(TambahData.this, "Gagal Menghubungkan Server : " +t.getMessage(), Toast.LENGTH_SHORT).show();
//                pbData.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }
    }

    private boolean checkInput() {
        boolean cek = false;
        if(!namaTugas.getText().toString().equalsIgnoreCase("") || !tglMulai.getText().toString().equalsIgnoreCase("") || !tglSelesai.getText().toString().equalsIgnoreCase("") || !jamMulai.getText().toString().equalsIgnoreCase("") || !jamSelesai.getText().toString().equalsIgnoreCase("")){
            cek = true;
        }
        return cek;
    }

    private void updateTglSelesai() {
        String format = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        tglSelesai.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTglMulai() {
        String format = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        tglMulai.setText(sdf.format(myCalendar.getTime()));
    }

}