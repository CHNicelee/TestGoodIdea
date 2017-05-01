package com.ice.testgoodidea.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ice.testgoodidea.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by asd on 5/1/2017.
 */

public class WaveButton extends View {

    private static final String TAG = "ICE";
    private Paint mPaint;
    private int mRadius;//里面圆圈的半径
    private Context mContext;
    private int mWidth;//控件的宽度
    private int mStrokeWidth;
    private int mFillColor;
    private int mCircleStrokeColor;
    private int mTextSize;
    private int gapSize;
    private int firstRadius;//第一个圆圈的半径
    private int numberOfCircle;
    private int mLineColor;
    private String mText;
    private int mTextColor;
    private Paint mTextPaint;
    private boolean isFirstTime = true;
    private OnClickListener mClickListener;//点击事件监听器
    private float mDownX,mDownY;

    public WaveButton(Context context) {
        this(context,null);
    }

    public WaveButton(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public WaveButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaveButton);
        mText = ta.getString(R.styleable.WaveButton_text);
        if (mText == null) mText = "" ;
        mTextColor = ta.getColor(R.styleable.WaveButton_textColor, Color.BLACK);
        mTextSize = ta.getDimensionPixelSize(R.styleable.WaveButton_textSize, 50);
        mFillColor = ta.getColor(R.styleable.WaveButton_fillColor,Color.WHITE);
        mLineColor = ta.getColor(R.styleable.WaveButton_waveColor,Color.BLACK);
        mCircleStrokeColor = ta.getColor(R.styleable.WaveButton_strokeColor,mFillColor);
        ta.recycle();  //注意回收

        init(context);
    }

    private void init(Context context){
        mContext = context;

        mWidth = dip2px(400);
        mStrokeWidth = 4;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        numberOfCircle = 4;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode){
            case MeasureSpec.EXACTLY:
                //match_parent 或者 精确的数值
                mWidth = width;
                break;
        }
        switch (heightMode){
            case MeasureSpec.EXACTLY:
                mWidth = Math.min(mWidth,height);
                break;
        }
        mRadius = mWidth/4;
        gapSize = (mWidth/2-mRadius)/numberOfCircle;
        firstRadius = mRadius + gapSize;
        setMeasuredDimension(mWidth,mWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mWidth/2);//平移

        //画中间的圆
        mPaint.setAlpha(255);
        mPaint.setColor(mFillColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0,0,mRadius,mPaint);
        //画圆的边
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mCircleStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0,0,mRadius,mPaint);
        //画文字
        Rect rect = new Rect();//文字的区域
        mTextPaint.getTextBounds(mText,0,mText.length(),rect);
        int height = rect.height();
        int width = rect.width();
        canvas.drawText(mText,-width/2,height/2,mTextPaint);

        //画周围的波浪
        firstRadius += 3;//每次刷新半径增加3像素
        firstRadius %= (mWidth/2);//控制在控件的范围中
        if(firstRadius<mRadius) isFirstTime =false;
        firstRadius = checkRadius(firstRadius);//检查半径的范围
        mPaint.setColor(mLineColor);
        mPaint.setStyle(Paint.Style.STROKE);

        //画波浪
        for (int i = 0; i < numberOfCircle; i++) {
            int radius = (firstRadius + i*gapSize ) % (mWidth/2);
            if(isFirstTime && radius>firstRadius) continue;
            radius = checkRadius(radius);//检查半径的范围
            //用半径来计算透明度  半径越大  越透明
            double x = (mWidth/2 -radius)*1.0 /(mWidth/2 - mRadius);
            mPaint.setAlpha((int) (255*x));
            canvas.drawCircle(0,0,radius,mPaint);
        }


    }

    //检查波浪的半径  如果小于圆圈，那么加上圆圈的半径
    private int checkRadius(int radius) {
        if(radius<mRadius){
            return radius+mRadius + gapSize;
        }
        return radius;
    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void startAnimation(){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        },0,50);

    }

    public void setText(String text){
        this.mText = text;
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public String getText() {
        return mText;
    }


    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public int getGapSize() {
        return gapSize;
    }

    public void setGapSize(int gapSize) {
        this.gapSize = gapSize;
    }

    public int getFillColor() {
        return mFillColor;
    }

    public void setFillColor(int mFillColor) {
        this.mFillColor = mFillColor;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mClickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                return checkIsInCircle((int)mDownX,(int)mDownY);

            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX(),upY = (int) event.getY();
                if(checkIsInCircle(upX,upY) && mClickListener!=null){
                    mClickListener.onClick(this);
                }
                break;

        }
        return true;
    }

    /**
     * 检查点x,y是否落在圆圈内
     * @param x
     * @param y
     * @return
     */
    private boolean checkIsInCircle(int x, int y){

        int centerX = (getRight() + getLeft())/2;
        int centerY = (getTop() + getBottom())/2;
        return  Math.pow(x-centerX,2)+Math.pow(y-centerY,2) < Math.pow(mRadius,2);
    }
}

class DisplayUtil {

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);

    }



}