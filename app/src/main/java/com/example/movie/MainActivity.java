package com.example.movie;

import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movie.adapters.MoviePagerAdapter;
import com.example.movie.utils.NetworkUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;



public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private static final String API_KEY = "4f27719d992fd010b0b6b8dc6a0472af";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);

        if (!NetworkUtils.isConnected(this)) {
            showNoConnectionDialog();
            return;
        }

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        MoviePagerAdapter adapter = new MoviePagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(position == 0 ? "Popular" : "Now Playing")
        ).attach();
    }

    private void showNoConnectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Please check your connection and try again.")
                .setCancelable(false)
                .setPositiveButton("Retry", (dialog, which) -> recreate())
                .setNegativeButton("Exit", (dialog, which) -> finish())
                .show();
    }
}
