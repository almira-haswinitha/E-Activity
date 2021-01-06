package com.example.e_activity.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Adapter.AdapterListPegawai;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.Models.ResponseUserList;
import com.example.e_activity.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class ListPegawaiFragment extends Fragment {

    private RecyclerView rvListPegawai;
    SharedPreferences mPrefs;
    ModelUser user;
    AdapterListPegawai adapterListPegawai;
    List<ModelUser> listModelUser = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_pegawai, container, false);
        rvListPegawai = view.findViewById(R.id.rv_pegawai);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        rvListPegawai.setLayoutManager(lm);
        mPrefs = getContext().getSharedPreferences("eactivity", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        user = gson.fromJson(json, ModelUser.class);
        Toast.makeText(getContext(), user.getDivisi(), Toast.LENGTH_LONG).show();
        getData();
        return view;
    }

    private void getData() {
        APIRequestData ardData = APIClient.getClient().create(APIRequestData.class); //menghubungkan class interface ke retrofit
        Call<ResponseUserList> tampilData = ardData.getListPegawai(user.getDivisi(),2);
        tampilData.enqueue(new Callback<ResponseUserList>() {
            @Override
            public void onResponse(Call<ResponseUserList> call, Response<ResponseUserList> response) {
                if(response.body().getData() != null){
                    listModelUser = response.body().getData();
                    adapterListPegawai = new AdapterListPegawai(listModelUser);
                    rvListPegawai.setAdapter(adapterListPegawai);
                    adapterListPegawai.notifyDataSetChanged();
                }
//                pbData.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseUserList> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Menghubungkan Server : " +t.getMessage(), Toast.LENGTH_SHORT).show();
//                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}