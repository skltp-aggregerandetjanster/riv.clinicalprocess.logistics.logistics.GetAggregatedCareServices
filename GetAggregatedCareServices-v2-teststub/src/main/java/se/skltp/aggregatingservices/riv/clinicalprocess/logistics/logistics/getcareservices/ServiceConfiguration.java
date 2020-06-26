package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.logistics.logistics.getcareservicesrequest.v2.GetCareServicesResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareservicesrequest.v2.GetCareServicesResponderService;
import se.skltp.aggregatingservices.config.TestProducerConfiguration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="getaggregatedcareservices.v2.teststub")
public class ServiceConfiguration extends TestProducerConfiguration {

  public static final String SCHEMA_PATH = "classpath:/schemas/clinicalprocess_logistics_logistics_3.0.4_RC1/interactions/GetCareServicesInteraction/GetCareServicesInteraction_2.0_RIVTABP21.wsdl";

  public ServiceConfiguration() {
    setProducerAddress("http://localhost:8083/vp");
    setServiceClass(GetCareServicesResponderInterface.class.getName());
    setServiceNamespace("urn:riv:clinicalprocess:logistics:logistics:GetCareServicesResponder:2");
    setPortName(GetCareServicesResponderService.GetCareServicesResponderPort.toString());
    setWsdlPath(SCHEMA_PATH);
    setTestDataGeneratorClass(ServiceTestDataGenerator.class.getName());
    setServiceTimeout(27000);
  }

}
