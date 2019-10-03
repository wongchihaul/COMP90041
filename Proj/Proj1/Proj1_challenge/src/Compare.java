/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Compare
 * @Description Compare.java provides some method to find out who is winner or who hold a draw. In this documentation,
 * I will use "candidate" to represent an instance of Combination Class, and candidates are obviously who prepare to
 * win the game. And "winToNextTurn" are those who win the current competition and prepares to next comparison. I will
 * also mention those cards other than same cards in one hand as "other card" in my comments.
 * Now, start gambling!
 * @date 2019-09-30 18:33
 **/


import java.util.ArrayList;
import java.util.Collections;


class Compare {
    /*
     * @Description //Compare every two adjacent classification-type elements, if all of their ordinals are equal,
     * then all of them are same classification
     * @Parameter [handClass]
     * @return boolean
     **/
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

    /*
     * @Description //Not a same class list. After one comparison, check the size of winToNextTurn list. If the size is
     * one, the only one element in winToNextTurn list represent winner. Otherwise, if the size is lager than one,
     * there are hands with same classification that needs further comparison(i.e. need to call sameClass method).
     * @Parameter [handClass]
     * @return java.util.ArrayList<java.lang.Integer>
     **/
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


    /*
     * @Description //sameClass indicates which method should be called under different circumstances. And those
     * methods will return an Integer ArrayList that represents playerID of winner or those whom hold a tie.
     * @Parameter [classification, candidate, eachPlayerComb]
     * @return java.util.ArrayList<java.lang.Integer>
     **/
    static ArrayList<Integer> sameClass(Combination.Classification classification,
                                        ArrayList<Integer> candidate,
                                        ArrayList<Combination> eachPlayerComb)
    {
        switch (classification)
        {
            case StraightF:
            case Straight:
                return compStraight(candidate, eachPlayerComb);
            case FourK:
            case ThreeK:
            case OneP:
                return hasOneSameCard(candidate, eachPlayerComb);
            case Flush:
            case HighC:
                return compOneByOne(candidate, eachPlayerComb);
            case FullH:
            case TwoP:
                return hasTwoSameCards(candidate, eachPlayerComb);
            default:
                return null;
        }
    }

    /*
     * @Description //The method will affect the winToNextTurn ArrayList. It will make winToNextTurn ArrayList contain
     * latest winners of current competition. The method is for those same cards of n-of-a-kind and those hands which
     * are straight or straight flush.
     * @Parameter [candidate, winToNextTurn, toCompare, eachPlayerComb, samePokerGroupSize, isStraight]
     * @return void
     **/
    private static void nextTurn_same(ArrayList<Integer> candidate, ArrayList<Integer> winToNextTurn,
                                      ArrayList<Poker.Rank> toCompare, ArrayList<Combination> eachPlayerComb,
                                      int samePokerGroupSize, boolean isStraight)
    {

        for (Integer i : candidate)
        {
            Poker.Rank cardToCompare;
            if(isStraight)
            {
                //for straight or straight flush, we can just compare the rank of first card to know who is the winner.
                cardToCompare = eachPlayerComb.get(i).getCombCardRank().get(0);
            }
            else
            {
                //SamePokerGroup is sorted by ascending order, and we should compare the one that has higher priority.
                if (samePokerGroupSize == 1)
                {
                    cardToCompare = eachPlayerComb.get(i).getSamePokerGroup().get(0);
                }
                else    //samePokerGroupSize == 2; and only this case makes sense.
                {
                    cardToCompare = eachPlayerComb.get(i).getSamePokerGroup().get(1);
                }
            }
            toCompare.add(cardToCompare);
        }
        for(int j = 0; j < toCompare.size(); j ++)
        {
            //Find out those elements of toCompare ArrayList that equals the maximum element of this ArrayList and add
            //them into winToNextTurn ArrayList.
            if(toCompare.get(j).equals(Collections.max(toCompare)))
            {
                winToNextTurn.add(candidate.get(j));
            }
        }
    }

    /*
     * @Description //It will affect winToNextTurn ArrayList as same as nextTurn_same. However, this method is for those
     * cards other than same cards of n-of-a-kind and those hands which are flush or high card.
     * @Parameter [winToNextTurn, toCompare, eachPlayerComb]
     * @return void
     **/
    private static void nextTurn_other(ArrayList<Integer> winToNextTurn, ArrayList<Poker.Rank> toCompare,
                                       ArrayList<Combination> eachPlayerComb)
    {
        Combination onePlayer = eachPlayerComb.get(winToNextTurn.get(0));
        int cnt = otherRankCard(onePlayer).size();      //cnt represents the amount of "other card" in one hand.

        while(winToNextTurn.size() > 1 && cnt > 0)
        {
            //Make a shallow copy of winToNextTurn. So that we can still make comparision with clearing winToNextTurn.
            //Take out everyone's "other card" with highest rank to compare with each other.
            //And add the latest winners per iteration.
            ArrayList<Integer> copy = cloneList(winToNextTurn);
            winToNextTurn.clear();
            toCompare.clear();

            for (Integer i : copy) {
                ArrayList<Poker.Rank> otherCard = otherRankCard(eachPlayerComb.get(i));
                toCompare.add(otherCard.get(cnt - 1));
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

    /*
     * @Description //Input a player (i.e. a Combination instance) and get his/her "other card" array list.
     * @Parameter [player]
     * @return java.util.ArrayList<Poker.Rank>
     **/
    private static ArrayList<Poker.Rank> otherRankCard(Combination player)
    {
        ArrayList<Poker.Rank> cards = player.getCombCardRank();
        ArrayList<Poker.Rank> samePokerGroup = player.getSamePokerGroup();
        cards.removeAll(samePokerGroup);
        Collections.sort(cards);
        return cards;
    }


    private static ArrayList<Integer> compStraight(ArrayList<Integer> candidate,
                                                   ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
        nextTurn_same(candidate, winToNextTurn, toCompare,eachPlayerComb, 0, true);
        return winToNextTurn;
    }


    private static ArrayList<Integer> hasOneSameCard(ArrayList<Integer> candidate,
                                                     ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
        nextTurn_same(candidate, winToNextTurn, toCompare, eachPlayerComb, 1, false);
        nextTurn_other(winToNextTurn, toCompare, eachPlayerComb);
        return winToNextTurn;
    }


    private static ArrayList<Integer> compOneByOne(ArrayList<Integer> candidate,
                                                   ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = cloneList(candidate);
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
        nextTurn_other(winToNextTurn, toCompare, eachPlayerComb);
        return winToNextTurn;
    }


    private static ArrayList<Integer> hasTwoSameCards(ArrayList<Integer> candidate,
                                                      ArrayList<Combination> eachPlayerComb)
    {
        ArrayList<Integer> winToNextTurn = new ArrayList<>();
        ArrayList<Poker.Rank> toCompare = new ArrayList<>();
        nextTurn_same(candidate,winToNextTurn,toCompare,eachPlayerComb, 2, false);
        if(winToNextTurn.size() == 1)
        {
            return winToNextTurn;
        }
        ArrayList<Integer> copy = cloneList(winToNextTurn);
        winToNextTurn.clear();
        toCompare.clear();

        nextTurn_same(copy,winToNextTurn,toCompare,eachPlayerComb, 1, false);

        nextTurn_other(winToNextTurn, toCompare, eachPlayerComb);
        return winToNextTurn;

    }



    /* Shallow copy of an ArrayList.*/
    private static <T> ArrayList<T> cloneList(ArrayList<T> arrayList)
    {
        ArrayList<T> clone = new ArrayList<>();
        for (T t : arrayList)
        {
            clone.add(t);
        }
        return clone;
    }


}
