package com.example.practice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText textEditText;
    private EditText deadlineEditText;
    private CheckBox doneCheckBox;
    private NotesDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        dbHelper = new NotesDbHelper(this);

        titleEditText = findViewById(R.id.titleEditText);
        textEditText = findViewById(R.id.textEditText);
        deadlineEditText = findViewById(R.id.deadlineEditText);
        doneCheckBox = findViewById(R.id.doneCheckBox);

        Button saveButton = findViewById(R.id.saveButton);
        Button backButton = findViewById(R.id.backButton);

        saveButton.setOnClickListener(v -> saveNote());
        backButton.setOnClickListener(v -> finish());
    }

    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String text = textEditText.getText().toString().trim();
        String deadline = deadlineEditText.getText().toString().trim();
        boolean done = doneCheckBox.isChecked();

        if (title.isEmpty()) {
            titleEditText.setError("Введите название");
            return;
        }

        dbHelper.addNote(title, text, deadline, done);
        Toast.makeText(this, "Заметка сохранена", Toast.LENGTH_SHORT).show();
        finish();
    }
}
