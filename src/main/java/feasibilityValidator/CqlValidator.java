package feasibilityValidator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.aktin.broker.client2.validator.RequestValidator;
import org.aktin.broker.client2.validator.ValidationError;
import org.cqframework.cql.cql2elm.CqlCompilerException;
import org.cqframework.cql.cql2elm.CqlTranslator;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.cqframework.cql.cql2elm.ModelResolver;
import org.cqframework.cql.cql2elm.quick.FhirLibrarySourceProvider;

public class CqlValidator implements RequestValidator {


  @Override
  public void validate(InputStream inputStream)
      throws ValidationError, IOException {

    String cqlString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

    ModelManager modelManager = new ModelManager();
    LibraryManager libraryManager = new LibraryManager(modelManager);
    libraryManager.getLibrarySourceLoader().registerProvider(new FhirLibrarySourceProvider());

    CqlTranslator
        .fromText(cqlString, modelManager , libraryManager)
        .getErrors()
        .stream()
        .forEach((e) -> System.out.println(e.getMessage()));

    /*if (! CqlTranslator
        .fromText(cqlString,modelManager , new LibraryManager(modelManager))
        .getErrors().isEmpty()){

        throw new IOException("CQL verification failed");
    }*/

  }
}
