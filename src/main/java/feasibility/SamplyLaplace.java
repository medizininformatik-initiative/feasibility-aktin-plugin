package feasibility;
import java.security.SecureRandom;

/**
 * This class implements the laplacian mechanism.
 * @author Julian Gruendner
 *
 * Taken and changed from: https://github.com/samply/share-client/blob/master/src/main/java/de/samply/share/client/util/connector/LaplaceMechanism.java
 * @author Tobias Kussel, Deniz Tas, Alexander Kiel
 */

public class SamplyLaplace {

    /**
     * Permute a value with the (epsilon, 0) laplacian mechanism.
     *
     * @param value       clear value to permute
     * @param obfuscationNum number to obfuscate the result by
     * @return the permuted value
     */
    public static long privatize(long value, double obfuscationNum) {
        return Math.max(0,Math.round((value + obfuscationNum) / 10) * 10);
    }

    /**
     * Draw from a laplacian distribution.
     *
     * @param meanDist mean of distribution
     * divDist  diversity of distribution
     */
    public static double laplace(double meanDist, double sensitivity, double epsilon, SecureRandom rand) {
      double divDist = sensitivity / epsilon;
      double min = -0.5;
      double max = 0.5;
      double random = rand.nextDouble();
      double uniform = min + random * (max - min);
      return meanDist - divDist * Math.signum(uniform) * Math.log(1 - 2 * Math.abs(uniform));
    }
  }

