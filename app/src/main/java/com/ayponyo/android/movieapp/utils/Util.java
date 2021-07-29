package com.ayponyo.android.movieapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.ayponyo.android.movieapp.utils.MaDate;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class Util {
    public static final String NUMBER_MOVIE = "number_movie";
    public static final String TITLE_MOVIE = "title_movie";
    public static final String DATE_MOVIE = "date_movie";


    public static String loadJSONFromAsset(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static void loadImage(ImageView imageView, String url, Integer width, Integer height){
        Picasso.get()
                .load(url)
                .resize(width, height)
                .centerCrop()
                .into(imageView);

        Log.d("MOVIE1",Picasso.get().getSnapshot().toString());
    }

    public static String changeDateFormat(String date){

        MaDate newDate = null;
        try {
            newDate = new MaDate("dd MMMMM yyyy", date);
            return newDate.toStringPattern("dd/MM/yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }
}
