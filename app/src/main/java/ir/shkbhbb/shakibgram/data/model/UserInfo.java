package ir.shkbhbb.shakibgram.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by shkbhbb on 2/19/18.
 */

public class UserInfo implements Serializable {

  @SerializedName("id")
  private long id;
  @SerializedName("userName")
  private String userName;
  @SerializedName("password")
  private String password;
  @SerializedName("name")
  private String name;
  @SerializedName("status")
  private int status;
  @SerializedName("verifyCode")
  private String verifyCode;
  @SerializedName("emailAddress")
  private String emailAddress;
  @SerializedName("imageUrl")
  private String imageUrl;
  @SerializedName("hasLoggedIn")
  private int hasLoggedIn;
  @SerializedName("isAdmin")
  private int isAdmin;
  @SerializedName("registerDate")
  private Date registerDate;
  @SerializedName("lastActivityDate")
  private Date lastActivityDate;

  public UserInfo(long id, String userName, String password, String name, int status,
      String verifyCode, String emailAddress, String imageUrl, int hasLoggedIn, int isAdmin,
      Date registerDate, Date lastActivityDate) {
    this.id = id;
    this.userName = userName;
    this.password = password;
    this.name = name;
    this.status = status;
    this.verifyCode = verifyCode;
    this.emailAddress = emailAddress;
    this.imageUrl = imageUrl;
    this.hasLoggedIn = hasLoggedIn;
    this.isAdmin = isAdmin;
    this.registerDate = registerDate;
    this.lastActivityDate = lastActivityDate;
  }

  public Date getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }

  public Date getLastActivityDate() {
    return lastActivityDate;
  }

  public void setLastActivityDate(Date lastActivityDate) {
    this.lastActivityDate = lastActivityDate;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getVerifyCode() {
    return verifyCode;
  }

  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getHasLoggedIn() {
    return hasLoggedIn;
  }

  public void setHasLoggedIn(int hasLoggedIn) {
    this.hasLoggedIn = hasLoggedIn;
  }

  public int getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(int isAdmin) {
    this.isAdmin = isAdmin;
  }
}
