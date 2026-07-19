/**
 * GetSubscriberInfoV3_StringResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSubscriberInfoV3_StringResponse  implements java.io.Serializable {
    private String getSubscriberInfoV3_StringResult;

    public GetSubscriberInfoV3_StringResponse() {
    }

    public GetSubscriberInfoV3_StringResponse(
           String getSubscriberInfoV3_StringResult) {
           this.getSubscriberInfoV3_StringResult = getSubscriberInfoV3_StringResult;
    }


    /**
     * Gets the getSubscriberInfoV3_StringResult value for this GetSubscriberInfoV3_StringResponse.
     * 
     * @return getSubscriberInfoV3_StringResult
     */
    public String getGetSubscriberInfoV3_StringResult() {
        return getSubscriberInfoV3_StringResult;
    }


    /**
     * Sets the getSubscriberInfoV3_StringResult value for this GetSubscriberInfoV3_StringResponse.
     * 
     * @param getSubscriberInfoV3_StringResult
     */
    public void setGetSubscriberInfoV3_StringResult(String getSubscriberInfoV3_StringResult) {
        this.getSubscriberInfoV3_StringResult = getSubscriberInfoV3_StringResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSubscriberInfoV3_StringResponse)) return false;
        GetSubscriberInfoV3_StringResponse other = (GetSubscriberInfoV3_StringResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getSubscriberInfoV3_StringResult==null && other.getGetSubscriberInfoV3_StringResult()==null) || 
             (this.getSubscriberInfoV3_StringResult!=null &&
              this.getSubscriberInfoV3_StringResult.equals(other.getGetSubscriberInfoV3_StringResult())));
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
        if (getGetSubscriberInfoV3_StringResult() != null) {
            _hashCode += getGetSubscriberInfoV3_StringResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubscriberInfoV3_StringResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSubscriberInfoV3_StringResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getSubscriberInfoV3_StringResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberInfoV3_StringResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
