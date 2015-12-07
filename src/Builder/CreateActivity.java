package jemboy.alarmz.Builder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import jemboy.alarmz.Dialog.AddDialog;
import jemboy.alarmz.Dialog.DeleteDialog;
import jemboy.alarmz.Dialog.EditDialog;
import jemboy.alarmz.Main.AlarmActivity;
import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Interval;

public class CreateActivity extends Activity implements OnDialogCompleted {
    ArrayList<Interval> intervalArrayList;
    ArrayList<String> stringArrayList;
    ListView intervalView;
    Button addButton, editButton, deleteButton, saveButton, startButton;
    EditText titleField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        intervalArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();

        intervalView = (ListView)findViewById(R.id.intervals);

        titleField = (EditText)findViewById(R.id.title);

        addButton = (Button)findViewById(R.id.add);
        editButton = (Button)findViewById(R.id.edit);
        deleteButton = (Button)findViewById(R.id.delete);
        saveButton = (Button)findViewById(R.id.save);
        startButton = (Button)findViewById(R.id.start);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog addDialog = new AddDialog(CreateActivity.this);
                addDialog.show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog = new EditDialog(CreateActivity.this);
                editDialog.show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog deleteDialog = new DeleteDialog(CreateActivity.this);
                deleteDialog.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleField.getText().toString();

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intervalArrayList.size() == 0) {
                    startButton.setError("You haven't set any alarms!");
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                    // Set the bundle extras
                    startActivity(intent);
                }
            }
        });
    }

    public void onAddCompleted(String description, int duration) {
        Interval interval = new Interval(description, duration, intervalArrayList.size());
        intervalArrayList.add(interval);
        stringArrayList.add(interval.toString());
        updateListView();
    }

    public void onEditCompleted(String description, int duration, int position) {
        Interval interval = new Interval(description, duration, position);
        intervalArrayList.set(position, interval);
        stringArrayList.set(position, interval.toString());
        updateListView();
    }

    public void onDeleteCompleted(int position) {
        intervalArrayList.remove(position);
        stringArrayList.remove(position);
        for (int i = position; i < intervalArrayList.size(); i++) {
            Interval interval = intervalArrayList.get(i);
            interval.setPosition(i);
            intervalArrayList.set(i, interval);
            stringArrayList.set(i, interval.toString());
        }
        updateListView();
    }

    public void updateListView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stringArrayList
        );
        intervalView.setAdapter(arrayAdapter);
    }
}
