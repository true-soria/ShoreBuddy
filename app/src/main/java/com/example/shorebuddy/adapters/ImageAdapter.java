package com.example.shorebuddy.adapters;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.shorebuddy.R;

import java.io.File;
import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] imageIds = new int[]{R.drawable.ic_full_moon,R.drawable.ic_weather_windy,R.drawable.ic_waxing_crescent};
    private ArrayList<String> imagePaths;
    public ImageAdapter(Context context, ArrayList<String> imagePaths){
        this.mContext = context;
        this.imagePaths = imagePaths;

    }
    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageURI(Uri.fromFile(new File(imagePaths.get(position))));
        container.addView(imageView,0);
        return imageView;
    }
}
