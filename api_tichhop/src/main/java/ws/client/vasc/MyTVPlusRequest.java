/**
 * MyTVPlusRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class MyTVPlusRequest  implements java.io.Serializable {
    private String IPTVAccount;

    private String operatorCD;

    private String serviceCD;

    private String reason;

    public MyTVPlusRequest() {
    }

    public MyTVPlusRequest(
           String IPTVAccount,
           String operatorCD,
           String serviceCD,
           String reason) {
           this.IPTVAccount = IPTVAccount;
           this.operatorCD = operatorCD;
           this.serviceCD = serviceCD;
           this.reason = reason;
    }


    /**
     * Gets the IPTVAccount value for this MyTVPlusRequest.
     * 
     * @return IPTVAccount
     */
    public String getIPTVAccount() {
        return IPTVAccount;
    }


    /**
     * Sets the IPTVAccount value for this MyTVPlusRequest.
     * 
     * @param IPTVAccount
     */
    public void setIPTVAccount(String IPTVAccount) {
        this.IPTVAccount = IPTVAccount;
    }


    /**
     * Gets the operatorCD value for this MyTVPlusRequest.
     * 
     * @return operatorCD
     */
    public String getOperatorCD() {
        return operatorCD;
    }


    /**
     * Sets the operatorCD value for this MyTVPlusRequest.
     * 
     * @param operatorCD
     */
    public void setOperatorCD(String operatorCD) {
        this.operatorCD = operatorCD;
    }


    /**
     * Gets the serviceCD value for this MyTVPlusRequest.
     * 
     * @return serviceCD
     */
    public String getServiceCD() {
        return serviceCD;
    }


    /**
     * Sets the serviceCD value for this MyTVPlusRequest.
     * 
     * @param serviceCD
     */
    public void setServiceCD(String serviceCD) {
        this.serviceCD = serviceCD;
    }


    /**
     * Gets the reason value for this MyTVPlusRequest.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this MyTVPlusRequest.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof MyTVPlusRequest)) return false;
        MyTVPlusRequest other = (MyTVPlusRequest) obj;
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
              this.IPTVAccount.equals(other.getIPTVAccount()))) &&
            ((this.operatorCD==null && other.getOperatorCD()==null) || 
             (this.operatorCD!=null &&
              this.operatorCD.equals(other.getOperatorCD()))) &&
            ((this.serviceCD==null && other.getServiceCD()==null) || 
             (this.serviceCD!=null &&
              this.serviceCD.equals(other.getServiceCD()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason())));
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
        if (getOperatorCD() != null) {
            _hashCode += getOperatorCD().hashCode();
        }
        if (getServiceCD() != null) {
            _hashCode += getServiceCD().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MyTVPlusRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "MyTVPlusRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operatorCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "OperatorCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ServiceCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Reason"));
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
