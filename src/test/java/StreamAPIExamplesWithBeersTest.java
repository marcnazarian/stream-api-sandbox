import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamAPIExamplesWithBeersTest {

    private User alice = new User("Alice", 26, Arrays.asList(Beer.SAISON_DUPONT_BIO, Beer.CHIMAY_DOREE, Beer.PUNK_IPA));
    private User bob = new User("Bob", 42, emptyList());
    private User charline = new User("Charline", 33, Arrays.asList(Beer.PUNK_IPA, Beer.MANDRIN_CHARTREUSE, Beer.KERNEL_IPA));
    private User danny = new User("Danny", 19, singletonList(Beer.MAREDSOUS));
    private User elena = new User("Elena", 22, Arrays.asList(Beer.PUNK_IPA, Beer.KERNEL_IPA, Beer.LONDON_PORTER, Beer.ST_BERNARDUS, Beer.MANDRIN_CHARTREUSE));
    private User franck = new User("Franck", 38, Arrays.asList(Beer.MANDRIN_CHARTREUSE, Beer.MARKUS_BRUNE));
    private User gina = new User("Gina", 54, Arrays.asList(Beer.MANDRIN_CHARTREUSE, Beer.LONDON_PORTER, Beer.KERNEL_IPA, Beer.PUNK_IPA, Beer.MAREDSOUS, Beer.CHIMAY_DOREE, Beer.SAISON_DUPONT_BIO, Beer.MARKUS_BRUNE, Beer.ST_BERNARDUS, Beer.DUVEL, Beer.BLANCHE_DE_BRUXELLES, Beer.MOINETTE, Beer.TRIPLE_KARMELIET));
    private User hans = new User("Hans", 17, emptyList());
    private User irina = new User("Irina", 21, singletonList(Beer.KERNEL_IPA));
    private User jan = new User("Jan", 41, Arrays.asList(Beer.ST_BERNARDUS, Beer.MANDRIN_CHARTREUSE));
    private User karen = new User("Karen", 77, Arrays.asList(Beer.MANDRIN_CHARTREUSE, Beer.LONDON_PORTER, Beer.BLANCHE_DE_BRUXELLES, Beer.TRIPLE_KARMELIET));
    private User luis = new User("Luis", 28, Arrays.asList(Beer.PUNK_IPA, Beer.PUNK_IPA, Beer.PUNK_IPA, Beer.PUNK_IPA));

    private List<User> users = Arrays.asList(alice, bob, charline, danny, elena, franck, gina, hans, irina, jan, karen, luis);

    
    /**
     * EXAMPLE 1: Find all users who liked MARKUS_BRUNE
     */
    
    @Test
    public void _1_1_filter_a_collection_without_stream_api() {

        List<User> markusBruneFans = new ArrayList<>();
        for (User user: users) {
            if (user.doesLike(Beer.MARKUS_BRUNE)) {
                markusBruneFans.add(user);
            }
        }

        assertThat(markusBruneFans).isEqualTo(Arrays.asList(franck, gina));
    }

    @Test
    public void _1_2_filter_a_collection() {

        List<User> markusBruneFans = users.stream()
                .filter(user -> user.doesLike(Beer.MARKUS_BRUNE))
                .collect(Collectors.toList());

        assertThat(markusBruneFans).isEqualTo(Arrays.asList(franck, gina));
    }

    @Test
    public void _1_3_filter_a_collection_multiple_filters_and_sort() {

        List<User> topBeerFans = users.stream()
                .filter(user -> user.getAge() >= 18)
                .filter(user -> user.getName().toLowerCase().contains("a"))
                .sorted(Comparator.comparing(User::getNumberOfFavouriteBeers).reversed())
                .collect(Collectors.toList());

        assertThat(topBeerFans).doesNotContain(bob); // no A in name and 0 beers
        assertThat(topBeerFans).doesNotContain(hans); // under 18 and 0 beers
        assertThat(topBeerFans).doesNotContain(luis); // no A in name
        assertThat(topBeerFans).isEqualTo(Arrays.asList(gina, elena, karen, alice, charline, franck, jan, danny, irina));
    }

    @Test
    public void _1_4_filter_a_collection_multiple_filters_and_sort_and_collect_in_map() {
        Map<String, Integer> topBeerFansMap = users.stream()
                .filter(user -> user.getAge() >= 18)
                .filter(user -> user.getName().toLowerCase().contains("a"))
                .sorted(Comparator.comparing(User::getNumberOfFavouriteBeers).reversed())
                .collect(Collectors.toMap(User::getName, User::getNumberOfFavouriteBeers, (oldKey, newKey) -> oldKey, LinkedHashMap::new));

        assertThat(topBeerFansMap.get("Gina")).isEqualTo(13);
        assertThat(topBeerFansMap.get("Franck")).isEqualTo(2);
        assertThat(topBeerFansMap.get("Irina")).isEqualTo(1);
    }


    /**
     * EXAMPLE 2: Check that nobody liked KRO
     */

    @Test
    public void _2_1_check_that_nobody_liked_kro_without_stream_api_with_for_loop() {

        boolean noOneLikedKro = true;
        for (User user: users) {
            if (user.doesLike(Beer.KRO)) {
                noOneLikedKro = false;
            }
        }

        assertThat(noOneLikedKro).isTrue();
    }

    @Test
    public void _2_2_check_that_nobody_liked_kro_without_stream_api_with_while() {

        boolean noOneLikedKro = true;
        Iterator<User> userIterator = users.iterator();
        while (noOneLikedKro && userIterator.hasNext()) {
            User user = userIterator.next();
            if (user.doesLike(Beer.KRO)) {
                noOneLikedKro = false;
            }
        }

        assertThat(noOneLikedKro).isTrue();
    }

    @Test
    public void _2_3_check_that_nobody_liked_kro() {
        
        boolean noOneLikedKro = users.stream()
                .noneMatch(user -> user.doesLike(Beer.KRO));

        assertThat(noOneLikedKro).isTrue();
    }


    /**
     * EXAMPLE 3: Compute all email addresses of users
     */

    @Test
    public void _3_1_apply_operation_on_all_elements_without_stream_api() {

        List<String> emails = new ArrayList<>();
        for (User user: users) {
            String email = user.getName().toLowerCase() + "@unepetitemousse.fr";
            emails.add(email);
        }

        assertThat(emails.get(0)).isEqualTo("alice@unepetitemousse.fr");
        assertThat(emails.size()).isEqualTo(12);
        assertThat(emails.get(11)).isEqualTo("luis@unepetitemousse.fr");

    }

    @Test
    public void _3_2_apply_operation_on_all_elements() {

        List<String> emails = users.stream()
                .map(user -> user.getName().toLowerCase() + "@unepetitemousse.fr")
                .collect(Collectors.toList());

        assertThat(emails.get(0)).isEqualTo("alice@unepetitemousse.fr");
        assertThat(emails.size()).isEqualTo(12);
        assertThat(emails.get(11)).isEqualTo("luis@unepetitemousse.fr");
    }

    @Test
    public void _3_3_apply_operation_on_all_elements_chain_map() {

        List<String> emails = users.stream()
                .map(user -> user.getName())
                .map(username -> username.toLowerCase())
                .map(username -> username.concat("@unepetitemousse.fr"))
                .collect(Collectors.toList());

        assertThat(emails.get(0)).isEqualTo("alice@unepetitemousse.fr");
        assertThat(emails.size()).isEqualTo(12);
        assertThat(emails.get(11)).isEqualTo("luis@unepetitemousse.fr");
    }

    /**
     * EXAMPLE 4: Find average age of users who like PUNK_IPA
     */
    
    @Test
    public void _4_1_compute_average_age_of_people_who_like_punk_ipa_without_stream_api() {

        // build a list of users who liked Punk IPA
        List<User> punkIpaFans = new ArrayList<>();
        for (User user: users) {
            if (user.doesLike(Beer.PUNK_IPA)) {
                punkIpaFans.add(user);
            }
        }

        // compute sum of users who liked Punk IPA
        int sumAgeOfPunkIpaFans = 0;
        for (User punkIpaFan: punkIpaFans)  {
            sumAgeOfPunkIpaFans += punkIpaFan.getAge();
        }

        // compute the average age of users who liked Punk IPA
        double averageAgeOfPunkIpaFans = (double) sumAgeOfPunkIpaFans / punkIpaFans.size();

        // assert
        assertThat(averageAgeOfPunkIpaFans).isEqualTo(32.6); // AVG(26, 33, 22, 54, 28) = 32.6
    }

    @Test
    public void _4_2_compute_average_age_of_people_who_like_punk_ipa() {

        double averageAgeOfPunkIpaFans = users.stream()
                .filter(user -> user.doesLike(Beer.PUNK_IPA))
                .collect(Collectors.averagingDouble(User::getAge));

        assertThat(averageAgeOfPunkIpaFans).isEqualTo(32.6); // AVG(26, 33, 22, 54, 28) = 32.6
    }

    
}
