package feasibility;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BasicAuthInterceptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.Getter;
import org.aktin.broker.client.live.CLIClientPluginConfiguration;
import org.aktin.broker.client2.BrokerClient2;
import org.aktin.broker.client2.validator.RequestValidatorFactory;

@Getter
public class FeasibilityExecutionPlugin extends CLIClientPluginConfiguration<FeasibilityExecutionService>{

	private RequestValidatorFactory requestValidation;
	private FlareExecutor flareExecutor;
	private CqlExecutor cqlExecutor;
	protected ResultObfuscator resultObfuscator;

	public FeasibilityExecutionPlugin(InputStream in) throws IOException {
		super(in);
	}

	@Override
	protected void loadConfig(Properties properties) throws IOException{
		// load any extra configuration parameters here.

		this.resultObfuscator = new ResultObfuscator(1,0.5);

		String flareBaseUrl = properties.getProperty("plugin.feasibility.flare.url");
		String fhirBaseUrl = properties.getProperty("plugin.feasibility.cql.fhirbaseurl");

		this.requestValidation = loadValidatorFactory(properties);

		// Set up FLARE execution

		String flareAuthUser = properties.getProperty("plugin.feasibility.flare.user");
		String flareAuthPw = properties.getProperty("plugin.feasibility.flare.pw");
		this.flareExecutor = new FlareExecutor(flareBaseUrl, flareAuthUser, flareAuthPw);


		// Set up CQL execution
		FhirContext fhirContext = FhirContext.forR4();
		IGenericClient genFhirClient = fhirContext.newRestfulGenericClient(fhirBaseUrl);

		String fhirUserCql = properties.getProperty("plugin.feasibility.cql.fhiruser");
		String fhirPwCql = properties.getProperty("plugin.feasibility.cql.fhirpw");

		if(! fhirUserCql.equals("") && ! fhirPwCql.equals("")) {
			IClientInterceptor authInterceptor = new BasicAuthInterceptor(fhirUserCql, fhirPwCql);
			genFhirClient.registerInterceptor(authInterceptor);
		}

		this.cqlExecutor = new CqlExecutor(
				new FhirConnector(fhirContext.newRestfulGenericClient(fhirBaseUrl)),
				new FhirHelper(fhirContext)
		);

	}

	@Override
	protected FeasibilityExecutionService createService(BrokerClient2 client) {
		return new FeasibilityExecutionService(client, this);
	}

}
