/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Poker
 * @Description Poker.java documentation has a main method to meet requirements for outputs of Project 1. And it also
 * has two enums to represent properties, rank and suit, of poker.
 * @date 2019-09-30 18:04
 **/

import java.util.ArrayList;
import java.util.Collections;

public class Poker {
    /**The maximum amount of cards in one hand.**/
    private static final int ONEHASCARDS = 5;

    /**Representing serial numbers (starts from 0) of players.**/
    private static ArrayList<Integer> playerID = new ArrayList<>();

    /**Representing the classification of one hand.**/
    private static ArrayList<Combination.Classification> handClass = new ArrayList<>();

    /**Representing the combination of each player **/
    private static ArrayList<Combination> eachPlayerComb = new ArrayList<>();

    /**Create an enum that contains ranks of cards.**/
    public enum Rank{
        TWO('2'),
        THREE('3'),
        FOUR('4'),
        FIVE('5'),
        SIX('6'),
        SEVEN('7'),
        EIGHT('8'),
        NINE('9'),
        TEN('T'),
        Jack('J'),
        Queen('Q'),
        King('K'),
        Ace('A');

        /**Overload the valueOf method of enum. It will get a char-type argument, and return the corresponding
         * enum-type member of enum Rank.
         */
        public static Rank valueOf(char rank){
            switch (rank) {
                case '2':
                    return TWO;
                case '3':
                    return THREE;
                case '4':
                    return FOUR;
                case '5':
                    return FIVE;
                case '6':
                    return SIX;
                case '7':
                    return SEVEN;
                case '8':
                    return EIGHT;
                case '9':
                    return NINE;
                case 'T':
                    return TEN;
                case 'J':
                    return Jack;
                case 'Q':
                    return Queen;
                case 'K':
                    return King;
                case 'A':
                    return Ace;
                default:
                    return null;
            }
        }

        private char rank;

        Rank(char rank){
            this.rank = rank;
        }

        public char getRank() {
            return this.rank;
        }

        /** Overwrite the toString method of enum. */
        public String toString() {
            if(this.ordinal() < 8)
            {
                return String.valueOf(this.getRank());
            }
            else if(this.ordinal() == 8)
            {
                return "10";
            }
            else
            {
                return this.name();
            }
        }
    }

    /** Create an enum that contains suits of cards.**/
    public enum Suit{
        CLUB('C'),
        DIAMOND('D'),
        HEART('H'),
        SPADE('S');

        public static Suit valueOf(char suit){
            switch (suit){
                case 'C':
                    return CLUB;
                case 'D':
                    return DIAMOND;
                case 'H':
                    return HEART;
                case 'S':
                    return SPADE;
                default:
                    return null;
            }
        }

        private char suit;

        Suit(char suit){
            this.suit = suit;
        }

        public char getSuit() {
            return this.suit;
        }

    }


    /**
     * @Description To give the output that meets requirements, including classification of a hand and the result of
     * a comparision, excluding playerID information.
     * @Parameter [classification, playerComb]
     * @return void
     **/
    private static void printOut(Combination.Classification classification, Combination playerComb)
    {
        Rank highestOne = playerComb.getCombCardRank().get(ONEHASCARDS-1);
        int sameGroupSize = playerComb.getSamePokerGroup().size();
        if(sameGroupSize > 0)       /*This hand must be N_of_a_kind.*/
        {
            /*If there is only one element in sameRankGroup, lagerSameOne will get that one. Otherwise, lagerSameOne
             will get the one having higher priority in comparison, while smallerSameOne get the other one. */
            Rank largerSameOne = playerComb.getSamePokerGroup().get(sameGroupSize-1);
            Rank smallerSameOne = playerComb.getSamePokerGroup().get(0);

            switch(classification)
            {
                case FourK:
                    System.out.println("Four " + largerSameOne + "s");
                    break;
                case ThreeK:
                    System.out.println("Three " + largerSameOne + "s");
                    break;
                case FullH:
                    System.out.println(largerSameOne + "s full of " + smallerSameOne + "s");
                    break;
                case TwoP:
                    System.out.println(largerSameOne+ "s over " + smallerSameOne + "s");
                    break;
                case OneP:
                    System.out.println("Pair of " + largerSameOne + "s");
                    break;
            }
        }
        else    /* This hand must be Straight, Flush, StraightFlush or High card.*/
        {
            switch(classification)
            {
                case StraightF:
                    System.out.println(highestOne + "-high straight flush");
                    break;
                case Straight:
                    System.out.println(highestOne + "-high straight");
                    break;
                case Flush:
                    System.out.println(highestOne + "-high flush");
                    break;
                case HighC:
                    System.out.println(highestOne + "-high");
                    break;
            }
        }


    }

    private static void whoWins (ArrayList<Integer> winner)
    {
        switch (winner.size())
        {
            case 1:
                System.out.println("Player " + (winner.get(0) + 1) + " wins.");
                break;
            case 2:
                System.out.println("Players " + (winner.get(0) + 1) +" and " + (winner.get(1) + 1) + " draw.");
                break;
            default:
                System.out.print("Players");
                for(int i = 0; i < winner.size() - 2; i++)
                {
                    System.out.print(" " + (winner.get(i) + 1) + ",");
                }
                System.out.println(" " + (winner.get(winner.size() - 2) + 1) + " and " +
                        (winner.get(winner.size() - 1) + 1)+" draw.");
        }
    }

    public static void main(String[] args) {
        if(args.length == 0 || args.length % 5 != 0)
        {
            System.out.println("Error: wrong number of arguments; must be a multiple of 5");
            System.exit(0);
        }

        /*Construct a Combination instance for every five cards, and the instance will be added into an Combination-type
         * ArrayList, eachPlayerComb. Meanwhile, there will be a Classification-type ArrayList, handClass, and an
         * Integer ArrayList, playerID.
         */
        for(int i = 0; i < (args.length / 5) ; i ++ )
        {
            ArrayList<Rank> cardsRank = new ArrayList<>();
            ArrayList<Suit> cardsSuit = new ArrayList<>();
            ArrayList<Rank> samePokerGroup = new ArrayList<>();
            for(int j = i * 5 ; j < (i + 1) * 5 ; j ++) {
                if (Rank.valueOf(args[j].toUpperCase().charAt(0)) != null &&
                        Suit.valueOf(args[j].toUpperCase().charAt(1)) != null)      /*should be valid card name.*/
                {

                    cardsRank.add(Rank.valueOf(args[j].toUpperCase().charAt(0)));
                    cardsSuit.add(Suit.valueOf(args[j].toUpperCase().charAt(1)));
                }
                else
                 {
                    System.out.println("Error: invalid card name " + "'" + args[j] + "'");
                    System.exit(0);
                 }
            }
            Collections.sort(cardsRank);
            Collections.sort(cardsSuit);        /*Sort ArrayList in ascending order*/
            Combination player = new Combination(cardsRank, cardsSuit, samePokerGroup );
            Combination.Classification classification = player.whoAmI(player.getCombCardRank(),
                                                                        player.getCombCardSuit());
            eachPlayerComb.add(player);
            handClass.add(classification);
            playerID.add(i);
        }


        /*Output each player's classification of cards*/
        for(int i = 0; i < eachPlayerComb.size(); i++)
        {
            System.out.print("Player " + (i+1) + ": ");
            printOut(handClass.get(i), eachPlayerComb.get(i));
        }

        if(args.length == 5)
        {
            System.exit(0);
        }

        /*Start the comparison and find out winners, if there are more than one winners,
        then those winners hold a draw */
        ArrayList<Integer> winner;
        if(Compare.isSameClass(handClass))
        {
            winner = Compare.sameClass(handClass.get(0), playerID, eachPlayerComb);
        }
        else
        {
            ArrayList<Integer> winToNextTurn = Compare.diffClass(handClass);
            winner = Compare.sameClass(handClass.get(winToNextTurn.get(0)), winToNextTurn, eachPlayerComb);
        }
        whoWins(winner);

    }
}
