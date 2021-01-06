package com.example.e_activity.Models;

import java.util.List;

public class ResponseUserList {
    private int kode;
    private String pesan;
    private List<ModelUser> data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<ModelUser> getData() {
        return data;
    }

    public void setData(List<ModelUser> data) {
        this.data = data;
    }
}
