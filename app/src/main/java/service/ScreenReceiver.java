package service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.pooky.demo.R;

/**
 * 类描述：
 * Created by Gpsi on 2017-05-22.
 */

public class ScreenReceiver extends BroadcastReceiver {

    private TextView ll_iv_service;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Toast.makeText(context,"hei",Toast.LENGTH_SHORT).show();
        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            Toast.makeText(context,"亮",Toast.LENGTH_SHORT).show();
        }
    }
}
























