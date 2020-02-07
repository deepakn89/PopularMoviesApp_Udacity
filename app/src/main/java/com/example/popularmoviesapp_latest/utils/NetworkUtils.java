package com.example.popularmoviesapp_latest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Network activeNetwork = connectivity.getActiveNetwork();
        if (activeNetwork == null) {
            Log.d(TAG,"No active network.");
            return false;
        }
        NetworkCapabilities networkCapabilities =
                connectivity.getNetworkCapabilities(activeNetwork);
        boolean validated =
                networkCapabilities == null
                        || !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
        return !validated;
    }
}
