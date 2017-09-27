package com.filmpolis.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.MatrixCursor;

import com.filmpolis.DetailsActivity;
import com.filmpolis.MainActivity;
import com.filmpolis.models.Actor;
import com.filmpolis.models.Director;
import com.filmpolis.models.Movie;
import com.filmpolis.models.Suggestion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Miguel on 20/09/2017.
 */

public class Utils {
    public static final String[] SUGGESTIONS_COLUMS = new String[]{"_id", "icon", "suggestion"};

    public static final String URL_API_ACTORS = "http://192.168.0.101:9090/api-gateway/actors";
    public static final String URL_API_MOVIES = "http://192.168.0.101:9090/api-gateway/movies";
    public static final String URL_API_DIRECTORS = "http://192.168.0.101:9090/api-gateway/directors";

    public static final String[] FONTPATH =
            {  "fonts/ZonaPro-Thin.ttf", "fonts/ZonaPro-Bold.ttf" };

    public static void changueActivity(Activity activity, String type, Object o) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("object", o instanceof Movie?
                                      (Movie) o: (o instanceof Actor? (Actor) o: (Director) o));
        activity.startActivity(intent);
        activity.finish();
    }

    public static void goBackActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public static Movie jsonToMovie(JSONObject json) throws JSONException {
        Movie movie = new Movie(json.getString("title"), json.getString("imdbId"));
        movie.setReleased(json.getString("released"));
        movie.setRated(json.getString("rated"));
        movie.setStudio(json.getString("studio"));
        movie.setDescription(json.getString("description"));
        movie.setGenre(json.getString("genre"));
        movie.setLanguage(json.getString("language"));
        movie.setRuntime(json.getString("runtime"));
        movie.setImageURL(json.getString("imageURL"));
        return movie;
    }

    public static MatrixCursor jsonToCursor (List<Suggestion> movieSuggestions) {
        MatrixCursor cursor = new MatrixCursor(Utils.SUGGESTIONS_COLUMS);
        for (int i = 0; i < movieSuggestions.size(); i++) {
            String[] temp = new String[3];
            temp[0] = Integer.toString(i);
            temp[1] = movieSuggestions.get(i).getUrl_icon();
            temp[2] = movieSuggestions.get(i).getText();
            cursor.addRow(temp);
        }
        return cursor;
    }

    public static Object jsonToStar (JSONObject json, String type) throws JSONException {
        Object o;
        if (type.equals("Director")) {
            Director director = new Director(json.getString("name"), json.getString("imdbId"));
            director.setBirthDay(json.getString("birthDay"));
            director.setDeathDay(json.getString("deathDay"));
            director.setBirthPlace(json.getString("birthPlace"));
            director.setBiography(json.getString("biography"));
            director.setGender(json.getString("gender"));
            director.setImageURL(json.getString("imageURL"));
            o = director;
        }
        else
        {
            Actor actor = new Actor(json.getString("name"), json.getString("imdbId"));
            actor.setBirthDay(json.getString("birthDay"));
            actor.setDeathDay(json.getString("deathDay"));
            actor.setBirthPlace(json.getString("birthPlace"));
            actor.setBiography(json.getString("biography"));
            actor.setGender(json.getString("gender"));
            actor.setImageURL(json.getString("imageURL"));
            o = actor;
        }
        return o;
    }
}
