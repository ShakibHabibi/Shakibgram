package ir.shkbhbb.shakibgram.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class Contact implements Serializable {

  @SerializedName("contactId")
  private long contactId;
  @SerializedName("userId")
  private long userId;
  @SerializedName("name")
  private String name;

  public Contact(long contactId, long userId, String name) {
    this.contactId = contactId;
    this.userId = userId;
    this.name = name;
  }

  public long getContactId() {
    return contactId;
  }

  public void setContactId(long contactId) {
    this.contactId = contactId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
