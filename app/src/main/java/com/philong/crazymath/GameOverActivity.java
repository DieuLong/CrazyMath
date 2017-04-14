package com.philong.crazymath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtYourScore, txtGameOver, txtYS;
    private int score;
    private Button btnTryAgain, btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        init();
    }

    private void init() {
        btnTryAgain = (Button)findViewById(R.id.btnTryAgain);
        btnHome = (Button)findViewById(R.id.btnHome);
        txtYS = (TextView)findViewById(R.id.txtYS);
        txtYourScore = (TextView)findViewById(R.id.txtYourScore);
        txtGameOver = (TextView)findViewById(R.id.txtGameOver);
        score = getIntent().getExtras().getInt(PlayActivity.SCORE);
        txtYourScore.setText(String.valueOf(score));
        int best = MainActivity.sharedPreferences.getInt("BEST", 0);
        if(score > best){
            SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
            editor.putInt("BEST", score);
            editor.commit();
        }

        btnHome.setOnClickListener(this);
        btnTryAgain.setOnClickListener(this);

        txtYS.setTypeface(MainActivity.typeface);
        txtGameOver.setTypeface(MainActivity.typeface);
        txtYourScore.setTypeface(MainActivity.typeface);
        btnTryAgain.setTypeface(MainActivity.typeface);
        btnHome.setTypeface(MainActivity.typeface);

    }

    @Override
    public void onClick(View v) {
        int id =  v.getId();
        switch(id){
            case R.id.btnHome:
                finish();
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnTryAgain:
                finish();
                Intent intentAgain = new Intent(GameOverActivity.this, PlayActivity.class);
                startActivity(intentAgain);
                break;
        }
    }
}
