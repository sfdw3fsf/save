/**
 * STB_Prom_ActiveDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class STB_Prom_ActiveDTO  implements java.io.Serializable {
    private String PROGRAM;

    private String USERNAME;

    private boolean IS_FIRST;

    private String STB_TYPE;

    private String STB_SERIAL;

    private String STB_DATE;

    private String NOTE;

    public STB_Prom_ActiveDTO() {
    }

    public STB_Prom_ActiveDTO(
           String PROGRAM,
           String USERNAME,
           boolean IS_FIRST,
           String STB_TYPE,
           String STB_SERIAL,
           String STB_DATE,
           String NOTE) {
           this.PROGRAM = PROGRAM;
           this.USERNAME = USERNAME;
           this.IS_FIRST = IS_FIRST;
           this.STB_TYPE = STB_TYPE;
           this.STB_SERIAL = STB_SERIAL;
           this.STB_DATE = STB_DATE;
           this.NOTE = NOTE;
    }


    /**
     * Gets the PROGRAM value for this STB_Prom_ActiveDTO.
     * 
     * @return PROGRAM
     */
    public String getPROGRAM() {
        return PROGRAM;
    }


    /**
     * Sets the PROGRAM value for this STB_Prom_ActiveDTO.
     * 
     * @param PROGRAM
     */
    public void setPROGRAM(String PROGRAM) {
        this.PROGRAM = PROGRAM;
    }


    /**
     * Gets the USERNAME value for this STB_Prom_ActiveDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this STB_Prom_ActiveDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the IS_FIRST value for this STB_Prom_ActiveDTO.
     * 
     * @return IS_FIRST
     */
    public boolean isIS_FIRST() {
        return IS_FIRST;
    }


    /**
     * Sets the IS_FIRST value for this STB_Prom_ActiveDTO.
     * 
     * @param IS_FIRST
     */
    public void setIS_FIRST(boolean IS_FIRST) {
        this.IS_FIRST = IS_FIRST;
    }


    /**
     * Gets the STB_TYPE value for this STB_Prom_ActiveDTO.
     * 
     * @return STB_TYPE
     */
    public String getSTB_TYPE() {
        return STB_TYPE;
    }


    /**
     * Sets the STB_TYPE value for this STB_Prom_ActiveDTO.
     * 
     * @param STB_TYPE
     */
    public void setSTB_TYPE(String STB_TYPE) {
        this.STB_TYPE = STB_TYPE;
    }


    /**
     * Gets the STB_SERIAL value for this STB_Prom_ActiveDTO.
     * 
     * @return STB_SERIAL
     */
    public String getSTB_SERIAL() {
        return STB_SERIAL;
    }


    /**
     * Sets the STB_SERIAL value for this STB_Prom_ActiveDTO.
     * 
     * @param STB_SERIAL
     */
    public void setSTB_SERIAL(String STB_SERIAL) {
        this.STB_SERIAL = STB_SERIAL;
    }


    /**
     * Gets the STB_DATE value for this STB_Prom_ActiveDTO.
     * 
     * @return STB_DATE
     */
    public String getSTB_DATE() {
        return STB_DATE;
    }


    /**
     * Sets the STB_DATE value for this STB_Prom_ActiveDTO.
     * 
     * @param STB_DATE
     */
    public void setSTB_DATE(String STB_DATE) {
        this.STB_DATE = STB_DATE;
    }


    /**
     * Gets the NOTE value for this STB_Prom_ActiveDTO.
     * 
     * @return NOTE
     */
    public String getNOTE() {
        return NOTE;
    }


    /**
     * Sets the NOTE value for this STB_Prom_ActiveDTO.
     * 
     * @param NOTE
     */
    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof STB_Prom_ActiveDTO)) return false;
        STB_Prom_ActiveDTO other = (STB_Prom_ActiveDTO) obj;
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
            this.IS_FIRST == other.isIS_FIRST() &&
            ((this.STB_TYPE==null && other.getSTB_TYPE()==null) || 
             (this.STB_TYPE!=null &&
              this.STB_TYPE.equals(other.getSTB_TYPE()))) &&
            ((this.STB_SERIAL==null && other.getSTB_SERIAL()==null) || 
             (this.STB_SERIAL!=null &&
              this.STB_SERIAL.equals(other.getSTB_SERIAL()))) &&
            ((this.STB_DATE==null && other.getSTB_DATE()==null) || 
             (this.STB_DATE!=null &&
              this.STB_DATE.equals(other.getSTB_DATE()))) &&
            ((this.NOTE==null && other.getNOTE()==null) || 
             (this.NOTE!=null &&
              this.NOTE.equals(other.getNOTE())));
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
        _hashCode += (isIS_FIRST() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getSTB_TYPE() != null) {
            _hashCode += getSTB_TYPE().hashCode();
        }
        if (getSTB_SERIAL() != null) {
            _hashCode += getSTB_SERIAL().hashCode();
        }
        if (getSTB_DATE() != null) {
            _hashCode += getSTB_DATE().hashCode();
        }
        if (getNOTE() != null) {
            _hashCode += getNOTE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(STB_Prom_ActiveDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "STB_Prom_ActiveDTO"));
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
        elemField.setFieldName("IS_FIRST");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IS_FIRST"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
