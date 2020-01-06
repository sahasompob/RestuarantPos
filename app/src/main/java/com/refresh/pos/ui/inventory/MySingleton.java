package com.refresh.pos.ui.inventory;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by TOUCH on 1/28/2019.
 */

public class MySingleton {

    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton(Context context){

        mCtx = context;
        requestQueue = getRequestQueue();


    }

    private RequestQueue getRequestQueue(){
        if (requestQueue==null)
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        return requestQueue;
    }

    public static synchronized MySingleton getInstance (Context context){
        if (mInstance==null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public <T> void  addtoRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }

    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }


}
