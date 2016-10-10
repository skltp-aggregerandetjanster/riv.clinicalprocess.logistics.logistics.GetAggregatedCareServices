package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.soitoolkit.commons.mule.jaxb.JaxbUtil;

import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesResponseType;
import riv.clinicalprocess.logistics.logistics.v3.CareServiceType;
import se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices.ResponseListFactoryImpl;
import se.skltp.agp.riv.interoperability.headers.v1.ProcessingStatusType;
import se.skltp.agp.riv.itintegration.engagementindex.findcontentresponder.v1.FindContentType;
import se.skltp.agp.service.api.QueryObject;

public class ResponseListFactoryImplTest {

    private static final JaxbUtil jaxbUtil = new JaxbUtil(GetCareServicesResponseType.class, ProcessingStatusType.class);

    @Test
    public void getXmlFromAggregatedResponse(){
        FindContentType fc = new FindContentType();
        fc.setRegisteredResidentIdentification("1212121212");
        QueryObject queryObject = new QueryObject(fc, null);
        List<Object> responseList = new ArrayList<Object>(2);
        responseList.add(createGetCareServicesResponse());
        responseList.add(createGetCareServicesResponse());
        ResponseListFactoryImpl responseListFactory = new ResponseListFactoryImpl();

        String responseXML = responseListFactory.getXmlFromAggregatedResponse(queryObject, responseList);
        GetCareServicesResponseType response = (GetCareServicesResponseType)jaxbUtil.unmarshal(responseXML);
        assertEquals(2, response.getCareService().size());
    }

    private GetCareServicesResponseType createGetCareServicesResponse(){
        GetCareServicesResponseType getCareDocResponse = new GetCareServicesResponseType();
        getCareDocResponse.getCareService().add(new CareServiceType());
        return getCareDocResponse;
    }


    @Test
    public void getXmlFromAggregatedResponse_incorrect() throws Exception{

        InputStream stream = ResponseListFactoryImplTest.class.getResourceAsStream("/GetCareServicesResponse-example-incorrect.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        GetCareServicesResponseType response = (GetCareServicesResponseType)jaxbUtil.unmarshal(sb.toString());
        assertEquals(0, response.getCareService().size());
    }

    @Test
    public void getXmlFromAggregatedResponse_correct() throws Exception{

        InputStream stream = ResponseListFactoryImplTest.class.getResourceAsStream("/GetCareServicesResponse-example-correct.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        GetCareServicesResponseType response = (GetCareServicesResponseType)jaxbUtil.unmarshal(sb.toString());
        assertEquals(1, response.getCareService().size());
    }
}
