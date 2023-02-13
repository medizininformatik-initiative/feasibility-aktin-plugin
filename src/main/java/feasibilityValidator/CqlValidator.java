package feasibilityValidator;

import java.io.IOException;
import java.io.InputStream;
import org.aktin.broker.client2.validator.RequestValidator;
import org.aktin.broker.client2.validator.ValidationError;

public class CqlValidator implements RequestValidator {

  @Override
  public void validate(InputStream inputStream)
      throws ValidationError, IOException {
  }
}
