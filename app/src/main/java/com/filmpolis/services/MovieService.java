package com.filmpolis.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.filmpolis.R;
import com.filmpolis.adapters.SuggestionsAdapter;
import com.filmpolis.models.Movie;
import com.filmpolis.models.Suggestion;
import com.filmpolis.singleton.ViewSnackBar;
import com.filmpolis.utils.HttpStatusCode;
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

import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by Miguel on 15/09/2017.
 */

public class MovieService extends AsyncTask<String, Void, String> {
    public static String URL_MOVIE_SERVICE_SUGGESTIONS =
            Utils.URL_API_MOVIES + "/suggestions/";
    public static String URL_MOVIE_SERVICE_BY_TITLE = Utils.URL_API_MOVIES + "/";
    private MovieServiceType option;
    private SuggestionsAdapter searchViewAdapter;
    private List<Suggestion> movieSuggestions;
    private HttpClient httpclient;
    private HttpGet httpget;
    private HttpResponse response;
    private Activity activity;
    private AVLoadingIndicatorView progressLoading;
    private final ViewSnackBar viewSnackBar = ViewSnackBar.getInstance();
    private Movie movie;
    private boolean err;

    public MovieService (HashMap<String, Object> params) {
        activity = (Activity) params.get("handleActivity");
        searchViewAdapter = (SuggestionsAdapter) params.get("searchViewAdapter");
        option = (MovieServiceType) params.get("option");
        movieSuggestions = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (option == MovieServiceType.GET_BY_TITLE) {
            progressLoading = (AVLoadingIndicatorView) activity.findViewById(R.id.avi_loading);
            progressLoading.show();
        }
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
        if (err) {
            Utils.hideSoftKeyBoard(activity);
            viewSnackBar.viewSnackBar(activity.findViewById(R.id.searchView),
                    activity.getResources().getString(R.string.CANNOT_CONNECT));
        }
        else
            switch (option) {
                case GET_SUGGESTIONS:
                    searchViewAdapter.changeCursor(Utils.jsonToCursor(movieSuggestions));
                    break;
                case GET_BY_TITLE:
                    progressLoading.hide();
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
                    case HttpStatusCode.OK:
                        JSONArray data = result.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            Suggestion suggestion = new Suggestion();
                            suggestion.setUrl_icon(((JSONObject) data.get(i)).getString("imageURL"));
                            suggestion.setText(((JSONObject) data.get(i)).getString("title"));
                            movieSuggestions.add(suggestion);
                        }
                        break;
                    case HttpStatusCode.NOT_FOUND:
                        break;
                }
            }
        } catch (Exception e) {
            err = true;
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
                    case HttpStatusCode.OK:
                        JSONObject data = result.getJSONObject("data");
                        movie = Utils.jsonToMovie(data);
                        Log.e("Movie: ", movie.toString());
                        break;
                    case HttpStatusCode.NOT_FOUND:
                        break;
                }
            }
        } catch (Exception e) {
            err = true;
            Log.e("Error: ", e.getMessage());
        }
    }
}
