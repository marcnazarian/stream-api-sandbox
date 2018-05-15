import java.util.List;

public class User {

    private String name;
    private int age;
    private List<Beer> favouriteBeers;

    User(String name, int age, List<Beer> favoriteBeers) {
        this.name = name;
        this.age = age;
        this.favouriteBeers = favoriteBeers;
    }

    public boolean doesLike(Beer beer) {
        return favouriteBeers.contains(beer);
    }

    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }

    public int getNumberOfFavouriteBeers() {
        return favouriteBeers.size();
    }

}
