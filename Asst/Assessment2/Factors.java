//package Factors;

public class Factors {
    public static void main(String[] args) {
        boolean notHaveSpace = true;
        int num = Integer.parseInt(args[0]);
        for(int i = 1; i <= num; i++){
            if(num % i == 0){
                if(notHaveSpace){
                    System.out.print(i);
                    notHaveSpace = false;
                }
                else{
                    System.out.print(" " + i);
                }
            }
        }
    }
}
