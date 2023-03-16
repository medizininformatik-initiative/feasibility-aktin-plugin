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
	protected FeasibilityRateLimiter feasibilityRateLimiter;

	public FeasibilityExecutionPlugin(InputStream in) throws IOException {
		super(in);
	}

	@Override
	protected void loadConfig(Properties properties) throws IOException{

		var resultObfuscatorEpsilon = Double.valueOf(properties.getProperty("plugin.feasibility.obfuscator.epsilon"));
		this.resultObfuscator = new CachingLaplaceCountObfuscator(1,resultObfuscatorEpsilon);
		String flareBaseUrl = properties.getProperty("plugin.feasibility.flare.url");
		String fhirBaseUrl = properties.getProperty("plugin.feasibility.cql.fhirbaseurl");
		this.requestValidation = loadValidatorFactory(properties);

		int maxRequests = Integer.valueOf(properties.getProperty("plugin.feasibility.ratelimit.nmaxrequests"));
		int resetTimeMinutes = Integer.valueOf(properties.getProperty("plugin.feasibility.ratelimit.resettimeminutes"));
		this.feasibilityRateLimiter = new FeasibilityRateLimiter(maxRequests, resetTimeMinutes);
		// Set up FLARE execution
		String flareBaseUrl = properties.getProperty("plugin.feasibility.flare.url");
		String flareAuthUser = properties.getProperty("plugin.feasibility.flare.user");
		String flareAuthPw = properties.getProperty("plugin.feasibility.flare.pw");
		this.flareExecutor = new FlareExecutor(flareBaseUrl, flareAuthUser, flareAuthPw);

		// Set up CQL execution
		FhirContext fhirContext = FhirContext.forR4();
		String fhirBaseUrl = properties.getProperty("plugin.feasibility.cql.fhirbaseurl");
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
