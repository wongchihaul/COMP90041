import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author Zhihao Huang
 * @LoginID zhihhuang
 * @ClassName HandDESC
 * @Description TODO
 * @date 2019-09-29 23:34
 **/
public class HandDESC {
    //创建 categories of hand 的enum，包含了优先级
    public enum Category{
        StraightF,             //Straight flush
        FourK,              //Four of a kind
        FullH,              //Full house
        Flush,
        Straight,
        ThreeK,             //Three of a kind
        TwoP,               //Two pair
        OneP,               //One pair
        HighC              //High card
    }


    //输入排好序的Rank类型数组，比较相邻两元素，如果它们的ordinal均相差1，那么是顺子
    public static boolean isStraight(Poker.Rank[] cards){
        for(int i = 1; i < cards.length; i++){
            if(cards[i-1].ordinal() != cards[i].ordinal()-1){
                return false;
            }
        }
        return true;
    }

    //输入Suit类型数组，比较相邻两元素，如果它们的ordinal均相等，那么是同花
    public static boolean isFlush(ArrayList<Poker.Suit> cards){
        for(int i = 1; i < cards.size(); i++){
            if(cards.get(i-1).ordinal() != cards.get(i).ordinal()){
                return false;
            }
        }
        return true;
    }

    //其余情况
    public static void isN_of_a_Kind(ArrayList<Poker.Rank> cards){
        int cnt = 0;    //cnt = 相同元素个数 - 1
        int flag = 0;   //flag用以将相同元素中的一个放入到tmp1 arraylist里面
        Poker.Rank[] cards2 = new Poker.Rank[cards.size()];
        cards.toArray(cards2);
        Arrays.sort(cards2);
        ArrayList<Poker.Rank> tmp1 = new ArrayList<>();
        for(int i = 1; i < cards2.length; i++)
        {
            if(cards2[i-1].ordinal() == cards2[i].ordinal())
            {
                cnt ++;
                flag ++;
                if(flag == 1)
                {
                    tmp1.add(cards2[i]);
                }
            }
            else
            {
                flag = 0;
            }


        }
        Poker.Rank[] tmp2 = new Poker.Rank[tmp1.size()];
        tmp1.toArray(tmp2);
        Arrays.sort(tmp2);
        switch (cnt){
            case 0 :
                System.out.println(cards2[cards2.length-1]+ "-high");
                break;
            case 1 :
                System.out.println("Pair of " + tmp2[0]+ "s");
                break;
            case 2 :
                if(tmp2.length == 2){ //two pairs
                    System.out.println(tmp2[1]+ "s over " + tmp2[0]+ "s");
                }
                else // three of a kind
                {
                    System.out.println("Three " + tmp2[0]+ "s");
                }
                break;
            case 3:
                if(tmp2.length == 2){ //full house
                    int count = 0;
                    for(Poker.Rank element : cards2){   //计算在tmp2中的两个元素在card2中的重复次数，以判断谁是rank of 3 cards，谁是2
                        if(element.equals(tmp2[0]))
                        {
                            count ++;
                        }
                    }
                    if(count == 2){
                        System.out.println(tmp2[1] + "s full of " +tmp2[0] + "s");
                    }
                    else{
                        System.out.println(tmp2[0] +"s full of " +tmp2[1] + "s");
                    }

                }
                else // Four of a kind
                {
                    System.out.println("Four " + tmp2[0] + "s");
                }
                break;
        }

    }

}
