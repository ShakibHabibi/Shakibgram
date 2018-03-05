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
import ir.shkbhbb.shakibgram.event.ChangePasswordVerificationEvent;
import ir.shkbhbb.shakibgram.event.CheckUsernameEvent;
import ir.shkbhbb.shakibgram.event.Event;
import ir.shkbhbb.shakibgram.utils.Constant;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RestorePasswordActivity extends AppCompatActivity {

  @BindView(R.id.user_name_edt)
  EditText userNameEdt;
  @BindView(R.id.user_name_error_tv)
  TextView userNameErrorTv;
  @BindView(R.id.email_edt)
  EditText emailEdt;
  @BindView(R.id.email_error_tv)
  TextView emailErrorTv;

  private AuthServiceImpl authDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_restore_password);
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
      userNameErrorTv.setText(getString(R.string.please_enter_user_name));
    } else {
      userNameErrorTv.setVisibility(View.INVISIBLE);
    }
    if (emailEdt.getVisibility() == View.VISIBLE && TextUtils
        .isEmpty(emailEdt.getText().toString().trim())) {
      isValid = false;
      emailErrorTv.setVisibility(View.VISIBLE);
      emailErrorTv.setText(getString(R.string.please_enter_email));
    } else if (emailEdt.getVisibility() == View.VISIBLE && !android.util.Patterns.EMAIL_ADDRESS
        .matcher(emailEdt.getText().toString().trim())
        .matches()) {
      isValid = false;
      emailErrorTv.setVisibility(View.VISIBLE);
      emailErrorTv.setText(getString(R.string.please_enter_valid_email));
    } else {
      emailErrorTv.setVisibility(View.INVISIBLE);
    }
    return isValid;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof CheckUsernameEvent) {
      if (((CheckUsernameEvent) event).isOk() && !TextUtils
          .isEmpty(((CheckUsernameEvent) event).getEmailAddress())) {
        emailEdt.setVisibility(View.VISIBLE);
        StringBuilder emailAddress = new StringBuilder(
            ((CheckUsernameEvent) event).getEmailAddress());

        int indexOfAtSign = emailAddress.indexOf("@");
        if (indexOfAtSign > 4) {
          for (int i = 3; i < indexOfAtSign; i++) {
            emailAddress.setCharAt(i, '*');
          }
        } else {
          for (int i = 0; i < indexOfAtSign; i++) {
            emailAddress.setCharAt(i, '*');
          }
        }
        emailEdt.setHint(emailAddress.toString());
      } else {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
      }
    } else if (event instanceof ChangePasswordVerificationEvent) {
      if (((ChangePasswordVerificationEvent) event).isOk()) {
        Intent intent = new Intent(this, VerifyCodeActivity.class);
        intent
            .putExtra(Constant.USER_NAME, ((ChangePasswordVerificationEvent) event).getUsername());
        intent.putExtra(Constant.IS_FROM_CHANGE_PASSWORD, true);
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

  @OnClick({R.id.back_img, R.id.recovery_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.recovery_btn:
        if (isValid()) {
          if (emailEdt.getVisibility() == View.GONE) {
            authDao.checkUsername(this, userNameEdt.getText().toString().trim());
          } else {
            authDao.changePasswordVerification(this, userNameEdt.getText().toString().trim(),
                emailEdt.getText().toString().trim());
          }
        }
        break;
    }
  }
}
