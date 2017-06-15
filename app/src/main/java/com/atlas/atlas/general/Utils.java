package com.atlas.atlas.general;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.atlas.atlas.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Random;


public class Utils {

    public static Bitmap drawableToBitmap(Context context, int DrawableID){
        return drawableToBitmap(ContextCompat.getDrawable(context,DrawableID), 0, 0);
    }

   public   static Bitmap drawableToBitmap(Context context, int DrawableID, int width, int height){
        return drawableToBitmap(ContextCompat.getDrawable(context,DrawableID), width, height);
    }

    public  static Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        if(width == 0 && height == 0) {
            width = drawable.getIntrinsicWidth();
            width = width > 0 ? width : 1;
            height = drawable.getIntrinsicHeight();
            height = height > 0 ? height : 1;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getStatusBarHeight(Context context){
        int result = 0;
        int resourceID = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resourceID > 0 ){
            result = context.getResources().getDimensionPixelSize(resourceID);
        }
        return result;
    }


    public static float dpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static float pixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static int getDisplayDimensions(Context context, Boolean xValue) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (xValue) {
            return size.x;
        } else {
            return size.y;

        }


    }

    static void createNoMediaFile(File file){
        Log.d("debug", "Create nomedia file   #" + file.toString()+ "#");

        try {
            boolean fileCreated = file.createNewFile();
            if(fileCreated) {
                Log.d("debug", "    CreateNomediaFile: successful");
            }else{
                Log.d("debug", "    CreateNomediaFile: Error Occured");}
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static String saveToInternalStorage(Bitmap bitmapImage, Context context, String directoryName, int id){
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(directoryName, Context.MODE_PRIVATE);
        // Create imageDir
        File myPath=new File(directory,id+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public static String dateAI(long timestamp) {

        Calendar smsTime = Calendar.getInstance();
        timestamp = timestamp * 1000;
        smsTime.setTimeInMillis(timestamp);

        Calendar now = Calendar.getInstance();

        long nowt = (System.currentTimeMillis());
        long difference =nowt -timestamp;
        if(difference < 86400000){
            if(difference < 1000*60){
                return "Now";
            }
            if(difference < 3600000){
                return Math.round(difference/60000)+ " min ago";
            }
            return Math.round(difference/3600000)+" hours ago";
        }

        final String timeFormatString = "h:mm";
        final String dateTimeFormatString = "MMM d";
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return DateFormat.format(timeFormatString, smsTime).toString();
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday";
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy", smsTime).toString();
        }

    }

    public static void saveImage(Bitmap b) {

        File myDir=new File("/sdcard/atlas");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 1000000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public  static String getStream(String urlS) {
        try {
            URL url = new URL(urlS);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(1000);
            return urlConnection.getContentEncoding();
        } catch (Exception ex) {
            return null;
        }
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static int  getSoftButtonsBarHeight(Activity context) {
            DisplayMetrics metrics = new DisplayMetrics();
            context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;

    }





}
