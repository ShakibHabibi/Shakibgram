package ir.shkbhbb.shakibgram.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by shkbhbb on 2/27/18.
 */

public class PrivateMessage implements Serializable {

  @SerializedName("id")
  private long id;
  @SerializedName("senderUserId")
  private long senderUserId;
  @SerializedName("message")
  private String message;
  @SerializedName("createDate")
  private Date createDate;
  @SerializedName("status")
  private int status;
  @SerializedName("chatId")
  private long chatId;
  @SerializedName("senderName")
  private String senderName;

  public PrivateMessage(long id, long senderUserId, String message, Date createDate, int status,
      long chatId, String senderName) {
    this.id = id;
    this.senderUserId = senderUserId;
    this.message = message;
    this.createDate = createDate;
    this.status = status;
    this.chatId = chatId;
    this.senderName = senderName;
  }

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getSenderUserId() {
    return senderUserId;
  }

  public void setSenderUserId(long senderUserId) {
    this.senderUserId = senderUserId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getChatId() {
    return chatId;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }
}
