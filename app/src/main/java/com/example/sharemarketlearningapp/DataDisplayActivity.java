package com.example.sharemarketlearningapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DataDisplayActivity extends AppCompatActivity {

    private LinearLayout llData;
    private String title, name;
    private FirebaseFirestore firebaseFirestore;
    private FloatingActionButton fabLeft, fabRight;
    private ArrayList<String> alTitle;

    private void initialize() {
        llData = findViewById(R.id.llData);

        title = getIntent().getExtras().getString("title");
        name = getIntent().getExtras().getString("name");

        fabLeft = findViewById(R.id.fabLeft);
        fabRight = findViewById(R.id.fabRight);

        alTitle = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        initialize();

        firebaseFirestore.collection(name)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        for (DocumentChange documentChange : value.getDocumentChanges()) {
//                            alTitle.add(documentChange.getDocument().getData().get("title").toString());
//                        }
                    }
                });

        Log.d("tag" , String.valueOf(alTitle.size()));

        fabLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llData.removeView(llData.getChildAt(0));
            }
        });

        fabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llData.removeView(llData.getChildAt(0));
            }
        });

        firebaseFirestore.collection(name)
                .whereEqualTo("title", title)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String title = documentChange.getDocument().getData().get("title").toString();
                            String des = documentChange.getDocument().getData().get("des").toString();
                            addData(title, des);
                        }
                    }
                });

    }

    private void addData(String title, String des) {
        View view = getLayoutInflater().inflate(R.layout.data_card_content_layout, null, false);

        TextView tvTitle, tvDes;

        tvTitle = view.findViewById(R.id.tvTitle);
        tvDes = view.findViewById(R.id.tvDes);

        tvTitle.setText(title);
        String des_new = des.replace("\\n", "\n");
        tvDes.setText(des_new);

        llData.addView(view);
    }

}