package ir.shkbhbb.shakibgram.event;

import java.io.Serializable;

public class Event implements Serializable {

  protected String msg;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
