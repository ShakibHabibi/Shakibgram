package ir.shkbhbb.shakibgram.event;

/**
 * Created by shkbhbb on 2/18/18.
 */

public class SignUpEvent extends Event {

  private boolean isOk;
  private String username;

  public SignUpEvent(boolean isOk, String username, String msg) {
    this.isOk = isOk;
    this.msg = msg;
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }
}
