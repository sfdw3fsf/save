/**
 * RegisterSelfCare.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterSelfCare  implements java.io.Serializable {
    private SelfCareDTO scDTO;

    public RegisterSelfCare() {
    }

    public RegisterSelfCare(
           SelfCareDTO scDTO) {
           this.scDTO = scDTO;
    }


    /**
     * Gets the scDTO value for this RegisterSelfCare.
     * 
     * @return scDTO
     */
    public SelfCareDTO getScDTO() {
        return scDTO;
    }


    /**
     * Sets the scDTO value for this RegisterSelfCare.
     * 
     * @param scDTO
     */
    public void setScDTO(SelfCareDTO scDTO) {
        this.scDTO = scDTO;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterSelfCare)) return false;
        RegisterSelfCare other = (RegisterSelfCare) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.scDTO==null && other.getScDTO()==null) || 
             (this.scDTO!=null &&
              this.scDTO.equals(other.getScDTO())));
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
        if (getScDTO() != null) {
            _hashCode += getScDTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterSelfCare.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterSelfCare"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "scDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "SelfCareDTO"));
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
