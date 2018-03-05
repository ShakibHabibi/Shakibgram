package ir.shkbhbb.shakibgram.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by shkbhbb on 2/22/18.
 */

public class Chat implements Serializable {

  @SerializedName("chatId")
  private long chatId;
  @SerializedName("name")
  private String name;
  @SerializedName("lastMessageText")
  private String lastMessageText;
  @SerializedName("lastMessageDate")
  private Date lastMessageDate;

  public Chat(long chatId, String name, String lastMessageText, Date lastMessageDate) {
    this.chatId = chatId;
    this.name = name;
    this.lastMessageText = lastMessageText;
    this.lastMessageDate = lastMessageDate;
  }

  public long getChatId() {
    return chatId;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastMessageText() {
    return lastMessageText;
  }

  public void setLastMessageText(String lastMessageText) {
    this.lastMessageText = lastMessageText;
  }

  public Date getLastMessageDate() {
    return lastMessageDate;
  }

  public void setLastMessageDate(Date lastMessageDate) {
    this.lastMessageDate = lastMessageDate;
  }

}
