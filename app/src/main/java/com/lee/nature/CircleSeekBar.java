package com.lee.nature;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 * CircleSeekBar
 * 
 * @author lee
 *
 */

public class CircleSeekBar extends View {

    public final boolean DEBUG = true;
    public final String TAG = "CircleSeekBar";
    
    public Context mContext = null;
    public AttributeSet mAttrs = null;
    
    public Drawable mThumbDrawable = null;
    public int mThumbHeight = 0;
    public int mThumbWidth = 0;
    public int[] mThumbNormal = null;
    public int[] mThumbPressed = null;
    
    public int mSeekBarMax = 0;
    public Paint mSeekBarBackgroundPaint = null;
    public Paint mSeekbarProgressPaint = null;
    public RectF mArcRectF = null;
    
    public boolean mIsShowProgressText = false;
    public Paint mProgressTextPaint = null;
    public int mProgressTextSize = 0;
    
    public int mViewHeight = 0;
    public int mViewWidth = 0;
    public int mSeekBarSize = 0;
    public int mSeekBarRadius = 0;
    public int mSeekBarCenterX = 0;
    public int mSeekBarCenterY = 0;
    public float mThumbLeft = 0;
    public float mThumbTop = 0;
    
    public float mSeekBarDegree = 0;
    public int mCurrentProgress = 0;
    
    private OnCircleSeekBarChangeListener mOnCircleSeekBarChangeListener; 
    
    public CircleSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mAttrs = attrs;
        initView();
    }
    
    public CircleSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mAttrs = attrs;
        initView();
    }
    
    public CircleSeekBar(Context context) {
        super(context);
        mContext = context;
        initView();
    }
    
    public void initView(){
        if(DEBUG) Log.d(TAG, "initView");
        TypedArray localTypedArray = mContext.obtainStyledAttributes(mAttrs, R.styleable.CircleSeekBar);
        

        mThumbDrawable = localTypedArray.getDrawable(R.styleable.CircleSeekBar_android_thumb);
        mThumbWidth = this.mThumbDrawable.getIntrinsicWidth();
        mThumbHeight = this.mThumbDrawable.getIntrinsicHeight();
        
        mThumbNormal = new int[]{-android.R.attr.state_focused, -android.R.attr.state_pressed, 
                -android.R.attr.state_selected, -android.R.attr.state_checked};
        mThumbPressed = new int[]{android.R.attr.state_focused, android.R.attr.state_pressed, 
                android.R.attr.state_selected, android.R.attr.state_checked};
        
        float progressWidth = localTypedArray.getDimension(R.styleable.CircleSeekBar_progress_width, 5);
        int progressBackgroundColor = localTypedArray.getColor(R.styleable.CircleSeekBar_progress_background, Color.GRAY);
        int progressFrontColor = localTypedArray.getColor(R.styleable.CircleSeekBar_progress_front, Color.BLUE);
        mSeekBarMax = localTypedArray.getInteger(R.styleable.CircleSeekBar_progress_max, 100);
        
        mSeekbarProgressPaint = new Paint();
        mSeekBarBackgroundPaint = new Paint();
        
        mSeekbarProgressPaint.setColor(progressFrontColor);
        mSeekBarBackgroundPaint.setColor(progressBackgroundColor);
        
        mSeekbarProgressPaint.setAntiAlias(true);
        mSeekBarBackgroundPaint.setAntiAlias(true);
        
        mSeekbarProgressPaint.setStyle(Paint.Style.STROKE);
        mSeekBarBackgroundPaint.setStyle(Paint.Style.STROKE);
        
        mSeekbarProgressPaint.setStrokeWidth(progressWidth);
        mSeekBarBackgroundPaint.setStrokeWidth(progressWidth);
        
        mArcRectF = new RectF();
        
        mIsShowProgressText = localTypedArray.getBoolean(R.styleable.CircleSeekBar_show_progress_text, false);
        int progressTextStroke = (int) localTypedArray.getDimension(R.styleable.CircleSeekBar_progress_text_stroke_width, 5);
        int progressTextColor = localTypedArray.getColor(R.styleable.CircleSeekBar_progress_text_color, Color.GREEN);
        mProgressTextSize = (int) localTypedArray.getDimension(R.styleable.CircleSeekBar_progress_text_size, 50);
        mProgressTextPaint = new Paint();
        mProgressTextPaint.setColor(progressTextColor);
        mProgressTextPaint.setAntiAlias(true);
        mProgressTextPaint.setStrokeWidth(progressTextStroke);
        mProgressTextPaint.setTextSize(mProgressTextSize);
        
        localTypedArray.recycle();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(DEBUG) Log.d(TAG, "onMeasure");
        mViewWidth = getWidth();
        mViewHeight = getHeight();
        
        mSeekBarSize = mViewWidth > mViewHeight ? mViewHeight : mViewWidth;

        mSeekBarCenterX = mViewWidth / 2;
        mSeekBarCenterY = mViewHeight / 2;
        
        mSeekBarRadius = mSeekBarSize / 2 - mThumbWidth / 2;
        
        int left = mSeekBarCenterX - mSeekBarRadius;
        int right = mSeekBarCenterX + mSeekBarRadius;
        int top = mSeekBarCenterY - mSeekBarRadius;
        int bottom = mSeekBarCenterY + mSeekBarRadius;
        mArcRectF.set(left, top, right, bottom);

        setThumbPosition(Math.toRadians(mSeekBarDegree));
    }
    
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(mSeekBarCenterX, mSeekBarCenterY, mSeekBarRadius,
                mSeekBarBackgroundPaint);
        canvas.drawArc(this.mArcRectF, 0.0F, mSeekBarDegree, false, mSeekbarProgressPaint);
        drawThumbBitmap(canvas);
        drawProgressText(canvas);
        
        super.onDraw(canvas);
    }
    
    public void drawThumbBitmap(Canvas canvas) {
        this.mThumbDrawable.setBounds((int) mThumbLeft, (int) mThumbTop,
                (int) (mThumbLeft + mThumbWidth - 1), (int) (mThumbTop + mThumbHeight - 1));
        this.mThumbDrawable.draw(canvas);
    }
    
    public void drawProgressText(Canvas canvas) {
        if (true == mIsShowProgressText){
            float textWidth = mProgressTextPaint.measureText("" + mCurrentProgress);
            canvas.drawText("" + mCurrentProgress, mSeekBarCenterX - textWidth / 2, mSeekBarCenterY
                    + mProgressTextSize / 2, mProgressTextPaint);
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                seekTo(eventX, eventY, false);
                break ;
                
            case MotionEvent.ACTION_MOVE:
                seekTo(eventX, eventY, false);
                if (mOnCircleSeekBarChangeListener != null)  
                mOnCircleSeekBarChangeListener.onProgressChanged(this, mCurrentProgress, true);  
                break ;
                
            case MotionEvent.ACTION_UP:
                seekTo(eventX, eventY, false);
                break ;
        }
        return true;
    }

    public void seekTo(float eventX, float eventY, boolean isUp) {
        if (true == isPointOnThumb(eventX, eventY) && false == isUp) {
            mThumbDrawable.setState(mThumbPressed);
            double radian = Math.atan2(eventY - mSeekBarCenterY, eventX - mSeekBarCenterX);
            /*

             */
            if (radian < 0){
                radian = radian + 2*Math.PI;
            }
            if(DEBUG) Log.e(TAG, "seekTo radian = " + radian);
            setThumbPosition(radian);
            
            mSeekBarDegree = (float) Math.round(Math.toDegrees(radian));
            mCurrentProgress = (int) (mSeekBarMax * mSeekBarDegree / 360);
            invalidate();
        }else{
            mThumbDrawable.setState(mThumbNormal);
            invalidate();
        }
    }

    public boolean isPointOnThumb(float eventX, float eventY) {
        boolean result = false;
        double distance = Math.sqrt(Math.pow(eventX - mSeekBarCenterX, 2)
                + Math.pow(eventY - mSeekBarCenterY, 2));
        if (distance < mSeekBarSize && distance > (mSeekBarSize / 2 - mThumbWidth)){
            result = true;
        }
        return result;
    }
    
    public void setThumbPosition(double radian) {
        if(DEBUG) Log.v(TAG, "setThumbPosition radian = " + radian);
        double x = mSeekBarCenterX + mSeekBarRadius * Math.cos(radian);
        double y = mSeekBarCenterY + mSeekBarRadius * Math.sin(radian);
        mThumbLeft = (float) (x - mThumbWidth / 2);
        mThumbTop = (float) (y - mThumbHeight / 2);
    }
    

    public void setProgress(int progress) {
        if(DEBUG) Log.v(TAG, "setProgress progress = " + progress);
        if (progress > mSeekBarMax){
            progress = mSeekBarMax;
        }
        if (progress < 0){
            progress = 0;
        }
        mCurrentProgress = progress;
        mSeekBarDegree = (progress * 360 / mSeekBarMax);
        if(DEBUG) Log.d(TAG, "setProgress mSeekBarDegree = " + mSeekBarDegree);
        setThumbPosition(Math.toRadians(mSeekBarDegree));
        
        invalidate();
    }
    
    public int getProgress(){
        return mCurrentProgress;
    }
    
    public void setProgressMax(int max){
        if(DEBUG) Log.v(TAG, "setProgressMax max = " + max);
        mSeekBarMax = max;
    }
    
    public int getProgressMax(){
        return mSeekBarMax;
    }
    
    public void setProgressThumb(int thumbId){
        mThumbDrawable = mContext.getResources().getDrawable(thumbId);
    }
    
    public void setProgressWidth(int width){
        if(DEBUG) Log.v(TAG, "setProgressWidth width = " + width);
        mSeekbarProgressPaint.setStrokeWidth(width);
        mSeekBarBackgroundPaint.setStrokeWidth(width);
    }
    
    public void setProgressBackgroundColor(int color){
        mSeekBarBackgroundPaint.setColor(color);
    }
    
    public void setProgressFrontColor(int color){
        mSeekbarProgressPaint.setColor(color);
    }
    
    public void setProgressTextColor(int color){
        mProgressTextPaint.setColor(color);
    }
    
    public void setProgressTextSize(int size){
        if(DEBUG) Log.v(TAG, "setProgressTextSize size = " + size);
        mProgressTextPaint.setTextSize(size);
    }
    
    public void setProgressTextStrokeWidth(int width){
        if(DEBUG) Log.v(TAG, "setProgressTextStrokeWidth width = " + width);
        mProgressTextPaint.setStrokeWidth(width);
    }
    
    public void setIsShowProgressText(boolean isShow){
        mIsShowProgressText = isShow;
    }
    public void setOnSeekBarChangeListener(OnCircleSeekBarChangeListener l) {  
       mOnCircleSeekBarChangeListener = l;  
    }  
  
    public interface OnCircleSeekBarChangeListener {  
  
        public abstract void onProgressChanged(CircleSeekBar seekBar,  
                int progress, boolean fromUser);  
  
    }

}


