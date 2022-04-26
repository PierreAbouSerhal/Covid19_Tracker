package com.example.covid19tracker;

import java.util.Comparator;

public class CountryDetails implements Comparable<CountryDetails>{
    private String iso2;
    private int confNum;
    private int recovNum;
    private int deathNum;
    private String name;

    public CountryDetails(String iso2, int confNum, int recovNum, int deathNum) {
        this.iso2 = iso2;
        this.confNum = confNum;
        this.recovNum = recovNum;
        this.deathNum = deathNum;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public int getConfNum() {
        return confNum;
    }

    public void setConfNum(int confNum) {
        this.confNum = confNum;
    }

    public int getRecovNum() {
        return recovNum;
    }

    public void setRecovNum(int recovNum) {
        this.recovNum = recovNum;
    }

    public int getDeathNum() {
        return deathNum;
    }

    public void setDeathNum(int deathNum) {
        this.deathNum = deathNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(CountryDetails o) {
        return this.name.compareTo(o.getName());
    }

}
