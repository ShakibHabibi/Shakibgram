package ir.shkbhbb.shakibgram.event;

import ir.shkbhbb.shakibgram.data.model.UpdateStatusRange;

/**
 * Created by shkbhbb on 3/2/18.
 */

public class UpdateStatusRangeEvent extends Event {

  private UpdateStatusRange updateStatusRange;

  public UpdateStatusRangeEvent(UpdateStatusRange updateStatusRange) {
    this.updateStatusRange = updateStatusRange;
  }

  public UpdateStatusRange getUpdateStatusRange() {
    return updateStatusRange;
  }

  public void setUpdateStatusRange(UpdateStatusRange updateStatusRange) {
    this.updateStatusRange = updateStatusRange;
  }
}
