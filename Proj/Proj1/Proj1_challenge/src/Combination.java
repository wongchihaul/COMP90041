/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Classification
 * @Description In Combination.java documentation, Combination classcan be instantiated to represent a player's five
 * cards, including their ranks and suits, and the object also has an ArrayList containing Ranks which have same rank
 * with others, but each rank in list is distinct(e.g., say player has 8H, 8S, 9H, 9S, 7C, then sameRankGroup will add
 *  one 8 and one 9 in list.) And the Combination class has an enum to represent 9 classifications as well.
 * !!IMPORTANT!! to mention : all ArrayLists are sorted in ascending order.
 * @date 2019-09-30 18:33
 **/

import java.util.ArrayList;
import java.util.Collections;

class Combination {
    private ArrayList<Poker.Rank> sameRankGroup;
    private ArrayList<Poker.Rank> combCardRank;
    private ArrayList<Poker.Suit> combCardSuit;
    public enum Classification
    {
        HighC,              //High card
        OneP,               //One pair
        TwoP,               //Two pair
        ThreeK,             //Three of a kind
        Straight,           //Straight
        Flush,              //Flush
        FullH,              //Full house
        FourK,              //Four of a kind
        StraightF,          //Straight flush
    }

    Combination(ArrayList<Poker.Rank> combCardRank, ArrayList<Poker.Suit> combCardSuit,
                ArrayList<Poker.Rank> sameRankGroup  )
    {
        this.combCardRank = combCardRank;
        this.combCardSuit = combCardSuit;
        this.sameRankGroup = sameRankGroup;
    }

    ArrayList<Poker.Rank> getSamePokerGroup() {
        return sameRankGroup;
    }

    ArrayList<Poker.Rank> getCombCardRank() {
        return combCardRank;
    }

    ArrayList<Poker.Suit> getCombCardSuit() {
        return combCardSuit;
    }

    /*
     * @Description //whoAmI is combination of straightAndFlush and whichNK, and I will calling whoAmI directly
     * in Poker.java to classify the player's hand.
     * @Parameter [cardRank, cardSuit]
     * @return Combination.Classification
     */
    Classification whoAmI(ArrayList<Poker.Rank> cardRank, ArrayList<Poker.Suit> cardSuit)
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

    /*Input: ascending sorted Rank-type ArrayList*/
    /*Compare every two adjacent elements, if their ordinals are different by 1, then it is a straight*/
    private static boolean isStraight(ArrayList<Poker.Rank> cards)
    {
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i-1).ordinal() != cards.get(i).ordinal() - 1 ){
                return false;
            }
        }
        return true;
    }

    /*Input: ascending sorted Rank-type ArrayList*/
    /*Compare every two adjacent elements, if all of their ordinals are equal, then it is a flush*/
    private static boolean isFlush(ArrayList<Poker.Suit> cards)
    {
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i-1).ordinal() != cards.get(i).ordinal()){
                return false;
            }
        }
        return true;
    }

    /*Determine if it is a flush or a straight or straight flush; if not, return null*/
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

    /*To determine which N_of_a_kind the hand is and whether the hand is High Card or not*/
    public Classification whichNK(ArrayList<Poker.Rank> cards) {
        int count = 0;    //used to preliminary classify which N_of_a_Kind or High Card the hand may be.
        int flag = 0;   //used to add distinct Ranks having same rank with others.
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
                    //Calculate the number of repetitions of the first elements in the samePokerGroup in cards
                    //, in order to find out which one belongs to "three cards of same rank".
                    for(Poker.Rank element : cards)
                    {
                        if(element.equals(sameRankGroup.get(0)))
                        {
                            cnt ++;
                        }
                    }
                    if(cnt == 3)
                    {
                        //cnt=3 indicates that the first element of the samePokerGroup has high priority,
                        //so put it to the back and put the original second element to the first place.
                        Collections.swap(sameRankGroup, 0, 1);
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


}
