package com.omnia.nn.read.txt.file;

public class NnRecord {
    private String dataZlecenia;
    private String dataWyceny;
    private String nazwaFunduszu;
    private String typTransakcji;
    private String status;
    private String kwota;
    private String KwotaJednostka;
    private String wartoscRejestru;
    private String wartoscRejestruJednostka;

    public String getDataZlecenia() {
        return dataZlecenia;
    }

    public void setDataZlecenia(String dataZlecenia) {
        this.dataZlecenia = dataZlecenia;
    }

    public String getDataWyceny() {
        return dataWyceny;
    }

    public void setDataWyceny(String dataWyceny) {
        this.dataWyceny = dataWyceny;
    }

    public String getNazwaFunduszu() {
        return nazwaFunduszu;
    }

    public void setNazwaFunduszu(String nazwaFunduszu) {
        this.nazwaFunduszu = nazwaFunduszu;
    }

    public String getTypTransakcji() {
        return typTransakcji;
    }

    public void setTypTransakcji(String typTransakcji) {
        this.typTransakcji = typTransakcji;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getKwotaJednostka() {
        return KwotaJednostka;
    }

    public void setKwotaJednostka(String kwotaJednostka) {
        KwotaJednostka = kwotaJednostka;
    }

    public String getWartoscRejestru() {
        return wartoscRejestru;
    }

    public void setWartoscRejestru(String wartoscRejestru) {
        this.wartoscRejestru = wartoscRejestru;
    }

    public String getWartoscRejestruJednostka() {
        return wartoscRejestruJednostka;
    }

    public void setWartoscRejestruJednostka(String wartoscRejestruJednostka) {
        this.wartoscRejestruJednostka = wartoscRejestruJednostka;
    }
}
