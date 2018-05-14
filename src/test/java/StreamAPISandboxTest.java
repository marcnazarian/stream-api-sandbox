import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamAPISandboxTest {

    private Person alice = new Person("Alice", 26, Arrays.asList(Country.PERU, Country.COSTA_RICA, Country.SPAIN));
    private Person bob = new Person("Bob", 42, emptyList());
    private Person charline = new Person("Charline", 33, Arrays.asList(Country.SPAIN, Country.FRANCE, Country.ITALY));
    private Person danny = new Person("Danny", 19, singletonList(Country.AUSTRALIA));
    private Person elena = new Person("Elena", 22, Arrays.asList(Country.SPAIN, Country.ITALY, Country.ENGLAND, Country.GERMANY, Country.FRANCE));
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
    public void _1_1_filter_a_collection_without_stream_api() {

        List<Person> cubaVisitors = new ArrayList<>();
        for (Person person: people) {
            if (person.hasVisited(Country.CUBA)) {
                cubaVisitors.add(person);
            }
        }

        assertThat(cubaVisitors).isEqualTo(Arrays.asList(franck, gina));
    }

    @Test
    public void _1_2_filter_a_collection() {

        List<Person> cubaVisitors = people.stream()
                .filter(person -> person.hasVisited(Country.CUBA))
                .collect(Collectors.toList());

        assertThat(cubaVisitors).isEqualTo(Arrays.asList(franck, gina));
    }


    /**
     * EXAMPLE 2: Check that nobody visited Antarctica
     */

    @Test
    public void _2_1_check_that_nobody_visited_antarctica_without_stream_api_with_for_loop() {

        boolean noOneVisitedAntarctica = true;
        for (Person person: people) {
            if (person.hasVisited(Country.ANTARCTICA)) {
                noOneVisitedAntarctica = false;
            }
        }

        assertThat(noOneVisitedAntarctica).isTrue();
    }

    @Test
    public void _2_2_check_that_nobody_visited_antarctica_without_stream_api_with_while() {

        boolean noOneVisitedAntarctica = true;
        Iterator<Person> peopleIterator = people.iterator();
        while (noOneVisitedAntarctica && peopleIterator.hasNext()) {
            Person person = peopleIterator.next();
            if (person.hasVisited(Country.ANTARCTICA)) {
                noOneVisitedAntarctica = false;
            }
        }

        assertThat(noOneVisitedAntarctica).isTrue();
    }

    @Test
    public void _2_3_check_that_nobody_visited_antarctica() {
        
        boolean noOneVisitedAntarctica = people.stream()
                .noneMatch(person -> person.hasVisited(Country.ANTARCTICA));

        assertThat(noOneVisitedAntarctica).isTrue();
    }


    /**
     * EXAMPLE 3: Find the sum of all visited countries by people over 33
     */

    @Test
    public void _3_1_sum_of_all_visited_countries_without_stream_api() {

        int allVisitedCountriesByPeopleOver33 = 0;
        for (Person person: people) {
            if (person.getAge() > 33) {
                allVisitedCountriesByPeopleOver33 += person.getNumberOfVisitedCountries();
            }
        }

        assertThat(allVisitedCountriesByPeopleOver33).isEqualTo(22);
    }

    @Test
    public void _3_2_sum_of_all_visited_countries() {

        int allVisitedCountriesByPeopleOver33 = people.stream()
                .filter(person -> person.getAge() > 33)
                .map(Person::getNumberOfVisitedCountries)
                .reduce(0, Integer::sum);

        assertThat(allVisitedCountriesByPeopleOver33).isEqualTo(22);
    }


    /**
     * EXAMPLE 4: Find average age of people who have visited SPAIN
     */
    
    @Test
    public void _4_1_compute_average_age_of_people_visiting_spain_without_stream_api() {

        // build a list of people who visited spain
        List<Person> spainVisitors = new ArrayList<>();
        for (Person person: people) {
            if (person.hasVisited(Country.SPAIN)) {
                spainVisitors.add(person);
            }
        }

        // compute sum of people who visited spain
        int sumAgeOfSpainVisitors = 0;
        for (Person spainVisitor: spainVisitors)  {
            sumAgeOfSpainVisitors += spainVisitor.getAge();
        }

        // compute the average age of people who visited spain
        double averageAgeOfSpainVisitors = (double) sumAgeOfSpainVisitors / spainVisitors.size();

        // assert
        assertThat(averageAgeOfSpainVisitors).isEqualTo(32.6); // AVG(26, 33, 22, 54, 28) = 32.6
    }

    @Test
    public void _4_2_compute_average_age_of_people_visiting_spain() {

        double averageAgeOfSpainVisitors = people.stream()
                .filter(person -> person.hasVisited(Country.SPAIN))
                .collect(Collectors.averagingDouble(Person::getAge));

        assertThat(averageAgeOfSpainVisitors).isEqualTo(32.6); // AVG(26, 33, 22, 54, 28) = 32.6
    }


    
//    /**
//     * EXAMPLE 5: Find most popular countries
//     */
//
//    @Test
//    public void _5_1_find_countries_with_most_visitors_without_stream_api() {
//        // act
//        // init map
//        Map<Country, Integer> mostPopularCountries = new HashMap<>();
//        for (Person person : people) {
//            for (Country country : Country.values()) {
//                if (person.hasVisited(country)) {
//                    mostPopularCountries.put(country, mostPopularCountries.getOrDefault(country, 0) + 1);
//                }
//            }
//        }
//
//        // get most popular country
//        Country mostPopularCountry = null;
//        int numberOfVisitsOfMostPopularCountries = 0;
//        for (Country country : mostPopularCountries.keySet()) {
//            if (mostPopularCountries.get(country) > numberOfVisitsOfMostPopularCountries) {
//                mostPopularCountry = country;
//                numberOfVisitsOfMostPopularCountries = mostPopularCountries.get(country);
//            }
//        }
//
//        // assert
//        assertThat(mostPopularCountry).isEqualTo(Country.FRANCE);
//        assertThat(numberOfVisitsOfMostPopularCountries).isEqualTo(6);
//    }
//
//    @Test
//    public void _5_2_find_countries_with_most_visitors() {
//        
//    }
    
}
