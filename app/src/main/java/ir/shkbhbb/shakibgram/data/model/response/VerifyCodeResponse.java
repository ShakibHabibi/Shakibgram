package ir.shkbhbb.shakibgram.data.model.response;

import com.google.gson.annotations.SerializedName;
import ir.shkbhbb.shakibgram.data.model.UserInfo;
import java.io.Serializable;

/**
 * Created by shkbhbb on 2/19/18.
 */

public class VerifyCodeResponse extends Response implements Serializable {

  @SerializedName("userInfo")
  private UserInfo userInfo;

  public VerifyCodeResponse(boolean isOk, String message,
      UserInfo userInfo) {
    super(isOk, message);
    this.userInfo = userInfo;
  }

  public UserInfo getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }
}
