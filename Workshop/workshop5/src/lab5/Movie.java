package lab5;

public class Movie
{
    private String title;
    private int rank;
    private int runTime;

    public Movie(String title, int rank, int runTime)
    {
        this.title = title;
        this.rank = rank;
        this.runTime = runTime;
    }


    public String toString()
    {
        return title + " " + rank + " " + runTime;
    }

    public boolean equals(Movie that)
    {
        return this.title.equals(that) && this.rank == that.rank && this.runTime == that.runTime;
    }

    public static void main(String[] args) {
        Character BlackWidow = new Character("BlackWidow", "Scarlett", "The Avenger", 5 );
        System.out.println(BlackWidow);
    }

}
