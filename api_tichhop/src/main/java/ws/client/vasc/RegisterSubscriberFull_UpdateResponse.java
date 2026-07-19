/**
 * RegisterSubscriberFull_UpdateResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberFull_UpdateResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberFull_UpdateResult;

    public RegisterSubscriberFull_UpdateResponse() {
    }

    public RegisterSubscriberFull_UpdateResponse(
           RegisterSubscriberVO registerSubscriberFull_UpdateResult) {
           this.registerSubscriberFull_UpdateResult = registerSubscriberFull_UpdateResult;
    }


    /**
     * Gets the registerSubscriberFull_UpdateResult value for this RegisterSubscriberFull_UpdateResponse.
     * 
     * @return registerSubscriberFull_UpdateResult
     */
    public RegisterSubscriberVO getRegisterSubscriberFull_UpdateResult() {
        return registerSubscriberFull_UpdateResult;
    }


    /**
     * Sets the registerSubscriberFull_UpdateResult value for this RegisterSubscriberFull_UpdateResponse.
     * 
     * @param registerSubscriberFull_UpdateResult
     */
    public void setRegisterSubscriberFull_UpdateResult(RegisterSubscriberVO registerSubscriberFull_UpdateResult) {
        this.registerSubscriberFull_UpdateResult = registerSubscriberFull_UpdateResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberFull_UpdateResponse)) return false;
        RegisterSubscriberFull_UpdateResponse other = (RegisterSubscriberFull_UpdateResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberFull_UpdateResult==null && other.getRegisterSubscriberFull_UpdateResult()==null) || 
             (this.registerSubscriberFull_UpdateResult!=null &&
              this.registerSubscriberFull_UpdateResult.equals(other.getRegisterSubscriberFull_UpdateResult())));
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
        if (getRegisterSubscriberFull_UpdateResult() != null) {
            _hashCode += getRegisterSubscriberFull_UpdateResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberFull_UpdateResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberFull_UpdateResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberFull_UpdateResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberFull_UpdateResult"));
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
