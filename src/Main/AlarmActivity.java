package jemboy.alarmz.Main;

import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Constants;
import jemboy.alarmz.Utility.Interval;

public class AlarmActivity extends Activity {
    private ArrayList<Interval> intervalArrayList;
    private Interval interval;

    private CountDownTimer countDown;
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

        intervalDescription     = (TextView)findViewById(R.id.alarm_description);
        alarmChronometer        = (TextView)findViewById(R.id.alarm_countdown);
        totalChronometer        = (TextView)findViewById(R.id.total_countdown);

        ToggleButton continueButton = (ToggleButton)findViewById(R.id.continue_pause);
        continueButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {

                }
            }
        });

        Button previousButton = (Button)findViewById(R.id.previous);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition > 0)
                    currentPosition--;
            }
        });

        Button nextButton = (Button)findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition < intervalArrayList.size() - 1)
                    currentPosition++;
            }
        });

        Button quitButton = (Button)findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
            }
        });
    }

    public void instantiateCountDown(long millisInFuture, long countDownInterval, int index) {
        interval = intervalArrayList.get(index);
        countDown = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        };
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