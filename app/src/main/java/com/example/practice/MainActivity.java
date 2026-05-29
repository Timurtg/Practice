package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private NotesDbHelper dbHelper;
    private NotesAdapter adapter;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new NotesDbHelper(this);
        emptyTextView = findViewById(R.id.emptyTextView);

        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new NotesAdapter(dbHelper.getAllNotes(), note -> {
            Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
            intent.putExtra("note_id", note.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> startActivity(new Intent(this, AddNoteActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setNotes(dbHelper.getAllNotes());
        checkEmptyList();
    }

    private void checkEmptyList() {
        if (adapter.getItemCount() == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
    }
}
