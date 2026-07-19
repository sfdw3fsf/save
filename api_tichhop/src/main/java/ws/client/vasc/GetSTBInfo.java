/**
 * GetSTBInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSTBInfo  implements java.io.Serializable {
    private String IPTVAccount;

    public GetSTBInfo() {
    }

    public GetSTBInfo(
           String IPTVAccount) {
           this.IPTVAccount = IPTVAccount;
    }


    /**
     * Gets the IPTVAccount value for this GetSTBInfo.
     * 
     * @return IPTVAccount
     */
    public String getIPTVAccount() {
        return IPTVAccount;
    }


    /**
     * Sets the IPTVAccount value for this GetSTBInfo.
     * 
     * @param IPTVAccount
     */
    public void setIPTVAccount(String IPTVAccount) {
        this.IPTVAccount = IPTVAccount;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSTBInfo)) return false;
        GetSTBInfo other = (GetSTBInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IPTVAccount==null && other.getIPTVAccount()==null) || 
             (this.IPTVAccount!=null &&
              this.IPTVAccount.equals(other.getIPTVAccount())));
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
        if (getIPTVAccount() != null) {
            _hashCode += getIPTVAccount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSTBInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSTBInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVAccount"));
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
