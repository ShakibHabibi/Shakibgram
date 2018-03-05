package ir.shkbhbb.shakibgram.data.api;

import ir.shkbhbb.shakibgram.data.model.response.CreateChatResponse;
import ir.shkbhbb.shakibgram.data.model.response.GetChatsResponse;
import ir.shkbhbb.shakibgram.data.model.response.GetContactsResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by shkbhbb on 2/21/18.
 */

public interface ChatService {

  @FormUrlEncoded
  @POST(ApiConstant.CREATE_CHAT)
  Call<CreateChatResponse> createChat(@Field("userId") String userId,
      @Field("otherUserId") String otherUserId);

  @GET(ApiConstant.GET_CHATS)
  Call<GetChatsResponse> getChats(@Query("userId") String userId);

}
