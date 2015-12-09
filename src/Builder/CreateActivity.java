package jemboy.alarmz.Builder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import jemboy.alarmz.Dialog.AddDialog;
import jemboy.alarmz.Dialog.DeleteDialog;
import jemboy.alarmz.Dialog.EditDialog;
import jemboy.alarmz.Listener.OnSaveListener;
import jemboy.alarmz.Main.AlarmActivity;
import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Constants;
import jemboy.alarmz.Utility.Interval;

public class CreateActivity extends Activity implements OnDialogCompleted {
    private ArrayList<Interval> intervalArrayList;
    public ArrayList<Interval> getIntervalArrayList() {
        return intervalArrayList;
    }
    private ArrayList<String> stringArrayList;
    private ListView intervalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Bundle bundle = getIntent().getExtras();

        final EditText titleText = (EditText)findViewById(R.id.title);
        String title = bundle.getString("title");
        titleText.setText(title);

        intervalArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        try {
            String jsonString = bundle.getString(Constants.jsonArray);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String description = jsonObject.getString("description");
                int duration = jsonObject.getInt("duration"), position = jsonObject.getInt("position");

                Interval interval = new Interval(description, duration, position);
                intervalArrayList.add(interval);
                stringArrayList.add(interval.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intervalView = (ListView)findViewById(R.id.intervals);
        updateListView();

        final Button    addButton = (Button)findViewById(R.id.add),
                        editButton = (Button)findViewById(R.id.edit),
                        deleteButton = (Button)findViewById(R.id.delete),
                        saveButton = (Button)findViewById(R.id.save),
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

        saveButton.setOnClickListener(new OnSaveListener(this));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intervalArrayList.size() == 0) {
                    startButton.setError("You haven't set any alarms!");
                } else if (titleText.getText().toString().equals("")) {
                    titleText.setError("You need to set a title!");
                } else {
                    Intent intent = new Intent(CreateActivity.this, AlarmActivity.class);
                    String jsonString = getJSONString(intervalArrayList);
                    intent.putExtra("title", titleText.getText().toString());
                    intent.putExtra("jsonArray", jsonString);
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

    public String getJSONString(ArrayList<Interval> arrayList) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < arrayList.size(); i++) {
                Interval interval = arrayList.get(i);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("position", interval.getPosition());
                jsonObject.put("description", interval.getDescription());
                jsonObject.put("duration", interval.getDuration());

                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}