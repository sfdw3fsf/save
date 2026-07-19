/**
 * UpdateGponCabRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateGponCabRequest  implements java.io.Serializable {
    private String userType;

    private String username;

    private String methodFlg;

    private String comboUsername;

    private String cabNumber;

    private String contractCD;

    private String signDate;

    private String activeDate;

    private String chargeType;

    public UpdateGponCabRequest() {
    }

    public UpdateGponCabRequest(
           String userType,
           String username,
           String methodFlg,
           String comboUsername,
           String cabNumber,
           String contractCD,
           String signDate,
           String activeDate,
           String chargeType) {
           this.userType = userType;
           this.username = username;
           this.methodFlg = methodFlg;
           this.comboUsername = comboUsername;
           this.cabNumber = cabNumber;
           this.contractCD = contractCD;
           this.signDate = signDate;
           this.activeDate = activeDate;
           this.chargeType = chargeType;
    }


    /**
     * Gets the userType value for this UpdateGponCabRequest.
     * 
     * @return userType
     */
    public String getUserType() {
        return userType;
    }


    /**
     * Sets the userType value for this UpdateGponCabRequest.
     * 
     * @param userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }


    /**
     * Gets the username value for this UpdateGponCabRequest.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this UpdateGponCabRequest.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the methodFlg value for this UpdateGponCabRequest.
     * 
     * @return methodFlg
     */
    public String getMethodFlg() {
        return methodFlg;
    }


    /**
     * Sets the methodFlg value for this UpdateGponCabRequest.
     * 
     * @param methodFlg
     */
    public void setMethodFlg(String methodFlg) {
        this.methodFlg = methodFlg;
    }


    /**
     * Gets the comboUsername value for this UpdateGponCabRequest.
     * 
     * @return comboUsername
     */
    public String getComboUsername() {
        return comboUsername;
    }


    /**
     * Sets the comboUsername value for this UpdateGponCabRequest.
     * 
     * @param comboUsername
     */
    public void setComboUsername(String comboUsername) {
        this.comboUsername = comboUsername;
    }


    /**
     * Gets the cabNumber value for this UpdateGponCabRequest.
     * 
     * @return cabNumber
     */
    public String getCabNumber() {
        return cabNumber;
    }


    /**
     * Sets the cabNumber value for this UpdateGponCabRequest.
     * 
     * @param cabNumber
     */
    public void setCabNumber(String cabNumber) {
        this.cabNumber = cabNumber;
    }


    /**
     * Gets the contractCD value for this UpdateGponCabRequest.
     * 
     * @return contractCD
     */
    public String getContractCD() {
        return contractCD;
    }


    /**
     * Sets the contractCD value for this UpdateGponCabRequest.
     * 
     * @param contractCD
     */
    public void setContractCD(String contractCD) {
        this.contractCD = contractCD;
    }


    /**
     * Gets the signDate value for this UpdateGponCabRequest.
     * 
     * @return signDate
     */
    public String getSignDate() {
        return signDate;
    }


    /**
     * Sets the signDate value for this UpdateGponCabRequest.
     * 
     * @param signDate
     */
    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }


    /**
     * Gets the activeDate value for this UpdateGponCabRequest.
     * 
     * @return activeDate
     */
    public String getActiveDate() {
        return activeDate;
    }


    /**
     * Sets the activeDate value for this UpdateGponCabRequest.
     * 
     * @param activeDate
     */
    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }


    /**
     * Gets the chargeType value for this UpdateGponCabRequest.
     * 
     * @return chargeType
     */
    public String getChargeType() {
        return chargeType;
    }


    /**
     * Sets the chargeType value for this UpdateGponCabRequest.
     * 
     * @param chargeType
     */
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateGponCabRequest)) return false;
        UpdateGponCabRequest other = (UpdateGponCabRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.userType==null && other.getUserType()==null) || 
             (this.userType!=null &&
              this.userType.equals(other.getUserType()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.methodFlg==null && other.getMethodFlg()==null) || 
             (this.methodFlg!=null &&
              this.methodFlg.equals(other.getMethodFlg()))) &&
            ((this.comboUsername==null && other.getComboUsername()==null) || 
             (this.comboUsername!=null &&
              this.comboUsername.equals(other.getComboUsername()))) &&
            ((this.cabNumber==null && other.getCabNumber()==null) || 
             (this.cabNumber!=null &&
              this.cabNumber.equals(other.getCabNumber()))) &&
            ((this.contractCD==null && other.getContractCD()==null) || 
             (this.contractCD!=null &&
              this.contractCD.equals(other.getContractCD()))) &&
            ((this.signDate==null && other.getSignDate()==null) || 
             (this.signDate!=null &&
              this.signDate.equals(other.getSignDate()))) &&
            ((this.activeDate==null && other.getActiveDate()==null) || 
             (this.activeDate!=null &&
              this.activeDate.equals(other.getActiveDate()))) &&
            ((this.chargeType==null && other.getChargeType()==null) || 
             (this.chargeType!=null &&
              this.chargeType.equals(other.getChargeType())));
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
        if (getUserType() != null) {
            _hashCode += getUserType().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getMethodFlg() != null) {
            _hashCode += getMethodFlg().hashCode();
        }
        if (getComboUsername() != null) {
            _hashCode += getComboUsername().hashCode();
        }
        if (getCabNumber() != null) {
            _hashCode += getCabNumber().hashCode();
        }
        if (getContractCD() != null) {
            _hashCode += getContractCD().hashCode();
        }
        if (getSignDate() != null) {
            _hashCode += getSignDate().hashCode();
        }
        if (getActiveDate() != null) {
            _hashCode += getActiveDate().hashCode();
        }
        if (getChargeType() != null) {
            _hashCode += getChargeType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateGponCabRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "UpdateGponCabRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UserType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("methodFlg");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "MethodFlg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comboUsername");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ComboUsername"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cabNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CabNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ContractCD"));
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
        elemField.setFieldName("activeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ActiveDate"));
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
