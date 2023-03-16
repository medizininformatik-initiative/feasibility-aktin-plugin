import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import feasibility.FeasibilityCountObfuscator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

public class FeasibilityCountObfuscatorTest {


  FeasibilityCountObfuscator resultObfuscator;

  @BeforeEach
  public void setUp() {
    this.resultObfuscator = new FeasibilityCountObfuscator();
  }

  @RepeatedTest(1000)
  public void testObfuscateNeverReturnsNegativeValuesSensitivityOne() {
    long leftLimit = 0L;
    long rightLimit = 10L;
    long generatedLong =  leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    assertTrue(0 <= resultObfuscator.obfuscateResult(Long.valueOf(generatedLong)));
  }

  @RepeatedTest(1000)
  public void testObfuscateReturnsZeroIfLessThenFive() {
    long leftLimit = 0L;
    long rightLimit = 5L;
    long generatedLong =  leftLimit + (long) (Math.random() * (rightLimit - leftLimit));

    assertFalse(List.of(1L,2L,3L,4L).stream().anyMatch((notAllowed) -> notAllowed == resultObfuscator.obfuscateResult(Long.valueOf(generatedLong)).longValue()));

  }

}
