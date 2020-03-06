package com.example.shorebuddy.utilities;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.NoCache;

public class NetworkAccessor {
    private static NetworkAccessor INSTANCE;
    private final RequestQueue requestQueue;

    public static synchronized NetworkAccessor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkAccessor();
        }
        return INSTANCE;
    }

    private NetworkAccessor() {
        requestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
        requestQueue.start();
    }

    public <T> void addToRequestQueue(Request<T> req) {
        requestQueue.add(req);
    }
}
