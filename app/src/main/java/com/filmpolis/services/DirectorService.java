package com.filmpolis.services;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.filmpolis.adapters.SuggestionsAdapter;
import com.filmpolis.models.Director;
import com.filmpolis.models.Suggestion;
import com.filmpolis.utils.DirectorServiceType;
import com.filmpolis.utils.HttpStatusCode;
import com.filmpolis.utils.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Miguel on 15/09/2017.
 */

public class DirectorService extends AsyncTask<String, Void, String> {
    private static final String URL_DIRECTOR_SERVICE_SUGGESTIONS =
            Utils.URL_API_DIRECTORS + "/suggestions/";
    private static final String URL_DIRECTOR_SERVICE_BY_NAME = Utils.URL_API_DIRECTORS + "/";
    private DirectorServiceType option;
    private SuggestionsAdapter searchViewAdapter;
    private List<Suggestion> directorSuggestions;
    private HttpClient httpclient;
    private HttpGet httpget;
    private HttpResponse response;
    private Activity activity;
    private Director director;

    public DirectorService (HashMap<String, Object> params) {
        activity = (Activity) params.get("handleActivity");
        searchViewAdapter = (SuggestionsAdapter) params.get("searchViewAdapter");
        option = (DirectorServiceType) params.get("option");
        directorSuggestions = new ArrayList<>();
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
                case GET_BY_NAME:
                    findByName(URLEncoder.encode(params[0].replaceAll(" ", "%20"), "UTF-8"));
                    break;
                case GET_BY_IMDB:
                    break;
                case ADD_DIRECTOR:
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
                searchViewAdapter.changeCursor(Utils.jsonToCursor(directorSuggestions));
                break;
            case GET_BY_NAME:
                Utils.changueActivity(activity, "Director", director);
                break;
            case GET_BY_IMDB:
                break;
            case ADD_DIRECTOR:
                break;
        }
    }

    private void findWithSuggestions(String text) {
        String res;

        try {
            httpget = new HttpGet(URL_DIRECTOR_SERVICE_SUGGESTIONS + text);
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
                            suggestion.setText(((JSONObject) data.get(i)).getString("name"));
                            directorSuggestions.add(suggestion);
                        }
                        break;
                    case HttpStatusCode.NOT_FOUND:
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    private void findByName (String name) {
        String res;

        try {
            httpget = new HttpGet(URL_DIRECTOR_SERVICE_BY_NAME + name);
            response = httpclient.execute(httpget);

            if (response != null) {
                res = EntityUtils.toString(response.getEntity());
                JSONObject result = new JSONObject(res); // Convert

                switch (result.getInt("status")) {
                    case HttpStatusCode.OK:
                        JSONObject data = result.getJSONObject("data");
                        director = (Director) Utils.jsonToStar(data, "Director");
                        Log.e("Director: ", director.toString());
                        break;
                    case HttpStatusCode.NOT_FOUND:
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }
}
