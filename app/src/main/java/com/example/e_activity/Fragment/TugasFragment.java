package com.example.e_activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_activity.API.APIClient;
import com.example.e_activity.API.APIRequestData;
import com.example.e_activity.Activity.TambahData;
import com.example.e_activity.Adapter.AdapterData;
import com.example.e_activity.Models.DataModel;
import com.example.e_activity.Models.ModelUser;
import com.example.e_activity.Models.ResponseModel;
import com.example.e_activity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class TugasFragment extends Fragment {
    FloatingActionButton fab_add;
    private RecyclerView rvTugas;
    public AdapterData adapterData;
    private RecyclerView.LayoutManager lmTugas;
    private List<DataModel> ListTugas = new ArrayList<>();
    private SwipeRefreshLayout srlTugas;
    private ProgressBar pbData;


    public TugasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    SharedPreferences mPrefs;
    ModelUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tugas, container, false);
        mPrefs = getContext().getSharedPreferences("eactivity", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userKey","");
        user = gson.fromJson(json, ModelUser.class);

        fab_add = view.findViewById(R.id.fab);
        if(String.valueOf(user.getRole()).equalsIgnoreCase("1")){
            fab_add.setVisibility(View.INVISIBLE);
        }else if(String.valueOf(user.getRole()).equalsIgnoreCase("2")){
            fab_add.setVisibility(View.VISIBLE);
        }
        rvTugas = view.findViewById(R.id.rv_data);
//        srlTugas = view.findViewById(R.id.srl_data);
//        pbData = view.findViewById(R.id.pb_data);

        lmTugas = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        rvTugas.setLayoutManager(lmTugas);
        dataTugas();

//        srlTugas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                srlTugas.setRefreshing(true);
//                dataTugas();
//                srlTugas.setRefreshing(false);
//            }
//        });


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TambahData.class);
                startActivity(intent);

            }
        });

        return view;
    }

    //    @Override
//    protected void onResume() {
//
//        super.onResume();
//        dataTugas();
//    }


    public void dataTugas(){
//        pbData.setVisibility(View.VISIBLE);
        Call<ResponseModel> tampilData = null;
        APIRequestData ardData = APIClient.getClient().create(APIRequestData.class); //menghubungkan class interface ke retrofit
        if(String.valueOf(user.getRole()).equalsIgnoreCase("1")){
            tampilData = ardData.getTugasAtasan(user.getDivisi());
        }else if(String.valueOf(user.getRole()).equalsIgnoreCase("2")){
            tampilData = ardData.getTugasPegawai(user.getEmail());
        }
        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().getData() != null){
                    ListTugas = response.body().getData();
                    adapterData = new AdapterData(getActivity(), ListTugas, user);
                    rvTugas.setAdapter(adapterData);
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