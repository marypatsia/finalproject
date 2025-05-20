package com.example.movie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class FavoritesManager {
    private static final String PREFS_NAME = "movie_favorites";

    // Check if a movie is marked as favorite
    public static boolean isFavorite(Context context, int movieId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(String.valueOf(movieId), false);
    }

    // Add a movie to favorites
    public static void addFavorite(Context context, int movieId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(String.valueOf(movieId), true);
        editor.apply();
    }

    // Remove a movie from favorites
    public static void removeFavorite(Context context, int movieId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(String.valueOf(movieId));
        editor.apply();
    }

    public static Set<Integer> getAllFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Map<String, ?> allEntries = prefs.getAll();
        Set<Integer> favorites = new HashSet<>();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue() instanceof Boolean && (Boolean) entry.getValue()) {
                try {
                    favorites.add(Integer.parseInt(entry.getKey()));
                } catch (NumberFormatException e) {
                    // ignore invalid keys
                }
            }
        }

        return favorites;
    }
}
