package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import com.pooky.demo.R;

/**
 * 类描述：
 * Created by Gpsi on 2017-04-13.
 */

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 2200;
    private Button btn_splash_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btn_splash_close = (Button) findViewById(R.id.btn_splash_close);
/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainActivity();
            }
        }, SPLASH_DISPLAY_LENGHT);*/
        countDownTimer.start();
    }

    private CountDownTimer countDownTimer = new CountDownTimer(SPLASH_DISPLAY_LENGHT,1000) {
        @Override
        public void onTick(long l) {
            btn_splash_close.setText("跳过 " + l / 1000);
        }

        @Override
        public void onFinish() {
            btn_splash_close.setText("跳过 " + 0 );
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
}
