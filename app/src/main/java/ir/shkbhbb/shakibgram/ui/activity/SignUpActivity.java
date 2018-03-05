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
import ir.shkbhbb.shakibgram.event.SignUpEvent;
import ir.shkbhbb.shakibgram.utils.Constant;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SignUpActivity extends AppCompatActivity {


  @BindView(R.id.name_edt)
  EditText nameEdt;
  @BindView(R.id.name_error_tv)
  TextView nameErrorTv;
  @BindView(R.id.email_edt)
  EditText emailEdt;
  @BindView(R.id.email_error_tv)
  TextView emailErrorTv;
  @BindView(R.id.user_name_edt)
  EditText userNameEdt;
  @BindView(R.id.user_name_error_tv)
  TextView userNameErrorTv;
  @BindView(R.id.password_edt)
  EditText passwordEdt;
  @BindView(R.id.password_error_tv)
  TextView passwordErrorTv;
  @BindView(R.id.re_password_edt)
  EditText rePasswordEdt;
  @BindView(R.id.re_password_error_tv)
  TextView rePasswordErrorTv;
  private AuthServiceImpl authDao;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    ButterKnife.bind(this);
    init();
  }

  private void init() {
    authDao = new AuthServiceImpl();
  }

  private boolean isValid() {
    boolean isValid = true;
    if (TextUtils.isEmpty(nameEdt.getText().toString().trim())) {
      isValid = false;
      nameErrorTv.setVisibility(View.VISIBLE);
      nameErrorTv.setText(getString(R.string.please_enter_name));
    } else {
      nameErrorTv.setVisibility(View.INVISIBLE);
    }
    if (TextUtils.isEmpty(emailEdt.getText().toString().trim())) {
      isValid = false;
      emailErrorTv.setVisibility(View.VISIBLE);
      emailErrorTv.setText(getString(R.string.please_enter_email));
    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString().trim())
        .matches()) {
      isValid = false;
      emailErrorTv.setVisibility(View.VISIBLE);
      emailErrorTv.setText(getString(R.string.please_enter_valid_email));
    } else {
      emailErrorTv.setVisibility(View.INVISIBLE);
    }
    if (TextUtils.isEmpty(userNameEdt.getText().toString().trim())) {
      isValid = false;
      userNameErrorTv.setVisibility(View.VISIBLE);
      userNameErrorTv.setText(getString(R.string.please_enter_user_name));
    } else {
      userNameErrorTv.setVisibility(View.INVISIBLE);
    }
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

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof SignUpEvent) {
      if (((SignUpEvent) event).isOk()) {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VerifyCodeActivity.class);
        intent.putExtra(Constant.USER_NAME, ((SignUpEvent) event).getUsername());
        intent.putExtra(Constant.IS_FROM_CHANGE_PASSWORD, false);
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

  @OnClick({R.id.back_img, R.id.sign_up_btn})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.sign_up_btn:
        if (isValid()) {
          authDao.signUp(this,
              nameEdt.getText().toString().trim(),
              userNameEdt.getText().toString().trim(),
              passwordEdt.getText().toString().trim(),
              emailEdt.getText().toString().trim());
        }
        break;
    }
  }
}
