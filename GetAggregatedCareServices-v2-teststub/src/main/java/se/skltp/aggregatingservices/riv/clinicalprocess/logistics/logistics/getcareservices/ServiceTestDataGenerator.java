package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesType;
import riv.clinicalprocess.logistics.logistics.v3.CareServiceBodyType;
import riv.clinicalprocess.logistics.logistics.v3.CareServiceType;
import riv.clinicalprocess.logistics.logistics.v3.HealthcareProfessionalType;
import riv.clinicalprocess.logistics.logistics.v3.IIType;
import riv.clinicalprocess.logistics.logistics.v3.OrgUnitType;
import riv.clinicalprocess.logistics.logistics.v3.PatientSummaryHeaderType;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;

import org.apache.cxf.message.MessageContentsList;
import org.springframework.stereotype.Service;

import se.skltp.aggregatingservices.data.TestDataDefines;
import se.skltp.aggregatingservices.data.TestDataGenerator;

@Log4j2
@Service
public class ServiceTestDataGenerator extends TestDataGenerator {

	@Override
	public String getPatientId(MessageContentsList messageContentsList) {
		GetCareServicesType request = (GetCareServicesType) messageContentsList.get(1);
		String patientId = request.getPatientId().getId();
		return patientId;
	}

	@Override
	public Object createResponse(Object... responseItems) {
		log.info("Creating a response with {} items", responseItems.length);
		GetCareServicesResponseType response = new GetCareServicesResponseType();
		for (int i = 0; i < responseItems.length; i++) {
            response.getCareService().add((CareServiceType)responseItems[i]);
		}

		log.info("response.toString:" + response.toString());

		return response;
	}

	@Override
	public Object createResponseItem(String logicalAddress, String registeredResidentId, String businessObjectId, String time) {
		log.debug("Created ResponseItem for logical-address {}, registeredResidentId {} and businessObjectId {}",
				new Object[]{logicalAddress, registeredResidentId, businessObjectId});

        CareServiceType response = new CareServiceType();
        PatientSummaryHeaderType header = new PatientSummaryHeaderType();
        PersonIdType patientId = new PersonIdType();
        patientId.setId(registeredResidentId);
        patientId.setType("1.2.752.129.2.1.3.1");
        header.setPatientId(patientId);
        header.setApprovedForPatient(true);
        header.setSourceSystemHSAId(logicalAddress);
        header.setDocumentId(businessObjectId);

        HealthcareProfessionalType author = new HealthcareProfessionalType();
        author.setHealthcareProfessionalCareGiverHSAId(logicalAddress);
        author.setAuthorTime(time);
        header.setAccountableHealthcareProfessional(author);
        header.setSourceSystemHSAId(logicalAddress);

        OrgUnitType orgUnit = new OrgUnitType();
        orgUnit.setOrgUnitHSAId(logicalAddress);
        if(TestDataDefines.TEST_LOGICAL_ADDRESS_1.equals(logicalAddress)){
            orgUnit.setOrgUnitName("Vårdcentralen Kusten, Kärna");
        } else if(TestDataDefines.TEST_LOGICAL_ADDRESS_2.equals(logicalAddress)){
            orgUnit.setOrgUnitName("Vårdcentralen Molnet");
        } else {
            orgUnit.setOrgUnitName("Vårdcentralen Stacken");
        }
        header.getAccountableHealthcareProfessional().setHealthcareProfessionalOrgUnit(orgUnit);
        header.getAccountableHealthcareProfessional().setHealthcareProfessionalName("John Doe");;

        response.setCareServiceHeader(header);

        CareServiceBodyType body = new CareServiceBodyType();

        body.setHousing("H");
        body.setInput("Test description");
        body.setPerformerHealthCareProfessional(createII(logicalAddress, "0000"));
        body.setPerformerHealthCareUnit(createII(logicalAddress, "1111"));
        
        response.setCareServiceBody(body);
        
        return response;
	}

	public Object createRequest(String patientId, String sourceSystemHSAId){
        GetCareServicesType request = new GetCareServicesType();

        PersonIdType patient = new PersonIdType();
        patient.setId(patientId);
        request.setPatientId(patient);
        request.setSourceSystemHSAId(sourceSystemHSAId);

		return request;
	}
	
    private IIType createII(String root, String extension) {
        IIType ii = new IIType();
        ii.setRoot(root);
        ii.setExtension(extension);   
        
        return ii;
    }
}
