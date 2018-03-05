package ir.shkbhbb.shakibgram.data.api;

import ir.shkbhbb.shakibgram.data.model.response.ChangePasswordVerificationResponse;
import ir.shkbhbb.shakibgram.data.model.response.CheckUsernameResponse;
import ir.shkbhbb.shakibgram.data.model.response.LogInResponse;
import ir.shkbhbb.shakibgram.data.model.response.Response;
import ir.shkbhbb.shakibgram.data.model.response.SignUpResponse;
import ir.shkbhbb.shakibgram.data.model.response.VerifyCodeResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ShakibIsTheBest on 9/8/2017.
 */

public interface AuthService {

  @FormUrlEncoded
  @POST(ApiConstant.SIGN_UP)
  Call<SignUpResponse> signUp(@Field("username") String username,
      @Field("name") String name, @Field("emailAddress") String emailAddress,
      @Field("password") String password);

  @FormUrlEncoded
  @POST(ApiConstant.VERIFY_CODE)
  Call<VerifyCodeResponse> verifyCode(@Field("username") String username,
      @Field("code") String code);

  @FormUrlEncoded
  @POST(ApiConstant.LOG_IN)
  Call<LogInResponse> login(@Field("username") String username,
      @Field("password") String password);

  @FormUrlEncoded
  @POST(ApiConstant.CHECK_USER_NAME)
  Call<CheckUsernameResponse> checkUserName(@Field("username") String username);

  @FormUrlEncoded
  @POST(ApiConstant.CHANGE_PASSWORD_VERIFICATION)
  Call<ChangePasswordVerificationResponse> changePasswordVerification(
      @Field("username") String username,
      @Field("emailAddress") String emailAddress);

  @FormUrlEncoded
  @POST(ApiConstant.CHANGE_PASSWORD)
  Call<Response> changePassword(@Field("username") String username,
      @Field("password") String password);

}
