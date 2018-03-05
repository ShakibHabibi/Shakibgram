package ir.shkbhbb.shakibgram.data.socket;


import android.content.Context;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.event.InternetEvent;
import ir.shkbhbb.shakibgram.utils.SocketHelper;
import ir.shkbhbb.shakibgram.utils.Utils;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.greenrobot.eventbus.EventBus;

public class SendData {

  private final String command;
  private Object object;
  private Context context;

  public SendData(Context context, Object object, String command) {
    this.object = object;
    this.context = context;
    this.command = command;
    new Thread(new Worker()).start();
  }

  class Worker implements Runnable {

    @Override
    public void run() {
      try {
        Socket socket = SocketHelper.getInstance(context);
        if (socket != null) {
          ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
          objectOutputStream.writeObject(command + Utils.toJson(object));
          objectOutputStream.flush();
        }
      } catch (IOException e) {
        EventBus.getDefault()
            .post(new InternetEvent(context.getString(R.string.check_your_internet_connection)));
        e.printStackTrace();
      }
    }
  }
}
