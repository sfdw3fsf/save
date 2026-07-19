/**
 * ChangePackageAndMegaVNNRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangePackageAndMegaVNNRequest  implements java.io.Serializable {
    private String IPTVAccount;

    private String packageNo;

    private String serviceCD;

    private String serviceVAS;

    private String reason;

    private String megaMyTV;

    private String combo_FiberCD;

    public ChangePackageAndMegaVNNRequest() {
    }

    public ChangePackageAndMegaVNNRequest(
           String IPTVAccount,
           String packageNo,
           String serviceCD,
           String serviceVAS,
           String reason,
           String megaMyTV,
           String combo_FiberCD) {
           this.IPTVAccount = IPTVAccount;
           this.packageNo = packageNo;
           this.serviceCD = serviceCD;
           this.serviceVAS = serviceVAS;
           this.reason = reason;
           this.megaMyTV = megaMyTV;
           this.combo_FiberCD = combo_FiberCD;
    }


    /**
     * Gets the IPTVAccount value for this ChangePackageAndMegaVNNRequest.
     * 
     * @return IPTVAccount
     */
    public String getIPTVAccount() {
        return IPTVAccount;
    }


    /**
     * Sets the IPTVAccount value for this ChangePackageAndMegaVNNRequest.
     * 
     * @param IPTVAccount
     */
    public void setIPTVAccount(String IPTVAccount) {
        this.IPTVAccount = IPTVAccount;
    }


    /**
     * Gets the packageNo value for this ChangePackageAndMegaVNNRequest.
     * 
     * @return packageNo
     */
    public String getPackageNo() {
        return packageNo;
    }


    /**
     * Sets the packageNo value for this ChangePackageAndMegaVNNRequest.
     * 
     * @param packageNo
     */
    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }


    /**
     * Gets the serviceCD value for this ChangePackageAndMegaVNNRequest.
     * 
     * @return serviceCD
     */
    public String getServiceCD() {
        return serviceCD;
    }


    /**
     * Sets the serviceCD value for this ChangePackageAndMegaVNNRequest.
     * 
     * @param serviceCD
     */
    public void setServiceCD(String serviceCD) {
        this.serviceCD = serviceCD;
    }


    /**
     * Gets the serviceVAS value for this ChangePackageAndMegaVNNRequest.
     * 
     * @return serviceVAS
     */
    public String getServiceVAS() {
        return serviceVAS;
    }


    /**
     * Sets the serviceVAS value for this ChangePackageAndMegaVNNRequest.
     * 
     * @param serviceVAS
     */
    public void setServiceVAS(String serviceVAS) {
        this.serviceVAS = serviceVAS;
    }


    /**
     * Gets the reason value for this ChangePackageAndMegaVNNRequest.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this ChangePackageAndMegaVNNRequest.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


    /**
     * Gets the megaMyTV value for this ChangePackageAndMegaVNNRequest.
     * 
     * @return megaMyTV
     */
    public String getMegaMyTV() {
        return megaMyTV;
    }


    /**
     * Sets the megaMyTV value for this ChangePackageAndMegaVNNRequest.
     * 
     * @param megaMyTV
     */
    public void setMegaMyTV(String megaMyTV) {
        this.megaMyTV = megaMyTV;
    }


    /**
     * Gets the combo_FiberCD value for this ChangePackageAndMegaVNNRequest.
     * 
     * @return combo_FiberCD
     */
    public String getCombo_FiberCD() {
        return combo_FiberCD;
    }


    /**
     * Sets the combo_FiberCD value for this ChangePackageAndMegaVNNRequest.
     * 
     * @param combo_FiberCD
     */
    public void setCombo_FiberCD(String combo_FiberCD) {
        this.combo_FiberCD = combo_FiberCD;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangePackageAndMegaVNNRequest)) return false;
        ChangePackageAndMegaVNNRequest other = (ChangePackageAndMegaVNNRequest) obj;
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
            ((this.packageNo==null && other.getPackageNo()==null) || 
             (this.packageNo!=null &&
              this.packageNo.equals(other.getPackageNo()))) &&
            ((this.serviceCD==null && other.getServiceCD()==null) || 
             (this.serviceCD!=null &&
              this.serviceCD.equals(other.getServiceCD()))) &&
            ((this.serviceVAS==null && other.getServiceVAS()==null) || 
             (this.serviceVAS!=null &&
              this.serviceVAS.equals(other.getServiceVAS()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.megaMyTV==null && other.getMegaMyTV()==null) || 
             (this.megaMyTV!=null &&
              this.megaMyTV.equals(other.getMegaMyTV()))) &&
            ((this.combo_FiberCD==null && other.getCombo_FiberCD()==null) || 
             (this.combo_FiberCD!=null &&
              this.combo_FiberCD.equals(other.getCombo_FiberCD())));
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
        if (getPackageNo() != null) {
            _hashCode += getPackageNo().hashCode();
        }
        if (getServiceCD() != null) {
            _hashCode += getServiceCD().hashCode();
        }
        if (getServiceVAS() != null) {
            _hashCode += getServiceVAS().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getMegaMyTV() != null) {
            _hashCode += getMegaMyTV().hashCode();
        }
        if (getCombo_FiberCD() != null) {
            _hashCode += getCombo_FiberCD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangePackageAndMegaVNNRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangePackageAndMegaVNNRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PackageNo"));
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
        elemField.setFieldName("serviceVAS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ServiceVAS"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("megaMyTV");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MegaMyTV"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("combo_FiberCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Combo_FiberCD"));
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
