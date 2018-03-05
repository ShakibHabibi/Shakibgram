package ir.shkbhbb.shakibgram.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.shkbhbb.shakibgram.R;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class Utils {

  public static void hideKeyboard(Context context, View view) {
    InputMethodManager imm = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public static void showKeyboard(Context context, View view) {
    InputMethodManager inputMethodManager = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
        InputMethodManager.SHOW_FORCED, 0);
  }

  public static String toJson(Object o) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(o);
  }

  public static void sendNotification(Context context, String title, String detail, long id) {
    Builder builder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.mipmap.ic_launcher_sh)
        .setContentTitle(title)
        .setContentText(detail)
        .setAutoCancel(true)
        .setGroup("messages")
        .setGroupSummary(true)
        .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager
        .notify((int) id, builder.build());

  }
}
