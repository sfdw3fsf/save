/**
 * UpdateKplusInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateKplusInfo  implements java.io.Serializable {
    private String myTVAccount;

    private String strOperator;

    public UpdateKplusInfo() {
    }

    public UpdateKplusInfo(
           String myTVAccount,
           String strOperator) {
           this.myTVAccount = myTVAccount;
           this.strOperator = strOperator;
    }


    /**
     * Gets the myTVAccount value for this UpdateKplusInfo.
     * 
     * @return myTVAccount
     */
    public String getMyTVAccount() {
        return myTVAccount;
    }


    /**
     * Sets the myTVAccount value for this UpdateKplusInfo.
     * 
     * @param myTVAccount
     */
    public void setMyTVAccount(String myTVAccount) {
        this.myTVAccount = myTVAccount;
    }


    /**
     * Gets the strOperator value for this UpdateKplusInfo.
     * 
     * @return strOperator
     */
    public String getStrOperator() {
        return strOperator;
    }


    /**
     * Sets the strOperator value for this UpdateKplusInfo.
     * 
     * @param strOperator
     */
    public void setStrOperator(String strOperator) {
        this.strOperator = strOperator;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateKplusInfo)) return false;
        UpdateKplusInfo other = (UpdateKplusInfo) obj;
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
            ((this.strOperator==null && other.getStrOperator()==null) || 
             (this.strOperator!=null &&
              this.strOperator.equals(other.getStrOperator())));
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
        if (getStrOperator() != null) {
            _hashCode += getStrOperator().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateKplusInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdateKplusInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("myTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MyTVAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strOperator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "strOperator"));
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
