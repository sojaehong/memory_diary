package com.jaehong.kcci.memorydiary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaveAndRotation {
    private String imagePath = null;
    private Matrix matrix;

    public ImageSaveAndRotation(){

    }

    public ImageSaveAndRotation(Context context, Bitmap bitmap, String name){

        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard

        String foler_name = "/추억 일기장/";
        String file_name = name+".png";
        String string_path = ex_storage+foler_name;

        File file_path;

        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(string_path+file_name);

            //이미지 사이즈가 너무 커서 리사이즈 해줌.
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 500, 500, true);

            resized.compress(Bitmap.CompressFormat.PNG, 100, out);

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(string_path)));

            imagePath = string_path + file_name;

            out.close();

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }

    }

    public String getImagePath(){ return imagePath; }

    public Bitmap bitmapRotation(String filePath, Bitmap originalBitmap) throws IOException {

        ExifInterface exif = new ExifInterface(filePath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        matrix = new Matrix();

        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }

        originalBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);

        return originalBitmap;
    }

}
