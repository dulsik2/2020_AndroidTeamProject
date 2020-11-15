package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        final ArrayList<String> tFLst = new ArrayList<String>();
        final ArrayList<String> iCLst = new ArrayList<String>();
        final ArrayList<String> rGLst = new ArrayList<String>();

        final CheckBox tuitionInCkBox = (CheckBox)findViewById(R.id.tuitionInCk);
        final CheckBox tuitionOutCkBox = (CheckBox)findViewById(R.id.tuitionOutCK);
        final CheckBox tuitionInandOutCkBox = (CheckBox)findViewById(R.id.tuitionInandOutCk);
        final CheckBox incomeCkBox = (CheckBox)findViewById(R.id.incomeCk);
        final CheckBox regionCkBox = (CheckBox)findViewById(R.id.regionCk);

        Spinner incomeSpinner = (Spinner)findViewById(R.id.incomeSpin);
        Spinner regionSpinner = (Spinner)findViewById(R.id.regionSpin);

        final TextView tuitioninTxtView = (TextView)findViewById(R.id.tuitionInTxt);
        final TextView tuitionOutTxtView = (TextView)findViewById(R.id.tuitionOutTxt);
        final TextView tuitionInandOutTxtView = (TextView)findViewById(R.id.tuitionInandOutTxt);
        final TextView incomeTxtView = (TextView)findViewById(R.id.incomeTxt);
        final TextView regionTxtView = (TextView)findViewById(R.id.regionTxt);

        Button allCkButton = (Button)findViewById(R.id.allCkBoxBT);
        Button cancelButton = (Button)findViewById(R.id.cancelBT);
        Button setFilterButton = (Button)findViewById(R.id.setFilteringBT);

        Intent getIntent = getIntent();
        final Intent backIntent = new Intent();

        filterLst FL = new filterLst();

        FL = (filterLst)getIntent.getSerializableExtra("filterLst");

        incomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeTxtView.setText("" + parent.getItemAtPosition(position));
                if (incomeTxtView.getText().equals("선택안함")) {incomeCkBox.setChecked(false);}
                else incomeCkBox.setChecked(true);
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
                if (regionTxtView.getText().equals("선택안함")) regionCkBox.setChecked(false);
                else regionCkBox.setChecked(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
                regionTxtView.setText("선택안함");
            }
        });

        final filterLst finalFL = FL;
        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tuitionInCkBox.isChecked()) {tFLst.add(tuitioninTxtView.getText().toString());}
                if (tuitionOutCkBox.isChecked()) {tFLst.add(tuitionOutTxtView.getText().toString());}
                if (tuitionInandOutCkBox.isChecked()) {tFLst.add(tuitionInandOutTxtView.getText().toString());}
                if (incomeCkBox.isChecked()) {iCLst.add(incomeTxtView.getText().toString());}
                if (regionCkBox.isChecked()) {rGLst.add(regionTxtView.getText().toString());}

                finalFL.setTuitionFeeLst(tFLst);
                finalFL.setIncomeLst(iCLst);
                finalFL.setRegionLst(rGLst);
                backIntent.putExtra("backFilterLst", finalFL);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });
    }
}