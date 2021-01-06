package com.example.e_activity.Models;

import java.io.Serializable;

public class DataModel implements Serializable {
    private int id;
    private String nama_tugas;
    private String tanggal_mulai;
    private String tanggal_selesai;
    private String jam_mulai;
    private String jam_selesai;
    private String comment;
    private String persetujuan;
    private String email;
    private String nama;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPersetujuan() {
        return persetujuan;
    }

    public void setPersetujuan(String persetujuan) {
        this.persetujuan = persetujuan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_tugas() {
        return nama_tugas;
    }

    public void setNama_tugas(String nama_tugas) {
        this.nama_tugas = nama_tugas;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }
}
