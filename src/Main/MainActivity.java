package jemboy.alarmz.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import jemboy.alarmz.Builder.CreateActivity;
import jemboy.alarmz.Builder.LoadActivity;
import jemboy.alarmz.R;

public class MainActivity extends Activity {
    private Button newPlaylist, loadPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPlaylist = (Button)findViewById(R.id.new_playlist);
        loadPlaylist = (Button)findViewById(R.id.load_playlist);

        newPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });

        loadPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
                startActivity(intent);
            }
        });
    }
}
