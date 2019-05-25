package com.moehandi.moktest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Random;

/**
 * Created by moehandi on 23/5/19.
 */

public class AppUtil {

    public static int getRandomNumber(int min, int max) {

        Random rand = new Random();
        int number = rand.nextInt(max) + min;
        return number;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
