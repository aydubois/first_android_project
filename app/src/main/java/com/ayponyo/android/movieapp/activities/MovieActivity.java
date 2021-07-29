package com.ayponyo.android.movieapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.ayponyo.android.movieapp.R;
import com.ayponyo.android.movieapp.models.Movie;
import com.ayponyo.android.movieapp.utils.Util;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


//import com.ayponyo.android.movieapp.databinding.ActivityMovieBinding;

public class MovieActivity extends AppCompatActivity {

    //private ActivityMovieBinding binding;
    private ImageView mImageViewMovie;
    /*private LinearLayout mLinearLayoutPresentationBase;*/
    private TextView mTextViewTitleMovie;
    private TextView mTextViewReleaseMovie;
    private TextView mTextViewGenre;
    private TextView mTextViewParagraphBody;
    private TextView mButtonSeeMore;
    private TextView mTextViewDirector;
    private TextView mTextViewActors;
    private TextView mTextViewAwards;
    private LinearLayout mLinearLayoutContentOneMovie;
    private ProgressBar mProgressBarOneMovie;
    private TextView mTextViewMessageError;

    private OkHttpClient mOkHttpClient;

    private Movie mMovie;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMovieBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        //Toolbar toolbar = binding.toolbar;
        //CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        //FloatingActionButton fab = binding.fab;



        // Recovery of sent data
        Bundle extras = getIntent().getExtras();
        /*String numberMovie = extras.getString(Util.number_movie);*/
        //String titleMovie = extras.getString(Util.TITLE_MOVIE);
        /*String dateMovie = extras.getString(Util.date_movie);*/
        // Log.d("MOVIE ACT", numberMovie);

        //Log.d("MOVIE1", "Title movie extra : " +titleMovie);

        setContentView(R.layout.activity_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        /*toolBarLayout.setTitle(titleMovie);*/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Ajout aux favoris <3", Snackbar.LENGTH_LONG)
                .setAction("Action", handleClickFavourite).show());
        Log.d("MOVIE1", "MovieActivity: onCreate()");

        this.initialisationDom();

        mOkHttpClient = new OkHttpClient();
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Oui il y a Internet je lance un appel API
            Request request = new Request.Builder().url("http://www.omdbapi.com/?i=tt0076759&apikey=bf4e1adb&plot=full").build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    MovieActivity.this.updateUI(true, false);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String stringJson = response.body().string();
                        Log.d("MOVIE1", stringJson);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            // Code exécuté dans le Thread principale
                                mMovie = new Gson().fromJson(stringJson, Movie.class);
                                if(mMovie.getTitle().isEmpty() && mMovie.getPlot().isEmpty()){
                                    MovieActivity.this.updateUI(true, false);
                                }else{
                                    MovieActivity.this.updateUI(true, true);
                                }
                            }
                        });


                    }
                }
            });

        } else {
            this.updateUI(false, false);
        }






        //Injection of data
       /* mTextViewTitleMovie.setText(getString(R.string.release) + " : "+dateMovie);
        mTextViewTitleMovie.setBackgroundColor(R.color.green_3);*/

        /*try {
            JSONObject obj = new JSONObject(Objects.requireNonNull(Util.loadJSONFromAsset(this.getApplicationContext(), "movie.json")));

            mMovie = new Movie(obj.get("Title").toString(), obj.get("Released").toString(), obj.get("Director").toString(), obj.get("Plot").toString(), obj.get("Genre").toString(), obj.get("Actors").toString(), obj.get("Poster").toString(), obj.get("Awards").toString() );
            toolBarLayout.setTitle(mMovie.getTitle());
            Log.d("MOVIE1", "Model movie : "+ mMovie.toString());
            this.updateUI();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("MOVIE1", "Model movie echec : "+ e.getMessage());

        }*/
    }
    protected void initialisationDom(){
        mLinearLayoutContentOneMovie = findViewById(R.id.linear_layout_content_one_movie);
        mProgressBarOneMovie = findViewById(R.id.progress_bar_one_movie);
        mImageViewMovie = findViewById(R.id.image_view_movie);
        /*mLinearLayoutPresentationBase = findViewById(R.id.linear_layout_presentation_base);*/
        mTextViewTitleMovie = findViewById(R.id.text_view_title_movie);
        mTextViewReleaseMovie = findViewById(R.id.text_view_release_movie);
        mTextViewGenre = findViewById(R.id.text_view_genre_movie);
        mTextViewParagraphBody = findViewById(R.id.text_view_paragraph_body);
        mButtonSeeMore = findViewById(R.id.button_seemore);
        mTextViewDirector = findViewById(R.id.text_view_director);
        mTextViewActors = findViewById(R.id.tewt_view_actors);
        mTextViewAwards = findViewById(R.id.text_view_awards);
        mTextViewMessageError = findViewById(R.id.text_view_message_error);
    }

    protected void updateUI(boolean hasInternet, boolean hasSuccessFull){
        if(hasInternet && hasSuccessFull){

            mTextViewTitleMovie.setText(mMovie.getTitle());
            String released = Util.changeDateFormat(mMovie.getReleased());
            mTextViewReleaseMovie.setText(released);
            mTextViewGenre.setText(mMovie.getGenre());
            mTextViewParagraphBody.setText(mMovie.getPlot());
            mTextViewDirector.setText(mMovie.getDirector());
            mTextViewActors.setText(mMovie.getActors());
            mTextViewAwards.setText(mMovie.getAwards());

            mButtonSeeMore.setOnClickListener(handleClickSeeMore);
            Util.loadImage(mImageViewMovie, mMovie.getPoster(), 350,500);
            mLinearLayoutContentOneMovie.setVisibility(View.VISIBLE);
            mProgressBarOneMovie.setVisibility(View.GONE);
        }else if(!hasInternet){
            mTextViewMessageError.setText(R.string.error_network);
            mTextViewMessageError.setVisibility(View.VISIBLE);
            mProgressBarOneMovie.setVisibility(View.INVISIBLE);
        }else if(!hasSuccessFull){
            mTextViewMessageError.setText(R.string.error_API);
            mTextViewMessageError.setVisibility(View.VISIBLE);
            mProgressBarOneMovie.setVisibility(View.INVISIBLE);
        }
    }
    private final View.OnClickListener handleClickFavourite = arg0 -> {
        //mMovie.setFavourite(!mMovie.isFavourite());

    };

    private final View.OnClickListener handleClickSeeMore = arg0 -> {
        TextView buttonSeeMore = (TextView) arg0;
        Log.d("MOVIE1", "equals see more : "+buttonSeeMore.getText().equals(getString(R.string.see_more) ) + " "+ buttonSeeMore.getText() + " / " + getString(R.string.see_more) );
        if(buttonSeeMore.getText().equals(getString(R.string.see_more))){
            mTextViewParagraphBody.setMaxLines(Integer.MAX_VALUE);
            buttonSeeMore.setText(R.string.see_less);
        }else{
            buttonSeeMore.setText(R.string.see_more);
            mTextViewParagraphBody.setMaxLines(3);
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MovieActivity: onDestroy()");
    }


}