package ir.shkbhbb.shakibgram.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    Intent intent;
    if (PreferenceHelper.hasLoggedIn()) {
      intent = new Intent(this, MainActivity.class);
    } else {
      intent = new Intent(this, AuthActivity.class);
    }
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }
}
