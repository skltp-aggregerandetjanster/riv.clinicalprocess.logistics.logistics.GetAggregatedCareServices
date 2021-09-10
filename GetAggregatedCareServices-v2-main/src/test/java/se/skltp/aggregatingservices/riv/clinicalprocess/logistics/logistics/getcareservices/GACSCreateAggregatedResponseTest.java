package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;


import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)

public class GACSCreateAggregatedResponseTest extends CreateAggregatedResponseTest {

  private static GACSAgpServiceConfiguration configuration = new GACSAgpServiceConfiguration();
  private static AgpServiceFactory<GetCareServicesResponseType> agpServiceFactory = new GACSAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GACSCreateAggregatedResponseTest() {
      super(testDataGenerator, agpServiceFactory, configuration);
  }

  @Override
  public int getResponseSize(Object response) {
        GetCareServicesResponseType responseType = (GetCareServicesResponseType)response;
    return responseType.getCareService().size();
  }
}