package ir.shkbhbb.shakibgram.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.shkbhbb.shakibgram.R;

public class AuthActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
        LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_auth);
    ButterKnife.bind(this);
  }

  @OnClick({R.id.sign_up_tv, R.id.login_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.sign_up_tv:
        startActivity(new Intent(this, SignUpActivity.class));
        break;
      case R.id.login_tv:
        startActivity(new Intent(this, LoginActivity.class));
        break;
    }
  }

  @Override
  public void onBackPressed() {
    finish();
  }
}
