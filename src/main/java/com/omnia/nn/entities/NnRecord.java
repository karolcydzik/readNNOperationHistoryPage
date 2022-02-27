package com.omnia.nn.entities;

public class NnRecord {
    private String dataZlecenia;
    private String dataWyceny;
    private String nazwaFunduszu;
    private String typTransakcji;
    private String status;
    private Float kwota;
    private String jednostka;
    private Float wartoscRejestru;
    private Float cenaDatyZlecenia;
    private Float cenaDatyWyceny;

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

    public Float getKwota() {
        return kwota;
    }

    public void setKwota(Float kwota) {
        this.kwota = kwota;
    }

    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public Float getWartoscRejestru() {
        return wartoscRejestru;
    }

    public void setWartoscRejestru(Float wartoscRejestru) {
        this.wartoscRejestru = wartoscRejestru;
    }

    public Float getCenaDatyZlecenia() {
        return cenaDatyZlecenia;
    }

    public void setCenaDatyZlecenia(Float cenaDatyZlecenia) {
        this.cenaDatyZlecenia = cenaDatyZlecenia;
    }

    public Float getCenaDatyWyceny() {
        return cenaDatyWyceny;
    }

    public void setCenaDatyWyceny(Float cenaDatyWyceny) {
        this.cenaDatyWyceny = cenaDatyWyceny;
    }
}
