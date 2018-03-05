package ir.shkbhbb.shakibgram.event;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class AddContactEvent extends Event {

  private boolean isOk;

  public AddContactEvent(boolean isOk, String msg) {
    this.isOk = isOk;
    this.msg = msg;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }
}
