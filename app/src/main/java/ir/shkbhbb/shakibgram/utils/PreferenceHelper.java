package ir.shkbhbb.shakibgram.utils;

import com.google.gson.Gson;
import ir.shkbhbb.shakibgram.ShakibgramApp;
import ir.shkbhbb.shakibgram.data.model.UserInfo;

public class PreferenceHelper {


  private static final String USER_INFO = "USER_INFO";
  private static final String HAS_LOGGED_IN = "HAS_LOGGED_IN";

  public static UserInfo getUserInfo() {
    Gson gson = new Gson();
    String userInfoToString = ShakibgramApp.getPreference().getString(USER_INFO, "");
    return gson.fromJson(userInfoToString, UserInfo.class);
  }

  public static void setUserInfo(UserInfo userInfo) {
    Gson gson = new Gson();
    String userInfoToJson = gson.toJson(userInfo);
    ShakibgramApp.getPreference().edit().putString(USER_INFO, userInfoToJson).apply();
  }

  public static boolean hasLoggedIn() {
    return ShakibgramApp.getPreference().getBoolean(HAS_LOGGED_IN, false);
  }

  public static void setHasLoggedIn(boolean hasLoggedIn) {
    ShakibgramApp.getPreference().edit().putBoolean(HAS_LOGGED_IN, hasLoggedIn).apply();
  }
}
