package com.example.appproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class MedicalHistoryActivity extends AppCompatActivity {

    private EditText MedicalHistoryEditText;
    private Button submitMedicalHistoryButton;
    private TextView MedicalHistoryTextView;


    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        MedicalHistoryEditText = findViewById(R.id.MedicalHistoryEditText);

        submitMedicalHistoryButton = findViewById(R.id.submitMedicalHistoryButton);
        MedicalHistoryTextView = findViewById(R.id.MedicalHistoryTextView);


        setupSubmitMedicalHistoryButton();
    }

    private void setupSubmitMedicalHistoryButton() {
        submitMedicalHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySubmittedMedicalHistory();
                saveToFirestore();
            }
        });
    }

    private void displaySubmittedMedicalHistory() {
        String MedicalHistory = MedicalHistoryEditText.getText().toString();


        MedicalHistoryTextView.setText("Medical History:\n" + MedicalHistory);

    }

    private void saveToFirestore() {
        String MedicalHistory = MedicalHistoryEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("MedicalHistory", MedicalHistory);

        db.collection("medicalHistories")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error adding document", e);
                    }
                });
    }
}
