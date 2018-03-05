package ir.shkbhbb.shakibgram.data.model.response;

import ir.shkbhbb.shakibgram.data.model.Contact;
import java.util.List;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class GetContactsResponse extends Response {

  private List<Contact> contacts;

  public GetContactsResponse(boolean isOk, String message,
      List<Contact> contacts) {
    super(isOk, message);
    this.contacts = contacts;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }
}
