import java.util.List;

public class Person {

    private String name;
    private int age;
    private List<Country> visitedCountries;

    Person(String name, int age, List<Country> visitedCountries) {
        this.name = name;
        this.age = age;
        this.visitedCountries = visitedCountries;
    }

    public boolean hasVisited(Country country) {
        return visitedCountries.contains(country);
    }

    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }

    public int getNumberOfVisitedCountries() {
        return visitedCountries.size();
    }

}
