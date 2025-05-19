package com.example.movie.fragments;
import com.example.movie.adapters.MovieAdapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import android.widget.Toast;

import com.example.movie.R;
import com.example.movie.models1.Movie;
import com.example.movie.models1.MovieResponse;
import com.example.movie.network2.ApiClient;
import com.example.movie.network2.TMDBApiService;
import com.example.movie.utils.FavoritesManager;

import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private static final String API_KEY = "a1b2c3d4e5f6";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        Set<String> favoriteIds = FavoritesManager.getAllFavorites(getContext());

        if (favoriteIds.isEmpty()) {
            Toast.makeText(getContext(), "No favorites yet", Toast.LENGTH_SHORT).show();
            return;
        }

        TMDBApiService apiService = ApiClient.getApiService();
        apiService.getPopularMovies(API_KEY, "en-US", 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> allMovies = response.body().getResults();
                    List<Movie> favoriteMovies = new ArrayList<>();

                    for (Movie movie : allMovies) {
                        if (favoriteIds.contains(String.valueOf(movie.getId()))) {
                            favoriteMovies.add(movie);
                        }
                    }

                    adapter.setMovies(favoriteMovies);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
