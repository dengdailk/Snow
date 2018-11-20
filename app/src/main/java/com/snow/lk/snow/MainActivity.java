package com.snow.lk.snow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
////绘制雪球bitmap
    Paint snowPaint;
    Bitmap bitmap;
    Canvas bitmapCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paint snowPaint = new Paint();
        snowPaint.setColor(Color.WHITE);
        snowPaint.setStyle(Paint.Style.FILL);
        Bitmap bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmapCanvas.drawCircle(25,25,25,snowPaint);
        //初始化一个雪球样式的fallObject
        FallObject.Builder builder = new FallObject.Builder(bitmap);
        FallObject fallObject = builder
                .setSpeed(20)
                .build();

        FallingView fallingView = (FallingView) findViewById(R.id.fallingView);
        fallingView.addFallObject(fallObject,50);//添加50个雪球对象

    }
}
