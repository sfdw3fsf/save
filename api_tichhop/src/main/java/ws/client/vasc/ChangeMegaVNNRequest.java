/**
 * ChangeMegaVNNRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeMegaVNNRequest  implements java.io.Serializable {
    private String IPTVAccount;

    private String megaMyTV;

    private String combo_FiberCD;

    public ChangeMegaVNNRequest() {
    }

    public ChangeMegaVNNRequest(
           String IPTVAccount,
           String megaMyTV,
           String combo_FiberCD) {
           this.IPTVAccount = IPTVAccount;
           this.megaMyTV = megaMyTV;
           this.combo_FiberCD = combo_FiberCD;
    }


    /**
     * Gets the IPTVAccount value for this ChangeMegaVNNRequest.
     * 
     * @return IPTVAccount
     */
    public String getIPTVAccount() {
        return IPTVAccount;
    }


    /**
     * Sets the IPTVAccount value for this ChangeMegaVNNRequest.
     * 
     * @param IPTVAccount
     */
    public void setIPTVAccount(String IPTVAccount) {
        this.IPTVAccount = IPTVAccount;
    }


    /**
     * Gets the megaMyTV value for this ChangeMegaVNNRequest.
     * 
     * @return megaMyTV
     */
    public String getMegaMyTV() {
        return megaMyTV;
    }


    /**
     * Sets the megaMyTV value for this ChangeMegaVNNRequest.
     * 
     * @param megaMyTV
     */
    public void setMegaMyTV(String megaMyTV) {
        this.megaMyTV = megaMyTV;
    }


    /**
     * Gets the combo_FiberCD value for this ChangeMegaVNNRequest.
     * 
     * @return combo_FiberCD
     */
    public String getCombo_FiberCD() {
        return combo_FiberCD;
    }


    /**
     * Sets the combo_FiberCD value for this ChangeMegaVNNRequest.
     * 
     * @param combo_FiberCD
     */
    public void setCombo_FiberCD(String combo_FiberCD) {
        this.combo_FiberCD = combo_FiberCD;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeMegaVNNRequest)) return false;
        ChangeMegaVNNRequest other = (ChangeMegaVNNRequest) obj;
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
        new org.apache.axis.description.TypeDesc(ChangeMegaVNNRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeMegaVNNRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVAccount"));
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
