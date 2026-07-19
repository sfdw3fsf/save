/**
 * B2B_RoomDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_RoomDTO  implements java.io.Serializable {
    private String HOTEL_CODE;

    private String CODE;

    private String USERNAME;

    private String PASSWORD;

    private String START_DATE;

    private String PACK_CD;

    private String SYS_FLG;

    private String USER_TYPE;

    public B2B_RoomDTO() {
    }

    public B2B_RoomDTO(
           String HOTEL_CODE,
           String CODE,
           String USERNAME,
           String PASSWORD,
           String START_DATE,
           String PACK_CD,
           String SYS_FLG,
           String USER_TYPE) {
           this.HOTEL_CODE = HOTEL_CODE;
           this.CODE = CODE;
           this.USERNAME = USERNAME;
           this.PASSWORD = PASSWORD;
           this.START_DATE = START_DATE;
           this.PACK_CD = PACK_CD;
           this.SYS_FLG = SYS_FLG;
           this.USER_TYPE = USER_TYPE;
    }


    /**
     * Gets the HOTEL_CODE value for this B2B_RoomDTO.
     * 
     * @return HOTEL_CODE
     */
    public String getHOTEL_CODE() {
        return HOTEL_CODE;
    }


    /**
     * Sets the HOTEL_CODE value for this B2B_RoomDTO.
     * 
     * @param HOTEL_CODE
     */
    public void setHOTEL_CODE(String HOTEL_CODE) {
        this.HOTEL_CODE = HOTEL_CODE;
    }


    /**
     * Gets the CODE value for this B2B_RoomDTO.
     * 
     * @return CODE
     */
    public String getCODE() {
        return CODE;
    }


    /**
     * Sets the CODE value for this B2B_RoomDTO.
     * 
     * @param CODE
     */
    public void setCODE(String CODE) {
        this.CODE = CODE;
    }


    /**
     * Gets the USERNAME value for this B2B_RoomDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this B2B_RoomDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the PASSWORD value for this B2B_RoomDTO.
     * 
     * @return PASSWORD
     */
    public String getPASSWORD() {
        return PASSWORD;
    }


    /**
     * Sets the PASSWORD value for this B2B_RoomDTO.
     * 
     * @param PASSWORD
     */
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }


    /**
     * Gets the START_DATE value for this B2B_RoomDTO.
     * 
     * @return START_DATE
     */
    public String getSTART_DATE() {
        return START_DATE;
    }


    /**
     * Sets the START_DATE value for this B2B_RoomDTO.
     * 
     * @param START_DATE
     */
    public void setSTART_DATE(String START_DATE) {
        this.START_DATE = START_DATE;
    }


    /**
     * Gets the PACK_CD value for this B2B_RoomDTO.
     * 
     * @return PACK_CD
     */
    public String getPACK_CD() {
        return PACK_CD;
    }


    /**
     * Sets the PACK_CD value for this B2B_RoomDTO.
     * 
     * @param PACK_CD
     */
    public void setPACK_CD(String PACK_CD) {
        this.PACK_CD = PACK_CD;
    }


    /**
     * Gets the SYS_FLG value for this B2B_RoomDTO.
     * 
     * @return SYS_FLG
     */
    public String getSYS_FLG() {
        return SYS_FLG;
    }


    /**
     * Sets the SYS_FLG value for this B2B_RoomDTO.
     * 
     * @param SYS_FLG
     */
    public void setSYS_FLG(String SYS_FLG) {
        this.SYS_FLG = SYS_FLG;
    }


    /**
     * Gets the USER_TYPE value for this B2B_RoomDTO.
     * 
     * @return USER_TYPE
     */
    public String getUSER_TYPE() {
        return USER_TYPE;
    }


    /**
     * Sets the USER_TYPE value for this B2B_RoomDTO.
     * 
     * @param USER_TYPE
     */
    public void setUSER_TYPE(String USER_TYPE) {
        this.USER_TYPE = USER_TYPE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_RoomDTO)) return false;
        B2B_RoomDTO other = (B2B_RoomDTO) obj;
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
            ((this.CODE==null && other.getCODE()==null) || 
             (this.CODE!=null &&
              this.CODE.equals(other.getCODE()))) &&
            ((this.USERNAME==null && other.getUSERNAME()==null) || 
             (this.USERNAME!=null &&
              this.USERNAME.equals(other.getUSERNAME()))) &&
            ((this.PASSWORD==null && other.getPASSWORD()==null) || 
             (this.PASSWORD!=null &&
              this.PASSWORD.equals(other.getPASSWORD()))) &&
            ((this.START_DATE==null && other.getSTART_DATE()==null) || 
             (this.START_DATE!=null &&
              this.START_DATE.equals(other.getSTART_DATE()))) &&
            ((this.PACK_CD==null && other.getPACK_CD()==null) || 
             (this.PACK_CD!=null &&
              this.PACK_CD.equals(other.getPACK_CD()))) &&
            ((this.SYS_FLG==null && other.getSYS_FLG()==null) || 
             (this.SYS_FLG!=null &&
              this.SYS_FLG.equals(other.getSYS_FLG()))) &&
            ((this.USER_TYPE==null && other.getUSER_TYPE()==null) || 
             (this.USER_TYPE!=null &&
              this.USER_TYPE.equals(other.getUSER_TYPE())));
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
        if (getCODE() != null) {
            _hashCode += getCODE().hashCode();
        }
        if (getUSERNAME() != null) {
            _hashCode += getUSERNAME().hashCode();
        }
        if (getPASSWORD() != null) {
            _hashCode += getPASSWORD().hashCode();
        }
        if (getSTART_DATE() != null) {
            _hashCode += getSTART_DATE().hashCode();
        }
        if (getPACK_CD() != null) {
            _hashCode += getPACK_CD().hashCode();
        }
        if (getSYS_FLG() != null) {
            _hashCode += getSYS_FLG().hashCode();
        }
        if (getUSER_TYPE() != null) {
            _hashCode += getUSER_TYPE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_RoomDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_RoomDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HOTEL_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "HOTEL_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CODE"));
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
        elemField.setFieldName("PASSWORD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PASSWORD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("START_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "START_DATE"));
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
        elemField.setFieldName("SYS_FLG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SYS_FLG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USER_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "USER_TYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
