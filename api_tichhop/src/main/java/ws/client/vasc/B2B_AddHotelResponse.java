/**
 * B2B_AddHotelResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_AddHotelResponse  implements java.io.Serializable {
    private B2BCommonVO b2B_AddHotelResult;

    public B2B_AddHotelResponse() {
    }

    public B2B_AddHotelResponse(
           B2BCommonVO b2B_AddHotelResult) {
           this.b2B_AddHotelResult = b2B_AddHotelResult;
    }


    /**
     * Gets the b2B_AddHotelResult value for this B2B_AddHotelResponse.
     * 
     * @return b2B_AddHotelResult
     */
    public B2BCommonVO getB2B_AddHotelResult() {
        return b2B_AddHotelResult;
    }


    /**
     * Sets the b2B_AddHotelResult value for this B2B_AddHotelResponse.
     * 
     * @param b2B_AddHotelResult
     */
    public void setB2B_AddHotelResult(B2BCommonVO b2B_AddHotelResult) {
        this.b2B_AddHotelResult = b2B_AddHotelResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_AddHotelResponse)) return false;
        B2B_AddHotelResponse other = (B2B_AddHotelResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.b2B_AddHotelResult==null && other.getB2B_AddHotelResult()==null) || 
             (this.b2B_AddHotelResult!=null &&
              this.b2B_AddHotelResult.equals(other.getB2B_AddHotelResult())));
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
        if (getB2B_AddHotelResult() != null) {
            _hashCode += getB2B_AddHotelResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_AddHotelResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">B2B_AddHotelResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("b2B_AddHotelResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_AddHotelResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "b2bCommonVO"));
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
