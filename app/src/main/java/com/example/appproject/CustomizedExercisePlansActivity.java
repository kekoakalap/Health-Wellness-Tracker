package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomizedExercisePlansActivity extends AppCompatActivity {

    private Spinner ownerFitnessLevelSpinner;
    private Button generateExercisePlanButton;
    private ListView exercisePlanListView;

    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized_exercise_plans);

        ownerFitnessLevelSpinner = findViewById(R.id.ownerFitnessLevelSpinner);
        generateExercisePlanButton = findViewById(R.id.generateExercisePlanButton);
        exercisePlanListView = findViewById(R.id.exercisePlanListView);

        setupSpinner();
        setupGenerateExercisePlanButton();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.owner_fitness_levels, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ownerFitnessLevelSpinner.setAdapter(adapter);
    }

    private void setupGenerateExercisePlanButton() {
        generateExercisePlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAndDisplayExercisePlan();
            }
        });
    }

    private void generateAndDisplayExercisePlan() {
        String ownerFitnessLevel = ownerFitnessLevelSpinner.getSelectedItem().toString();

        List<String> exercisePlan = generateExercisePlanBasedOnPreferences(ownerFitnessLevel);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, exercisePlan);
        exercisePlanListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        saveToFirestore(ownerFitnessLevel, exercisePlan);
    }

    private List<String> generateExercisePlanBasedOnPreferences(String ownerFitnessLevel) {
        List<String> exercisePlan = new ArrayList<>();
        switch (ownerFitnessLevel) {
            case "Beginner":
                exercisePlan.add("Walk with your pet for 30 minutes and Stretch with your pet for 10 minutes");
                break;
            case "Intermediate":
                exercisePlan.add("Run with your pet for 30 minutes and Strength training with your pet for 20 minutes");
                break;
            case "Advanced":
                exercisePlan.add("Run with your pet for 45 minutes, Strength training with your pet for 30 minutes, and Yoga with your pet for 20 minutes");
                break;
        }
        return exercisePlan;
    }

    private void saveToFirestore(String ownerFitnessLevel, List<String> exercisePlan) {
        Map<String, Object> data = new HashMap<>();
        data.put("ownerFitnessLevel", ownerFitnessLevel);
        data.put("exercisePlan", exercisePlan);

        db.collection("exercisePlans")
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
