/**
 * ChangePackageRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangePackageRequest  implements java.io.Serializable {
    private String combo_FiberCD;

    private String addOnCD;

    private String packageNo;

    private String reason;

    private String megaMyTV;

    private String IPTVAccount;

    public ChangePackageRequest() {
    }

    public ChangePackageRequest(
           String combo_FiberCD,
           String addOnCD,
           String packageNo,
           String reason,
           String megaMyTV,
           String IPTVAccount) {
           this.combo_FiberCD = combo_FiberCD;
           this.addOnCD = addOnCD;
           this.packageNo = packageNo;
           this.reason = reason;
           this.megaMyTV = megaMyTV;
           this.IPTVAccount = IPTVAccount;
    }


    /**
     * Gets the combo_FiberCD value for this ChangePackageRequest.
     * 
     * @return combo_FiberCD
     */
    public String getCombo_FiberCD() {
        return combo_FiberCD;
    }


    /**
     * Sets the combo_FiberCD value for this ChangePackageRequest.
     * 
     * @param combo_FiberCD
     */
    public void setCombo_FiberCD(String combo_FiberCD) {
        this.combo_FiberCD = combo_FiberCD;
    }


    /**
     * Gets the addOnCD value for this ChangePackageRequest.
     * 
     * @return addOnCD
     */
    public String getAddOnCD() {
        return addOnCD;
    }


    /**
     * Sets the addOnCD value for this ChangePackageRequest.
     * 
     * @param addOnCD
     */
    public void setAddOnCD(String addOnCD) {
        this.addOnCD = addOnCD;
    }


    /**
     * Gets the packageNo value for this ChangePackageRequest.
     * 
     * @return packageNo
     */
    public String getPackageNo() {
        return packageNo;
    }


    /**
     * Sets the packageNo value for this ChangePackageRequest.
     * 
     * @param packageNo
     */
    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo;
    }


    /**
     * Gets the reason value for this ChangePackageRequest.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this ChangePackageRequest.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


    /**
     * Gets the megaMyTV value for this ChangePackageRequest.
     * 
     * @return megaMyTV
     */
    public String getMegaMyTV() {
        return megaMyTV;
    }


    /**
     * Sets the megaMyTV value for this ChangePackageRequest.
     * 
     * @param megaMyTV
     */
    public void setMegaMyTV(String megaMyTV) {
        this.megaMyTV = megaMyTV;
    }


    /**
     * Gets the IPTVAccount value for this ChangePackageRequest.
     * 
     * @return IPTVAccount
     */
    public String getIPTVAccount() {
        return IPTVAccount;
    }


    /**
     * Sets the IPTVAccount value for this ChangePackageRequest.
     * 
     * @param IPTVAccount
     */
    public void setIPTVAccount(String IPTVAccount) {
        this.IPTVAccount = IPTVAccount;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangePackageRequest)) return false;
        ChangePackageRequest other = (ChangePackageRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.combo_FiberCD==null && other.getCombo_FiberCD()==null) || 
             (this.combo_FiberCD!=null &&
              this.combo_FiberCD.equals(other.getCombo_FiberCD()))) &&
            ((this.addOnCD==null && other.getAddOnCD()==null) || 
             (this.addOnCD!=null &&
              this.addOnCD.equals(other.getAddOnCD()))) &&
            ((this.packageNo==null && other.getPackageNo()==null) || 
             (this.packageNo!=null &&
              this.packageNo.equals(other.getPackageNo()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.megaMyTV==null && other.getMegaMyTV()==null) || 
             (this.megaMyTV!=null &&
              this.megaMyTV.equals(other.getMegaMyTV()))) &&
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
        if (getCombo_FiberCD() != null) {
            _hashCode += getCombo_FiberCD().hashCode();
        }
        if (getAddOnCD() != null) {
            _hashCode += getAddOnCD().hashCode();
        }
        if (getPackageNo() != null) {
            _hashCode += getPackageNo().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getMegaMyTV() != null) {
            _hashCode += getMegaMyTV().hashCode();
        }
        if (getIPTVAccount() != null) {
            _hashCode += getIPTVAccount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangePackageRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangePackageRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("combo_FiberCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Combo_FiberCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addOnCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "AddOnCD"));
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
