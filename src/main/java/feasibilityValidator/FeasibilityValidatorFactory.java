package feasibilityValidator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.SneakyThrows;
import org.aktin.broker.client2.validator.RequestValidator;
import org.aktin.broker.client2.validator.RequestValidatorFactory;
import org.aktin.broker.client2.validator.ValidationError;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Validates content to be valid JSON. Needs {@code org.json} library.
 * @author R.W.Majeed
 *
 */
public class FeasibilityValidatorFactory implements RequestValidatorFactory, RequestValidator {


  @SneakyThrows
  @Override
  public RequestValidator getValidator(String mediaType) throws IllegalArgumentException {

    switch (mediaType) {
      case "text/cql" : return new CqlValidator();
      case "application/sq+json": return new SqValidator();
      default: return this;
    }
  }

  @Override
  public void validate(InputStream in) throws IOException, ValidationError {

  }

}
