package com.notes.iit.simplenotesmanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NotesListActivity extends AppCompatActivity {
    FloatingActionButton noteEditOpenButton;
    ListView listView;
    SqliteHelper sqliteHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        initalizeViews();
        noteEditOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesListActivity.this, ListEditActivity.class);
                startActivity(intent);
            }
        });
        sqliteHelper = new SqliteHelper(this);
        cursor = sqliteHelper.retriveAllNotesCursor();
        final CursorAdapter cursorAdapter = new NotesListAdapter(this, cursor);
        listView.setAdapter(cursorAdapter);


    }
    

    private void initalizeViews() {
        noteEditOpenButton = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.list);
    }
}
