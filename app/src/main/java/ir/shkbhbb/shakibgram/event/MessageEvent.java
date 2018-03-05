package ir.shkbhbb.shakibgram.event;


import ir.shkbhbb.shakibgram.data.model.PrivateMessage;

/**
 * Created by shkbhbb on 2/25/18.
 */

public class MessageEvent extends Event {

  private PrivateMessage privateMessage;

  public MessageEvent(PrivateMessage privateMessage) {
    this.privateMessage = privateMessage;
  }

  public PrivateMessage getPrivateMessage() {
    return privateMessage;
  }

  public void setPrivateMessage(PrivateMessage privateMessage) {
    this.privateMessage = privateMessage;
  }
}
