/**
 * ChangeDateBill.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeDateBill  implements java.io.Serializable {
    private String myTVAccount;

    private String strDateBillNew;

    public ChangeDateBill() {
    }

    public ChangeDateBill(
           String myTVAccount,
           String strDateBillNew) {
           this.myTVAccount = myTVAccount;
           this.strDateBillNew = strDateBillNew;
    }


    /**
     * Gets the myTVAccount value for this ChangeDateBill.
     * 
     * @return myTVAccount
     */
    public String getMyTVAccount() {
        return myTVAccount;
    }


    /**
     * Sets the myTVAccount value for this ChangeDateBill.
     * 
     * @param myTVAccount
     */
    public void setMyTVAccount(String myTVAccount) {
        this.myTVAccount = myTVAccount;
    }


    /**
     * Gets the strDateBillNew value for this ChangeDateBill.
     * 
     * @return strDateBillNew
     */
    public String getStrDateBillNew() {
        return strDateBillNew;
    }


    /**
     * Sets the strDateBillNew value for this ChangeDateBill.
     * 
     * @param strDateBillNew
     */
    public void setStrDateBillNew(String strDateBillNew) {
        this.strDateBillNew = strDateBillNew;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeDateBill)) return false;
        ChangeDateBill other = (ChangeDateBill) obj;
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
            ((this.strDateBillNew==null && other.getStrDateBillNew()==null) || 
             (this.strDateBillNew!=null &&
              this.strDateBillNew.equals(other.getStrDateBillNew())));
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
        if (getStrDateBillNew() != null) {
            _hashCode += getStrDateBillNew().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeDateBill.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeDateBill"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("myTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MyTVAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strDateBillNew");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "strDateBillNew"));
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
