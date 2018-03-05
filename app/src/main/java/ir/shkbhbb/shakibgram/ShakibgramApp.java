package ir.shkbhbb.shakibgram;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by shkbhbb on 2/18/18.
 */

public class ShakibgramApp extends Application {

  public static ShakibgramApp ShakibgramApp;
  public static SharedPreferences sPreference;


  public static ShakibgramApp getInstance() {
    return ShakibgramApp;
  }

  public static synchronized SharedPreferences getPreference() {
    if (sPreference == null) {
      sPreference = PreferenceManager
          .getDefaultSharedPreferences(ShakibgramApp.getApplicationContext());
    }
    return sPreference;
  }

  public static Context getAppContext() {
    return getAppContext();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    ShakibgramApp = this;
    EmojiManager.install(new IosEmojiProvider());
//    initFont();
  }

  private void initFont() {
    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        .setDefaultFontPath("fonts/IRANSansMobile.ttf")
        .setFontAttrId(R.attr.fontPath)
        .build()
    );
  }
}
