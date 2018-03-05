package ir.shkbhbb.shakibgram.data.model.response;

import com.google.gson.annotations.SerializedName;
import ir.shkbhbb.shakibgram.data.model.Chat;
import java.io.Serializable;
import java.util.List;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class GetChatsResponse extends Response implements Serializable {

  @SerializedName("chatDetails")
  private List<Chat> chats;

  public GetChatsResponse(boolean isOk, String message,
      List<Chat> chats) {
    super(isOk, message);
    this.chats = chats;
  }

  public List<Chat> getChats() {
    return chats;
  }

  public void setChats(List<Chat> chats) {
    this.chats = chats;
  }
}
