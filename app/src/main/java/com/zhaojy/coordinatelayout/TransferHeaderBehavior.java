package com.zhaojy.coordinatelayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * @author Zhaojy
 * @date 2019/4/4.
 * email：770363647@qq.com
 * weixin:18774512067
 * description：
 */
public class TransferHeaderBehavior extends CoordinatorLayout.Behavior<CircleImageView> {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * 处于中心时候原始X轴
     */
    private int mOriginalHeaderX = 0;
    /**
     * 处于中心时候原始Y轴
     */
    private int mOriginalHeaderY = 0;

    /**
     * 初始头像宽高
     */
    private int avatarWh = 0;

    /**
     * toolbar高度
     */
    private int toolbarH = 0;

    /**
     * 头像最小缩放比
     */
    private final static double MIN_AVATAR_SCALE = 0.6;

    private Context context;

    public TransferHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {

        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        // 计算X轴坐标
        if (mOriginalHeaderX == 0) {
            this.mOriginalHeaderX = dependency.getWidth() / 2 - child.getWidth() / 2;
        }
        // 计算Y轴坐标
        if (mOriginalHeaderY == 0) {
            mOriginalHeaderY = dependency.getHeight() - child.getHeight();
        }
        //计算头像宽高
        if (avatarWh == 0) {
            avatarWh = child.getWidth();
        }
        //计算toolbar高度
        if (toolbarH == 0) {
            toolbarH = DimenUtil.dp2px(context, 64);
        }
        //X轴百分比
        float mPercentX = dependency.getY() / mOriginalHeaderX;
        if (mPercentX >= 1) {
            mPercentX = 1;
        }
        //Y轴百分比
        float mPercentY = dependency.getY() / mOriginalHeaderY;
        if (mPercentY >= 1) {
            mPercentY = 1;
        }

        float x = mOriginalHeaderX - mOriginalHeaderX * mPercentX;
        if (x <= DimenUtil.dp2px(context, 40)) {
            x = DimenUtil.dp2px(context, 40);
        }
        float y = mOriginalHeaderY - mOriginalHeaderY * mPercentY;

        //像的放大和缩小
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                child.getLayoutParams();
        //头像最小宽高
        int minWh = (int) (avatarWh * MIN_AVATAR_SCALE);
        int tempWh = (int) (avatarWh - mPercentY * (avatarWh - minWh));
        if (tempWh < minWh) {
            tempWh = minWh;
        }
        layoutParams.width = tempWh;
        layoutParams.height = tempWh;
        child.setLayoutParams(layoutParams);

        //保证头像在toolbar上居中
        int minY = toolbarH / 2 - minWh / 2;
        if (y < minY) {
            y = minY;
        }

        child.setX(x);
        child.setY(y);

        return true;
    }
}