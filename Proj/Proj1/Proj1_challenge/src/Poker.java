/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Poker
 * @Description TODO
 * @date 2019-09-30 18:04
 **/

import java.util.ArrayList;
import java.util.Collections;

public class Poker {
    static final int ONEHASCARDS = 5;
    static ArrayList<Integer> playerID = new ArrayList<>();
    static ArrayList<Classification.Category> handCat = new ArrayList<>();
    static ArrayList<Classification> eachPlayerCard = new ArrayList<>();
    //Create an enum class that contains ranks of cards.
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
        public static Rank valueOf(char val){
            switch (val) {
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

    //Create an enum class that contains suits of cards.
    public enum Suit{
        CLUB('C'),
        DIAMOND('D'),
        HEART('H'),
        SPADE('S');
        public static Suit valueOf(char val){
            switch (val){
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



    //输出话语
    public static void printOut(Classification.Category category, Classification player)
    {
        Rank highestOne = player.everyFiveCardRank.get(ONEHASCARDS-1);
        int sameGroupSize = player.samePokerGroup.size();
        if(sameGroupSize > 0)
        {
            Rank largerSameOne = player.samePokerGroup.get(sameGroupSize-1);
            Rank smallerSameOne = player.samePokerGroup.get(0);
            switch(category)
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
        else
        {
            switch(category)
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

    public static void main(String[] args) {
        if(args.length == 0 || args.length % 5 != 0)
        {
            System.out.println("Error: wrong number of arguments; must be a multiple of 5");
            System.exit(0);
        }

        //构建包含Classification实例的数组以及每个玩家手牌类型的数组,还有playerID数组
        for(int i = 0; i < (args.length / 5) ; i ++ )
        {
            ArrayList<Rank> cardsRank = new ArrayList<>();
            ArrayList<Suit> cardsSuit = new ArrayList<>();
            ArrayList<Rank> samePokerGroup = new ArrayList<>();
            for(int j = i * 5 ; j < (i + 1) * 5 ; j ++) {
                if (Rank.valueOf(args[j].toUpperCase().charAt(0)) != null &&
                        Suit.valueOf(args[j].toUpperCase().charAt(1)) != null)
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
            Collections.sort(cardsSuit);
            Classification player = new Classification(cardsRank, cardsSuit, samePokerGroup );
            Classification.Category category = player.whoAmI(player.everyFiveCardRank, player.everyFiveCardSuit);
            eachPlayerCard.add(player);
            handCat.add(category);
            playerID.add(i);
        }


        //输出每位玩家手牌的类型；
        for(int i = 0; i < eachPlayerCard.size(); i++)
        {
            System.out.print("Player " + (i+1) + ": ");
            printOut(handCat.get(i), eachPlayerCard.get(i));
        }

        if(args.length == 5)
        {
            System.exit(0);
        }

        //开始比较找出最后赢家或者draw
        ArrayList<Integer> winner = new ArrayList<>();
        if(Compare.isSameCat(handCat))          //如果都是同一类型的；
        {
            winner = Compare.sameCat(handCat.get(0), playerID, eachPlayerCard );
        }
        else
        {
            ArrayList<Integer> winToNextTurn = Compare.diffCat(handCat);
            if(winToNextTurn.size() > 1)
            {
                winner = Compare.sameCat(handCat.get(winToNextTurn.get(0)), winToNextTurn, eachPlayerCard);
            }
            else
            {
                System.out.println("Player " + (winToNextTurn.get(0) + 1) + " wins.");
                System.exit(0);
            }
        }
        if(winner.size() == 1)
        {
            System.out.println("Player " + (winner.get(0) + 1) + " wins.");
        }
        else if(winner.size() == 2)
        {
            System.out.println("Players " + (winner.get(0) + 1) +" and " + (winner.get(1) + 1) + " draw.");
        }
        else
        {
            System.out.print("Players");
            for(int i = 0; i < winner.size() - 2; i++)
            {
                System.out.print(" " + (winner.get(i) + 1) + ",");
            }
            System.out.println(" " + (winner.get(winner.size() - 2) + 1) + " and " +
                    (winner.get(winner.size() - 1) + 1)+" draw.");
        }

    }
}
