import static org.junit.Assert.assertTrue;

import feasibility.FeasibilityCountObfuscator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class FeasibilityCountObfuscatorTest {


  FeasibilityCountObfuscator resultObfuscator;

  @BeforeEach
  public void setUp() {
    this.resultObfuscator = new FeasibilityCountObfuscator();
  }

  @Test
  public void obfuscateResult() {

    //TODO - create proper obfuscation tests

    Long resultsToObfuscate[] = {0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 1823L, 123L, 1321L,12321L};

    for(Long result: resultsToObfuscate){
      System.out.println(result + "|" + resultObfuscator.obfuscateResult(result));
    }


  }

  @RepeatedTest(1000)
  public void testObfuscateNeverReturnsNegativeValuesSensitivityOne() {
    long leftLimit = 0L;
    long rightLimit = 10L;
    long generatedLong =  leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    assertTrue(0 <= resultObfuscator.obfuscateResult(Long.valueOf(generatedLong)));
  }


}
