/**
 * RegisterSubscriberV3Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSubscriberV3Response  implements java.io.Serializable {
    private RegisterSubscriberResp registerSubscriberV3Result;

    public RegisterSubscriberV3Response() {
    }

    public RegisterSubscriberV3Response(
           RegisterSubscriberResp registerSubscriberV3Result) {
           this.registerSubscriberV3Result = registerSubscriberV3Result;
    }


    /**
     * Gets the registerSubscriberV3Result value for this RegisterSubscriberV3Response.
     * 
     * @return registerSubscriberV3Result
     */
    public RegisterSubscriberResp getRegisterSubscriberV3Result() {
        return registerSubscriberV3Result;
    }


    /**
     * Sets the registerSubscriberV3Result value for this RegisterSubscriberV3Response.
     * 
     * @param registerSubscriberV3Result
     */
    public void setRegisterSubscriberV3Result(RegisterSubscriberResp registerSubscriberV3Result) {
        this.registerSubscriberV3Result = registerSubscriberV3Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSubscriberV3Response)) return false;
        RegisterSubscriberV3Response other = (RegisterSubscriberV3Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerSubscriberV3Result==null && other.getRegisterSubscriberV3Result()==null) || 
             (this.registerSubscriberV3Result!=null &&
              this.registerSubscriberV3Result.equals(other.getRegisterSubscriberV3Result())));
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
        if (getRegisterSubscriberV3Result() != null) {
            _hashCode += getRegisterSubscriberV3Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSubscriberV3Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSubscriberV3Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerSubscriberV3Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberV3Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterSubscriberResp"));
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
