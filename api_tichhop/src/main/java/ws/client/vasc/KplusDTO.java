/**
 * KplusDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class KplusDTO  implements java.io.Serializable {
    private String USERNAME;

    private int METHOD_FLG;

    private String SUBPACK;

    private int PRICE;

    private String REASON;

    public KplusDTO() {
    }

    public KplusDTO(
           String USERNAME,
           int METHOD_FLG,
           String SUBPACK,
           int PRICE,
           String REASON) {
           this.USERNAME = USERNAME;
           this.METHOD_FLG = METHOD_FLG;
           this.SUBPACK = SUBPACK;
           this.PRICE = PRICE;
           this.REASON = REASON;
    }


    /**
     * Gets the USERNAME value for this KplusDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this KplusDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the METHOD_FLG value for this KplusDTO.
     * 
     * @return METHOD_FLG
     */
    public int getMETHOD_FLG() {
        return METHOD_FLG;
    }


    /**
     * Sets the METHOD_FLG value for this KplusDTO.
     * 
     * @param METHOD_FLG
     */
    public void setMETHOD_FLG(int METHOD_FLG) {
        this.METHOD_FLG = METHOD_FLG;
    }


    /**
     * Gets the SUBPACK value for this KplusDTO.
     * 
     * @return SUBPACK
     */
    public String getSUBPACK() {
        return SUBPACK;
    }


    /**
     * Sets the SUBPACK value for this KplusDTO.
     * 
     * @param SUBPACK
     */
    public void setSUBPACK(String SUBPACK) {
        this.SUBPACK = SUBPACK;
    }


    /**
     * Gets the PRICE value for this KplusDTO.
     * 
     * @return PRICE
     */
    public int getPRICE() {
        return PRICE;
    }


    /**
     * Sets the PRICE value for this KplusDTO.
     * 
     * @param PRICE
     */
    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }


    /**
     * Gets the REASON value for this KplusDTO.
     * 
     * @return REASON
     */
    public String getREASON() {
        return REASON;
    }


    /**
     * Sets the REASON value for this KplusDTO.
     * 
     * @param REASON
     */
    public void setREASON(String REASON) {
        this.REASON = REASON;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof KplusDTO)) return false;
        KplusDTO other = (KplusDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.USERNAME==null && other.getUSERNAME()==null) || 
             (this.USERNAME!=null &&
              this.USERNAME.equals(other.getUSERNAME()))) &&
            this.METHOD_FLG == other.getMETHOD_FLG() &&
            ((this.SUBPACK==null && other.getSUBPACK()==null) || 
             (this.SUBPACK!=null &&
              this.SUBPACK.equals(other.getSUBPACK()))) &&
            this.PRICE == other.getPRICE() &&
            ((this.REASON==null && other.getREASON()==null) || 
             (this.REASON!=null &&
              this.REASON.equals(other.getREASON())));
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
        if (getUSERNAME() != null) {
            _hashCode += getUSERNAME().hashCode();
        }
        _hashCode += getMETHOD_FLG();
        if (getSUBPACK() != null) {
            _hashCode += getSUBPACK().hashCode();
        }
        _hashCode += getPRICE();
        if (getREASON() != null) {
            _hashCode += getREASON().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(KplusDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "KplusDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "USERNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("METHOD_FLG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "METHOD_FLG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SUBPACK");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SUBPACK"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("REASON");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "REASON"));
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
