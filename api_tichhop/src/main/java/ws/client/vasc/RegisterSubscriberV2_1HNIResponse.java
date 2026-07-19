/**
 * RegisterSubscriberV2_1HNIResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberV2_1HNIResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberV2_1HNIResult;

    public RegisterSubscriberV2_1HNIResponse() {
    }

    public RegisterSubscriberV2_1HNIResponse(
           RegisterSubscriberVO registerSubscriberV2_1HNIResult) {
           this.registerSubscriberV2_1HNIResult = registerSubscriberV2_1HNIResult;
    }


    /**
     * Gets the registerSubscriberV2_1HNIResult value for this RegisterSubscriberV2_1HNIResponse.
     * 
     * @return registerSubscriberV2_1HNIResult
     */
    public RegisterSubscriberVO getRegisterSubscriberV2_1HNIResult() {
        return registerSubscriberV2_1HNIResult;
    }


    /**
     * Sets the registerSubscriberV2_1HNIResult value for this RegisterSubscriberV2_1HNIResponse.
     * 
     * @param registerSubscriberV2_1HNIResult
     */
    public void setRegisterSubscriberV2_1HNIResult(RegisterSubscriberVO registerSubscriberV2_1HNIResult) {
        this.registerSubscriberV2_1HNIResult = registerSubscriberV2_1HNIResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberV2_1HNIResponse)) return false;
        RegisterSubscriberV2_1HNIResponse other = (RegisterSubscriberV2_1HNIResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberV2_1HNIResult==null && other.getRegisterSubscriberV2_1HNIResult()==null) || 
             (this.registerSubscriberV2_1HNIResult!=null &&
              this.registerSubscriberV2_1HNIResult.equals(other.getRegisterSubscriberV2_1HNIResult())));
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
        if (getRegisterSubscriberV2_1HNIResult() != null) {
            _hashCode += getRegisterSubscriberV2_1HNIResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberV2_1HNIResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberV2_1HNIResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberV2_1HNIResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberV2_1HNIResult"));
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
