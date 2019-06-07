package com.example.win.cdd;

import com.example.win.cdd.Model.GameModel;

import java.util.Random;

import static com.example.win.cdd.Constants.CardTypeConstansts.CARD_ERROR;
import static com.example.win.cdd.Constants.CardTypeConstansts.DAN_ZHANG;
import static com.example.win.cdd.Constants.CardTypeConstansts.SAN_DAI_ER;
import static com.example.win.cdd.Constants.CardTypeConstansts.SAN_GE;
import static com.example.win.cdd.Constants.CardTypeConstansts.SI_DAI_YI;
import static com.example.win.cdd.Constants.CardTypeConstansts.TONG_HUA_SHAN;
import static com.example.win.cdd.Constants.CardTypeConstansts.TONG_HUA_WU;
import static com.example.win.cdd.Constants.CardTypeConstansts.YI_DUI;
import static com.example.win.cdd.Constants.CardTypeConstansts.ZA_SHUN;

public class CardsManager {

    private GameModel gameModel=GameModel.getGameModel();
    private static int[] allCards = new int[52];//一副牌
    private int[][] gameCards=new int[4][13];
    public static final int[][] cardImages = {
            {R.drawable.spade_3, R.drawable.heart_3, R.drawable.club_3, R.drawable.diamond_3},
            {R.drawable.spade_4, R.drawable.heart_4, R.drawable.club_4, R.drawable.diamond_4},
            {R.drawable.spade_5, R.drawable.heart_5, R.drawable.club_5, R.drawable.diamond_5},
            {R.drawable.spade_6, R.drawable.heart_6, R.drawable.club_6, R.drawable.diamond_6},
            {R.drawable.spade_7, R.drawable.heart_7, R.drawable.club_7, R.drawable.diamond_7},
            {R.drawable.spade_8, R.drawable.heart_8, R.drawable.club_8, R.drawable.diamond_8},
            {R.drawable.spade_9, R.drawable.heart_9, R.drawable.club_9, R.drawable.diamond_9},
            {R.drawable.spade_10, R.drawable.heart_10, R.drawable.club_10, R.drawable.diamond_10},
            {R.drawable.spade_11, R.drawable.heart_11, R.drawable.club_11, R.drawable.diamond_11},
            {R.drawable.spade_12, R.drawable.heart_12, R.drawable.club_12, R.drawable.diamond_12},
            {R.drawable.spade_13, R.drawable.heart_13, R.drawable.club_13, R.drawable.diamond_13},
            {R.drawable.spade_14, R.drawable.heart_14, R.drawable.club_14, R.drawable.diamond_14},
            {R.drawable.spade_15, R.drawable.heart_15, R.drawable.club_15, R.drawable.diamond_15},
    };

    public static Random rand = new Random();

    private static CardsManager cardsManager=new CardsManager();
    private  CardsManager(){
        int count=0;
        for(int i=0;i<52;i++)
            allCards[i]=i;

    };
    public static CardsManager getCardsManager() {
        return cardsManager;
    }

    public void faPai(){
        int count=0;
        for (int i = 0; i <4 ; i++) {
            for(int k=0;k<13;k++) {
                gameCards[i][k] = count++;
            }
        }

        // 对于52张牌中的任何一张，都随机找一张和它互换，将牌顺序打乱。
        for (int i = 0; i <4 ; i++) {
            for(int k=0;k<13;k++) {
                int x = rand.nextInt(4);
                int y = rand.nextInt(13);
                int temp = gameCards[i][k];
                gameCards[i][k] = gameCards[x][y];
                gameCards[x][y] = temp;
                System.out.println("发牌输出测试：第一次  "+i+"  "+gameCards[i][k]);
            }
        }
            for(int i = 0; i < 4; i++) {
                gameModel.getplayer(i).restart();
                gameModel.getplayer(i).setHoldingCards(sort(gameCards[i]));
                for(int k=0;k<13;k++){
                    System.out.println("发牌输出测试："+gameCards[i][k]);
                }
            }
    }

    //对牌进行从大到小排序，冒泡排序
    public static int[] sort(int[] cards) {
        for(int i = 0; i < cards.length; i++) {
            for(int j = i + 1; j < cards.length; j++) {
                if(cards[i] < cards[j]) {
                    int temp = cards[i];
                    cards[i] = cards[j];
                    cards[j] = temp;
                }
            }
        }
        return cards;
    }

    //返回牌的大小-3
    public static int getImageRow(int poke) {
        return poke / 4;
    }

    //返回的int分别对应的花色为黑桃、红心、梅花、方片
    public static int getImageCol(int poke) {
        if( poke % 4==0)return 3;
        if(poke%4==3)return 0;
        if(poke%4==2)return 1;
        if(poke%4==1)return 2;
        return -3;
    }

    //获取某张牌的大小
    public static int getCardNumber(int card) {
        return getImageRow(card) + 3;
    }


    //判断是否为顺
    public static boolean isShun(int[] cards) {

        //12345
        if(getCardNumber(cards[0]) == 15
                && getCardNumber(cards[1]) == 14
                && getCardNumber(cards[2]) == 5
                && getCardNumber(cards[3]) == 4
                && getCardNumber(cards[4]) == 3)
            return true;
        //A不可以在中间
        if(getCardNumber(cards[1]) == 14||
                getCardNumber(cards[2]) == 14||
                getCardNumber(cards[3]) == 14)
            return false;
        //23456
        if(getCardNumber(cards[0]) == 15
                && getCardNumber(cards[1]) == 6
                && getCardNumber(cards[2]) == 5
                && getCardNumber(cards[3]) == 4
                && getCardNumber(cards[4]) == 3)
            return true;

        int start = getCardNumber(cards[0]);
        int next;

        for(int i = 1; i < cards.length; i++) {
            next = getCardNumber(cards[i]);
            if ((start - next) != 1) {
                return false;
            }
            start = next;
        }
        return true;
    }

    //判断是否为杂顺
    public static boolean isZaShun(int[] cards) {
        if (!isShun(cards))
            return false;
        if (isTongHuaShun(cards))
            return false;
        return true;
    }

    //判断是否为同花顺
    public static boolean isTongHuaShun(int[] cards) {
        if(!isShun(cards))
            return false;
        for(int i = 0; i < cards.length - 1; i++) {
            if(getImageCol(cards[i]) != getImageCol(cards[i + 1]))
                return false;
        }
        return true;
    }

    //判断是否为同花五
    public static boolean isTongHuaWu(int[] cards) {
        if(isTongHuaShun(cards))
            return false;
        for(int i = 0; i < cards.length - 1; i++) {
            if(getImageCol(cards[i]) != getImageCol(cards[i + 1]))
                return false;
        }
        return true;
    }

    //判断是否为四带一
    public static boolean isSiDaiYi(int[] cards) {
        if(getImageRow(cards[0])!=getImageRow(cards[1]))//单张大，93333
        {
            for (int i=1;i<cards.length-1;i++) {
                if (getImageRow(cards[i]) != getImageRow(cards[i + 1]))
                    return false;
            }
        }
        else {//单张小，99993
            for(int i = 0; i < cards.length - 2; i++) {
                if (getImageRow(cards[i]) != getImageRow(cards[i + 1]))
                    return false;
            }
        }
        return true;
    }

    //判断是否为三带一
    public static boolean isSanDaiEr(int[] cards) {
        if(getImageRow(cards[1])!=getImageRow(cards[2]))//两张的大，99333
        {
            if(getImageRow(cards[0])!=getImageRow(cards[1])||getImageRow(cards[2])!=getImageRow(cards[3])||getImageRow(cards[3])!=getImageRow(cards[4]))
                return false;
        }
        else {//两张的小，99933
            if(getImageRow(cards[0])!=getImageRow(cards[1])||getImageRow(cards[1])!=getImageRow(cards[2])||getImageRow(cards[3])!=getImageRow(cards[4]))
                return false;
        }
        return true;
    }

    //判断基本牌型，判断时已从大到小排序
    public static int getType(int[] cards) {
        int length = cards.length;
        switch(length){
            case 1://当牌数量为1时，单牌
                return DAN_ZHANG;
            case 2://当牌数量为2是，一对
                if(getCardNumber(cards[0]) == getCardNumber(cards[1])) {
                    return YI_DUI;
                }
                break;
            case 3: // 当牌数为3时,三个
                if (getCardNumber(cards[0]) == getCardNumber(cards[1])&&getCardNumber(cards[1]) == getCardNumber(cards[2])) {
                    return SAN_GE;
                }
                break;
            case 5: //当牌数为5时，可能为同花顺、四带一张、三带一对、同花五、杂顺
                if (isTongHuaShun(cards))
                    return TONG_HUA_SHAN;
                else if(isSiDaiYi(cards))
                    return SI_DAI_YI;
                else if(isSanDaiEr(cards))
                    return SAN_DAI_ER;
                else if(isTongHuaWu(cards))
                    return TONG_HUA_SHAN;
                else if (isZaShun(cards))
                    return ZA_SHUN;
                break;
            default:
                return CARD_ERROR;
        }
        return CARD_ERROR;
    }

    //找到一组牌最大的那个，作为这组牌的值
    // 返回的Int值包含花色和数值，后面若要得到大小和花色，要用getCardNumber()（大小）和getImageCol()（花色）处理
    public static int getValue(int[] cards) {
        int type;
        type = getType(cards);
        switch (type){
            case DAN_ZHANG:
                return cards[0];
            case YI_DUI:
                return cards[0];
            case SAN_GE:
                return cards[0];
            case ZA_SHUN:
                if(getCardNumber(cards[0]) == 15){
                    if(getCardNumber(cards[1]) == 14){//21543
                        return cards[2];
                    }
                    else{//26543
                        return cards[1];
                    }
                }else {
                    return cards[0];
                }
                case TONG_HUA_SHAN:
                    if(getCardNumber(cards[0]) == 15){
                        if(getCardNumber(cards[1]) == 14){//21543
                            return cards[2];
                        }
                        else{//26543
                            return cards[1];
                        }
                    } else {
                            return cards[0];
                        }
                        case SAN_DAI_ER:
                            if(getCardNumber(cards[0]) == getCardNumber(cards[2])) {//对小，99933
                                return cards[0];
                            }else {//对大，99333
                                return cards[2];
                            }
                            case SI_DAI_YI:
                                if(getCardNumber(cards[0]) == getCardNumber(cards[3])) {//单小，99993
                                    return cards[0];
                                    }
                                    else{//单大，98888
                                    return cards[1];
                                }
                                case TONG_HUA_WU:
                                    return cards[0];
        }
        return 0;
    }


     //比较两组牌大小，1为前者大，0为后者大，无法比较为-1
    public static int compare(CardSet f, CardSet s) {
        if(f.cards.length != s.cards.length)//长度不一样，不符合规律
            return -1;
        if(getCardNumber(f.value) > getCardNumber(s.value)) {
            return 1;
        }
        if(getCardNumber(f.value) == getCardNumber(s.value)) {
            if(getImageCol(f.value) < getImageCol(s.value)){
                return 1;
            }
            if(getImageCol(f.value) > getImageCol(s.value)){
                return 0;
            }
        }
        if(getCardNumber(f.value) < getCardNumber(s.value)) {
            return 0;
        }
        return -1;
    }

    //找到手中比某组牌card大的一组牌，用于电脑出牌算法
    public static int[] findBigerCards(CardSet card, int zijicards[]) {
        int cardType = card.cardsType;
        int cardValue = card.value;//最大的那个牌
        switch (cardType) {
            case DAN_ZHANG:
                for (int i = zijicards.length-1; i >=0; i--) {
                    if (zijicards[i] > cardValue)
                        return new int[]{zijicards[i]};
                }
                return null;
            case YI_DUI:
                for (int i = zijicards.length-2; i >=0; i--) {
                    if (zijicards[i] >= cardValue && zijicards[i +1]/4 == zijicards[i]/4)
                        return new int[]{zijicards[i], zijicards[i + 1]};
                }
                return null;
            case SAN_GE:
                for (int i = zijicards.length-3; i >=0; i--) {
                    if (zijicards[i] > cardValue && zijicards[i +1]/4 == zijicards[i]/4&&zijicards[i + 1]/4 == zijicards[i+2]/4)
                        return new int[]{zijicards[i], zijicards[i + 1],zijicards[i+2]};
                }
                return null;
            case ZA_SHUN:
                for (int i = zijicards.length-5; i >=0; i--) {
                    if (zijicards[i] > cardValue) {
                        int[] a = new int[5];
                        int k=i;
                        int j = i + 1;
                        int count = -1;
                        a[++count] = zijicards[i];
                        while (count < 4) {
                            while (zijicards[k] / 4 == zijicards[j] / 4) {
                                if (j <zijicards.length-1)
                                    j++;
                                else
                                    break;
                            }
                            a[++count] = zijicards[j];
                            if (j <zijicards.length-1) {
                                k=j;
                                j=k+1;
                            }
                            else break;
                        }
                        if(a.length==5)
                        {
                            if (isZaShun(a))
                                return a;
                        }
                    }
                }
                return null;
            case TONG_HUA_SHAN:
                for (int i = zijicards.length-5; i >=0; i--) {
                    if (zijicards[i] > cardValue) {
                        int[] a = new int[5];
                        int count = -1;
                        a[++count]=zijicards[i];
                        for(int k=i+1;k<zijicards.length;k++)
                        {
                            if(getImageCol(zijicards[i])  == getImageCol(zijicards[k]) )
                                a[++count] = zijicards[k];
                            if(count==4)
                                break;
                        }
                        if(count==4)
                            return a;
                    }
                }
                return null;
            case SAN_DAI_ER:
                for (int i = zijicards.length-3; i >=0; i--) {
                    if (zijicards[i] > cardValue) {
                        int[] a = new int[5];
                        if (getImageRow(zijicards[i]) == getImageRow(zijicards[i + 1]) && getImageRow(zijicards[i + 2] )==getImageRow( zijicards[i + 1]))
                        {
                            for(int k=zijicards.length-1;k>0;k--){
                                if(getImageRow(zijicards[k])==getImageRow(zijicards[k-1])){
                                    if(getImageRow(zijicards[k])<getImageRow(zijicards[i])) {
                                        a[0] = zijicards[i];
                                        a[1] = zijicards[i+1];
                                        a[2]=zijicards[i+2];
                                        a[3]=zijicards[k-1];
                                        a[4]=zijicards[k];
                                        return a;
                                    }
                                    else if(getImageRow(zijicards[k])>getImageRow(zijicards[i])) {
                                        a[0]=zijicards[k-1];
                                        a[1]=zijicards[k];
                                        a[2] = zijicards[i];
                                        a[3] = zijicards[i+1];
                                        a[4]=zijicards[i+2];
                                        return a;
                                    }
                                }
                            }
                        }
                    }
                }
                return null;
            case SI_DAI_YI:
                for (int i = zijicards.length-4; i >=0; i--) {
                    if (zijicards[i] > cardValue) {
                        int[] a = new int[5];
                        if (getImageRow(zijicards[i]) == getImageRow(zijicards[i+1]) && getImageRow(zijicards[i+1])== getImageRow(zijicards[i+2])&&getImageRow(zijicards[i+2])== getImageRow(zijicards[i+3]))
                        {
                            if(i == zijicards.length-4){
                                a[0] = zijicards[i-1];
                                a[1] = zijicards[i];
                                a[2]=zijicards[i+1];
                                a[3]=zijicards[i+2];
                                a[4]=zijicards[i+3];
                                return a;
                            }
                            else {
                                a[0] = zijicards[i];
                                a[1]=zijicards[i+1];
                                a[2]=zijicards[i+2];
                                a[3]=zijicards[i+3];
                                a[4] = zijicards[zijicards.length-1];
                                return a;
                            }
                        }
                    }
                }
                return null;
            case TONG_HUA_WU:
                for (int i = zijicards.length-5; i >=0; i--) {
                    if (zijicards[i] > cardValue) {
                        int[] a = new int[5];
                        int k=i;
                        int j = i + 1;
                        int count = -1;
                        a[++count] = zijicards[i];
                        while (count < 4) {
                            while (zijicards[k] / 4 == zijicards[j] / 4) {
                                if (j <zijicards.length-1)
                                    j++;
                                else
                                    break;
                            }
                            a[++count] = zijicards[j];
                            if (j <zijicards.length-1) {
                                k=j;
                                j=k+1;
                            }
                            else break;
                        }
                        if(a.length==5)
                        {
                            if (isTongHuaShun(a))
                                return a;
                        }
                    }
                }
                return null;
            default:
                return null;
        }
    }
}
