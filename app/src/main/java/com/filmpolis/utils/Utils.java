package com.filmpolis.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.MatrixCursor;
import android.text.format.DateFormat;
import android.view.inputmethod.InputMethodManager;

import com.filmpolis.DetailsActivity;
import com.filmpolis.MainActivity;
import com.filmpolis.models.Actor;
import com.filmpolis.models.Director;
import com.filmpolis.models.Movie;
import com.filmpolis.models.Suggestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Miguel on 20/09/2017.
 */

public class Utils {
    public static final String[] SUGGESTIONS_COLUMS = new String[]{"_id", "icon", "suggestion"};

    public static final String URL_API_ACTORS = "http://192.168.0.106:9090/api-gateway/actors";
    public static final String URL_API_MOVIES = "http://192.168.0.106:9090/api-gateway/movies";
    public static final String URL_API_DIRECTORS = "http://192.168.0.106:9090/api-gateway/directors";

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
        Movie movie = new Movie(json.getString("title"),
                json.getString("imdbId").isEmpty()? "N/A": json.getString("imdbId"));
        movie.setReleased(json.getString("released").isEmpty()? "N/A": json.getString("released"));
        movie.setRated(json.getString("rated").isEmpty()? "N/A": json.getString("rated"));
        movie.setStudio(json.getString("studio"));
        movie.setDescription(json.getString("description"));
        movie.setGenre(json.getString("genre").isEmpty()? "N/A": json.getString("genre"));
        movie.setLanguage(json.getString("language"));
        movie.setRuntime(json.getString("runtime").isEmpty()? "N/A": json.getString("runtime"));
        movie.setImageURL(json.getString("imageURL"));
        movie.setImdbRating(json.getString("imdbRating").isEmpty()?
                                                        "N/A": json.getString("imdbRating") +"/10");
        movie.setDirectors(rolesToString(json.getJSONArray("directors"), "Directors"));
        movie.setActors(rolesToString(json.getJSONArray("actors"), "Actors"));
        return movie;
    }

    /*** Ocultar teclado ***/
    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
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

    public static Object jsonToStar (JSONObject json, String type) throws JSONException, ParseException {
        Object o;
        if (type.equals("Director")) {
            Director director = new Director(json.getString("name"),
                    json.getString("imdbId").isEmpty()?
                                             "N/A": json.getString("imdbId"));
            director.setBirthDay(json.getString("birthDay").isEmpty()?
                                                "not found!!": json.getString("birthDay"));
            director.setDeathDay(json.getString("deathDay").isEmpty()?
                                                "not yet!!": json.getString("deathDay"));
            director.setBirthPlace(json.getString("birthPlace").isEmpty()?
                                                 "N/A": json.getString("birthPlace"));
            director.setBiography(json.getString("biography").isEmpty()?
                                                              "N/A": json.getString("biography"));
            director.setGender(json.getString("gender"));
            director.setImageURL(json.getString("imageURL"));
            director.setAge(calcAge(director.getBirthDay(),
                    director.getDeathDay().equals("not yet!!")?
                            DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString():
                            director.getDeathDay()));
            o = director;
        }
        else
        {
            Actor actor = new Actor(json.getString("name"), json.getString("imdbId").isEmpty()?
                                                                   "N/A": json.getString("imdbId"));
            actor.setBirthDay(json.getString("birthDay").isEmpty()?
                    "not found!!": json.getString("birthDay"));
            actor.setDeathDay(json.getString("deathDay").isEmpty()?
                    "not yet!!": json.getString("deathDay"));
            actor.setBirthPlace(json.getString("birthPlace").isEmpty()?
                    "N/A": json.getString("birthPlace"));
            actor.setBiography(json.getString("biography").isEmpty()?
                    "N/A": json.getString("biography"));
            actor.setGender(json.getString("gender"));
            actor.setImageURL(json.getString("imageURL"));
            actor.setAge(calcAge(actor.getBirthDay(),
                    actor.getDeathDay().equals("not yet!!")?
                            DateFormat.format("yyyy-MM-dd", new Date().getTime()).toString():
                            actor.getDeathDay()));
            o = actor;
        }
        return o;
    }

    private static String calcAge (String str1, String str2) throws ParseException {
        String age;
        if (str1.equals("not found!!"))
            age = "N/A";
        else {
            Calendar cld1 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cld1.setTime(sdf.parse(str1));
            Calendar cld2 = Calendar.getInstance();
            cld2.setTime(sdf.parse(str2));
            age = Integer.toString(cld2.get(Calendar.YEAR) - cld1.get(Calendar.YEAR));
        }
        return age;
    }

    private static String rolesToString (JSONArray jsonArray, String type) throws JSONException {
        String castOrDirector = "";
        if (jsonArray.length() == 0)
            castOrDirector = "not found!!";

        else {
            for (int i = 0; i < jsonArray.length(); i++) {

                if (type.equals("Actors"))
                    castOrDirector += ((JSONObject) jsonArray.get(i)).getString("name")
                            + " as " + ((JSONObject) jsonArray.get(i)).getString("role")
                            + (i + 1 == jsonArray.length() ? "." : ", ");
                else
                    castOrDirector += ((JSONObject) jsonArray.get(i)).getString("name")
                            + (i + 1 == jsonArray.length() ? "." : ", ");
            }
        }android.util.Log.e("dfgh", castOrDirector);
        return castOrDirector;
    }
}
