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

public class CreateActivity extends Activity implements Creator {
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
        String title = bundle.getString(Constants.TITLE);
        titleText.setText(title);

        intervalArrayList = new ArrayList<>();
        stringArrayList = new ArrayList<>();
        try {
            String jsonString = bundle.getString(Constants.JSONSTRING);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String description = jsonObject.getString(Constants.DESCRIPTION);
                int duration = jsonObject.getInt(Constants.DURATION);

                Interval interval = new Interval(description, duration);
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

        // TEST DAT SHIT SON
        final AddDialog addDialog = new AddDialog(CreateActivity.this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog.show();
            }
        });

        final EditDialog editDialog = new EditDialog(CreateActivity.this);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.show();
            }
        });

        final DeleteDialog deleteDialog = new DeleteDialog(CreateActivity.this);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.show();
            }
        });

        saveButton.setOnClickListener(new OnSaveListener(this));

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intervalArrayList.size() == 0) {
                    startButton.setError("You haven't set any alarms!", null);
                } else if (titleText.getText().toString().equals("")) {
                    titleText.setError("You need to set a title!", null);
                } else {
                    Intent intent = new Intent(CreateActivity.this, AlarmActivity.class);
                    String jsonString = getJSONString(intervalArrayList);
                    intent.putExtra(Constants.TITLE, titleText.getText().toString());
                    intent.putExtra(Constants.JSONSTRING, jsonString);
                    startActivity(intent);
                }
            }
        });
    }

    public void onAddCompleted(String description, int duration) {
        Interval interval = new Interval(description, duration);
        intervalArrayList.add(interval);
        stringArrayList.add("#" + intervalArrayList.size() + " - " + interval.toString());
        updateListView();
    }

    public void onEditCompleted(String description, int duration, int position) {
        Interval interval = new Interval(description, duration);
        intervalArrayList.set(position - 1, interval);
        stringArrayList.set(position - 1, interval.toString());
        updateListView();
    }

    public void onDeleteCompleted(int position) {
        position = position - 1;
        intervalArrayList.remove(position);
        stringArrayList.remove(position);
        for (int i = position; i < intervalArrayList.size(); i++) {
            Interval interval = intervalArrayList.get(i);
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
                jsonObject.put(Constants.DESCRIPTION, interval.getDescription());
                jsonObject.put(Constants.DURATION, interval.getDuration());
                jsonArray.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}