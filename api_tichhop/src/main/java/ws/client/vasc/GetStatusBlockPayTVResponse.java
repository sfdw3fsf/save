/**
 * GetStatusBlockPayTVResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetStatusBlockPayTVResponse  implements java.io.Serializable {
    private StatusBlockPayTVInfor getStatusBlockPayTVResult;

    public GetStatusBlockPayTVResponse() {
    }

    public GetStatusBlockPayTVResponse(
           StatusBlockPayTVInfor getStatusBlockPayTVResult) {
           this.getStatusBlockPayTVResult = getStatusBlockPayTVResult;
    }


    /**
     * Gets the getStatusBlockPayTVResult value for this GetStatusBlockPayTVResponse.
     * 
     * @return getStatusBlockPayTVResult
     */
    public StatusBlockPayTVInfor getGetStatusBlockPayTVResult() {
        return getStatusBlockPayTVResult;
    }


    /**
     * Sets the getStatusBlockPayTVResult value for this GetStatusBlockPayTVResponse.
     * 
     * @param getStatusBlockPayTVResult
     */
    public void setGetStatusBlockPayTVResult(StatusBlockPayTVInfor getStatusBlockPayTVResult) {
        this.getStatusBlockPayTVResult = getStatusBlockPayTVResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetStatusBlockPayTVResponse)) return false;
        GetStatusBlockPayTVResponse other = (GetStatusBlockPayTVResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getStatusBlockPayTVResult==null && other.getGetStatusBlockPayTVResult()==null) || 
             (this.getStatusBlockPayTVResult!=null &&
              this.getStatusBlockPayTVResult.equals(other.getGetStatusBlockPayTVResult())));
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
        if (getGetStatusBlockPayTVResult() != null) {
            _hashCode += getGetStatusBlockPayTVResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetStatusBlockPayTVResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">getStatusBlockPayTVResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getStatusBlockPayTVResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "getStatusBlockPayTVResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "StatusBlockPayTVInfor"));
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
