package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GoalSettingActivity extends AppCompatActivity {

    private EditText ownerGoalEditText;
    private EditText petGoalEditText;
    private Button submitGoalsButton;
    private TextView ownerGoalTextView;
    private TextView motivationalMessageTextView;

    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Array of motivational messages
    private String[] motivationalMessages = {
            "You can do it!",
            "Keep going!",
            "Don't give up!",
            "Every step counts!",
            "Believe in yourself!"
            // Add more messages as needed
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_setting);

        ownerGoalEditText = findViewById(R.id.ownerGoalEditText);
        submitGoalsButton = findViewById(R.id.submitGoalsButton);
        ownerGoalTextView = findViewById(R.id.ownerGoalTextView);
        motivationalMessageTextView = findViewById(R.id.motivationalMessageTextView);
        setupSubmitGoalsButton();
    }

    private void setupSubmitGoalsButton() {
        submitGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayGoals();
                displayMotivationalMessage();
                saveToFirestore();
            }
        });
    }

    private void displayGoals() {
        String ownerGoal = ownerGoalEditText.getText().toString();
        String petGoal = petGoalEditText.getText().toString();

        ownerGoalTextView.setText("Owner's Fitness Goal:\n" + ownerGoal);
    }

    private void displayMotivationalMessage() {
        int index = new Random().nextInt(motivationalMessages.length);
        String randomMotivationalMessage = motivationalMessages[index];
        motivationalMessageTextView.setText(randomMotivationalMessage);
    }

    private void saveToFirestore() {
        String ownerGoal = ownerGoalEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("ownerGoal", ownerGoal);

        db.collection("goals")
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