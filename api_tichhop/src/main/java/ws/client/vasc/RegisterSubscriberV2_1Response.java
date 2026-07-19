/**
 * RegisterSubscriberV2_1Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberV2_1Response  implements java.io.Serializable {
    private RegisterSubscriberVO registerSubscriberV2_1Result;

    public RegisterSubscriberV2_1Response() {
    }

    public RegisterSubscriberV2_1Response(
           RegisterSubscriberVO registerSubscriberV2_1Result) {
           this.registerSubscriberV2_1Result = registerSubscriberV2_1Result;
    }


    /**
     * Gets the registerSubscriberV2_1Result value for this RegisterSubscriberV2_1Response.
     * 
     * @return registerSubscriberV2_1Result
     */
    public RegisterSubscriberVO getRegisterSubscriberV2_1Result() {
        return registerSubscriberV2_1Result;
    }


    /**
     * Sets the registerSubscriberV2_1Result value for this RegisterSubscriberV2_1Response.
     * 
     * @param registerSubscriberV2_1Result
     */
    public void setRegisterSubscriberV2_1Result(RegisterSubscriberVO registerSubscriberV2_1Result) {
        this.registerSubscriberV2_1Result = registerSubscriberV2_1Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberV2_1Response)) return false;
        RegisterSubscriberV2_1Response other = (RegisterSubscriberV2_1Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberV2_1Result==null && other.getRegisterSubscriberV2_1Result()==null) || 
             (this.registerSubscriberV2_1Result!=null &&
              this.registerSubscriberV2_1Result.equals(other.getRegisterSubscriberV2_1Result())));
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
        if (getRegisterSubscriberV2_1Result() != null) {
            _hashCode += getRegisterSubscriberV2_1Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberV2_1Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberV2_1Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberV2_1Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberV2_1Result"));
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
