package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pooky.demo.R;

import bean.SplashBean;
import cn.youmi.framework.http.AbstractRequest;
import cn.youmi.framework.http.GetRequest;
import cn.youmi.framework.http.parsers.GsonParser;
import cn.youmi.framework.util.ImageLoader;
import utils.MyApplication;
import utils.ToastUtil;

/**
 * 类描述：
 * Created by Gpsi on 2017-04-13.
 */

public class SplashActivity extends Activity implements View.OnClickListener {
    private final int SPLASH_DISPLAY_LENGHT = 22000;
    private Button btn_splash_close;
    private ImageView iv_splash;
    private ImageLoader mImageLoader;
    private String SPLASH_INTRODUCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mImageLoader = new ImageLoader(this);
        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        btn_splash_close = (Button) findViewById(R.id.btn_splash_close);
        btn_splash_close.setOnClickListener(this);

        loadSplash();
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainActivity();
            }
        }, SPLASH_DISPLAY_LENGHT);*/
        countDownTimer.start();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(SPLASH_DISPLAY_LENGHT, 1000) {
        @Override
        public void onTick(long l) {
            btn_splash_close.setText("跳过 " + l / 1000);
        }

        @Override
        public void onFinish() {
            btn_splash_close.setText("跳过 " + 0);
            gotoMainActivity();
        }
    };

    private void gotoMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    /**
     * 加载闪屏数据
     */
    private void loadSplash() {
        String SPLASH_URL = "https://raw.githubusercontent.com/gpsi12/Pooky_Demo/master/Splash.json";
//        mImageLoader.loadImage(SPLASH_URL,iv_splash,R.mipmap.splash);
        GetRequest<SplashBean> request = new GetRequest<>(
                SPLASH_URL, GsonParser.class,
                SplashBean.class,
                splashActionListener);
        request.go();
    }

    private AbstractRequest.Listener<SplashBean> splashActionListener = new AbstractRequest.Listener<SplashBean>() {
        @Override
        public void onResult(AbstractRequest<SplashBean> request, SplashBean splashBean) {
            mImageLoader.loadImage(splashBean.getImageUrl(), iv_splash, R.mipmap.splash);
//            SPLASH_INTRODUCE = splashBean.getIntroduce();
        }

        @Override
        public void onError(AbstractRequest<SplashBean> requet, int statusCode, String body) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_splash_close:
                ToastUtil.showToast("跳过");
                countDownTimer.cancel();
                gotoMainActivity();
                break;
            case R.id.iv_splash:
                ToastUtil.showDIYToast(SPLASH_INTRODUCE);
        }
    }
}




















