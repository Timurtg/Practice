package com.example.practice;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText textEditText;
    private EditText deadlineEditText;
    private CheckBox doneCheckBox;
    private NotesDbHelper dbHelper;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        dbHelper = new NotesDbHelper(this);
        noteId = getIntent().getIntExtra("note_id", -1);

        titleEditText = findViewById(R.id.titleEditText);
        textEditText = findViewById(R.id.textEditText);
        deadlineEditText = findViewById(R.id.deadlineEditText);
        doneCheckBox = findViewById(R.id.doneCheckBox);

        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button backButton = findViewById(R.id.backButton);

        loadNote();

        updateButton.setOnClickListener(v -> updateNote());
        deleteButton.setOnClickListener(v -> showDeleteDialog());
        backButton.setOnClickListener(v -> finish());
    }

    private void loadNote() {
        Note note = dbHelper.getNoteById(noteId);
        if (note == null) {
            Toast.makeText(this, "Заметка не найдена", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        titleEditText.setText(note.getTitle());
        textEditText.setText(note.getText());
        deadlineEditText.setText(note.getDeadline());
        doneCheckBox.setChecked(note.isDone());
    }

    private void updateNote() {
        String title = titleEditText.getText().toString().trim();
        String text = textEditText.getText().toString().trim();
        String deadline = deadlineEditText.getText().toString().trim();
        boolean done = doneCheckBox.isChecked();

        if (title.isEmpty()) {
            titleEditText.setError("Введите название");
            return;
        }

        dbHelper.updateNote(noteId, title, text, deadline, done);
        Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Вы действительно хотите удалить заметку?")
                .setPositiveButton("Да", (dialog, which) -> deleteNote())
                .setNegativeButton("Нет", null)
                .show();
    }

    private void deleteNote() {
        dbHelper.deleteNote(noteId);
        Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show();
        finish();
    }
}
