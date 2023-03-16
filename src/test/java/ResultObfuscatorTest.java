
import static org.junit.Assert.assertTrue;

import feasibility.CachingLaplaceCountObfuscator;
import feasibility.ResultObfuscator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.BeforeEach;

public class ResultObfuscatorTest {

  ResultObfuscator resultObfuscator;

  @BeforeEach
  public void setUp() {
    this.resultObfuscator = new CachingLaplaceCountObfuscator(1, 0.28);
  }

  @RepeatedTest(1000)
  public void testObfuscateNeverReturnsNegativeValuesSensitivityOne() {
    long leftLimit = 0L;
    long rightLimit = 10L;
    long generatedLong =  leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    assertTrue(0 <= (long) resultObfuscator.obfuscateResult(Long.valueOf(generatedLong)));
  }

}
