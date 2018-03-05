package ir.shkbhbb.shakibgram.data.model;

public class UpdateStatus {

  private long chatId;
  private long messageId;
  private int status;
  private long senderUserId;

  public UpdateStatus(long chatId, long messageId, int status, long senderUserId) {
    this.chatId = chatId;
    this.messageId = messageId;
    this.status = status;
    this.senderUserId = senderUserId;
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

  public long getMessageId() {
    return messageId;
  }

  public void setMessageId(long messageId) {
    this.messageId = messageId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
