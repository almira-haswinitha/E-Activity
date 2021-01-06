package com.example.e_activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Models.DataModel;
import com.example.e_activity.Models.ResponseModel;
import com.example.e_activity.R;

@SuppressWarnings("ALL")
public class PersetujuanActivity extends AppCompatActivity {

    TextView tglMulai, tglSelesai, jamMulai, jamSelesai;
    Button btnTerima, btnTolak;
    EditText namaTugas, komentarPersetujuan;
    DataModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan);
        dataModel = (DataModel) getIntent().getSerializableExtra("user");
        Log.d("ID: ", String.valueOf(dataModel.getId()));
        init();
        namaTugas.setText(dataModel.getNama_tugas());
        tglMulai.setText(dataModel.getTanggal_mulai());
        tglSelesai.setText(dataModel.getTanggal_selesai());
        jamMulai.setText(dataModel.getJam_mulai());
        jamSelesai.setText(dataModel.getJam_selesai());
        namaTugas.setEnabled(false);
        btnTerima.setOnClickListener(v -> {
            persetujuan("diterima");
        });
        btnTolak.setOnClickListener(v -> {
            persetujuan("ditolak");
        });
    }

    private void persetujuan(String textpersetujuan) {
        APIRequestData ardData = APIClient.getClient().create(APIRequestData.class); //menghubungkan class interface ke retrofit
        Call<ResponseModel> tampilData = ardData.persetujuanTugas(
                dataModel.getId(),
                komentarPersetujuan.getText().toString(),
                textpersetujuan
        );

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(PersetujuanActivity.this, response.body().getPesan(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersetujuanActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(PersetujuanActivity.this, "Gagal Menghubungkan Server : " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void init() {
        namaTugas = findViewById(R.id.et_namatugas);
        tglMulai = findViewById(R.id.tv_tglmulai);
        tglSelesai = findViewById(R.id.tv_tglselesai);
        jamMulai = findViewById(R.id.tv_jammulai);
        jamSelesai = findViewById(R.id.tv_jamselesai);
        btnTerima = findViewById(R.id.btn_terima);
        btnTolak = findViewById(R.id.btn_tolak);
        komentarPersetujuan = findViewById(R.id.komentarPersetujuan);
    }
}