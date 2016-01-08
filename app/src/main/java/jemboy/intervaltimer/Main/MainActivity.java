package jemboy.intervaltimer.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;

import jemboy.intervaltimer.Builder.CreateActivity;
import jemboy.intervaltimer.Builder.LoadActivity;
import jemboy.intervaltimer.R;
import jemboy.intervaltimer.Utility.Constants;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newPlaylist = (Button)findViewById(R.id.new_playlist);
        Button loadPlaylist = (Button)findViewById(R.id.load_playlist);

        newPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                intent.putExtra(Constants.TITLE, "");
                intent.putExtra(Constants.JSONSTRING, new JSONArray().toString());
                startActivity(intent);
            }
        });

        loadPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoadActivity.class);
                startActivity(intent);
            }
        });
    }
}
