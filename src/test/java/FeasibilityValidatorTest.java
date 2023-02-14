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

  private static Path resourcePath(String name) throws URISyntaxException {
    return Paths.get(
        Objects.requireNonNull(FeasibilityValidatorTest.class.getResource(name)).toURI());
  }

  private static String slurp(String name) throws Exception {
    return Files.readString(resourcePath(name));
  }


  @Test
  public void acceptValidSq()
      throws Exception {

    SqValidator sqValidator = new SqValidator();

    String sqInputString = slurp("example-sq.json");

    assertDoesNotThrow(() -> sqValidator.validate(
        new ByteArrayInputStream(sqInputString.getBytes(
            StandardCharsets.UTF_8))));

  }

  @Test
  public void rejectCommandInputs()
      throws Exception {

    SqValidator sqValidator = new SqValidator();
    String[] testInputStrings = {"*/*", "/*/*/*/*/../../../../*/*/*/*", "*"};

    for (String inputString: testInputStrings) {
      assertThrows(IOException.class, () -> sqValidator.validate(
          new ByteArrayInputStream(inputString.getBytes(
              StandardCharsets.UTF_8))));
    }

  }

}
