package com.praktikum.testing.service;

import com.praktikum.testing.model.Peminjaman;
import com.praktikum.testing.model.Anggota;

public class KalkulatorDenda {

    // Tarif denda harian per tipe (dalam Rupiah)
    private static final double TARIF_DENDA_MAHASISWA = 1000.0;
    private static final double TARIF_DENDA_DOSEN = 2000.0;
    private static final double TARIF_DENDA_UMUM = 1500.0;

    // Batas maksimal denda
    private static final double DENDA_MAX_MAHASISWA = 50000.0;
    private static final double DENDA_MAX_DOSEN = 100000.0;
    private static final double DENDA_MAX_UMUM = 75000.0;

    public double hitungDenda(Peminjaman peminjaman, Anggota anggota) {
        if (peminjaman == null || anggota == null) {
            throw new IllegalArgumentException("Peminjaman dan Anggota tidak boleh null");
        }
        if (!peminjaman.isTerlambat()) {
            return 0.0;
        }
        long hariTerlambat = peminjaman.getHariTerlambat();
        if (hariTerlambat <= 0) {
            return 0.0;
        }
        double tarifHarian = getTarifDendaHarian(anggota.getTipeAnggota());
        double totalDenda = tarifHarian * hariTerlambat;
        double dendaMaks = getDendaMaximal(anggota.getTipeAnggota());
        return Math.min(totalDenda, dendaMaks);
    }

    public double getTarifDendaHarian(Anggota.TipeAnggota tipeAnggota) {
        if (tipeAnggota == null) {
            throw new IllegalArgumentException("Tipe anggota tidak boleh null");
        }
        switch (tipeAnggota) {
            case MAHASISWA:
                return TARIF_DENDA_MAHASISWA;
            case DOSEN:
                return TARIF_DENDA_DOSEN;
            case UMUM:
                return TARIF_DENDA_UMUM;
            default:
                throw new IllegalArgumentException("Tipe anggota tidak dikenali: " + tipeAnggota);
        }
    }

    public double getDendaMaximal(Anggota.TipeAnggota tipeAnggota) {
        if (tipeAnggota == null) {
            throw new IllegalArgumentException("Tipe anggota tidak boleh null");
        }
        switch (tipeAnggota) {
            case MAHASISWA:
                return DENDA_MAX_MAHASISWA;
            case DOSEN:
                return DENDA_MAX_DOSEN;
            case UMUM:
                return DENDA_MAX_UMUM;
            default:
                throw new IllegalArgumentException("Tipe anggota tidak dikenali: " + tipeAnggota);
        }
    }

    public boolean adaDenda(Peminjaman peminjaman) {
        return peminjaman != null && peminjaman.isTerlambat() && peminjaman.getHariTerlambat() > 0;
    }

    public String getDeskripsiDenda(double jumlahDenda) {
        if (jumlahDenda <= 0) {
            return "Tidak ada denda";
        } else if (jumlahDenda < 10000) {
            return "Denda ringan";
        } else if (jumlahDenda < 50000) {
            return "Denda sedang";
        } else {
            return "Denda berat";
        }
    }
}