package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.UserInfo;

/**
 * Created by shkbhbb on 2/19/18.
 */

public class VerifyCodeEvent extends Event {

  private boolean isOk;
  private UserInfo userInfo;

  public VerifyCodeEvent(String msg, boolean isOk, UserInfo userInfo) {
    this.msg = msg;
    this.isOk = isOk;
    this.userInfo = userInfo;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }
}
