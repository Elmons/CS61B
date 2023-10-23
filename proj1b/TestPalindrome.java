import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } //Uncomment this class once you've created your Palindrome class.

    @Test
    public void testIsPalindrome() {
        String s1 = "abcccba";
        String s2 = "sdfsasdas";
        String s3 = "";
        String s4 = "x";
        assertTrue(palindrome.isPalindrome(s1));
        assertFalse(palindrome.isPalindrome(s2));
        assertTrue(palindrome.isPalindrome(s3));
        assertTrue(palindrome.isPalindrome(s4));
    }

    @Test
    public void testIsPalindromeCc() {
        String s1 = "abccdcb";
        String s2 = "sdfsasdas";
        String s3 = "";
        String s4 = "x";
        OffByOne cc = new OffByOne();
        assertTrue(palindrome.isPalindrome(s1, cc));
        assertFalse(palindrome.isPalindrome(s2, cc));
        assertTrue(palindrome.isPalindrome(s3, cc));
        assertTrue(palindrome.isPalindrome(s4, cc));
    }
}
