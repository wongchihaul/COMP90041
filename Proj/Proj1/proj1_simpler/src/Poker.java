/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName Poker
 * @Description TODO
 * @Date 2019-09-29 16:34
 **/
import java.util.Arrays;
import java.util.ArrayList;
public class Poker {

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
            if(this.ordinal() < 9)
            {
                return String.valueOf(this.getRank());
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


    public static void main(String[] args) {
        ArrayList<Rank> ranksOfCardList = new ArrayList<>();
        ArrayList<Suit> suitsOfCardList = new ArrayList<>();
        if(args.length > 5)
        {
            System.out.println("NOT UNDERTAKEN");
            System.exit(0);
        }
        if(args.length == 0 || args.length % 5 != 0)
        {
            System.out.println("Error: wrong number of arguments; must be a multiple of 5");
            System.exit(0);
        }
        for(int i = 0; i < args.length; i ++ )
        {
        if(Rank.valueOf(args[i].toUpperCase().charAt(0)) != null &&
                Suit.valueOf(args[i].toUpperCase().charAt(1)) != null)
            {
                ranksOfCardList.add(Rank.valueOf(args[i].toUpperCase().charAt(0)));
                suitsOfCardList.add(Suit.valueOf(args[i].toUpperCase().charAt(1)));
            }
        else
            {
                System.out.println("Error: invalid card name " + "'" +args[i] + "'");
                System.exit(0);
            }
        }
        Rank[] ranksOfCards = new Rank[ranksOfCardList.size()];
        ranksOfCardList.toArray(ranksOfCards);
        Arrays.sort(ranksOfCards);
        System.out.print("Player 1: ");
        if(HandDESC.isStraight(ranksOfCards) && HandDESC.isFlush(suitsOfCardList))
        {
            System.out.println(ranksOfCards[ranksOfCards.length-1] + "-high straight flush");
        }
        else if (HandDESC.isStraight(ranksOfCards))
        {
            System.out.println(ranksOfCards[ranksOfCards.length-1] + "-high straight");
        }
        else if (HandDESC.isFlush(suitsOfCardList))
        {
            System.out.println(ranksOfCards[ranksOfCards.length-1] + "-high flush");
        }
        else
        {
            HandDESC.isN_of_a_Kind(ranksOfCardList);
        }

    }



}
