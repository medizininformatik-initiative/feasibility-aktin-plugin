package feasibility;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FlareExecutor {

  protected static final String CONTENT_TYPE = "Content-Type";
  protected static final String SQ_CONTENT_TYPE = "application/sq+json";

  private final String flareBaseUrl;
  private final String basicAuthUser;
  private final String basicAuthPw;

  public FlareExecutor(String flareBaseUrl, String basicAuthUser, String basicAuthPw) {
    this.flareBaseUrl = flareBaseUrl;
    this.basicAuthUser = basicAuthUser;
    this.basicAuthPw = basicAuthPw;

  }

  HttpClient httpClient = HttpClient.newBuilder().version(Version.HTTP_1_1).build();

  public String evaluateSq(String sq) throws IOException {

    try {

      String valueToEncode = this.basicAuthUser + ":" + this.basicAuthPw;
      String authHeader =  "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());

      HttpRequest sqRequest = HttpRequest.newBuilder().uri(new URI(flareBaseUrl))
          .header(CONTENT_TYPE, SQ_CONTENT_TYPE)
          .header("Authorization", authHeader)
          .POST(BodyPublishers.ofString(sq, StandardCharsets.UTF_8))
          .build();

      HttpResponse flareResult = httpClient.send(sqRequest, BodyHandlers.ofString());

      return flareResult.body().toString();

    } catch (Exception e) {
      throw new IOException("An error while evaluating sq on flare " , e);
    }
  }

}
