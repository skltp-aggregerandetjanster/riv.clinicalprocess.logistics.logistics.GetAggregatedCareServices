package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentResponseType;
import se.skltp.agp.riv.itintegration.engagementindex.v1.EngagementType;
import se.skltp.agp.service.api.QueryObject;
import se.skltp.agp.service.api.RequestListFactory;

public class RequestListFactoryImpl implements RequestListFactory {

    private static final Logger log = LoggerFactory.getLogger(RequestListFactoryImpl.class);

    /**
     * Filtrera svarsposter från engagemangsindexet baserat parametrar i GetCareServices requestet. 
     * Följande villkor måste vara sanna för att en svarspost från EI skall tas med i svaret:
     * 
     * 1. request.fromDate <= ei-engagement.mostRecentContent <= reqest.toDate 
     * 2. request.careUnitId.size == 0 or request.careUnitId.contains(ei-engagement.logicalAddress)
     * 
     * Svarsposter från engagemangsindexet som passerat filtreringen grupperas på fältet sourceSystem 
     * samt postens fält logicalAddress (producenter-enhet) samlas i listan careUnitId per varje sourceSystem
     * 
     * Ett anrop görs per funnet sourceSystem med följande värden i anropet:
     * 
     * 1. logicalAddress = sourceSystem (systemadressering) 
     * 2. subjectOfCareId = orginal-request.subjectOfCareId 
     * 3. careUnitId = listan av producenter som returnerats från engagemangsindexet för aktuellt source system
     * 4. fromDate = orginal-request.fromDate 
     * 5. toDate = orginal-request.toDate
     */
    @Override
    public List<Object[]> createRequestList(QueryObject qo, FindContentResponseType findContentResponse) {

        GetCareServicesType request = (GetCareServicesType) qo.getExtraArg();

        String sourceSystemHsaId = request.getSourceSystemHSAId();

        FindContentResponseType eiResp = (FindContentResponseType) findContentResponse;
        List<EngagementType> inEngagements = eiResp.getEngagement();

        log.info("Got {} hits in the engagement index", inEngagements.size());

        Map<String, List<String>> sourceSystem_pdlUnitList_map = new HashMap<String, List<String>>();

        for (EngagementType engagement : inEngagements) {
            if (isPartOf(sourceSystemHsaId, engagement.getLogicalAddress())) {
                log.debug("Add source system: {} for producer: {}", engagement.getSourceSystem(), engagement.getLogicalAddress());
                addPdlUnitToSourceSystem(sourceSystem_pdlUnitList_map, engagement.getSourceSystem(), engagement.getLogicalAddress());
            }
        }

        // Prepare the result of the transformation as a list of request-payloads,
        // one payload for each unique logical-address (e.g. source system since we are using system addressing),
        // each payload built up as an object-array according to the JAX-WS signature for the method in the service interface
        List<Object[]> reqList = new ArrayList<Object[]>();

        for (Entry<String, List<String>> entry : sourceSystem_pdlUnitList_map.entrySet()) {
            String sourceSystem = entry.getKey();
            log.info("Calling source system using logical address {} for subject of care id {}", sourceSystem, request.getPatientId().getId());
            Object[] reqArr = new Object[] { sourceSystem, request };
            reqList.add(reqArr);
        }

        log.debug("Transformed payload: {}", reqList);
        return reqList;
    }

    private boolean isPartOf(String careUnitId, String careUnit) {
        log.debug("Check presence of {} in {}", careUnit, careUnitId);
        if (StringUtils.isBlank(careUnitId))
            return true;
        return careUnitId.equals(careUnit);
    }

    private void addPdlUnitToSourceSystem(Map<String, List<String>> sourceSystem_pdlUnitList_map, String sourceSystem, String pdlUnitId) {
        List<String> careUnitList = sourceSystem_pdlUnitList_map.get(sourceSystem);
        if (careUnitList == null) {
            careUnitList = new ArrayList<String>();
            sourceSystem_pdlUnitList_map.put(sourceSystem, careUnitList);
        }
        careUnitList.add(pdlUnitId);
    }
}
