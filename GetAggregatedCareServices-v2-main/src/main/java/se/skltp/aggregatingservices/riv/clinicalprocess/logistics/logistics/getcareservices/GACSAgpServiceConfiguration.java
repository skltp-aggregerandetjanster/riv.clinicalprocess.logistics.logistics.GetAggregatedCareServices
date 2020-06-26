package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import riv.clinicalprocess.logistics.logistics.getcareservicesrequest.v2.GetCareServicesResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareservicesrequest.v2.GetCareServicesResponderService;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "getaggregatedcareservices.v2")
public class GACSAgpServiceConfiguration extends se.skltp.aggregatingservices.configuration.AgpServiceConfiguration {

public static final String SCHEMA_PATH = "classpath:/schemas/clinicalprocess_logistics_logistics_3.0.4_RC1/interactions/GetCareServicesInteraction/GetCareServicesInteraction_2.0_RIVTABP21.wsdl";

  public GACSAgpServiceConfiguration() {

    setServiceName("GetAggregatedCareServices-v2");
    setTargetNamespace("urn:riv:clinicalprocess:logistics:logistics:GetCareServices:2:rivtabp21");

    // Set inbound defaults
    setInboundServiceURL("http://localhost:9022/GetAggregatedCareServices/service/v2");
    setInboundServiceWsdl(SCHEMA_PATH);
    setInboundServiceClass(GetCareServicesResponderInterface.class.getName());
    setInboundPortName(GetCareServicesResponderService.GetCareServicesResponderPort.toString());

    // Set outbound defaults
    setOutboundServiceWsdl(SCHEMA_PATH);
    setOutboundServiceClass(GetCareServicesResponderInterface.class.getName());
    setOutboundPortName(GetCareServicesResponderService.GetCareServicesResponderPort.toString());

    // FindContent
    setEiServiceDomain("riv:clinicalprocess:logistics:logistics");
    setEiCategorization("cll-cs");

    // TAK
    setTakContract("urn:riv:clinicalprocess:logistics:logistics:GetCareServicesResponder:2");

    // Set service factory
    setServiceFactoryClass(GACSAgpServiceFactoryImpl.class.getName());
    }


}
