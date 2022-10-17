package com.example.sharemarketlearningapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    private LinearLayout llData;
    private String name;
    private FirebaseFirestore firebaseFirestore;

    private void initialize() {
        llData = findViewById(R.id.llData);

        name = getIntent().getExtras().getString("name");

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        initialize();

        firebaseFirestore.collection(name)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String title = documentChange.getDocument().getData().get("title").toString();

                            addData(title);
                        }
                    }
                });

    }

    private void addData(String title) {
        View view = getLayoutInflater().inflate(R.layout.data_card_layout, null, false);

        TextView tvTitle = view.findViewById(R.id.tvTitle);

        tvTitle.setText(title);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataActivity.this, DataDisplayActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        llData.addView(view);
    }

}