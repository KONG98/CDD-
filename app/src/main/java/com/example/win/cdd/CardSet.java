package com.example.win.cdd;

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

public class CardSet {
    int value ;//这组牌的值，具体定义可见CardsManager中getValue
    int cardsType ;//这组牌的类型
    int[] cards;//这组牌
    int playerId;//拥有者


    public int getPlayerId(){
        return playerId;
    }

    public CardSet(int[] cards, int id) {
        this.playerId = id;
        this.cards = cards;

        cardsType = CardsManager.getType(cards);
        value = CardsManager.getValue(cards);
    }
}
