/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Classification
 * @Description TODO
 * @date 2019-09-30 18:33
 **/

import java.util.ArrayList;
import java.util.Collections;

public class Combination {
    public ArrayList<Poker.Rank> sameRankGroup;   //这个array list用以记录每个Classification对象，也即玩家，手中的重复牌组
    public ArrayList<Poker.Rank> combCardRank; //这个array list用以记录每个玩家的五张手牌的Rank
    public ArrayList<Poker.Suit> combCardSuit; //
    //创建 categories of hand 的enum，包含了优先级
    public enum Classification
    {
        HighC,               //High card
        OneP,               //One pair
        TwoP,               //Two pair
        ThreeK,             //Three of a kind
        Straight,           //Straight
        Flush,              //Flush
        FullH,              //Full house
        FourK,              //Four of a kind
        StraightF,          //Straight flush
    }

    //TODO 初始化Classfication实例的时候记得ArrayList<Poker.Rank> cards需要排好序的
    Combination(ArrayList<Poker.Rank> combCardRank, ArrayList<Poker.Suit> combCardSuit,
                ArrayList<Poker.Rank> sPG  )
    {
        this.combCardRank = combCardRank;
        this.combCardSuit = combCardSuit;
        this.sameRankGroup = sPG;
    }

//    //Getter
//    public ArrayList<Poker.Rank> getSamePokerGroup() {
//        return samePokerGroup;
//    }
//
//    public ArrayList<Poker.Rank> getCards() {
//        return everyFiveCardRank;
//    }
//
//    public ArrayList<Poker.Suit> getEveryFiveCardSuit() {
//        return everyFiveCardSuit;
//    }

    //这个分类class里面的ArrayList<Poker.Rank> cards都是五个一组

    //输入排好序的Rank类型数组，比较相邻两元素，如果它们的ordinal均相差1，那么是顺子
    private static boolean isStraight(ArrayList<Poker.Rank> cards)
    {
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i-1).ordinal() != cards.get(i).ordinal() - 1 ){
                return false;
            }
        }
        return true;
    }

    //输入Suit类型数组，比较相邻两元素，如果它们的ordinal均相等，那么是同花
    private static boolean isFlush(ArrayList<Poker.Suit> cards)
    {
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i-1).ordinal() != cards.get(i).ordinal()){
                return false;
            }
        }
        return true;
    }

    //判断是不是同花或者顺子或者同花顺；如果不是的话就返回null；
    public Classification straightAndFlush(ArrayList<Poker.Rank> cardRank, ArrayList<Poker.Suit> cardSuit )
    {
        if(isStraight(cardRank) && isFlush(cardSuit))
        {
            return Classification.StraightF;
        }
        else if(isStraight(cardRank))
        {
            return Classification.Straight;
        }
        else if (isFlush(cardSuit))
        {
            return Classification.Flush;
        }
        else
        {
            return null;
        }
    }

    //其余情况
    public Classification whichNK(ArrayList<Poker.Rank> cards) {
        int count = 0;    //cnt = 相同元素个数 - 1
        int flag = 0;   //flag用以将相同元素中的一个放入到tmp arraylist里面
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i - 1).ordinal() == cards.get(i).ordinal())
            {
                count++;
                flag++;
                if (flag == 1)
                {
                    sameRankGroup.add(cards.get(i - 1));
                }
            }
            else {
                flag = 0;
            }
        }
        Collections.sort(sameRankGroup);
        switch (count) {
            case 0:
                return Classification.HighC;
            case 1:
                return Classification.OneP;
            case 2:
                if(sameRankGroup.size() == 2)
                {
                    return Classification.TwoP;
                }
                else
                {
                    return Classification.ThreeK;
                }
            case 3:
                if(sameRankGroup.size() == 2)
                {
                    int cnt = 0;
                    for(Poker.Rank element : cards) //计算在samePokerGroup中的两个元素在cards中的重复次数，以判断谁是rank of 3 cards，谁是2
                    {
                        if(element.equals(sameRankGroup.get(0)))
                        {
                            cnt ++;
                        }
                    }
                    if(cnt == 3)
                    {
                        Collections.swap(sameRankGroup, 0, 1); //cnt==3说明samePokerGroup的第一位才是有高优先级的，所以把它排到后面，把原来的第二位排到第一位
                    }
                    return Classification.FullH;
                }
                else
                {
                    return Classification.FourK;
                }
            default:
                return null;
        }
    }

    //total
    public Classification whoAmI(ArrayList<Poker.Rank> cardRank, ArrayList<Poker.Suit> cardSuit)
    {
        if(straightAndFlush(cardRank, cardSuit) != null)
        {
            return straightAndFlush(cardRank, cardSuit);
        }
        else
        {
            return whichNK(cardRank);
        }
    }

}
