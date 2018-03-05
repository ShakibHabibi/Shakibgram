package ir.shkbhbb.shakibgram.data.model.response;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Response implements Serializable {

  @SerializedName("isOk")
  private boolean isOk;
  @SerializedName("message")
  private String message;

  public Response(boolean isOk, String message) {
    this.isOk = isOk;
    this.message = message;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
