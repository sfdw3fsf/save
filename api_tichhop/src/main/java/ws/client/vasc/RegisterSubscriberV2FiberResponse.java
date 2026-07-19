/**
 * RegisterSubscriberV2FiberResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberV2FiberResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberV2FiberResult;

    public RegisterSubscriberV2FiberResponse() {
    }

    public RegisterSubscriberV2FiberResponse(
           RegisterSubscriberVO registerSubscriberV2FiberResult) {
           this.registerSubscriberV2FiberResult = registerSubscriberV2FiberResult;
    }


    /**
     * Gets the registerSubscriberV2FiberResult value for this RegisterSubscriberV2FiberResponse.
     * 
     * @return registerSubscriberV2FiberResult
     */
    public RegisterSubscriberVO getRegisterSubscriberV2FiberResult() {
        return registerSubscriberV2FiberResult;
    }


    /**
     * Sets the registerSubscriberV2FiberResult value for this RegisterSubscriberV2FiberResponse.
     * 
     * @param registerSubscriberV2FiberResult
     */
    public void setRegisterSubscriberV2FiberResult(RegisterSubscriberVO registerSubscriberV2FiberResult) {
        this.registerSubscriberV2FiberResult = registerSubscriberV2FiberResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberV2FiberResponse)) return false;
        RegisterSubscriberV2FiberResponse other = (RegisterSubscriberV2FiberResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberV2FiberResult==null && other.getRegisterSubscriberV2FiberResult()==null) || 
             (this.registerSubscriberV2FiberResult!=null &&
              this.registerSubscriberV2FiberResult.equals(other.getRegisterSubscriberV2FiberResult())));
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
        if (getRegisterSubscriberV2FiberResult() != null) {
            _hashCode += getRegisterSubscriberV2FiberResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberV2FiberResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberV2FiberResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberV2FiberResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberV2FiberResult"));
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
