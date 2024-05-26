package com.example.appproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class WeightExerciseTrackingActivity extends AppCompatActivity {

    private EditText ownerCurrentWeightEditText;
    private EditText petCurrentWeightEditText;
    private EditText ownerExerciseRoutineEditText;
    private EditText petExerciseRoutineEditText;
    private TextView ownerWeightExerciseTextView;
    private TextView petWeightExerciseTextView;

    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_exercise_tracking);

        ownerCurrentWeightEditText = findViewById(R.id.ownerCurrentWeightEditText);
        petCurrentWeightEditText = findViewById(R.id.petCurrentWeightEditText);
        ownerExerciseRoutineEditText = findViewById(R.id.ownerExerciseRoutineEditText);
        petExerciseRoutineEditText = findViewById(R.id.petExerciseRoutineEditText);
        ownerWeightExerciseTextView = findViewById(R.id.ownerWeightExerciseTextView);
        petWeightExerciseTextView = findViewById(R.id.petWeightExerciseTextView);

        Button submitWeightExerciseButton = findViewById(R.id.submitWeightExerciseButton);
        submitWeightExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWeightExerciseInfo();
                saveToFirestore();
            }
        });
    }

    private void updateWeightExerciseInfo() {
        String ownerWeightExerciseInfo = "Owner's Current Weight: " + ownerCurrentWeightEditText.getText().toString() +
                "\nOwner's Exercise Routine: " + ownerExerciseRoutineEditText.getText().toString();
        ownerWeightExerciseTextView.setText(ownerWeightExerciseInfo);

        String petWeightExerciseInfo = "Pet's Current Weight: " + petCurrentWeightEditText.getText().toString() +
                "\nPet's Exercise Routine: " + petExerciseRoutineEditText.getText().toString();
        petWeightExerciseTextView.setText(petWeightExerciseInfo);
    }

    private void saveToFirestore() {
        String ownerCurrentWeight = ownerCurrentWeightEditText.getText().toString();
        String petCurrentWeight = petCurrentWeightEditText.getText().toString();
        String ownerExerciseRoutine = ownerExerciseRoutineEditText.getText().toString();
        String petExerciseRoutine = petExerciseRoutineEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("ownerCurrentWeight", ownerCurrentWeight);
        data.put("petCurrentWeight", petCurrentWeight);
        data.put("ownerExerciseRoutine", ownerExerciseRoutine);
        data.put("petExerciseRoutine", petExerciseRoutine);

        db.collection("weightExercise")
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
