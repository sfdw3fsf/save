/**
 * RegisterSubscriberPayResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberPayResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberPayResult;

    public RegisterSubscriberPayResponse() {
    }

    public RegisterSubscriberPayResponse(
           RegisterSubscriberVO registerSubscriberPayResult) {
           this.registerSubscriberPayResult = registerSubscriberPayResult;
    }


    /**
     * Gets the registerSubscriberPayResult value for this RegisterSubscriberPayResponse.
     * 
     * @return registerSubscriberPayResult
     */
    public RegisterSubscriberVO getRegisterSubscriberPayResult() {
        return registerSubscriberPayResult;
    }


    /**
     * Sets the registerSubscriberPayResult value for this RegisterSubscriberPayResponse.
     * 
     * @param registerSubscriberPayResult
     */
    public void setRegisterSubscriberPayResult(RegisterSubscriberVO registerSubscriberPayResult) {
        this.registerSubscriberPayResult = registerSubscriberPayResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberPayResponse)) return false;
        RegisterSubscriberPayResponse other = (RegisterSubscriberPayResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberPayResult==null && other.getRegisterSubscriberPayResult()==null) || 
             (this.registerSubscriberPayResult!=null &&
              this.registerSubscriberPayResult.equals(other.getRegisterSubscriberPayResult())));
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
        if (getRegisterSubscriberPayResult() != null) {
            _hashCode += getRegisterSubscriberPayResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberPayResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberPayResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberPayResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberPayResult"));
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
