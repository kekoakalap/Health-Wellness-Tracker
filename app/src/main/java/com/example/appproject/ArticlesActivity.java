package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArticlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Button petHealthButton = findViewById(R.id.petHealthButton);
        petHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.petmd.com/");
            }
        });

        Button petFitnessButton = findViewById(R.id.petFitnessButton);
        petFitnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.akc.org/expert-advice/health/exercise-needs-by-breed-size/");
            }
        });
        Button humanFitnessButton = findViewById(R.id.humanFitnessButton);
        humanFitnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.mayoclinic.org/healthy-lifestyle/fitness/in-depth/exercise/art-20048389");
            }
        });

        Button petNutritionButton = findViewById(R.id.petNutritionButton);
        petNutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.aspca.org/pet-care/cat-care/cat-nutrition-tips");
            }
        });

        Button humanHealthButton = findViewById(R.id.humanHealthButton);
        humanHealthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.webmd.com/");
            }
        });
    }

    public void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}

