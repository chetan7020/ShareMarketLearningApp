package com.example.sharemarketlearningapp;

import android.os.Bundle;
import android.util.Log;
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

public class WarrenBuffetActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private LinearLayout llData;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warren_buffet);

        firebaseFirestore = FirebaseFirestore.getInstance();
        llData = findViewById(R.id.llData);
        tvTitle = findViewById(R.id.tvTitle);

        tvTitle.setText("Invest Mate - W. Buffet Quotes");

        firebaseFirestore.collection("warren_buffet")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String quote = documentChange.getDocument().getData().get("quote").toString();
                            addQuote(quote.trim());

                            Log.d("tag" , quote);
                        }
                    }
                });
    }

    private void addQuote(String quote) {
        View view = getLayoutInflater().inflate(R.layout.buffet_quote_layout, null, false);

        TextView tvQuote = view.findViewById(R.id.tvQuote);

        tvQuote.setText(quote);

        llData.addView(view);
    }
}