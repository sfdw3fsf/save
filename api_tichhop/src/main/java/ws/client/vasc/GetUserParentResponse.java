/**
 * GetUserParentResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetUserParentResponse  implements java.io.Serializable {
    private GetListSubscriberVO getUserParentResult;

    public GetUserParentResponse() {
    }

    public GetUserParentResponse(
           GetListSubscriberVO getUserParentResult) {
           this.getUserParentResult = getUserParentResult;
    }


    /**
     * Gets the getUserParentResult value for this GetUserParentResponse.
     * 
     * @return getUserParentResult
     */
    public GetListSubscriberVO getGetUserParentResult() {
        return getUserParentResult;
    }


    /**
     * Sets the getUserParentResult value for this GetUserParentResponse.
     * 
     * @param getUserParentResult
     */
    public void setGetUserParentResult(GetListSubscriberVO getUserParentResult) {
        this.getUserParentResult = getUserParentResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetUserParentResponse)) return false;
        GetUserParentResponse other = (GetUserParentResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getUserParentResult==null && other.getGetUserParentResult()==null) || 
             (this.getUserParentResult!=null &&
              this.getUserParentResult.equals(other.getGetUserParentResult())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getGetUserParentResult() != null) {
            _hashCode += getGetUserParentResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUserParentResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUserParentResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getUserParentResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetUserParentResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetListSubscriberVO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
