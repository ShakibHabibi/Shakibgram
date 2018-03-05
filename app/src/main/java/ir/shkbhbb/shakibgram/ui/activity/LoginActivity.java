package ir.shkbhbb.shakibgram.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.impl.AuthServiceImpl;
import ir.shkbhbb.shakibgram.event.Event;
import ir.shkbhbb.shakibgram.event.LogInEvent;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends AppCompatActivity {

  @BindView(R.id.user_name_edt)
  EditText userNameEdt;
  @BindView(R.id.password_edt)
  EditText passwordEdt;
  @BindView(R.id.user_name_error_tv)
  TextView userNameErrorTv;
  @BindView(R.id.password_error_tv)
  TextView passwordErrorTv;
  private AuthServiceImpl authDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    ButterKnife.bind(this);
    init();
  }

  private void init() {
    authDao = new AuthServiceImpl();
  }

  private boolean isValid() {
    boolean isValid = true;
    if (TextUtils.isEmpty(userNameEdt.getText().toString().trim())) {
      isValid = false;
      userNameErrorTv.setVisibility(View.VISIBLE);
      userNameErrorTv.setText(R.string.please_enter_user_name);
    } else {
      userNameErrorTv.setVisibility(View.INVISIBLE);
    }
    if (TextUtils.isEmpty(passwordEdt.getText().toString().trim())) {
      isValid = false;
      passwordErrorTv.setVisibility(View.VISIBLE);
      passwordErrorTv.setText(R.string.please_enter_password);
    } else {
      passwordErrorTv.setVisibility(View.INVISIBLE);
    }
    return isValid;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof LogInEvent) {
      if (((LogInEvent) event).isOk()) {
        PreferenceHelper.setUserInfo(((LogInEvent) event).getUserInfo());
        PreferenceHelper.setHasLoggedIn(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      } else {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (!EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().register(this);
    }
  }

  @OnClick({R.id.back_img, R.id.login_btn, R.id.forget_password_tv})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.forget_password_tv:
        startActivity(new Intent(this, RestorePasswordActivity.class));
        break;
      case R.id.login_btn:
        if (isValid()) {
          authDao.login(this,
              passwordEdt.getText().toString().trim(),
              userNameEdt.getText().toString().trim());
        }
        break;
    }
  }
}
