package com.example.teamproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Filter extends AppCompatActivity {
    int flag = 0;

    Calendar calendar = Calendar.getInstance(); //calendar _ 이연주

    DatePickerDialog.OnDateSetListener datePick = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth); //년, 월, 일 set
            update(); //Text set
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        //검색조건의 체크 박스 _ 이연주
        final CheckBox tuitionInCkBox = (CheckBox)findViewById(R.id.tuitionInCk);
        final CheckBox tuitionOutCkBox = (CheckBox)findViewById(R.id.tuitionOutCK);
        final CheckBox tuitionInandOutCkBox = (CheckBox)findViewById(R.id.tuitionInandOutCk); //tuition in, out, in+out _ 이연주
        final CheckBox incomeCkBox = (CheckBox)findViewById(R.id.incomeCk); //income _ 이연주
        final CheckBox regionCkBox = (CheckBox)findViewById(R.id.regionCk); //region _ 이연주
        final CheckBox spanCkBox = (CheckBox)findViewById(R.id.spanCk); //span _ 이연주

        //income, region을 Spinner로 선택 _ 이연주
        Spinner incomeSpinner = (Spinner)findViewById(R.id.incomeSpin);
        Spinner regionSpinner = (Spinner)findViewById(R.id.regionSpin);

        //레이아웃에 띄운 TextView에 대한 TextView _ 이연주
        final TextView tuitioninTxtView = (TextView)findViewById(R.id.tuitionInTxt);
        final TextView tuitionOutTxtView = (TextView)findViewById(R.id.tuitionOutTxt);
        final TextView tuitionInandOutTxtView = (TextView)findViewById(R.id.tuitionInandOutTxt); //tuition in, out, in+out _ 이연주
        final TextView incomeTxtView = (TextView)findViewById(R.id.incomeTxt); //income _ 이연주
        final TextView regionTxtView = (TextView)findViewById(R.id.regionTxt); //span _ 이연주

        //시작, 종료날짜를 설정하기 위한 EditText _ 이연주
        final EditText spanStartTxt = (EditText)findViewById(R.id.startDate); //시작일 _ 이연주
        final EditText spanTillTxt = (EditText)findViewById(R.id.tillDate); //종료일 _ 이연주

        //버튼 (전체선택, 취소, 검색조건 선택) _ 이연주
        Button allCkButton = (Button)findViewById(R.id.allCkBoxBT);
        Button cancelButton = (Button)findViewById(R.id.cancelBT);
        Button setFilterButton = (Button)findViewById(R.id.setFilteringBT);

        //Data를 받아오고 돌려주기 위한 인텐트 _ 이연주
        Intent getIntent = getIntent();
        final Intent backIntent = new Intent();

        filterLst FL = new filterLst();

        //데이터 받아옴 _ 이연주
        FL = (filterLst)getIntent.getSerializableExtra("filterLst");

        //income Spinner에 대한 이벤트 처리 _ 이연주
        incomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeTxtView.setText("" + parent.getItemAtPosition(position)); //선택한 String으로 Text를 설정 _ 이연주
                //String을 선택하지 않으면 checkbox는 false로, 선택하면 true로 _ 이연주
                if (incomeTxtView.getText().equals("선택안함")) {incomeCkBox.setChecked(false);}
                else incomeCkBox.setChecked(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){ //선택하지 않은 default
                incomeTxtView.setText("선택안함");
            }
        });

        //region Spinner에 대한 이벤트 처리 _ 이연주
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                regionTxtView.setText("" + parent.getItemAtPosition(position)); //선택한 String으로 Text를 설정 _ 이연주
                //String을 선택하지 않으면 checkbox는 false로, 선택하면 true로 _ 이연주
                if (regionTxtView.getText().equals("전체")) regionCkBox.setChecked(false);
                else regionCkBox.setChecked(true);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){ //선택하지 않은 default
                regionTxtView.setText("전체");
            }
        });

        //전체선택 버튼에 대한 이벤트 처리 _ 이연주
        allCkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //체크박스가 true이면 false로, false이면 true로
                if (tuitionInCkBox.isChecked() && tuitionOutCkBox.isChecked() && tuitionInandOutCkBox.isChecked() && incomeCkBox.isChecked() && regionCkBox.isChecked() && spanCkBox.isChecked()) {
                    tuitionInCkBox.setChecked(false);
                    tuitionOutCkBox.setChecked(false);
                    tuitionInandOutCkBox.setChecked(false);
                    incomeCkBox.setChecked(false);
                    regionCkBox.setChecked(false);
                    spanCkBox.setChecked(false);
                }
                else {
                    tuitionInCkBox.setChecked(true);
                    tuitionOutCkBox.setChecked(true);
                    tuitionInandOutCkBox.setChecked(true);
                    incomeCkBox.setChecked(true);
                    regionCkBox.setChecked(true);
                    spanCkBox.setChecked(true);
                }
            }
        });

        final filterLst finalFL = FL;
        //취소 버튼을 클릭하였을 때 _ 이연주
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backIntent.putExtra("backFilterLst", finalFL);
                setResult(RESULT_OK, backIntent);
                finish(); //FL을 그대로 돌려주고 액티비티 종료 _ 이연주
            }
        });

        //검색조건을 선택하는 버튼을 클릭하였을 때 _ 이연주
        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //filterLst객체에 set하기 위해 만들어진 Lst, String _ 이연주
                ArrayList<String> tFLst = new ArrayList<String>(); //tuition _ 이연주
                String[] iC = {""}; //income _ 이연주
                String[] rG = {""}; //region _ 이연주
                ArrayList<String> sPLst = new ArrayList<>(); //span _ 이연주

                tFLst = new ArrayList<String>();
                //Ck된 조건들만 담아서 FL에 설정한다. _ 이연주
                if (tuitionInCkBox.isChecked()) {tFLst.add(tuitioninTxtView.getText().toString());} //tuition in _ 이연주
                if (tuitionOutCkBox.isChecked()) {tFLst.add(tuitionOutTxtView.getText().toString());} //tuition out _ 이연주
                if (tuitionInandOutCkBox.isChecked()) {tFLst.add(tuitionInandOutTxtView.getText().toString());} //tuition in + out _ 이연주
                if (incomeCkBox.isChecked()) { //income _ 이연주
                    if (incomeTxtView.getText().toString().equals("선택안함")){ //CkBox는 되어있지만 선택되지 않았을 때_ 이연주
                        AlertDialog.Builder noOpBand = new AlertDialog.Builder(Filter.this);
                        noOpBand.setMessage("소득분위를 선택해주세요!");
                        noOpBand.setPositiveButton("확인", null);
                        noOpBand.create().show();
                        return;
                    }
                    iC[0] = incomeTxtView.getText().toString();}
                if (regionCkBox.isChecked()) { //region _ 이연주
                    if (regionTxtView.getText().toString().equals("전체")){ //CkBox는 되어있지만 선택되지 않았을 때
                        AlertDialog.Builder noOpBand = new AlertDialog.Builder(Filter.this);
                        noOpBand.setMessage("지역을 선택해주세요!");
                        noOpBand.setPositiveButton("확인", null);
                        noOpBand.create().show();
                        return;
                    }
                    rG[0] = regionTxtView.getText().toString();}
                if (spanCkBox.isChecked()) { //span _ 이연주
                    if (spanStartTxt.getText().toString().substring(0, 2).equals("ex") || spanTillTxt.getText().toString().substring(0, 2).equals("ex")){ //기간이 설정되어 있지 않으면 경고문을 띄움 _ 이연주
                        AlertDialog.Builder noOpBand = new AlertDialog.Builder(Filter.this);
                        noOpBand.setMessage("기간을 선택해주세요!");
                        noOpBand.setPositiveButton("확인", null);
                        noOpBand.create().show();
                        return;
                    }
                    try {
                        //시작일과 종료일이 잘못되어있으면 경고문 띄우기 _ 이연주
                        if (ckSpanisOK(spanStartTxt.getText().toString(), spanTillTxt.getText().toString())){
                            AlertDialog.Builder noOpBand = new AlertDialog.Builder(Filter.this);
                            noOpBand.setMessage("기간을 다시 설정해주세요!");
                            noOpBand.setPositiveButton("확인", null);
                            noOpBand.create().show();
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    sPLst.add(spanStartTxt.getText().toString());
                    sPLst.add(spanTillTxt.getText().toString());}

                //filterLst에 대한 Data를 모두 set해주고 액티비티 종료 _ 이연주
                finalFL.setTuitionFeeLst(tFLst);
                finalFL.setIncome(iC[0]);
                finalFL.setRegion(rG[0]);
                finalFL.setSpanLst(sPLst);
                backIntent.putExtra("backFilterLst", finalFL);
                setResult(RESULT_OK, backIntent);
                finish();
            }
        });

        spanStartTxt.setInputType(0);
        spanTillTxt.setInputType(0);
        //시작일 TextEdit을 클릭하였을 때 _ 이연주
        spanStartTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1; //flag 를 1 로 설정 _ 이연주
                new DatePickerDialog(Filter.this, R.style.DatePickerTheme, datePick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(); //달력을 띄우고 Text설정 _ 이연주
            }
        });
        //종료일 TextEdit을 클릭하였을 때 _ 이연주
        spanTillTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 2; //flag 를 2 로 설정 _ 이연주
                new DatePickerDialog(Filter.this, R.style.DatePickerTheme, datePick, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(); //달력을 띄우고 Text설정 _ 이연주
            }

        });
    }
    private void update(){ //TextEdit의 Text를 set해주는 Method _ 이연주
        String myFormat = "yyyy-MM-dd";    // 출력형식   2018-11-28 _ 이연주
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        if (flag == 1){ //시작일 _ 이연주
            EditText tmp = (EditText)findViewById(R.id.startDate);
            tmp.setText(sdf.format(calendar.getTime()));
        }
        else if (flag == 2){ //종료일 _ 이연주
            EditText tmp = (EditText)findViewById(R.id.tillDate);
            tmp.setText(sdf.format(calendar.getTime()));
        }
    }

    //시작일과 종료일에 대해서 체크하는 함수 _ 이연주
    public boolean ckSpanisOK(String start, String till) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date stDate = sdf.parse(start);
        Date tiDate = sdf.parse(till);

        if (stDate.after(tiDate)) return true; //시작일이 종료일보다 이후에 있으면 true 반환 _ 이연주
        return false;
    }
}