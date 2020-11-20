package com.example.teamproject;

import java.io.Serializable;
import java.util.ArrayList;

public class filterLst implements Serializable {
    private ArrayList<String> tuitionFeeLst; //등록금 내, 외, 내+외 _ 이연주
    private String income; //소득분위 _ 이연주
    private String region; //지역 _ 이연주
    private ArrayList<String> spanLst; //시작기간, 종료기간 _ 이연주

    //default _ 이연주
    public filterLst(){
        tuitionFeeLst = new ArrayList<String>();
        income = "";
        region = "";
        spanLst = new ArrayList<String>();
    }

    //get Method _ 이연주
    public ArrayList<String> getTuitionFeeLst(){return tuitionFeeLst;}
    public String getIncome(){return income;}
    public String getRegion(){return region;}
    public ArrayList<String> getSpanLst(){return spanLst;}

    //set Method _ 이연주
    public void setTuitionFeeLst(ArrayList<String> ttL){tuitionFeeLst = ttL;}
    public void setIncome(String icL){income = icL;}
    public void setRegion(String rgL){region = rgL;}
    public void setSpanLst(ArrayList<String> spL){spanLst = spL;}
}
