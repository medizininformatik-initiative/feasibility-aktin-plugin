import feasibilityValidator.CqlValidator;
import feasibilityValidator.SqValidator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import org.junit.Test;

public class FeasibilityValidatorTest {

  CqlValidator cqlValidator = new CqlValidator();
  SqValidator sqValidator = new SqValidator();

  public FeasibilityValidatorTest()
      throws ClassNotFoundException, NoSuchMethodException {
  }

  private static Path resourcePath(String name) throws URISyntaxException {
    return Paths.get(
        Objects.requireNonNull(FeasibilityValidatorTest.class.getResource(name)).toURI());
  }

  private static String slurp(String name) throws Exception {
    return Files.readString(resourcePath(name));
  }


  @Test
  public void acceptValidCql()
      throws Exception {
    String inputString = slurp("example-cql");

    assertDoesNotThrow(() -> cqlValidator.validate(
        new ByteArrayInputStream(inputString.getBytes(
            StandardCharsets.UTF_8))));
  }

  @Test
  public void rejectInvalidCql() {
    String inputString = "Invalid CQL";

    assertThrows(IOException.class, () -> cqlValidator.validate(
        new ByteArrayInputStream(inputString.getBytes(
            StandardCharsets.UTF_8))));
  }


  @Test
  public void acceptValidSq()
      throws Exception {
    String sqInputString = slurp("example-sq.json");

    assertDoesNotThrow(() -> sqValidator.validate(
        new ByteArrayInputStream(sqInputString.getBytes(
            StandardCharsets.UTF_8))));
  }

  @Test
  public void rejectCommandInputs() {
    String[] testInputStrings = {"*/*", "/*/*/*/*/../../../../*/*/*/*", "*"};

    for (String inputString: testInputStrings) {
      assertThrows(IOException.class, () -> sqValidator.validate(
          new ByteArrayInputStream(inputString.getBytes(
              StandardCharsets.UTF_8))));
      assertThrows(IOException.class, () -> cqlValidator.validate(
          new ByteArrayInputStream(inputString.getBytes(
              StandardCharsets.UTF_8))));
    }
  }

}
