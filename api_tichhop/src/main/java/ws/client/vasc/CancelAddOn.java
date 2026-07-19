/**
 * CancelAddOn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CancelAddOn  implements java.io.Serializable {
    private CancelAddOnDTO addonDTO;

    public CancelAddOn() {
    }

    public CancelAddOn(
           CancelAddOnDTO addonDTO) {
           this.addonDTO = addonDTO;
    }


    /**
     * Gets the addonDTO value for this CancelAddOn.
     * 
     * @return addonDTO
     */
    public CancelAddOnDTO getAddonDTO() {
        return addonDTO;
    }


    /**
     * Sets the addonDTO value for this CancelAddOn.
     * 
     * @param addonDTO
     */
    public void setAddonDTO(CancelAddOnDTO addonDTO) {
        this.addonDTO = addonDTO;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CancelAddOn)) return false;
        CancelAddOn other = (CancelAddOn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addonDTO==null && other.getAddonDTO()==null) || 
             (this.addonDTO!=null &&
              this.addonDTO.equals(other.getAddonDTO())));
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
        if (getAddonDTO() != null) {
            _hashCode += getAddonDTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CancelAddOn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">CancelAddOn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addonDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "addonDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CancelAddOnDTO"));
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
