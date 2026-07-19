/**
 * RegisterSubscriberResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberResponse  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberResult;

    public RegisterSubscriberResponse() {
    }

    public RegisterSubscriberResponse(
           RegisterSubscriberVO registerSubscriberResult) {
           this.registerSubscriberResult = registerSubscriberResult;
    }


    /**
     * Gets the registerSubscriberResult value for this RegisterSubscriberResponse.
     * 
     * @return registerSubscriberResult
     */
    public RegisterSubscriberVO getRegisterSubscriberResult() {
        return registerSubscriberResult;
    }


    /**
     * Sets the registerSubscriberResult value for this RegisterSubscriberResponse.
     * 
     * @param registerSubscriberResult
     */
    public void setRegisterSubscriberResult(RegisterSubscriberVO registerSubscriberResult) {
        this.registerSubscriberResult = registerSubscriberResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberResponse)) return false;
        RegisterSubscriberResponse other = (RegisterSubscriberResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberResult==null && other.getRegisterSubscriberResult()==null) || 
             (this.registerSubscriberResult!=null &&
              this.registerSubscriberResult.equals(other.getRegisterSubscriberResult())));
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
        if (getRegisterSubscriberResult() != null) {
            _hashCode += getRegisterSubscriberResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberResult"));
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
