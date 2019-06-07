package com.example.win.cdd.View;


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
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.example.win.cdd.CardsManager;
import com.example.win.cdd.Controller.ResultController;
import com.example.win.cdd.MainActivity;
import com.example.win.cdd.Model.GameModel;
import com.example.win.cdd.R;

public class ResultView extends SurfaceView {
   GameModel gameModel=GameModel.getGameModel();
   Bitmap backgroundBitmap;
   Bitmap cardImage;
   Context context;

   SurfaceHolder holder;
   Paint paint = new Paint();
   Rect src = new Rect();
   Rect des = new Rect();

   private int[][] playerCardsPosition = {{30, 210}, {30, 60}, {410, 60}, {80,20}};
   private int x = 270;
   private int y = 180;
   private Bitmap[] menuItems;

   public ResultView(Context context) {
      super(context);
      this.context = context;
      init();
   }
   private void init() {
      menuItems = new Bitmap[2];

      holder = getHolder();
      backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_bg);
      menuItems[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu1);
      menuItems[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu5);
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      paint.setColor(Color.WHITE);
      paint.setStrokeWidth(1);
      paint.setStyle(Paint.Style.FILL);
      paint.setTextSize((int) (16 * MainActivity.SCALE_HORIAONTAL));
   }

    protected void onDraw(Canvas canvas) {
       //画背景
       paintBackGround(canvas);
       paintResult(canvas);
       paintCardsAfterGame(canvas);
    }
   private void paintBackGround(Canvas canvas) {
      //backgournd
      src.set(0, 0, backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
      des.set(0, 0, MainActivity.SCREEN_WIDTH, MainActivity.SCREEN_HEIGHT);
      canvas.drawBitmap(backgroundBitmap, src, des, null);

      //button
      for (int i = 0; i < menuItems.length; i++) {
         canvas.drawBitmap(menuItems[i], (int) (x * MainActivity.SCALE_HORIAONTAL + 200),
                 (int) ((y + i * 43) * MainActivity.SCALE_VERTICAL + 150), paint);
      }
   }
   // 画游戏结束时的分数和各自剩余牌
    private void paintResult(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) (20 * MainActivity.SCALE_HORIAONTAL));
        for (int i = 0; i < 4; i++) {
            canvas.drawText("玩家" + i + ":本局得分:" + gameModel.getplayer(i).getOneScorce() + "   总分：" + gameModel.getplayer(i).getScorce(),
                    (int) (110 * MainActivity.SCALE_HORIAONTAL),
                    (int) ((96 + i * 30) * MainActivity.SCALE_VERTICAL), paint);
        }
    }

   private void paintCardsAfterGame(Canvas canvas) {
      Rect src = new Rect();
      Rect des = new Rect();

      int row;
      int col;

      for(int i=0;i<4;i++) {
         // 当玩家是1、2时，竖向绘制
         if (i == 1 || i == 2) {
            //System.out.print("shengpai:"+gameModel.getplayer(i).getHoldingCards().length);
            for (int k = 0; k < gameModel.getplayer(i).getHoldingCards().length; k++) {

               row = CardsManager.getImageRow(gameModel.getplayer(i).getHoldingCards()[k]);
               col = CardsManager.getImageCol(gameModel.getplayer(i).getHoldingCards()[k]);
               cardImage = BitmapFactory.decodeResource(context.getResources(), CardsManager.cardImages[row][col]);

               src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
               des.set((int) (playerCardsPosition[i][0] * MainActivity.SCALE_HORIAONTAL),
                       (int) ((playerCardsPosition[i][1] - 40 + k * 15) * MainActivity.SCALE_VERTICAL),
                       (int) ((playerCardsPosition[i][0] + 40) * MainActivity.SCALE_HORIAONTAL),
                       (int) ((playerCardsPosition[i][1] + 20 + k * 15) * MainActivity.SCALE_VERTICAL));
               RectF rectF = new RectF(des);
               canvas.drawRoundRect(rectF, 5, 5, paint);
               canvas.drawBitmap(cardImage, src, des, paint);
            }
         }
         // 当玩家是0、4时，横向绘制
         else {
            for (int k = 0; k < gameModel.getplayer(i).getHoldingCards().length; k++) {
               row = CardsManager.getImageRow(gameModel.getplayer(i).getHoldingCards()[k]);
               col = CardsManager.getImageCol(gameModel.getplayer(i).getHoldingCards()[k]);
               cardImage = BitmapFactory.decodeResource(context.getResources(), CardsManager.cardImages[row][col]);
               src.set(0, 0, cardImage.getWidth(), cardImage.getHeight());
               des.set((int) ((playerCardsPosition[i][0] + 40 + k * 20) * MainActivity.SCALE_HORIAONTAL),
                       (int) (playerCardsPosition[i][1] * MainActivity.SCALE_VERTICAL),
                       (int) ((playerCardsPosition[i][0] + 80 + k * 20) * MainActivity.SCALE_HORIAONTAL),
                       (int) ((playerCardsPosition[i][1] + 60) * MainActivity.SCALE_VERTICAL));
               RectF rectF = new RectF(des);
               canvas.drawRoundRect(rectF, 5, 5, paint);
               canvas.drawBitmap(cardImage, src, des, paint);
            }
         }
      }
   }
}
