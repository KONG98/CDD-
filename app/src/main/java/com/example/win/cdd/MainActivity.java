package com.example.win.cdd;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.win.cdd.View.GameView;
import com.example.win.cdd.View.MenuView;
import com.example.win.cdd.View.ResultView;
import com.example.win.cdd.View.myView;

import static com.example.win.cdd.Constants.ErrorConstants.EMPTY_CARD;
import static com.example.win.cdd.Constants.ErrorConstants.SMALL_CARD;
import static com.example.win.cdd.Constants.ErrorConstants.WRONG_CARD;
import static com.example.win.cdd.Constants.GameStateConstants.EXIT;
import static com.example.win.cdd.Constants.GameStateConstants.GAME;
import static com.example.win.cdd.Constants.GameStateConstants.START;
import static com.example.win.cdd.Constants.GameStateConstants.STOP;

public class MainActivity extends AppCompatActivity {


    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static double SCALE_VERTICAL;
    public static double SCALE_HORIAONTAL;
    public static Handler handler;
    private myView cddview;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置屏幕横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // 设置屏幕全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 获取屏幕长宽
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        SCREEN_WIDTH = displayMetrics.widthPixels;
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        if(SCREEN_HEIGHT > SCREEN_WIDTH) {
            int temp = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = temp;
        }

        // 适应屏幕大小
        SCALE_VERTICAL = SCREEN_HEIGHT / 320.0;
        SCALE_HORIAONTAL = SCREEN_WIDTH / 480.0;


        cddview =new myView(this);


        // 进入开始菜单
        setContentView(cddview);


        // 监听主进程中的信息
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case EXIT : // 点击退出游戏
                        finish();
                        break;
                    case SMALL_CARD : // 提示出牌过小
                        Toast.makeText(getApplicationContext(), "你的牌太小！", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case WRONG_CARD : // 提示出牌违规
                        Toast.makeText(getApplicationContext(), "出牌不符合规则！", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case EMPTY_CARD : // 提示尚未出牌
                        Toast.makeText(getApplicationContext(), "请出牌！", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        };
    }
}

