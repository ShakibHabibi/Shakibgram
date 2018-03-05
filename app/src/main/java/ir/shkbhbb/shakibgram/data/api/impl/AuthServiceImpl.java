package ir.shkbhbb.shakibgram.data.api.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import ir.shkbhbb.shakibgram.R;
import ir.shkbhbb.shakibgram.data.api.AuthService;
import ir.shkbhbb.shakibgram.data.model.response.ChangePasswordVerificationResponse;
import ir.shkbhbb.shakibgram.data.model.response.CheckUsernameResponse;
import ir.shkbhbb.shakibgram.data.model.response.LogInResponse;
import ir.shkbhbb.shakibgram.data.model.response.SignUpResponse;
import ir.shkbhbb.shakibgram.data.model.response.VerifyCodeResponse;
import ir.shkbhbb.shakibgram.event.ChangePasswordEvent;
import ir.shkbhbb.shakibgram.event.ChangePasswordVerificationEvent;
import ir.shkbhbb.shakibgram.event.CheckUsernameEvent;
import ir.shkbhbb.shakibgram.event.LogInEvent;
import ir.shkbhbb.shakibgram.event.SignUpEvent;
import ir.shkbhbb.shakibgram.event.VerifyCodeEvent;
import ir.shkbhbb.shakibgram.utils.Dialog;
import ir.shkbhbb.shakibgram.utils.NetworkUtil;
import ir.shkbhbb.shakibgram.utils.RetrofitHelper;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthServiceImpl {

  private AuthService authService = RetrofitHelper.getAuthInstance();
  private MaterialDialog loading;

  /**
   * sign up api
   */
  public void signUp(final Context context, String name, final String username, String password,
      String emailAddress) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<SignUpResponse> call = authService
        .signUp(username, name, emailAddress, password);
    call.enqueue(new Callback<SignUpResponse>() {
      @Override
      public void onResponse(Call<SignUpResponse> call,
          Response<SignUpResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
          SignUpResponse signUpResponse = response.body();
          EventBus.getDefault()
              .post(new SignUpEvent(signUpResponse.isOk(), signUpResponse.getUsername(),
                  signUpResponse.getMessage()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<SignUpResponse> call, Throwable t) {
        loading.dismiss();
        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * verify code api
   */
  public void verifyCode(final Context context, String code, final String username) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<VerifyCodeResponse> call = authService
        .verifyCode(username, code);
    call.enqueue(new Callback<VerifyCodeResponse>() {
      @Override
      public void onResponse(Call<VerifyCodeResponse> call,
          Response<VerifyCodeResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
          VerifyCodeResponse verifyCodeResponse = response.body();
          EventBus.getDefault()
              .post(new VerifyCodeEvent(verifyCodeResponse.getMessage(), verifyCodeResponse.isOk(),
                  verifyCodeResponse.getUserInfo()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<VerifyCodeResponse> call,
          Throwable t) {
        loading.dismiss();
        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * login api
   */
  public void login(final Context context, String password, final String username) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<LogInResponse> call = authService
        .login(username, password);
    call.enqueue(new Callback<LogInResponse>() {
      @Override
      public void onResponse(Call<LogInResponse> call,
          Response<LogInResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
          LogInResponse logInResponse = response.body();
          EventBus.getDefault()
              .post(new LogInEvent(logInResponse.isOk(), logInResponse.getMessage(),
                  logInResponse.getUserInfo()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<LogInResponse> call,
          Throwable t) {
        loading.dismiss();
        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * change user name
   */
  public void checkUsername(final Context context, final String username) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<CheckUsernameResponse> call = authService.checkUserName(username);
    call.enqueue(new Callback<CheckUsernameResponse>() {
      @Override
      public void onResponse(Call<CheckUsernameResponse> call,
          Response<CheckUsernameResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
          CheckUsernameResponse checkUsernameResponse = response.body();
          EventBus.getDefault()
              .post(new CheckUsernameEvent(checkUsernameResponse.isOk(),
                  checkUsernameResponse.getMessage(),
                  checkUsernameResponse.getEmailAddress()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<CheckUsernameResponse> call,
          Throwable t) {
        loading.dismiss();
        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * change password verification
   */
  public void changePasswordVerification(final Context context, final String username,
      String emailAddress) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<ChangePasswordVerificationResponse> call = authService
        .changePasswordVerification(username, emailAddress);
    call.enqueue(new Callback<ChangePasswordVerificationResponse>() {
      @Override
      public void onResponse(Call<ChangePasswordVerificationResponse> call,
          Response<ChangePasswordVerificationResponse> response) {
        loading.dismiss();
        if (response.body() != null) {
          ChangePasswordVerificationResponse changePasswordVerificationResponse = response.body();
          EventBus.getDefault()
              .post(new ChangePasswordVerificationEvent(changePasswordVerificationResponse.isOk(),
                  changePasswordVerificationResponse.getMessage(),
                  changePasswordVerificationResponse.getUsername()));
        } else if (response.body() == null) {
          try {
            Log.i("retrofit error msg", response.errorBody().string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void onFailure(Call<ChangePasswordVerificationResponse> call,
          Throwable t) {
        loading.dismiss();
        Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  /**
   * change password
   */
  public void changePassword(final Context context, final String username, String password) {
    loading = Dialog.loadingDialog(context);
    loading.show();
    if (!NetworkUtil.isConnectedToInternet(context)) {
      loading.dismiss();
      Toast.makeText(context, context.getString(R.string.check_your_internet_connection),
          Toast.LENGTH_SHORT).show();
      return;
    }
    Call<ir.shkbhbb.shakibgram.data.model.response.Response> call = authService
        .changePassword(username, password);
    call.enqueue(new Callback<ir.shkbhbb.shakibgram.data.model.response.Response>() {
      @Override
      public void onResponse(Call<ir.shkbhbb.shakibgram.data.model.response.Response> call,
          Response<ir.shkbhbb.shakibgram.data.model.response.Response> response) {
        loading.dismiss();
        if (response.body() != null) {
          ir.shkbhbb.shakibgram.data.model.response.Response response1 = response.body();
          EventBus.getDefault()
              .post(new ChangePasswordEvent(response1.isOk(), response1.getMessage()));
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
}
