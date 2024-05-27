package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private EditText fitnessGoalEditText;
    private Button submitGoalsButton;
    private TextView fitnessGoalTextView;
    private TextView motivationalMessageTextView;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private String[] motivationalMessages = {
            "You can do it!",
            "Keep going!",
            "Don't give up!",
            "Every step counts!",
            "Believe in yourself!"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_setting);

        fitnessGoalEditText = findViewById(R.id.fitnessGoalEditText);
        submitGoalsButton = findViewById(R.id.submitGoalsButton);
        fitnessGoalTextView = findViewById(R.id.fitnessGoalTextView);
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


                Toast.makeText(GoalSettingActivity.this, "Goals submitted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayGoals() {
        String fitnessGoal = fitnessGoalEditText.getText().toString();

        fitnessGoalTextView.setText("Fitness Goal:\n" + fitnessGoal);
    }

    private void displayMotivationalMessage() {
        int index = new Random().nextInt(motivationalMessages.length);
        String randomMotivationalMessage = motivationalMessages[index];
        motivationalMessageTextView.setText(randomMotivationalMessage);
    }

    private void saveToFirestore() {
        String fitnessGoal = fitnessGoalEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("fitnessGoal", fitnessGoal);

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
