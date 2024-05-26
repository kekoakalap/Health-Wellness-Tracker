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

    private EditText ownerMedicalHistoryEditText;
    private EditText petMedicalHistoryEditText;
    private Button submitMedicalHistoryButton;
    private TextView ownerMedicalHistoryTextView;
    private TextView petMedicalHistoryTextView;

    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        ownerMedicalHistoryEditText = findViewById(R.id.ownerMedicalHistoryEditText);
        petMedicalHistoryEditText = findViewById(R.id.petMedicalHistoryEditText);
        submitMedicalHistoryButton = findViewById(R.id.submitMedicalHistoryButton);
        ownerMedicalHistoryTextView = findViewById(R.id.ownerMedicalHistoryTextView);
        petMedicalHistoryTextView = findViewById(R.id.petMedicalHistoryTextView);

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
        String ownerMedicalHistory = ownerMedicalHistoryEditText.getText().toString();
        String petMedicalHistory = petMedicalHistoryEditText.getText().toString();

        ownerMedicalHistoryTextView.setText("Owner's Medical History:\n" + ownerMedicalHistory);
        petMedicalHistoryTextView.setText("Pet's Medical History:\n" + petMedicalHistory);
    }

    private void saveToFirestore() {
        String ownerMedicalHistory = ownerMedicalHistoryEditText.getText().toString();
        String petMedicalHistory = petMedicalHistoryEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("ownerMedicalHistory", ownerMedicalHistory);
        data.put("petMedicalHistory", petMedicalHistory);

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
