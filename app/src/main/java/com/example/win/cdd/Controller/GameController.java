package com.example.win.cdd.Controller;

import com.example.win.cdd.CardSet;
import com.example.win.cdd.MainActivity;
import com.example.win.cdd.Model.GameModel;

//监听ui
public class GameController {

    private static GameController gameController = new GameController();

    private GameController() {
    }

    GameModel gameModel = GameModel.getGameModel();
    private int buttonPosition_X = 240;
    private int buttonPosition_Y = 160;

    public static GameController getGameController() {
        return gameController;
    }

    public static boolean ifInRect(int x, int y, int rectX, int rectY, int rectW, int rectH) {
        if (x <= rectX || x >= rectX + rectW || y <= rectY || y >= rectY + rectH) {
            return false;
        }
        return true;
    }

    public void returnMessage(int x, int y) {
        //重置
        if (ifInRect(x, y,
                (int) ((buttonPosition_X + 80) * MainActivity.SCALE_HORIAONTAL),
                (int) (buttonPosition_Y * MainActivity.SCALE_VERTICAL),
                (int) (80 * MainActivity.SCALE_HORIAONTAL),
                (int) (40 * MainActivity.SCALE_VERTICAL))) {
            gameModel.getplayer(0).reset();
        }

        //点击牌
        for (int i = 0; i < gameModel.getplayer(0).getHoldingCards().length; i++) {
            // 判断是那张牌被选中，设置标志
            if (i != gameModel.getplayer(0).getHoldingCards().length - 1) {//不是最右边的牌的时候
                if (ifInRect(x, y,
                        (int) ((30 + i * 20) * MainActivity.SCALE_HORIAONTAL),
                        (int) ((210 - (gameModel.getplayer(0).getSelectedCards()[i] ? 10 : 0)) * MainActivity.SCALE_VERTICAL),
                        (int) (20 * MainActivity.SCALE_HORIAONTAL),
                        (int) (60 * MainActivity.SCALE_VERTICAL))) {
                    gameModel.getplayer(0).changeSelectedCards(i);
                    break;
                }
            } else {//最右边的牌
                if (ifInRect(x, y,
                        (int) ((30 + i * 20) * MainActivity.SCALE_HORIAONTAL),
                        (int) ((210 - (gameModel.getplayer(0).getSelectedCards()[i] ? 10 : 0)) * MainActivity.SCALE_VERTICAL),
                        (int) (40 * MainActivity.SCALE_HORIAONTAL),
                        (int) (60 * MainActivity.SCALE_VERTICAL))) {
                    gameModel.getplayer(0).changeSelectedCards(i);
                    break;
                }
            }
        }

        if (gameModel.getCurrentId() == 0) {
            //点击出牌
            CardSet tempcard ;
            if (ifInRect(x, y, (int) (buttonPosition_X * MainActivity.SCALE_HORIAONTAL),
                    (int) (buttonPosition_Y * MainActivity.SCALE_VERTICAL),
                    (int) (80 * MainActivity.SCALE_HORIAONTAL),
                    (int) (40 * MainActivity.SCALE_VERTICAL))) {

                tempcard = gameModel.getplayer(0).chupaiID(gameModel.getCardsOnDesktop());

                if (tempcard != null) {
                    //手中有大过的牌，则出
                    gameModel.setCardsOnDesktop(tempcard);
                    gameModel.nextPerson();
                } else {
                    gameModel.getplayer(0).reset();
                }
                //其他三个玩家出牌
                if (gameModel.getCurrentId() != 0) {
                    for (int i = 1; i < 4; i++) {
                        tempcard = gameModel.getplayer(gameModel.getCurrentId()).chupaiAI(gameModel.getCardsOnDesktop());
                        if (tempcard != null) {
                            //手中有大过的牌，则出
                            gameModel.setCardsOnDesktop(tempcard);
                        } else {
                            gameModel.getplayer(gameModel.getCurrentId()).buyao();
                            //如果已经转回来，则该人继续出牌，本轮清空，新一轮开始

                        }
                        gameModel.nextPerson();
                        if (gameModel.getCardsOnDesktop() != null && gameModel.getCurrentId() == gameModel.getCardsOnDesktop().getPlayerId()) {
                            gameModel.setCardsOnDesktop(null);
                        }
                    }  //checkGameOver();
                }
            }

            //选择不要的触发事件
            if (ifInRect(x, y,
                    (int) ((buttonPosition_X - 80) * MainActivity.SCALE_HORIAONTAL),
                    (int) (buttonPosition_Y * MainActivity.SCALE_VERTICAL),
                    (int) (80 * MainActivity.SCALE_HORIAONTAL),
                    (int) (40 * MainActivity.SCALE_VERTICAL))) {
                gameModel.getplayer(0).buyao();
                gameModel.getplayer(0).reset();
                gameModel.nextPerson();
                //其他三个玩家出牌
                for (int i = 1; i < 4; i++) {
                    if (gameModel.getCardsOnDesktop() != null && gameModel.getCurrentId() == gameModel.getCardsOnDesktop().getPlayerId()) {
                        gameModel.setCardsOnDesktop(null);
                    }
                    tempcard = gameModel.getplayer(gameModel.getCurrentId()).chupaiAI(gameModel.getCardsOnDesktop());
                    if (tempcard != null) {
                        //手中有大过的牌，则出
                        gameModel.setCardsOnDesktop(tempcard);
                    } else {
                        gameModel.getplayer(gameModel.getCurrentId()).buyao();
                    }
                    gameModel.nextPerson();
                }
            }
        }
    }
}



