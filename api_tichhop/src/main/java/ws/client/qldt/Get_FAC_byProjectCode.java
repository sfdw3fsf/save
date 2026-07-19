/**
 * Get_FAC_byProjectCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class Get_FAC_byProjectCode  implements java.io.Serializable {
    private String sUserID;

    private String sPass;

    private String sDepartmentCode;

    private String sProjectCode;

    private String sStartDate;

    private String sEndDate;

    public Get_FAC_byProjectCode() {
    }

    public Get_FAC_byProjectCode(
           String sUserID,
           String sPass,
           String sDepartmentCode,
           String sProjectCode,
           String sStartDate,
           String sEndDate) {
           this.sUserID = sUserID;
           this.sPass = sPass;
           this.sDepartmentCode = sDepartmentCode;
           this.sProjectCode = sProjectCode;
           this.sStartDate = sStartDate;
           this.sEndDate = sEndDate;
    }


    /**
     * Gets the sUserID value for this Get_FAC_byProjectCode.
     * 
     * @return sUserID
     */
    public String getSUserID() {
        return sUserID;
    }


    /**
     * Sets the sUserID value for this Get_FAC_byProjectCode.
     * 
     * @param sUserID
     */
    public void setSUserID(String sUserID) {
        this.sUserID = sUserID;
    }


    /**
     * Gets the sPass value for this Get_FAC_byProjectCode.
     * 
     * @return sPass
     */
    public String getSPass() {
        return sPass;
    }


    /**
     * Sets the sPass value for this Get_FAC_byProjectCode.
     * 
     * @param sPass
     */
    public void setSPass(String sPass) {
        this.sPass = sPass;
    }


    /**
     * Gets the sDepartmentCode value for this Get_FAC_byProjectCode.
     * 
     * @return sDepartmentCode
     */
    public String getSDepartmentCode() {
        return sDepartmentCode;
    }


    /**
     * Sets the sDepartmentCode value for this Get_FAC_byProjectCode.
     * 
     * @param sDepartmentCode
     */
    public void setSDepartmentCode(String sDepartmentCode) {
        this.sDepartmentCode = sDepartmentCode;
    }


    /**
     * Gets the sProjectCode value for this Get_FAC_byProjectCode.
     * 
     * @return sProjectCode
     */
    public String getSProjectCode() {
        return sProjectCode;
    }


    /**
     * Sets the sProjectCode value for this Get_FAC_byProjectCode.
     * 
     * @param sProjectCode
     */
    public void setSProjectCode(String sProjectCode) {
        this.sProjectCode = sProjectCode;
    }


    /**
     * Gets the sStartDate value for this Get_FAC_byProjectCode.
     * 
     * @return sStartDate
     */
    public String getSStartDate() {
        return sStartDate;
    }


    /**
     * Sets the sStartDate value for this Get_FAC_byProjectCode.
     * 
     * @param sStartDate
     */
    public void setSStartDate(String sStartDate) {
        this.sStartDate = sStartDate;
    }


    /**
     * Gets the sEndDate value for this Get_FAC_byProjectCode.
     * 
     * @return sEndDate
     */
    public String getSEndDate() {
        return sEndDate;
    }


    /**
     * Sets the sEndDate value for this Get_FAC_byProjectCode.
     * 
     * @param sEndDate
     */
    public void setSEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Get_FAC_byProjectCode)) return false;
        Get_FAC_byProjectCode other = (Get_FAC_byProjectCode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sUserID==null && other.getSUserID()==null) || 
             (this.sUserID!=null &&
              this.sUserID.equals(other.getSUserID()))) &&
            ((this.sPass==null && other.getSPass()==null) || 
             (this.sPass!=null &&
              this.sPass.equals(other.getSPass()))) &&
            ((this.sDepartmentCode==null && other.getSDepartmentCode()==null) || 
             (this.sDepartmentCode!=null &&
              this.sDepartmentCode.equals(other.getSDepartmentCode()))) &&
            ((this.sProjectCode==null && other.getSProjectCode()==null) || 
             (this.sProjectCode!=null &&
              this.sProjectCode.equals(other.getSProjectCode()))) &&
            ((this.sStartDate==null && other.getSStartDate()==null) || 
             (this.sStartDate!=null &&
              this.sStartDate.equals(other.getSStartDate()))) &&
            ((this.sEndDate==null && other.getSEndDate()==null) || 
             (this.sEndDate!=null &&
              this.sEndDate.equals(other.getSEndDate())));
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
        if (getSUserID() != null) {
            _hashCode += getSUserID().hashCode();
        }
        if (getSPass() != null) {
            _hashCode += getSPass().hashCode();
        }
        if (getSDepartmentCode() != null) {
            _hashCode += getSDepartmentCode().hashCode();
        }
        if (getSProjectCode() != null) {
            _hashCode += getSProjectCode().hashCode();
        }
        if (getSStartDate() != null) {
            _hashCode += getSStartDate().hashCode();
        }
        if (getSEndDate() != null) {
            _hashCode += getSEndDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_FAC_byProjectCode.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Get_FAC_byProjectCode"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SUserID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sUserID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sPass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SDepartmentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sDepartmentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SProjectCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sProjectCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sEndDate"));
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
