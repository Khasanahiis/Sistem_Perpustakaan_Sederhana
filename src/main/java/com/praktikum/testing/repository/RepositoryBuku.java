package com.praktikum.testing.repository;

import com.praktikum.testing.model.Book;

import java.util.List;
import java.util.Optional;

public interface RepositoryBuku {
    boolean simpan(Book buku);
    Optional<Book> cariByIsbn(String isbn);
    List<Book> cariByJudul(String judul);
    List<Book> cariByPengarang(String pengarang);
    boolean hapus(String isbn);
    boolean updateJumlahTersedia(String isbn, int jumlahTersediaBaru);
    List<Book> cariSemua();
}