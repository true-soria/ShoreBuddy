package com.example.shorebuddy.utilities;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NoCache;

public class NetworkAccessor {
    private static NetworkAccessor INSTANCE;
    private final RequestQueue mRequestQueue;
    private final ImageLoader mImageLoader;

    public static synchronized NetworkAccessor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkAccessor();
        }
        return INSTANCE;
    }

    private NetworkAccessor() {
        mRequestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<>(10);
            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
        });

        mRequestQueue.start();
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }
}
