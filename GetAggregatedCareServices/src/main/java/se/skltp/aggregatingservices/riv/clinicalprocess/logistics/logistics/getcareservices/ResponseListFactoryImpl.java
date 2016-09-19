package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.logistics.logistics.enums.v3.ResultCodeEnum;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.ObjectFactory;
import riv.clinicalprocess.logistics.logistics.v3.ResultType;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.ResponseListFactory;

public class ResponseListFactoryImpl implements ResponseListFactory {

    private static final Logger log = LoggerFactory.getLogger(ResponseListFactoryImpl.class);
    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetCareServicesResponseType.class, ProcessingStatusType.class);
    private static final ObjectFactory OF = new ObjectFactory();

    @Override
    public String getXmlFromAggregatedResponse(QueryObject queryObject, List<Object> aggregatedResponseList) {
        GetCareServicesResponseType aggregatedResponse = new GetCareServicesResponseType();

        for (Object object : aggregatedResponseList) {
            GetCareServicesResponseType response = (GetCareServicesResponseType)object;
            aggregatedResponse.getCareService().addAll(response.getCareService());
        }

        aggregatedResponse.setResult(new ResultType());
        aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);
        aggregatedResponse.getResult().setLogId("NA");
        
        if (log.isInfoEnabled()) {
            String subjectOfCareId = queryObject.getFindContent().getRegisteredResidentIdentification();
            log.info("Returning {} aggregated care contact for subject of care id {}", aggregatedResponse.getCareService().size() ,subjectOfCareId);
        }

        // Since the class GetCareServicesResponseType don't have an @XmlRootElement annotation
        // we need to use the ObjectFactory to add it.
        return jaxbUtil.marshal(OF.createGetCareServicesResponse(aggregatedResponse));
    }
}
