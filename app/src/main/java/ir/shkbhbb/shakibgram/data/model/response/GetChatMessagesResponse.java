package ir.shkbhbb.shakibgram.data.model.response;

import com.google.gson.annotations.SerializedName;
import ir.shkbhbb.shakibgram.data.model.PrivateMessage;
import java.io.Serializable;
import java.util.List;

/**
 * Created by shkbhbb on 2/27/18.
 */

public class GetChatMessagesResponse implements Serializable {

  @SerializedName("isOk")
  private boolean isOk;
  @SerializedName("message")
  private String message;
  @SerializedName("messages")
  private List<PrivateMessage> messages;
  @SerializedName("isLoadMore")
  private boolean isLoadMore;

  public GetChatMessagesResponse(boolean isOk, String message, List<PrivateMessage> messages,
      boolean isLoadMore) {
    this.isOk = isOk;
    this.messages = messages;
    this.message = message;
    this.isLoadMore = isLoadMore;
  }

  public boolean isLoadMore() {
    return isLoadMore;
  }

  public void setLoadMore(boolean loadMore) {
    isLoadMore = loadMore;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isOk() {
    return isOk;
  }

  public void setOk(boolean ok) {
    isOk = ok;
  }

  public List<PrivateMessage> getMessages() {
    return messages;
  }

  public void setMessages(
      List<PrivateMessage> messages) {
    this.messages = messages;
  }
}
