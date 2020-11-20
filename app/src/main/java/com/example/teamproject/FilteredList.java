package com.example.teamproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import List.ListViewAdaptor;
import List.Scholar;

// ***** 전체_이소영, 캘린더 연동_이연주 *****
public class FilteredList extends AppCompatActivity {
    private filterLst FL;   // MainActivity에서 받아오는 객체
    private Scholar selectedItem;   // 리스트뷰에서 선택된 아이템 객체
    private String link = null;   // 선택된 Scholar 객체의 링크를 저장
    private String toSaveDate = null;   // 선택된 Scholar 객체의 endDate를 저장
    private String toSaveTitle = null;   // 선택된 Scholar 객체의 title 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filteredlist);
        setTitle("FilteredListActivity");

        final Intent getIntent = getIntent();
        FL = (filterLst)getIntent.getSerializableExtra("FilteredList");

        // 필터링을 위해 FL에서 받아온 값들 저장
        final ArrayList<String> tFLst = FL.getTuitionFeeLst();
        final String iC = FL.getIncome();
        final String rG = FL.getRegion();
        final ArrayList<String> spanLst = FL.getSpanLst();

        // 파이어스토어 DB 연결
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        // activity_filteredlist에서 뷰 객체 얻어오기
        final Button linkBtn = (Button) findViewById(R.id.goLink);
        final Button backBtn = (Button) findViewById(R.id.backMain);
        final Button calBt = (Button)findViewById(R.id.upcalendar);

        // ListView를 Custom Adaptor 객체에 연결
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final ListViewAdaptor mAdaptor = new ListViewAdaptor(FilteredList.this);
        listView.setAdapter(mAdaptor);

        // ListView 아이템 클릭 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View last;   // 마지막으로 선택된 객체 저장
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 선택된 뷰가 달라지면 이전에 선택되었던 뷰의 배경색 변경
                if (last != null) last.setBackgroundColor(Color.WHITE);

                // 선택된 뷰의 배경색 변경
                view.setBackgroundColor(Color.parseColor("#cfe4f4"));

                // 선택된 뷰의 객체 받아와서 link, title, endDate 저장
                selectedItem = mAdaptor.getItem(i);
                link = mAdaptor.getItem(i).getLink();
                toSaveDate = mAdaptor.getItem(i).getEndDate();
                toSaveTitle = mAdaptor.getItem(i).getTitle();

                // last 업데이트
                last = view;
            }
        });

        // 링크 연결 버튼 클릭 이벤트 처리
        linkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 선택된 뷰가 없는 경우
                if (link == null){
                    Toast.makeText(getApplicationContext(), "장학금을 선택하세요!", Toast.LENGTH_SHORT).show();
                }
                // 선택된 뷰가 있는 경우
                else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                }
            }
        });

        // 돌아가기 버튼 클릭 이벤트 처리
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, getIntent);
                finish();
            }
        });

        // Firestore에서 선택된 조건에 맞춰 장학금 검색 구현
        Query q = null;

        // 등록금 내, 외 선택 정보 받아와서 쿼리문 작성
        if (tFLst.size() > 0){   // 등록금 내, 외 선택 X => 쿼리문 붙이지 않음
            // 쿼리가 null인 경우 데이터베이스 컬렉션 참조 필요
            if (q == null) q = db.collection("Scholarship").whereIn("tuitionFee", tFLst);
            else q = q.whereIn("tuitionFee", tFLst);
        }
        // 소득 분위 선택 정보 받아와서 쿼리문 작성
        if (!iC.equals("")){
            if (q == null) q = db.collection("Scholarship").whereEqualTo("income", iC);
            else q = q.whereEqualTo("income", iC);
        }
        // 지역 선택 정보 받아와서 쿼리문 작성
        if (!rG.equals("")){
            if (q == null) q = db.collection("Scholarship").whereEqualTo("region", rG);
            else q = q.whereEqualTo("income", rG);
        }

        // 쿼리문 실행
        // 쿼리가 null인 경우 데이터베이스 컬렉션 참조 필요
        if (q == null) q = db.collection(("Scholarship"));
        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task){
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        // 조회된 문서를 Scholar 클래스 객체로 변환
                        Scholar tmp = document.toObject(Scholar.class);
                        // 선택한 기간 정보에 따라 필터링
                        // => whereGreaterThanOrEqualTo()와 whereLessThanOrEqualTo()는 같은 필드에만 사용해야함
                        // => if문을 이용해 필터링
                        if (spanLst.size() > 0){
                            if (dateToInt(tmp.getStartDate()) >= dateToInt(spanLst.get(0)) && dateToInt(tmp.getEndDate()) <= dateToInt(spanLst.get(1))){
                                mAdaptor.addItem(tmp);
                            }
                        }else{   // 기간 선택하지 않은 경우
                            mAdaptor.addItem(tmp);
                        }
                    }
                    // 선택한 조건에 해당하는 장학금이 없는 경우 경고창 출력 후 기본 페이지로 이동
                    if (mAdaptor.isEmpty()){
                        AlertDialog.Builder noScholar = new AlertDialog.Builder(FilteredList.this);
                        noScholar.setMessage("해당되는 장학금이 없습니다!");
                        noScholar.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(RESULT_OK, getIntent);
                                finish();
                            }
                        });
                        noScholar.create().show();
                    }
                }else {
                    Log.w("", "Error getting documents.", task.getException());
                }
            }
        });

        //일정추가 버튼 클릭시_이연주
        calBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 장학금 선택하지 않고 캘린더에 저장 버튼 누른 경우 처리_이소영
                if (toSaveTitle == null || toSaveDate == null){
                    Toast.makeText(getApplicationContext(), "장학금을 선택하세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //DB에서 년/월/일 받아서 할당
                int year = Integer.parseInt(toSaveDate.substring(0, 4));
                int month = Integer.parseInt(toSaveDate.substring(5, 7)) - 1;
                int day = Integer.parseInt(toSaveDate.substring(8, 10));

                //Calendar 가져오기
                Calendar calAppointment = Calendar.getInstance();

                //Calendar 에 년/월/일 set
                calAppointment.set(Calendar.YEAR, year);
                calAppointment.set(Calendar.MONTH, month);
                calAppointment.set(Calendar.DAY_OF_MONTH, day);
                calAppointment.set(Calendar.HOUR_OF_DAY, 12);
                calAppointment.set(Calendar.MINUTE, 30);

                //구글 캘린더 연동
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calAppointment.getTime().getTime()); //시간 설정
                intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, "Your Timezone");
                intent.putExtra(CalendarContract.Events.TITLE, toSaveTitle); //타이틀 == 장학금 명
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "Your description");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Your location");
                if (intent.resolveActivityInfo(getApplicationContext().getPackageManager(), 0) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    // String인 startDate, endDate를 int로 변환하는 함수
    // 기간 비교하여 필터링하기위해
    public int dateToInt(String t){
        return Integer.parseInt(t.replace("-", ""));
    }
}