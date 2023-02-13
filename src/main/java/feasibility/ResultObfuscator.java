package feasibility;

public class ResultObfuscator {

  public static int obfuscateResult(int result){
    var obfuscatedResult = result + ( 10 - (result % 10));
    return obfuscatedResult;
  }

}
