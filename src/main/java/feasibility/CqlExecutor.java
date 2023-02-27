package feasibility;

import java.io.IOException;
import java.util.UUID;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.MeasureReport;

public class CqlExecutor {

  FhirHelper fhirHelper;
  FhirConnector fhirConnector;

  public CqlExecutor(FhirConnector fhirConnector, FhirHelper fhirHelper) {
    this.fhirConnector = fhirConnector;
    this.fhirHelper = fhirHelper;
  }

  public int evaluateCql(String cql) throws IOException {

    var libraryUri = "urn:uuid" + UUID.randomUUID();
    var measureUri = "urn:uuid" + UUID.randomUUID();

    Bundle bundle = fhirHelper.createBundle(cql, libraryUri, measureUri);
    fhirConnector.transmitBundle(bundle);
    MeasureReport measureReport = fhirConnector.evaluateMeasure(measureUri);

    return measureReport.getGroupFirstRep().getPopulationFirstRep().getCount();
  }
}
