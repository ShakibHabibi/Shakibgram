package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.Chat;
import java.util.List;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class GetChatsEvent extends Event {

  private boolean isOk;
  private List<Chat> chats;

  public GetChatsEvent(boolean isOk, String msg, List<Chat> chats) {
    this.isOk = isOk;
    this.chats = chats;
    this.msg = msg;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public List<Chat> getChats() {
    return chats;
  }

  public void setChats(List<Chat> chats) {
    this.chats = chats;
  }
}
