package com.example.practice;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    private ArrayList<Note> notes;
    private OnNoteClickListener listener;

    public NotesAdapter(ArrayList<Note> notes, OnNoteClickListener listener) {
        this.notes = notes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.titleTextView.setText(note.getTitle());

        if (note.getDeadline() == null || note.getDeadline().isEmpty()) {
            holder.deadlineTextView.setText("Срок не указан");
        } else {
            holder.deadlineTextView.setText("Срок: " + note.getDeadline());
        }

        if (note.isDone()) {
            holder.statusTextView.setText("Выполнено");
            holder.statusTextView.setTextColor(Color.parseColor("#2E7D32"));
            holder.statusIconTextView.setText("\u2713");
            holder.statusIconTextView.setTextColor(Color.parseColor("#2E7D32"));
        } else {
            holder.statusTextView.setText("Не выполнено");
            holder.statusTextView.setTextColor(Color.parseColor("#C62828"));
            holder.statusIconTextView.setText("!");
            holder.statusIconTextView.setTextColor(Color.parseColor("#C62828"));
        }

        holder.itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(ArrayList<Note> newNotes) {
        notes = newNotes;
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView deadlineTextView;
        TextView statusTextView;
        TextView statusIconTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            deadlineTextView = itemView.findViewById(R.id.deadlineTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            statusIconTextView = itemView.findViewById(R.id.statusIconTextView);
        }
    }
}
