package jemboy.intervaltimer.Runnable;

import android.content.Context;
import android.os.Handler;

import jemboy.intervaltimer.Main.AlarmActivity;
import jemboy.intervaltimer.Main.LovelyCounter;

public class LovelyCountDown {
    private boolean running = false;
    private LovelyCounter lovelyCounter;

    public LovelyCountDown(Context context) {
        this.lovelyCounter = ((AlarmActivity)context);
        this.initialize();
    }

    public void start()
    {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public void initialize() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                if (running) {
                    lovelyCounter.onLovelyCount();
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
