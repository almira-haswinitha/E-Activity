package com.example.e_activity.API;

import com.example.e_activity.Models.ResponseModel;
import com.example.e_activity.Models.ResponseUser;
import com.example.e_activity.Models.ResponseUserList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIRequestData {

    @FormUrlEncoded
    @POST("createtugas.php")
    Call<ResponseModel> createTugas(@Field("nama_tugas") String nama_tugas,
                                    @Field("tanggal_mulai") String tanggal_mulai,
                                    @Field("tanggal_selesai") String tanggal_selesai,
                                    @Field("jam_mulai") String jam_mulai,
                                    @Field("jam_selesai") String jam_selesai,
                                    @Field("persetujuan") String persetujuan,
                                    @Field("email") String email,
                                    @Field("nama") String nama,
                                    @Field("divisi") String divisi);

    @FormUrlEncoded
    @POST("createuser.php")
    Call<ResponseUser> createUser(@Field("nama") String nama,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("divisi") String divisi,
                                  @Field("jabatan") int jabatan);

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseModel> deleteTugas(@Field("id") String id);

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<ResponseUser> loginUser(@Field("email") String email,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("persetujuan.php")
    Call<ResponseModel> persetujuanTugas(@Field("id") int id,
                                         @Field("comment") String comment,
                                         @Field("persetujuan") String persetujuan);

    @FormUrlEncoded
    @POST("retrievelistpegawai.php")
    Call<ResponseUserList> getListPegawai(@Field("divisi") String divisi,
                                          @Field("role") int role);

    @FormUrlEncoded
    @POST("retrievestatustugas.php")
    Call<ResponseModel> getStatusTugas(@Field("email") String email);

    @FormUrlEncoded
    @POST("retrievetugas.php")
    Call<ResponseModel> getTugasAtasan(@Field("divisi") String divisi);

    @FormUrlEncoded
    @POST("retrievetugaspegawai.php")
    Call<ResponseModel> getTugasPegawai(@Field("email") String email);

}
