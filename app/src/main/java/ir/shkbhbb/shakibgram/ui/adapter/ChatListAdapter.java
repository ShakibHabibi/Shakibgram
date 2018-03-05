package ir.shkbhbb.shakibgram.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.model.Chat;
import ir.shkbhbb.shakibgram.ui.activity.ChatActivity;
import ir.shkbhbb.shakibgram.ui.adapter.ChatListAdapter.ViewHolder;
import ir.shkbhbb.shakibgram.utils.Constant;
import ir.shkbhbb.shakibgram.utils.DateUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class ChatListAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<Chat> chats;
  private LayoutInflater inflater;

  public ChatListAdapter(Context context,
      List<Chat> chats) {
    this.context = context;
    this.chats = chats;
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_chat_list, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return chats.size();
  }

  public void updateAll(List<Chat> chats) {
    this.chats = chats;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_tv)
    TextView dateTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.last_message_tv)
    TextView lastMessageTv;

    private Chat chat;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      chat = chats.get(position);
      if (!TextUtils.isEmpty(chat.getName())) {
        nameTv.setText(chat.getName());
      }
      if (!TextUtils.isEmpty(chat.getLastMessageText())) {
        lastMessageTv.setText(chat.getLastMessageText());
      }
      Calendar messageCalendar = Calendar.getInstance();
      messageCalendar.setTime(chat.getLastMessageDate());
      int messageDayOfWeek = messageCalendar.get(Calendar.DAY_OF_YEAR);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      int currentDayOfWeek = calendar.get(Calendar.DAY_OF_YEAR);
      if (currentDayOfWeek == messageDayOfWeek) {
        dateTv.setText(
            String.format(Locale.US, "%02d:%02d", messageCalendar.get(Calendar.HOUR_OF_DAY),
                messageCalendar.get(Calendar.MINUTE)));
      } else {
        dateTv.setText(
            DateUtil.convertDate(chat.getLastMessageDate(), DateUtil.GLOBAL_FORMATTER, "FA"));
      }
    }

    @OnClick(R.id.main_lay)
    public void onViewClicked() {
      Intent intent = new Intent(context, ChatActivity.class);
      intent.putExtra(Constant.CHAT, chat);
      context.startActivity(intent);
    }

  }
}
