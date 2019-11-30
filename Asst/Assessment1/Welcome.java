package Welcome;

public class Welcome {
    public static void main(String[] args) {
        System.out.println("Hello " + args[0] + " " + args[1] + ".");
        System.out.println("Is that "+args[0]+" "+args[1].toUpperCase()+" or "+args[0].toUpperCase()+" "+args[1]+"?" );
    }
}
