/**
 * B2B_RoomAddOnDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_RoomAddOnDTO  implements java.io.Serializable {
    private String HOTEL_CODE;

    private String ROOM_CODE;

    private String USERNAME;

    private String REGISTDATE;

    private String CANCELDATE;

    private String ADDON_CODE;

    private String ZIPCD;

    private int UNITPRICE;

    public B2B_RoomAddOnDTO() {
    }

    public B2B_RoomAddOnDTO(
           String HOTEL_CODE,
           String ROOM_CODE,
           String USERNAME,
           String REGISTDATE,
           String CANCELDATE,
           String ADDON_CODE,
           String ZIPCD,
           int UNITPRICE) {
           this.HOTEL_CODE = HOTEL_CODE;
           this.ROOM_CODE = ROOM_CODE;
           this.USERNAME = USERNAME;
           this.REGISTDATE = REGISTDATE;
           this.CANCELDATE = CANCELDATE;
           this.ADDON_CODE = ADDON_CODE;
           this.ZIPCD = ZIPCD;
           this.UNITPRICE = UNITPRICE;
    }


    /**
     * Gets the HOTEL_CODE value for this B2B_RoomAddOnDTO.
     * 
     * @return HOTEL_CODE
     */
    public String getHOTEL_CODE() {
        return HOTEL_CODE;
    }


    /**
     * Sets the HOTEL_CODE value for this B2B_RoomAddOnDTO.
     * 
     * @param HOTEL_CODE
     */
    public void setHOTEL_CODE(String HOTEL_CODE) {
        this.HOTEL_CODE = HOTEL_CODE;
    }


    /**
     * Gets the ROOM_CODE value for this B2B_RoomAddOnDTO.
     * 
     * @return ROOM_CODE
     */
    public String getROOM_CODE() {
        return ROOM_CODE;
    }


    /**
     * Sets the ROOM_CODE value for this B2B_RoomAddOnDTO.
     * 
     * @param ROOM_CODE
     */
    public void setROOM_CODE(String ROOM_CODE) {
        this.ROOM_CODE = ROOM_CODE;
    }


    /**
     * Gets the USERNAME value for this B2B_RoomAddOnDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this B2B_RoomAddOnDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the REGISTDATE value for this B2B_RoomAddOnDTO.
     * 
     * @return REGISTDATE
     */
    public String getREGISTDATE() {
        return REGISTDATE;
    }


    /**
     * Sets the REGISTDATE value for this B2B_RoomAddOnDTO.
     * 
     * @param REGISTDATE
     */
    public void setREGISTDATE(String REGISTDATE) {
        this.REGISTDATE = REGISTDATE;
    }


    /**
     * Gets the CANCELDATE value for this B2B_RoomAddOnDTO.
     * 
     * @return CANCELDATE
     */
    public String getCANCELDATE() {
        return CANCELDATE;
    }


    /**
     * Sets the CANCELDATE value for this B2B_RoomAddOnDTO.
     * 
     * @param CANCELDATE
     */
    public void setCANCELDATE(String CANCELDATE) {
        this.CANCELDATE = CANCELDATE;
    }


    /**
     * Gets the ADDON_CODE value for this B2B_RoomAddOnDTO.
     * 
     * @return ADDON_CODE
     */
    public String getADDON_CODE() {
        return ADDON_CODE;
    }


    /**
     * Sets the ADDON_CODE value for this B2B_RoomAddOnDTO.
     * 
     * @param ADDON_CODE
     */
    public void setADDON_CODE(String ADDON_CODE) {
        this.ADDON_CODE = ADDON_CODE;
    }


    /**
     * Gets the ZIPCD value for this B2B_RoomAddOnDTO.
     * 
     * @return ZIPCD
     */
    public String getZIPCD() {
        return ZIPCD;
    }


    /**
     * Sets the ZIPCD value for this B2B_RoomAddOnDTO.
     * 
     * @param ZIPCD
     */
    public void setZIPCD(String ZIPCD) {
        this.ZIPCD = ZIPCD;
    }


    /**
     * Gets the UNITPRICE value for this B2B_RoomAddOnDTO.
     * 
     * @return UNITPRICE
     */
    public int getUNITPRICE() {
        return UNITPRICE;
    }


    /**
     * Sets the UNITPRICE value for this B2B_RoomAddOnDTO.
     * 
     * @param UNITPRICE
     */
    public void setUNITPRICE(int UNITPRICE) {
        this.UNITPRICE = UNITPRICE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_RoomAddOnDTO)) return false;
        B2B_RoomAddOnDTO other = (B2B_RoomAddOnDTO) obj;
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
            ((this.ROOM_CODE==null && other.getROOM_CODE()==null) || 
             (this.ROOM_CODE!=null &&
              this.ROOM_CODE.equals(other.getROOM_CODE()))) &&
            ((this.USERNAME==null && other.getUSERNAME()==null) || 
             (this.USERNAME!=null &&
              this.USERNAME.equals(other.getUSERNAME()))) &&
            ((this.REGISTDATE==null && other.getREGISTDATE()==null) || 
             (this.REGISTDATE!=null &&
              this.REGISTDATE.equals(other.getREGISTDATE()))) &&
            ((this.CANCELDATE==null && other.getCANCELDATE()==null) || 
             (this.CANCELDATE!=null &&
              this.CANCELDATE.equals(other.getCANCELDATE()))) &&
            ((this.ADDON_CODE==null && other.getADDON_CODE()==null) || 
             (this.ADDON_CODE!=null &&
              this.ADDON_CODE.equals(other.getADDON_CODE()))) &&
            ((this.ZIPCD==null && other.getZIPCD()==null) || 
             (this.ZIPCD!=null &&
              this.ZIPCD.equals(other.getZIPCD()))) &&
            this.UNITPRICE == other.getUNITPRICE();
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
        if (getROOM_CODE() != null) {
            _hashCode += getROOM_CODE().hashCode();
        }
        if (getUSERNAME() != null) {
            _hashCode += getUSERNAME().hashCode();
        }
        if (getREGISTDATE() != null) {
            _hashCode += getREGISTDATE().hashCode();
        }
        if (getCANCELDATE() != null) {
            _hashCode += getCANCELDATE().hashCode();
        }
        if (getADDON_CODE() != null) {
            _hashCode += getADDON_CODE().hashCode();
        }
        if (getZIPCD() != null) {
            _hashCode += getZIPCD().hashCode();
        }
        _hashCode += getUNITPRICE();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_RoomAddOnDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_RoomAddOnDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HOTEL_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "HOTEL_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ROOM_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ROOM_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "USERNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("REGISTDATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "REGISTDATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CANCELDATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CANCELDATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ADDON_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ADDON_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ZIPCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ZIPCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UNITPRICE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UNITPRICE"));
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
