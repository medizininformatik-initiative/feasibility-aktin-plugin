package feasibility;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.MeasureReport;

public class CqlExecutor {

  protected static final String CONTENT_TYPE = "Content-Type";
  protected static final String SQ_CONTENT_TYPE = "application/sq+json";

  FhirHelper fhirHelper;
  FhirConnector fhirConnector;

  public CqlExecutor(FhirConnector fhirConnector, FhirHelper fhirHelper) {
    this.fhirConnector = fhirConnector;
    this.fhirHelper = fhirHelper;
  }

  public int evaluateCql(String cql) throws IOException {

    var libraryUri = "urn:uuid" + UUID.randomUUID();
    var measureUri = "urn:uuid" + UUID.randomUUID();
    MeasureReport measureReport;

    try {
      Bundle bundle = fhirHelper.createBundle(cql, libraryUri, measureUri);
      fhirConnector.transmitBundle(bundle);
      measureReport = fhirConnector.evaluateMeasure(measureUri);
    } catch (IOException e) {
      throw e;
    }

    var resultCount = measureReport.getGroupFirstRep().getPopulationFirstRep().getCount();
    return resultCount;
  }

}
