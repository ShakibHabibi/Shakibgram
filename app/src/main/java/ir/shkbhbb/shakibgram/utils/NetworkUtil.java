package ir.shkbhbb.shakibgram.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtil {
  public static Handler handler;

  public static boolean isConnectedToInternet(Context context) {
    ConnectivityManager conManager = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = conManager.getActiveNetworkInfo();
    return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
  }

  public static boolean hasActiveInternetConnection() {
    try {
      HttpURLConnection httpURLConnection = (HttpURLConnection) (new URL("http://37.228.137.38/").openConnection());
      httpURLConnection.setRequestProperty("User-Agent", "Test");
      httpURLConnection.setRequestProperty("Connection", "close");
      httpURLConnection.setConnectTimeout(1500);
      httpURLConnection.connect();
      return (httpURLConnection.getResponseCode() == 200);
    } catch (IOException e) {
      Log.e("NETWORK", "Error checking internet connection");
    }
    return false;
  }

  public static Handler getHandler() {
    if (handler == null)
      handler = new Handler();
    return handler;
  }
}
