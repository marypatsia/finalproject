package com.example.movie.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.MovieDetailsActivity;
import com.example.movie.R;
import com.example.movie.models1.Movie;

import java.util.List;
import com.example.movie.utils.FavoritesManager;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movieList = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.textTitle.setText(movie.getTitle());

        // Load poster
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.imagePoster);

        // Set heart icon state
        boolean isFav = FavoritesManager.isFavorite(context, movie.getId());
        holder.imageFavorite.setImageResource(
                isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline
        );

        // Toggle favorite on click
        holder.imageFavorite.setOnClickListener(v -> {
            boolean currentFav = FavoritesManager.isFavorite(context, movie.getId());
            if (currentFav) {
                FavoritesManager.removeFavorite(context, movie.getId());
                holder.imageFavorite.setImageResource(R.drawable.ic_heart_outline);
            } else {
                FavoritesManager.addFavorite(context, movie.getId());
                holder.imageFavorite.setImageResource(R.drawable.ic_heart_filled);
            }
        });

        // Optional: click to open MovieDetails
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("poster_path", movie.getPosterPath());
            intent.putExtra("overview", movie.getOverview());
            intent.putExtra("release_date", movie.getReleaseDate());
            intent.putExtra("vote_average", movie.getVoteAverage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePoster;
        ImageView imageFavorite;
        TextView textTitle;

        MovieViewHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            imageFavorite = itemView.findViewById(R.id.imageFavorite); // ← connect this to your item_movie.xml
            textTitle = itemView.findViewById(R.id.textTitle);
        }
    }
}
