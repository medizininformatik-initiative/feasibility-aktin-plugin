package feasibilityValidator;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.aktin.broker.client2.validator.RequestValidator;
import org.aktin.broker.client2.validator.ValidationError;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class SqValidator implements RequestValidator {


  private Constructor<?> jsonObjectConstructor;
  private Charset defaultCharset;

  public SqValidator() throws ClassNotFoundException, NoSuchMethodException {
    Class<?> jsonObjectClass = Class.forName("org.json.JSONObject");
    jsonObjectConstructor = jsonObjectClass.getConstructor(String.class);
    defaultCharset = StandardCharsets.UTF_8;
  }

  private static String slurp(String name) throws Exception {
    Path schemaPath = Path.of(name);
    return Files.readString(schemaPath);
  }

  @Override
  public void validate(InputStream in)
      throws ValidationError, IOException {

    String sq_input = new String(in.readAllBytes(), defaultCharset);
    try {
      jsonObjectConstructor.newInstance(sq_input);

      JSONObject sq_object = new JSONObject(sq_input);

      // validation successful. input parsed as JSON array.
      var jsonSchema = new JSONObject(new JSONTokener(slurp("/Users/juliangruendner/phd/code/mii-feasibility/feasibility-aktin/src/test/resources/sq-schema.json")));


      SchemaLoader loader = SchemaLoader.builder()
          .schemaJson(jsonSchema)
          .draftV7Support()
          .build();
      var schema = loader.load().build();

      schema.validate(sq_object);

      return;
    } catch (Exception e) {
      if( e.getClass().getName().equals("org.json.JSONException") ) {
        // validation failed, try parsing JSON array
        throw new ValidationError(e);
      }
      else if (e.getClass().getName().equals("org.everit.json.schema.ValidationException")){
        throw new ValidationError(e);
      }else {
        throw new IOException("Unexpected error during JSON validation", e);
      }
    }

  }
}
