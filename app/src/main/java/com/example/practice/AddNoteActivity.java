package com.example.practice;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

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

        deadlineEditText.setOnClickListener(v -> showDateDialog());
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

        if (deadline.isEmpty()) {
            deadlineEditText.setError("Укажите срок выполнения");
            return;
        }

        if (!DateHelper.isDateCorrect(deadline)) {
            deadlineEditText.setError("Дата должна быть в формате дд.мм.гггг");
            return;
        }

        dbHelper.addNote(title, text, deadline, done);
        Toast.makeText(this, "Заметка сохранена", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDateDialog() {
        Calendar calendar = DateHelper.getCalendarFromText(deadlineEditText.getText().toString());

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    deadlineEditText.setText(DateHelper.makeDateText(year, month, dayOfMonth));
                    deadlineEditText.setError(null);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }
}
