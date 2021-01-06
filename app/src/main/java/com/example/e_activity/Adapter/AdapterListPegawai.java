package com.example.e_activity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterListPegawai extends RecyclerView.Adapter<AdapterListPegawai.HolderData>{
    List<ModelUser> listModelUser;

    public AdapterListPegawai(List<ModelUser> list) {
        listModelUser = list;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_listnamapegawai, parent, false);
        return new HolderData(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelUser modelUser = listModelUser.get(position);
        holder.tvNama.setText(modelUser.getNama());
        holder.tvEmail.setText(modelUser.getEmail());
    }

    @Override
    public int getItemCount() {
        return listModelUser.size();
    }

    public static class HolderData extends RecyclerView.ViewHolder{
        TextView tvNama, tvEmail;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.namaPegawai);
            tvEmail = itemView.findViewById(R.id.emailPegawai);
        }
    }
}
