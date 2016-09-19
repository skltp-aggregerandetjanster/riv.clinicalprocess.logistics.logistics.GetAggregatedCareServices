package se.skltp.aggregatingservices.riv.clinicalprocess.logistics.logistics.getaggregatedcareservices

trait CommonParameters {
  val serviceName:String     = "CareServices"
  val urn:String             = "urn:riv:clinicalprocess:logistics:logistics:GetCareServicesResponder:3"
  val responseElement:String = "GetCareServicesResponse"
  val responseItem:String    = "careContact"
  var baseUrl:String         = if (System.getProperty("baseUrl") != null && !System.getProperty("baseUrl").isEmpty()) {
                                   System.getProperty("baseUrl")
                               } else {
                                   "http://33.33.33.33:8081/GetAggregatedCareServices/service/v3"
                               }
}
