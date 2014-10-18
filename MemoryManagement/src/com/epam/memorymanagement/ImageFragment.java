package com.epam.memorymanagement;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ImageFragment extends Fragment {

    private static final String IMAGE_INDEX_EXTRA = "imageIndex";

    private int imageIndex;

    static ImageFragment newInstance(int imageIndex) {
        final ImageFragment f = new ImageFragment();
        final Bundle args = new Bundle();
        args.putInt(IMAGE_INDEX_EXTRA, imageIndex);
        f.setArguments(args);
        return f;
    }

    public ImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageIndex = getArguments() != null ? getArguments().getInt(
                IMAGE_INDEX_EXTRA) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image, container, false);

        final ImageView imageView = (ImageView) v.findViewById(R.id.imageView);

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                ImagesApplication.App.get().images().setImage(imageView, imageIndex, imageView.getWidth(), imageView.getHeight());
                removeOnGlobalLayoutListener(imageView, this);
            }

            public void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
                if (Build.VERSION.SDK_INT < 16) {
                    v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
                } else {
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Load image into ImageView

    }
}
