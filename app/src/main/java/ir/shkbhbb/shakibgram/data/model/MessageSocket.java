package ir.shkbhbb.shakibgram.data.model;

public class MessageSocket {

  private long senderUserId;
  private long chatId;
  private String message;
  private long id;
  private String senderName;

  public MessageSocket(long senderUserId, long chatId, String message, long id,
      String senderName) {
    this.senderUserId = senderUserId;
    this.chatId = chatId;
    this.message = message;
    this.id = id;
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

  public long getChatId() {
    return chatId;
  }

  public void setChatId(long chatId) {
    this.chatId = chatId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }
}
