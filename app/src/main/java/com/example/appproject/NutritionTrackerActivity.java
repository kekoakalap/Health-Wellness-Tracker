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

    private EditText DietEditText;
    private Button analyzeDietButton;
    private TextView DietAnalysisTextView;
    private String[] insightsAndRecommendations;

    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_tracker);

        DietEditText = findViewById(R.id.DietEditText);
        analyzeDietButton = findViewById(R.id.analyzeDietButton);
        DietAnalysisTextView = findViewById(R.id.DietAnalysisTextView);

        setupAnalyzeDietButton();

        insightsAndRecommendations = new String[] {
                "Ensure to have a balanced diet that includes all nutrients in the right proportions.",
                "Regular exercise is key to maintain good health.",
                "Drink plenty of water daily.",
                "Limit the intake of processed food."
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
        String Diet = DietEditText.getText().toString();


        String Analysis = "Dietary Habits:\n" + Diet + "\nInsights & Recommendations:\n" + getRandomInsight();


        DietAnalysisTextView.setText(Analysis);

    }

    private String getRandomInsight() {
        Random random = new Random();
        int index = random.nextInt(insightsAndRecommendations.length);
        return insightsAndRecommendations[index];
    }

    private void saveToFirestore() {
        String Diet = DietEditText.getText().toString();


        Map<String, Object> data = new HashMap<>();
        data.put("Diet", Diet);


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
