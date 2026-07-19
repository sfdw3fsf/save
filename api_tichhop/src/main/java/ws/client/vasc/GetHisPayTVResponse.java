/**
 * GetHisPayTVResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetHisPayTVResponse  implements java.io.Serializable {
    private GetHisPayTVResp getHisPayTVResult;

    public GetHisPayTVResponse() {
    }

    public GetHisPayTVResponse(
           GetHisPayTVResp getHisPayTVResult) {
           this.getHisPayTVResult = getHisPayTVResult;
    }


    /**
     * Gets the getHisPayTVResult value for this GetHisPayTVResponse.
     * 
     * @return getHisPayTVResult
     */
    public GetHisPayTVResp getGetHisPayTVResult() {
        return getHisPayTVResult;
    }


    /**
     * Sets the getHisPayTVResult value for this GetHisPayTVResponse.
     * 
     * @param getHisPayTVResult
     */
    public void setGetHisPayTVResult(GetHisPayTVResp getHisPayTVResult) {
        this.getHisPayTVResult = getHisPayTVResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetHisPayTVResponse)) return false;
        GetHisPayTVResponse other = (GetHisPayTVResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getHisPayTVResult==null && other.getGetHisPayTVResult()==null) || 
             (this.getHisPayTVResult!=null &&
              this.getHisPayTVResult.equals(other.getGetHisPayTVResult())));
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
        if (getGetHisPayTVResult() != null) {
            _hashCode += getGetHisPayTVResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetHisPayTVResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetHisPayTVResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getHisPayTVResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetHisPayTVResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetHisPayTVResp"));
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
