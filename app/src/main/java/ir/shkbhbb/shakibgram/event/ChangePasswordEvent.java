package ir.shkbhbb.shakibgram.event;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class ChangePasswordEvent extends Event {

  private boolean isOk;

  public ChangePasswordEvent(boolean isOk, String message) {
    this.msg = message;
    this.isOk = isOk;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }
}
