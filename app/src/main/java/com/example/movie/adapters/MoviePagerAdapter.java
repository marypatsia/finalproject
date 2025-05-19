package com.example.movie.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movie.fragments.PopularMoviesFragment;
import com.example.movie.fragments.NowPlayingMoviesFragment;

public class MoviePagerAdapter extends FragmentStateAdapter {


    public MoviePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0
                ? new PopularMoviesFragment()
                : new NowPlayingMoviesFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
