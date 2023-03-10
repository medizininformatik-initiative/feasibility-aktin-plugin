package feasibility;

import org.aktin.broker.client.live.CLIExecutionService;
import org.aktin.broker.client2.BrokerClient2;

public class FeasibilityExecutionService extends CLIExecutionService<FeasibilityExecution>{
	private final FeasibilityExecutionPlugin config;

	public FeasibilityExecutionService(BrokerClient2 client, FeasibilityExecutionPlugin config) {
		super(client, config);
		this.config = config;
	}

	@Override
	protected FeasibilityExecution initializeExecution(Integer requestId) {
		return new FeasibilityExecution(requestId, config);
	}

}
