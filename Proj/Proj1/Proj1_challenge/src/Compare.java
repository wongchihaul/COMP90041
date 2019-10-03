/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Compare
 * @Description TODO
 * @date 2019-09-30 18:33
 **/


import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.Collections;


class Compare {
    static boolean isSameClass(ArrayList<Combination.Classification> handClass)
    {
        for(int i = 1; i < handClass.size(); i++)
        {
            if(handClass.get(i-1).ordinal() != handClass.get(i).ordinal())
            {
                return false;
            }
        }
        return true;
    }

    //查看返回的winToNextTurn的长度；如果每个Cat都不同，那么返回的长度是1，其元素对应赢家；如果有两个或以上的相同，则返回整数类型array list，记录着玩家的ID，进入sameCat进行比较
    static ArrayList<Integer> diffClass(ArrayList<Combination.Classification> handClass)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        for(int i = 0; i < handClass.size(); i++)
        {
            if(handClass.get(i).equals(Collections.max(handClass)))
            {
                winToNextTurn.add(i);
            }
        }
        return winToNextTurn;

    }

    //传入winToNextTurn对应的玩家的手牌Category！
    static ArrayList<Integer> sameClass(Combination.Classification classification,
                                        ArrayList<Integer> winToNextTurn,
                                        ArrayList<Combination> eachPlayerComb)
    {
        switch (classification)
        {
            case StraightF:
            case Straight:
                return compSAndF(winToNextTurn, eachPlayerComb);
            case FourK:
            case ThreeK:
            case OneP:
                return hasOneSameCard(winToNextTurn, eachPlayerComb);
            case Flush:
            case HighC:
                return compHighC(winToNextTurn, eachPlayerComb);
            case FullH:
            case TwoP:
                return compHasTwoSameCards(winToNextTurn, eachPlayerComb);
            default:
                return null;
        }
    }

    //返回同花、顺子、同花顺的最后赢家们的ID(没+1的，要记得自己加)
    public static ArrayList<Integer> compSAndF(ArrayList<Integer> candidate,
                                               ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
        for(int i = 0; i < candidate.size(); i++)
        {
            Poker.Rank cardToCompare = eachPlayerComb.get(candidate.get(i)).getCombCardRank().get(0);
            toCompare.add(cardToCompare); //winToNextTurn记录了玩家ID，然后在eachPlayerCard里面找已经排好序的第一张牌
        }

//        // 我觉得这个把胜出者加到下一轮的这一part可以分离开来
//        for(int j = 0; j < firstCardRank.size(); j ++)
//        {
//            if (firstCardRank.get(j).equals(Collections.max(firstCardRank)))
//            {
//                winner.add(candidate.get(j));
//            }
//        }
        nextTurn_same(candidate, winToNextTurn, toCompare,eachPlayerComb);
        return winToNextTurn;
    }

    //返回4K, 3K, 1P的最后赢家们的ID(没+1的，要记得自己加);原因是只有一个拥有重复的数字
    public static ArrayList<Integer> hasOneSameCard(ArrayList<Integer> candidate,
                                                    ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
//        for(int i = 0; i < candidate.size(); i++)
//        {
//            Poker.Rank NsCard = eachPlayerComb.get(candidate.get(i)).getSamePokerGroup().get(0);
//            toCompare.add(NsCard);
//        }
//        // 我觉得这个把胜出者加到下一轮的这一part可以分离开来
//        for(int j = 0; j < toCompare.size(); j++)
//        {
//            if(toCompare.get(j).equals(Collections.max(toCompare)))
//            {
//                winToNextTurn.add(candidate.get(j));
//            }
//        }

        nextTurn_same(candidate, winToNextTurn, toCompare, eachPlayerComb);

        //之后对比重复牌之外的牌的rank
        //先找出不重复的牌有多少张
        //然后再开始对比
//        Combination onePlayer = eachPlayerComb.get(winToNextTurn.get(0));
//        int cnt = otherRankCard(onePlayer).size(); //cnt为不重复的牌的张数；
//
//        while (winToNextTurn.size() > 1 && cnt > 0)
//        {
//            ArrayList<Integer> nextTurnCopied = cloneList(winToNextTurn);
//            winToNextTurn.clear();
//            toCompare.clear();
//            for(int k = 0; k < nextTurnCopied.size(); k++)
//            {
//                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerComb.get(nextTurnCopied.get(k)));
//                toCompare.add(otherCard.get(cnt-1)); //取不重复牌组的最后一张，也就是点数最大的那张；
//            }
//            for(int m = 0; m < toCompare.size(); m++)
//            {
//                if(toCompare.get(m).equals(Collections.max(toCompare)))
//                {
//                    winToNextTurn.add(candidate.get(m));
//                }
//            }
//            cnt --;
//        }
        nextTurn_other(winToNextTurn, toCompare, eachPlayerComb);
    return winToNextTurn;
    }

    //返回HighC的最后赢家们的ID(没+1的，要记得自己加)
    public static ArrayList<Integer> compHighC(ArrayList<Integer> candidate,
                                            ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = cloneList(candidate);
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();

//        Combination onePlayer = eachPlayerComb.get(candidate.get(0));
//        int cnt = otherRankCard(onePlayer).size(); //cnt为不重复的牌的张数；
//        while (winToNextTurn.size() > 1 && cnt > 0)
//        {
//            ArrayList<Integer> nextTurnCopied = cloneList(winToNextTurn);
//            winToNextTurn.clear();
//            toCompare.clear();
//            for(int k = 0; k < nextTurnCopied.size(); k++)
//            {
//                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerComb.get(nextTurnCopied.get(k)));
//                toCompare.add(otherCard.get(cnt-1)); //取不重复牌组的最后一张，也就是点数最大的那张；
//            }
//            for(int m = 0; m < toCompare.size(); m++)
//            {
//                if(toCompare.get(m).equals(Collections.max(toCompare)))
//                {
//                    winToNextTurn.add(nextTurnCopied.get(m));
//                }
//            }
//            cnt --;
//        }
        nextTurn_other(winToNextTurn, toCompare, eachPlayerComb);
        return winToNextTurn;
    }

    //返回FullHouse和2P的最后赢家ID（要记得+1）;
    public static ArrayList<Integer> compHasTwoSameCards(ArrayList<Integer> candidate,
                                               ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
//            for(int i = 0; i < candidate.size(); i++)
//            {
//                Poker.Rank sameCard = eachPlayerComb.get(candidate.get(i)).getSamePokerGroup().get(1);
//                toCompare.add(sameCard);
//            }
//            for(int j = 0; j < toCompare.size(); j++ )
//            {
//                if(toCompare.get(j).equals(Collections.max(toCompare)))
//                {
//                    winToNextTurn.add(candidate.get(j));
//                }
//            }
            nextTurn_same(candidate,winToNextTurn,toCompare,eachPlayerComb);
            if(winToNextTurn.size() == 1)
            {
                return winToNextTurn;
            }
            ArrayList<Integer> copy = cloneList(winToNextTurn);
            winToNextTurn.clear();
            toCompare.clear();
//            for(int k = 0; k < copy.size(); k++)
//            {
//                Poker.Rank sameCard = eachPlayerComb.get(copy.get(k)).getSamePokerGroup().get(0);
//                toCompare.add(sameCard);
//            }
//            for(int m = 0; m < toCompare.size(); m++ )
//            {
//                if(toCompare.get(m).equals(Collections.max(toCompare)))
//                {
//                    winToNextTurn.add(copy.get(m));
//                }
//            }
            nextTurn_same(copy,winToNextTurn,toCompare,eachPlayerComb);

        //之后对比重复牌之外的牌的rank
        //先找出不重复的牌有多少张
        //然后再开始对比
//        Combination onePlayer = eachPlayerComb.get(winToNextTurn.get(0));
//        int cnt = otherRankCard(onePlayer).size(); //cnt为不重复的牌的张数；
//
//        while (winToNextTurn.size() > 1 && cnt > 0)
//        {
//            ArrayList<Integer> nextTurnCopied2 = cloneList(winToNextTurn);
//            winToNextTurn.clear();
//            toCompare.clear();
//            for(int k = 0; k < nextTurnCopied2.size(); k++)
//            {
//                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerComb.get(nextTurnCopied2.get(k)));
//                toCompare.add(otherCard.get(cnt-1)); //取不重复牌组的最后一张，也就是点数最大的那张；
//            }
//            for(int m = 0; m < toCompare.size(); m++)
//            {
//                if(toCompare.get(m).equals(Collections.max(toCompare)))
//                {
//                    winToNextTurn.add(nextTurnCopied2.get(m));
//                }
//            }
//            cnt --;
//        }
        nextTurn_other(winToNextTurn, toCompare, eachPlayerComb);
        return winToNextTurn;

    }

    //传入一个classification实例，并且得到其不重复的手牌的rank
    public static ArrayList<Poker.Rank> otherRankCard(Combination player)
    {
        ArrayList<Poker.Rank> cards = player.getCombCardRank();
        ArrayList<Poker.Rank> samePokerGroup = player.getSamePokerGroup();
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

    //choose whom winToNextTurn
    public static void nextTurn_same(ArrayList<Integer> candidate,
                                     ArrayList<Integer> winToNextTurn,
                                     ArrayList<Poker.Rank> toCompare,
                                     ArrayList<Combination> eachPlayerComb)
    {
        for(Integer can : candidate)
        {
            Poker.Rank sameCard = eachPlayerComb.get(can).getSamePokerGroup().get(1);
            toCompare.add(sameCard);
        }
        for(int i = 0; i < toCompare.size(); i ++)
        {
            if(toCompare.get(i).equals(Collections.max(toCompare)))
            {
                winToNextTurn.add(candidate.get(i));
            }
        }
    }

    //choose whom winToNextTurn of other cards(i.e. no same card)
    public static void nextTurn_other(ArrayList<Integer> winToNextTurn, ArrayList<Poker.Rank> toCompare,
                                      ArrayList<Combination> eachPlayerComb)
    {
        Combination onePlayer = eachPlayerComb.get(winToNextTurn.get(0));
        int cnt = otherRankCard(onePlayer).size();

        while(winToNextTurn.size() > 1 && cnt > 0)
        {
            ArrayList<Integer> copy = cloneList(winToNextTurn);
            winToNextTurn.clear();
            toCompare.clear();
            for (Integer i : copy)
            {
                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerComb.get(i));//取需要比较玩家的不重复牌组
                toCompare.add(otherCard.get(cnt - 1));//取不重复牌组的最后一张，也就是点数最大的那张；
            }
            for(int j = 0; j < toCompare.size(); j++)
            {
                if(toCompare.get(j).equals(Collections.max(toCompare)))
                {
                    winToNextTurn.add(copy.get(j));
                }
            }

            cnt --;
        }
    }

}
