package ir.shkbhbb.shakibgram.data.model.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by shkbhbb on 2/20/18.
 */

public class ChangePasswordVerificationResponse extends Response implements Serializable {

  @SerializedName("username")
  private String username;

  public ChangePasswordVerificationResponse(boolean isOk, String message, String username) {
    super(isOk, message);
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
