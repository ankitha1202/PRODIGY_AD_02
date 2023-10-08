package com.example.to_do_list;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.DialogInterface;
import android.widget.AdapterView;



public class MainActivity extends AppCompatActivity {

    private ArrayList<String> tasks;
    private ArrayAdapter<String> adapter;
    private EditText taskEditText;
    private ListView taskListView;
    private int selectedTaskIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.taskEditText);
        taskListView = findViewById(R.id.taskListView);

        tasks = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        taskListView.setAdapter(adapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = taskEditText.getText().toString().trim();
                if (!task.isEmpty()) {
                    tasks.add(task);
                    adapter.notifyDataSetChanged();
                    taskEditText.getText().clear();
                }
            }
        });

        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTaskIndex != -1) {
                    editTask(selectedTaskIndex);
                }
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTaskIndex != -1) {
                    deleteTask(selectedTaskIndex);
                }
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTaskIndex = position;
            }
        });
    }

    private void editTask(final int position) {
        final EditText editTaskEditText = new EditText(this);
        editTaskEditText.setText(tasks.get(position));

        AlertDialog.Builder editDialog = new AlertDialog.Builder(this);
        editDialog.setTitle("Edit Task");
        editDialog.setView(editTaskEditText);

        editDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String updatedTask = editTaskEditText.getText().toString().trim();
                if (!updatedTask.isEmpty()) {
                    tasks.set(position, updatedTask);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        editDialog.show();
    }

    private void deleteTask(final int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle("Delete Task");
        deleteDialog.setMessage("Are you sure you want to delete this task?");

        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                selectedTaskIndex = -1;
            }
        });

        deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        deleteDialog.show();
    }
}
