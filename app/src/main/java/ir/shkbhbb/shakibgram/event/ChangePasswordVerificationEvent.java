package ir.shkbhbb.shakibgram.event;

/**
 * Created by shkbhbb on 2/20/18.
 */

public class ChangePasswordVerificationEvent extends Event {

  private boolean isOk;
  private String username;

  public ChangePasswordVerificationEvent(boolean isOk, String msg, String username) {
    this.isOk = isOk;
    this.msg = msg;
    this.username = username;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
