/**
 * RegisterSubscriberV2HNIResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberV2HNIResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberV2HNIResult;

    public RegisterSubscriberV2HNIResponse() {
    }

    public RegisterSubscriberV2HNIResponse(
           RegisterSubscriberVO registerSubscriberV2HNIResult) {
           this.registerSubscriberV2HNIResult = registerSubscriberV2HNIResult;
    }


    /**
     * Gets the registerSubscriberV2HNIResult value for this RegisterSubscriberV2HNIResponse.
     * 
     * @return registerSubscriberV2HNIResult
     */
    public RegisterSubscriberVO getRegisterSubscriberV2HNIResult() {
        return registerSubscriberV2HNIResult;
    }


    /**
     * Sets the registerSubscriberV2HNIResult value for this RegisterSubscriberV2HNIResponse.
     * 
     * @param registerSubscriberV2HNIResult
     */
    public void setRegisterSubscriberV2HNIResult(RegisterSubscriberVO registerSubscriberV2HNIResult) {
        this.registerSubscriberV2HNIResult = registerSubscriberV2HNIResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberV2HNIResponse)) return false;
        RegisterSubscriberV2HNIResponse other = (RegisterSubscriberV2HNIResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberV2HNIResult==null && other.getRegisterSubscriberV2HNIResult()==null) || 
             (this.registerSubscriberV2HNIResult!=null &&
              this.registerSubscriberV2HNIResult.equals(other.getRegisterSubscriberV2HNIResult())));
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
        if (getRegisterSubscriberV2HNIResult() != null) {
            _hashCode += getRegisterSubscriberV2HNIResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberV2HNIResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberV2HNIResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberV2HNIResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberV2HNIResult"));
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
