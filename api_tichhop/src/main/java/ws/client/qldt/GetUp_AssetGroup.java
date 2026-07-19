/**
 * GetUp_AssetGroup.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class GetUp_AssetGroup  implements java.io.Serializable {
    private String sUserID;

    private String sPass;

    private String sDepartmnetCode;

    private String iAmount;

    private String sSys;

    public GetUp_AssetGroup() {
    }

    public GetUp_AssetGroup(
           String sUserID,
           String sPass,
           String sDepartmnetCode,
           String iAmount,
           String sSys) {
           this.sUserID = sUserID;
           this.sPass = sPass;
           this.sDepartmnetCode = sDepartmnetCode;
           this.iAmount = iAmount;
           this.sSys = sSys;
    }


    /**
     * Gets the sUserID value for this GetUp_AssetGroup.
     * 
     * @return sUserID
     */
    public String getSUserID() {
        return sUserID;
    }


    /**
     * Sets the sUserID value for this GetUp_AssetGroup.
     * 
     * @param sUserID
     */
    public void setSUserID(String sUserID) {
        this.sUserID = sUserID;
    }


    /**
     * Gets the sPass value for this GetUp_AssetGroup.
     * 
     * @return sPass
     */
    public String getSPass() {
        return sPass;
    }


    /**
     * Sets the sPass value for this GetUp_AssetGroup.
     * 
     * @param sPass
     */
    public void setSPass(String sPass) {
        this.sPass = sPass;
    }


    /**
     * Gets the sDepartmnetCode value for this GetUp_AssetGroup.
     * 
     * @return sDepartmnetCode
     */
    public String getSDepartmnetCode() {
        return sDepartmnetCode;
    }


    /**
     * Sets the sDepartmnetCode value for this GetUp_AssetGroup.
     * 
     * @param sDepartmnetCode
     */
    public void setSDepartmnetCode(String sDepartmnetCode) {
        this.sDepartmnetCode = sDepartmnetCode;
    }


    /**
     * Gets the iAmount value for this GetUp_AssetGroup.
     * 
     * @return iAmount
     */
    public String getIAmount() {
        return iAmount;
    }


    /**
     * Sets the iAmount value for this GetUp_AssetGroup.
     * 
     * @param iAmount
     */
    public void setIAmount(String iAmount) {
        this.iAmount = iAmount;
    }


    /**
     * Gets the sSys value for this GetUp_AssetGroup.
     * 
     * @return sSys
     */
    public String getSSys() {
        return sSys;
    }


    /**
     * Sets the sSys value for this GetUp_AssetGroup.
     * 
     * @param sSys
     */
    public void setSSys(String sSys) {
        this.sSys = sSys;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetUp_AssetGroup)) return false;
        GetUp_AssetGroup other = (GetUp_AssetGroup) obj;
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
            ((this.iAmount==null && other.getIAmount()==null) || 
             (this.iAmount!=null &&
              this.iAmount.equals(other.getIAmount()))) &&
            ((this.sSys==null && other.getSSys()==null) || 
             (this.sSys!=null &&
              this.sSys.equals(other.getSSys())));
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
        if (getIAmount() != null) {
            _hashCode += getIAmount().hashCode();
        }
        if (getSSys() != null) {
            _hashCode += getSSys().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUp_AssetGroup.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUp_AssetGroup"));
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
        elemField.setFieldName("IAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "iAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSys");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sSys"));
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
