/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Compare
 * @Description TODO
 * @date 2019-09-30 18:33
 **/


import java.util.ArrayList;
import java.util.Collections;


public class Compare {
    public static boolean isSameCat(ArrayList<Classification.Category> handCat)
    {
        for(int i = 1; i < handCat.size(); i++)
        {
            if(handCat.get(i-1).ordinal() != handCat.get(i).ordinal())
            {
                return false;
            }
        }
        return true;
    }

    //查看返回的winToNextTurn的长度；如果每个Cat都不同，那么返回的长度是1，其元素对应赢家；如果有两个或以上的相同，则返回整数类型array list，记录着玩家的ID，进入sameCat进行比较
    public static ArrayList<Integer> diffCat(ArrayList<Classification.Category> handCat)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        for(int i = 0; i < handCat.size(); i++)
        {
            if(handCat.get(i).equals(Collections.max(handCat)))
            {
                winToNextTurn.add(i);
            }
        }
        return winToNextTurn;

    }

    //传入winToNextTurn对应的玩家的手牌Category！
    public static ArrayList<Integer> sameCat(Classification.Category category,
                               ArrayList<Integer> winToNextTurn,
                               ArrayList<Classification> eachPlayerCard) {
        switch (category) {
            case StraightF:
            case Straight:
                return compSAndF(winToNextTurn, eachPlayerCard);
            case FourK:
            case ThreeK:
            case OneP:
                return compNK(winToNextTurn, eachPlayerCard);
            case Flush:
            case HighC:
                return compHighC(winToNextTurn, eachPlayerCard);
            case FullH:
            case TwoP:
                return compHasTwoSameCards(winToNextTurn, eachPlayerCard);
            default:
                return null;
        }
    }

    //返回同花、顺子、同花顺的最后赢家们的ID(没+1的，要记得自己加)
    public static ArrayList<Integer> compSAndF(ArrayList<Integer> winToNextTurn, ArrayList<Classification> eachPlayerCard)
    {
        ArrayList<Poker.Rank> firstCardRank = new ArrayList<>();
        ArrayList<Integer> winner = new ArrayList<>();
        for(int i = 0; i < winToNextTurn.size(); i++)
        {
            Poker.Rank firstCard = eachPlayerCard.get(winToNextTurn.get(i)).everyFiveCardRank.get(0);
            firstCardRank.add(firstCard); //winToNextTurn记录了玩家ID，然后在eachPlayerCard里面找已经排好序的第一张牌
        }

        //TODO 我觉得这个把胜出者加到下一轮的这一part可以分离开来
        for(int j = 0; j < firstCardRank.size(); j ++)
        {
            if (firstCardRank.get(j).equals(Collections.max(firstCardRank)))
            {
                winner.add(winToNextTurn.get(j));
            }
        }
        return winner;
    }

    //返回4K, 3K, 1P的最后赢家们的ID(没+1的，要记得自己加);原因是只有一个拥有重复的数字
    public static ArrayList<Integer> compNK(ArrayList<Integer> winToNextTurn,
                                                   ArrayList<Classification> eachPlayerCard)
    {
        ArrayList<Integer> nextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
        for(int i = 0; i < winToNextTurn.size(); i++)
        {
            Poker.Rank NsCard = eachPlayerCard.get(winToNextTurn.get(i)).samePokerGroup.get(0);
            toCompare.add(NsCard);
        }
        //TODO 我觉得这个把胜出者加到下一轮的这一part可以分离开来
        for(int j = 0; j < toCompare.size(); j++)
        {
            if(toCompare.get(j).equals(Collections.max(toCompare)))
            {
                nextTurn.add(winToNextTurn.get(j));
            }
        }
        //之后对比重复牌之外的牌的rank
        //先找出不重复的牌有多少张
        //然后再开始对比
        Classification onePlayer = eachPlayerCard.get(nextTurn.get(0));
        int cnt = otherRankCard(onePlayer).size(); //cnt为不重复的牌的张数；

        while (nextTurn.size() > 1 && cnt > 0)
        {
            ArrayList<Integer> nextTurnCopied = cloneList(nextTurn);
            nextTurn.clear();
            toCompare.clear();
            for(int k = 0; k < nextTurnCopied.size(); k++)
            {
                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerCard.get(nextTurnCopied.get(k)));
                toCompare.add(otherCard.get(cnt-1)); //取不重复牌组的最后一张，也就是点数最大的那张；
            }
            for(int m = 0; m < toCompare.size(); m++)
            {
                if(toCompare.get(m).equals(Collections.max(toCompare)))
                {
                    nextTurn.add(winToNextTurn.get(m));
                }
            }
            cnt --;
        }
    return nextTurn;
    }

    //返回HighC的最后赢家们的ID(没+1的，要记得自己加)
    public static ArrayList<Integer> compHighC(ArrayList<Integer> winToNextTurn,
                                            ArrayList<Classification> eachPlayerCard)
    {
        ArrayList<Integer> nextTurn = cloneList(winToNextTurn);
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();

        Classification onePlayer = eachPlayerCard.get(winToNextTurn.get(0));
        int cnt = otherRankCard(onePlayer).size(); //cnt为不重复的牌的张数；
        while (nextTurn.size() > 1 && cnt > 0)
        {
            ArrayList<Integer> nextTurnCopied = cloneList(nextTurn);
            nextTurn.clear();
            toCompare.clear();
            for(int k = 0; k < nextTurnCopied.size(); k++)
            {
                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerCard.get(nextTurnCopied.get(k)));
                toCompare.add(otherCard.get(cnt-1)); //取不重复牌组的最后一张，也就是点数最大的那张；
            }
            for(int m = 0; m < toCompare.size(); m++)
            {
                if(toCompare.get(m).equals(Collections.max(toCompare)))
                {
                    nextTurn.add(nextTurnCopied.get(m));
                }
            }
            cnt --;
        }
        return nextTurn;
    }

    //返回FullHouse和2P的最后赢家ID（要记得+1）
    public static ArrayList<Integer> compHasTwoSameCards(ArrayList<Integer> winToNextTurn,
                                               ArrayList<Classification> eachPlayerCard)
    {
        ArrayList<Integer> nextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
            for(int i = 0; i < winToNextTurn.size(); i++)
            {
                Poker.Rank sameCard = eachPlayerCard.get(winToNextTurn.get(i)).samePokerGroup.get(1);
                toCompare.add(sameCard);
            }
            for(int j = 0; j < toCompare.size(); j++ )
            {
                if(toCompare.get(j).equals(Collections.max(toCompare)))
                {
                    nextTurn.add(winToNextTurn.get(j));
                }
            }
            if(nextTurn.size() == 1)
            {
                return nextTurn;
            }
            ArrayList<Integer> nextTurnCopied1 = cloneList(nextTurn);
            nextTurn.clear();
            toCompare.clear();
            for(int k = 0; k < nextTurnCopied1.size(); k++)
            {
                Poker.Rank sameCard = eachPlayerCard.get(nextTurnCopied1.get(k)).samePokerGroup.get(0);
                toCompare.add(sameCard);
            }
            for(int m = 0; m < toCompare.size(); m++ )
            {
                if(toCompare.get(m).equals(Collections.max(toCompare)))
                {
                    nextTurn.add(nextTurnCopied1.get(m));
                }
            }

        //之后对比重复牌之外的牌的rank
        //先找出不重复的牌有多少张
        //然后再开始对比
        Classification onePlayer = eachPlayerCard.get(nextTurn.get(0));
        int cnt = otherRankCard(onePlayer).size(); //cnt为不重复的牌的张数；

        while (nextTurn.size() > 1 && cnt > 0)
        {
            ArrayList<Integer> nextTurnCopied2 = cloneList(nextTurn);
            nextTurn.clear();
            toCompare.clear();
            for(int k = 0; k < nextTurnCopied2.size(); k++)
            {
                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerCard.get(nextTurnCopied2.get(k)));
                toCompare.add(otherCard.get(cnt-1)); //取不重复牌组的最后一张，也就是点数最大的那张；
            }
            for(int m = 0; m < toCompare.size(); m++)
            {
                if(toCompare.get(m).equals(Collections.max(toCompare)))
                {
                    nextTurn.add(nextTurnCopied2.get(m));
                }
            }
            cnt --;
        }
        return nextTurn;

    }

    //传入一个classification实例，并且得到其不重复的手牌的rank
    public static ArrayList<Poker.Rank> otherRankCard(Classification player)
    {
        ArrayList<Poker.Rank> cards = player.everyFiveCardRank;
        ArrayList<Poker.Rank> samePokerGroup = player.samePokerGroup;
        cards.removeAll(samePokerGroup);
        Collections.sort(cards);
        return cards;
    }

    //浅层复制数组
    public static <T> ArrayList<T> cloneList(ArrayList<T> arrayList)
    {
        ArrayList<T> clone = new ArrayList<>();
        for(int i = 0; i < arrayList.size(); i++)
        {
            clone.add(arrayList.get(i));
        }
        return clone;
    }

}
