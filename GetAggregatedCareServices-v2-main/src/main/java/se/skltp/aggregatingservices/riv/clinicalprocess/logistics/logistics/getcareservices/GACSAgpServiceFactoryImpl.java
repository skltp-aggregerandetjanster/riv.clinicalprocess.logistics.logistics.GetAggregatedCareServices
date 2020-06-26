package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import riv.clinicalprocess.logistics.logistics.enums.v3.ResultCodeEnum;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesType;
import riv.clinicalprocess.logistics.logistics.v3.ResultType;
import se.skltp.aggregatingservices.AgServiceFactoryBase;

@Log4j2
public class GACSAgpServiceFactoryImpl extends
    AgServiceFactoryBase<GetCareServicesType, GetCareServicesResponseType>{

@Override
public String getPatientId(GetCareServicesType queryObject){
    return queryObject.getPatientId().getId();
    }

@Override
public String getSourceSystemHsaId(GetCareServicesType queryObject){
    return queryObject.getSourceSystemHSAId();
    }

@Override
public GetCareServicesResponseType aggregateResponse(List<GetCareServicesResponseType> aggregatedResponseList ){

    GetCareServicesResponseType aggregatedResponse=new GetCareServicesResponseType();

    for (GetCareServicesResponseType object : aggregatedResponseList) {
        GetCareServicesResponseType response = object;
        aggregatedResponse.getCareService().addAll(response.getCareService());
    }

    aggregatedResponse.setResult(new ResultType());
    aggregatedResponse.getResult().setResultCode(ResultCodeEnum.INFO);
    aggregatedResponse.getResult().setLogId("NA");

    return aggregatedResponse;
    }
}

