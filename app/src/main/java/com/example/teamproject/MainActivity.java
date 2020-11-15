package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    filterLst FL = new filterLst();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button filterBt = (Button)findViewById(R.id.filterBT);
        Button searchBt = (Button)findViewById(R.id.searchBT);

        filterBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterIntent = new Intent(getApplicationContext(), Filter.class);
                filterIntent.putExtra("filterLst", FL);
                startActivityForResult(filterIntent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String tmp = "";
                TextView ttSetFilt = (TextView)findViewById(R.id.tuitionSetFilterTxt);
                TextView icSetFilt = (TextView)findViewById(R.id.incomeSetFilterTxt);
                TextView rgSetFilt = (TextView)findViewById(R.id.regionSetFilterTxt);

                FL = (filterLst)data.getSerializableExtra("backFilterLst");
                ArrayList<String> tuitionfeeLst = FL.getTuitionFeeLst();
                ArrayList<String> incomeLst = FL.getincomeLst();
                ArrayList<String> regionLst = FL.getregionLst();

                if (!tuitionfeeLst.isEmpty()) {
                    tmp = "[ 등록금 내 / 외 / 내 + 외 : ";
                    for (String str:tuitionfeeLst){
                        tmp += str + ", ";
                    }
                    tmp += "]";
                    ttSetFilt.setText(tmp);
                    ttSetFilt.setVisibility(View.VISIBLE);
                }
                else ttSetFilt.setVisibility(View.GONE);
                if (!incomeLst.isEmpty()) {
                    tmp = "[ 소득분위 구분 / 미구분 : ";
                    for (String str:incomeLst){
                        tmp += str + ", ";
                    }
                    tmp += "]";
                    icSetFilt.setText(tmp);
                    icSetFilt.setVisibility(View.VISIBLE);
                }
                else icSetFilt.setVisibility(View.GONE);
                if (!regionLst.isEmpty()) {
                    tmp = "[ 지역 : ";
                    for (String str:regionLst){
                        tmp += str + ", ";
                    }
                    tmp += "]";
                    rgSetFilt.setText(tmp);
                    rgSetFilt.setVisibility(View.VISIBLE);
                }
                else rgSetFilt.setVisibility(View.GONE);
            }
        }
    }
}