package fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pooky.demo.R;

import service.OnProgressListener;
import service.ScreenService;

/**
 * 类描述：我的
 * Created by Gpsi on 2017-04-12.
 */

public class MeFragment extends Fragment {

    private RelativeLayout me_qq_qun;
    private RelativeLayout me_ll_service;
    private LinearLayout me_ll_anim;
    private ImageView me_iv_share;
    private ImageView me_iv_d_anim;

    private ScreenService screenService;
    private ProgressBar mProgressBar;
    private AnimationDrawable animationDrawable;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);

        Intent intent = new Intent(getContext(),ScreenService.class);
        getActivity().bindService(intent,connection,getActivity().BIND_AUTO_CREATE);

        me_qq_qun = (RelativeLayout) view.findViewById(R.id.me_ll_qq_qun);
        me_qq_qun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isSucceed = joinQQGroup("hJtmRITn9VadK7CsvpXYWv68n7OrMbcW");
            }
        });
        mProgressBar = (ProgressBar) view.findViewById(R.id.me_pb_donw);
        me_ll_service = (RelativeLayout) view.findViewById(R.id.me_ll_service);
        me_ll_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent service = new Intent(getContext(), ScreenService.class);
//                getActivity().startService(service);
//                Intent intent = new Intent("com.pooky.demo.MSG_ACTION");

                screenService.startDownLoad();
            }
        });
        me_iv_d_anim = (ImageView) view.findViewById(R.id.me_iv_d_anim);
//        me_ll_anim = (LinearLayout) view.findViewById(R.id.me_ll_anim);
        me_iv_d_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me_iv_d_anim.setImageResource(R.drawable.animation_list);
                animationDrawable = (AnimationDrawable) me_iv_d_anim.getDrawable();
                animationDrawable.start();
            }
        });

        return view;

    }

    /****************
     * 发起添加群流程。群号：测试(617955537) 的 key 为： hJtmRITn9VadK7CsvpXYWv68n7OrMbcW
     * 调用 joinQQGroup(hJtmRITn9VadK7CsvpXYWv68n7OrMbcW) 即可发起手Q客户端申请加群 测试(617955537)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(getActivity(), "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            //返回一个ScreenService对象
            screenService = ((ScreenService.MsgBinder)service).getSService();
            //注册回调接口来接受下载进度变化
            screenService.setOnProgressListener(new OnProgressListener() {
                @Override
                public void onProgress(int progress) {
                    mProgressBar.setProgress(progress);

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onDestroy() {
        getActivity().unbindService(connection);
        super.onDestroy();
    }
}
