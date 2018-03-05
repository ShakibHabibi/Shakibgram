package ir.shkbhbb.shakibgram.data.api;

import ir.shkbhbb.shakibgram.data.model.response.GetContactsResponse;
import ir.shkbhbb.shakibgram.data.model.response.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by shkbhbb on 2/21/18.
 */

public interface ContactService {

  @FormUrlEncoded
  @POST(ApiConstant.ADD_CONTACT)
  Call<Response> addContact(@Field("userId") String userId, @Field("username") String username);

  @GET(ApiConstant.GET_CONTACT)
  Call<GetContactsResponse> getContacts(@Query("userId") String userId);
}
