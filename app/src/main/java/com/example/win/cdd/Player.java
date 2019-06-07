package com.example.win.cdd;



import static com.example.win.cdd.Constants.CardTypeConstansts.CARD_ERROR;
import static com.example.win.cdd.Constants.ErrorConstants.EMPTY_CARD;
import static com.example.win.cdd.Constants.ErrorConstants.SMALL_CARD;
import static com.example.win.cdd.Constants.ErrorConstants.WRONG_CARD;

public class Player {
    private int id;
    private boolean canDrawPass=false;
    private int scorce=0;
    private int oneScorce=0;
    public boolean isCanDrawPass() {
        return canDrawPass;
    }

    public Player(int id) {
        this.id = id;
        selectedCards=new boolean[13];
        holdingCards=new int[13];
    }

    private int[] holdingCards;// 玩家手中的牌
    private  boolean[] selectedCards;// 玩家选中的牌

    public void setScorce(int s){
        oneScorce=s;
        scorce=scorce+s;
    }
    public void cleaerOneScorce(){
        oneScorce=0;
    }


    public void setHoldingCards(int[] holdingCards) {
        this.holdingCards = holdingCards;
    }

    public int[] getHoldingCards(){
        return holdingCards;
    }

    public boolean[] getSelectedCards() {
        return selectedCards;
    }
    public int getScorce(){return scorce;}

    public int getOneScorce() { return oneScorce; }

    public void changeSelectedCards(int i) {
        selectedCards[i]=!selectedCards[i];
    }

    private int[] cardsInHands=null;  // 玩家最新出的牌

    public int[] getCardsInHands(){
        return cardsInHands;
    }

    public void reset() {
        for (int i = 0; i < selectedCards.length; i++) {
            selectedCards[i] = false;
        }
    }
    public void restart(){
        canDrawPass=false;
        holdingCards=new int[13];
        cardsInHands=null;
        selectedCards=new boolean[13];
        for(int i=0;i<13;i++){
            selectedCards[i]=false;
            holdingCards[i]=0;
        }
    }


    // 电脑出牌算法
 public CardSet chupaiAI(CardSet card) {
        int[] pokerWanted = null;

        if (card == null) {
            // 出最小的单张
            pokerWanted =new int[] {holdingCards[holdingCards.length-1]} ;
        }
        else {
            // 玩家需要出一手比card大的牌
            pokerWanted = CardsManager.findBigerCards(card, holdingCards);
        }
        // 如果不能出牌，则返回
        if (pokerWanted == null) {
            return null;
        }
        // 出牌后将牌从玩家手中剔除
        for (int i = 0; i < pokerWanted.length; i++) {
            for (int j = 0; j < holdingCards.length; j++) {
                if (holdingCards[j] == pokerWanted[i]) {
                    holdingCards[j] = -1;
                    break;
                }
            }
        }
        int[] newpokes =new int[0];
        if (holdingCards.length - pokerWanted.length > 0) {
            newpokes = new int[holdingCards.length - pokerWanted.length];
        }
        else {
            newpokes = new int[pokerWanted.length - holdingCards.length];
        }

        int j = 0;
        for (int i = 0; i < holdingCards.length; i++) {
            if (holdingCards[i] != -1&&j<newpokes.length) {
                newpokes[j] = holdingCards[i];
                j++;
            }
        }

        this.holdingCards = newpokes;
        CardSet thiscard = new CardSet(pokerWanted, id);

        // 更新桌面最近的一手牌
        this.cardsInHands = pokerWanted;
        return thiscard;
    }

    // 用户出牌
    public CardSet chupaiID(CardSet card) {
        int count = 0;//出了几张牌
        //统计用户选了几张牌
        for(int i = 0; i < holdingCards.length; i++) {
            if(selectedCards[i]) {
                count++;
            }
        }

        //得到用户要出的牌
        int[] chupaiPokes = new int[count];
        int j = 0;
        for(int i = 0; i < holdingCards.length; i++) {
            if(selectedCards[i]){
                chupaiPokes[j] = holdingCards[i];
                j++;
            }
        }
        //分析出的什么牌：单张，顺子，杂顺等
        int cardType = CardsManager.getType(chupaiPokes);

        if(cardType == CARD_ERROR) {
            // 出牌错误
            if(chupaiPokes.length != 0) {
                MainActivity.handler.sendEmptyMessage(WRONG_CARD);
            }
            else {
                MainActivity.handler.sendEmptyMessage(EMPTY_CARD);
            }
            reset();
            return null;
        }

        //用用户选择的牌更新cardsInHands和cardsOnDesktop
        CardSet newLatestCardSet =new CardSet(chupaiPokes, id) ;

        //用户获得牌权
        if(card == null) {
            this.cardsInHands = chupaiPokes;
            int[] newPokes = new int[holdingCards.length - count];
            int k = 0;
            for(int i = 0; i < holdingCards.length; i++) {
                if(!selectedCards[i]) {
                    newPokes[k] = holdingCards[i];
                    k++;
                }
            }
            this.holdingCards = newPokes;
            this.selectedCards = new boolean[holdingCards.length];
        }
        else {
            //比大小，用户大
            if (CardsManager.compare(newLatestCardSet, card) == 1) {
                this.cardsInHands = chupaiPokes;

                int[] newPokes = new int[holdingCards.length - count];
                int ni = 0;
                for (int i = 0; i < holdingCards.length; i++) {
                    if (!selectedCards[i]) {
                        newPokes[ni] = holdingCards[i];
                        ni++;
                    }
                }
                this.holdingCards = newPokes;
                this.selectedCards = new boolean[holdingCards.length];
            }
            //用户小
            if (CardsManager.compare(newLatestCardSet, card) == 0) {
                MainActivity.handler.sendEmptyMessage(SMALL_CARD);
                reset();
                return null;
            }
            //不和规则
            if (CardsManager.compare(newLatestCardSet, card) == -1) {
                MainActivity.handler.sendEmptyMessage(WRONG_CARD);
                reset();
                return null;
            }
        }
        return newLatestCardSet;
    }

    public void buyao() {
        //清空当前不要牌的人的最后一手牌
        canDrawPass=true;
        cardsInHands = null;
    }
}
