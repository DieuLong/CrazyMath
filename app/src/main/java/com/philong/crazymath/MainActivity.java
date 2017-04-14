package com.philong.crazymath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay, btnMore;
    private TextView txtBestScore, txtTitle;
    public static SharedPreferences sharedPreferences;
    private static final String DATA = "DATA";
    private final String PATH_FONT = "fonts/angelina.TTF";
    public static  Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addEvents();
    }

    private void addEvents() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        typeface = Typeface.createFromAsset(getAssets(), PATH_FONT);
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        btnPlay = (Button)findViewById(R.id.btnPLay);
        btnMore = (Button)findViewById(R.id.btnMore);
        txtBestScore = (TextView)findViewById(R.id.txtBestScore);
        sharedPreferences = getSharedPreferences(DATA, MODE_PRIVATE);
        String best = getString(R.string.best_score, sharedPreferences.getInt("BEST", 0));
        txtBestScore.setText(best);
        btnPlay.setTypeface(typeface);
        btnMore.setTypeface(typeface);
        txtBestScore.setTypeface(typeface);
        txtTitle.setTypeface(typeface);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        String best = getString(R.string.best_score, sharedPreferences.getInt("BEST", 0));
        txtBestScore.setText(best);
    }
}
