package com.example.teamproject;

import java.io.Serializable;
import java.util.ArrayList;

public class filterLst implements Serializable {
    private ArrayList<String> tuitionFeeLst;
    private ArrayList<String> incomeLst;
    private ArrayList<String> regionLst;

    public filterLst(){
        tuitionFeeLst = new ArrayList<String>();
        incomeLst = new ArrayList<String>();
        regionLst = new ArrayList<String>();
    }

    public ArrayList<String> getTuitionFeeLst(){return tuitionFeeLst;}
    public ArrayList<String> getincomeLst(){return incomeLst;}
    public ArrayList<String> getregionLst(){return regionLst;}

    public void setTuitionFeeLst(ArrayList<String> ttL){tuitionFeeLst = ttL;}
    public void setIncomeLst(ArrayList<String> icL){incomeLst = icL;}
    public void setRegionLst(ArrayList<String> rgL){regionLst = rgL;}
}
