package com.example.teamproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import List.ListViewAdaptor;
import List.Scholar;

public class FilteredList extends AppCompatActivity {
    private filterLst FL;
    private Scholar selectedItem;
    private String link = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filteredList);
        setTitle("SelectedActivity");

        final Intent getIntent = getIntent();
        FL = (filterLst)getIntent.getSerializableExtra("selectedLst");

        final ArrayList<String> tFLst = FL.getTuitionFeeLst();
        final String iC = FL.getIncome();
        final String rG = FL.getRegion();
        final String TAG = "TestFB";
        final ArrayList<String> spanLst = FL.getSpanLst();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final Button linkBtn = (Button) findViewById(R.id.goLink);
        final Button backBtn = (Button) findViewById(R.id.backMain);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        final ListViewAdaptor mAdaptor = new ListViewAdaptor(FilteredList.this);
        listView.setAdapter(mAdaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View last;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (last != null) last.setBackgroundColor(Color.WHITE);
                view.setBackgroundColor(Color.parseColor("#FFDEDE"));
                selectedItem = mAdaptor.getItem(i);
                link = mAdaptor.getItem(i).getLink();
                Log.d("Link", link);
                last = view;
            }
        });

        linkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (link == null){
                    Toast.makeText(getApplicationContext(), "장학금을 선택하세요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, getIntent);
                finish();
            }
        });
        Query q = null;
        if (tFLst.size() > 0){
            if (q == null) q = db.collection("Scholarship").whereIn("tuitionFee", tFLst);
            else q = q.whereIn("tuitionFee", tFLst);
        }
        if (!iC.equals("")){
            if (q == null) q = db.collection("Scholarship").whereEqualTo("income", iC);
            else q = q.whereEqualTo("income", iC);
        }
        if (!rG.equals("")){
            if (q == null) q = db.collection("Scholarship").whereEqualTo("region", rG);
            else q = q.whereEqualTo("income", rG);
        }

        if (q == null) q = db.collection(("Scholarship"));
        q.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task){
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Scholar tmp = document.toObject(Scholar.class);
                        if (spanLst.size() > 0){
                            if (dateToInt(tmp.getStartDate()) >= dateToInt(spanLst.get(0)) && dateToInt(tmp.getEndDate()) <= dateToInt(spanLst.get(1))){
                                mAdaptor.addItem(tmp);
                                Log.d(TAG, tmp.getIncome() + ", " + tmp.getTuitionFee() + ", " + tmp.getRegion());
                            }
                        }else{
                            mAdaptor.addItem(tmp);
                            Log.d(TAG, tmp.getIncome() + ", " + tmp.getTuitionFee() + ", " + tmp.getRegion());
                        }
                    }
                }else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        if (mAdaptor.getCount() == 0){
            AlertDialog.Builder noScholar = new AlertDialog.Builder(this);
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
    }
    public int dateToInt(String t){
        return Integer.parseInt(t.replace("-", ""));
    }
}