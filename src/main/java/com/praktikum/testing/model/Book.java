package com.praktikum.testing.model;

import java.util.Objects;

public class Book {
    private String isbn;
    private String judul;
    private String pengarang;
    private int jumlahTotal;
    private int jumlahTersedia;
    private double harga;

    public Book() {
    }

    public Book(String isbn, String judul, String pengarang, int jumlahTotal, double harga) {
        this.isbn = isbn;
        this.judul = judul;
        this.pengarang = pengarang;
        this.jumlahTotal = jumlahTotal;
        this.jumlahTersedia = jumlahTotal;
        this.harga = harga;
    }

    // Getters dan Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public int getJumlahTotal() {
        return jumlahTotal;
    }

    public void setJumlahTotal(int jumlahTotal) {
        this.jumlahTotal = jumlahTotal;
    }

    public int getJumlahTersedia() {
        return jumlahTersedia;
    }

    public void setJumlahTersedia(int jumlahTersedia) {
        this.jumlahTersedia = jumlahTersedia;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public boolean isTersedia() {
        return jumlahTersedia > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book buku = (Book) o;
        return Objects.equals(isbn, buku.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Buku{" +
                "isbn='" + isbn + '\'' +
                ", judul='" + judul + '\'' +
                ", pengarang='" + pengarang + '\'' +
                ", jumlahTotal=" + jumlahTotal +
                ", jumlahTersedia=" + jumlahTersedia +
                ", harga=" + harga +
                '}';
    }
}