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

public class DataActivity extends AppCompatActivity {

    private LinearLayout llData;
    private String name;
    private FirebaseFirestore firebaseFirestore;
    private TextView tvTitle;
    private String tool_title = "";

    private void initialize() {
        llData = findViewById(R.id.llData);

        name = getIntent().getExtras().getString("name");

        firebaseFirestore = FirebaseFirestore.getInstance();

        tvTitle = findViewById(R.id.tvTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        initialize();

        String[] arrTitle = name.split("_", 2);

        for (String i : arrTitle) {
            tool_title = tool_title + i.substring(0, 1).toUpperCase() + i.substring(1).toLowerCase() + " ";
        }

        tvTitle.setText("Invest Mate - " + tool_title.trim());

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