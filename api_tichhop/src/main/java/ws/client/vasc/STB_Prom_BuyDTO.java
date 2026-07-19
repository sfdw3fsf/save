/**
 * STB_Prom_BuyDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class STB_Prom_BuyDTO  implements java.io.Serializable {
    private String PROGRAM;

    private String USERNAME;

    private String BILLDATE;

    public STB_Prom_BuyDTO() {
    }

    public STB_Prom_BuyDTO(
           String PROGRAM,
           String USERNAME,
           String BILLDATE) {
           this.PROGRAM = PROGRAM;
           this.USERNAME = USERNAME;
           this.BILLDATE = BILLDATE;
    }


    /**
     * Gets the PROGRAM value for this STB_Prom_BuyDTO.
     * 
     * @return PROGRAM
     */
    public String getPROGRAM() {
        return PROGRAM;
    }


    /**
     * Sets the PROGRAM value for this STB_Prom_BuyDTO.
     * 
     * @param PROGRAM
     */
    public void setPROGRAM(String PROGRAM) {
        this.PROGRAM = PROGRAM;
    }


    /**
     * Gets the USERNAME value for this STB_Prom_BuyDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this STB_Prom_BuyDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the BILLDATE value for this STB_Prom_BuyDTO.
     * 
     * @return BILLDATE
     */
    public String getBILLDATE() {
        return BILLDATE;
    }


    /**
     * Sets the BILLDATE value for this STB_Prom_BuyDTO.
     * 
     * @param BILLDATE
     */
    public void setBILLDATE(String BILLDATE) {
        this.BILLDATE = BILLDATE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof STB_Prom_BuyDTO)) return false;
        STB_Prom_BuyDTO other = (STB_Prom_BuyDTO) obj;
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
            ((this.BILLDATE==null && other.getBILLDATE()==null) || 
             (this.BILLDATE!=null &&
              this.BILLDATE.equals(other.getBILLDATE())));
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
        if (getBILLDATE() != null) {
            _hashCode += getBILLDATE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(STB_Prom_BuyDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "STB_Prom_BuyDTO"));
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
        elemField.setFieldName("BILLDATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BILLDATE"));
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
