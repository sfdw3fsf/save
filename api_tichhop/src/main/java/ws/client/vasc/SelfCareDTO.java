/**
 * SelfCareDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class SelfCareDTO  implements java.io.Serializable {
    private String username;

    private String password;

    private String areaCode;

    private String packCD;

    private String tel;

    private String IP;

    private int trialMonths;

    private String referralCode;

    public SelfCareDTO() {
    }

    public SelfCareDTO(
           String username,
           String password,
           String areaCode,
           String packCD,
           String tel,
           String IP,
           int trialMonths,
           String referralCode) {
           this.username = username;
           this.password = password;
           this.areaCode = areaCode;
           this.packCD = packCD;
           this.tel = tel;
           this.IP = IP;
           this.trialMonths = trialMonths;
           this.referralCode = referralCode;
    }


    /**
     * Gets the username value for this SelfCareDTO.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this SelfCareDTO.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the password value for this SelfCareDTO.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this SelfCareDTO.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Gets the areaCode value for this SelfCareDTO.
     * 
     * @return areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this SelfCareDTO.
     * 
     * @param areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the packCD value for this SelfCareDTO.
     * 
     * @return packCD
     */
    public String getPackCD() {
        return packCD;
    }


    /**
     * Sets the packCD value for this SelfCareDTO.
     * 
     * @param packCD
     */
    public void setPackCD(String packCD) {
        this.packCD = packCD;
    }


    /**
     * Gets the tel value for this SelfCareDTO.
     * 
     * @return tel
     */
    public String getTel() {
        return tel;
    }


    /**
     * Sets the tel value for this SelfCareDTO.
     * 
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }


    /**
     * Gets the IP value for this SelfCareDTO.
     * 
     * @return IP
     */
    public String getIP() {
        return IP;
    }


    /**
     * Sets the IP value for this SelfCareDTO.
     * 
     * @param IP
     */
    public void setIP(String IP) {
        this.IP = IP;
    }


    /**
     * Gets the trialMonths value for this SelfCareDTO.
     * 
     * @return trialMonths
     */
    public int getTrialMonths() {
        return trialMonths;
    }


    /**
     * Sets the trialMonths value for this SelfCareDTO.
     * 
     * @param trialMonths
     */
    public void setTrialMonths(int trialMonths) {
        this.trialMonths = trialMonths;
    }


    /**
     * Gets the referralCode value for this SelfCareDTO.
     * 
     * @return referralCode
     */
    public String getReferralCode() {
        return referralCode;
    }


    /**
     * Sets the referralCode value for this SelfCareDTO.
     * 
     * @param referralCode
     */
    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SelfCareDTO)) return false;
        SelfCareDTO other = (SelfCareDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.areaCode==null && other.getAreaCode()==null) || 
             (this.areaCode!=null &&
              this.areaCode.equals(other.getAreaCode()))) &&
            ((this.packCD==null && other.getPackCD()==null) || 
             (this.packCD!=null &&
              this.packCD.equals(other.getPackCD()))) &&
            ((this.tel==null && other.getTel()==null) || 
             (this.tel!=null &&
              this.tel.equals(other.getTel()))) &&
            ((this.IP==null && other.getIP()==null) || 
             (this.IP!=null &&
              this.IP.equals(other.getIP()))) &&
            this.trialMonths == other.getTrialMonths() &&
            ((this.referralCode==null && other.getReferralCode()==null) || 
             (this.referralCode!=null &&
              this.referralCode.equals(other.getReferralCode())));
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getAreaCode() != null) {
            _hashCode += getAreaCode().hashCode();
        }
        if (getPackCD() != null) {
            _hashCode += getPackCD().hashCode();
        }
        if (getTel() != null) {
            _hashCode += getTel().hashCode();
        }
        if (getIP() != null) {
            _hashCode += getIP().hashCode();
        }
        _hashCode += getTrialMonths();
        if (getReferralCode() != null) {
            _hashCode += getReferralCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SelfCareDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "SelfCareDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "AreaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PackCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Tel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trialMonths");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TrialMonths"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("referralCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ReferralCode"));
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
