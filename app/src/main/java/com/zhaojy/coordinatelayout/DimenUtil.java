package com.zhaojy.coordinatelayout;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Field;

/**
 * 类功能描述：Dimen utils: constains some dimension utils.
 *
 * @author 肖毅
 * @version 1.0
 * E-mail: 381004367@qq.com
 * @date 创建时间：2015年12月11日 下午10:15:10
 * @date 修改时间：2015年12月11日 下午10:15:10
 */
public class DimenUtil {
    /**
     * Get the width of current screen.
     *
     * @param contex the contex
     * @return the width
     */
    public static int getScreenWidth(Context contex) {
        DisplayMetrics dm = contex.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * Get the height of current screen.
     *
     * @param contex the context
     * @return the height
     */
    public static int getScreenHeight(Context contex) {
        DisplayMetrics dm = contex.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * Convert dp to pixel.
     *
     * @param context context to use
     * @param dp      dp
     * @return pixel
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5);
    }

    /**
     * Convert pixel to dp.
     *
     * @param context the context to use
     * @param px      pixel
     * @return dp
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5);
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, spValue,
                context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    private static int statusBarHeight = 0;

    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = context.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }


}
