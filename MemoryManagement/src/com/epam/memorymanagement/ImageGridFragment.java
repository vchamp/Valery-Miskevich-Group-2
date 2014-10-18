package com.epam.memorymanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridFragment extends Fragment implements AdapterView.OnItemClickListener, BitmapProviderListener {

    private final int imageHeightPx = 600;
    private final int imageWidthPx = (int) (imageHeightPx * (1300 / 800f));
    private int imageHeightDp;

    private ImageAdapter imageAdapter;

    public ImageGridFragment() {
        super();
    }

    @Override public void onResume() {
        super.onResume();
        getBMProvider().addListener(this);
    }

    @Override public void onPause() {
        getBMProvider().removeListener(this);
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(ImagesApplication.TAG, "ImageGridFragment create");
        super.onCreate(savedInstanceState);

        imageHeightDp = (int) (imageHeightPx / getResources().getDisplayMetrics().density);

        imageAdapter = new ImageAdapter(getActivity());
    }

    @Override
    public void onDestroy() {
        Log.i(ImagesApplication.TAG, "ImageGridFragment destroy");
        super.onDestroy();
    }

    @Override
    protected void finalize() throws Throwable {
        Log.i(ImagesApplication.TAG, "ImageGridFragment finalize");
        super.finalize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        getBMProvider().setSelectedImage(position);
        final Intent i = new Intent(getActivity(), ImagePagerActivity.class);
        startActivity(i);
    }

    @Override
    public void onSelectedImageChanged() {
        imageAdapter.notifyDataSetChanged();
    }

    private class ImageAdapter extends BaseAdapter {

        private final Context mContext;

        int id = 0;

        public ImageAdapter(Context context) {
            super();
            mContext = context;
        }

        @Override
        public int getCount() {
            return getBMProvider().getCount();
        }

        @Override
        public Object getItem(int position) {
            return position+""+id;
        }

        @Override
        public long getItemId(int position) {
            return id++;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            Log.d("TAG", "position "+position);
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(8, 8, 8, 8);
                // imageView.setLayoutParams(new GridView.LayoutParams(
                // LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, imageHeightDp));
            } else {
                imageView = (ImageView) convertView;
            }

            int imageViewWidth = imageView.getWidth();
            int height = imageView.getHeight();
            getBMProvider().setImage(imageView, position, imageViewWidth == 0 ? imageHeightPx : imageViewWidth, height == 0 ? imageHeightPx : height);

            int color = position == getBMProvider().getSelectedImageIndex() ? R.color.grid_state_selected : android.R.color.transparent;

            imageView.setBackgroundColor(getResources().getColor(color));

            return imageView;
        }
    }

    private BitmapProvider getBMProvider() {
        return ImagesApplication.App.get().images();
    }
}
