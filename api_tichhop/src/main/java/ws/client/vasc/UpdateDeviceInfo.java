/**
 * UpdateDeviceInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateDeviceInfo  implements java.io.Serializable {
    private String myTVAccount;

    private String strDeviceName;

    private String strPortID;

    public UpdateDeviceInfo() {
    }

    public UpdateDeviceInfo(
           String myTVAccount,
           String strDeviceName,
           String strPortID) {
           this.myTVAccount = myTVAccount;
           this.strDeviceName = strDeviceName;
           this.strPortID = strPortID;
    }


    /**
     * Gets the myTVAccount value for this UpdateDeviceInfo.
     * 
     * @return myTVAccount
     */
    public String getMyTVAccount() {
        return myTVAccount;
    }


    /**
     * Sets the myTVAccount value for this UpdateDeviceInfo.
     * 
     * @param myTVAccount
     */
    public void setMyTVAccount(String myTVAccount) {
        this.myTVAccount = myTVAccount;
    }


    /**
     * Gets the strDeviceName value for this UpdateDeviceInfo.
     * 
     * @return strDeviceName
     */
    public String getStrDeviceName() {
        return strDeviceName;
    }


    /**
     * Sets the strDeviceName value for this UpdateDeviceInfo.
     * 
     * @param strDeviceName
     */
    public void setStrDeviceName(String strDeviceName) {
        this.strDeviceName = strDeviceName;
    }


    /**
     * Gets the strPortID value for this UpdateDeviceInfo.
     * 
     * @return strPortID
     */
    public String getStrPortID() {
        return strPortID;
    }


    /**
     * Sets the strPortID value for this UpdateDeviceInfo.
     * 
     * @param strPortID
     */
    public void setStrPortID(String strPortID) {
        this.strPortID = strPortID;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateDeviceInfo)) return false;
        UpdateDeviceInfo other = (UpdateDeviceInfo) obj;
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
            ((this.strDeviceName==null && other.getStrDeviceName()==null) || 
             (this.strDeviceName!=null &&
              this.strDeviceName.equals(other.getStrDeviceName()))) &&
            ((this.strPortID==null && other.getStrPortID()==null) || 
             (this.strPortID!=null &&
              this.strPortID.equals(other.getStrPortID())));
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
        if (getStrDeviceName() != null) {
            _hashCode += getStrDeviceName().hashCode();
        }
        if (getStrPortID() != null) {
            _hashCode += getStrPortID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateDeviceInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdateDeviceInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("myTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MyTVAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strDeviceName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "strDeviceName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("strPortID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "strPortID"));
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
