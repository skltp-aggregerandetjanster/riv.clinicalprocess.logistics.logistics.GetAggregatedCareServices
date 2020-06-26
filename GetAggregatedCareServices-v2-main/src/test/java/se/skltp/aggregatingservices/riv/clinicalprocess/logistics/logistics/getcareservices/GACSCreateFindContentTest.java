package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import se.skltp.aggregatingservices.api.AgpServiceFactory;
import se.skltp.aggregatingservices.tests.CreateFindContentTest;


@RunWith(SpringJUnit4ClassRunner.class)
public class GACSCreateFindContentTest extends CreateFindContentTest {

  private static GACSAgpServiceConfiguration configuration = new GACSAgpServiceConfiguration();
  private static AgpServiceFactory<GetCareServicesResponseType> agpServiceFactory = new GACSAgpServiceFactoryImpl();
  private static ServiceTestDataGenerator testDataGenerator = new ServiceTestDataGenerator();

  public GACSCreateFindContentTest() {
    super(testDataGenerator, agpServiceFactory, configuration);
  }

  @BeforeClass
  public static void before() {
    configuration = new GACSAgpServiceConfiguration();
    agpServiceFactory = new GACSAgpServiceFactoryImpl();
    agpServiceFactory.setAgpServiceConfiguration(configuration);
  }


}
