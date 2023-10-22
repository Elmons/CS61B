import org.junit.Test;
import static org.junit.Assert.*;

/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }
    @Test
    public void testIsSameNumber(){
        int a = 127;
        int b = 127;
        assertTrue(isSameNumber(a,b));
        a = 128;
        b = 128;
        assertTrue(isSameNumber(a,b));
    }
}
