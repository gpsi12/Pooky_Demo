package fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pooky.demo.R;

/**
 * 类描述：我的
 * Created by Gpsi on 2017-04-12.
 */

public class MeFragment extends Fragment {

    private RelativeLayout me_qq_qun;
    private ImageView me_iv_share;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, null);

        me_qq_qun = (RelativeLayout) view.findViewById(R.id.me_ll_qq_qun);
        me_qq_qun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isSucceed = joinQQGroup("hJtmRITn9VadK7CsvpXYWv68n7OrMbcW");
            }
        });
        me_iv_share = (ImageView) view.findViewById(R.id.me_iv_share);
        me_iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

}
