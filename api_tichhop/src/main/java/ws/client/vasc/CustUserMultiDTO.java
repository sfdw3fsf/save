/**
 * CustUserMultiDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CustUserMultiDTO  implements java.io.Serializable {
    private String USERNAME_FIRST;

    private String USERNAME_MULTI;

    private String MULTI_FLG;

    private String METHOD_FLG;

    public CustUserMultiDTO() {
    }

    public CustUserMultiDTO(
           String USERNAME_FIRST,
           String USERNAME_MULTI,
           String MULTI_FLG,
           String METHOD_FLG) {
           this.USERNAME_FIRST = USERNAME_FIRST;
           this.USERNAME_MULTI = USERNAME_MULTI;
           this.MULTI_FLG = MULTI_FLG;
           this.METHOD_FLG = METHOD_FLG;
    }


    /**
     * Gets the USERNAME_FIRST value for this CustUserMultiDTO.
     * 
     * @return USERNAME_FIRST
     */
    public String getUSERNAME_FIRST() {
        return USERNAME_FIRST;
    }


    /**
     * Sets the USERNAME_FIRST value for this CustUserMultiDTO.
     * 
     * @param USERNAME_FIRST
     */
    public void setUSERNAME_FIRST(String USERNAME_FIRST) {
        this.USERNAME_FIRST = USERNAME_FIRST;
    }


    /**
     * Gets the USERNAME_MULTI value for this CustUserMultiDTO.
     * 
     * @return USERNAME_MULTI
     */
    public String getUSERNAME_MULTI() {
        return USERNAME_MULTI;
    }


    /**
     * Sets the USERNAME_MULTI value for this CustUserMultiDTO.
     * 
     * @param USERNAME_MULTI
     */
    public void setUSERNAME_MULTI(String USERNAME_MULTI) {
        this.USERNAME_MULTI = USERNAME_MULTI;
    }


    /**
     * Gets the MULTI_FLG value for this CustUserMultiDTO.
     * 
     * @return MULTI_FLG
     */
    public String getMULTI_FLG() {
        return MULTI_FLG;
    }


    /**
     * Sets the MULTI_FLG value for this CustUserMultiDTO.
     * 
     * @param MULTI_FLG
     */
    public void setMULTI_FLG(String MULTI_FLG) {
        this.MULTI_FLG = MULTI_FLG;
    }


    /**
     * Gets the METHOD_FLG value for this CustUserMultiDTO.
     * 
     * @return METHOD_FLG
     */
    public String getMETHOD_FLG() {
        return METHOD_FLG;
    }


    /**
     * Sets the METHOD_FLG value for this CustUserMultiDTO.
     * 
     * @param METHOD_FLG
     */
    public void setMETHOD_FLG(String METHOD_FLG) {
        this.METHOD_FLG = METHOD_FLG;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CustUserMultiDTO)) return false;
        CustUserMultiDTO other = (CustUserMultiDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.USERNAME_FIRST==null && other.getUSERNAME_FIRST()==null) || 
             (this.USERNAME_FIRST!=null &&
              this.USERNAME_FIRST.equals(other.getUSERNAME_FIRST()))) &&
            ((this.USERNAME_MULTI==null && other.getUSERNAME_MULTI()==null) || 
             (this.USERNAME_MULTI!=null &&
              this.USERNAME_MULTI.equals(other.getUSERNAME_MULTI()))) &&
            ((this.MULTI_FLG==null && other.getMULTI_FLG()==null) || 
             (this.MULTI_FLG!=null &&
              this.MULTI_FLG.equals(other.getMULTI_FLG()))) &&
            ((this.METHOD_FLG==null && other.getMETHOD_FLG()==null) || 
             (this.METHOD_FLG!=null &&
              this.METHOD_FLG.equals(other.getMETHOD_FLG())));
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
        if (getUSERNAME_FIRST() != null) {
            _hashCode += getUSERNAME_FIRST().hashCode();
        }
        if (getUSERNAME_MULTI() != null) {
            _hashCode += getUSERNAME_MULTI().hashCode();
        }
        if (getMULTI_FLG() != null) {
            _hashCode += getMULTI_FLG().hashCode();
        }
        if (getMETHOD_FLG() != null) {
            _hashCode += getMETHOD_FLG().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustUserMultiDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CustUserMultiDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME_FIRST");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "USERNAME_FIRST"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME_MULTI");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "USERNAME_MULTI"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MULTI_FLG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MULTI_FLG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("METHOD_FLG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "METHOD_FLG"));
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
