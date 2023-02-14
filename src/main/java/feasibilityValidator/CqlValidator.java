package feasibilityValidator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.aktin.broker.client2.validator.RequestValidator;
import org.aktin.broker.client2.validator.ValidationError;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.cqframework.cql.cql2elm.quick.FhirLibrarySourceProvider;

public class CqlValidator implements RequestValidator {

  public void validate(InputStream inputStream)
      throws IOException, ValidationError {

    String cqlString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

    ModelManager modelManager = new ModelManager();
    LibraryManager libraryManager = new LibraryManager(modelManager);
    libraryManager.getLibrarySourceLoader().registerProvider(new FhirLibrarySourceProvider());

    if  (! CqlTranslator
         .fromText(cqlString, modelManager , libraryManager)
         .getErrors().isEmpty()){
        throw new ValidationError(new Exception("CQL validation failed"));
    }

  }
}
