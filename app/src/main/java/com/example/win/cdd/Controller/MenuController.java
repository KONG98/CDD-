package com.example.win.cdd.Controller;

import com.example.win.cdd.CardsManager;
import com.example.win.cdd.MainActivity;
import com.example.win.cdd.Model.GameModel;

import static com.example.win.cdd.Constants.GameStateConstants.EXIT;
import static com.example.win.cdd.Constants.GameStateConstants.GAME;

public class MenuController {
    private int x = 270;
    private int y = 180;
    private static MenuController menuController = new MenuController();
    private GameModel gameModel=GameModel.getGameModel();
    private CardsManager cardsManager=CardsManager.getCardsManager();

    int selectIndex = -1;

    private MenuController() {}

    public static MenuController getMenuController() {
        return menuController;
    }

    public static boolean ifInRect(int x, int y, int rectX, int rectY, int rectW, int rectH) {
        if (x <= rectX || x >= rectX + rectW || y <= rectY || y >= rectY + rectH) {
            return false;
        }
        return true;
    }
    //对menuview中的触摸事件进行响应
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
            case 0:
                cardsManager.faPai();
                gameModel.setGameState(1);
                MainActivity.handler.sendEmptyMessage(GAME);

                break;
            case 1:
                MainActivity.handler.sendEmptyMessage(EXIT);
                break;
            default:
                break;
        }
    }
}
