/**
 * RegisterKplusPromotion2021_V2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterKplusPromotion2021_V2Response  implements java.io.Serializable {
    private CommonVO registerKplusPromotion2021_V2Result;

    public RegisterKplusPromotion2021_V2Response() {
    }

    public RegisterKplusPromotion2021_V2Response(
           CommonVO registerKplusPromotion2021_V2Result) {
           this.registerKplusPromotion2021_V2Result = registerKplusPromotion2021_V2Result;
    }


    /**
     * Gets the registerKplusPromotion2021_V2Result value for this RegisterKplusPromotion2021_V2Response.
     * 
     * @return registerKplusPromotion2021_V2Result
     */
    public CommonVO getRegisterKplusPromotion2021_V2Result() {
        return registerKplusPromotion2021_V2Result;
    }


    /**
     * Sets the registerKplusPromotion2021_V2Result value for this RegisterKplusPromotion2021_V2Response.
     * 
     * @param registerKplusPromotion2021_V2Result
     */
    public void setRegisterKplusPromotion2021_V2Result(CommonVO registerKplusPromotion2021_V2Result) {
        this.registerKplusPromotion2021_V2Result = registerKplusPromotion2021_V2Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterKplusPromotion2021_V2Response)) return false;
        RegisterKplusPromotion2021_V2Response other = (RegisterKplusPromotion2021_V2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registerKplusPromotion2021_V2Result==null && other.getRegisterKplusPromotion2021_V2Result()==null) || 
             (this.registerKplusPromotion2021_V2Result!=null &&
              this.registerKplusPromotion2021_V2Result.equals(other.getRegisterKplusPromotion2021_V2Result())));
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
        if (getRegisterKplusPromotion2021_V2Result() != null) {
            _hashCode += getRegisterKplusPromotion2021_V2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterKplusPromotion2021_V2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterKplusPromotion2021_V2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerKplusPromotion2021_V2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterKplusPromotion2021_V2Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CommonVO"));
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
