/**
 * Smart_OTTGetMemberPendingResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class Smart_OTTGetMemberPendingResponse  implements java.io.Serializable {
    private Smart_OTTGetMemberPendingVO smart_OTTGetMemberPendingResult;

    public Smart_OTTGetMemberPendingResponse() {
    }

    public Smart_OTTGetMemberPendingResponse(
           Smart_OTTGetMemberPendingVO smart_OTTGetMemberPendingResult) {
           this.smart_OTTGetMemberPendingResult = smart_OTTGetMemberPendingResult;
    }


    /**
     * Gets the smart_OTTGetMemberPendingResult value for this Smart_OTTGetMemberPendingResponse.
     * 
     * @return smart_OTTGetMemberPendingResult
     */
    public Smart_OTTGetMemberPendingVO getSmart_OTTGetMemberPendingResult() {
        return smart_OTTGetMemberPendingResult;
    }


    /**
     * Sets the smart_OTTGetMemberPendingResult value for this Smart_OTTGetMemberPendingResponse.
     * 
     * @param smart_OTTGetMemberPendingResult
     */
    public void setSmart_OTTGetMemberPendingResult(Smart_OTTGetMemberPendingVO smart_OTTGetMemberPendingResult) {
        this.smart_OTTGetMemberPendingResult = smart_OTTGetMemberPendingResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Smart_OTTGetMemberPendingResponse)) return false;
        Smart_OTTGetMemberPendingResponse other = (Smart_OTTGetMemberPendingResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.smart_OTTGetMemberPendingResult==null && other.getSmart_OTTGetMemberPendingResult()==null) || 
             (this.smart_OTTGetMemberPendingResult!=null &&
              this.smart_OTTGetMemberPendingResult.equals(other.getSmart_OTTGetMemberPendingResult())));
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
        if (getSmart_OTTGetMemberPendingResult() != null) {
            _hashCode += getSmart_OTTGetMemberPendingResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Smart_OTTGetMemberPendingResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Smart_OTTGetMemberPendingResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smart_OTTGetMemberPendingResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Smart_OTTGetMemberPendingResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Smart_OTTGetMemberPendingVO"));
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
