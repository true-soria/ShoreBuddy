package com.example.shorebuddy.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.viewpager.widget.PagerAdapter;

import com.example.shorebuddy.data.catches.CatchPhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> listImagePaths;

    public ImageAdapter(Context context) {
        this.mContext = context;
    }

    public void setImagePaths(List<CatchPhoto> imagePaths) {
        for(CatchPhoto imagePath : imagePaths){
            listImagePaths.add(imagePath.path);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listImagePaths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
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
        imageView.setImageURI(Uri.fromFile(new File(listImagePaths.get(position))));
        container.addView(imageView, 0);
        return imageView;
    }
}
