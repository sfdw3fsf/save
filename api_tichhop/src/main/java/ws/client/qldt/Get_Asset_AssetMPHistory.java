/**
 * Get_Asset_AssetMPHistory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class Get_Asset_AssetMPHistory  implements java.io.Serializable {
    private String sUserID;

    private String sPass;

    private String sDepartmnetCode;

    private String sProjectCode;

    private String sStartDate;

    private String sEndDate;

    public Get_Asset_AssetMPHistory() {
    }

    public Get_Asset_AssetMPHistory(
           String sUserID,
           String sPass,
           String sDepartmnetCode,
           String sProjectCode,
           String sStartDate,
           String sEndDate) {
           this.sUserID = sUserID;
           this.sPass = sPass;
           this.sDepartmnetCode = sDepartmnetCode;
           this.sProjectCode = sProjectCode;
           this.sStartDate = sStartDate;
           this.sEndDate = sEndDate;
    }


    /**
     * Gets the sUserID value for this Get_Asset_AssetMPHistory.
     * 
     * @return sUserID
     */
    public String getSUserID() {
        return sUserID;
    }


    /**
     * Sets the sUserID value for this Get_Asset_AssetMPHistory.
     * 
     * @param sUserID
     */
    public void setSUserID(String sUserID) {
        this.sUserID = sUserID;
    }


    /**
     * Gets the sPass value for this Get_Asset_AssetMPHistory.
     * 
     * @return sPass
     */
    public String getSPass() {
        return sPass;
    }


    /**
     * Sets the sPass value for this Get_Asset_AssetMPHistory.
     * 
     * @param sPass
     */
    public void setSPass(String sPass) {
        this.sPass = sPass;
    }


    /**
     * Gets the sDepartmnetCode value for this Get_Asset_AssetMPHistory.
     * 
     * @return sDepartmnetCode
     */
    public String getSDepartmnetCode() {
        return sDepartmnetCode;
    }


    /**
     * Sets the sDepartmnetCode value for this Get_Asset_AssetMPHistory.
     * 
     * @param sDepartmnetCode
     */
    public void setSDepartmnetCode(String sDepartmnetCode) {
        this.sDepartmnetCode = sDepartmnetCode;
    }


    /**
     * Gets the sProjectCode value for this Get_Asset_AssetMPHistory.
     * 
     * @return sProjectCode
     */
    public String getSProjectCode() {
        return sProjectCode;
    }


    /**
     * Sets the sProjectCode value for this Get_Asset_AssetMPHistory.
     * 
     * @param sProjectCode
     */
    public void setSProjectCode(String sProjectCode) {
        this.sProjectCode = sProjectCode;
    }


    /**
     * Gets the sStartDate value for this Get_Asset_AssetMPHistory.
     * 
     * @return sStartDate
     */
    public String getSStartDate() {
        return sStartDate;
    }


    /**
     * Sets the sStartDate value for this Get_Asset_AssetMPHistory.
     * 
     * @param sStartDate
     */
    public void setSStartDate(String sStartDate) {
        this.sStartDate = sStartDate;
    }


    /**
     * Gets the sEndDate value for this Get_Asset_AssetMPHistory.
     * 
     * @return sEndDate
     */
    public String getSEndDate() {
        return sEndDate;
    }


    /**
     * Sets the sEndDate value for this Get_Asset_AssetMPHistory.
     * 
     * @param sEndDate
     */
    public void setSEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Get_Asset_AssetMPHistory)) return false;
        Get_Asset_AssetMPHistory other = (Get_Asset_AssetMPHistory) obj;
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
            ((this.sDepartmnetCode==null && other.getSDepartmnetCode()==null) || 
             (this.sDepartmnetCode!=null &&
              this.sDepartmnetCode.equals(other.getSDepartmnetCode()))) &&
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
        if (getSDepartmnetCode() != null) {
            _hashCode += getSDepartmnetCode().hashCode();
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
        new org.apache.axis.description.TypeDesc(Get_Asset_AssetMPHistory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Get_Asset_AssetMPHistory"));
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
        elemField.setFieldName("SDepartmnetCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sDepartmnetCode"));
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
