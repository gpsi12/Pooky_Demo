package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pookyimageloader.ImageLoader;
import com.google.gson.Gson;
import com.pooky.demo.R;

import java.io.IOException;

import bean.SplashBean;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import utils.HttpUtil;
import utils.L;
import utils.MyApplication;
import utils.ToastUtil;

/**
 * 类描述：
 * Created by Gpsi on 2017-04-13.
 */

public class SplashActivity extends Activity implements View.OnClickListener {
    private final int SPLASH_DISPLAY_LENGHT = 22000;
    String SPLASH_URL = "http://i.v.youmi.cn/ClientApi/huodonglist/?pagenum=8&inprogress=%s&p=%s";
    String SPLASH_URL1 = "https://raw.githubusercontent.com/gpsi12/Pooky_Demo/master/Splash.json";
    private Button btn_splash_close;
    private ImageView iv_splash;
    private String SPLASH_INTRODUCE;

    private  ImageLoader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loader = new ImageLoader(MyApplication.getAppContext());
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

        String VIDEO_TUIJIAN = "http://v.youmi.cn/api8/index";
//        mImageLoader.loadImage(SPLASH_URL,iv_splash,R.mipmap.splash);
//        GetRequest<SplashBean> request = new GetRequest<>(
//                SPLASH_URL, GsonParser.class, SplashBean.class, splashActionListener);
//        request.go();

        sendRequestWithOkHttp();


    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //在子线程中执行Http请求，并将最终的请求结果回调到okhttp3.Callback中
                HttpUtil.sendOKHttpRequest(SPLASH_URL1, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //在这里进行异常情况处理
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String respomseData = response.body().string();
                        L.e(respomseData);
                        parseJSONWithGSON(respomseData);
                        //添加到UI
//                        showResponse(respomseData.toString());

                    }
                });
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData){
        //使用轻量级的Gson解析得到的json
        Gson gson = new Gson();
        L.e("第一步  +  " + jsonData);
//        ArrayList<RecommendBean.RBean.HuodongBean> splashBeen = gson.fromJson(jsonData,new TypeToken<ArrayList<RecommendBean.RBean.HuodongBean>>(){}.getType());
        SplashBean huodongBean = gson.fromJson(jsonData,SplashBean.class);
        L.e("第二步  +  " + huodongBean.getIntroduce());
        showResponse(huodongBean.getImageUrl());
//        for (RecommendBean.RBean.HuodongBean splashBean : splashBeen){
//            L.e(splashBean.getTitle());
//            L.e(splashBean.getUrl().toString());
//            L.e(splashBean.getContent());
//        }
    }

    private void showResponse(final String url){
        //在子线程中更新UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loader.loadImage(url,iv_splash,R.mipmap.splash);
//                ToastUtil.showDIYToast(reponse);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_splash_close:
                ToastUtil.showToast("跳过");
                countDownTimer.cancel();
                gotoMainActivity();
                break;
            case R.id.iv_splash:
//                ToastUtil.showDIYToast(SPLASH_INTRODUCE);
        }
    }
}




















