package feasibility;

import java.security.SecureRandom;
import java.util.HashMap;

public class ResultObfuscator {

  private static HashMap<Long, Double> obfuscationNumMap = new HashMap();
  private static double sensitivity = 1;
  private static double epsilon = 0.5;

  public static long obfuscateResult(Long result){

    Double obfuscationNum = obfuscationNumMap.get(result);

    if (obfuscationNum == null){
        obfuscationNum = SamplyLaplace.laplace(0, sensitivity, epsilon, new SecureRandom());
    }

    obfuscationNumMap.put(result, obfuscationNum);

    return SamplyLaplace.privatize(result, obfuscationNum);

  }

}
