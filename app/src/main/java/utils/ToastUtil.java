package utils;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pooky.demo.R;

import java.util.IllegalFormatCodePointException;
import java.util.logging.Handler;

/**
 * Created by Pooky on 2017/9/5.
 */
public class ToastUtil {
    private static Toast toast;
    private static Toast mDIYToast;
    private static TextView tv_content;
//    public static android.os.Handler mMainHandler = new android.os.Handler();

    /**
     * 在主线程执行任务
     *
     * @param r
     */
    private static void runOnUIThread(Runnable r) {
        MyApplication.getMainHandler().post(r);
//        mMainHandler.post(r);
    }

    /**
     * 单例吐司，可以连续吐
     *
     * @param text
     */
    public static void showToast(final String text) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(MyApplication.getAppContext(), text, Toast.LENGTH_SHORT);
                }
                toast.setText(text);
                toast.show();
            }
        });
    }

    /**
     * 定义的toast,自定义的布局,文字大小等
     * @param text
     */
    public static void showDIYToast(final String text) {
        runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (mDIYToast == null) {
                    mDIYToast = new Toast(MyApplication.getAppContext());
                    mDIYToast.setGravity(Gravity.CENTER, 0, 0);
                    mDIYToast.setDuration(Toast.LENGTH_SHORT);
                    LayoutInflater inflater = LayoutInflater.from(MyApplication.getAppContext());
                    View view = inflater.inflate(R.layout.toast_diy, null);
                    tv_content = (TextView) view.findViewById(R.id.tv_content);
                    mDIYToast.setView(view);
                }
                if (tv_content != null) {
                    tv_content.setText(text);
                }
                mDIYToast.show();
            }
        });
    }


}
