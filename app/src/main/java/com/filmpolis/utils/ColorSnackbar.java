package com.filmpolis.utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Miguel on 29/09/2017.
 */

public class ColorSnackbar {

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, int colorBackgroundId, int color_text) {
        View snackBarView = getSnackBarLayout(snackbar);
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorBackgroundId);

            TextView textView = (TextView) snackBarView
                    .findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color_text);
            textView.setGravity(Gravity.CENTER);
        }

        return snackbar;
    }

    public static Snackbar info(Snackbar snackbar, int colorBackgroundId, int colorTextId) {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId);
    }
    public static Snackbar warning(Snackbar snackbar, int colorBackgroundId, int colorTextId) {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId);
    }

    public static Snackbar alert(Snackbar snackbar, int colorBackgroundId, int colorTextId) {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId);
    }

    public static Snackbar confirm(Snackbar snackbar, int colorBackgroundId, int colorTextId) {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId);
    }
}