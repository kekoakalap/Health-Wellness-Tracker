package com.example.appproject;

import androidx.annotation.NonNull;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Random;

public class NutritionTrackerActivity extends AppCompatActivity {

    private EditText ownerDietEditText;
    private EditText petDietEditText;
    private Button analyzeDietButton;
    private TextView ownerDietAnalysisTextView;
    private TextView petDietAnalysisTextView;

    private String[] insightsAndRecommendations;

    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tracker);

        ownerDietEditText = findViewById(R.id.ownerDietEditText);
        petDietEditText = findViewById(R.id.petDietEditText);
        analyzeDietButton = findViewById(R.id.analyzeDietButton);
        ownerDietAnalysisTextView = findViewById(R.id.ownerDietAnalysisTextView);
        petDietAnalysisTextView = findViewById(R.id.petDietAnalysisTextView);
        setupAnalyzeDietButton();

        insightsAndRecommendations = new String[] {
                "Ensure to have a balanced diet that includes all nutrients in the right proportions.",
                "Regular exercise is key to maintain good health.",
                "Drink plenty of water daily.",
                "Limit the intake of processed food."
                //"Regularly feed your pet with pet-specific nutrient rich food.",
                //"Ensure your pet gets regular exercise.",
                //"Ensure your pet has access to clean water at all times.",
                //"Avoid feeding your pet with human food as it may not be suitable for their health.",
                //"Regular vet checkups are important for your pet's health.",
                //"Ensure to follow the diet recommended by the vet for your pet."
        };
    }

    private void setupAnalyzeDietButton() {
        analyzeDietButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDietAnalysis();
                saveToFirestore();
            }
        });
    }

    private void displayDietAnalysis() {
        String ownerDiet = ownerDietEditText.getText().toString();
        String petDiet = petDietEditText.getText().toString();

        String ownerAnalysis = "Owner's Dietary Habits:\n" + ownerDiet + "\nInsights & Recommendations:\n" + getRandomInsight();
        String petAnalysis = "Pet's Dietary Habits:\n" + petDiet + "\nInsights & Recommendations:\n" + getRandomInsight();

        ownerDietAnalysisTextView.setText(ownerAnalysis);
        petDietAnalysisTextView.setText(petAnalysis);
    }

    private String getRandomInsight() {
        Random random = new Random();
        int index = random.nextInt(insightsAndRecommendations.length);
        return insightsAndRecommendations[index];
    }

    private void saveToFirestore() {
        String ownerDiet = ownerDietEditText.getText().toString();
        String petDiet = petDietEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("ownerDiet", ownerDiet);
        data.put("petDiet", petDiet);

        db.collection("nutritionTracker")
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
