package ir.shkbhbb.shakibgram.data.socket;

import android.app.IntentService;
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.shkbhbb.shakibgram.data.model.MessageUpdate;
import ir.shkbhbb.shakibgram.data.model.PrivateMessage;
import ir.shkbhbb.shakibgram.data.model.UpdateStatus;
import ir.shkbhbb.shakibgram.data.model.UpdateStatusRange;
import ir.shkbhbb.shakibgram.event.MessageEvent;
import ir.shkbhbb.shakibgram.event.UpdateMessageEvent;
import ir.shkbhbb.shakibgram.event.UpdateStatusEvent;
import ir.shkbhbb.shakibgram.event.UpdateStatusRangeEvent;
import ir.shkbhbb.shakibgram.utils.SocketHelper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import org.greenrobot.eventbus.EventBus;


public class GetDataIntentService extends IntentService {

  public GetDataIntentService() {
    super("GetData");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    while (true) {
      try {
        Socket socket = SocketHelper.getInstance(getApplicationContext());
        if (socket != null) {
          ObjectInputStream objectInputStream = new ObjectInputStream(
              socket.getInputStream());
          String command = (String) objectInputStream.readObject();
          if (command.startsWith("message")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            PrivateMessage privateMessage = gson
                .fromJson(command.substring(8), PrivateMessage.class);
            EventBus.getDefault().post(new MessageEvent(privateMessage));
          } else if (command.startsWith("updateMessage")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            MessageUpdate messageUpdate = gson
                .fromJson(command.substring(14), MessageUpdate.class);
            EventBus.getDefault().post(new UpdateMessageEvent(messageUpdate));
          } else if (command.startsWith("updateStatusRange")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            UpdateStatusRange updateStatusRange = gson
                .fromJson(command.substring(18), UpdateStatusRange.class);
            EventBus.getDefault()
                .post(new UpdateStatusRangeEvent(updateStatusRange));
          } else if (command.startsWith("updateStatus")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            UpdateStatus updateStatus = gson
                .fromJson(command.substring(13), UpdateStatus.class);
            EventBus.getDefault().post(new UpdateStatusEvent(updateStatus));
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

  }
}
