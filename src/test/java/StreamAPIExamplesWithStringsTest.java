import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamAPIExamplesWithStringsTest {
    
    @Test
    public void apply_lower_case_and_join_list_of_string_without_streams() {
        List<String> words = Arrays.asList("ABC", "Def", "gHI", "JKl", "mno", "pqR");

        String sentence = "";
        for (String word : words) {
            sentence += word.toLowerCase();
        }

        assertThat(sentence).isEqualTo("abcdefghijklmnopqr");
    }

    @Test
    public void apply_lower_case_and_join_list_of_string() {
        List<String> words = Arrays.asList("ABC", "Def", "gHI", "JKl", "mno", "pqR");

        String sentence = words.stream().map(String::toLowerCase).reduce("", String::concat);

        assertThat(sentence).isEqualTo("abcdefghijklmnopqr");
    }
    
}
