package com.filmpolis.services;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.util.Log;

import com.filmpolis.adapters.SuggestionsAdapter;
import com.filmpolis.models.Movie;
import com.filmpolis.models.Suggestion;
import com.filmpolis.utils.MovieServiceType;
import com.filmpolis.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Miguel on 15/09/2017.
 */

public class MovieService extends AsyncTask<String, Void, String> {
    private static final String URL_MOVIE_SERVICE_SUGGESTIONS =
            Utils.URL_API_MOVIES + "/suggestions/";
    private static final String URL_MOVIE_SERVICE_BY_TITLE = Utils.URL_API_MOVIES + "/";
    private MovieServiceType option;
    private SuggestionsAdapter searchViewAdapter;
    private List<Suggestion> movieSuggestions;
    private HttpClient httpclient;
    private HttpGet httpget;
    private HttpResponse response;
    private Activity activity;
    private Movie movie;

    public MovieService (HashMap<String, Object> params) {
        activity = (Activity) params.get("handleActivity");
        searchViewAdapter = (SuggestionsAdapter) params.get("searchViewAdapter");
        option = (MovieServiceType) params.get("option");
        movieSuggestions = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            httpclient = new DefaultHttpClient();
            switch (option) {
                case GET_SUGGESTIONS:
                    findWithSuggestions(URLEncoder.encode(params[0].replaceAll(" ", "%20"), "UTF-8"));
                    break;
                case GET_BY_TITLE:
                    findByTitle(URLEncoder.encode(params[0].replaceAll(" ", "%20"), "UTF-8"));
                    break;
                case GET_BY_IMDB:
                    break;
                case ADD_MOVIE:
                    break;
            }
        } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        switch (option) {
            case GET_SUGGESTIONS:
                searchViewAdapter.changeCursor(Utils.jsonToCursor(movieSuggestions));
                break;
            case GET_BY_TITLE:
                Utils.changueActivity(activity, "Movie", movie);
                break;
            case GET_BY_IMDB:
                break;
            case ADD_MOVIE:
                break;
        }
    }

    private void findWithSuggestions(String text) {
        String res;

        try {
            httpget = new HttpGet(URL_MOVIE_SERVICE_SUGGESTIONS + text);
            response = httpclient.execute(httpget);

            if (response != null) {
                res = EntityUtils.toString(response.getEntity());
                JSONObject result = new JSONObject(res); // Convert

                switch (result.getInt("status")) {
                    case 200:
                        JSONArray data = result.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            Suggestion suggestion = new Suggestion();
                            suggestion.setUrl_icon(((JSONObject) data.get(i)).getString("imageURL"));
                            suggestion.setText(((JSONObject) data.get(i)).getString("title"));
                            movieSuggestions.add(suggestion);
                        }
                        break;
                    case 404:
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    private void findByTitle (String title) {
        String res;

        try {
            httpget = new HttpGet(URL_MOVIE_SERVICE_BY_TITLE + title);
            response = httpclient.execute(httpget);

            if (response != null) {
                res = EntityUtils.toString(response.getEntity());
                JSONObject result = new JSONObject(res); // Convert

                switch (result.getInt("status")) {
                    case 200:
                        JSONObject data = result.getJSONObject("data");
                        movie = Utils.jsonToMovie(data);
                        Log.e("Movie: ", movie.toString());
                        break;
                    case 404:
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }
}
