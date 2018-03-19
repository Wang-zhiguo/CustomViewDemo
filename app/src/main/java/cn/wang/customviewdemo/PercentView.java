package cn.wang.customviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class PercentView extends View {
    private static final String TAG = "PercentView";
    private Paint mPaint;
    private RectF oval;
    private int backgroundColor = Color.GRAY;
    private int progressColor = Color.BLUE;
    private float radius;
    private int progress;
    private float centerX = 0;
    private float centerY = 0;
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int CENTER = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM = 4;
    private int gravity = CENTER;
    public PercentView(Context context) {
        super(context);
        init();
    }

    public PercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context,attrs);
    }

    public PercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context,attrs);
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval=new RectF();
    }
    //读取布局文件中的自定义属性值
    private void initParams(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval = new RectF();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentView);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.PercentView_percent_background_color, Color.GRAY);
            progressColor = typedArray.getColor(R.styleable.PercentView_percent_progress_color, Color.BLUE);
            radius = typedArray.getDimension(R.styleable.PercentView_percent_circle_radius, 0);
            progress = typedArray.getInt(R.styleable.PercentView_percent_circle_progress, 0);
            gravity = typedArray.getInt(R.styleable.PercentView_percent_circle_gravity, CENTER);
            typedArray.recycle();
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "onMeasure--widthMode-->" + widthMode);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:

                break;
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.UNSPECIFIED:

                break;
        }
        Log.e(TAG, "onMeasure--widthSize-->" + widthSize);
        Log.e(TAG, "onMeasure--heightMode-->" + heightMode);
        Log.e(TAG, "onMeasure--heightSize-->" + heightSize);
        //-------------------------以下为新增代码-------------------------------
        //1.首先获取PercentView的宽度和高度
        int with = getMeasuredWidth();
        int height = getMeasuredHeight();
        Log.e(TAG, "onDraw---->" + with + "*" + height);
        //2.计算出圆心的坐标
        centerX = with / 2;
        centerY = with / 2;
        //3.根据gravity（对齐方式），修改圆心坐标
        switch (gravity) {
            case LEFT:
                centerX = radius + getPaddingLeft();
                break;
            case TOP:
                centerY = radius + getPaddingTop();
                break;
            case CENTER:
                break;
            case RIGHT:
                centerX = with - radius - getPaddingRight();
                break;
            case BOTTOM:
                centerY = height - radius - getPaddingBottom();
                break;
        }
        //4.计算出圆的外接矩形，用来绘制进度的扇形
        float left = centerX - radius;
        float top = centerY - radius;
        float right = centerX + radius;
        float bottom = centerY + radius;
        oval.set(left, top, right, bottom);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setColor(Color.GRAY);
//        // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        int with = getWidth();
//        int height = getHeight();
//        Log.e(TAG, "onDraw---->" + with + "*" + height);
//        float radius = with / 4;
//        canvas.drawCircle(with / 2, with / 2, radius, mPaint);
//        mPaint.setColor(Color.BLUE);
//        oval.set(with / 2 - radius, with / 2 - radius, with / 2
//                + radius, with / 2 + radius);//用于定义的圆弧的形状和大小的界限
//        canvas.drawArc(oval, 270, 120, true, mPaint);  //根据进度画圆弧

        //由于圆的参数由用户指定，故将源代码注释
        //1.使用backgroundColor绘制圆
        mPaint.setColor(backgroundColor);
        // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        //2.绘制进度的扇形
        mPaint.setColor(progressColor);
        double percent = progress * 1.0 / 100;
        int angle = (int) (percent * 360);
        canvas.drawArc(oval, 270, angle, true, mPaint);  //根据进度画圆弧
    }
}
