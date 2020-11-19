package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    filterLst FL = new filterLst(); //DB에 넘길 Data들을 담은 객체
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button filterBt = (Button)findViewById(R.id.filterBT); //검색조건 찾는 액티비티로 넘어가는 버튼
        Button searchBt = (Button)findViewById(R.id.searchBT); //필터링하여 검색하는 액티비티로 넘어가는 버튼

        filterBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterIntent = new Intent(getApplicationContext(), Filter.class);
                filterIntent.putExtra("filterLst", FL);
                startActivityForResult(filterIntent, 0);//액티비티로 넘어감
            }
        });

        // 검색 버튼 클릭에 대한 이벤트 리스너
       searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectedIntent = new Intent(getApplicationContext(), Selected.class);
                selectedIntent.putExtra("selectedLst", FL);
                startActivityForResult(selectedIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) { //filterIntent에서의 결과를 처리
            if (resultCode == RESULT_OK) {
                String tmp = ""; //Txt를 설정하기 위한 temp변수
                TextView ttSetFilt = (TextView)findViewById(R.id.tuitionSetFilterTxt);
                TextView icSetFilt = (TextView)findViewById(R.id.incomeSetFilterTxt);
                TextView rgSetFilt = (TextView)findViewById(R.id.regionSetFilterTxt);
                TextView spSetFilt = (TextView)findViewById(R.id.spanSetFilterTxt); //필터링 조건들을 띄우기 위한 TextView

                FL = (filterLst)data.getSerializableExtra("backFilterLst"); //FL을 다시 할당
                ArrayList<String> tuitionfeeLst = FL.getTuitionFeeLst();
                String income = FL.getIncome();
                String region = FL.getRegion();
                ArrayList<String> spanLst = FL.getSpanLst(); //tuition, income, region, span을 담은 데이터를 get

                //tuition에 대한 정보를 TextView 로 띄움
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

                //income에 대한 정보를 TextView 로 띄움
                if (!income.equals("")) {
                    tmp = "[ 소득분위 구분 / 미구분 : " + income + "]";
                    icSetFilt.setText(tmp);
                    icSetFilt.setVisibility(View.VISIBLE);
                }
                else icSetFilt.setVisibility(View.GONE);

                //region에 대한 정보를 TextView 로 띄움
                if (!region.equals("")) {
                    tmp = "[ 지역 : " + region + "]";
                    rgSetFilt.setText(tmp);
                    rgSetFilt.setVisibility(View.VISIBLE);
                }
                else rgSetFilt.setVisibility(View.GONE);

                //span에 대한 정보를 TextView 로 띄움
                if (!spanLst.isEmpty()) {
                    Log.d("mainSpan", spanLst.get(0));
                    tmp = "[ 기간 : " + spanLst.get(0) + " ~ " + spanLst.get(1) + "]";
                    spSetFilt.setText(tmp);
                    spSetFilt.setVisibility(View.VISIBLE);
                }
                else spSetFilt.setVisibility(View.GONE);
            }
        }
    }
}