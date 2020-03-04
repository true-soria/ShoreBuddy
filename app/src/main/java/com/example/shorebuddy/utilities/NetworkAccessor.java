package com.example.shorebuddy.utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkAccessor {
    private static NetworkAccessor INSTANCE;
    private RequestQueue requestQueue;

    public static synchronized NetworkAccessor getInstance(Context ctx) {
        if (INSTANCE == null) {
            INSTANCE = new NetworkAccessor(ctx);
        }
        return INSTANCE;
    }

    private NetworkAccessor(Context ctx) {
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }
}
