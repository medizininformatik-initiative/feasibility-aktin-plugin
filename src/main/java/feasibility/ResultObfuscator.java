package feasibility;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.security.SecureRandom;

  /**
   * Describes how values of a type T can get obfuscated.
   *
   * @param <T> The type of the value that shall get obfuscated.
   */
  public interface ResultObfuscator<T> {

    /**
     * Obfuscates the specified value and returns the result.
     *
     * @param value Gets obfuscated.
     * @return The obfuscated value.
     */
    T obfuscateResult(T value);
  }