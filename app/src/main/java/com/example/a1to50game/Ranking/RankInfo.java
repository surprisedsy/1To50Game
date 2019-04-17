package com.example.a1to50game.Ranking;

public class RankInfo {

    String nameTxt, recordTxt;

    public RankInfo() {}

    public RankInfo(String name, String record)
    {
        this.nameTxt = name;
        this.recordTxt = record;
    }

    public String getNameTxt() {
        return nameTxt;
    }

    public void setNameTxt(String nameTxt) {
        this.nameTxt = nameTxt;
    }

    public String getRecordTxt() {
        return recordTxt;
    }

    public void setRecordTxt(String recordTxt) {
        this.recordTxt = recordTxt;
    }
}
