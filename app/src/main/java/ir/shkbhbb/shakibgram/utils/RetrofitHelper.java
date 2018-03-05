package ir.shkbhbb.shakibgram.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.shkbhbb.shakibgram.data.api.ApiConstant;
import ir.shkbhbb.shakibgram.data.api.AuthService;
import ir.shkbhbb.shakibgram.data.api.ChatService;
import ir.shkbhbb.shakibgram.data.api.ContactService;
import ir.shkbhbb.shakibgram.data.api.MessageService;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

  private static AuthService authService;
  private static ContactService contactService;
  private static ChatService chatService;
  private static MessageService messageService;

  public static AuthService getAuthInstance() {
    if (authService == null) {
      authService = getAuthService();
    }
    return authService;
  }

  public static MessageService getMessageInstance() {
    if (messageService == null) {
      messageService = getMessageService();
    }
    return messageService;
  }

  public static ChatService getChatInstance() {
    if (chatService == null) {
      chatService = getChatService();
    }
    return chatService;
  }

  public static ContactService getContactInstance() {
    if (contactService == null) {
      contactService = getContactService();
    }
    return contactService;
  }

  private static Gson getGson() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
    gsonBuilder.setLenient();
    return gsonBuilder.create();
  }

  private static Retrofit getRetrofit() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(logging)
        .build();
    return new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(getGson()))
        .baseUrl(ApiConstant.BASE_URL)
        .client(okHttpClient)
        .build();
  }

  private static AuthService getAuthService() {
    return getRetrofit().create(AuthService.class);
  }

  private static MessageService getMessageService() {
    return getRetrofit().create(MessageService.class);
  }

  private static ChatService getChatService() {
    return getRetrofit().create(ChatService.class);
  }

  private static ContactService getContactService() {
    return getRetrofit().create(ContactService.class);
  }


}
