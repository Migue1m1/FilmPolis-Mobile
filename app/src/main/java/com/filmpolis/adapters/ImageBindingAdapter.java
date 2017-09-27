package com.filmpolis.adapters;

import android.databinding.BindingAdapter;
import android.provider.Settings;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Miguel on 26/09/2017.
 */

public class ImageBindingAdapter {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView image, String imageURL) {
        if (!imageURL.toString().isEmpty()
                && !imageURL.toString().equals("N/A")){
            Picasso.with(image.getContext()).load(imageURL.toString()).into(image);
        }
    }
}
