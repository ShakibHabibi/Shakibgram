package ir.shkbhbb.shakibgram.event;

/**
 * Created by shkbhbb on 2/20/18.
 */

public class CheckUsernameEvent extends Event {

  private String emailAddress;
  private boolean isOk;

  public CheckUsernameEvent(boolean isOk, String msg, String emailAddress) {
    this.emailAddress = emailAddress;
    this.isOk = isOk;
    this.msg = msg;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
}
