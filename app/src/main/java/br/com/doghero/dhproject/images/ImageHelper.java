package br.com.doghero.dhproject.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageHelper {

    public static void loadImage(Context context, String imageUrl, int placeHolderResourceId, ImageView imageView) {
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(placeHolderResourceId)
                .into(imageView);
    }

    public static void loadImageBackground(Context context, String imageUrl, int placeHolderResourceId, final ImageView imageView){
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(placeHolderResourceId)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        imageView.setImageDrawable(placeHolderDrawable);
                    }
                });
    }

}
