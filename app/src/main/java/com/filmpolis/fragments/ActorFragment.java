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
import com.filmpolis.databinding.FragmentActorDetailsBinding;
import com.filmpolis.models.Actor;
import com.filmpolis.utils.Utils;

/**
 * Created by Miguel on 22/09/2017.
 */

public class ActorFragment extends Fragment implements View.OnClickListener {
    private Typeface fontBold, fontThin;
    private FragmentActorDetailsBinding actorDetailsBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        actorDetailsBinding = DataBindingUtil.inflate
                (inflater, R.layout.fragment_actor_details, container, false);
        Actor actor = (Actor) getActivity().getIntent().getSerializableExtra("object");
        actorDetailsBinding.setActor(actor);

        view = actorDetailsBinding.getRoot();

        initGUI(view);

        return view;
    }

    private void initGUI(View view) {
        fontThin = Typeface.createFromAsset(getActivity().getAssets(), Utils.FONTPATH[0]);
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), Utils.FONTPATH[1]);

        ((TextView) view.findViewById(R.id.tv_name)).setTypeface(fontBold);
        ((TextView) view.findViewById(R.id.tv_age)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_biography)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_birthday)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_deathday)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_birthplace)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_gender)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_imdb_id)).setTypeface(fontThin);

        ((TextView) view.findViewById(R.id.tv_age_text)).setTypeface(fontThin);
        ((EditText) view.findViewById(R.id.tv_biography_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_birthday_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_deathday_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_birthplace_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_gender_text)).setTypeface(fontThin);
        ((TextView) view.findViewById(R.id.tv_imdb_id_text)).setTypeface(fontThin);

        view.findViewById(R.id.tv_biography_text)
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

    @Override
    public void onClick(View view) {

    }
}
