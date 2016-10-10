package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices.integrationtest;

import static se.skltp.agp.test.producer.TestProducerDb.TEST_RR_ID_ONE_HIT;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareservicesrequest.v2.GetCareServicesResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;
import se.skltp.aggregatingcareservices.CareServicesMuleServer;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.test.consumer.AbstractTestConsumer;
import se.skltp.agp.test.consumer.SoapHeaderCxfInterceptor;

public class CareServicesTestConsumer extends AbstractTestConsumer<GetCareServicesResponderInterface>{

    private static final Logger log = LoggerFactory.getLogger(CareServicesTestConsumer.class);

    public static void main(String[] args) {
        log.info("URL: " + CareServicesMuleServer.getAddress("SERVICE_INBOUND_URL"));
        String serviceAddress = CareServicesMuleServer.getAddress("SERVICE_INBOUND_URL");
        String personnummer = TEST_RR_ID_ONE_HIT;

        CareServicesTestConsumer consumer = new CareServicesTestConsumer(serviceAddress, SAMPLE_SENDER_ID, SAMPLE_ORIGINAL_CONSUMER_HSAID, SAMPLE_CORRELATION_ID);
        Holder<GetCareServicesResponseType> responseHolder = new Holder<GetCareServicesResponseType>();
        Holder<ProcessingStatusType> processingStatusHolder = new Holder<ProcessingStatusType>();
        long now = System.currentTimeMillis();
        consumer.callService("logical-adress", personnummer, processingStatusHolder, responseHolder);
        log.info("Returned #care contact = " + responseHolder.value.getCareService().size() + " in " + (System.currentTimeMillis() - now) + " ms.");
    }

    public CareServicesTestConsumer(String serviceAddress, String senderId, String originalConsumerHsaId, String correlationId) {
        // Setup a web service proxy for communication using HTTPS with Mutual Authentication
        super(GetCareServicesResponderInterface.class, serviceAddress, senderId, originalConsumerHsaId, correlationId);
    }

    public void callService(String logicalAddress, String id, Holder<ProcessingStatusType> processingStatusHolder, Holder<GetCareServicesResponseType> responseHolder) {
        log.debug("Calling GetCareService-soap-service with id = {}", id);

        GetCareServicesType request = new GetCareServicesType();
        PersonIdType patientId = new PersonIdType();
        patientId.setId(id);
        request.setPatientId(patientId);

        GetCareServicesResponseType response = _service.getCareServices(logicalAddress, request);
        responseHolder.value = response;

        processingStatusHolder.value = SoapHeaderCxfInterceptor.getLastFoundProcessingStatus();
    }
}
