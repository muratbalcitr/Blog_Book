package com.murat.books.model;

public class Book {
    private String kitap_adi, yazar_Adi, ISBN, basim_tarihi, ozet;

    public Book() {
    }

    public Book(String kitap_adi, String yazar_Adi, String ISBN, String basim_tarihi, String ozet) {
        this.kitap_adi = kitap_adi;
        this.yazar_Adi = yazar_Adi;
        this.ISBN = ISBN;
        this.basim_tarihi = basim_tarihi;
        this.ozet = ozet;
    }

    public String getKitap_adi() {
        return kitap_adi;
    }

    public void setKitap_adi(String kitap_adi) {
        this.kitap_adi = kitap_adi;
    }

    public String getYazar_Adi() {
        return yazar_Adi;
    }

    public void setYazar_Adi(String yazar_Adi) {
        this.yazar_Adi = yazar_Adi;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBasim_tarihi() {
        return basim_tarihi;
    }

    public void setBasim_tarihi(String basim_tarihi) {
        this.basim_tarihi = basim_tarihi;
    }

    public String getOzet() {
        return ozet;
    }

    public void setOzet(String ozet) {
        this.ozet = ozet;
    }
}
