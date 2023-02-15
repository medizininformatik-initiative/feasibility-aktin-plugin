package feasibility;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.security.SecureRandom;


public class ResultObfuscator {

  private final LoadingCache<Long, Long> cache;
  private double sensitivity = 1;
  private double epsilon = 0.5;


  public ResultObfuscator(double sensitivity, double epsilon){

    this.sensitivity = sensitivity;
    this.epsilon = epsilon;
    this.cache = Caffeine.newBuilder()
        .build(key -> load(key));
  }

  private Long load(Long key){
    return SamplyLaplace.privatize(key, SamplyLaplace.laplace(0, sensitivity, epsilon, new SecureRandom()));
  }

  public long obfuscateResult(Long result){
    return cache.get(result);
  }

}
