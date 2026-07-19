/**
 * RegisterSubscriberV2_1FiberResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberV2_1FiberResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberV2_1FiberResult;

    public RegisterSubscriberV2_1FiberResponse() {
    }

    public RegisterSubscriberV2_1FiberResponse(
           RegisterSubscriberVO registerSubscriberV2_1FiberResult) {
           this.registerSubscriberV2_1FiberResult = registerSubscriberV2_1FiberResult;
    }


    /**
     * Gets the registerSubscriberV2_1FiberResult value for this RegisterSubscriberV2_1FiberResponse.
     * 
     * @return registerSubscriberV2_1FiberResult
     */
    public RegisterSubscriberVO getRegisterSubscriberV2_1FiberResult() {
        return registerSubscriberV2_1FiberResult;
    }


    /**
     * Sets the registerSubscriberV2_1FiberResult value for this RegisterSubscriberV2_1FiberResponse.
     * 
     * @param registerSubscriberV2_1FiberResult
     */
    public void setRegisterSubscriberV2_1FiberResult(RegisterSubscriberVO registerSubscriberV2_1FiberResult) {
        this.registerSubscriberV2_1FiberResult = registerSubscriberV2_1FiberResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberV2_1FiberResponse)) return false;
        RegisterSubscriberV2_1FiberResponse other = (RegisterSubscriberV2_1FiberResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberV2_1FiberResult==null && other.getRegisterSubscriberV2_1FiberResult()==null) || 
             (this.registerSubscriberV2_1FiberResult!=null &&
              this.registerSubscriberV2_1FiberResult.equals(other.getRegisterSubscriberV2_1FiberResult())));
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
        if (getRegisterSubscriberV2_1FiberResult() != null) {
            _hashCode += getRegisterSubscriberV2_1FiberResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberV2_1FiberResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberV2_1FiberResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberV2_1FiberResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberV2_1FiberResult"));
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
