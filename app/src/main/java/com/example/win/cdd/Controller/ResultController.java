package com.example.win.cdd.Controller;

import com.example.win.cdd.CardsManager;
import com.example.win.cdd.MainActivity;
import com.example.win.cdd.Model.GameModel;

public class ResultController {
    private static ResultController resultController = new ResultController();
    private CardsManager cardsManager=CardsManager.getCardsManager();
    private int x = 270;
    private int y = 180;
    int selectIndex = -1;
    private GameModel gameModel=GameModel.getGameModel();

    private ResultController() {}

    public static ResultController getResultController() {
        return resultController;
    }

    public static boolean ifInRect(int x, int y, int rectX, int rectY, int rectW, int rectH) {
        if (x <= rectX || x >= rectX + rectW || y <= rectY || y >= rectY + rectH) {
            return false;
        }
        return true;
    }
    public void returnMessage(int ex, int ey) {
        selectIndex=-1;
        for (int i = 0; i < 2; i++) {
            if (ifInRect(ex, ey, (int) (x * MainActivity.SCALE_HORIAONTAL + 200),
                    (int) ((y + i * 43) * MainActivity.SCALE_VERTICAL + 150),
                    (int) (125 * MainActivity.SCALE_HORIAONTAL),
                    (int) (33 * MainActivity.SCALE_VERTICAL))) {
                selectIndex = i;
                break;
            }
        }
        switch (selectIndex) {
            case 0://继续游戏，回到游戏界面
                for(int i = 0; i < 4; i++) {
                    gameModel.getplayer(i).restart();
                }
                cardsManager.faPai();
                gameModel.setCurrentId(0);
                gameModel.setGameState(1);
                gameModel.setCardsOnDesktop(null);

                break;
            case 1://退出游戏,回到菜单界面
                for(int i = 0; i < 4; i++) {
                    gameModel.getplayer(i).restart();
                }
                gameModel.setCurrentId(0);
                gameModel.setGameState(0);
                gameModel.setCardsOnDesktop(null);

                break;
            default:
                break;
        }
    }
}
