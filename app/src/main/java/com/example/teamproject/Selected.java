package com.example.teamproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

public class Selected extends AppCompatActivity {
    filterLst FL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected);
        setTitle("SelectedActivity");

        Intent getIntent = getIntent();
        FL = (filterLst)getIntent.getSerializableExtra("selectedLst");

        final ArrayList<String> tFLst = FL.getTuitionFeeLst();
        final String iC = FL.getIncome();
        final String rG = FL.getRegion();
        final String TAG = "TestFB";
        final ArrayList<String> spanLst = FL.getSpanLst();

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ListView listView = (android.widget.ListView) findViewById(R.id.listView);
        final ListViewAdaptor mAdaptor = new ListViewAdaptor(Selected.this);
        listView.setAdapter(mAdaptor);

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
    }
    public int dateToInt(String t){
        return Integer.parseInt(t.replace("-", ""));
    }
}