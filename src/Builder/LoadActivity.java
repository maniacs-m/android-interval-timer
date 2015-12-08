package jemboy.alarmz.Builder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Constants;
import jemboy.alarmz.Utility.Interval;

public class LoadActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        ListView listView = (ListView)findViewById(R.id.listView);

        SharedPreferences sharedPref = getSharedPreferences(Constants.sharedPrefName, Context.MODE_PRIVATE);
        Map<String, ?> sharedPrefData = sharedPref.getAll();

        final ArrayList<String> stringArrayList = new ArrayList<>();

        for (String key : sharedPrefData.keySet()) {
            stringArrayList.add(key);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stringArrayList
        );
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sharedPref = getSharedPreferences(Constants.sharedPrefName, Context.MODE_PRIVATE);
                String title = stringArrayList.get(position);
                String jsonString = sharedPref.getString(title, new JSONArray().toString());

                Intent intent = new Intent(LoadActivity.this, CreateActivity.class);
                intent.putExtra("title", title);
                intent.putExtra(Constants.jsonArray, jsonString);
                startActivity(intent);
            }
        });
    }
}
