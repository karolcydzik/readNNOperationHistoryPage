package com.omnia.nn.read.txt.file;

import java.util.Date;

public class StockExchange {
    private Date date;
    private Float value;

    public StockExchange(Date date, Float value) {
        this.date = date;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}