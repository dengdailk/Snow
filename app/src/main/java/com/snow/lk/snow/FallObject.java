package com.snow.lk.snow;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class FallObject {
    private  int initX;
    private int initY;
    private Random random;
    private int parentWidth;//父容器宽高
    private int parentHeight;
    private float objectWidth;//下落物体宽高
    private float objectHeight;

    public int initSpeed;//初始下降速度

    public float presentX;//当前位置坐标
    public float presentY;
    public float presentSpeed;//当前下降速度

    private Bitmap bitmap;
    public Builder builder;

    private static final int defautSpeed = 10;//默认下降速度

    public FallObject(Builder builder,int parentWidth,int parentHeight){
        random = new Random();
        this.parentWidth = parentWidth;
        this.parentHeight = parentHeight;
        initX = random.nextInt(parentWidth);//随机物体坐标
        initY = random.nextInt(parentHeight)-parentHeight;//让物体一开始从屏幕上端随机下降

        presentX = initX;
        presentY = initY;

        initSpeed = builder.initSpeed;

        presentSpeed = initSpeed;
        bitmap = builder.bitmap;
        objectWidth = bitmap.getWidth();
        objectHeight = bitmap.getHeight();
    }


    private FallObject(Builder builder){
        this.builder = builder;
        initSpeed = builder.initSpeed;
        bitmap = builder.bitmap;
    }



    public static final class Builder{
        private int initSpeed;
        private Bitmap bitmap;

        public Builder(Bitmap bitmap){
            this.initSpeed = defautSpeed;
            this.bitmap = bitmap;
        }
        public Builder(Drawable drawable){
            this.initSpeed = defautSpeed;
            this.bitmap = drawableToBitmap(drawable);
        }

        /**
         * 设置物体初始下落速度
         * @param speed
         * @return
         */
        public Builder setSpeed(int speed){
            this.initSpeed = speed;
            return this;
        }

        public FallObject build(){
            return new FallObject(this);
        }
        public Builder setSize(int w,int h){
            this.bitmap = changeBitmapSize(this.bitmap,w,h);
            return this;
        }

        public static Bitmap drawableToBitmap(Drawable drawable){
            Bitmap bitmap = Bitmap.createBitmap(
              drawable.getIntrinsicWidth(),
              drawable.getIntrinsicHeight(),
              drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565
            );
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        public static Bitmap changeBitmapSize(Bitmap bitmap,int newW,int newH){
            int oldW = bitmap.getWidth();
            int oldH = bitmap.getHeight();

            float scaleWidth = ((float)newW)/oldW;
            float scaleHeight = ((float)newH)/oldH;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth,scaleHeight);

            bitmap = Bitmap.createBitmap(bitmap,0,0,oldW,oldH,matrix,true);
            return bitmap;
        }


    }
    /**
     * 绘制物体对象
     */
    public void drawObject(Canvas canvas){
        moveObject();
        canvas.drawBitmap(bitmap,presentX,presentY,null);
    }

    /**
     * 移动物体对象
     */
    private void moveObject(){
        moveY();
        if(presentY > parentHeight){
            reset();
        }
    }

    /**
     * Y轴上的移动逻辑
     */
    private void moveY(){
        presentY += presentSpeed;
    }

    /**
     * 重置object位置
     */
    private void reset(){
        presentY = -objectHeight;
        presentSpeed = initSpeed;
    }
}
