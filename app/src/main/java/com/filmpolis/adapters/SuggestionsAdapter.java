package com.filmpolis.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.filmpolis.R;
import com.filmpolis.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * Created by Miguel on 20/09/2017.
 */

public class SuggestionsAdapter extends SimpleCursorAdapter {
    private Typeface font;

    public SuggestionsAdapter(Context context, int layout,
                              Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

        font = Typeface.createFromAsset(context.getAssets(), Utils.FONTPATH[0]);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imgSuggestion = (ImageView) view.findViewById(R.id.suggestion_icon);
        if (!cursor.getString(1).toString().isEmpty()
                && !cursor.getString(1).toString().equals("N/A"))
            Picasso.with(view.getContext()).load(cursor.getString(1).toString()).into(imgSuggestion);

        TextView tvSuggestion = (TextView) view.findViewById(R.id.suggestion_text);
        tvSuggestion.setText(cursor.getString(2));
        tvSuggestion.setTypeface(font);

    }
}
