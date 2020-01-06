package com.refresh.pos.techicalservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by TOUCH on 1/28/2019.
 */

public class NetworkMonitor extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if (checkNetworkConnection(context)){



        }
    }

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }


}
