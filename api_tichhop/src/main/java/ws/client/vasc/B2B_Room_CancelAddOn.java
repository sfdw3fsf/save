/**
 * B2B_Room_CancelAddOn.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_Room_CancelAddOn  implements java.io.Serializable {
    private B2B_RoomAddOnDTO rAddOnDTO;

    public B2B_Room_CancelAddOn() {
    }

    public B2B_Room_CancelAddOn(
           B2B_RoomAddOnDTO rAddOnDTO) {
           this.rAddOnDTO = rAddOnDTO;
    }


    /**
     * Gets the rAddOnDTO value for this B2B_Room_CancelAddOn.
     * 
     * @return rAddOnDTO
     */
    public B2B_RoomAddOnDTO getRAddOnDTO() {
        return rAddOnDTO;
    }


    /**
     * Sets the rAddOnDTO value for this B2B_Room_CancelAddOn.
     * 
     * @param rAddOnDTO
     */
    public void setRAddOnDTO(B2B_RoomAddOnDTO rAddOnDTO) {
        this.rAddOnDTO = rAddOnDTO;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_Room_CancelAddOn)) return false;
        B2B_Room_CancelAddOn other = (B2B_Room_CancelAddOn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rAddOnDTO==null && other.getRAddOnDTO()==null) || 
             (this.rAddOnDTO!=null &&
              this.rAddOnDTO.equals(other.getRAddOnDTO())));
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
        if (getRAddOnDTO() != null) {
            _hashCode += getRAddOnDTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_Room_CancelAddOn.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">B2B_Room_CancelAddOn"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RAddOnDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "rAddOnDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_RoomAddOnDTO"));
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
