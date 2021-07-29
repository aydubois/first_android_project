package com.ayponyo.android.movieapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayponyo.android.movieapp.R;
import com.ayponyo.android.movieapp.models.Movie;
import com.ayponyo.android.movieapp.utils.Util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    public SearchAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_search_movie,parent , false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        try {

            Movie movie = mMovies.get(position);
            Log.d("Test movie", movie.getTitle());
            Log.d("Test movie", movie.getActors());
            Log.d("Test movie", movie.getAwards());
            Log.d("Test movie", movie.getBoxOffice());
            Log.d("Test movie", movie.getCountry());
            Log.d("Test movie", movie.getDirector());
            Log.d("Test movie", movie.getDVD());
            Log.d("Test movie", movie.getGenre());


        /*    holder.mTextViewTitleMovie.setText(movie.getTitle().isEmpty() ? "" : movie.getTitle());
            if(!movie.getReleased().isEmpty() && !movie.getReleased().equals("N/A") ){
                holder.mTextViewReleaseMovie.setText(Util.changeDateFormat(movie.getReleased()));
            }else if(!movie.getReleased().isEmpty()){
                holder.mTextViewReleaseMovie.setText(movie.getReleased());
            }
            if(!movie.getPoster().isEmpty())
                Util.loadImage(holder.mImageViewMovie, movie.getPoster(), 350,500);*/

        }catch (IndexOutOfBoundsException e){
            Log.d("ERROR1", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // Classe holder qui contient la vue d’un item
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitleMovie;
        private TextView mTextViewReleaseMovie;
        private ImageView mImageViewMovie;


        public ViewHolder(View view){
                super(view);
                mTextViewReleaseMovie = view.findViewById(R.id.text_view_release_movie_item);
                mTextViewTitleMovie = view.findViewById(R.id.text_view_title_movie_item);
                mImageViewMovie = view.findViewById(R.id.image_view_movie_item);
        }

    }


}