package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        Spinner incomeSpinner = (Spinner)findViewById(R.id.incomeSpin);
        Spinner regionSpinner = (Spinner)findViewById(R.id.regionSpin);

        final TextView incomeTxtView = (TextView)findViewById(R.id.incomeTxt);
        final TextView regionTxtView = (TextView)findViewById(R.id.regionTxt);

        Intent getIntent = getIntent();

        ArrayList<Integer> t = getIntent.getExtras().getIntegerArrayList("test");

        incomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeTxtView.setText("" + parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                incomeTxtView.setText("선택안함");
            }
        });

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                regionTxtView.setText("" + parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                regionTxtView.setText("선택안함");
            }
        });
    }

}