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
import ir.shkbhbb.shakibgram.event.ChangePasswordEvent;
import ir.shkbhbb.shakibgram.event.Event;
import ir.shkbhbb.shakibgram.utils.Constant;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ResetPasswordActivity extends AppCompatActivity {

  @BindView(R.id.password_edt)
  EditText passwordEdt;
  @BindView(R.id.password_error_tv)
  TextView passwordErrorTv;
  @BindView(R.id.re_password_edt)
  EditText rePasswordEdt;
  @BindView(R.id.re_password_error_tv)
  TextView rePasswordErrorTv;

  private AuthServiceImpl authDao;
  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);
    ButterKnife.bind(this);
    init();
    getIntentData();
  }

  private void init() {
    authDao = new AuthServiceImpl();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof ChangePasswordEvent) {
      if (((ChangePasswordEvent) event).isOk()) {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      }
    } else {
      Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
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

  private void getIntentData() {
    if (getIntent().getExtras() != null
        && getIntent().getExtras().getString(Constant.USER_NAME) != null) {
      username = getIntent().getExtras().getString(Constant.USER_NAME);

    }
  }

  private boolean isValid() {
    boolean isValid = true;
    if (TextUtils.isEmpty(passwordEdt.getText().toString().trim())) {
      isValid = false;
      passwordErrorTv.setVisibility(View.VISIBLE);
      passwordErrorTv.setText(getString(R.string.please_enter_password));
    } else {
      passwordErrorTv.setVisibility(View.INVISIBLE);
    }
    if (TextUtils.isEmpty(rePasswordEdt.getText().toString().trim())) {
      isValid = false;
      rePasswordErrorTv.setVisibility(View.VISIBLE);
      rePasswordErrorTv.setText(getString(R.string.please_enter_password));
    } else if (!rePasswordEdt.getText().toString().trim()
        .equals(passwordEdt.getText().toString().trim())) {
      isValid = false;
      rePasswordErrorTv.setVisibility(View.VISIBLE);
      rePasswordErrorTv.setText(getString(R.string.password_are_not_same));
    } else {
      rePasswordErrorTv.setVisibility(View.INVISIBLE);
    }
    return isValid;
  }

  @OnClick({R.id.back_img, R.id.change_pass_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.change_pass_btn:
        if (isValid()) {
          authDao.changePassword(this, username, passwordEdt.getText().toString().trim());
        }
        break;
    }
  }
}
