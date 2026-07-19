/**
 * B2B_AddHotel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_AddHotel  implements java.io.Serializable {
    private B2B_HotelDTO hotelDTO;

    public B2B_AddHotel() {
    }

    public B2B_AddHotel(
           B2B_HotelDTO hotelDTO) {
           this.hotelDTO = hotelDTO;
    }


    /**
     * Gets the hotelDTO value for this B2B_AddHotel.
     * 
     * @return hotelDTO
     */
    public B2B_HotelDTO getHotelDTO() {
        return hotelDTO;
    }


    /**
     * Sets the hotelDTO value for this B2B_AddHotel.
     * 
     * @param hotelDTO
     */
    public void setHotelDTO(B2B_HotelDTO hotelDTO) {
        this.hotelDTO = hotelDTO;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_AddHotel)) return false;
        B2B_AddHotel other = (B2B_AddHotel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.hotelDTO==null && other.getHotelDTO()==null) || 
             (this.hotelDTO!=null &&
              this.hotelDTO.equals(other.getHotelDTO())));
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
        if (getHotelDTO() != null) {
            _hashCode += getHotelDTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_AddHotel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">B2B_AddHotel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hotelDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "hotelDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_HotelDTO"));
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
