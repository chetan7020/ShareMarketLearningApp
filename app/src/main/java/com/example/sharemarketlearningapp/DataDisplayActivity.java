package com.example.sharemarketlearningapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataDisplayActivity extends AppCompatActivity {

    private LinearLayout llData;
    private String title, name;
    private FirebaseFirestore firebaseFirestore;
    private FloatingActionButton fabLeft, fabRight;
    private ArrayList<String> alTitle;
    private TextView tvTitle;
    private String tool_title = "";
    private CircularProgressIndicator pbLoader;
    int total = 0;

    private void initialize() {
        llData = findViewById(R.id.llData);

        title = getIntent().getExtras().getString("title");
        name = getIntent().getExtras().getString("name");

        fabLeft = findViewById(R.id.fabLeft);
        fabRight = findViewById(R.id.fabRight);

        tvTitle = findViewById(R.id.tvTitle);

        pbLoader = findViewById(R.id.pbLoader);

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        initialize();

        firebaseFirestore.collection(name).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                total = value.size();
            }
        });

        String[] arrTitle = name.split("_", 2);

        for (String i : arrTitle) {
            tool_title = tool_title + i.substring(0, 1).toUpperCase() + i.substring(1).toLowerCase() + " ";
        }

        tvTitle.setText("Invest Mate - " + tool_title.trim());
        fabLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llData.removeView(llData.getChildAt(0));

                firebaseFirestore.collection("position")
                        .document("right_left")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String title_left = String.valueOf(documentSnapshot.get("left")).trim();

                                firebaseFirestore.collection(name)
                                        .whereEqualTo("title", title_left)
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
                        });
                firebaseFirestore.collection(name).whereEqualTo("title", title)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                llData.removeView(llData.getChildAt(0));
                                for (DocumentChange documentChange : value.getDocumentChanges()) {
                                    int id = Integer.parseInt(documentChange.getDocument().getData().get("id").toString());

                                    if (id == 1 || id == total){
                                        if (id == 1){
                                            id = total ;
                                        }
                                    }
                                }
                            }
                        });

            }
        });

        fabRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llData.removeView(llData.getChildAt(0));

                firebaseFirestore.collection("position")
                        .document("right_left")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                String title_right = String.valueOf(documentSnapshot.get("right")).trim();

                                firebaseFirestore.collection(name)
                                        .whereEqualTo("title", title_right)
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
                        });

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

        firebaseFirestore.collection(name)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        alTitle = new ArrayList<>();

                        String left;
                        String right;

                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            alTitle.add(documentChange.getDocument().getData().get("title").toString().trim());
                        }

                        if (alTitle.indexOf(title.trim()) == 0) {
                            left = alTitle.get(alTitle.size() - 1);
                            right = alTitle.get(alTitle.indexOf(title.trim()) + 1);
                        } else if (alTitle.indexOf(title.trim()) == alTitle.size() - 1) {
                            left = alTitle.get(alTitle.indexOf(title.trim()) - 1);
                            right = alTitle.get(0);
                        } else {
                            left = alTitle.get(alTitle.indexOf(title.trim()) - 1);
                            right = alTitle.get(alTitle.indexOf(title.trim()) + 1);
                        }

                        Map<String, Object> data = new HashMap<>();

                        data.put("left", left);
                        data.put("right", right);

                        firebaseFirestore
                                .collection("position")
                                .document("right_left")
                                .set(data);

                    }
                });

    }

}