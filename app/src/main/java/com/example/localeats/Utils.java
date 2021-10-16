package com.example.localeats;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Utils {
    public static Bitmap getBitmapFromUri(Context context, Uri selectedImage, int requiredSize) {
        Bitmap bitmap = null;
        Bitmap rotatedBitmap = null;
        if (Build.VERSION.SDK_INT < 19) {
            String selectedImagePath = getRealPathFromURI(context, selectedImage);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            bitmap = BitmapFactory.decodeFile(selectedImagePath, decodeFile(options, requiredSize));
            try {
                ExifInterface exif = new ExifInterface(selectedImagePath);
                String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
                int rotationAngle = 0;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_NORMAL:
                        rotationAngle = 0;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotationAngle = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotationAngle = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotationAngle = 270;
                        break;
                    case ExifInterface.ORIENTATION_UNDEFINED:
                        rotationAngle = 0;
                        break;
                    default:
                        rotationAngle = 90;
                }

                Matrix matrix = new Matrix();
                matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, options.outWidth, options.outHeight, matrix, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            ParcelFileDescriptor parcelFileDescriptor;
            try {
                parcelFileDescriptor = context.getContentResolver().openFileDescriptor(selectedImage, "r");
                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, decodeFile(options, requiredSize));
                parcelFileDescriptor.close();
                try {
                    ExifInterface exif = new ExifInterface(getRealPathFromURI(context, selectedImage));
                    String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                    int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
                    int rotationAngle = 0;
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_NORMAL:
                            rotationAngle = 0;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotationAngle = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotationAngle = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotationAngle = 270;
                            break;
                        case ExifInterface.ORIENTATION_UNDEFINED:
                            rotationAngle = 0;
                            break;
                        default:
                            rotationAngle = 90;
                    }
                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
                    rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, options.outWidth, options.outHeight, matrix, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (rotatedBitmap == null) {
            return bitmap;
        } else {
            return rotatedBitmap;
        }
        //return rotatedBitmap;
    }

    /**
     * FUNCTION-2 FOR GETTING IMAGE USING CAMERA AND GALLERY
     **/
    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            Log.e("cursor:",""+cursor.getCount());
            if (cursor.moveToFirst()){
                do{
                    String data = cursor.getString(0);
                    Log.e("data:",""+data);
                    // do what ever you want here
                }while(cursor.moveToNext());
            }
            cursor.moveToFirst();
            result = cursor.getString(0);
            cursor.close();
        }
        return result;
    }

    /**
     * FUNCTION-3 FOR GETTING IMAGE USING CAMERA AND GALLERY
     **/
    private static BitmapFactory.Options decodeFile(BitmapFactory.Options options, int requiredSize) {
        // Find the correct scale value. It should be the power of 2.
        int width_tmp = options.outWidth, height_tmp = options.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < requiredSize && height_tmp < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize

        options.inJustDecodeBounds = false;
        options.inSampleSize = scale;

        return options;
    }
}
