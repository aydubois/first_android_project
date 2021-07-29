package com.ayponyo.android.movieapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayponyo.android.movieapp.R;
import com.ayponyo.android.movieapp.utils.Util;

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


    }
    private View.OnClickListener handleClick = arg0 -> {
        Button btn = (Button)arg0;
        Log.d("Click", "Click button search");
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    };
    private View.OnClickListener handleClickMovie = arg0 -> {
        String tagNumberMovie = arg0.getTag().toString();
        int idTitle = getResources().getIdentifier("title_movie_"+tagNumberMovie, "string", getPackageName());
        String titleMovie = getString(idTitle);
        int idDate = getResources().getIdentifier("date_movie_"+tagNumberMovie, "string", getPackageName());
        String dateMovie = getString(idDate);
        Log.d("Click", "Click movie "+tagNumberMovie);
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra(Util.NUMBER_MOVIE,tagNumberMovie);
        intent.putExtra(Util.TITLE_MOVIE,titleMovie);
        intent.putExtra(Util.DATE_MOVIE,dateMovie);
        startActivity(intent);
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MainActivity: onDestroy()");
    }

}