/**
 * RegistKplus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegistKplus  implements java.io.Serializable {
    private KplusDTO kplusDTO;

    public RegistKplus() {
    }

    public RegistKplus(
           KplusDTO kplusDTO) {
           this.kplusDTO = kplusDTO;
    }


    /**
     * Gets the kplusDTO value for this RegistKplus.
     * 
     * @return kplusDTO
     */
    public KplusDTO getKplusDTO() {
        return kplusDTO;
    }


    /**
     * Sets the kplusDTO value for this RegistKplus.
     * 
     * @param kplusDTO
     */
    public void setKplusDTO(KplusDTO kplusDTO) {
        this.kplusDTO = kplusDTO;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegistKplus)) return false;
        RegistKplus other = (RegistKplus) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.kplusDTO==null && other.getKplusDTO()==null) || 
             (this.kplusDTO!=null &&
              this.kplusDTO.equals(other.getKplusDTO())));
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
        if (getKplusDTO() != null) {
            _hashCode += getKplusDTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistKplus.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegistKplus"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("kplusDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "kplusDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "KplusDTO"));
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
