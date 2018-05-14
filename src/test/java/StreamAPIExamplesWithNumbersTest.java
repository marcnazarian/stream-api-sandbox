import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamAPIExamplesWithNumbersTest {

    @Test
    public void _1_1_min_max_of_a_number_collection_without_stream_api() {
        List<Integer> numbers = Arrays.asList(12, 42, 55, 3, 121, 666, 202);
        
        Integer min = Integer.MAX_VALUE; // ??? what if list is empty?
        Integer max = Integer.MIN_VALUE; // ??? what if list is empty?
        for (Integer number: numbers) {
            if (number < min) {
                min = number;
            }
            if (number > max) {
                max = number;
            }
        }

        assertThat(min).isEqualTo(3);
        assertThat(max).isEqualTo(666);
    }

    @Test
    public void _1_2_min_max_of_a_number_collection() {
        List<Integer> numbers = Arrays.asList(12, 42, 55, 3, 121, 666, 202);
        
        Integer min = numbers.stream().min(Integer::compareTo).get();
        Integer max = numbers.stream().max(Integer::compareTo).get();

        assertThat(min).isEqualTo(3);
        assertThat(max).isEqualTo(666);
    }

    @Test
    public void _1_3_min_max_of_an_empty_collection() {
        List<Integer> numbers = Collections.emptyList();
        
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);

        assertThat(min).isEqualTo(Optional.empty());
        assertThat(max).isEqualTo(Optional.empty());
    }
    
    @Test
    public void _2_1_find_all_even_numbers_without_stream_api() {
        List<Integer> numbers = Arrays.asList(12, 42, 55, 3, 121, 666, 202);

        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer number: numbers) {
            if (number % 2 == 0) {
                evenNumbers.add(number);
            }
        }

        assertThat(evenNumbers).isEqualTo(Arrays.asList(12, 42, 666, 202));
    }

    @Test
    public void _2_2_find_all_even_numbers() {
        List<Integer> numbers = Arrays.asList(12, 42, 55, 3, 121, 666, 202);

        List<Integer> evenNumbers = numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());

        assertThat(evenNumbers).isEqualTo(Arrays.asList(12, 42, 666, 202));
    }

    @Test
    public void _3_1_compute_sum_of_square_of_even_numbers_without_stream_api() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        int sumOfSquares = 0;
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                sumOfSquares += number * number;
            }
        }

        assertThat(sumOfSquares).isEqualTo(56);
    }

    @Test
    public void _3_2_compute_sum_of_square_of_even_numbers() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        int sumOfSquare = numbers.stream()
                .filter(number -> number % 2 == 0)
                .map(number -> number * number)
                .reduce(0, Integer::sum);

        assertThat(sumOfSquare).isEqualTo(56);
    }

}
