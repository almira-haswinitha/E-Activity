package com.example.e_activity.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Activity.PersetujuanActivity;
import com.example.e_activity.Fragment.TugasFragment;
import com.example.e_activity.Models.DataModel;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.Models.ResponseModel;
import com.example.e_activity.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    List<DataModel> ListTugas;
    private final Context ctx;
    private String idTugas;
    private final ModelUser user;
    private DataModel dm;

    public AdapterData(Context ctx, List<DataModel> listTugas, ModelUser user) {
        ListTugas = listTugas;
        this.user = user;
        this.ctx = ctx;
    }

    // inflate data dari card item ke recycle
    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new HolderData(layout);
    }

    // menyimpan text view yg telah dideklarasikan
    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        dm = ListTugas.get(position);
        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNamaTugas.setText(dm.getNama_tugas());
        holder.tvTglMulai.setText(dm.getTanggal_mulai());
        holder.tvTglSelesai.setText(dm.getTanggal_selesai());
        holder.tvJamMulai.setText(dm.getJam_mulai());
        holder.tvJamSelesai.setText(dm.getJam_selesai());
        holder.tvTanggapan.setText(dm.getPersetujuan());
        holder.tvComment.setText(dm.getComment());
        holder.tvNama.setText(dm.getNama());
    }

    // berapa banyak data yg ditarik
    @Override
    public int getItemCount() {
        return ListTugas.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNamaTugas, tvTglMulai, tvTglSelesai, tvJamMulai, tvJamSelesai, tvTanggapan, tvComment, tvNama;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tv_id);
            tvNamaTugas = (TextView) itemView.findViewById(R.id.tv_tugas);
            tvTglMulai = (TextView) itemView.findViewById(R.id.tv_tglmulai);
            tvTglSelesai = (TextView) itemView.findViewById(R.id.tv_tglselesai);
            tvJamMulai = (TextView) itemView.findViewById(R.id.tv_jammulai);
            tvJamSelesai = (TextView) itemView.findViewById(R.id.tv_jamselesai);
            tvNama = (TextView) itemView.findViewById(R.id.namaPembuatTugas);
            tvTanggapan = (TextView) itemView.findViewById(R.id.tanggapan);
            tvComment = (TextView) itemView.findViewById(R.id.komentar);
            if(String.valueOf(user.getRole()).equalsIgnoreCase("1")){
                itemView.setOnClickListener(v -> {
                    Intent i = new Intent(ctx, PersetujuanActivity.class);
                    i.putExtra("user", dm);
                    ctx.startActivity(i);
                });
            }else if(String.valueOf(user.getRole()).equalsIgnoreCase("2")){
                itemView.setOnLongClickListener((View.OnLongClickListener) v -> {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih Operasi Yang Akan Dilakukan");
                    dialogPesan.setCancelable(true); // dialog nya dapat di cancel

//                    idTugas = Integer.parseInt(tvId.getText().toString()); jika integer
                    idTugas = tvId.getText().toString();

                    dialogPesan.setPositiveButton("Hapus", (DialogInterface.OnClickListener) (dialogInterface, which) -> {
                        deleteTugas();
                        dialogInterface.dismiss();
//                            ((TugasFragment) ctx).dataTugas();
                        TugasFragment ctx = new TugasFragment();
                        ((TugasFragment) ctx).dataTugas();
                    });

                    dialogPesan.setNegativeButton("Ubah", (dialogInterface, which) -> {

                    });

                    dialogPesan.show();

                    return false;
                });
            }
            // tekan + tahan untuk menghapus data
        }
        private void deleteTugas(){
            APIRequestData ardData = APIClient.getClient().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.deleteTugas(idTugas);
            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String status = response.body().getPesan();

                    Log.d("SERVER_KODE",String.valueOf(kode));
                    Log.d("SERVER_PESAN",status);

                    Toast.makeText(ctx, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();

//                    Toast.makeText(ctx, "Kode :" +kode+"| Status : "+status, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Log.d("SERVER_PESAN","Gagal Menghubungi Server");
//                    Toast.makeText(ctx, "Gagal Menghubungi Server"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
