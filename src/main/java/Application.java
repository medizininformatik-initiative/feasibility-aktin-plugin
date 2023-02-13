import java.io.IOException;
import java.net.URISyntaxException;
import org.aktin.broker.client.live.CLI;

public class Application {


  public static void main(String[] args) throws IOException, URISyntaxException {

    System.out.println("Running my awesome aktin Plugin");
    CLI.main(args);

    System.exit(0);
  }
}
