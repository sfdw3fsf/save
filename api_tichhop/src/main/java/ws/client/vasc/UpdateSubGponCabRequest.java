/**
 * UpdateSubGponCabRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateSubGponCabRequest  implements java.io.Serializable {
    private String IPTVAccount;

    private String gponFlg;

    private String chargeType;

    private int noCab;

    private String billDate;

    private String signDate;

    private String reason;

    public UpdateSubGponCabRequest() {
    }

    public UpdateSubGponCabRequest(
           String IPTVAccount,
           String gponFlg,
           String chargeType,
           int noCab,
           String billDate,
           String signDate,
           String reason) {
           this.IPTVAccount = IPTVAccount;
           this.gponFlg = gponFlg;
           this.chargeType = chargeType;
           this.noCab = noCab;
           this.billDate = billDate;
           this.signDate = signDate;
           this.reason = reason;
    }


    /**
     * Gets the IPTVAccount value for this UpdateSubGponCabRequest.
     * 
     * @return IPTVAccount
     */
    public String getIPTVAccount() {
        return IPTVAccount;
    }


    /**
     * Sets the IPTVAccount value for this UpdateSubGponCabRequest.
     * 
     * @param IPTVAccount
     */
    public void setIPTVAccount(String IPTVAccount) {
        this.IPTVAccount = IPTVAccount;
    }


    /**
     * Gets the gponFlg value for this UpdateSubGponCabRequest.
     * 
     * @return gponFlg
     */
    public String getGponFlg() {
        return gponFlg;
    }


    /**
     * Sets the gponFlg value for this UpdateSubGponCabRequest.
     * 
     * @param gponFlg
     */
    public void setGponFlg(String gponFlg) {
        this.gponFlg = gponFlg;
    }


    /**
     * Gets the chargeType value for this UpdateSubGponCabRequest.
     * 
     * @return chargeType
     */
    public String getChargeType() {
        return chargeType;
    }


    /**
     * Sets the chargeType value for this UpdateSubGponCabRequest.
     * 
     * @param chargeType
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }


    /**
     * Gets the noCab value for this UpdateSubGponCabRequest.
     * 
     * @return noCab
     */
    public int getNoCab() {
        return noCab;
    }


    /**
     * Sets the noCab value for this UpdateSubGponCabRequest.
     * 
     * @param noCab
     */
    public void setNoCab(int noCab) {
        this.noCab = noCab;
    }


    /**
     * Gets the billDate value for this UpdateSubGponCabRequest.
     * 
     * @return billDate
     */
    public String getBillDate() {
        return billDate;
    }


    /**
     * Sets the billDate value for this UpdateSubGponCabRequest.
     * 
     * @param billDate
     */
    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }


    /**
     * Gets the signDate value for this UpdateSubGponCabRequest.
     * 
     * @return signDate
     */
    public String getSignDate() {
        return signDate;
    }


    /**
     * Sets the signDate value for this UpdateSubGponCabRequest.
     * 
     * @param signDate
     */
    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }


    /**
     * Gets the reason value for this UpdateSubGponCabRequest.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this UpdateSubGponCabRequest.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateSubGponCabRequest)) return false;
        UpdateSubGponCabRequest other = (UpdateSubGponCabRequest) obj;
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
            ((this.gponFlg==null && other.getGponFlg()==null) || 
             (this.gponFlg!=null &&
              this.gponFlg.equals(other.getGponFlg()))) &&
            ((this.chargeType==null && other.getChargeType()==null) || 
             (this.chargeType!=null &&
              this.chargeType.equals(other.getChargeType()))) &&
            this.noCab == other.getNoCab() &&
            ((this.billDate==null && other.getBillDate()==null) || 
             (this.billDate!=null &&
              this.billDate.equals(other.getBillDate()))) &&
            ((this.signDate==null && other.getSignDate()==null) || 
             (this.signDate!=null &&
              this.signDate.equals(other.getSignDate()))) &&
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
        if (getGponFlg() != null) {
            _hashCode += getGponFlg().hashCode();
        }
        if (getChargeType() != null) {
            _hashCode += getChargeType().hashCode();
        }
        _hashCode += getNoCab();
        if (getBillDate() != null) {
            _hashCode += getBillDate().hashCode();
        }
        if (getSignDate() != null) {
            _hashCode += getSignDate().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateSubGponCabRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "UpdateSubGponCabRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gponFlg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GponFlg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChargeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noCab");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NoCab"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BillDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SignDate"));
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
