package com.example.movie.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movie.R;
import com.example.movie.adapters.MovieAdapter;
import com.example.movie.databinding.FragmentFirstBinding;
import com.example.movie.models1.Movie;
import com.example.movie.models1.MovieResponse;
import com.example.movie.network2.ApiClient;
import com.example.movie.network2.TMDBApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesFragment extends Fragment {

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

        loadPopularMovies();

        return view;
    }

    private void loadPopularMovies() {
        TMDBApiService apiService = ApiClient.getApiService();
        apiService.getPopularMovies(API_KEY, "en-US", 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Movie> movies = response.body().getResults();
                            adapter.setMovies(movies);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to load popular movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
