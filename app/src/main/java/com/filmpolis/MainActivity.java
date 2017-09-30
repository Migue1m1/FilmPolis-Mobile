package com.filmpolis;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.filmpolis.adapters.SuggestionsAdapter;
import com.filmpolis.services.ActorService;
import com.filmpolis.services.DirectorService;
import com.filmpolis.services.MovieService;
import com.filmpolis.utils.ActorServiceType;
import com.filmpolis.utils.DirectorServiceType;
import com.filmpolis.utils.MovieServiceType;
import com.filmpolis.utils.ServiceState;
import com.filmpolis.utils.Utils;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
                                                               SearchView.OnQueryTextListener,
                                                               SearchView.OnSuggestionListener,
                                                               AdapterView.OnItemSelectedListener {
    private static ServiceState appState = ServiceState.MOVIES;
    private SearchView searchView;
    private SuggestionsAdapter searchViewAdapter;
    private Typeface font;
    private Spinner spinner;
    private MovieService movieService;
    private ActorService actorService;
    private DirectorService directorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGUI();

        movieService = null;
        actorService = null;
        directorService = null;
    }

    private void initGUI() {
        font = Typeface.createFromAsset(getAssets(), Utils.FONTPATH[0]);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryRefinementEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        searchViewAdapter = new SuggestionsAdapter(this,
                R.layout.list_search_suggestions, null, Utils.SUGGESTIONS_COLUMS, null, -1000);
        searchView.setSuggestionsAdapter(searchViewAdapter);
        searchView.findViewById(android.support.v7.appcompat.R.id.search_plate)
                .setBackgroundResource(R.drawable.search_view_rounded_background);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setTextColor(getResources().getColor(R.color.colorPrimary));
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.colorHint));
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setTypeface(font);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        ((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn))
                .setColorFilter(ContextCompat
                        .getColor(MainActivity.this, R.color.colorVine), PorterDuff.Mode.SRC_IN);
        searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn)
                .setOnClickListener(this);
        ((ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon))
                .setImageDrawable(null);

        /*** Inicialización de Spinner de servicios ***/
        spinner = (Spinner) findViewById(R.id.sp_service);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>
                        (this,
                                R.layout.service_item,
                                getResources()
                                        .getStringArray(R.array.services_array)){
                    public View getView
                            (int position,
                             View convertView,
                             ViewGroup parent){
                        View v = super.getView(position, convertView, parent);
                        ((TextView) v).setTypeface(font);
                        v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        return v;
                    }

                    public View getDropDownView
                            (int position,
                             View convertView,
                             ViewGroup parent){
                        View v = super.getDropDownView(position, convertView, parent);
                        ((TextView) v).setTypeface(font);
                        v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        return v;
                    }
                };
        adapter.setDropDownViewResource(R.layout.services_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_close_btn:
                cancelTask();
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 0) {
            selectSuggestionsService(newText);
        }
        return true;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
        String suggestion = cursor.getString(2);
        searchView.setQuery(suggestion, false);
        searchView.clearFocus();

        selectDetailsService(suggestion);

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String opt = ((TextView) view).getText().toString();

        cancelTask();

        if (opt.equals("Movies")) {
            appState = ServiceState.MOVIES;
            ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                    .setHint(getResources().getString(R.string.hint_search_movie));
        }
        else
            if (opt.equals("Actors")) {
                appState = ServiceState.ACTORS;
                ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                        .setHint(getResources().getString(R.string.hint_search_actor));
            }
            else {
                appState = ServiceState.DIRECTORS;
                ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                        .setHint(getResources().getString(R.string.hint_search_director));
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void selectSuggestionsService(String newText) {
        HashMap<String, Object> params = new HashMap<>();
        switch (appState) {
            case MOVIES:
                params.put("option", MovieServiceType.GET_SUGGESTIONS);
                params.put("searchViewAdapter", searchViewAdapter);
                params.put("handleActivity", this);

                movieService = new MovieService(params);
                movieService.execute(newText);

                break;
            case ACTORS:
                params.put("option", ActorServiceType.GET_SUGGESTIONS);
                params.put("searchViewAdapter", searchViewAdapter);
                params.put("handleActivity", this);

                actorService = new ActorService(params);
                actorService.execute(newText);

                break;
            case DIRECTORS:
                params.put("option", DirectorServiceType.GET_SUGGESTIONS);
                params.put("searchViewAdapter", searchViewAdapter);
                params.put("handleActivity", this);

                directorService = new DirectorService(params);
                directorService.execute(newText);

                break;
        }
    }

    private void selectDetailsService(String text) {
        HashMap<String, Object> params = new HashMap<>();

        switch (appState) {
            case MOVIES:
                params.put("option", MovieServiceType.GET_BY_TITLE);
                params.put("searchViewAdapter", searchViewAdapter);
                params.put("handleActivity", this);

                movieService = new MovieService(params);
                movieService.execute(text);

                break;
            case ACTORS:
                params.put("option", ActorServiceType.GET_BY_NAME);
                params.put("searchViewAdapter", searchViewAdapter);
                params.put("handleActivity", this);

                actorService = new ActorService(params);
                actorService.execute(text);

                break;
            case DIRECTORS:
                params.put("option", DirectorServiceType.GET_BY_NAME);
                params.put("searchViewAdapter", searchViewAdapter);
                params.put("handleActivity", this);

                directorService = new DirectorService(params);
                directorService.execute(text);

                break;
        }

    }

    private void cancelTask() {
        ((EditText) searchView.findViewById(
                android.support.v7.appcompat.R.id.search_src_text)).setText("");
        ((AVLoadingIndicatorView) findViewById(R.id.avi_loading)).hide();
        if (movieService != null)
            movieService.cancel(true);
        if (actorService != null)
            actorService.cancel(true);
        if (directorService != null)
            directorService.cancel(true);
    }
}
