package com.philong.crazymath;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtTime, txtBieuThuc, txtBest, txtScore;
    private Button btnKQ1, btnKQ2, btnKQ3;
    private int so1, so2, toanTu, kq;
    private static final int TIMER = 3;
    private Random random;
    private String cauHoi, dapAn1, dapAn2, dapAn3;
    private MyAsyncTask myAsyncTask;
    private MyAsyncTask1 myAsyncTask1;
    private int score = 0;
    public static final String SCORE = "SCORE";
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
        addEvents();
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
        myAsyncTask1 = new MyAsyncTask1();
        myAsyncTask1.execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getBestScore();
    }

    private void addEvents() {
        btnKQ1.setOnClickListener(this);
        btnKQ2.setOnClickListener(this);
        btnKQ3.setOnClickListener(this);
    }


    private void phepToan() {
        so1 = random.nextInt(10);
        so2 = 1 + random.nextInt(9);
        toanTu = random.nextInt(3);
        cauHoi = String.valueOf(so1);
        switch(toanTu){
            case 0:
                cauHoi += " + " + so2;
                kq = so1 + so2;
                dapAn1 = String.valueOf(kq);
                dapAn2 = String.valueOf(random.nextInt(9) - (1 +random.nextInt(8)));
                dapAn3 = String.valueOf(random.nextInt(9) * (1 +random.nextInt(8)));
                while(Integer.parseInt(dapAn1) == Integer.parseInt(dapAn2) || Integer.parseInt(dapAn1) == Integer.parseInt(dapAn3) || Integer.parseInt(dapAn2) == Integer.parseInt(dapAn3)){
                    dapAn2 = String.valueOf(random.nextInt(9) - (1 +random.nextInt(8)));
                    dapAn3 = String.valueOf(random.nextInt(9) * (1 +random.nextInt(8)));
                }

                break;
            case 1:
                cauHoi += " - " + so2;
                kq = so1 - so2;
                dapAn2 = String.valueOf(kq);
                dapAn1 = String.valueOf(random.nextInt(9) + (1 +random.nextInt(8)));
                dapAn3 = String.valueOf(random.nextInt(9) * (1 +random.nextInt(8)));
                while(Integer.parseInt(dapAn1) == Integer.parseInt(dapAn2) || Integer.parseInt(dapAn1) == Integer.parseInt(dapAn3) || Integer.parseInt(dapAn2) == Integer.parseInt(dapAn3)){
                    dapAn1 = String.valueOf(random.nextInt(9) - (1 +random.nextInt(8)));
                    dapAn3 = String.valueOf(random.nextInt(9) * (1 +random.nextInt(8)));
                }
                break;
            case 2:
                cauHoi += " * " + so2;
                kq = so1 * so2;
                dapAn3 = String.valueOf(kq);
                dapAn1 = String.valueOf(random.nextInt(9) + (1 +random.nextInt(8)));
                dapAn2 = String.valueOf(random.nextInt(9) - (1 +random.nextInt(8)));
                while(Integer.parseInt(dapAn1) == Integer.parseInt(dapAn2) || Integer.parseInt(dapAn1) == Integer.parseInt(dapAn3) || Integer.parseInt(dapAn2) == Integer.parseInt(dapAn3)){
                    dapAn2 = String.valueOf(random.nextInt(9) - (1 +random.nextInt(8)));
                    dapAn1 = String.valueOf(random.nextInt(9) * (1 +random.nextInt(8)));
                }
                break;
        }

    }

    private void init(){
        txtScore = (TextView)findViewById(R.id.txtScore);
        txtBest = (TextView)findViewById(R.id.txtBest);
        txtTime = (TextView)findViewById(R.id.txtTime);
        txtBieuThuc = (TextView)findViewById(R.id.txtBieuThuc);
        btnKQ1 = (Button)findViewById(R.id.btnKQ1);
        btnKQ2 = (Button)findViewById(R.id.btnKQ2);
        btnKQ3 = (Button)findViewById(R.id.btnKQ3);
        random = new Random();
        txtTime.setText(String.valueOf(TIMER));
        String temp = getString(R.string.score, score);
        txtScore.setText(temp);
        getBestScore();

        txtScore.setTypeface(MainActivity.typeface);
        txtBest.setTypeface(MainActivity.typeface);
        txtTime.setTypeface(MainActivity.typeface);
    }

    private void getBestScore(){
        String best = getString(R.string.best, MainActivity.sharedPreferences.getInt("BEST", 0));
        txtBest.setText(best);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean check = false;
        if(R.id.btnKQ1 == id){
            if(Integer.parseInt(btnKQ1.getText().toString())  == kq){
                check = true;
            }
        }else if(R.id.btnKQ2 == id){
            if(Integer.parseInt(btnKQ2.getText().toString())  == kq){
                check = true;
            }
        }else{
            if(Integer.parseInt(btnKQ3.getText().toString())  == kq){
                check = true;
            }
        }
        if(check){ // Đáp án đúng;
            score++;
            String temp = getString(R.string.score, score);
            txtScore.setText(temp);
            myAsyncTask.cancel(true);
            myAsyncTask1.cancel(true);
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
            myAsyncTask1 = new MyAsyncTask1();
            myAsyncTask1.execute();

        }else{ // Đáp án sai;
            myAsyncTask.cancel(true);
            myAsyncTask1.cancel(true);
            finish();
            Intent intent = new Intent(PlayActivity.this, GameOverActivity.class);
            intent.putExtra(SCORE, score);
            startActivity(intent);
        }
    }


    private class MyAsyncTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            phepToan();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txtBieuThuc.setText(cauHoi);
            Random random = new Random();
            int ngauNhien = random.nextInt(6);
            switch(ngauNhien){
                case 0:
                    btnKQ1.setText(dapAn1);
                    btnKQ2.setText(dapAn2);
                    btnKQ3.setText(dapAn3);
                    break;
                case 1:
                    btnKQ1.setText(dapAn2);
                    btnKQ2.setText(dapAn1);
                    btnKQ3.setText(dapAn3);
                    break;
                case 2:
                    btnKQ1.setText(dapAn3);
                    btnKQ2.setText(dapAn1);
                    btnKQ3.setText(dapAn2);
                    break;
                case 3:
                    btnKQ1.setText(dapAn2);
                    btnKQ2.setText(dapAn3);
                    btnKQ3.setText(dapAn1);
                    break;
                case 4:
                    btnKQ1.setText(dapAn3);
                    btnKQ2.setText(dapAn2);
                    btnKQ3.setText(dapAn1);
                    break;
                case 5:
                    btnKQ1.setText(dapAn1);
                    btnKQ2.setText(dapAn3);
                    btnKQ3.setText(dapAn2);
                    break;
            }

        }
    }

    private class MyAsyncTask1 extends AsyncTask<Void, Integer, Integer>{

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                for (int i = 3; i >= 0; i--) {
                    publishProgress(i);
                    new Thread().sleep(1000);

                }
            }catch(Exception ex){
                    Log.d("DEBUG", ex.toString());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            txtTime.setText(String.valueOf(values[0]));
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            myAsyncTask1.cancel(true);
            myAsyncTask.cancel(true);
            finish();
            Intent intent = new Intent(PlayActivity.this, GameOverActivity.class);
            intent.putExtra(SCORE, score);
            startActivity(intent);
        }
    }

}
