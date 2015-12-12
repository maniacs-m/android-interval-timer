package jemboy.alarmz.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import jemboy.alarmz.R;
import jemboy.alarmz.Runnable.LovelyCountDown;
import jemboy.alarmz.Utility.Constants;
import jemboy.alarmz.Utility.Interval;

public class AlarmActivity extends Activity implements LovelyCounter {
    private ArrayList<Interval> intervalArrayList;

    private LovelyCountDown lovelyCountDown;
    private TextView intervalDescription, alarmChronometer, totalChronometer;
    private int totalDuration = 0, currentDuration = 0, currentPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Bundle bundle = getIntent().getExtras();
        TextView titleView = (TextView)findViewById(R.id.filename);
        titleView.setText(bundle.getString(Constants.TITLE));

        intervalArrayList = new ArrayList<>();
        try {
            String jsonString = bundle.getString(Constants.JSONSTRING);
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String description = jsonObject.getString(Constants.DESCRIPTION);
                int duration = jsonObject.getInt(Constants.DURATION);
                totalDuration = totalDuration + duration;

                Interval interval = new Interval(description, duration);
                intervalArrayList.add(interval);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        currentDuration = intervalArrayList.get(0).getDuration();
        intervalDescription     = (TextView)findViewById(R.id.alarm_description);
        alarmChronometer        = (TextView)findViewById(R.id.alarm_countdown);
        totalChronometer        = (TextView)findViewById(R.id.total_countdown);

        intervalDescription.setText(intervalArrayList.get(0).getDescription());
        alarmChronometer.setText(formatSeconds(currentDuration));
        totalChronometer.setText(formatSeconds(totalDuration));

        final ToggleButton continueButton = (ToggleButton)findViewById(R.id.continue_pause);
        continueButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lovelyCountDown = new LovelyCountDown(AlarmActivity.this);
                    lovelyCountDown.start();
                } else {
                    lovelyCountDown.stop();
                }
            }
        });

        Button previousButton = (Button)findViewById(R.id.previous);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition > 0)
                    currentPosition--;
                intervalDescription.setText(intervalArrayList.get(currentPosition).getDescription());
                currentDuration = intervalArrayList.get(currentPosition).getDuration();
                totalDuration = 0;
                for (int i = currentPosition; i < intervalArrayList.size(); i++) {
                    totalDuration += intervalArrayList.get(i).getDuration();
                }
                alarmChronometer.setText(formatSeconds(currentDuration));
                totalChronometer.setText(formatSeconds(totalDuration));
            }
        });

        Button nextButton = (Button)findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition < intervalArrayList.size() - 1)
                    currentPosition++;
                intervalDescription.setText(intervalArrayList.get(currentPosition).getDescription());
                currentDuration = intervalArrayList.get(currentPosition).getDuration();
                totalDuration = 0;
                for (int i = currentPosition; i < intervalArrayList.size(); i++) {
                    totalDuration += intervalArrayList.get(i).getDuration();
                }
                alarmChronometer.setText(formatSeconds(currentDuration));
                totalChronometer.setText(formatSeconds(totalDuration));
            }
        });

        Button quitButton = (Button)findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
                if (continueButton.isChecked()) {
                    continueButton.setChecked(false);
                    // Stop the timers
                }
                startActivity(intent);
            }
        });
    }

    public void onLovelyCount() {
        currentDuration--;
        totalDuration--;

        alarmChronometer.setText(formatSeconds(currentDuration));
        totalChronometer.setText(formatSeconds(totalDuration));

        if (totalDuration == 0) {
            lovelyCountDown.stop();
        }

        else if (currentDuration == 0) {
            currentPosition++;
            currentDuration = intervalArrayList.get(currentPosition).getDuration();
            intervalDescription.setText(intervalArrayList.get(currentPosition).getDescription());
        }
    }

    public String formatSeconds(int seconds) {
        int minutes = seconds / 60, remainder = seconds % 60;

        String alpha = "", omega = "";
        if (String.valueOf(minutes).length() == 1)
            alpha = "0";
        if (String.valueOf(remainder).length() == 1)
            omega = "0";

        return alpha + minutes + ":" + omega + remainder;
    }
}