package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.UserInfo;

/**
 * Created by shkbhbb on 2/20/18.
 */

public class LogInEvent extends Event {

  private boolean isOk;
  private UserInfo userInfo;

  public LogInEvent(boolean isOk, String msg, UserInfo userInfo) {
    this.isOk = isOk;
    this.msg = msg;
    this.userInfo = userInfo;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }
}
