package com.example.movie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView imagePoster;
    TextView textTitle, textOverview, textReleaseDate, textRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imagePoster = findViewById(R.id.imagePoster);
        textTitle = findViewById(R.id.textTitle);
        textOverview = findViewById(R.id.textOverview);
        textReleaseDate = findViewById(R.id.textReleaseDate);
        textRating = findViewById(R.id.textRating);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String posterPath = intent.getStringExtra("poster_path");
        String overview = intent.getStringExtra("overview");
        String releaseDate = intent.getStringExtra("release_date");
        float voteAverage = intent.getFloatExtra("vote_average", 0.0f);

        textTitle.setText(title);
        textOverview.setText(overview);
        textReleaseDate.setText("Release: " + releaseDate);
        textRating.setText("Rating: " + voteAverage + "/10");

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + posterPath)
                .into(imagePoster);
        ImageButton buttonShare = findViewById(R.id.buttonShare);

        buttonShare.setOnClickListener(v -> {
            String movieUrl = "https://www.themoviedb.org/search?query=" + title.replace(" ", "+");

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this movie!");
            shareIntent.putExtra(Intent.EXTRA_TEXT, title + " - " + movieUrl);

            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }
}

