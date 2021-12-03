package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    // constructor and member variables
    Context context;
    List<Movie> movies;
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateVieHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder "+position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);

    }

    // Return the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverviewDetail);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText((movie.getOverview()));
            String imageURL;
            // if phone is landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                // then imageurl is landscape backdrop
                imageURL = movie.getBackdropPath();
            }
            else{ // movieURL is portrait
                imageURL = movie.getPosterPath();
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new RoundedCorners(50));


            Glide.with(context).load(imageURL).apply(requestOptions).into(ivPoster);

//            Glide.with(context).load(imageURL).into(ivPoster);


            // trying to add white space in between the items by adding on at the end
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context,
//                    context.getResources().getConfiguration().orientation);
//            recyclerView.addItemDecoration(dividerItemDecoration);


            // 1. Make click listener on whole row

            container.setOnClickListener(new View.OnClickListener() { // onclick listener for the whole container of the movie
                @Override
                public void onClick(View v) {
                    Log.i("MovieAdapter", "CHICKEN BUTT" + movie.getTitle());
                    // 2. go to new activity on click
                    Intent i = new Intent(context, DetailActivity.class);

                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                }
            });
        }
    }
}
