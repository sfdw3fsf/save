/**
 * UpdateUserParentReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateUserParentReq  implements java.io.Serializable {
    private String childUser;

    private String parentUser;

    public UpdateUserParentReq() {
    }

    public UpdateUserParentReq(
           String childUser,
           String parentUser) {
           this.childUser = childUser;
           this.parentUser = parentUser;
    }


    /**
     * Gets the childUser value for this UpdateUserParentReq.
     * 
     * @return childUser
     */
    public String getChildUser() {
        return childUser;
    }


    /**
     * Sets the childUser value for this UpdateUserParentReq.
     * 
     * @param childUser
     */
    public void setChildUser(String childUser) {
        this.childUser = childUser;
    }


    /**
     * Gets the parentUser value for this UpdateUserParentReq.
     * 
     * @return parentUser
     */
    public String getParentUser() {
        return parentUser;
    }


    /**
     * Sets the parentUser value for this UpdateUserParentReq.
     * 
     * @param parentUser
     */
    public void setParentUser(String parentUser) {
        this.parentUser = parentUser;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateUserParentReq)) return false;
        UpdateUserParentReq other = (UpdateUserParentReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.childUser==null && other.getChildUser()==null) || 
             (this.childUser!=null &&
              this.childUser.equals(other.getChildUser()))) &&
            ((this.parentUser==null && other.getParentUser()==null) || 
             (this.parentUser!=null &&
              this.parentUser.equals(other.getParentUser())));
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
        if (getChildUser() != null) {
            _hashCode += getChildUser().hashCode();
        }
        if (getParentUser() != null) {
            _hashCode += getParentUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateUserParentReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "UpdateUserParentReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childUser");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChildUser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentUser");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ParentUser"));
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
