package ir.shkbhbb.shakibgram.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.impl.ChatServiceImpl;
import ir.shkbhbb.shakibgram.data.model.Contact;
import ir.shkbhbb.shakibgram.ui.adapter.ContactAdapter.ViewHolder;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import java.util.List;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class ContactAdapter extends Adapter<ViewHolder> {

  private Context context;
  private List<Contact> contacts;
  private LayoutInflater inflater;
  private ChatServiceImpl chatService;

  public ContactAdapter(Context context,
      List<Contact> contacts) {
    this.context = context;
    this.contacts = contacts;
    this.chatService = new ChatServiceImpl();
    this.inflater = LayoutInflater.from(context);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.item_contact_list, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(position);
  }

  @Override
  public int getItemCount() {
    return contacts.size();
  }

  public void updateAll(List<Contact> contacts) {
    this.contacts = contacts;
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contact_name_tv)
    TextView contactNameTv;

    private Contact contact;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setData(int position) {
      this.contact = contacts.get(position);
      if (!TextUtils.isEmpty(contact.getName())) {
        contactNameTv.setText(contact.getName());
      }
    }

    @OnClick(R.id.main_lay)
    public void onViewClicked() {
      chatService.createChat(context,String.valueOf(contact.getUserId()),
          String.valueOf(PreferenceHelper.getUserInfo().getId()));
    }
  }
}
