package com.jaehong.kcci.memorydiary;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by jeahong on 2018-05-14.
 */

public class CoustomDialog{
    AlertDialog.Builder builder;

    AlertDialog alertDialog;

    public CoustomDialog(Context context, int layout, Bitmap bitmap){

        LayoutInflater inflater
                = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);

        ImageView imageView = view.findViewById(R.id.image_dialog);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(false);

        builder = new AlertDialog.Builder(context);
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();
    }

}
