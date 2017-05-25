package service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 类描述：服务
 * Created by Gpsi on 2017-05-22.
 */

public class ScreenService extends Service {

    private ScreenReceiver receiver;

    /**
     * 进度条的最大值
     */
    public static final int MAX_PROGRESS = 100;
    /**
     * 进度条的进度值
     */
    private int progress = 0;

    //更新进度回调的接口
    private OnProgressListener onProgressListener;

    //注册回调接口的方法，供外部调用
    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    //get()方法，供Activity调用，return 下载进度。
    public int getProgress() {
        return progress;
    }

    public void startDownLoad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress < MAX_PROGRESS) {
                    progress += 5;
                    //进度放生变化通知调用方
                    if (onProgressListener != null) {
                        onProgressListener.onProgress(progress);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException o) {
                        o.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
       /**
      * 获取当前Service的实例
       *
       * @return
       */
      public ScreenService getSService() {
          return ScreenService.this;
      }
    }
/*
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }*/

/*    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 将注册广播的操作在服务中去完成
        registerReceiver(receiver,filter);

    }*/
}
