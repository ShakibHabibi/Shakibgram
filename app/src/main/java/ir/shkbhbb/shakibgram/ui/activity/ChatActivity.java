package ir.shkbhbb.shakibgram.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.EmojiPopup.Builder;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.impl.MessageServiceImpl;
import ir.shkbhbb.shakibgram.data.model.Chat;
import ir.shkbhbb.shakibgram.data.model.Enum.Command;
import ir.shkbhbb.shakibgram.data.model.Enum.MessageStatus;
import ir.shkbhbb.shakibgram.data.model.MessageSocket;
import ir.shkbhbb.shakibgram.data.model.PrivateMessage;
import ir.shkbhbb.shakibgram.data.model.UpdateStatus;
import ir.shkbhbb.shakibgram.data.socket.SendData;
import ir.shkbhbb.shakibgram.event.Event;
import ir.shkbhbb.shakibgram.event.GetMessagesEvent;
import ir.shkbhbb.shakibgram.event.InternetEvent;
import ir.shkbhbb.shakibgram.event.MessageEvent;
import ir.shkbhbb.shakibgram.event.UpdateMessageEvent;
import ir.shkbhbb.shakibgram.event.UpdateStatusEvent;
import ir.shkbhbb.shakibgram.event.UpdateStatusRangeEvent;
import ir.shkbhbb.shakibgram.ui.adapter.ChatAdapter;
import ir.shkbhbb.shakibgram.utils.Constant;
import ir.shkbhbb.shakibgram.utils.NetworkUtil;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ChatActivity extends AppCompatActivity {

  @BindView(R.id.toolbar_title_tv)
  TextView toolbarTitleTv;
  @BindView(R.id.send_img)
  ImageView sendImg;
  @BindView(R.id.user_input_edt)
  EmojiEditText userInputEdt;
  @BindView(R.id.change_keyboard_img)
  ImageView changeKeyboardImg;
  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.activity_chat)
  RelativeLayout activityChat;
  @BindView(R.id.swipe_to_refresh)
  SwipeRefreshLayout swipeToRefresh;

  private Chat chat;
  private List<PrivateMessage> privateMessages;
  private ChatAdapter chatAdapter;
  private MessageServiceImpl messageService;
  private EmojiPopup emojiPopup;
  private boolean isSimpleKeyboard = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    ButterKnife.bind(this);
    getIntentData();
    handleUserInput();
    setData();
    messageService.getMessages(this, String.valueOf(chat.getChatId()), "0", null);
  }

  private void setData() {
    toolbarTitleTv.setText(chat.getName());
    emojiPopup = Builder.fromRootView(activityChat).build(userInputEdt);
    messageService = new MessageServiceImpl();
    swipeToRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        messageService.getMessages(ChatActivity.this, String.valueOf(chat.getChatId()),
            String.valueOf(privateMessages.get(0).getId()), swipeToRefresh);

      }
    });
  }

  /**
   * set send and attach visibility on text change
   */

  private void handleUserInput() {
    TextWatcher textWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before,
          int count) {
        if (s.length() > 0) {
          sendImg.setVisibility(View.VISIBLE);
        } else {
          sendImg.setVisibility(View.GONE);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    };
    userInputEdt.addTextChangedListener(textWatcher);
  }

  private void getIntentData() {
    if (getIntent().getExtras() != null
        && getIntent().getExtras().getSerializable(Constant.CHAT) != null) {
      chat = (Chat) getIntent().getExtras().getSerializable(Constant.CHAT);
    }
  }

  private void setUpRecyclerView() {
    chatAdapter = new ChatAdapter(this, chat, privateMessages);
    LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(chatAdapter);
    scrollToPosition(chatAdapter.getItemCount() - 1);
  }

  /**
   * Scroll to position.
   */

  public void scrollToPosition(int position) {
    recyclerView.scrollToPosition(position);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof GetMessagesEvent) {
      if (((GetMessagesEvent) event).isOk()) {
        if (!((GetMessagesEvent) event).isLoadMore()) {
          privateMessages = ((GetMessagesEvent) event).getPrivateMessages();
          setUpRecyclerView();
        } else {
          chatAdapter.loadMore(((GetMessagesEvent) event).getPrivateMessages());
        }
      } else {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
      }
    } else if (event instanceof MessageEvent) {
      if (((MessageEvent) event).getPrivateMessage().getChatId() == chat.getChatId()) {
        if (privateMessages == null) {
          privateMessages = new ArrayList<>();
        }
        privateMessages.add(((MessageEvent) event).getPrivateMessage());
        chatAdapter.addItem(((MessageEvent) event).getPrivateMessage());
        scrollToPosition(chatAdapter.getItemCount() - 1);
        new SendData(this, new UpdateStatus(chat.getChatId(),
            ((MessageEvent) event).getPrivateMessage().getId(),
            MessageStatus.SEEN.getMessageStatus(),
            ((MessageEvent) event).getPrivateMessage().getSenderUserId()),
            Command.STATUS.getCommand());
      }
    } else if (event instanceof UpdateMessageEvent) {
      chatAdapter.updateItem(((UpdateMessageEvent) event).getMessageUpdate());
    } else if (event instanceof UpdateStatusEvent) {
      chatAdapter.updateItem(((UpdateStatusEvent) event).getUpdateStatus());
    } else if (event instanceof InternetEvent) {
      Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
    } else if (event instanceof UpdateStatusRangeEvent) {
      chatAdapter.updateItems(((UpdateStatusRangeEvent) event).getUpdateStatusRange().getEnd(),
          ((UpdateStatusRangeEvent) event).getUpdateStatusRange().getStart());
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (!EventBus.getDefault().isRegistered(this)) {
      EventBus.getDefault().register(this);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override
  public void onBackPressed() {
    finish();
  }

  private void toggleImage() {
    if (isSimpleKeyboard) {
      changeKeyboardImg.setImageResource(R.drawable.ic_keyboard);
    } else {
      changeKeyboardImg.setImageResource(R.drawable.ic_mood_24);
    }
    isSimpleKeyboard = !isSimpleKeyboard;
  }

  @OnClick({R.id.back_img, R.id.send_img, R.id.change_keyboard_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.send_img:
        if (NetworkUtil.isConnectedToInternet(this)) {
          PrivateMessage privateMessage = new PrivateMessage(new Date().getTime(),
              PreferenceHelper.getUserInfo().getId(), userInputEdt.getText().toString().trim(),
              new Date(),
              MessageStatus.POSTING.getMessageStatus(), chat.getChatId(),
              PreferenceHelper.getUserInfo().getName());
          if (privateMessages == null) {
            privateMessages = new ArrayList<>();
          }
          privateMessages.add(privateMessage);
          chatAdapter.addItem(privateMessage);
          scrollToPosition(chatAdapter.getItemCount() - 1);
          new SendData(this,
              new MessageSocket(privateMessage.getSenderUserId(), privateMessage.getChatId(),
                  privateMessage.getMessage(), privateMessage.getId(),
                  privateMessage.getSenderName()),
              Command.MESSAGE.getCommand());
          userInputEdt.setText("");
        } else {
          Toast.makeText(this, getString(R.string.check_your_internet_connection),
              Toast.LENGTH_SHORT).show();
        }
        break;
      case R.id.change_keyboard_img:
        toggleImage();
        emojiPopup.toggle();
        break;
    }
  }
}
