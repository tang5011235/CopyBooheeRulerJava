package test.copybooheerulerjava;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import test.copybooheerulerjava.utils.DensityUtils;

/**
 * Created by Administrator on 2018/2/23.
 */

public class RulerView extends View {

    private Paint mRulerScalePaint;
    private Paint mRulerPaint;
    private Ruler mRuler;

    //速度获取
    private VelocityTracker mVelocityTracker;
    private OverScroller mOverScroller;
    private int mMinimumFlingVelocity;
    private int mMaximumFlingVelocity;
    private float mLastX;
    private int mMinPositionX;
    private int mMaxPositionX;
    private int mHalfWidth;
    private float mCurrentScale;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRuler(context);
    }

    private void initRuler(Context context) {
        mRulerScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRulerScalePaint.setStrokeWidth(DensityUtils.dip2px(context,2));
        mRulerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRulerPaint.setTextAlign(Paint.Align.CENTER);
        mRulerPaint.setTextSize(DensityUtils.dip2px(context, 30));

        if (mRuler == null) {
            mRuler = new Ruler(context);
            mRuler.setInterval(15);
            mRuler.setMinScale(30);
            mRuler.setMaxScale(80);
            mRuler.setScaleColor(Color.BLACK);
            mRuler.setScaleTextColor(Color.BLUE);
            mRuler.setSmallerScaleHight(10);
            mRuler.setBiggerScaleHight(20);
            mRuler.setSpaceBetween(15);
        }

        mOverScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = resolveSize(getMeasuredWidth(), widthMeasureSpec);
        int height = resolveSize(getMeasuredWidth(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画尺子
        mRulerScalePaint.setColor(mRuler.getScaleColor());
        mRulerPaint.setColor(mRuler.getScaleTextColor());
        canvas.save();
        for (float i = mRuler.getMinScale(); i <= mRuler.getMaxScale(); i++) {
            if (i % 10 == 0) {
                Rect  rect = new Rect();
                mRulerPaint.getTextBounds(i + "",0,(i + "").length(),rect);
                canvas.drawText(i + "", 0, mRuler.getBiggerScaleHight()+rect.bottom-rect.top + mRuler.getSpaceBetween(), mRulerPaint);
                canvas.drawLine(0, 0, 0, mRuler.getBiggerScaleHight(), mRulerScalePaint);
            } else {
                canvas.drawLine(0, 0, 0, mRuler.getSmallerScaleHight(), mRulerScalePaint);
            }
            canvas.translate(mRuler.getInterval(), 0);
        }
        canvas.restore();
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        mVelocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                mLastX = currentX;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = mLastX - currentX;
                mLastX = currentX;
                scrollBy((int) (moveX), 0);
                break;
            case MotionEvent.ACTION_UP:
                //处理松手后的Fling
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                int velocityX = (int) mVelocityTracker.getXVelocity();
                if (Math.abs(velocityX) > mMinimumFlingVelocity) {
                    fling(-velocityX);
                } else {
                     scrollBackToCurrentScale();
                }
                mVelocityTracker.clear();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                break;
        }
        return true;
    }

    private void scrollBackToCurrentScale() {
        mCurrentScale = Math.round(mCurrentScale);
        mOverScroller.startScroll(getScrollX(),0,scaleToScrollX(mCurrentScale) - getScrollX(),0,1000);
        invalidate();
    }

    private void fling(int vX) {
        mOverScroller.fling(getScrollX(), 0, vX, 0, mMinPositionX, mMaxPositionX, 0, 0);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHalfWidth = getMeasuredWidth() / 2;
        mMinPositionX = -mHalfWidth;
        mMaxPositionX = (int) (mRuler.getRulerSize() * mRuler.getInterval()) - mHalfWidth;
    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            if (!mOverScroller.computeScrollOffset() && mCurrentScale != Math.round(mCurrentScale)){
                //Fling完进行一次检测回滚
                scrollBackToCurrentScale();
            }
            invalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (x < mMinPositionX) {
            x = mMinPositionX;
        }
        if (x > mMaxPositionX) {
            x = mMaxPositionX;
        }
        if (x != getScrollX()) {
            super.scrollTo(x, y);
        }

        mCurrentScale = scrollXtoScale(x);
        Log.d(TAG, "scrollTo: " + mCurrentScale);

        if (mRulerCallBack != null) {
            mRulerCallBack.onScaleChanging(mCurrentScale);
        }
    }

    private float scrollXtoScale(int scrollX){
        //因为最小值mMinPosition = -mHalfWidth故加了一个mHalfWidth归为原点
        return ((float) (scrollX + mHalfWidth) / mRuler.getInterval()) + mRuler.getMinScale();
    }

    //把Scale转化为ScrollX
    private int scaleToScrollX(float scale){
        return (int) ((scale  - mRuler.getMinScale())* mRuler.getInterval() - mHalfWidth);
    }

    private static final String TAG = "RulerView";

    public void setRulerStyle(Ruler ruler){
        mRuler = ruler;
        invalidate();
    }

    public  interface RulerCallBack{
        void onScaleChanging(float currentScale);
    }

    private RulerCallBack mRulerCallBack;

    public void setRulerCallBack(RulerCallBack callBack){
        this.mRulerCallBack = callBack;
    }
}
