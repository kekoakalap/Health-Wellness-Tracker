package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.net.Uri;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Add a click listener for the first feature button
        Button buttonFeature1 = findViewById(R.id.buttonFeature1);
        buttonFeature1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PersonalExercisePlans.class);
                startActivity(intent);
            }
        });

        // Add a click listener for the medical history button
        Button medicalHistoryButton = findViewById(R.id.medicalHistoryButton);
        medicalHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MedicalHistoryActivity.class);
                startActivity(intent);
            }
        });

        // Add click listeners for other feature buttons here
        Button nutritionTrackerButton = findViewById(R.id.nutritionTrackerButton);
        nutritionTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, NutritionTrackerActivity.class);
                startActivity(intent);
            }
        });
        Button goalSettingButton = findViewById(R.id.goalSettingButton);
        goalSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, GoalSettingActivity.class);
                startActivity(intent);
            }
        });
        Button weightExerciseTrackingButton = findViewById(R.id.weightExerciseTrackingButton);
        weightExerciseTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, WeightExerciseTrackingActivity.class);
                startActivity(intent);
            }
        });
        Button fitnessGuidelinesButton = findViewById(R.id.fitnessGuidelinesButton);
        fitnessGuidelinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, FitnessGuidelines.class);
                startActivity(intent);
            }
        });
        Button openReminderActivityButton = findViewById(R.id.openReminderActivityButton);
        openReminderActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ReminderActivity.class);
                startActivity(intent);
            }
        });

        // Adding the 8th feature button
        Button btnSendSms = findViewById(R.id.btnSendSms);
        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSmsApp();
            }
        });
        Button findPlacesButton = findViewById(R.id.findPlacesButton);
        findPlacesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, FindPlacesActivity.class);
                startActivity(intent);
            }
        });

    }

    private void openSmsApp() {
        String phoneNumber = "09183643605";  // Replace with the actual phone number
        String message = "Hi, I'm using the Pet+Fit App..."; // Replace with your actual message

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }
}
