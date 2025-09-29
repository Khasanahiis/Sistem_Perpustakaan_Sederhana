package com.praktikum.testing.repository;

import com.praktikum.testing.model.Book;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Mock implementation dari RepositoryBuku untuk keperluan testing
 * Menggunakan in-memory storage dengan ConcurrentHashMap
 *
 * Catatan: Ini hanya untuk demo! Dalam test yang sebenarnya,
 * sebaiknya gunakan Mockito untuk mocking repository.
 */
public class MockRepositoryBuku implements RepositoryBuku {
    private final Map<String, Book> bukuMap = new ConcurrentHashMap<>();

    @Override
    public boolean simpan(Book buku) {
        if (buku == null || buku.getIsbn() == null) {
            return false;
        }

        // Simulasi operasi simpan ke database
        bukuMap.put(buku.getIsbn(), buku);
        return true;
    }

    @Override
    public Optional<Book> cariByIsbn(String isbn) {
        if (isbn == null) {
            return Optional.empty();
        }
        Book buku = bukuMap.get(isbn);
        return Optional.ofNullable(buku);
    }

    @Override
    public List<Book> cariByJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return bukuMap.values().stream()
                .filter(buku -> buku.getJudul().toLowerCase()
                        .contains(judul.toLowerCase().trim()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> cariByPengarang(String pengarang) {
        if (pengarang == null || pengarang.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return bukuMap.values().stream()
                .filter(buku -> buku.getPengarang().toLowerCase()
                        .contains(pengarang.toLowerCase().trim()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hapus(String isbn) {
        if (isbn == null) {
            return false;
        }
        Book bukuDihapus = bukuMap.remove(isbn);
        return bukuDihapus != null;
    }

    @Override
    public boolean updateJumlahTersedia(String isbn, int jumlahTersediaBaru) {
        if (isbn == null || jumlahTersediaBaru < 0) {
            return false;
        }

        Book buku = bukuMap.get(isbn);
        if (buku == null) {
            return false;
        }

        // Cek apakah jumlah tersedia baru valid
        if (jumlahTersediaBaru > buku.getJumlahTotal()) {
            return false;
        }

        buku.setJumlahTersedia(jumlahTersediaBaru);
        return true;
    }

    @Override
    public List<Book> cariSemua() {
        return new ArrayList<>(bukuMap.values());
    }

    // Utility methods untuk testing
    public void bersihkan() {
        bukuMap.clear();
    }

    public int ukuran() {
        return bukuMap.size();
    }

    public boolean mengandung(String isbn) {
        return bukuMap.containsKey(isbn);
    }
}