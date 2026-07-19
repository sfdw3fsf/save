/**
 * STB_Rent_UpdateInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class STB_Rent_UpdateInfoResponse  implements java.io.Serializable {
    private CommonVO STB_Rent_UpdateInfoResult;

    public STB_Rent_UpdateInfoResponse() {
    }

    public STB_Rent_UpdateInfoResponse(
           CommonVO STB_Rent_UpdateInfoResult) {
           this.STB_Rent_UpdateInfoResult = STB_Rent_UpdateInfoResult;
    }


    /**
     * Gets the STB_Rent_UpdateInfoResult value for this STB_Rent_UpdateInfoResponse.
     * 
     * @return STB_Rent_UpdateInfoResult
     */
    public CommonVO getSTB_Rent_UpdateInfoResult() {
        return STB_Rent_UpdateInfoResult;
    }


    /**
     * Sets the STB_Rent_UpdateInfoResult value for this STB_Rent_UpdateInfoResponse.
     * 
     * @param STB_Rent_UpdateInfoResult
     */
    public void setSTB_Rent_UpdateInfoResult(CommonVO STB_Rent_UpdateInfoResult) {
        this.STB_Rent_UpdateInfoResult = STB_Rent_UpdateInfoResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof STB_Rent_UpdateInfoResponse)) return false;
        STB_Rent_UpdateInfoResponse other = (STB_Rent_UpdateInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.STB_Rent_UpdateInfoResult==null && other.getSTB_Rent_UpdateInfoResult()==null) || 
             (this.STB_Rent_UpdateInfoResult!=null &&
              this.STB_Rent_UpdateInfoResult.equals(other.getSTB_Rent_UpdateInfoResult())));
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
        if (getSTB_Rent_UpdateInfoResult() != null) {
            _hashCode += getSTB_Rent_UpdateInfoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(STB_Rent_UpdateInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">STB_Rent_UpdateInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STB_Rent_UpdateInfoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "STB_Rent_UpdateInfoResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CommonVO"));
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
