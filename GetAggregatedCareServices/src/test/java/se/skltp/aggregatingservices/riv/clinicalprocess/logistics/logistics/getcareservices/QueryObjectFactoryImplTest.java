/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.GetCareServicesType;
import riv.clinicalprocess.logistics.logistics.getcareservicesresponder.v2.ObjectFactory;
import riv.clinicalprocess.logistics.logistics.v3.PersonIdType;
import se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getcareservices.QueryObjectFactoryImpl;
import se.skltp.agp.service.api.QueryObject;

public class QueryObjectFactoryImplTest {

    private static final String CATEGORIZATION = "cll-cs";
    private static final String SERVICE_DOMAIN = "riv:clinicalprocess:logistics:logistics";
    private static final String RR_ID = "1212121212";
    private static final String SOURCE_SYSTEM = "SS1";

    QueryObjectFactoryImpl objectFactory = new QueryObjectFactoryImpl();

    @Before
    public void setup(){
        objectFactory.setEiCategorization(CATEGORIZATION);
        objectFactory.setEiServiceDomain(SERVICE_DOMAIN);
    }

    @Test
    public void createQueryObject() throws Exception{
        GetCareServicesType getCareDoc = new GetCareServicesType();
        PersonIdType patientId = new PersonIdType();
        patientId.setId(RR_ID);
        getCareDoc.setPatientId(patientId);

        Document doc = createDocument(getCareDoc);
        QueryObject queryObj = objectFactory.createQueryObject(doc);

        assertNotNull(queryObj.getFindContent());
        assertEquals(SERVICE_DOMAIN, queryObj.getFindContent().getServiceDomain());
        assertEquals(CATEGORIZATION, queryObj.getFindContent().getCategorization());
        assertEquals(RR_ID, queryObj.getFindContent().getRegisteredResidentIdentification());
        assertNull(queryObj.getFindContent().getSourceSystem());
    }

    @Test
    public void createQueryObject_with_source_system() throws Exception{
        GetCareServicesType getCareDoc = new GetCareServicesType();
        PersonIdType patientId = new PersonIdType();
        patientId.setId(RR_ID);
        getCareDoc.setPatientId(patientId);
        getCareDoc.setSourceSystemHSAId(SOURCE_SYSTEM);

        Document doc = createDocument(getCareDoc);
        QueryObject queryObj = objectFactory.createQueryObject(doc);

        assertNotNull(queryObj.getFindContent());
        assertEquals(SOURCE_SYSTEM, queryObj.getFindContent().getSourceSystem());
    }

    private Document createDocument(GetCareServicesType getCareService) throws Exception {
        ObjectFactory of = new ObjectFactory();
        JAXBContext jaxbContext = JAXBContext.newInstance(GetCareServicesType.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().newDocument();
        marshaller.marshal(of.createGetCareServices(getCareService), doc);
        return doc;
    }

}
