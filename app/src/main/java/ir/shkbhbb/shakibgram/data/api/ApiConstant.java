package ir.shkbhbb.shakibgram.data.api;

/**
 * Created by shkbhbb on 2/18/18.
 */

public class ApiConstant {

  public static final String BASE_URL = "http://192.168.1.12:8081/";
  //AUTH apis
  static final String SIGN_UP = "auth/signup";
  static final String LOG_IN = "auth/login";
  static final String VERIFY_CODE = "auth/verifycode";
  static final String CHECK_USER_NAME = "auth/checkusername";
  static final String CHANGE_PASSWORD_VERIFICATION = "auth/changepasswordverification";
  static final String CHANGE_PASSWORD = "auth/changepassword";
  //CONTACT apis
  static final String ADD_CONTACT = "contact/addcontact";
  static final String GET_CONTACT = "contact/getcontact";
  //CHAT apis
  static final String CREATE_CHAT = "chat/createchat";
  static final String GET_CHATS = "chat/getchats";
  //MESSAGE apis
  static final String GET_CHAT_MESSAGES = "message/getchatmessages";

}
