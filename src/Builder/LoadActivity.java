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

import java.util.ArrayList;
import java.util.Map;

import jemboy.alarmz.Dialog.LoadDialog;
import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Constants;

public class LoadActivity extends Activity implements Loader {
    ListView listView;
    ArrayList<String> stringArrayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        listView = (ListView)findViewById(R.id.listView);

        SharedPreferences sharedPref = getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        Map<String, ?> sharedPrefData = sharedPref.getAll();

        stringArrayList = new ArrayList<>();
        for (String key : sharedPrefData.keySet()) {
            stringArrayList.add(key);
        }
        updateListView();

        final LoadDialog loadDialog = new LoadDialog(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = stringArrayList.get(position);
                loadDialog.setTitle(title);
                loadDialog.show();
            }
        });
    }

    public void onLoadCompleted(String title) {
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        String jsonString = sharedPref.getString(title, new JSONArray().toString());

        Intent intent = new Intent(LoadActivity.this, CreateActivity.class);
        intent.putExtra(Constants.TITLE, title);
        intent.putExtra(Constants.JSONSTRING, jsonString);
        startActivity(intent);
    }

    public void onDeleteCompleted(String title) {
        SharedPreferences sharedPref = getSharedPreferences(Constants.SHAREDPREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(title);
        editor.apply();
        
        stringArrayList.remove(title);
        updateListView();
    }

    public void updateListView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                stringArrayList
        );
        listView.setAdapter(arrayAdapter);
    }
}
