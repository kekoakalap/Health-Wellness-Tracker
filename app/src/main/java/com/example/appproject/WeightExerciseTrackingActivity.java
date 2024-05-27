package com.example.appproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import android.util.Log;

public class WeightExerciseTrackingActivity extends AppCompatActivity {

    private EditText currentWeightEditText;
    private EditText exerciseRoutineEditText;
    private Button submitWeightExerciseButton;
    private TextView weightExerciseTextView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_exercise_tracking);

        currentWeightEditText = findViewById(R.id.currentWeightEditText);
        exerciseRoutineEditText = findViewById(R.id.exerciseRoutineEditText);
        weightExerciseTextView = findViewById(R.id.weightExerciseTextView);
        submitWeightExerciseButton = findViewById(R.id.submitWeightExerciseButton);

        setupSubmitWeightExerciseButton();
    }

    private void setupSubmitWeightExerciseButton() {
        submitWeightExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeightExerciseInfo();
                saveToFirestore();
            }
        });
    }

    private void updateWeightExerciseInfo() {
        String weightExerciseInfo = "Current Weight: " + currentWeightEditText.getText().toString() +
                "\nExercise Routine: " + exerciseRoutineEditText.getText().toString();
        weightExerciseTextView.setText(weightExerciseInfo);
    }

    private void saveToFirestore() {
        String currentWeight = currentWeightEditText.getText().toString();
        String exerciseRoutine = exerciseRoutineEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("currentWeight", currentWeight);
        data.put("exerciseRoutine", exerciseRoutine);

        db.collection("weightExercise")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(WeightExerciseTrackingActivity.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error adding document", e);
                        Toast.makeText(WeightExerciseTrackingActivity.this, "Error submitting data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
