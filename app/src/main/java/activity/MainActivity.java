package activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.pooky.demo.R;

import fragment.ClassifyFragment;
import fragment.CleanFragment;
import fragment.IndexFragment;
import fragment.MeFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private Context mContext;
    private ImageView iv_index, iv_classify, iv_mv, iv_me;
    private long mPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtils.setWindowStatusBarColor(this,1296db);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();

        initSlidingMenu(savedInstanceState);
        FragmentPagerItems pagerItems = new FragmentPagerItems(this);
        pagerItems.add(FragmentPagerItem.of("首页", IndexFragment.class));
        pagerItems.add(FragmentPagerItem.of("分类", ClassifyFragment.class));
        pagerItems.add(FragmentPagerItem.of("清理", CleanFragment.class));
        pagerItems.add(FragmentPagerItem.of("我的", MeFragment.class));

        mViewPager.setCurrentItem(4);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pagerItems);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * 初始化侧滑菜单
     *
     * @param savedInstanceState
     */
    private void initSlidingMenu(Bundle savedInstanceState) {

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        iv_index = (ImageView) findViewById(R.id.iv_index);
        iv_index.setOnClickListener(this);
        iv_index.setImageResource(R.drawable.ic_bottom_index_enabled);
        iv_classify = (ImageView) findViewById(R.id.iv_classify);
        iv_classify.setOnClickListener(this);
        iv_mv = (ImageView) findViewById(R.id.iv_mv);
        iv_mv.setOnClickListener(this);
        iv_me = (ImageView) findViewById(R.id.iv_me);
        iv_me.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_index:
                mViewPager.setCurrentItem(0);
                iv_index.setImageResource(R.drawable.ic_bottom_index_enabled);
                iv_classify.setImageResource(R.drawable.ic_bottom_class_disabled);
                iv_mv.setImageResource(R.drawable.ic_bottom_mv_disabled);
                iv_me.setImageResource(R.drawable.ic_bottom_me_disabled);
                break;
            case R.id.iv_classify:
                mViewPager.setCurrentItem(1);
                iv_index.setImageResource(R.drawable.ic_bottom_index_disabled);
                iv_classify.setImageResource(R.drawable.ic_bottom_class_enabled);
                iv_mv.setImageResource(R.drawable.ic_bottom_mv_disabled);
                iv_me.setImageResource(R.drawable.ic_bottom_me_disabled);
                break;
            case R.id.iv_mv:
                mViewPager.setCurrentItem(2);
                iv_index.setImageResource(R.drawable.ic_bottom_index_disabled);
                iv_classify.setImageResource(R.drawable.ic_bottom_class_disabled);
                iv_mv.setImageResource(R.drawable.ic_bottom_mv_enabled);
                iv_me.setImageResource(R.drawable.ic_bottom_me_disabled);
                break;
            case R.id.iv_me:
                mViewPager.setCurrentItem(3);
                iv_index.setImageResource(R.drawable.ic_bottom_index_disabled);
                iv_classify.setImageResource(R.drawable.ic_bottom_class_disabled);
                iv_mv.setImageResource(R.drawable.ic_bottom_mv_disabled);
                iv_me.setImageResource(R.drawable.ic_bottom_me_enabled);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long mNoewTime = System.currentTimeMillis();
            if (mNoewTime - mPressedTime > 2000) {
                Toast.makeText(mContext, "再按一次退出", Toast.LENGTH_SHORT).show();
                mPressedTime = mNoewTime;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
