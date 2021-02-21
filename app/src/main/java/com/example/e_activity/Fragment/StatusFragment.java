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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Adapter.AdapterData;
import com.example.e_activity.Models.DataModel;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.Models.ResponseModel;
import com.example.e_activity.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class StatusFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<DataModel> ListTugas = new ArrayList<>();
    private AdapterData adapterData;
    SharedPreferences mPrefs;
    ModelUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        // mengakses dan menyimpan data
        mPrefs = getContext().getSharedPreferences("eactivity", Context.MODE_PRIVATE); // menandakan bahwa pengaturan ini tidak dibagikan ke aplikasi lain.
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        user = gson.fromJson(json, ModelUser.class);
        Log.d("Data : ", user.getEmail());
        recyclerView = view.findViewById(R.id.rv_status);
        LinearLayoutManager lmTugas = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lmTugas);
        statusTugas();
        return view;
    }

    private void statusTugas() {
        APIRequestData ardData = APIClient.getClient().create(APIRequestData.class); //menghubungkan class interface ke retrofit
        Call<ResponseModel> tampilData = ardData.getStatusTugas(user.getEmail());

        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().getData() != null){
                    ListTugas = response.body().getData();
                    Log.d("data: ",response.body().getData().toString());
                    adapterData = new AdapterData(getActivity(), ListTugas, user);
                    recyclerView.setAdapter(adapterData);
                    adapterData.notifyDataSetChanged();
                }
//                pbData.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Menghubungkan Server : " +t.getMessage(), Toast.LENGTH_SHORT).show();
//                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}