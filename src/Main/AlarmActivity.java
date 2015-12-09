package jemboy.alarmz.Main;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Constants;
import jemboy.alarmz.Utility.Interval;

public class AlarmActivity extends Activity {
    private ArrayList<Interval> intervalArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Bundle bundle = getIntent().getExtras();
        TextView titleView = (TextView)findViewById(R.id.titleView);
        titleView.setText(bundle.getString(Constants.TITLE));

        intervalArrayList = new ArrayList<>();
        try {
            String jsonString = bundle.getString(Constants.JSONSTRING);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String description = jsonObject.getString(Constants.DESCRIPTION);
                int duration = jsonObject.getInt(Constants.DURATION);

                Interval interval = new Interval(description, duration);
                intervalArrayList.add(interval);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView descriptionView = (TextView)findViewById(R.id.descriptionView);
        Interval interval = intervalArrayList.get(0);
        descriptionView.setText(interval.getDescription());

        Chronometer chronometer = (Chronometer)findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            Interval interval = intervalArrayList.remove(0);
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                if (elapsedMillis >= interval.getDuration() * 1000) {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    ringtone.play();
                    if (intervalArrayList.size() > 0) {
                        interval = intervalArrayList.remove(0);
                        descriptionView.setText(interval.getDescription());
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    } else {
                        Toast.makeText(getApplicationContext(), "Iteration is finished", Toast.LENGTH_SHORT).show();
                        chronometer.stop();
                    }
                }
            }
        });
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }
}
