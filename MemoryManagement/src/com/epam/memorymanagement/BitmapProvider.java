package com.epam.memorymanagement;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BitmapProvider {

    private static final String LOG_TAG = "BitmapProvider";

    private static int MAX_IN_SAMPLE_SIZE = 10;

    private static BitmapProvider instance;

    private int selectedImageIndex = 0;

    private List<BitmapProviderListener> listeners = new ArrayList<BitmapProviderListener>();

    private SparseArray<WeakReference<Bitmap>> cache = new SparseArray<WeakReference<Bitmap>>();

    private WeakReference<Context> contextReference = new WeakReference<Context>(null);

    private BitmapProvider(Context context) {
        super();
        if (contextReference.get() == null) {
            contextReference = new WeakReference<Context>(context);
        }
    }

    public static BitmapProvider getInstance(Context context) {
        if (instance == null) {
            instance = new BitmapProvider(context);
        }
        return instance;
    }

    public void addListener(BitmapProviderListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            Log.i(ImagesApplication.TAG, "added listener, " + listeners.size());
        }
    }

    public void removeListener(BitmapProviderListener listener) {
        if (listeners != null) listeners.remove(listener);
    }

    public int getCount() {
        return 22;
    }

    public int getSelectedImageIndex() {
        return selectedImageIndex;
    }

    public void setSelectedImage(int index) {
        selectedImageIndex = index;
        for (BitmapProviderListener listener : listeners) {
            listener.onSelectedImageChanged();
        }
    }

    public void setImage(ImageView imageView, final int index, int width, int height) {
        WeakReference<Bitmap> bitmapWeakReference = cache.get(index);
        if (bitmapWeakReference != null) {
            Bitmap cached = bitmapWeakReference.get();
            if (cached != null) {

                Log.d(LOG_TAG, "get from cache, name " + index);
                imageView.setImageBitmap(cached);

            }
        }
        loadBitmap(imageView, index, width, height);
    }

//    public Bitmap getImage(int index, int width, int height) {
//        return Bitmap.createScaledBitmap(getImage(index), width, height, false);
//    }

    private void loadBitmap(final ImageView imageView, final int index, int width, int height) {
        AsyncTask<Integer, Void, Bitmap> task = new AsyncTask<Integer, Void, Bitmap>() {

            @Override protected void onPreExecute() {
                imageView.setImageBitmap(null);

            }

            @Override protected Bitmap doInBackground(Integer... params) {
                AssetManager assetManager = ImagesApplication.App.get().getAssets();
                final Integer index = params[0];
                String imageName = "images/" + (index + 1) + ".jpg";

                Log.d(LOG_TAG, imageName);
                InputStream inputStream = null;
                Bitmap bitmap = null;
                try {
                    inputStream = assetManager.open(imageName);

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inJustDecodeBounds = true;

                    BitmapFactory.decodeStream(inputStream, null, options);

                    options.inSampleSize = calculateInSampleSize2(options, params[1], params[2]);

                    Log.d(LOG_TAG, "input width = " + options.outWidth + ", " + "input height = " + options.outHeight);
                    Log.d(LOG_TAG, "sample size = " + options.inSampleSize);
                    Log.d(LOG_TAG, "format = " + options.outMimeType);

                    options.inJustDecodeBounds = false;

                    bitmap = BitmapFactory.decodeStream(inputStream, null, options);



                } catch (IOException e) {
                    Log.d("BitmapProvider", "error loading image " + imageName);
                } finally {
                    IO.close(inputStream);
                }
                return bitmap;
            }

            @Override protected void onPostExecute(Bitmap bm) {
                imageView.setImageBitmap(bm);
                cache.put(index, new WeakReference<Bitmap>(bm));
                Log.d(LOG_TAG, "cache put ind: " + index);
                Log.d(LOG_TAG, "cache size: " + cache.size());
            }
        };
        if (Build.VERSION.SDK_INT > 10)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, index, width, height);
        else
            task.execute(index, width, height);


    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        Log.d(LOG_TAG, String.format("reqWidth = %d, reqHeight = %d", reqWidth, reqHeight));
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int calculateInSampleSize2(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    private int getMemClass() { //TODO use with LRUCache
        if (contextReference.get() != null) {
            ActivityManager activityManager = (ActivityManager) contextReference.get().getSystemService(Context.ACTIVITY_SERVICE);
            return activityManager.getMemoryClass();
        } else return -1;
    }

    public void cleanCache() {
        cache.clear();
    }
}
