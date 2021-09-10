package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import org.junit.jupiter.api.BeforeAll;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)

public class GACSCreateFindContentTest extends CreateFindContentTest {

  private static GACSAgpServiceConfiguration configuration = new GACSAgpServiceConfiguration();
  private static AgpServiceFactory<GetCareServicesResponseType> agpServiceFactory = new GACSAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GACSCreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @BeforeAll
  public static void before() {
    configuration = new GACSAgpServiceConfiguration();
    agpServiceFactory = new GACSAgpServiceFactoryImpl();
    agpServiceFactory.setAgpServiceConfiguration(configuration);
  }


}
