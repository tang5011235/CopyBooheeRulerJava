package test.copybooheerulerjava;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import test.copybooheerulerjava.utils.DensityUtils;

/**
 * Created by Administrator on 2018/2/23.
 */

public class BooheeRuler extends RelativeLayout {

    private int mHalfWidth;
    private Context context;

    public BooheeRuler(Context context) {
        super(context);
        initRuler(context);
    }


    public BooheeRuler(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRuler(context);
    }

    public BooheeRuler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRuler(context);
    }

    private void initRuler(Context context) {
        RulerView rulerView = new RulerView(context);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        rulerView.setLayoutParams(layoutParams);

        addView(rulerView);

        this.context = context;
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint  paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(DensityUtils.dip2px(context,4));
        canvas.save();
        canvas.translate(mHalfWidth,0);
        canvas.drawLine(0,0,0, DensityUtils.dip2px(context,20),paint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHalfWidth = getMeasuredWidth() / 2;
    }
}
