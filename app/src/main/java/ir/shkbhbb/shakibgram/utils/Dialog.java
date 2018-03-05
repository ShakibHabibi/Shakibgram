package ir.shkbhbb.shakibgram.utils;

import android.content.Context;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import ir.shkbhbb.shakibgram.R;

public class Dialog {

  public static MaterialDialog loadingDialog(Context context) {
    MaterialDialog loading = new MaterialDialog.Builder(context)
        .content(R.string.waiting)
        .progress(true, 0)
        .contentGravity(GravityEnum.START)
        .itemsGravity(GravityEnum.START)
        .buttonsGravity(GravityEnum.START)
        .autoDismiss(false)
        .cancelable(false)
        .build();
    loading.setCancelable(false);
    return loading;
  }

}
