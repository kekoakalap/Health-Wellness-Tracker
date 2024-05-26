package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;

public class ExerciseGuidelinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_guidelines);

        Button openYogaTutorialButton = findViewById(R.id.openYogaTutorialButton);
        Button openExerciseTutorialButton = findViewById(R.id.openExerciseTutorialButton);
        Button openRunningTutorialButton = findViewById(R.id.openRunningTutorialButton);

        openYogaTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYoutube("https://www.youtube.com/watch?v=dMBrNl3VFWM");
            }
        });

        openExerciseTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYoutube("https://www.youtube.com/watch?v=eRhPltsu1-Y&pp=ygUYb3duZXIgYW5kIHBldCBleGVyY2lzaW5n");
            }
        });

        openRunningTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYoutube("https://www.youtube.com/watch?v=kK_SUsnLL5o&pp=ygUkb3duZXIgYW5kIHBldCBydW5uaW5nIGZvciBleGVyaWNzaW5n");
            }
        });
    }

    private void openYoutube(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
