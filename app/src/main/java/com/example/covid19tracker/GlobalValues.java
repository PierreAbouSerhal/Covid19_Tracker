package com.example.covid19tracker;

public class GlobalValues {
    private String globalCase;
    private int caseNum;
    private int caseRate;

    public GlobalValues(String globalCase, int caseNum, int caseRate) {
        this.globalCase = globalCase;
        this.caseNum = caseNum;
        this.caseRate = caseRate;
    }

    public String getGlobalCase() {
        return globalCase;
    }

    public int getCaseNum() {
        return caseNum;
    }

    public int getCaseRate() {
        return caseRate;
    }

    public void setGlobalCase(String globalCase) {
        this.globalCase = globalCase;
    }

    public void setCaseNum(int caseNum) {
        this.caseNum = caseNum;
    }

    public void setCaseRate(int caseRate) {
        this.caseRate = caseRate;
    }
}
