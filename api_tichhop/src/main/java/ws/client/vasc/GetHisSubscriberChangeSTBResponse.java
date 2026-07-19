/**
 * GetHisSubscriberChangeSTBResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetHisSubscriberChangeSTBResponse  implements java.io.Serializable {
    private GetSTBHistoryInfoVO getHisSubscriberChangeSTBResult;

    public GetHisSubscriberChangeSTBResponse() {
    }

    public GetHisSubscriberChangeSTBResponse(
           GetSTBHistoryInfoVO getHisSubscriberChangeSTBResult) {
           this.getHisSubscriberChangeSTBResult = getHisSubscriberChangeSTBResult;
    }


    /**
     * Gets the getHisSubscriberChangeSTBResult value for this GetHisSubscriberChangeSTBResponse.
     * 
     * @return getHisSubscriberChangeSTBResult
     */
    public GetSTBHistoryInfoVO getGetHisSubscriberChangeSTBResult() {
        return getHisSubscriberChangeSTBResult;
    }


    /**
     * Sets the getHisSubscriberChangeSTBResult value for this GetHisSubscriberChangeSTBResponse.
     * 
     * @param getHisSubscriberChangeSTBResult
     */
    public void setGetHisSubscriberChangeSTBResult(GetSTBHistoryInfoVO getHisSubscriberChangeSTBResult) {
        this.getHisSubscriberChangeSTBResult = getHisSubscriberChangeSTBResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetHisSubscriberChangeSTBResponse)) return false;
        GetHisSubscriberChangeSTBResponse other = (GetHisSubscriberChangeSTBResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getHisSubscriberChangeSTBResult==null && other.getGetHisSubscriberChangeSTBResult()==null) || 
             (this.getHisSubscriberChangeSTBResult!=null &&
              this.getHisSubscriberChangeSTBResult.equals(other.getGetHisSubscriberChangeSTBResult())));
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
        if (getGetHisSubscriberChangeSTBResult() != null) {
            _hashCode += getGetHisSubscriberChangeSTBResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetHisSubscriberChangeSTBResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetHisSubscriberChangeSTBResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getHisSubscriberChangeSTBResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetHisSubscriberChangeSTBResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetSTBHistoryInfoVO"));
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
