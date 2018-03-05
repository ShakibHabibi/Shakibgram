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
import ir.shkbhbb.shakibgram.event.VerifyCodeEvent;
import ir.shkbhbb.shakibgram.utils.Constant;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VerifyCodeActivity extends AppCompatActivity {

  @BindView(R.id.code_tv)
  EditText codeEdt;
  @BindView(R.id.code_error_tv)
  TextView codeErrorTv;

  private String username;
  private boolean isFromChangePassword;
  private AuthServiceImpl authDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verify_code);
    ButterKnife.bind(this);
    getIntentData();
    init();
  }

  private void init() {
    authDao = new AuthServiceImpl();
  }

  private void getIntentData() {
    if (getIntent().getExtras() != null
        && getIntent().getExtras().getString(Constant.USER_NAME) != null) {
      username = getIntent().getExtras().getString(Constant.USER_NAME);
      isFromChangePassword = getIntent().getExtras().getBoolean(Constant.IS_FROM_CHANGE_PASSWORD);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof VerifyCodeEvent) {
      if (((VerifyCodeEvent) event).isOk()) {
        if (isFromChangePassword) {
          Intent intent = new Intent(this, ResetPasswordActivity.class);
          intent.putExtra(Constant.USER_NAME, username);
          startActivity(intent);
        } else {
          PreferenceHelper.setUserInfo(((VerifyCodeEvent) event).getUserInfo());
          PreferenceHelper.setHasLoggedIn(true);
          Intent intent = new Intent(this, MainActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
          finish();
        }
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

  private boolean isValid() {
    if (TextUtils.isEmpty(codeEdt.getText().toString().trim())) {
      codeErrorTv.setVisibility(View.VISIBLE);
      codeErrorTv.setText(getString(R.string.please_enter_code));
      return false;
    }
    codeErrorTv.setVisibility(View.INVISIBLE);
    return true;
  }

  @OnClick({R.id.back_img, R.id.sign_up_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.sign_up_btn:
        if (isValid()) {
          authDao.verifyCode(this,
              codeEdt.getText().toString().trim(),
              username);
        }
        break;
    }
  }
}
