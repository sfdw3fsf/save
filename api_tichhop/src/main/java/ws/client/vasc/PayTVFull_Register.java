/**
 * PayTVFull_Register.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class PayTVFull_Register  implements java.io.Serializable {
    private String myTVAccount;

    private int month;

    public PayTVFull_Register() {
    }

    public PayTVFull_Register(
           String myTVAccount,
           int month) {
           this.myTVAccount = myTVAccount;
           this.month = month;
    }


    /**
     * Gets the myTVAccount value for this PayTVFull_Register.
     * 
     * @return myTVAccount
     */
    public String getMyTVAccount() {
        return myTVAccount;
    }


    /**
     * Sets the myTVAccount value for this PayTVFull_Register.
     * 
     * @param myTVAccount
     */
    public void setMyTVAccount(String myTVAccount) {
        this.myTVAccount = myTVAccount;
    }


    /**
     * Gets the month value for this PayTVFull_Register.
     * 
     * @return month
     */
    public int getMonth() {
        return month;
    }


    /**
     * Sets the month value for this PayTVFull_Register.
     * 
     * @param month
     */
    public void setMonth(int month) {
        this.month = month;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof PayTVFull_Register)) return false;
        PayTVFull_Register other = (PayTVFull_Register) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.myTVAccount==null && other.getMyTVAccount()==null) || 
             (this.myTVAccount!=null &&
              this.myTVAccount.equals(other.getMyTVAccount()))) &&
            this.month == other.getMonth();
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
        if (getMyTVAccount() != null) {
            _hashCode += getMyTVAccount().hashCode();
        }
        _hashCode += getMonth();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PayTVFull_Register.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">PayTVFull_Register"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("myTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MyTVAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("month");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Month"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
