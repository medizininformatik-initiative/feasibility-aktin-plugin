
import feasibility.ResultObfuscator;
import org.junit.Test;
public class ResultObfuscatorTest {

  @Test
  public void obfuscateResult() {

    //TODO - create proper obfuscation tests

    Long resultsToObfuscate[] = {0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 1823L, 123L, 1321L,12321L};

    for (Long result : resultsToObfuscate){
      System.out.println(ResultObfuscator.obfuscateResult(result));
    }

  }


}
