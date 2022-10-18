package com.example.sharemarketlearningapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


public class StockActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TableLayout tlData;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        tvTitle = findViewById(R.id.tvTitle);
        tlData = findViewById(R.id.tlData);

        firebaseFirestore = FirebaseFirestore.getInstance();

        tvTitle.setText("Invest Mate - Stock Exchange");

        firebaseFirestore.collection("stock_exchange")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String stock_exchange = documentChange.getDocument().getData().get("stock_exchange").toString();
                            String short_name = documentChange.getDocument().getData().get("short_name").toString();
                            String country = documentChange.getDocument().getData().get("country").toString();
                            addMarket(stock_exchange.trim() , short_name.trim(), country.trim());
                        }
                    }
                });


    }

    private void addMarket(String stock_exchange, String short_name, String country) {
        View view = getLayoutInflater().inflate(R.layout.stock_exchange_table_row_layout, null, false);

        TextView tvStockExchange = view.findViewById(R.id.tvStockExchange);
        TextView tvShortName = view.findViewById(R.id.tvShortName);
        TextView tvCountry = view.findViewById(R.id.tvCountry);

        tvStockExchange.setText(stock_exchange);
        tvShortName.setText(short_name);
        tvCountry.setText(country);

        tlData.addView(view);

    }
}