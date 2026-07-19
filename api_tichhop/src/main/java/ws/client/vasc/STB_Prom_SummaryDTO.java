/**
 * STB_Prom_SummaryDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class STB_Prom_SummaryDTO  implements java.io.Serializable {
    private String BILLDATE;

    private int PSC;

    private int FIBER_MYTV_0;

    private int FIBER_MYTV_1;

    private int COMBO;

    private String NOTE;

    public STB_Prom_SummaryDTO() {
    }

    public STB_Prom_SummaryDTO(
           String BILLDATE,
           int PSC,
           int FIBER_MYTV_0,
           int FIBER_MYTV_1,
           int COMBO,
           String NOTE) {
           this.BILLDATE = BILLDATE;
           this.PSC = PSC;
           this.FIBER_MYTV_0 = FIBER_MYTV_0;
           this.FIBER_MYTV_1 = FIBER_MYTV_1;
           this.COMBO = COMBO;
           this.NOTE = NOTE;
    }


    /**
     * Gets the BILLDATE value for this STB_Prom_SummaryDTO.
     * 
     * @return BILLDATE
     */
    public String getBILLDATE() {
        return BILLDATE;
    }


    /**
     * Sets the BILLDATE value for this STB_Prom_SummaryDTO.
     * 
     * @param BILLDATE
     */
    public void setBILLDATE(String BILLDATE) {
        this.BILLDATE = BILLDATE;
    }


    /**
     * Gets the PSC value for this STB_Prom_SummaryDTO.
     * 
     * @return PSC
     */
    public int getPSC() {
        return PSC;
    }


    /**
     * Sets the PSC value for this STB_Prom_SummaryDTO.
     * 
     * @param PSC
     */
    public void setPSC(int PSC) {
        this.PSC = PSC;
    }


    /**
     * Gets the FIBER_MYTV_0 value for this STB_Prom_SummaryDTO.
     * 
     * @return FIBER_MYTV_0
     */
    public int getFIBER_MYTV_0() {
        return FIBER_MYTV_0;
    }


    /**
     * Sets the FIBER_MYTV_0 value for this STB_Prom_SummaryDTO.
     * 
     * @param FIBER_MYTV_0
     */
    public void setFIBER_MYTV_0(int FIBER_MYTV_0) {
        this.FIBER_MYTV_0 = FIBER_MYTV_0;
    }


    /**
     * Gets the FIBER_MYTV_1 value for this STB_Prom_SummaryDTO.
     * 
     * @return FIBER_MYTV_1
     */
    public int getFIBER_MYTV_1() {
        return FIBER_MYTV_1;
    }


    /**
     * Sets the FIBER_MYTV_1 value for this STB_Prom_SummaryDTO.
     * 
     * @param FIBER_MYTV_1
     */
    public void setFIBER_MYTV_1(int FIBER_MYTV_1) {
        this.FIBER_MYTV_1 = FIBER_MYTV_1;
    }


    /**
     * Gets the COMBO value for this STB_Prom_SummaryDTO.
     * 
     * @return COMBO
     */
    public int getCOMBO() {
        return COMBO;
    }


    /**
     * Sets the COMBO value for this STB_Prom_SummaryDTO.
     * 
     * @param COMBO
     */
    public void setCOMBO(int COMBO) {
        this.COMBO = COMBO;
    }


    /**
     * Gets the NOTE value for this STB_Prom_SummaryDTO.
     * 
     * @return NOTE
     */
    public String getNOTE() {
        return NOTE;
    }


    /**
     * Sets the NOTE value for this STB_Prom_SummaryDTO.
     * 
     * @param NOTE
     */
    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof STB_Prom_SummaryDTO)) return false;
        STB_Prom_SummaryDTO other = (STB_Prom_SummaryDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.BILLDATE==null && other.getBILLDATE()==null) || 
             (this.BILLDATE!=null &&
              this.BILLDATE.equals(other.getBILLDATE()))) &&
            this.PSC == other.getPSC() &&
            this.FIBER_MYTV_0 == other.getFIBER_MYTV_0() &&
            this.FIBER_MYTV_1 == other.getFIBER_MYTV_1() &&
            this.COMBO == other.getCOMBO() &&
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
        if (getBILLDATE() != null) {
            _hashCode += getBILLDATE().hashCode();
        }
        _hashCode += getPSC();
        _hashCode += getFIBER_MYTV_0();
        _hashCode += getFIBER_MYTV_1();
        _hashCode += getCOMBO();
        if (getNOTE() != null) {
            _hashCode += getNOTE().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(STB_Prom_SummaryDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "STB_Prom_SummaryDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILLDATE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BILLDATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PSC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PSC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIBER_MYTV_0");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "FIBER_MYTV_0"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FIBER_MYTV_1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "FIBER_MYTV_1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("COMBO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "COMBO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
