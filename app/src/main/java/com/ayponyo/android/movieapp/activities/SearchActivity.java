package com.ayponyo.android.movieapp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayponyo.android.movieapp.R;
import com.ayponyo.android.movieapp.adapters.SearchAdapter;
import com.ayponyo.android.movieapp.models.Movie;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity  extends AppCompatActivity {
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Context mContext;
    private SearchAdapter mAdapter;
    private OkHttpClient mOkHttpClient;

    private TextInputEditText mTextInputSearch;
    private Button mButtonSearchMovie;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;
        mOkHttpClient = new OkHttpClient();
        mTextInputSearch = findViewById(R.id.text_input_search);
        mButtonSearchMovie = findViewById(R.id.button_search_movie);

        Log.d("SEARCH1", "create Search Activity");

        String[] urlMovies = {"https://www.omdbapi.com/?t=rubber&apikey=bf4e1adb","https://www.omdbapi.com/?t=Seven+Pounds&apikey=bf4e1adb", "https://www.omdbapi.com/?t=jack&apikey=bf4e1adb", "https://www.omdbapi.com/?t=eternal+sunshine+of+the+spotless+mind&apikey=bf4e1adb", "https://www.omdbapi.com/?t=rockers&apikey=bf4e1adb"};

        /*this.addMovies(urlMovies);*/


        mButtonSearchMovie.setOnClickListener(handleClickSearch);




    }
    private View.OnClickListener handleClickSearch = arg0 -> {
        Button btn = (Button)arg0;
        Log.d("Click", "Click button search");

        String valueSearch = mTextInputSearch.getText().toString();
        valueSearch = valueSearch.replace(" ", "%20");
        String url = "https://omdbapi.com/?s="+valueSearch+"&apikey=bf4e1adb&plot=full";
        this.callAPI(url);
    };

    public void callAPI(String url){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            Request request = new Request.Builder().url(url).build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    /*MovieActivity.this.updateUI(true, false);*/
                    Log.d("SEARCH1","Oops probleme d'API");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        final String stringJson = response.body().string();
                        try {
                            JSONObject jsonO = new JSONObject(stringJson);
                            JSONArray jsonArray = jsonO.getJSONArray("Search");


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mMovies.clear();
                                    for(int i=0;i<jsonArray.length();i++){

                                        try {
                                            SearchActivity.this.addListMoviesIntoView(jsonArray.get(0).toString());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Log.d("TEST1", String.valueOf(mMovies.size()));
                                    SearchActivity.this.setRecycler();

                                }
                            });
                            /*Log.d("TEST1", test.get(0).toString());*/
                        } catch (JSONException e) {
                            Log.d("ERROR1",e.getMessage());
                            e.printStackTrace();
                        }
                        Log.d("MOVIE1", stringJson);



                    }
                }
            });

        } else {
            /* this.updateUI(false, false);*/
        }
    }

    public void addListMoviesIntoView(String json){
/*        ListMovies movies = new Gson().fromJson(json, ListMovies.class);
        Log.d("TEST1", String.valueOf(movies.getSearch().size()));
        Log.d("TEST1", movies.getResponse());*/

        /*json = json.substring(json.length() - 1);*/
        Movie movie = new Gson().newBuilder().serializeNulls().create().fromJson(json, Movie.class);
        mMovies.add(movie);
        Log.d("TEST1", movie.getTitle());
       /* for (int i= 0;i< movies.getSearch().size();i++){
            mMovies.add(movies.getSearch().get(i));
        }*/
    }
    public void addMovies(String[] urlMovies){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        for (int i = 0; i < urlMovies.length; i++){
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                // Oui il y a Internet je lance un appel API
                Request request = new Request.Builder().url(urlMovies[i]).build();
                Log.d("SEARCH1", "Blah");
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        /*MovieActivity.this.updateUI(true, false);*/
                        Log.d("SEARCH1","Oops probleme d'API");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("SEARCH1", "bizar response code: "+ String.valueOf(response.code()));
                        if (response.isSuccessful()) {
                            final String stringJson = response.body().string();
                            Log.d("MOVIE1", stringJson);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Code exécuté dans le Thread principale
                                    Movie movie = new Gson().fromJson(stringJson, Movie.class);
                                    mMovies.add(movie);
                                    if(mMovies.size() == urlMovies.length){
                                        SearchActivity.this.setRecycler();
                                    }

                                }
                            });


                        }
                    }
                });

            } else {
                /* this.updateUI(false, false);*/
            }

        }
    }

    public void setRecycler(){
        Log.d("SEARCH1", "before recycler");
        try{

            mRecyclerView = SearchActivity.this.findViewById(R.id.recycler_view_movie);
            Log.d("SEARCH1", mRecyclerView.toString());
            mRecyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 3));
            mAdapter = new SearchAdapter(mContext, mMovies);
            Log.d("SEARCH1", mAdapter.toString());
            mRecyclerView.setAdapter(mAdapter);
        }catch(Exception e){
            Log.d("SEARCH1", e.getMessage());
        }
    }
}
