/**
 * RegisterSelfCareResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSelfCareResponse  implements java.io.Serializable {
    private CommonVO registerSelfCareResult;

    public RegisterSelfCareResponse() {
    }

    public RegisterSelfCareResponse(
           CommonVO registerSelfCareResult) {
           this.registerSelfCareResult = registerSelfCareResult;
    }


    /**
     * Gets the registerSelfCareResult value for this RegisterSelfCareResponse.
     * 
     * @return registerSelfCareResult
     */
    public CommonVO getRegisterSelfCareResult() {
        return registerSelfCareResult;
    }


    /**
     * Sets the registerSelfCareResult value for this RegisterSelfCareResponse.
     * 
     * @param registerSelfCareResult
     */
    public void setRegisterSelfCareResult(CommonVO registerSelfCareResult) {
        this.registerSelfCareResult = registerSelfCareResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSelfCareResponse)) return false;
        RegisterSelfCareResponse other = (RegisterSelfCareResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSelfCareResult==null && other.getRegisterSelfCareResult()==null) || 
             (this.registerSelfCareResult!=null &&
              this.registerSelfCareResult.equals(other.getRegisterSelfCareResult())));
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
        if (getRegisterSelfCareResult() != null) {
            _hashCode += getRegisterSelfCareResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSelfCareResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSelfCareResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSelfCareResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSelfCareResult"));
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
