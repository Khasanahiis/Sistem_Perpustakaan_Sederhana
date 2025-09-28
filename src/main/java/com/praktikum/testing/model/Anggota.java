package com.praktikum.testing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Anggota {
    private String idAnggota;
    private String nama;
    private String email;
    private String telepon;
    private TipeAnggota tipeAnggota;
    private List<String> idBukuDipinjam;
    private boolean aktif;

    public enum TipeAnggota {
        MAHASISWA, DOSEN, UMUM
    }

    public Anggota() {
        this.idBukuDipinjam = new ArrayList<>();
        this.aktif = true;
    }

    public Anggota(String idAnggota, String nama, String email, String telepon, TipeAnggota tipeAnggota) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.email = email;
        this.telepon = telepon;
        this.tipeAnggota = tipeAnggota;
        this.idBukuDipinjam = new ArrayList<>();
        this.aktif = true;
    }

    // Getters dan Setters
    public String getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(String idAnggota) {
        this.idAnggota = idAnggota;
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

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public TipeAnggota getTipeAnggota() {
        return tipeAnggota;
    }

    public void setTipeAnggota(TipeAnggota tipeAnggota) {
        this.tipeAnggota = tipeAnggota;
    }

    public List<String> getIdBukuDipinjam() {
        return new ArrayList<>(idBukuDipinjam);
    }

    public void setIdBukuDipinjam(List<String> idBukuDipinjam) {
        this.idBukuDipinjam = new ArrayList<>(idBukuDipinjam);
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    public int getJumlahBukuDipinjam() {
        return idBukuDipinjam.size();
    }

    public int getBatasPinjam() {
        switch (tipeAnggota) {
            case MAHASISWA: return 3;
            case DOSEN: return 10;
            case UMUM: return 2;
            default: return 0;
        }
    }

    public boolean bolehPinjamLagi() {
        return aktif && getJumlahBukuDipinjam() < getBatasPinjam();
    }

    public void tambahBukuDipinjam(String idBuku) {
        if (!idBukuDipinjam.contains(idBuku)) {
            idBukuDipinjam.add(idBuku);
        }
    }

    public void hapusBukuDipinjam(String idBuku) {
        idBukuDipinjam.remove(idBuku);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anggota anggota = (Anggota) o;
        return Objects.equals(idAnggota, anggota.idAnggota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAnggota);
    }

    @Override
    public String toString() {
        return "Anggota{" +
                "idAnggota='" + idAnggota + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                ", telepon='" + telepon + '\'' +
                ", tipeAnggota=" + tipeAnggota +
                ", jumlahBukuDipinjam=" + idBukuDipinjam.size() +
                ", aktif=" + aktif +
                '}';
    }
}