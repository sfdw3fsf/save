/**
 * ChangeUserPassword.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeUserPassword  implements java.io.Serializable {
    private String strUserName;

    private String strOldPassword;

    private String strNewPassword;

    public ChangeUserPassword() {
    }

    public ChangeUserPassword(
           String strUserName,
           String strOldPassword,
           String strNewPassword) {
           this.strUserName = strUserName;
           this.strOldPassword = strOldPassword;
           this.strNewPassword = strNewPassword;
    }


    /**
     * Gets the strUserName value for this ChangeUserPassword.
     * 
     * @return strUserName
     */
    public String getStrUserName() {
        return strUserName;
    }


    /**
     * Sets the strUserName value for this ChangeUserPassword.
     * 
     * @param strUserName
     */
    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }


    /**
     * Gets the strOldPassword value for this ChangeUserPassword.
     * 
     * @return strOldPassword
     */
    public String getStrOldPassword() {
        return strOldPassword;
    }


    /**
     * Sets the strOldPassword value for this ChangeUserPassword.
     * 
     * @param strOldPassword
     */
    public void setStrOldPassword(String strOldPassword) {
        this.strOldPassword = strOldPassword;
    }


    /**
     * Gets the strNewPassword value for this ChangeUserPassword.
     * 
     * @return strNewPassword
     */
    public String getStrNewPassword() {
        return strNewPassword;
    }


    /**
     * Sets the strNewPassword value for this ChangeUserPassword.
     * 
     * @param strNewPassword
     */
    public void setStrNewPassword(String strNewPassword) {
        this.strNewPassword = strNewPassword;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeUserPassword)) return false;
        ChangeUserPassword other = (ChangeUserPassword) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.strUserName==null && other.getStrUserName()==null) || 
             (this.strUserName!=null &&
              this.strUserName.equals(other.getStrUserName()))) &&
            ((this.strOldPassword==null && other.getStrOldPassword()==null) || 
             (this.strOldPassword!=null &&
              this.strOldPassword.equals(other.getStrOldPassword()))) &&
            ((this.strNewPassword==null && other.getStrNewPassword()==null) || 
             (this.strNewPassword!=null &&
              this.strNewPassword.equals(other.getStrNewPassword())));
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
        if (getStrUserName() != null) {
            _hashCode += getStrUserName().hashCode();
        }
        if (getStrOldPassword() != null) {
            _hashCode += getStrOldPassword().hashCode();
        }
        if (getStrNewPassword() != null) {
            _hashCode += getStrNewPassword().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeUserPassword.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeUserPassword"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strUserName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "strUserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strOldPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "strOldPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strNewPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "strNewPassword"));
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
