package ir.shkbhbb.shakibgram.data.api;

import ir.shkbhbb.shakibgram.data.model.response.GetChatMessagesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shkbhbb on 2/21/18.
 */

public interface MessageService {

  @GET(ApiConstant.GET_CHAT_MESSAGES)
  Call<GetChatMessagesResponse> getMessages(@Query("chatId") String chatId,
      @Query("lastMessageId") String lastMessageId,@Query("userId") String userId);
}
