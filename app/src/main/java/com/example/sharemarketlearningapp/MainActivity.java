package com.example.sharemarketlearningapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CardView cvBasic, cvGlossary, cvAnalysis, cvRisk, cvDerivative, cvMutualFund, cvMultibagger, cvIPO, cvStockFormula, cvForexTrading,
            cvStockBroker, cvPrimaryMarket, cvCommodity, cvStockExchange;
    private FirebaseFirestore firebaseFirestore;
    private TextView tvTitle;
    private ImageView ivZerodha, ivUpstox, ivWarrenBuffet;

    private void initialize() {
        cvBasic = findViewById(R.id.cvBasic);
        cvGlossary = findViewById(R.id.cvGlossary);
        cvAnalysis = findViewById(R.id.cvAnalysis);
        cvRisk = findViewById(R.id.cvRisk);
        cvDerivative = findViewById(R.id.cvDerivative);
        cvMutualFund = findViewById(R.id.cvMutualFund);
        cvMultibagger = findViewById(R.id.cvMultibagger);
        cvIPO = findViewById(R.id.cvIPO);
        cvStockFormula = findViewById(R.id.cvStockFormula);
        cvForexTrading = findViewById(R.id.cvForexTrading);
        cvStockBroker = findViewById(R.id.cvStockBroker);
        cvPrimaryMarket = findViewById(R.id.cvPrimaryMarket);
        cvCommodity = findViewById(R.id.cvCommodity);
        cvStockExchange = findViewById(R.id.cvStockExchange);

        tvTitle = findViewById(R.id.tvTitle);

        ivZerodha = findViewById(R.id.ivZerodha);
        ivUpstox = findViewById(R.id.ivUpstox);
        ivWarrenBuffet = findViewById(R.id.ivWarrenBuffet);

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        tvTitle.setText("Invest Mate");

        ivZerodha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://signup.zerodha.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ivUpstox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://login.upstox.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ivWarrenBuffet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , WarrenBuffetActivity.class));
            }
        });

        cvGlossary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "glossary");
                startActivity(intent);
            }
        });

        cvAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "analysis");
                startActivity(intent);
            }
        });

        cvRisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "stock_risk");
                startActivity(intent);
            }
        });

        cvDerivative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "derivative");
                startActivity(intent);
            }
        });

        cvMutualFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "mutual_fund");
                startActivity(intent);
            }
        });

        cvMultibagger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "multi_bagger");
                startActivity(intent);
            }
        });

        cvIPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "ipo");
                startActivity(intent);
            }
        });

        cvStockFormula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "stock_formula");
                startActivity(intent);
            }
        });

        cvForexTrading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "forex_trading");
                startActivity(intent);
            }
        });

        cvStockBroker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "stock_broker");
                startActivity(intent);
            }
        });

        cvPrimaryMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "primary_market");
                startActivity(intent);
            }
        });

        cvCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "commodity");
                startActivity(intent);
            }
        });

        cvStockExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StockActivity.class));
            }
        });

        cvBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("name", "basic");
                startActivity(intent);
            }
        });

    }

}