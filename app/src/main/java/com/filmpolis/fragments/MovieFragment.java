package com.filmpolis.fragments;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.filmpolis.R;
import com.filmpolis.databinding.FragmentMovieDetailsBinding;
import com.filmpolis.models.Movie;
import com.filmpolis.utils.Utils;

/**
 * Created by Miguel on 22/09/2017.
 */

public class MovieFragment extends Fragment {
    private Typeface fontBold, fontThin;
    private FragmentMovieDetailsBinding movieDetailsBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        movieDetailsBinding = DataBindingUtil.inflate
                (inflater, R.layout.fragment_movie_details, container, false);
        Movie movie = (Movie) getActivity().getIntent().getSerializableExtra("object");
        movieDetailsBinding.setMovie(movie);

        view = movieDetailsBinding.getRoot();

        initGUI(view);

        return view;
    }

    private void initGUI(View view) {
        fontThin = Typeface.createFromAsset(getActivity().getAssets(), Utils.FONTPATH[0]);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), Utils.FONTPATH[1]);

        ((TextView) view.findViewById(R.id.tv_title)).setTypeface(fontBold);
        ((TextView) view.findViewById(R.id.tv_description)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_score)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_imdb_id)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_released)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_rated)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_studio)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_genre)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_language)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_runtime)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_directors)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_actors)).setTypeface(fontThin);

        ((EditText) view.findViewById(R.id.tv_description_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_score_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_imdb_id_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_released_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_rated_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_studio_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_genre_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_language_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_runtime_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_directors_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_actors_text)).setTypeface(fontThin);

        view.findViewById(R.id.tv_description_text)
            .setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
                return false;
            }
        });
    }
}