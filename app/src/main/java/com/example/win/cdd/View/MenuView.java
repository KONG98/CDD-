package com.example.win.cdd.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.win.cdd.Controller.MenuController;
import com.example.win.cdd.MainActivity;
import com.example.win.cdd.R;

public class MenuView extends SurfaceView  {

    SurfaceHolder holder;
    Bitmap background;
    Context context;
    private int x = 270;
    private int y = 180;
    private Bitmap[] menuItems;

    public MenuView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        menuItems = new Bitmap[2];
        holder = getHolder();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu_bg);
        menuItems[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu1);
        menuItems[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu5);
    }


        @SuppressLint("DrawAllocation")

        protected void onDraw(Canvas canvas) {
            Rect src = new Rect();
            Rect des = new Rect();
            src.set(0, 0, background.getWidth(), background.getHeight());
            des.set(0, 0, MainActivity.SCREEN_WIDTH, MainActivity.SCREEN_HEIGHT);
            Paint paint = new Paint();
            canvas.drawBitmap(background, src, des, paint);
            for (int i = 0; i < menuItems.length; i++) {
                canvas.drawBitmap(menuItems[i], (int) (x * MainActivity.SCALE_HORIAONTAL + 200),
                        (int) ((y + i * 43) * MainActivity.SCALE_VERTICAL + 150), paint);
            }
        }

    }
