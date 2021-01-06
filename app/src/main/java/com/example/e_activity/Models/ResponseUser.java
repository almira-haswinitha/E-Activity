package com.example.e_activity.Models;

public class ResponseUser {

    private int kode, role;
    private String pesan, nama, email, password, divisi;

    public ResponseUser(int id, int kode, int role, String pesan, String nama, String email, String password, String divisi) {
        this.kode = kode;
        this.role = role;
        this.pesan = pesan;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.divisi = divisi;
    }

    public ResponseUser() {
        this.kode = 0;
        this.role = 0;
        this.pesan = "";
        this.nama = "";
        this.email = "";
        this.password = "";
        this.divisi = "";
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }
}
