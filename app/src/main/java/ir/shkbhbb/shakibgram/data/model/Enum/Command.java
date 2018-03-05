package ir.shkbhbb.shakibgram.data.model.Enum;

public enum Command {

  USER_INFO("userInfo "),
  MESSAGE("message "),
  STATUS("status "),
  USER_STATUS("userStatus "),
  UPDATE_STATUS_RANGE("updateStatusRange ");

  private String command;

  Command(String command) {
    this.command = command;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }
}
