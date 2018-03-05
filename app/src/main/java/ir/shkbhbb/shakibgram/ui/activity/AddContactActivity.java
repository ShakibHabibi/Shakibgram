package ir.shkbhbb.shakibgram.ui.activity;

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
import ir.shkbhbb.shakibgram.data.api.impl.ContactServiceImpl;
import ir.shkbhbb.shakibgram.data.model.Enum.Command;
import ir.shkbhbb.shakibgram.data.model.Enum.MessageStatus;
import ir.shkbhbb.shakibgram.data.model.UpdateStatus;
import ir.shkbhbb.shakibgram.data.socket.SendData;
import ir.shkbhbb.shakibgram.event.AddContactEvent;
import ir.shkbhbb.shakibgram.event.Event;
import ir.shkbhbb.shakibgram.event.MessageEvent;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import ir.shkbhbb.shakibgram.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AddContactActivity extends AppCompatActivity {

  @BindView(R.id.user_name_edt)
  EditText userNameEdt;
  @BindView(R.id.user_name_error_tv)
  TextView userNameErrorTv;

  private ContactServiceImpl contactService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_contact);
    ButterKnife.bind(this);
    init();
  }

  private void init() {
    contactService = new ContactServiceImpl();
  }

  private boolean isValid() {
    if (TextUtils.isEmpty(userNameEdt.getText().toString().trim())) {
      userNameErrorTv.setText(getString(R.string.please_enter_user_name));
      userNameErrorTv.setVisibility(View.VISIBLE);
      return false;
    }
    userNameErrorTv.setVisibility(View.GONE);
    return true;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof AddContactEvent) {
      if (((AddContactEvent) event).isOk()) {
        onBackPressed();
      }
      Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
    } else if (event instanceof MessageEvent) {
      Utils.sendNotification(this, ((MessageEvent) event).getPrivateMessage().getSenderName(),
          ((MessageEvent) event).getPrivateMessage().getMessage(),((MessageEvent) event).getPrivateMessage().getId());
      new SendData(this, new UpdateStatus(((MessageEvent) event).getPrivateMessage().getChatId(),
          ((MessageEvent) event).getPrivateMessage().getId(),
          MessageStatus.RECEIVED.getMessageStatus(),
          ((MessageEvent) event).getPrivateMessage().getSenderUserId()),
          Command.STATUS.getCommand());
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

  @OnClick({R.id.back_img, R.id.add_contact_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.add_contact_img:
        if (isValid()) {
          contactService.addContact(this, userNameEdt.getText().toString().trim(),
              String.valueOf(PreferenceHelper.getUserInfo().getId()));
        }
        break;
    }
  }
}
