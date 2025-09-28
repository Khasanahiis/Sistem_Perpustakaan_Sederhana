package com.praktikum.testing.service;

import com.praktikum.testing.model.Book;
import com.praktikum.testing.model.Anggota;
import com.praktikum.testing.repository.RepositoryBuku;
import com.praktikum.testing.util.ValidationUtils;

import java.util.List;
import java.util.Optional;

public class ServicePerpustakaan {

    private final RepositoryBuku repositoryBuku;
    private final KalkulatorDenda kalkulatorDenda;

    public ServicePerpustakaan(RepositoryBuku repositoryBuku, KalkulatorDenda kalkulatorDenda) {
        this.repositoryBuku = repositoryBuku;
        this.kalkulatorDenda = kalkulatorDenda;
    }

    public boolean tambahBuku(Book buku) {
        if (!ValidationUtils.isValidBuku(buku)) {
            return false;
        }

        // Cek apakah buku dengan ISBN yang sama sudah ada
        Optional<Book> bukuExisting = repositoryBuku.cariByIsbn(buku.getIsbn());
        if (bukuExisting.isPresent()) {
            return false; // Buku sudah ada
        }

        return repositoryBuku.simpan(buku);
    }

    public boolean hapusBuku(String isbn) {
        if (!ValidationUtils.isValidISBN(isbn)) {
            return false;
        }

        Optional<Book> buku = repositoryBuku.cariByIsbn(isbn);
        if (!buku.isPresent()) {
            return false; // Buku tidak ditemukan
        }

        // Cek apakah ada salinan yang sedang dipinjam
        if (buku.get().getJumlahTersedia() < buku.get().getJumlahTotal()) {
            return false; // Tidak bisa hapus karena ada yang dipinjam
        }

        return repositoryBuku.hapus(isbn);
    }

    public Optional<Book> cariBukuByIsbn(String isbn) {
        if (!ValidationUtils.isValidISBN(isbn)) {
            return Optional.empty();
        }
        return repositoryBuku.cariByIsbn(isbn);
    }

    public List<Book> cariBukuByJudul(String judul) {
        return repositoryBuku.cariByJudul(judul);
    }

    public List<Book> cariBukuByPengarang(String pengarang) {
        return repositoryBuku.cariByPengarang(pengarang);
    }

    public boolean bukuTersedia(String isbn) {
        Optional<Book> buku = repositoryBuku.cariByIsbn(isbn);
        return buku.isPresent() && buku.get().isTersedia();
    }

    public int getJumlahTersedia(String isbn) {
        Optional<Book> buku = repositoryBuku.cariByIsbn(isbn);
        return buku.map(Book::getJumlahTersedia).orElse(0);
    }

    public boolean pinjamBuku(String isbn, Anggota anggota) {
        // Validasi anggota
        if (!ValidationUtils.isValidAnggota(anggota) || !anggota.isAktif()) {
            return false;
        }

        // Cek apakah anggota masih bisa pinjam
        if (!anggota.bolehPinjamLagi()) {
            return false;
        }

        // Cek ketersediaan buku
        Optional<Book> bukuOpt = repositoryBuku.cariByIsbn(isbn);
        if (!bukuOpt.isPresent() || !bukuOpt.get().isTersedia()) {
            return false;
        }

        Book buku = bukuOpt.get();

        // Update jumlah tersedia
        boolean updateBerhasil = repositoryBuku.updateJumlahTersedia(isbn, buku.getJumlahTersedia() - 1);
        if (updateBerhasil) {
            anggota.tambahBukuDipinjam(isbn);
            return true;
        }
        return false;
    }

    public boolean kembalikanBuku(String isbn, Anggota anggota) {
        // Validasi
        if (!ValidationUtils.isValidISBN(isbn) || anggota == null) {
            return false;
        }

        // Cek apakah anggota meminjam buku ini
        if (!anggota.getIdBukuDipinjam().contains(isbn)) {
            return false;
        }

        Optional<Book> bukuOpt = repositoryBuku.cariByIsbn(isbn);
        if (!bukuOpt.isPresent()) {
            return false;
        }

        Book buku = bukuOpt.get();

        // Update jumlah tersedia
        boolean updateBerhasil = repositoryBuku.updateJumlahTersedia(isbn, buku.getJumlahTersedia() + 1);
        if (updateBerhasil) {
            anggota.hapusBukuDipinjam(isbn);
            return true;
        }
        return false;
    }
}