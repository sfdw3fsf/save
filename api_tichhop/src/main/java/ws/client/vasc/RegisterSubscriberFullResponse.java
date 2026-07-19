/**
 * RegisterSubscriberFullResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberFullResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberFullResult;

    public RegisterSubscriberFullResponse() {
    }

    public RegisterSubscriberFullResponse(
           RegisterSubscriberVO registerSubscriberFullResult) {
           this.registerSubscriberFullResult = registerSubscriberFullResult;
    }


    /**
     * Gets the registerSubscriberFullResult value for this RegisterSubscriberFullResponse.
     * 
     * @return registerSubscriberFullResult
     */
    public RegisterSubscriberVO getRegisterSubscriberFullResult() {
        return registerSubscriberFullResult;
    }


    /**
     * Sets the registerSubscriberFullResult value for this RegisterSubscriberFullResponse.
     * 
     * @param registerSubscriberFullResult
     */
    public void setRegisterSubscriberFullResult(RegisterSubscriberVO registerSubscriberFullResult) {
        this.registerSubscriberFullResult = registerSubscriberFullResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberFullResponse)) return false;
        RegisterSubscriberFullResponse other = (RegisterSubscriberFullResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberFullResult==null && other.getRegisterSubscriberFullResult()==null) || 
             (this.registerSubscriberFullResult!=null &&
              this.registerSubscriberFullResult.equals(other.getRegisterSubscriberFullResult())));
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
        if (getRegisterSubscriberFullResult() != null) {
            _hashCode += getRegisterSubscriberFullResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberFullResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberFullResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberFullResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberFullResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberVO"));
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
