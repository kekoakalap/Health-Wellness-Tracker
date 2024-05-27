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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalExercisePlans extends AppCompatActivity {

    private Spinner FitnessLevelSpinner;
    private Button generateExercisePlanButton;
    private ListView exercisePlanListView;

    // Firestore instance
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_exercise_plans);

        FitnessLevelSpinner = findViewById(R.id.FitnessLevelSpinner);
        generateExercisePlanButton = findViewById(R.id.generateExercisePlanButton);
        exercisePlanListView = findViewById(R.id.exercisePlanListView);

        setupSpinner();
        setupGenerateExercisePlanButton();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.owner_fitness_levels, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        FitnessLevelSpinner.setAdapter(adapter);
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
        String fitnessLevel = FitnessLevelSpinner.getSelectedItem().toString();

        Map<String, String> exercisePlan = generateExercisePlanBasedOnPreferences(fitnessLevel);

        List<String> displayPlan = new ArrayList<>();
        for (String key : exercisePlan.keySet()) {
            displayPlan.add(key + ": " + exercisePlan.get(key));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, displayPlan);
        exercisePlanListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        saveToFirestore(fitnessLevel, exercisePlan);
    }

    private Map<String, String> generateExercisePlanBasedOnPreferences(String fitnessLevel) {
        Map<String, String> exercisePlan = new HashMap<>();
        String chestExercise;
        String bicepExercise;
        String tricepExercise;
        String shoulderExercise;
        String legExercise;

        switch (fitnessLevel) {
            case "Beginner":
                chestExercise = "3 sets of 10 reps of Push-ups";
                bicepExercise = "3 sets of 10 reps of Bicep Curls";
                tricepExercise = "3 sets of 10 reps of Tricep Dips";
                shoulderExercise = "3 sets of 10 reps of Shoulder Press";
                legExercise = "3 sets of 15 reps of Bodyweight Squats";
                break;
            case "Intermediate":
                chestExercise = "4 sets of 12 reps of Bench Press";
                bicepExercise = "4 sets of 12 reps of Hammer Curls";
                tricepExercise = "4 sets of 12 reps of Tricep Extensions";
                shoulderExercise = "4 sets of 12 reps of Lateral Raises";
                legExercise = "4 sets of 15 reps of Lunges";
                break;
            case "Advanced":
                chestExercise = "5 sets of 15 reps of Incline Bench Press";
                bicepExercise = "5 sets of 15 reps of Concentration Curls";
                tricepExercise = "5 sets of 15 reps of Skull Crushers";
                shoulderExercise = "5 sets of 15 reps of Arnold Press";
                legExercise = "5 sets of 20 reps of Bulgarian Split Squats";
                break;
            default:
                chestExercise = "";
                bicepExercise = "";
                tricepExercise = "";
                shoulderExercise = "";
                legExercise = "";
                break;
        }

        exercisePlan.put("Chest", chestExercise);
        exercisePlan.put("Bicep", bicepExercise);
        exercisePlan.put("Tricep", tricepExercise);
        exercisePlan.put("Shoulders", shoulderExercise);
        exercisePlan.put("Legs", legExercise);

        return exercisePlan;
    }

    private void saveToFirestore(String fitnessLevel, Map<String, String> exercisePlan) {
        db.collection("exercisePlan")
                .document("FitnessLevel")
                .collection("PersonalExercisePlan")
                .document(fitnessLevel)
                .set(exercisePlan)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error writing document", e);
                    }
                });
    }
}
