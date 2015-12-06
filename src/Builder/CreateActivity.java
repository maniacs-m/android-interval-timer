package jemboy.alarmz.Builder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import jemboy.alarmz.Dialog.AddDialog;
import jemboy.alarmz.Dialog.DeleteDialog;
import jemboy.alarmz.Dialog.EditDialog;
import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Interval;

public class CreateActivity extends Activity implements OnDialogCompleted {
    ArrayList<Interval> intervalArrayList;
    ArrayList<String> stringArrayList;
    ListView intervalView;
    Button addButton, editButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        intervalArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();

        intervalView = (ListView)findViewById(R.id.intervals);

        addButton = (Button)findViewById(R.id.add);
        editButton = (Button)findViewById(R.id.edit);
        deleteButton = (Button)findViewById(R.id.delete);

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
    }

    public void onAddCompleted(String description, int duration) {
        Interval interval = new Interval(description, duration, intervalArrayList.size());
        intervalArrayList.add(interval);
        stringArrayList.add(interval.toString());
        updateListView();
    }

    public void onEditCompleted() {

    }

    public void onDeleteCompleted() {

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
