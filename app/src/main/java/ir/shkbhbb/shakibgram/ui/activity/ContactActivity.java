package ir.shkbhbb.shakibgram.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.impl.ContactServiceImpl;
import ir.shkbhbb.shakibgram.data.model.Contact;
import ir.shkbhbb.shakibgram.data.model.Enum.Command;
import ir.shkbhbb.shakibgram.data.model.Enum.MessageStatus;
import ir.shkbhbb.shakibgram.data.model.UpdateStatus;
import ir.shkbhbb.shakibgram.data.socket.SendData;
import ir.shkbhbb.shakibgram.event.CreateChatEvent;
import ir.shkbhbb.shakibgram.event.Event;
import ir.shkbhbb.shakibgram.event.GetContactsEvent;
import ir.shkbhbb.shakibgram.event.MessageEvent;
import ir.shkbhbb.shakibgram.ui.adapter.ContactAdapter;
import ir.shkbhbb.shakibgram.utils.Constant;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import ir.shkbhbb.shakibgram.utils.SearchInputView;
import ir.shkbhbb.shakibgram.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ContactActivity extends AppCompatActivity {

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;
  @BindView(R.id.swipe_to_refresh)
  SwipeRefreshLayout swipeToRefresh;
  @BindView(R.id.back_clear_img)
  ImageView backClearImg;
  @BindView(R.id.search_contact_siv)
  SearchInputView searchContactSiv;
  @BindView(R.id.back_search_img)
  ImageView backSearchImg;
  @BindView(R.id.search_layout)
  LinearLayout searchLayout;

  private ContactServiceImpl contactService;
  private ContactAdapter contactAdapter;
  private List<Contact> contacts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact);
    ButterKnife.bind(this);
    init();
    contactService.getContacts(this, String.valueOf(PreferenceHelper.getUserInfo().getId()),
        null);
    onTextChange();
  }

  private void init() {
    contactService = new ContactServiceImpl();
    setUpSwipeRefresh();
  }

  private void onTextChange() {
    searchContactSiv.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        final String string = s.toString();
        if (string.equals("") && contactAdapter != null) {
          contactAdapter.updateAll(contacts);
        } else {
          final List<Contact> filteredModelList = filter(string);
          if (filteredModelList != null && contactAdapter != null) {
            contactAdapter.updateAll(filteredModelList);
          }
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (searchContactSiv.getText().toString().equals("")) {
          backClearImg.setVisibility(View.GONE);
        } else {
          backClearImg.setVisibility(View.VISIBLE);
        }
      }

    });
  }

  /**
   * filter contacts
   */

  private List<Contact> filter(String query) {
    if (contacts == null) {
      return null;
    }
    query = query.toLowerCase();
    final List<Contact> filteredModelList = new ArrayList<>();
    for (Contact contact : contacts) {
      final String name = contact.getName();
      if (TextUtils.isEmpty(name)) {
        continue;
      }
      if (name.contains(query)) {
        filteredModelList.add(contact);
      }
    }
    return filteredModelList;
  }

  private void setUpSwipeRefresh() {
    swipeToRefresh.setColorSchemeResources(R.color.colorPrimaryDark);
    swipeToRefresh.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        contactService.getContacts(ContactActivity.this,
            String.valueOf(PreferenceHelper.getUserInfo().getId()), swipeToRefresh);
      }
    });
  }

  private void setUpRecyclerView() {
    contactAdapter = new ContactAdapter(this, contacts);
    LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(contactAdapter);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMessageEvent(Event event) {
    if (event instanceof GetContactsEvent) {
      if (((GetContactsEvent) event).isOk()) {
        contacts = ((GetContactsEvent) event).getContacts();
        if (contactAdapter == null) {
          setUpRecyclerView();
        } else {
          contactAdapter.updateAll(contacts);
        }
      } else {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
      }
    } else if (event instanceof CreateChatEvent) {
      if (((CreateChatEvent) event).isOk()) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(Constant.CHAT, ((CreateChatEvent) event).getChat());
        startActivity(intent);
      } else {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
      }
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

  @OnClick({R.id.back_img, R.id.add_contact_fab, R.id.search_img, R.id.back_clear_img,
      R.id.back_search_img})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.search_img:
        searchLayout.setVisibility(View.VISIBLE);
        searchContactSiv.requestFocus();
        Utils.showKeyboard(this, searchContactSiv);
        break;
      case R.id.back_img:
        onBackPressed();
        break;
      case R.id.add_contact_fab:
        startActivity(new Intent(this, AddContactActivity.class));
        break;
      case R.id.back_clear_img:
        searchContactSiv.setText("");
        break;
      case R.id.back_search_img:
        searchLayout.setVisibility(View.GONE);
        Utils.hideKeyboard(this, searchContactSiv);
        searchContactSiv.setText("");
        if (contactAdapter != null && contacts != null) {
          contactAdapter.updateAll(contacts);
        }
        break;
    }
  }
}
