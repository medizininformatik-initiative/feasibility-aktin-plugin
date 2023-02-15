package feasibility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import lombok.extern.java.Log;
import org.aktin.broker.client.live.AbortableRequestExecution;
import org.aktin.broker.client2.validator.RequestValidatorFactory;
import org.aktin.broker.client2.validator.ValidationError;

@Log
public class FeasibilityExecution extends AbortableRequestExecution {
	private final FeasibilityExecutionPlugin config;
	private String requestBody;
	private String responseBody;

	public FeasibilityExecution(int requestId, FeasibilityExecutionPlugin config) {
		super(requestId);
		this.config = config;
	}

	@Override
	protected void prepareExecution() throws IOException {
		
		HttpResponse<String> def = client.getMyRequestDefinition(requestId, config.getRequestMediatype(), BodyHandlers.ofString());
		String body = def.body();

		RequestValidatorFactory validator = config.getRequestValidation();
		if( validator != null ) {
			try {
				validator.getValidator(config.getRequestMediatype()).validate(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
			} catch (Exception e) {
				this.responseBody = "{\"Request failed validation\"}";
				throw new IOException("Invalid request syntax", e);
			}
		}
		
		this.requestBody = body;
	}

	@Override
	protected void doExecution() throws IOException {

		long result = 0;

		if (config.getRequestMediatype().equals("text/cql")){
			log.info("Evaluating CQL against FHIR server, CQL evaluated is");
			log.info(requestBody);
			result = config.getCqlExecutor().evaluateCql(requestBody);
		} else if (config.getRequestMediatype().equals("application/sq+json")){
			log.info("Evaluating SQ with FLARE, SQ evaluated is");
			log.info(requestBody);
			String sqEvalResult = config.getFlareExecutor().evaluateSq(requestBody);
			result = Long.parseLong(sqEvalResult);
		}

		long obfuscatedResult = ResultObfuscator.obfuscateResult(result);
		log.info("Obfuscated SQ Result = " + obfuscatedResult);
		this.responseBody = String.valueOf(obfuscatedResult);

	}

	@Override
	protected void finishExecution() {
		reportCompleted();
	}

	@Override
	protected String getResultMediatype() {
		return "application/json";
	}

	@Override
	protected InputStream getResultData() {
		return new ByteArrayInputStream(this.responseBody.getBytes(StandardCharsets.UTF_8));
	}

}
