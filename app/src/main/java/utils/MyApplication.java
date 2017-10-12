package utils;

import android.app.Application;
import android.os.Handler;

import cn.youmi.framework.main.BaseApplication;

/**
 * Created by Pooky on 2017/9/5.
 */

public class MyApplication extends Application {
    private static MyApplication mMyApplicationContext;
    private static BaseApplication baseApplication = null;

    public static BaseApplication getApplication() {
        return baseApplication;
    }
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
