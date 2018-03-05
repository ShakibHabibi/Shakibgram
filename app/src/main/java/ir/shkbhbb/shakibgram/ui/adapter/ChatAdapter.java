package ir.shkbhbb.shakibgram.ui.adapter;

import static ir.shkbhbb.shakibgram.utils.Constant.TYPE_ME;
import static ir.shkbhbb.shakibgram.utils.Constant.TYPE_OTHERS;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vanniktech.emoji.EmojiTextView;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.impl.MessageServiceImpl;
import ir.shkbhbb.shakibgram.data.model.Chat;
import ir.shkbhbb.shakibgram.data.model.Enum.MessageStatus;
import ir.shkbhbb.shakibgram.data.model.MessageUpdate;
import ir.shkbhbb.shakibgram.data.model.PrivateMessage;
import ir.shkbhbb.shakibgram.data.model.UpdateStatus;
import ir.shkbhbb.shakibgram.ui.adapter.ChatAdapter.ViewHolder;
import ir.shkbhbb.shakibgram.utils.DateUtil;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class ChatAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<PrivateMessage> privateMessages;
  private LayoutInflater inflater;
  private MessageServiceImpl messageService;
  private Chat chat;

  public ChatAdapter(Context context, Chat chat, List<PrivateMessage> privateMessages) {
    this.context = context;
    this.messageService = new MessageServiceImpl();
    this.privateMessages = privateMessages;
    this.chat = chat;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    int layout = viewType == TYPE_ME ? R.layout.item_chat_me : R.layout.item_chat_other;
    View v = inflater.inflate(layout, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return privateMessages.size();
  }

  public void updateAll(List<PrivateMessage> privateMessages) {
    this.privateMessages = privateMessages;
    notifyDataSetChanged();
  }

  public void addItem(PrivateMessage privateMessage) {
    notifyItemInserted(privateMessages.size() - 1);
  }

  public void updateItem(MessageUpdate messageUpdate) {
    for (int i = 0; i < privateMessages.size(); i++) {
      if (privateMessages.get(i).getId() == messageUpdate.getId()) {
        if (messageUpdate.getNewId() != -1) {
          privateMessages.get(i).setId(messageUpdate.getNewId());
        }
        privateMessages.get(i).setStatus(messageUpdate.getStatus());
        notifyItemChanged(i);
        break;
      }
    }
  }

  public void updateItem(UpdateStatus updateStatus) {
    for (int i = 0; i < privateMessages.size(); i++) {
      if (privateMessages.get(i).getId() == updateStatus.getMessageId()) {
        privateMessages.get(i).setStatus(updateStatus.getStatus());
        notifyItemChanged(i);
        break;
      }
    }
  }

  public void loadMore(List<PrivateMessage> privateMessages) {
    this.privateMessages.addAll(0, privateMessages);
    notifyItemRangeInserted(0, privateMessages.size());
  }

  @Override
  public int getItemViewType(int position) {
    if (privateMessages.get(position).getSenderUserId() == PreferenceHelper.getUserInfo().getId()) {
      return TYPE_ME;
    }
    return TYPE_OTHERS;
  }

  public void updateItems(long end, long start) {
    for (int i = 0; i < privateMessages.size(); i++) {
      if (privateMessages.get(i).getId() >= start && privateMessages.get(i).getId() <= end
          && privateMessages.get(i).getSenderUserId() == PreferenceHelper.getUserInfo().getId()) {
        privateMessages.get(i).setStatus(MessageStatus.SEEN.getMessageStatus());
        notifyItemChanged(i);
      }
    }
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_divider_tv)
    TextView dateDividerTv;
    @BindView(R.id.date_lay)
    RelativeLayout dateLay;
    @BindView(R.id.chat_text_tv)
    EmojiTextView chatTextTv;
    @BindView(R.id.chat_time_tv)
    TextView chatTimeTv;
    @Nullable
    @BindView(R.id.deliver_check)
    ImageView deliverCheck;

    private int position;
    private PrivateMessage privateMessage;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.privateMessage = privateMessages.get(position);
      this.position = position;
      setUpDateVisibility();
      if (!TextUtils.isEmpty(privateMessage.getMessage())) {
        chatTextTv.setText(privateMessage.getMessage());
      }
      Calendar messageCalendar = Calendar.getInstance();
      messageCalendar.setTime(privateMessage.getCreateDate());
      chatTimeTv.setText(
          String.format(Locale.US, "%02d:%02d", messageCalendar.get(Calendar.HOUR_OF_DAY),
              messageCalendar.get(Calendar.MINUTE)));
      if (privateMessage.getSenderUserId() == PreferenceHelper.getUserInfo().getId()) {
        if (privateMessage.getStatus() == MessageStatus.POSTING.getMessageStatus()) {
          deliverCheck.setImageResource(R.drawable.ic_query_builder_m_2);
        } else if (privateMessage.getStatus() == MessageStatus.POSTED.getMessageStatus()) {
          deliverCheck.setImageResource(R.drawable.ic_done_18_2);
        } else if (privateMessage.getStatus() == MessageStatus.FAILED.getMessageStatus()) {
          deliverCheck.setImageResource(R.drawable.ic_info);
        } else if (privateMessage.getStatus() == MessageStatus.SEEN.getMessageStatus()) {
          deliverCheck.setImageResource(R.drawable.ic_done_all_blue_18);
        } else if (privateMessage.getStatus() == MessageStatus.RECEIVED.getMessageStatus()) {
          deliverCheck.setImageResource(R.drawable.ic_done_all);
        }
      }
    }

    private void setUpDateVisibility() {
      if (position != 0) {
        Calendar messageCalendar = Calendar.getInstance();
        messageCalendar.setTime(privateMessage.getCreateDate());
        int messageDayOfWeek = messageCalendar.get(Calendar.DAY_OF_YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(privateMessages.get(position - 1).getCreateDate());
        int preDayOfWeek = calendar.get(Calendar.DAY_OF_YEAR);
        if (messageDayOfWeek == preDayOfWeek) {
          dateLay.setVisibility(View.GONE);
        } else {
          dateDividerTv.setText(DateUtil.getFullPersianDate(privateMessage.getCreateDate()));
          dateLay.setVisibility(View.VISIBLE);
        }
      } else {
        dateLay.setVisibility(View.GONE);
      }
    }
  }
}
