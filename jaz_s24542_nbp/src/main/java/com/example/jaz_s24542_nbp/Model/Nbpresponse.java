package com.example.jaz_s24542_nbp.Model;

import java.util.List;

public class Nbpresponse {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;


    public static class Rate {
        private String no;
        private String effectiveData;
        private double mid;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getEffectiveData() {
            return effectiveData;
        }

        public void setEffectiveData(String effectiveData) {
            this.effectiveData = effectiveData;
        }

        public double getMid() {
            return mid;
        }

        public void setMid(double mid) {
            this.mid = mid;
        }
    }


    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
