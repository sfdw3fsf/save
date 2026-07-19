/**
 * B2B_PackageDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_PackageDTO  implements java.io.Serializable {
    private String PACK_CD;

    private String PACK_NAME;

    private String PACK_DES;

    private int PRICE;

    public B2B_PackageDTO() {
    }

    public B2B_PackageDTO(
           String PACK_CD,
           String PACK_NAME,
           String PACK_DES,
           int PRICE) {
           this.PACK_CD = PACK_CD;
           this.PACK_NAME = PACK_NAME;
           this.PACK_DES = PACK_DES;
           this.PRICE = PRICE;
    }


    /**
     * Gets the PACK_CD value for this B2B_PackageDTO.
     * 
     * @return PACK_CD
     */
    public String getPACK_CD() {
        return PACK_CD;
    }


    /**
     * Sets the PACK_CD value for this B2B_PackageDTO.
     * 
     * @param PACK_CD
     */
    public void setPACK_CD(String PACK_CD) {
        this.PACK_CD = PACK_CD;
    }


    /**
     * Gets the PACK_NAME value for this B2B_PackageDTO.
     * 
     * @return PACK_NAME
     */
    public String getPACK_NAME() {
        return PACK_NAME;
    }


    /**
     * Sets the PACK_NAME value for this B2B_PackageDTO.
     * 
     * @param PACK_NAME
     */
    public void setPACK_NAME(String PACK_NAME) {
        this.PACK_NAME = PACK_NAME;
    }


    /**
     * Gets the PACK_DES value for this B2B_PackageDTO.
     * 
     * @return PACK_DES
     */
    public String getPACK_DES() {
        return PACK_DES;
    }


    /**
     * Sets the PACK_DES value for this B2B_PackageDTO.
     * 
     * @param PACK_DES
     */
    public void setPACK_DES(String PACK_DES) {
        this.PACK_DES = PACK_DES;
    }


    /**
     * Gets the PRICE value for this B2B_PackageDTO.
     * 
     * @return PRICE
     */
    public int getPRICE() {
        return PRICE;
    }


    /**
     * Sets the PRICE value for this B2B_PackageDTO.
     * 
     * @param PRICE
     */
    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_PackageDTO)) return false;
        B2B_PackageDTO other = (B2B_PackageDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.PACK_CD==null && other.getPACK_CD()==null) || 
             (this.PACK_CD!=null &&
              this.PACK_CD.equals(other.getPACK_CD()))) &&
            ((this.PACK_NAME==null && other.getPACK_NAME()==null) || 
             (this.PACK_NAME!=null &&
              this.PACK_NAME.equals(other.getPACK_NAME()))) &&
            ((this.PACK_DES==null && other.getPACK_DES()==null) || 
             (this.PACK_DES!=null &&
              this.PACK_DES.equals(other.getPACK_DES()))) &&
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
        if (getPACK_CD() != null) {
            _hashCode += getPACK_CD().hashCode();
        }
        if (getPACK_NAME() != null) {
            _hashCode += getPACK_NAME().hashCode();
        }
        if (getPACK_DES() != null) {
            _hashCode += getPACK_DES().hashCode();
        }
        _hashCode += getPRICE();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_PackageDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_PackageDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PACK_CD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PACK_CD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PACK_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PACK_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PACK_DES");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PACK_DES"));
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
