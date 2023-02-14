package feasibility;

public class ResultObfuscator {

  public static int obfuscateResult(int result){
    return result + ( 10 - (result % 10));
  }

}
