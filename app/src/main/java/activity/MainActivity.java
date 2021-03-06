package activity;

import android.content.Context;
import android.content.res.Configuration;
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
import utils.L;
import widget.BottomView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private Context mContext;
    private long mPressedTime = 0;
    private BottomView bv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtils.setWindowStatusBarColor(this,1296db);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        L.isDebug = true;
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
//                Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        bv_main = (BottomView) findViewById(R.id.bv_main);
        bv_main.setOnPageSelectListener(new BottomView.IOnPageSelectedListener() {
            @Override
            public void onPageSelect(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mViewPager.getCurrentItem();
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