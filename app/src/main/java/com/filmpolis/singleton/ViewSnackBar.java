package com.filmpolis.singleton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import com.filmpolis.R;
import com.filmpolis.utils.ColorSnackbar;
import com.filmpolis.utils.Utils;

/**
 * Created by Miguel on 29/09/2017.
 */

public class ViewSnackBar {
    private static final ViewSnackBar ourInstance = new ViewSnackBar();
    private Snackbar snackbar;
    private Typeface fontType;

    public static ViewSnackBar getInstance() {
        return ourInstance;
    }

    private ViewSnackBar() {
    }

    public void viewSnackBar(View view, String msgWs){
        fontType = Typeface.createFromAsset(view.getContext()
                .getAssets(), Utils.FONTPATH[0]);

        //if (msgError){
        snackbar = Snackbar.make(view, msgWs, 3000);
        ColorSnackbar.alert(snackbar,
                view.getContext().getResources().getColor(R.color.colorPrimaryLight),
                view.getContext().getResources().getColor(R.color.colorLightGray)).show();

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTypeface(fontType);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(view.getContext().getResources()
                    .getColor(R.color.colorLightGray, view.getContext().getTheme()));
        } else {
            textView.setTextColor(view.getContext()
                    .getResources().getColor(R.color.colorLightGray));
        }

        snackbar.show();
    }
}