package lab5;

public class Character
{
    private String name;
    private String actor;
    private int rating;
    private String movie;

    public Character(String name, String actor, String movie, int ranting){
        this.name = name;
        this.actor = actor;
        this.movie = movie;
        this.rating = ranting;
    }

    public String getName()
    {
        return this.name;
    }

    public String getActor()
    {
        return this.actor;
    }

    public String getMovie()
    {
        return this.movie;
    }

    public int getRating()
    {
        return this.rating;
    }


}
