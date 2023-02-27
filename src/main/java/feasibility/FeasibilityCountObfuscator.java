package feasibility;

import java.security.SecureRandom;

public class FeasibilityCountObfuscator implements ResultObfuscator<Long>{

  final static int MAX_RANDOM_OBFUSCATOR_VALUE = 10;
  private static final Integer MIN_ALLOWED_OBFUSCATED_RESULT = 5;
  SecureRandom secRand = new SecureRandom();

  /**
   * Obfuscates the given feasibility count by randomly adding a value in the range of [-5, 5] to it.
   * <p>
   * Important:
   * <b>Returns 0, should the obfuscated result be less than 5.</b>
   * </p>
   *
   * @param result The feasibility count that shall be obfuscated.
   * @return The obfuscated feasibility count.
   */
  public Long obfuscateResult(Long result){
    int obfuscationInt = secRand.nextInt(MAX_RANDOM_OBFUSCATOR_VALUE + 1);
    var obfuscatedFeasibilityCount =result + obfuscationInt - (MAX_RANDOM_OBFUSCATOR_VALUE / 2);
    return (obfuscatedFeasibilityCount < MIN_ALLOWED_OBFUSCATED_RESULT) ? 0 : obfuscatedFeasibilityCount;
  }
}
