package ir.shkbhbb.shakibgram.data.model.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by shkbhbb on 2/20/18.
 */

public class CheckUsernameResponse extends Response implements Serializable {

  @SerializedName("emailAddress")
  private String emailAddress;

  public CheckUsernameResponse(boolean isOk, String message, String emailAddress) {
    super(isOk, message);
    this.emailAddress = emailAddress;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }
}
