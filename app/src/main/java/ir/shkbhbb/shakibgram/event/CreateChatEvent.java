package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.Chat;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class CreateChatEvent extends Event {

  private boolean isOk;
  private Chat chat;

  public CreateChatEvent(boolean isOk, String msg, Chat chat) {
    this.isOk = isOk;
    this.chat = chat;
    this.msg = msg;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public Chat getChat() {
    return chat;
  }

  public void setChat(Chat chat) {
    this.chat = chat;
  }
}
