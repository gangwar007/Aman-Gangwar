package com.buildrepo.circlerotation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

public class AnimationView extends View {

    Paint paint;
    Bitmap bm;
    int bm_offsetX, bm_offsetY;
    Path animPath;
    PathMeasure pathMeasure;
    float pathLength;
    float step;   //distance each step
    float distance;  //distance moved
    float[] pos;
    float[] tan;
    Matrix matrix;

    public AnimationView(Context context) {
        super(context);
        initMyView();
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initMyView();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMyView();
    }

    public void initMyView(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        DashPathEffect dashPath = new DashPathEffect(new float[]{6,6}, (float)2.0);

        paint.setPathEffect(dashPath);

        bm = BitmapFactory.decodeResource(getResources(), R.drawable.airplane);
        bm_offsetX = bm.getWidth()/2;
        bm_offsetY = bm.getHeight()/2;

        animPath = new Path();
        animPath.addCircle(600, 600, 500, Path.Direction.CW);
        animPath.close();

        pathMeasure = new PathMeasure(animPath, false);
        pathLength = pathMeasure.getLength();

        step = 1;
        distance = 0;
        pos = new float[2];
        tan = new float[2];

        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawPath(animPath, paint);

        if(distance < pathLength){
            pathMeasure.getPosTan(distance, pos, tan);

            matrix.reset();
            float degrees = (float)(Math.atan2(tan[0], tan[1])*0.0/Math.PI);
            matrix.postRotate(degrees, bm_offsetX, bm_offsetY);
            matrix.postTranslate(pos[0]-bm_offsetX, pos[1]-bm_offsetY);

            canvas.drawBitmap(bm, matrix, null);

            distance += step;
        }else{
            distance = 0;
        }

        invalidate();
    }

}
