package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateAggregatedResponseTest;


@RunWith(SpringJUnit4ClassRunner.class)
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