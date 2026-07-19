/**
 * STB_Prom_BackDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class STB_Prom_BackDTO  implements java.io.Serializable {
    private String PROGRAM;

    private String USERNAME;

    private String STB_TYPE;

    private String STB_SERIAL;

    private String STB_SERIAL_NEW;

    private String STB_DATE;

    private String NOTE;

    private boolean IS_BACK;

    private String NOTBACK_REASON;

    private boolean REUSE;

    private boolean NOTREUSE_DAMAGE;

    private String NOTREUSE_REASON;

    public STB_Prom_BackDTO() {
    }

    public STB_Prom_BackDTO(
           String PROGRAM,
           String USERNAME,
           String STB_TYPE,
           String STB_SERIAL,
           String STB_SERIAL_NEW,
           String STB_DATE,
           String NOTE,
           boolean IS_BACK,
           String NOTBACK_REASON,
           boolean REUSE,
           boolean NOTREUSE_DAMAGE,
           String NOTREUSE_REASON) {
           this.PROGRAM = PROGRAM;
           this.USERNAME = USERNAME;
           this.STB_TYPE = STB_TYPE;
           this.STB_SERIAL = STB_SERIAL;
           this.STB_SERIAL_NEW = STB_SERIAL_NEW;
           this.STB_DATE = STB_DATE;
           this.NOTE = NOTE;
           this.IS_BACK = IS_BACK;
           this.NOTBACK_REASON = NOTBACK_REASON;
           this.REUSE = REUSE;
           this.NOTREUSE_DAMAGE = NOTREUSE_DAMAGE;
           this.NOTREUSE_REASON = NOTREUSE_REASON;
    }


    /**
     * Gets the PROGRAM value for this STB_Prom_BackDTO.
     * 
     * @return PROGRAM
     */
    public String getPROGRAM() {
        return PROGRAM;
    }


    /**
     * Sets the PROGRAM value for this STB_Prom_BackDTO.
     * 
     * @param PROGRAM
     */
    public void setPROGRAM(String PROGRAM) {
        this.PROGRAM = PROGRAM;
    }


    /**
     * Gets the USERNAME value for this STB_Prom_BackDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this STB_Prom_BackDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the STB_TYPE value for this STB_Prom_BackDTO.
     * 
     * @return STB_TYPE
     */
    public String getSTB_TYPE() {
        return STB_TYPE;
    }


    /**
     * Sets the STB_TYPE value for this STB_Prom_BackDTO.
     * 
     * @param STB_TYPE
     */
    public void setSTB_TYPE(String STB_TYPE) {
        this.STB_TYPE = STB_TYPE;
    }


    /**
     * Gets the STB_SERIAL value for this STB_Prom_BackDTO.
     * 
     * @return STB_SERIAL
     */
    public String getSTB_SERIAL() {
        return STB_SERIAL;
    }


    /**
     * Sets the STB_SERIAL value for this STB_Prom_BackDTO.
     * 
     * @param STB_SERIAL
     */
    public void setSTB_SERIAL(String STB_SERIAL) {
        this.STB_SERIAL = STB_SERIAL;
    }


    /**
     * Gets the STB_SERIAL_NEW value for this STB_Prom_BackDTO.
     * 
     * @return STB_SERIAL_NEW
     */
    public String getSTB_SERIAL_NEW() {
        return STB_SERIAL_NEW;
    }


    /**
     * Sets the STB_SERIAL_NEW value for this STB_Prom_BackDTO.
     * 
     * @param STB_SERIAL_NEW
     */
    public void setSTB_SERIAL_NEW(String STB_SERIAL_NEW) {
        this.STB_SERIAL_NEW = STB_SERIAL_NEW;
    }


    /**
     * Gets the STB_DATE value for this STB_Prom_BackDTO.
     * 
     * @return STB_DATE
     */
    public String getSTB_DATE() {
        return STB_DATE;
    }


    /**
     * Sets the STB_DATE value for this STB_Prom_BackDTO.
     * 
     * @param STB_DATE
     */
    public void setSTB_DATE(String STB_DATE) {
        this.STB_DATE = STB_DATE;
    }


    /**
     * Gets the NOTE value for this STB_Prom_BackDTO.
     * 
     * @return NOTE
     */
    public String getNOTE() {
        return NOTE;
    }


    /**
     * Sets the NOTE value for this STB_Prom_BackDTO.
     * 
     * @param NOTE
     */
    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }


    /**
     * Gets the IS_BACK value for this STB_Prom_BackDTO.
     * 
     * @return IS_BACK
     */
    public boolean isIS_BACK() {
        return IS_BACK;
    }


    /**
     * Sets the IS_BACK value for this STB_Prom_BackDTO.
     * 
     * @param IS_BACK
     */
    public void setIS_BACK(boolean IS_BACK) {
        this.IS_BACK = IS_BACK;
    }


    /**
     * Gets the NOTBACK_REASON value for this STB_Prom_BackDTO.
     * 
     * @return NOTBACK_REASON
     */
    public String getNOTBACK_REASON() {
        return NOTBACK_REASON;
    }


    /**
     * Sets the NOTBACK_REASON value for this STB_Prom_BackDTO.
     * 
     * @param NOTBACK_REASON
     */
    public void setNOTBACK_REASON(String NOTBACK_REASON) {
        this.NOTBACK_REASON = NOTBACK_REASON;
    }


    /**
     * Gets the REUSE value for this STB_Prom_BackDTO.
     * 
     * @return REUSE
     */
    public boolean isREUSE() {
        return REUSE;
    }


    /**
     * Sets the REUSE value for this STB_Prom_BackDTO.
     * 
     * @param REUSE
     */
    public void setREUSE(boolean REUSE) {
        this.REUSE = REUSE;
    }


    /**
     * Gets the NOTREUSE_DAMAGE value for this STB_Prom_BackDTO.
     * 
     * @return NOTREUSE_DAMAGE
     */
    public boolean isNOTREUSE_DAMAGE() {
        return NOTREUSE_DAMAGE;
    }


    /**
     * Sets the NOTREUSE_DAMAGE value for this STB_Prom_BackDTO.
     * 
     * @param NOTREUSE_DAMAGE
     */
    public void setNOTREUSE_DAMAGE(boolean NOTREUSE_DAMAGE) {
        this.NOTREUSE_DAMAGE = NOTREUSE_DAMAGE;
    }


    /**
     * Gets the NOTREUSE_REASON value for this STB_Prom_BackDTO.
     * 
     * @return NOTREUSE_REASON
     */
    public String getNOTREUSE_REASON() {
        return NOTREUSE_REASON;
    }


    /**
     * Sets the NOTREUSE_REASON value for this STB_Prom_BackDTO.
     * 
     * @param NOTREUSE_REASON
     */
    public void setNOTREUSE_REASON(String NOTREUSE_REASON) {
        this.NOTREUSE_REASON = NOTREUSE_REASON;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof STB_Prom_BackDTO)) return false;
        STB_Prom_BackDTO other = (STB_Prom_BackDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.PROGRAM==null && other.getPROGRAM()==null) || 
             (this.PROGRAM!=null &&
              this.PROGRAM.equals(other.getPROGRAM()))) &&
            ((this.USERNAME==null && other.getUSERNAME()==null) || 
             (this.USERNAME!=null &&
              this.USERNAME.equals(other.getUSERNAME()))) &&
            ((this.STB_TYPE==null && other.getSTB_TYPE()==null) || 
             (this.STB_TYPE!=null &&
              this.STB_TYPE.equals(other.getSTB_TYPE()))) &&
            ((this.STB_SERIAL==null && other.getSTB_SERIAL()==null) || 
             (this.STB_SERIAL!=null &&
              this.STB_SERIAL.equals(other.getSTB_SERIAL()))) &&
            ((this.STB_SERIAL_NEW==null && other.getSTB_SERIAL_NEW()==null) || 
             (this.STB_SERIAL_NEW!=null &&
              this.STB_SERIAL_NEW.equals(other.getSTB_SERIAL_NEW()))) &&
            ((this.STB_DATE==null && other.getSTB_DATE()==null) || 
             (this.STB_DATE!=null &&
              this.STB_DATE.equals(other.getSTB_DATE()))) &&
            ((this.NOTE==null && other.getNOTE()==null) || 
             (this.NOTE!=null &&
              this.NOTE.equals(other.getNOTE()))) &&
            this.IS_BACK == other.isIS_BACK() &&
            ((this.NOTBACK_REASON==null && other.getNOTBACK_REASON()==null) || 
             (this.NOTBACK_REASON!=null &&
              this.NOTBACK_REASON.equals(other.getNOTBACK_REASON()))) &&
            this.REUSE == other.isREUSE() &&
            this.NOTREUSE_DAMAGE == other.isNOTREUSE_DAMAGE() &&
            ((this.NOTREUSE_REASON==null && other.getNOTREUSE_REASON()==null) || 
             (this.NOTREUSE_REASON!=null &&
              this.NOTREUSE_REASON.equals(other.getNOTREUSE_REASON())));
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
        if (getPROGRAM() != null) {
            _hashCode += getPROGRAM().hashCode();
        }
        if (getUSERNAME() != null) {
            _hashCode += getUSERNAME().hashCode();
        }
        if (getSTB_TYPE() != null) {
            _hashCode += getSTB_TYPE().hashCode();
        }
        if (getSTB_SERIAL() != null) {
            _hashCode += getSTB_SERIAL().hashCode();
        }
        if (getSTB_SERIAL_NEW() != null) {
            _hashCode += getSTB_SERIAL_NEW().hashCode();
        }
        if (getSTB_DATE() != null) {
            _hashCode += getSTB_DATE().hashCode();
        }
        if (getNOTE() != null) {
            _hashCode += getNOTE().hashCode();
        }
        _hashCode += (isIS_BACK() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getNOTBACK_REASON() != null) {
            _hashCode += getNOTBACK_REASON().hashCode();
        }
        _hashCode += (isREUSE() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isNOTREUSE_DAMAGE() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getNOTREUSE_REASON() != null) {
            _hashCode += getNOTREUSE_REASON().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(STB_Prom_BackDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "STB_Prom_BackDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PROGRAM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PROGRAM"));
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
        elemField.setFieldName("STB_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "STB_TYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STB_SERIAL");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "STB_SERIAL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STB_SERIAL_NEW");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "STB_SERIAL_NEW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STB_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "STB_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOTE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NOTE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IS_BACK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IS_BACK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOTBACK_REASON");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NOTBACK_REASON"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("REUSE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "REUSE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOTREUSE_DAMAGE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NOTREUSE_DAMAGE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOTREUSE_REASON");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NOTREUSE_REASON"));
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
