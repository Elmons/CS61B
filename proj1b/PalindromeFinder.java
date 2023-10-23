/** This class outputs all palindromes in the words file in the current directory. */
import org.junit.Test;
import static org.junit.Assert.*;

public class PalindromeFinder {
    @Test
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator cc = new OffByOne();
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, cc)) {
                System.out.println(word);
            }
        }
    } //Uncomment this class once you've written isPalindrome.
}
