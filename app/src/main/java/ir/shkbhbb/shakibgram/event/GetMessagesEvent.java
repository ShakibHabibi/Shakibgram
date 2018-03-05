package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.PrivateMessage;
import java.util.List;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class GetMessagesEvent extends Event {

  private boolean isOk;
  private boolean isLoadMore;
  private List<PrivateMessage> privateMessages;

  public GetMessagesEvent(boolean isOk, String message, boolean isLoadMore,
      List<PrivateMessage> privateMessages) {
    this.isOk = isOk;
    this.isLoadMore = isLoadMore;
    this.msg = message;
    this.privateMessages = privateMessages;
  }

  public boolean isLoadMore() {
    return isLoadMore;
  }

  public void setLoadMore(boolean loadMore) {
    isLoadMore = loadMore;
  }

  public List<PrivateMessage> getPrivateMessages() {
    return privateMessages;
  }

  public void setPrivateMessages(
      List<PrivateMessage> privateMessages) {
    this.privateMessages = privateMessages;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }
}
