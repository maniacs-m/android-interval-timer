package jemboy.alarmz.Main;

import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import jemboy.alarmz.R;
import jemboy.alarmz.Utility.Interval;

public class AlarmActivity extends Activity {
    ArrayList<Interval> intervalArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        intervalArrayList = new ArrayList<>();


    }
}
