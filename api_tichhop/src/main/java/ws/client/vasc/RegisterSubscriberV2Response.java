/**
 * RegisterSubscriberV2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberV2Response  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberV2Result;

    public RegisterSubscriberV2Response() {
    }

    public RegisterSubscriberV2Response(
           RegisterSubscriberVO registerSubscriberV2Result) {
           this.registerSubscriberV2Result = registerSubscriberV2Result;
    }


    /**
     * Gets the registerSubscriberV2Result value for this RegisterSubscriberV2Response.
     * 
     * @return registerSubscriberV2Result
     */
    public RegisterSubscriberVO getRegisterSubscriberV2Result() {
        return registerSubscriberV2Result;
    }


    /**
     * Sets the registerSubscriberV2Result value for this RegisterSubscriberV2Response.
     * 
     * @param registerSubscriberV2Result
     */
    public void setRegisterSubscriberV2Result(RegisterSubscriberVO registerSubscriberV2Result) {
        this.registerSubscriberV2Result = registerSubscriberV2Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberV2Response)) return false;
        RegisterSubscriberV2Response other = (RegisterSubscriberV2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberV2Result==null && other.getRegisterSubscriberV2Result()==null) || 
             (this.registerSubscriberV2Result!=null &&
              this.registerSubscriberV2Result.equals(other.getRegisterSubscriberV2Result())));
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
        if (getRegisterSubscriberV2Result() != null) {
            _hashCode += getRegisterSubscriberV2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberV2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberV2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberV2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberV2Result"));
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
