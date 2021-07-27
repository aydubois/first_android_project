package com.ayponyo.android.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewWelcome;
    private Button mButtonSearch;
    private LinearLayout mLinearLayoutFirstMovie;
    private LinearLayout mLinearLayoutSecondMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewWelcome = (TextView) findViewById(R.id.text_view_welcome);
        mTextViewWelcome.setText(getString(R.string.welcome));


        /** ////////////////////////////////////////////////// **/

        mButtonSearch = (Button) findViewById(R.id.button_search);
        mButtonSearch.setOnClickListener(handleClick);


        /** ////////////////////////////////////////////////// **/

        mLinearLayoutFirstMovie = (LinearLayout) findViewById(R.id.first_movie);
        mLinearLayoutFirstMovie.setClickable(true);
        mLinearLayoutFirstMovie.setTag("1");
        mLinearLayoutFirstMovie.setOnClickListener(handleClickMovie);

        mLinearLayoutSecondMovie = (LinearLayout) findViewById(R.id.second_movie);
        mLinearLayoutSecondMovie.setClickable(true);
        mLinearLayoutSecondMovie.setTag("2");
        mLinearLayoutSecondMovie.setOnClickListener(handleClickMovie);

        Log.d("TAG", "MainActivity: onCreate()");

    }
    private View.OnClickListener handleClick = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Button btn = (Button)arg0;
            Log.d("Click", "Click button search");
        }
    };
    private View.OnClickListener handleClickMovie = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            String tagNumberMovie = arg0.getTag().toString();
            Log.d("Click", "Click movie "+tagNumberMovie);
            Intent intent = new Intent(MainActivity.this, MovieActivity.class);
            intent.putExtra("number_movie",tagNumberMovie);
            startActivity(intent);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MainActivity: onDestroy()");
    }

}