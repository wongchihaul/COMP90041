package Circle;

public class Circle {
    public static void main(String[] args) {
        double area;
        double radius;
        radius = Double.parseDouble(args[0]) / 2;
        area = Math.pow(radius, 2) * Math.PI;
        System.out.printf("%.4f", area);
    }
}
