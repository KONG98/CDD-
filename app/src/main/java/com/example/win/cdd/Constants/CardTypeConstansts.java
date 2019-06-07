package com.example.win.cdd.Constants;

public class CardTypeConstansts {
      /*
    牌型说明：
    1、单张：任何一张单牌
    2、一对：二张牌点相同的牌
    3、三个：三张牌点相同的牌
    4、顺：连续五张牌点相邻的牌，如“34567”“910JQK”“10JQKA”“A2345”等，顺的张数必须是五张，A既可在顺的最后，
    也可在顺的最前，但不能在顺的中间，如“JQKA2”不是顺
    5、杂顺：花色不全部相同的牌称为杂顺
    6、同花顺：每张牌的花色都相同的顺称为同花顺
    7、同花五：由相同花色的五张牌组成，但不是顺，称“同花五”，如红桃“278JK”
    8、三个带一对：例如：99955
    9、四个带单张：例如：99995
     */

    public final static int DAN_ZHANG = 101;
    public final static int YI_DUI = 102;
    public final static int SAN_GE = 103;
    public final static int SHUN = 104;
    public final static int ZA_SHUN = 105;
    public final static int TONG_HUA_SHAN = 106;
    public final static int TONG_HUA_WU = 107;
    public final static int SAN_DAI_ER = 108;
    public final static int SI_DAI_YI = 109;
    public final static int CARD_ERROR = 110;

    int direction_Horizontal = 0;
    int direction_Vertical = 1;
}
