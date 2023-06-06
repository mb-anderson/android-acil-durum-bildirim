package com.gazi.myapplication;

public class Kullanici {
    String kullaniciid;
    String kullaniciAd;
    String kullaniciSoyad;
    String kullaniciTc;
    String telefon;

    public Kullanici(String kullaniciid, String kullaniciAd, String kullaniciSoyad, String kullaniciTc, String telefon) {
        this.kullaniciid = kullaniciid;
        this.kullaniciAd = kullaniciAd;
        this.kullaniciSoyad = kullaniciSoyad;
        this.kullaniciTc = kullaniciTc;
        this.telefon = telefon;

    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKullaniciid() {
        return kullaniciid;
    }

    public void setKullaniciid(String kullaniciid) {
        this.kullaniciid = kullaniciid;
    }

    public String getKullaniciAd() {
        return kullaniciAd;
    }

    public void setKullaniciAd(String kullaniciAd) {
        this.kullaniciAd = kullaniciAd;
    }

    public String getKullaniciSoyad() {
        return kullaniciSoyad;
    }

    public void setKullaniciSoyad(String kullaniciSoyad) {
        this.kullaniciSoyad = kullaniciSoyad;
    }

    public String getKullaniciTc() {
        return kullaniciTc;
    }

    public void setKullaniciTc(String kullaniciTc) {
        this.kullaniciTc = kullaniciTc;
    }

}
