package ir.shkbhbb.shakibgram.data.api.impl;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.MessageService;
import ir.shkbhbb.shakibgram.data.model.response.GetChatMessagesResponse;
import ir.shkbhbb.shakibgram.event.GetMessagesEvent;
import ir.shkbhbb.shakibgram.utils.Dialog;
import ir.shkbhbb.shakibgram.utils.NetworkUtil;
import ir.shkbhbb.shakibgram.utils.PreferenceHelper;
import ir.shkbhbb.shakibgram.utils.RetrofitHelper;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class MessageServiceImpl {

  private MessageService messageService = RetrofitHelper.getMessageInstance();
  private MaterialDialog loading;


  /**
   * get messages
   */
  public void getMessages(final Context context, String chatId, String lastMessageId,
      final SwipeRefreshLayout swipeToRefresh) {
    if (swipeToRefresh == null) {
      loading = Dialog.loadingDialog(context);
      loading.show();
    }
    if (!NetworkUtil.isConnectedToInternet(context)) {
      if (swipeToRefresh == null) {
        loading.dismiss();
      } else {
        swipeToRefresh.setRefreshing(false);
      }
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<GetChatMessagesResponse> call = messageService.getMessages(chatId, lastMessageId,
        String.valueOf(PreferenceHelper.getUserInfo().getId()));
    call.enqueue(new Callback<GetChatMessagesResponse>() {
      @Override
      public void onResponse(Call<GetChatMessagesResponse> call,
          Response<GetChatMessagesResponse> response) {
        if (swipeToRefresh == null) {
          loading.dismiss();
        } else {
          swipeToRefresh.setRefreshing(false);
        }

        if (response.body() != null) {
          GetChatMessagesResponse getChatMessagesResponse = response.body();
          EventBus.getDefault()
              .post(new GetMessagesEvent(getChatMessagesResponse.isOk(),
                  getChatMessagesResponse.getMessage(), getChatMessagesResponse.isLoadMore(),
                  getChatMessagesResponse.getMessages()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<GetChatMessagesResponse> call,
          Throwable t) {
        if (swipeToRefresh == null) {
          loading.dismiss();
        } else {
          swipeToRefresh.setRefreshing(false);
        }

        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }
}
