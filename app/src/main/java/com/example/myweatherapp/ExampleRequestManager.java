// Copyright 2019 Oath Inc. Licensed under the terms of the zLib license see https://opensource.org/licenses/Zlib for terms.

package com.example.myweatherapp;


import android.content.Context;
import android.os.Bundle;

//import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ExampleRequestManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_request_manager);
    }


    private static ExampleRequestManager sInstance;

    Context mContext;
    RequestQueue mRequestQueue;

    public static synchronized ExampleRequestManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ExampleRequestManager(context);
        }
            return sInstance;
        }

        private ExampleRequestManager(Context context) {
            mContext = context;
            mRequestQueue = Volley.newRequestQueue(mContext);
        }

        public <T> void addToRequestQueue(Request<T> request) {
            mRequestQueue.add(request);
        }


}


