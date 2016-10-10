package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices.integrationtest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareservicesrequest.v2.GetCareServicesResponderInterface;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesType;
import se.skltp.agp.test.producer.TestProducerDb;

@WebService(serviceName = "GetCareServicesResponderService", portName = "GetCareServicesResponderPort", targetNamespace = "urn:riv:clinicalprocess.logistics.logistics:GetCareServicesResponder:2:rivtabp21", name = "GetCareServicesInteraction")
public class CareServicesTestProducer implements GetCareServicesResponderInterface {

    private static final Logger log = LoggerFactory.getLogger(CareServicesTestProducer.class);

    private TestProducerDb testDb;

    public void setTestDb(TestProducerDb testDb) {
        this.testDb = testDb;
    }

    @Override
    @WebResult(name = "GetCareServicesResponse", targetNamespace = "urn:riv:clinicalprocess:logistics:logistics:GetCareServicesResponder:3", partName = "parameters")
    @WebMethod(operationName = "GetCareServices", action = "urn:riv:ehr:patientsummary:GetCareServicesResponder:2:GetCareServices")
    public GetCareServicesResponseType getCareServices(
            @WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true) String logicalAddress,
            @WebParam(partName = "parameters", name = "GetCareServices", targetNamespace = "urn:riv:clinicalprocess:logistics:logistics:GetCareServicesResponder:3") GetCareServicesType request) {
        log.info("### Virtual service for GetCareServices call the source system with logical address: {} and patientId: {}", logicalAddress, request.getPatientId().getId());

        GetCareServicesResponseType response = (GetCareServicesResponseType)testDb.processRequest(logicalAddress, request.getPatientId().getId());
        if (response == null) {
            // Return an empty response object instead of null if nothing is found
            response = new GetCareServicesResponseType();
        }

        log.info("### Virtual service got {} documents in the reply from the source system with logical address: {} and patientId: {}", new Object[] {response.getCareService().size(), logicalAddress, request.getPatientId().getId()});

        // We are done
        return response;
    }

}
