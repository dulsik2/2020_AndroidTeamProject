package com.example.teamproject;

import java.io.Serializable;
import java.util.ArrayList;

public class filterLst implements Serializable {
    private ArrayList<String> tuitionFeeLst; //등록금 내, 외, 내+외
    private String income; //소득분위
    private String region; //지역
    private ArrayList<String> spanLst; //시작기간, 종료기간

    //default
    public filterLst(){
        tuitionFeeLst = new ArrayList<String>();
        income = "";
        region = "";
        spanLst = new ArrayList<String>();
    }

    //get Method
    public ArrayList<String> getTuitionFeeLst(){return tuitionFeeLst;}
    public String getIncome(){return income;}
    public String getRegion(){return region;}
    public ArrayList<String> getSpanLst(){return spanLst;}

    //set Method
    public void setTuitionFeeLst(ArrayList<String> ttL){tuitionFeeLst = ttL;}
    public void setIncome(String icL){income = icL;}
    public void setRegion(String rgL){region = rgL;}
    public void setSpanLst(ArrayList<String> spL){spanLst = spL;}
}
