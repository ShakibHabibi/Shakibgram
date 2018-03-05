package ir.shkbhbb.shakibgram.data.api.impl;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.ChatService;
import ir.shkbhbb.shakibgram.data.model.response.CreateChatResponse;
import ir.shkbhbb.shakibgram.data.model.response.GetChatsResponse;
import ir.shkbhbb.shakibgram.event.CreateChatEvent;
import ir.shkbhbb.shakibgram.event.GetChatsEvent;
import ir.shkbhbb.shakibgram.utils.Dialog;
import ir.shkbhbb.shakibgram.utils.NetworkUtil;
import ir.shkbhbb.shakibgram.utils.RetrofitHelper;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shkbhbb on 2/21/18.
 */

public class ChatServiceImpl {

  private ChatService chatService = RetrofitHelper.getChatInstance();
  private MaterialDialog loading;

  /**
   * create chat
   */
  public void createChat(final Context context, final String otherUserId, String userId) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<CreateChatResponse> call = chatService.createChat(userId, otherUserId);
    call.enqueue(new Callback<CreateChatResponse>() {
      @Override
      public void onResponse(Call<CreateChatResponse> call,
          Response<CreateChatResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
          CreateChatResponse createChatResponse = response.body();
          EventBus.getDefault()
              .post(new CreateChatEvent(createChatResponse.isOk(), createChatResponse.getMessage(),
                  createChatResponse.getChat()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<CreateChatResponse> call,
          Throwable t) {
        loading.dismiss();
        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * get chats
   */
  public void getChats(final Context context, String userId,
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
    Call<GetChatsResponse> call = chatService.getChats(userId);
    call.enqueue(new Callback<GetChatsResponse>() {
      @Override
      public void onResponse(Call<GetChatsResponse> call,
          Response<GetChatsResponse> response) {
        if (swipeToRefresh == null) {
          loading.dismiss();
        } else {
          swipeToRefresh.setRefreshing(false);
        }
        if (response.body() != null) {
          GetChatsResponse getChatsResponse = response.body();
          EventBus.getDefault()
              .post(new GetChatsEvent(getChatsResponse.isOk(), getChatsResponse.getMessage(),
                  getChatsResponse.getChats()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<GetChatsResponse> call,
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
