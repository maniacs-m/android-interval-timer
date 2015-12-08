package jemboy.alarmz.Listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jemboy.alarmz.Builder.CreateActivity;
import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Constants;
import jemboy.alarmz.Utility.Interval;

public class OnSaveListener implements View.OnClickListener {
    private CreateActivity createActivity;
    private EditText titleText;
    private AlertDialog.Builder builder;
    private SharedPreferences sharedPref;

    public OnSaveListener(CreateActivity createActivity) {
        this.createActivity = createActivity;
        this.titleText = (EditText)createActivity.findViewById(R.id.title);
        this.builder = new AlertDialog.Builder(createActivity);
        this.sharedPref = createActivity.getSharedPreferences(Constants.sharedPrefName, Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        ArrayList<Interval> intervalArrayList = createActivity.getIntervalArrayList();

        final String title = titleText.getText().toString(), jsonString = createActivity.getJSONString(intervalArrayList);
        final SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        if (sharedPref.contains(title)) {
            builder.setTitle(title + " already exists! Do you want to overwrite it?");
            builder.setPositiveButton("Overwrite", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sharedPrefEditor.putString(title, jsonString);
                    sharedPrefEditor.apply();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        } else {
            sharedPrefEditor.putString(title, jsonString);
            sharedPrefEditor.apply();
        }
    }
}
