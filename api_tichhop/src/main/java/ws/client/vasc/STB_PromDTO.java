/**
 * STB_PromDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class STB_PromDTO  implements java.io.Serializable {
    private String PROGRAM;

    private String USERNAME;

    private String STB_TYPE;

    private String STB_SERIAL;

    private String FIBER_MYTV;

    private String NOTE;

    public STB_PromDTO() {
    }

    public STB_PromDTO(
           String PROGRAM,
           String USERNAME,
           String STB_TYPE,
           String STB_SERIAL,
           String FIBER_MYTV,
           String NOTE) {
           this.PROGRAM = PROGRAM;
           this.USERNAME = USERNAME;
           this.STB_TYPE = STB_TYPE;
           this.STB_SERIAL = STB_SERIAL;
           this.FIBER_MYTV = FIBER_MYTV;
           this.NOTE = NOTE;
    }


    /**
     * Gets the PROGRAM value for this STB_PromDTO.
     * 
     * @return PROGRAM
     */
    public String getPROGRAM() {
        return PROGRAM;
    }


    /**
     * Sets the PROGRAM value for this STB_PromDTO.
     * 
     * @param PROGRAM
     */
    public void setPROGRAM(String PROGRAM) {
        this.PROGRAM = PROGRAM;
    }


    /**
     * Gets the USERNAME value for this STB_PromDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this STB_PromDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the STB_TYPE value for this STB_PromDTO.
     * 
     * @return STB_TYPE
     */
    public String getSTB_TYPE() {
        return STB_TYPE;
    }


    /**
     * Sets the STB_TYPE value for this STB_PromDTO.
     * 
     * @param STB_TYPE
     */
    public void setSTB_TYPE(String STB_TYPE) {
        this.STB_TYPE = STB_TYPE;
    }


    /**
     * Gets the STB_SERIAL value for this STB_PromDTO.
     * 
     * @return STB_SERIAL
     */
    public String getSTB_SERIAL() {
        return STB_SERIAL;
    }


    /**
     * Sets the STB_SERIAL value for this STB_PromDTO.
     * 
     * @param STB_SERIAL
     */
    public void setSTB_SERIAL(String STB_SERIAL) {
        this.STB_SERIAL = STB_SERIAL;
    }


    /**
     * Gets the FIBER_MYTV value for this STB_PromDTO.
     * 
     * @return FIBER_MYTV
     */
    public String getFIBER_MYTV() {
        return FIBER_MYTV;
    }


    /**
     * Sets the FIBER_MYTV value for this STB_PromDTO.
     * 
     * @param FIBER_MYTV
     */
    public void setFIBER_MYTV(String FIBER_MYTV) {
        this.FIBER_MYTV = FIBER_MYTV;
    }


    /**
     * Gets the NOTE value for this STB_PromDTO.
     * 
     * @return NOTE
     */
    public String getNOTE() {
        return NOTE;
    }


    /**
     * Sets the NOTE value for this STB_PromDTO.
     * 
     * @param NOTE
     */
    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof STB_PromDTO)) return false;
        STB_PromDTO other = (STB_PromDTO) obj;
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
            ((this.FIBER_MYTV==null && other.getFIBER_MYTV()==null) || 
             (this.FIBER_MYTV!=null &&
              this.FIBER_MYTV.equals(other.getFIBER_MYTV()))) &&
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
        if (getSTB_TYPE() != null) {
            _hashCode += getSTB_TYPE().hashCode();
        }
        if (getSTB_SERIAL() != null) {
            _hashCode += getSTB_SERIAL().hashCode();
        }
        if (getFIBER_MYTV() != null) {
            _hashCode += getFIBER_MYTV().hashCode();
        }
        if (getNOTE() != null) {
            _hashCode += getNOTE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(STB_PromDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "STB_PromDTO"));
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
        elemField.setFieldName("FIBER_MYTV");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "FIBER_MYTV"));
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
