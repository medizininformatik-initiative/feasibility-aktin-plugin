import static org.junit.Assert.assertEquals;
import feasibility.SamplyLaplace;
import java.security.SecureRandom;
import org.junit.jupiter.api.RepeatedTest;

public class SamplyLaplaceTest {

  double sensitivity = 1;
  double epsilon = 0.01;

  @RepeatedTest(1000)
  public void ensurePrivatizedResultModTenIsZero() {
    long leftLimit = 0L;
    long rightLimit = 10L;
    long generatedLong =  leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    double laplaceResult = SamplyLaplace.laplace(0, sensitivity, epsilon, new SecureRandom());
    double finalPriv = SamplyLaplace.privatize(generatedLong, laplaceResult);
    assertEquals(0, finalPriv % 10, 0);

  }
}
