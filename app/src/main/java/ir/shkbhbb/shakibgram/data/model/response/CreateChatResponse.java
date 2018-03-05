package ir.shkbhbb.shakibgram.data.model.response;

import com.google.gson.annotations.SerializedName;
import ir.shkbhbb.shakibgram.data.model.Chat;
import java.io.Serializable;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class CreateChatResponse extends Response implements Serializable {

  @SerializedName("chat")
  private Chat chat;

  public CreateChatResponse(boolean isOk, String message,
      Chat chat) {
    super(isOk, message);
    this.chat = chat;
  }

  public Chat getChat() {
    return chat;
  }

  public void setChat(Chat chat) {
    this.chat = chat;
  }
}
