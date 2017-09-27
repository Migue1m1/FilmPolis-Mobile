package com.filmpolis;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.filmpolis.fragments.ActorFragment;
import com.filmpolis.fragments.DirectorFragment;
import com.filmpolis.fragments.MovieFragment;
import com.filmpolis.utils.Utils;

/**
 * Created by Miguel on 22/09/2017.
 */

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private String type;
    private Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        type = getIntent().getExtras().getString("type");

        initGUI();

        if (type.equals("Movie"))
           loadMovieDetails();
        else
            if (type.equals("Actor"))
                loadActorDetails();
            else
                loadDirectorDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initGUI() {
        findViewById(R.id.details_bar).setVisibility(View.VISIBLE);

        font = Typeface.createFromAsset(getAssets(), Utils.FONTPATH[1]);

        ((TextView) findViewById(R.id.tv_info_bar)).setTypeface(font);
        ((TextView) findViewById(R.id.tv_info_bar)).setText(type);

        findViewById(R.id.img_back).setOnClickListener(this);
    }

    private void loadMovieDetails() {
        MovieFragment movieFragment = new MovieFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, movieFragment).commit();
    }

    private void loadActorDetails() {
        ActorFragment actorFragment = new ActorFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, actorFragment).commit();
    }

    private void loadDirectorDetails() {
        DirectorFragment directorFragment = new DirectorFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, directorFragment).commit();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_back:
                findViewById(R.id.details_bar).setVisibility(View.GONE);
                Utils.goBackActivity(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Utils.goBackActivity(this);
    }
}
