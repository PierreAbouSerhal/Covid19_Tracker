package com.example.covid19tracker;

public class GeneralInfo {
    private int position;
    private String info;

    public GeneralInfo(int position, String info) {
        this.position = position;
        this.info = info;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
