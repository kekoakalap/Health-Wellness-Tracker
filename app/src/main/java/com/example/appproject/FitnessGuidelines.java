package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FitnessGuidelines extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_guidelines);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Set onClickListeners for the buttons
        Button btnBicep = findViewById(R.id.btnBicep);
        Button btnChest = findViewById(R.id.btnChest);
        Button btnLegs = findViewById(R.id.btnLegs);
        Button btnTricep = findViewById(R.id.btnTricep);
        Button btnShoulders = findViewById(R.id.btnShoulders);

        btnBicep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYouTubeLink("Bicep");
            }
        });

        btnChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYouTubeLink("Chest");
            }
        });

        btnLegs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYouTubeLink("Legs");
            }
        });

        btnTricep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYouTubeLink("Tricep");
            }
        });

        btnShoulders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYouTubeLink("Shoulders");
            }
        });
    }

    // Method to retrieve YouTube link from Firestore based on the exercise type
    private void getYouTubeLink(final String exerciseType) {
        db.collection("exercisePlans").document("ExerciseGuidelines")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String youtubeLink = documentSnapshot.getString(exerciseType);
                            if (youtubeLink != null && !youtubeLink.isEmpty()) {
                                openYoutube(youtubeLink);
                            }
                        }
                    }
                });
    }

    // Method to open YouTube link
    // Method to open YouTube link in a web browser
    private void openYoutube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.android.chrome"); // Explicitly use Chrome if available

        // Check if Chrome is installed and can handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // If Chrome is not installed, open with any available browser
            intent.setPackage(null);
            startActivity(intent);
        }
    }

}
