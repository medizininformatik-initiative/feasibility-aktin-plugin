
import static org.junit.Assert.assertTrue;

import feasibility.ResultObfuscator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ResultObfuscatorTest {


  ResultObfuscator resultObfuscator;

  @BeforeEach
  public void setUp() {
    this.resultObfuscator = new ResultObfuscator(1, 0.5);
  }

  @Test
  public void obfuscateResult() {

    //TODO - create proper obfuscation tests

    Long resultsToObfuscate[] = {0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 1823L, 123L, 1321L,12321L};

      System.out.println(resultObfuscator.obfuscateResult(resultsToObfuscate[0]));
  }

  @RepeatedTest(1000)
  public void testObfuscateNeverReturnsNegativeValuesSensitivityOne() {
    long leftLimit = 0L;
    long rightLimit = 10L;
    long generatedLong =  leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    assertTrue(0 <= resultObfuscator.obfuscateResult(Long.valueOf(generatedLong)));
  }


}
