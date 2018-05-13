import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamAPISandboxTest {

    private Person alice = new Person("Alice", 26, Arrays.asList(Country.PERU, Country.COSTA_RICA, Country.SPAIN));
    private Person bob = new Person("Bob", 42, emptyList());
    private Person charline = new Person("Charline", 33, Arrays.asList(Country.SPAIN, Country.FRANCE, Country.ITALY));
    private Person danny = new Person("Danny", 19, singletonList(Country.AUSTRALIA));
    private Person elena = new Person("Elena", 22, Arrays.asList(Country.SPAIN, Country.ITALY, Country.ENGLAND, Country.GERMANY));
    private Person franck = new Person("Franck", 38, Arrays.asList(Country.FRANCE, Country.CUBA));
    private Person gina = new Person("Gina", 54, Arrays.asList(Country.FRANCE, Country.ENGLAND, Country.ITALY, Country.SPAIN, Country.AUSTRALIA, Country.COSTA_RICA, Country.PERU, Country.CUBA, Country.GERMANY, Country.BALI, Country.CANADA, Country.EGYPT, Country.RUSSIA));
    private Person hans = new Person("Hans", 17, emptyList());
    private Person irina = new Person("Irina", 21, singletonList(Country.ITALY));
    private Person jan = new Person("Jan", 41, Arrays.asList(Country.GERMANY, Country.FRANCE));
    private Person karen = new Person("Karen", 77, Arrays.asList(Country.FRANCE, Country.ENGLAND, Country.GERMANY, Country.CANADA, Country.RUSSIA));
    private Person luis = new Person("Luis", 28, Arrays.asList(Country.SPAIN, Country.SPAIN, Country.SPAIN, Country.SPAIN));

    private List<Person> people = Arrays.asList(alice, bob, charline, danny, elena, franck, gina, hans, irina, jan, karen, luis);

    
    /**
     * EXAMPLE 1: Find all people who have visited CUBA
     */
    
    @Test
    public void filter_a_collection_without_stream_api() {
        // act
        List<Person> cubaVisitors = new ArrayList<>();
        for (Person person: people) {
            if (person.hasVisited(Country.CUBA)) {
                cubaVisitors.add(person);
            }
        }

        // assert
        List<Person> expectedCubaVisitors = Arrays.asList(franck, gina);
        assertThat(cubaVisitors).isEqualTo(expectedCubaVisitors);
    }

    @Test
    public void filter_a_collection() {
        // act
        List<Person> cubaVisitors = people.stream().filter(person -> person.hasVisited(Country.CUBA)).collect(Collectors.toList());

        // assert
        List<Person> expectedCubaVisitors = Arrays.asList(franck, gina);
        assertThat(cubaVisitors).isEqualTo(expectedCubaVisitors);
    }



    /**
     * EXAMPLE 2: Find the sum of all visited countries by people over 33
     */

    @Test
    public void sum_of_all_visited_countries_without_stream_api() {
        // act
        int allVisitedCountriesByPeopleOver33 = 0;
        for (Person person: people) {
            if (person.getAge() > 33) {
                allVisitedCountriesByPeopleOver33 += person.getNumberOfVisitedCountries();
            }
        }

        // assert
        assertThat(allVisitedCountriesByPeopleOver33).isEqualTo(22);
    }

    @Test
    public void sum_of_all_visited_countries() {
        // act
        int allVisitedCountriesByPeopleOver33 = people.stream()
                .filter(person -> person.getAge() > 33)
                .map(Person::getNumberOfVisitedCountries)
                .reduce(0, Integer::sum);

        // assert
        assertThat(allVisitedCountriesByPeopleOver33).isEqualTo(22);
    }


    /**
     * EXAMPLE 3: Find average age of people who have visited SPAIN
     */
    
    @Test
    public void compute_average_age_of_people_visiting_spain_without_stream_api() {
        // act
        List<Person> spainVisitors = new ArrayList<>();
        for (Person person: people) {
            if (person.hasVisited(Country.SPAIN)) {
                spainVisitors.add(person);
            }
        }

        int sumAgeOfSpainVisitors = 0;
        for (Person spainVisitor: spainVisitors)  {
            sumAgeOfSpainVisitors += spainVisitor.getAge();
        }
        
        double averageAgeOfSpainVisitors = (double) sumAgeOfSpainVisitors / spainVisitors.size();

        // assert
        assertThat(averageAgeOfSpainVisitors).isEqualTo(32.6); // AVG(26, 33, 22, 54, 28) = 32.6
    }

    @Test
    public void compute_average_age_of_people_visiting_spain() {
        // act
        double averageAgeOfSpainVisitors = people.stream()
                .filter(person -> person.hasVisited(Country.SPAIN))
                .collect(Collectors.averagingDouble(Person::getAge));

        // assert
        assertThat(averageAgeOfSpainVisitors).isEqualTo(32.6); // AVG(26, 33, 22, 54, 28) = 32.6
    }

    
}
