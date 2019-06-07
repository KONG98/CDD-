package com.example.win.cdd.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.win.cdd.CardSet;
import com.example.win.cdd.CardsManager;
import com.example.win.cdd.Controller.GameController;
import com.example.win.cdd.MainActivity;
import com.example.win.cdd.Model.GameModel;
import com.example.win.cdd.R;

//绘制ui
public class GameView extends SurfaceView {

    GameController gameController=GameController.getGameController();
    GameModel gameModel=GameModel.getGameModel();

    Bitmap redoImage;
    Bitmap passImage;
    Bitmap chupaiImage;
    Bitmap farmerImage;
    Bitmap landlordImage;
    Bitmap backgroundBitmap;
    Bitmap cardImage;

    private int[][] passPosition = {{30, 190}, {80, 80}, {360, 80}, {150,80}};
    private int[][] playerLatestCardsPosition = {{30, 140}, {30, 60}, {360, 60},{200, 60}};
    private int[][] scorePosition = {{70, 290}, {70, 30}, {370, 30}, {240,30}};
    private int[][] iconPosition = {{30, 270}, {30, 10}, {410, 10}, {200,10}};
    private int buttonPosition_X = 240;
    private int buttonPosition_Y = 160;


    Context context;

    Paint paint = new Paint();
    Rect src = new Rect();
    Rect des = new Rect();

    public GameView(Context context) {
        super(context);
        this.context = context;
       init();
    }
    private void init(){

        redoImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_redo);
        passImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_pass);
        chupaiImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.btn_chupai);
        landlordImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_landlord);

        backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_bg);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize((int) (16 * MainActivity.SCALE_HORIAONTAL));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        paintBackGround(canvas);
        paintCardsInGame(canvas);
        paintGaming(canvas);
    }

    private void paintBackGround(Canvas canvas) {

        //backgournd

        src.set(0, 0, backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
        des.set(0, 0, MainActivity.SCREEN_WIDTH, MainActivity.SCREEN_HEIGHT);
        canvas.drawBitmap(backgroundBitmap, src, des, null);

        //touxiang mingzi
        for (int i = 0; i < 4; i++) {
            src.set(0, 0, landlordImage.getWidth(), landlordImage.getHeight());
            des.set((int) (iconPosition[i][0] * MainActivity.SCALE_HORIAONTAL),
                    (int) (iconPosition[i][1] * MainActivity.SCALE_VERTICAL),
                    (int) ((iconPosition[i][0] + 40) * MainActivity.SCALE_HORIAONTAL),
                    (int) ((iconPosition[i][1] + 40) * MainActivity.SCALE_VERTICAL));
            RectF rectF = new RectF(des);
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.drawBitmap(landlordImage, src, des, paint);
            canvas.drawText("玩家" + i,
                    (int) (scorePosition[i][0] * MainActivity.SCALE_HORIAONTAL),
                    (int) (scorePosition[i][1] * MainActivity.SCALE_VERTICAL), paint);
        }
    }

    private void paintCardsInGame(Canvas canvas) {
        int row;
        int col;

        for (int i = 0; i < 4; i++) {
            if (i == 1 || i == 2 || i == 3) {// 当玩家id为1、2、3时，画剩余的牌数
                canvas.drawText("" + gameModel.getplayer(i).getHoldingCards().length, (int) (iconPosition[i][0] * MainActivity.SCALE_HORIAONTAL),
                        (int) ((playerLatestCardsPosition[i][1] + 80) * MainActivity.SCALE_VERTICAL), paint);
            } else {// 当玩家id为0时，横向绘制，扑克牌全是正面

                // 显示具体牌型
                for (int k = 0; k < gameModel.getplayer(i).getHoldingCards().length; k++) {
                    row = CardsManager.getImageRow(gameModel.getplayer(i).getHoldingCards()[k]);
                    System.out.println("输出测试："+gameModel.getplayer(i).getHoldingCards()[k]);
                    col = CardsManager.getImageCol(gameModel.getplayer(i).getHoldingCards()[k]);
                    cardImage = BitmapFactory.decodeResource(context.getResources(),
                            CardsManager.cardImages[row][col]);
                    int select = 0;//0未选中，10选中
                    if (gameModel.getplayer(i).getSelectedCards()[k]) {
                        select = 10;
                    }
                    //选中的比未选中的高10
                    src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
                    des.set((int) ((iconPosition[i][0] + k * 20) * MainActivity.SCALE_HORIAONTAL),
                            (int) ((iconPosition[i][1] -60- select) * MainActivity.SCALE_VERTICAL),
                            (int) ((iconPosition[i][0] + 40 + k * 20) * MainActivity.SCALE_HORIAONTAL),
                            (int) ((iconPosition[i][1] - select ) * MainActivity.SCALE_VERTICAL));
                    RectF rectF = new RectF(des);
                    canvas.drawRoundRect(rectF, 5, 5, paint);
                    canvas.drawBitmap(cardImage, src, des, paint);
                }
            }
        }
    }

    //绘制游戏画面
    private void paintGaming(Canvas canvas) {
        //如果轮到本人，画“不要”“出牌”“重新开始”按钮
        if(gameModel.getCurrentId()== 0) {
            src.set(0, 0, chupaiImage.getWidth(), chupaiImage.getHeight());
            des.set((int) (buttonPosition_X * MainActivity.SCALE_HORIAONTAL),
                    (int) (buttonPosition_Y * MainActivity.SCALE_VERTICAL),
                    (int) ((buttonPosition_X + 80) * MainActivity.SCALE_HORIAONTAL),
                    (int) ((buttonPosition_Y + 40) * MainActivity.SCALE_VERTICAL));
            canvas.drawBitmap(chupaiImage, src, des, null);

            if(gameModel.getCardsOnDesktop()!=null&&gameModel.getCardsOnDesktop().getPlayerId()!=0) {//桌子上有牌，而且不是自己的，就画“不出”
                src.set(0, 0, passImage.getWidth(), passImage.getHeight());
                des.set((int) ((buttonPosition_X - 80) * MainActivity.SCALE_HORIAONTAL),
                        (int) (buttonPosition_Y * MainActivity.SCALE_VERTICAL),
                        (int) ((buttonPosition_X) * MainActivity.SCALE_HORIAONTAL),
                        (int) ((buttonPosition_Y + 40) * MainActivity.SCALE_VERTICAL));
                canvas.drawBitmap(passImage, src, des, null);
            }

            src.set(0, 0, redoImage.getWidth(), redoImage.getHeight());
            des.set((int) ((buttonPosition_X + 80) * MainActivity.SCALE_HORIAONTAL),
                    (int) ((buttonPosition_Y) * MainActivity.SCALE_VERTICAL),
                    (int) ((buttonPosition_X + 160) * MainActivity.SCALE_HORIAONTAL),
                    (int) ((buttonPosition_Y + 40) * MainActivity.SCALE_VERTICAL));
            canvas.drawBitmap(redoImage, src, des, null);

        }

        //画各自刚出的牌或“不要”
        for(int i = 0; i < 4; i++) {
            if (gameModel.getplayer(i).getCardsInHands()!=null){
                PaintCard(canvas, playerLatestCardsPosition[i][0],
                        playerLatestCardsPosition[i][1],gameModel.getplayer(i).getCardsInHands());
            }
            if(gameModel.getCurrentId() == 0&&gameModel.getplayer(0).isCanDrawPass()&&gameModel.getplayer(0).getCardsInHands() == null)
                paintPass(canvas, 0);
            if (i!=0&&gameModel.getCurrentId() != i && gameModel.getplayer(i).getCardsInHands() == null&&gameModel.getplayer(i).isCanDrawPass() ) {
                paintPass(canvas, i);
            }
        }
    }

    private void PaintCard(Canvas canvas, int left, int top, int[] cards) {
        for (int i = 0; i < cards.length; i++) {
            //找到这张牌的图片
            int row = CardsManager.getImageRow(cards[i]);
            int col = CardsManager.getImageCol(cards[i]);
            cardImage = BitmapFactory.decodeResource(context.getResources(),
                    CardsManager.cardImages[row][col]);

            src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
            des.set((int) ((left + i * 20) * MainActivity.SCALE_HORIAONTAL),
                    (int) ((top) * MainActivity.SCALE_VERTICAL),
                    (int) ((left + 40 + i * 20) * MainActivity.SCALE_HORIAONTAL),
                    (int) ((top + 60) * MainActivity.SCALE_VERTICAL));

            RectF rectF = new RectF(des);
            canvas.drawRoundRect(rectF, 5, 5, paint);
            canvas.drawBitmap(cardImage, src, des, paint);

        }
    }
    // 画“不要”二字
    private void paintPass(Canvas canvas, int id) {
        canvas.drawText("不要", (int) (passPosition[id][0] * MainActivity.SCALE_HORIAONTAL),
                (int) (passPosition[id][1] * MainActivity.SCALE_VERTICAL), paint);
    }

}

