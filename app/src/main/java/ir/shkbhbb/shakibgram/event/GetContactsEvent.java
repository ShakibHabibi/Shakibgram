package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.Contact;
import java.util.List;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class GetContactsEvent extends Event {

  private boolean isOk;
  private List<Contact> contacts;

  public GetContactsEvent(boolean isOk, String msg, List<Contact> contacts) {
    this.isOk = isOk;
    this.msg = msg;
    this.contacts = contacts;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }
}
