package widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pooky.demo.R;

/**
 * 底部导航栏
 * <p>
 * Created by Pooky on 2017/9/1.
 */

public class BottomView extends LinearLayout implements View.OnClickListener {

    private ImageView iv_index = null;
    private ImageView iv_classify = null;
    private ImageView iv_mv = null;
    private ImageView iv_me = null;
    private IOnPageSelectedListener mListener;

    public BottomView(Context context) {
        super(context);
        init(context);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_bottom, this);
        iv_index = (ImageView) findViewById(R.id.iv_index);
        iv_classify = (ImageView) findViewById(R.id.iv_classify);
        iv_mv = (ImageView) findViewById(R.id.iv_mv);
        iv_me = (ImageView) findViewById(R.id.iv_me);

        iv_index.setOnClickListener(this);
        iv_classify.setOnClickListener(this);
        iv_mv.setOnClickListener(this);
        iv_me.setOnClickListener(this);

        switchBtnState(iv_index.getId());
    }

    public void setOnPageSelectListener(IOnPageSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        switchBtnState(view.getId());
        switch (view.getId()) {
            case R.id.iv_index:
                mListener.onPageSelect(0);
                break;
            case R.id.iv_classify:
                mListener.onPageSelect(1);
                break;
            case R.id.iv_mv:
                mListener.onPageSelect(2);
                break;
            case R.id.iv_me:
                mListener.onPageSelect(3);
                break;
        }
    }

    /**
     * 刷新icon状态
     *
     * @param viewId
     */
    private void switchBtnState(int viewId) {
        iv_index.setImageResource(viewId == iv_index.getId() ? R.drawable.ic_bottom_index_enabled : R.drawable.ic_bottom_index_disabled);
        iv_classify.setImageResource(viewId == iv_classify.getId() ? R.drawable.ic_bottom_class_enabled : R.drawable.ic_bottom_class_disabled);
        iv_mv.setImageResource(viewId == iv_mv.getId() ? R.drawable.ic_bottom_mv_enabled : R.drawable.ic_bottom_mv_disabled);
        iv_me.setImageResource(viewId == iv_me.getId() ? R.drawable.ic_bottom_me_enabled : R.drawable.ic_bottom_me_disabled);
    }

    public interface IOnPageSelectedListener {
        void onPageSelect(int index);
    }
}