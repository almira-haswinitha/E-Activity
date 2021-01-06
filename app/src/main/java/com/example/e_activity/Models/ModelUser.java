package com.example.e_activity.Models;

import java.io.Serializable;

public class ModelUser implements Serializable {
    private int role;

    private String nama, email, password, divisi;

    public ModelUser(int role, String nama, String email, String password, String divisi) {
        this.role = role;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.divisi = divisi;
    }

    public ModelUser() {
        this.role = 0;
        this.nama = "";
        this.email = "";
        this.password = "";
        this.divisi = "";
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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
