/**
 * AuditObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

public class AuditObject  implements java.io.Serializable {
    private String ISSUE_DATETIME;

    private String NAME;

    private String REASON;

    private String DESCRIPTION;

    public AuditObject() {
    }

    public AuditObject(
           String ISSUE_DATETIME,
           String NAME,
           String REASON,
           String DESCRIPTION) {
           this.ISSUE_DATETIME = ISSUE_DATETIME;
           this.NAME = NAME;
           this.REASON = REASON;
           this.DESCRIPTION = DESCRIPTION;
    }


    /**
     * Gets the ISSUE_DATETIME value for this AuditObject.
     * 
     * @return ISSUE_DATETIME
     */
    public String getISSUE_DATETIME() {
        return ISSUE_DATETIME;
    }


    /**
     * Sets the ISSUE_DATETIME value for this AuditObject.
     * 
     * @param ISSUE_DATETIME
     */
    public void setISSUE_DATETIME(String ISSUE_DATETIME) {
        this.ISSUE_DATETIME = ISSUE_DATETIME;
    }


    /**
     * Gets the NAME value for this AuditObject.
     * 
     * @return NAME
     */
    public String getNAME() {
        return NAME;
    }


    /**
     * Sets the NAME value for this AuditObject.
     * 
     * @param NAME
     */
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }


    /**
     * Gets the REASON value for this AuditObject.
     * 
     * @return REASON
     */
    public String getREASON() {
        return REASON;
    }


    /**
     * Sets the REASON value for this AuditObject.
     * 
     * @param REASON
     */
    public void setREASON(String REASON) {
        this.REASON = REASON;
    }


    /**
     * Gets the DESCRIPTION value for this AuditObject.
     * 
     * @return DESCRIPTION
     */
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }


    /**
     * Sets the DESCRIPTION value for this AuditObject.
     * 
     * @param DESCRIPTION
     */
    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AuditObject)) return false;
        AuditObject other = (AuditObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ISSUE_DATETIME==null && other.getISSUE_DATETIME()==null) || 
             (this.ISSUE_DATETIME!=null &&
              this.ISSUE_DATETIME.equals(other.getISSUE_DATETIME()))) &&
            ((this.NAME==null && other.getNAME()==null) || 
             (this.NAME!=null &&
              this.NAME.equals(other.getNAME()))) &&
            ((this.REASON==null && other.getREASON()==null) || 
             (this.REASON!=null &&
              this.REASON.equals(other.getREASON()))) &&
            ((this.DESCRIPTION==null && other.getDESCRIPTION()==null) || 
             (this.DESCRIPTION!=null &&
              this.DESCRIPTION.equals(other.getDESCRIPTION())));
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
        if (getISSUE_DATETIME() != null) {
            _hashCode += getISSUE_DATETIME().hashCode();
        }
        if (getNAME() != null) {
            _hashCode += getNAME().hashCode();
        }
        if (getREASON() != null) {
            _hashCode += getREASON().hashCode();
        }
        if (getDESCRIPTION() != null) {
            _hashCode += getDESCRIPTION().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuditObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.crm.telsoft/", "auditObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ISSUE_DATETIME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ISSUE_DATETIME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("REASON");
        elemField.setXmlName(new javax.xml.namespace.QName("", "REASON"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DESCRIPTION");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DESCRIPTION"));
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
