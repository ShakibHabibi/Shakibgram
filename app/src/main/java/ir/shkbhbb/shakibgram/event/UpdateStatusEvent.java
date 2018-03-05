package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.UpdateStatus;

/**
 * Created by shkbhbb on 2/28/18.
 */

public class UpdateStatusEvent extends Event {

  private UpdateStatus updateStatus;

  public UpdateStatusEvent(UpdateStatus updateStatus) {
    this.updateStatus = updateStatus;
  }

  public UpdateStatus getUpdateStatus() {
    return updateStatus;
  }

  public void setUpdateStatus(UpdateStatus updateStatus) {
    this.updateStatus = updateStatus;
  }
}
