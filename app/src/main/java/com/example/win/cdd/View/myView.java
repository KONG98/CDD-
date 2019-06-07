package com.example.win.cdd.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.win.cdd.Controller.GameController;
import com.example.win.cdd.Controller.MenuController;
import com.example.win.cdd.Controller.ResultController;
import com.example.win.cdd.Model.GameModel;

public class myView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    boolean threadFlag = true;
    Context context;
    Canvas canvas;
    GameView gameView;
    MenuView menuView;
    ResultView resultView;
    SurfaceHolder holder;
    GameModel gameModel=GameModel.getGameModel();
    GameController gameController=GameController.getGameController();
    MenuController menuController=MenuController.getMenuController();
   ResultController resultController=ResultController.getResultController();

    public myView(Context context) {
        super(context);
        this.context = context;
        gameView=new GameView(context);
        menuView=new MenuView(context);
        resultView=new ResultView(context);
        this.getHolder().addCallback(this);
        this.setOnTouchListener(this);

    }

    Thread gameThread = new Thread() {
        @SuppressLint("WrongCall")
        @Override
        public void run() {
            holder = getHolder();
            while (threadFlag) {
                //desk.gameLogic();
                try{
                    canvas = holder.lockCanvas();
                    switch (gameModel.getGameState()){
                        case 0:
                            menuView.onDraw(canvas);
                            break;
                        case 1:
                            gameView.onDraw(canvas);
                            gameModel.checkGameOver();
                            break;
                        case 2:
                            resultView.onDraw(canvas);
                            break;
                            default:
                                break;
                    }

                }finally {
                    holder.unlockCanvasAndPost(canvas);
                }
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void surfaceCreated(SurfaceHolder holder) {
        threadFlag = true;
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //gameThread.isInterrupted();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadFlag = false;
        boolean retry = true;
        while (retry) {// 循环
            try {
                gameThread.join();// 等待线程结束
                retry = false;// 停止循环
            } catch (InterruptedException e) {
            }// 不断地循环，直到刷帧线程结束
        }
    }

   @Override
    public boolean onTouch(android.view.View v, MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (gameModel.getGameState()){
            case 0:
                menuController.returnMessage(x,y);
                break;
            case 1:
                gameController.returnMessage(x,y);
                break;
            case 2:
                resultController.returnMessage(x,y);
        }


        // desk.onTouch(x, y);
        return super.onTouchEvent(event);
    }
}
