package ir.shkbhbb.shakibgram.data.api.impl;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.ContactService;
import ir.shkbhbb.shakibgram.data.model.response.GetContactsResponse;
import ir.shkbhbb.shakibgram.event.AddContactEvent;
import ir.shkbhbb.shakibgram.event.GetContactsEvent;
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

public class ContactServiceImpl {

  private ContactService contactService = RetrofitHelper.getContactInstance();
  private MaterialDialog loading;

  /**
   * add contacts
   */
  public void addContact(final Context context, final String username, String userId) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<ir.shkbhbb.shakibgram.data.model.response.Response> call = contactService
        .addContact(userId, username);
    call.enqueue(new Callback<ir.shkbhbb.shakibgram.data.model.response.Response>() {
      @Override
      public void onResponse(Call<ir.shkbhbb.shakibgram.data.model.response.Response> call,
          Response<ir.shkbhbb.shakibgram.data.model.response.Response> response) {
        loading.dismiss();
        if (response.body() != null) {
          ir.shkbhbb.shakibgram.data.model.response.Response response1 = response.body();
          EventBus.getDefault()
              .post(new AddContactEvent(response1.isOk(), response1.getMessage()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<ir.shkbhbb.shakibgram.data.model.response.Response> call,
          Throwable t) {
        loading.dismiss();
        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * get contacts
   */
  public void getContacts(final Context context, String userId,
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
    Call<GetContactsResponse> call = contactService.getContacts(userId);
    call.enqueue(new Callback<GetContactsResponse>() {
      @Override
      public void onResponse(Call<GetContactsResponse> call,
          Response<GetContactsResponse> response) {
        if (swipeToRefresh == null) {
          loading.dismiss();
        } else {
          swipeToRefresh.setRefreshing(false);
        }
        if (response.body() != null) {
          GetContactsResponse getContactsResponse = response.body();
          EventBus.getDefault()
              .post(new GetContactsEvent(getContactsResponse.isOk(),
                  getContactsResponse.getMessage(), getContactsResponse.getContacts()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<GetContactsResponse> call,
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
