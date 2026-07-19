/**
 * GetSubscriberInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSubscriberInfoResponse  implements java.io.Serializable {
    private GetSubscriberInfoVO getSubscriberInfoResult;

    public GetSubscriberInfoResponse() {
    }

    public GetSubscriberInfoResponse(
           GetSubscriberInfoVO getSubscriberInfoResult) {
           this.getSubscriberInfoResult = getSubscriberInfoResult;
    }


    /**
     * Gets the getSubscriberInfoResult value for this GetSubscriberInfoResponse.
     * 
     * @return getSubscriberInfoResult
     */
    public GetSubscriberInfoVO getGetSubscriberInfoResult() {
        return getSubscriberInfoResult;
    }


    /**
     * Sets the getSubscriberInfoResult value for this GetSubscriberInfoResponse.
     * 
     * @param getSubscriberInfoResult
     */
    public void setGetSubscriberInfoResult(GetSubscriberInfoVO getSubscriberInfoResult) {
        this.getSubscriberInfoResult = getSubscriberInfoResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSubscriberInfoResponse)) return false;
        GetSubscriberInfoResponse other = (GetSubscriberInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getSubscriberInfoResult==null && other.getGetSubscriberInfoResult()==null) || 
             (this.getSubscriberInfoResult!=null &&
              this.getSubscriberInfoResult.equals(other.getGetSubscriberInfoResult())));
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
        if (getGetSubscriberInfoResult() != null) {
            _hashCode += getGetSubscriberInfoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubscriberInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSubscriberInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getSubscriberInfoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberInfoResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberInfoVO"));
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
