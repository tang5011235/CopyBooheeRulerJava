package test.copybooheerulerjava;

import android.content.Context;

import test.copybooheerulerjava.utils.DensityUtils;

/**
 * Created by Administrator on 2018/2/23.
 */

public class Ruler {
    private float minScale;
    private float maxScale;
    private float interval;
    private int scaleTextColor;
    private int scaleColor;
    private float smallerScaleHight;
    private float biggerScaleHight;
    private float rulerSize;
    private Context context;

    public float getSpaceBetween() {
        return spaceBetween;
    }

    public void setSpaceBetween(float spaceBetween) {
        this.spaceBetween =  DensityUtils.dip2px(context,spaceBetween);
    }

    private float spaceBetween;//尺度与文字间距


    public Ruler(Context context) {
        this.context = context;
    }

    public float getRulerSize() {
        return maxScale - minScale;
    }

    public float getBiggerScaleHight() {
        return biggerScaleHight;
    }

    public void setBiggerScaleHight(float biggerScaleHight) {
        this.biggerScaleHight = DensityUtils.dip2px(context,biggerScaleHight);
    }

    public float getSmallerScaleHight() {
        return smallerScaleHight;
    }

    public void setSmallerScaleHight(float smallerScaleHight) {
        this.smallerScaleHight = DensityUtils.dip2px(context,smallerScaleHight);
    }

    public float getMinScale() {
        return minScale;
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    public float getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = DensityUtils.dip2px(context,interval);
    }

    public int getScaleTextColor() {
        return scaleTextColor;
    }

    public void setScaleTextColor(int scaleTextColor) {
        this.scaleTextColor = scaleTextColor;
    }

    public int getScaleColor() {
        return scaleColor;
    }

    public void setScaleColor(int scaleColor) {
        this.scaleColor = scaleColor;
    }
}
