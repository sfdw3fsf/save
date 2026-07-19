package vn.vnptit.sapi.oneapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.axis.AxisFault
import vn.vnptit.sapi.oneapp.managed.LoggingManaged

@JsonIgnoreProperties(["metaClass"])
class CommonResponse {
    @JsonProperty("errorCode")
    int errorCode

    @JsonProperty("faultCode")
    String faultCode

    @JsonProperty("faultString")
    String faultString

    @JsonProperty("data")
    Object data

    CommonResponse() {
    }

    CommonResponse(int errorCode, String faultString) {
        this.errorCode = errorCode
        this.faultString = faultString
    }

    static CommonResponse resolveException(Exception e, LoggingManaged loggingManaged) {
        loggingManaged.error(e.message, e) 
        if (e instanceof AxisFault) {
            return new CommonResponse(errorCode: 1, faultCode: e.faultCode.toString(), faultString: e.faultString, data: null)
        } else {
            return new CommonResponse(errorCode: 2, faultCode: "Exception", faultString: e.message, data: null)
        }
    }
}

class InvoiceCommonResponse extends CommonResponse {
    Integer site
}