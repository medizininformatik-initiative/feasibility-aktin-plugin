import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

public class FeasibilityExecutionTest {


  @Test
  public void simpleTest(){

    System.out.println(exampleCql);

  }

    String exampleCql = """
        library Retrieve
        using FHIR version '4.0.0'
        include FHIRHelpers version '4.0.0'

        codesystem frailty-score: 'https://www.netzwerk-universitaetsmedizin.de/fhir/CodeSystem/frailty-score'
        codesystem icd10: 'http://fhir.de/CodeSystem/dimdi/icd-10-gm'
        codesystem loinc: 'http://loinc.org'
        codesystem snomed: 'http://snomed.info/sct'
        codesystem ver_status: 'http://terminology.hl7.org/CodeSystem/condition-ver-status'
                        
        context Patient
                        
        define Inclusion:
          exists from [Observation: Code '713636003' from snomed] O
            where O.value.coding contains Code '1' from frailty-score or
              O.value.coding contains Code '2' from frailty-score
                        
        define Exclusion:
          exists from [Condition: Code '13645005' from snomed] C
            where C.verificationStatus.coding contains Code 'confirmed' from ver_status and
          exists from [Condition: Code 'G47.31' from icd10] C
            where C.verificationStatus.coding contains Code 'confirmed' from ver_status or
          exists from [Observation: Code '72166-2' from loinc] O
            where O.value.coding contains Code 'LA18976-3' from loinc
                        
        define InInitialPopulation:
          Inclusion and
          not Exclusion
        """;

}
