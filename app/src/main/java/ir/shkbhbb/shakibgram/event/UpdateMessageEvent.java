package ir.shkbhbb.shakibgram.event;


import ir.shkbhbb.shakibgram.data.model.MessageUpdate;

/**
 * Created by shkbhbb on 2/25/18.
 */

public class UpdateMessageEvent extends Event {

  private MessageUpdate messageUpdate;

  public UpdateMessageEvent(MessageUpdate messageUpdate) {
    this.messageUpdate = messageUpdate;
  }

  public MessageUpdate getMessageUpdate() {
    return messageUpdate;
  }

  public void setMessageUpdate(MessageUpdate messageUpdate) {
    this.messageUpdate = messageUpdate;
  }
}
