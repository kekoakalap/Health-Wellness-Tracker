package com.example.appproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

public class ReminderActivity extends AppCompatActivity {

    private EditText taskTitleEditText;
    private EditText taskTypeEditText;
    private EditText reminderTimeEditText;
    private Button scheduleReminderButton;

    // Add Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminders);

        taskTitleEditText = findViewById(R.id.taskTitleEditText);
        taskTypeEditText = findViewById(R.id.taskTypeEditText);
        reminderTimeEditText = findViewById(R.id.reminderTimeEditText);
        scheduleReminderButton = findViewById(R.id.scheduleReminderButton);

        scheduleReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirestore();

                // Show toast message
                Toast.makeText(ReminderActivity.this, "Reminder set successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFirestore() {
        String taskTitle = taskTitleEditText.getText().toString();
        String taskType = taskTypeEditText.getText().toString();
        String reminderTime = reminderTimeEditText.getText().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("taskTitle", taskTitle);
        data.put("taskType", taskType);
        data.put("reminderTime", reminderTime);

        db.collection("reminders")
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
    // Add method to schedule the notification here
}
