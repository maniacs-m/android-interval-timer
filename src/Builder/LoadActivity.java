package jemboy.alarmz.Builder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

        ArrayList<String> stringArrayList = new ArrayList<>();

        for (String key : sharedPrefData.keySet()) {
            stringArrayList.add(key);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stringArrayList
        );
        listView.setAdapter(arrayAdapter);
    }
}
