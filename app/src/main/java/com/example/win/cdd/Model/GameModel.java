package com.example.win.cdd.Model;

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

import com.example.win.cdd.CardSet;
import com.example.win.cdd.CardsManager;
import com.example.win.cdd.MainActivity;
import com.example.win.cdd.Player;
import com.example.win.cdd.R;

import static com.example.win.cdd.Constants.GameStateConstants.EXIT;
import static com.example.win.cdd.Constants.GameStateConstants.STOP;

public class GameModel {

    private static GameModel gameModel = new GameModel();

    private GameModel() {
        for(int i=0;i<4;i++){
                players[i] = new Player(i);
        }
    }

    public static GameModel getGameModel() {
        return gameModel;
    }

    private   Player[] players = new Player[4];  //四个玩家 Player[]

    private int gameState=0;
    public int getGameState(){
        return gameState;
    }
    public void setGameState(int s){
        this.gameState=s;
    }


    private int currentId = 0;// 当前操作的人
    public int getCurrentId(){
        return currentId;
    }

    public Player getplayer(int id){
        return players[id];
    }


    private  CardSet cardsOnDesktop = null;// 桌面上最新的一组牌
    public CardSet getCardsOnDesktop(){
        return cardsOnDesktop;
    }

    public void setCardsOnDesktop(CardSet cardsOnDesktop) {
        this.cardsOnDesktop = cardsOnDesktop;
    }

    public void nextPerson() {
        switch (currentId) {
            case 0:
                currentId = 2;
                break;
            case 1:
                currentId = 0;
                break;
            case 2:
                currentId = 3;
                break;
            case 3:
                currentId = 1;
                break;
        }

    }

    public void computeScorce(){

        int[] muliply=new int[4];
        int[] paiScorce=new int[4];

        for(int i=0;i<4;i++){//有黑桃二，翻倍
            for(int k=0;k<players[i].getHoldingCards().length;k++){
                if(players[i].getHoldingCards()[k]==51)
                    muliply[i]=2;
                else
                    muliply[i]=1;
            }
            if(players[i].getHoldingCards().length <8){
                paiScorce[i]=players[i].getHoldingCards().length;
            }
            else if(players[i].getHoldingCards().length <10){
                paiScorce[i]=players[i].getHoldingCards().length*2*muliply[i];
            }
            else if(players[i].getHoldingCards().length<13 ){
                paiScorce[i]=players[i].getHoldingCards().length*3*muliply[i];
            }
            else {
                paiScorce[i]=players[i].getHoldingCards().length*4*muliply[i];
            }
        }

        for(int i=0;i<4;i++){
            int s=0;
            for(int k=0;k<4;k++){
                if(k!=i){
                    s=s+paiScorce[k]-paiScorce[i];
                }
            }
            players[i].cleaerOneScorce();
            players[i].setScorce(s);
        }
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public void checkGameOver() {
        for(int i = 0; i < 4; i++) {
            //当三个人中其中一个人牌的数量为0，则游戏结束
            if(players[i].getHoldingCards().length == 0) {
                computeScorce();
                gameModel.setGameState(2);
            }
        }
    }
}
