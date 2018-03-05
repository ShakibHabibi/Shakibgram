package ir.shkbhbb.shakibgram.data.model;

public class MessageUpdate {

  private long id;
  private long newId;
  private int status;

  public MessageUpdate(long id, long newId, int status) {
    this.id = id;
    this.newId = newId;
    this.status = status;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getNewId() {
    return newId;
  }

  public void setNewId(long newId) {
    this.newId = newId;
  }
}
