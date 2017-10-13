package utils;

import android.app.Application;
import android.os.Handler;

/**
 * Created by Pooky on 2017/9/5.
 */

public class MyApplication extends Application {
    private static MyApplication mMyApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplicationContext = this;
    }

    /**
     * 主线程的handler
     */
    public static Handler mMainHandler = new Handler();

    /**
     * 获取主线程的handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        return mMainHandler;
    }
    public static MyApplication getAppContext(){
        return mMyApplicationContext;
    }
}
