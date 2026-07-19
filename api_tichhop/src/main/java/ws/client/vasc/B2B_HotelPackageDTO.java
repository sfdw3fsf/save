/**
 * B2B_HotelPackageDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_HotelPackageDTO  implements java.io.Serializable {
    private String HOTEL_CODE;

    private String PACK_CD;

    private int PRICE;

    public B2B_HotelPackageDTO() {
    }

    public B2B_HotelPackageDTO(
           String HOTEL_CODE,
           String PACK_CD,
           int PRICE) {
           this.HOTEL_CODE = HOTEL_CODE;
           this.PACK_CD = PACK_CD;
           this.PRICE = PRICE;
    }


    /**
     * Gets the HOTEL_CODE value for this B2B_HotelPackageDTO.
     * 
     * @return HOTEL_CODE
     */
    public String getHOTEL_CODE() {
        return HOTEL_CODE;
    }


    /**
     * Sets the HOTEL_CODE value for this B2B_HotelPackageDTO.
     * 
     * @param HOTEL_CODE
     */
    public void setHOTEL_CODE(String HOTEL_CODE) {
        this.HOTEL_CODE = HOTEL_CODE;
    }


    /**
     * Gets the PACK_CD value for this B2B_HotelPackageDTO.
     * 
     * @return PACK_CD
     */
    public String getPACK_CD() {
        return PACK_CD;
    }


    /**
     * Sets the PACK_CD value for this B2B_HotelPackageDTO.
     * 
     * @param PACK_CD
     */
    public void setPACK_CD(String PACK_CD) {
        this.PACK_CD = PACK_CD;
    }


    /**
     * Gets the PRICE value for this B2B_HotelPackageDTO.
     * 
     * @return PRICE
     */
    public int getPRICE() {
        return PRICE;
    }


    /**
     * Sets the PRICE value for this B2B_HotelPackageDTO.
     * 
     * @param PRICE
     */
    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_HotelPackageDTO)) return false;
        B2B_HotelPackageDTO other = (B2B_HotelPackageDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.HOTEL_CODE==null && other.getHOTEL_CODE()==null) || 
             (this.HOTEL_CODE!=null &&
              this.HOTEL_CODE.equals(other.getHOTEL_CODE()))) &&
            ((this.PACK_CD==null && other.getPACK_CD()==null) || 
             (this.PACK_CD!=null &&
              this.PACK_CD.equals(other.getPACK_CD()))) &&
            this.PRICE == other.getPRICE();
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
        if (getHOTEL_CODE() != null) {
            _hashCode += getHOTEL_CODE().hashCode();
        }
        if (getPACK_CD() != null) {
            _hashCode += getPACK_CD().hashCode();
        }
        _hashCode += getPRICE();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_HotelPackageDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_HotelPackageDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HOTEL_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "HOTEL_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PACK_CD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PACK_CD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PRICE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PRICE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
