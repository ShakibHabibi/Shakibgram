package ir.shkbhbb.shakibgram.utils;

import android.content.Context;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.model.Enum.Command;
import ir.shkbhbb.shakibgram.event.InternetEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by shkbhbb on 2/24/18.
 */

public class SocketHelper {

  private static Socket socket = null;
  private static Context mContext;

  public synchronized static Socket getInstance(Context context) throws IOException {
    mContext = context;
    if (socket == null) {
      setSocket();
    } else {
      checkConnectivity();
    }
    return socket;
  }

  private static void checkConnectivity() {
    try {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectOutputStream.writeObject("1 \n");
      objectOutputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
      setSocket();
    }
  }

  private static void setSocket() {
    try {
      socket = new Socket("192.168.1.12", 8082);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
      objectOutputStream.writeObject(
          Command.USER_INFO.getCommand() + String
              .valueOf(PreferenceHelper.getUserInfo().getId()));
      objectOutputStream.flush();
    } catch (IOException e) {
      EventBus.getDefault()
          .post(new InternetEvent(mContext.getString(R.string.check_your_internet_connection)));
      e.printStackTrace();
      socket = null;
    }
  }
}
