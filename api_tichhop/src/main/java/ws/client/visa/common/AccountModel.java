/**
 * AccountModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.common;

public class AccountModel  extends Account  implements java.io.Serializable {
    private String _activeDateStr;

    private String accType;

    private long acctId;

    private java.util.Calendar activeDate;

    private String activeDateStr;

    private int allowedHours;

    private String allowedTime;

    private java.util.Calendar createDate;

    private CustomerModel customer;

    private long dltId;

    private java.util.Calendar lastModifiedDate;

    private int numberPage;

    private String partnerCode;

    private String reason;

    private ReportAccountModel reportAccount;

    private java.util.Calendar time;

    private String toVisaOld;

    private String typeDLTAccount;

    private boolean valid;

    public AccountModel() {
    }

    public AccountModel(
           long accountId,
           String accountName,
           String accountPassword,
           long accountServicePk,
           String action,
           String areaCode,
           long customerId,
           long deletedId,
           String fullAction,
           Object[] historyIds,
           String newAccountName,
           Object[] services,
           String status,
           long userId,
           String _activeDateStr,
           String accType,
           long acctId,
           java.util.Calendar activeDate,
           String activeDateStr,
           int allowedHours,
           String allowedTime,
           java.util.Calendar createDate,
           CustomerModel customer,
           long dltId,
           java.util.Calendar lastModifiedDate,
           int numberPage,
           String partnerCode,
           String reason,
           ReportAccountModel reportAccount,
           java.util.Calendar time,
           String toVisaOld,
           String typeDLTAccount,
           boolean valid) {
        super(
            accountId,
            accountName,
            accountPassword,
            accountServicePk,
            action,
            areaCode,
            customerId,
            deletedId,
            fullAction,
            historyIds,
            newAccountName,
            services,
            status,
            userId);
        this._activeDateStr = _activeDateStr;
        this.accType = accType;
        this.acctId = acctId;
        this.activeDate = activeDate;
        this.activeDateStr = activeDateStr;
        this.allowedHours = allowedHours;
        this.allowedTime = allowedTime;
        this.createDate = createDate;
        this.customer = customer;
        this.dltId = dltId;
        this.lastModifiedDate = lastModifiedDate;
        this.numberPage = numberPage;
        this.partnerCode = partnerCode;
        this.reason = reason;
        this.reportAccount = reportAccount;
        this.time = time;
        this.toVisaOld = toVisaOld;
        this.typeDLTAccount = typeDLTAccount;
        this.valid = valid;
    }


    /**
     * Gets the _activeDateStr value for this AccountModel.
     * 
     * @return _activeDateStr
     */
    public String get_activeDateStr() {
        return _activeDateStr;
    }


    /**
     * Sets the _activeDateStr value for this AccountModel.
     * 
     * @param _activeDateStr
     */
    public void set_activeDateStr(String _activeDateStr) {
        this._activeDateStr = _activeDateStr;
    }


    /**
     * Gets the accType value for this AccountModel.
     * 
     * @return accType
     */
    public String getAccType() {
        return accType;
    }


    /**
     * Sets the accType value for this AccountModel.
     * 
     * @param accType
     */
    public void setAccType(String accType) {
        this.accType = accType;
    }


    /**
     * Gets the acctId value for this AccountModel.
     * 
     * @return acctId
     */
    public long getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this AccountModel.
     * 
     * @param acctId
     */
    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the activeDate value for this AccountModel.
     * 
     * @return activeDate
     */
    public java.util.Calendar getActiveDate() {
        return activeDate;
    }


    /**
     * Sets the activeDate value for this AccountModel.
     * 
     * @param activeDate
     */
    public void setActiveDate(java.util.Calendar activeDate) {
        this.activeDate = activeDate;
    }


    /**
     * Gets the activeDateStr value for this AccountModel.
     * 
     * @return activeDateStr
     */
    public String getActiveDateStr() {
        return activeDateStr;
    }


    /**
     * Sets the activeDateStr value for this AccountModel.
     * 
     * @param activeDateStr
     */
    public void setActiveDateStr(String activeDateStr) {
        this.activeDateStr = activeDateStr;
    }


    /**
     * Gets the allowedHours value for this AccountModel.
     * 
     * @return allowedHours
     */
    public int getAllowedHours() {
        return allowedHours;
    }


    /**
     * Sets the allowedHours value for this AccountModel.
     * 
     * @param allowedHours
     */
    public void setAllowedHours(int allowedHours) {
        this.allowedHours = allowedHours;
    }


    /**
     * Gets the allowedTime value for this AccountModel.
     * 
     * @return allowedTime
     */
    public String getAllowedTime() {
        return allowedTime;
    }


    /**
     * Sets the allowedTime value for this AccountModel.
     * 
     * @param allowedTime
     */
    public void setAllowedTime(String allowedTime) {
        this.allowedTime = allowedTime;
    }


    /**
     * Gets the createDate value for this AccountModel.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this AccountModel.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the customer value for this AccountModel.
     * 
     * @return customer
     */
    public CustomerModel getCustomer() {
        return customer;
    }


    /**
     * Sets the customer value for this AccountModel.
     * 
     * @param customer
     */
    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }


    /**
     * Gets the dltId value for this AccountModel.
     * 
     * @return dltId
     */
    public long getDltId() {
        return dltId;
    }


    /**
     * Sets the dltId value for this AccountModel.
     * 
     * @param dltId
     */
    public void setDltId(long dltId) {
        this.dltId = dltId;
    }


    /**
     * Gets the lastModifiedDate value for this AccountModel.
     * 
     * @return lastModifiedDate
     */
    public java.util.Calendar getLastModifiedDate() {
        return lastModifiedDate;
    }


    /**
     * Sets the lastModifiedDate value for this AccountModel.
     * 
     * @param lastModifiedDate
     */
    public void setLastModifiedDate(java.util.Calendar lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    /**
     * Gets the numberPage value for this AccountModel.
     * 
     * @return numberPage
     */
    public int getNumberPage() {
        return numberPage;
    }


    /**
     * Sets the numberPage value for this AccountModel.
     * 
     * @param numberPage
     */
    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }


    /**
     * Gets the partnerCode value for this AccountModel.
     * 
     * @return partnerCode
     */
    public String getPartnerCode() {
        return partnerCode;
    }


    /**
     * Sets the partnerCode value for this AccountModel.
     * 
     * @param partnerCode
     */
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }


    /**
     * Gets the reason value for this AccountModel.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this AccountModel.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


    /**
     * Gets the reportAccount value for this AccountModel.
     * 
     * @return reportAccount
     */
    public ReportAccountModel getReportAccount() {
        return reportAccount;
    }


    /**
     * Sets the reportAccount value for this AccountModel.
     * 
     * @param reportAccount
     */
    public void setReportAccount(ReportAccountModel reportAccount) {
        this.reportAccount = reportAccount;
    }


    /**
     * Gets the time value for this AccountModel.
     * 
     * @return time
     */
    public java.util.Calendar getTime() {
        return time;
    }


    /**
     * Sets the time value for this AccountModel.
     * 
     * @param time
     */
    public void setTime(java.util.Calendar time) {
        this.time = time;
    }


    /**
     * Gets the toVisaOld value for this AccountModel.
     * 
     * @return toVisaOld
     */
    public String getToVisaOld() {
        return toVisaOld;
    }


    /**
     * Sets the toVisaOld value for this AccountModel.
     * 
     * @param toVisaOld
     */
    public void setToVisaOld(String toVisaOld) {
        this.toVisaOld = toVisaOld;
    }


    /**
     * Gets the typeDLTAccount value for this AccountModel.
     * 
     * @return typeDLTAccount
     */
    public String getTypeDLTAccount() {
        return typeDLTAccount;
    }


    /**
     * Sets the typeDLTAccount value for this AccountModel.
     * 
     * @param typeDLTAccount
     */
    public void setTypeDLTAccount(String typeDLTAccount) {
        this.typeDLTAccount = typeDLTAccount;
    }


    /**
     * Gets the valid value for this AccountModel.
     * 
     * @return valid
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * Sets the valid value for this AccountModel.
     * 
     * @param valid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AccountModel)) return false;
        AccountModel other = (AccountModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this._activeDateStr==null && other.get_activeDateStr()==null) || 
             (this._activeDateStr!=null &&
              this._activeDateStr.equals(other.get_activeDateStr()))) &&
            ((this.accType==null && other.getAccType()==null) || 
             (this.accType!=null &&
              this.accType.equals(other.getAccType()))) &&
            this.acctId == other.getAcctId() &&
            ((this.activeDate==null && other.getActiveDate()==null) || 
             (this.activeDate!=null &&
              this.activeDate.equals(other.getActiveDate()))) &&
            ((this.activeDateStr==null && other.getActiveDateStr()==null) || 
             (this.activeDateStr!=null &&
              this.activeDateStr.equals(other.getActiveDateStr()))) &&
            this.allowedHours == other.getAllowedHours() &&
            ((this.allowedTime==null && other.getAllowedTime()==null) || 
             (this.allowedTime!=null &&
              this.allowedTime.equals(other.getAllowedTime()))) &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            ((this.customer==null && other.getCustomer()==null) || 
             (this.customer!=null &&
              this.customer.equals(other.getCustomer()))) &&
            this.dltId == other.getDltId() &&
            ((this.lastModifiedDate==null && other.getLastModifiedDate()==null) || 
             (this.lastModifiedDate!=null &&
              this.lastModifiedDate.equals(other.getLastModifiedDate()))) &&
            this.numberPage == other.getNumberPage() &&
            ((this.partnerCode==null && other.getPartnerCode()==null) || 
             (this.partnerCode!=null &&
              this.partnerCode.equals(other.getPartnerCode()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.reportAccount==null && other.getReportAccount()==null) || 
             (this.reportAccount!=null &&
              this.reportAccount.equals(other.getReportAccount()))) &&
            ((this.time==null && other.getTime()==null) || 
             (this.time!=null &&
              this.time.equals(other.getTime()))) &&
            ((this.toVisaOld==null && other.getToVisaOld()==null) || 
             (this.toVisaOld!=null &&
              this.toVisaOld.equals(other.getToVisaOld()))) &&
            ((this.typeDLTAccount==null && other.getTypeDLTAccount()==null) || 
             (this.typeDLTAccount!=null &&
              this.typeDLTAccount.equals(other.getTypeDLTAccount()))) &&
            this.valid == other.isValid();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (get_activeDateStr() != null) {
            _hashCode += get_activeDateStr().hashCode();
        }
        if (getAccType() != null) {
            _hashCode += getAccType().hashCode();
        }
        _hashCode += new Long(getAcctId()).hashCode();
        if (getActiveDate() != null) {
            _hashCode += getActiveDate().hashCode();
        }
        if (getActiveDateStr() != null) {
            _hashCode += getActiveDateStr().hashCode();
        }
        _hashCode += getAllowedHours();
        if (getAllowedTime() != null) {
            _hashCode += getAllowedTime().hashCode();
        }
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        if (getCustomer() != null) {
            _hashCode += getCustomer().hashCode();
        }
        _hashCode += new Long(getDltId()).hashCode();
        if (getLastModifiedDate() != null) {
            _hashCode += getLastModifiedDate().hashCode();
        }
        _hashCode += getNumberPage();
        if (getPartnerCode() != null) {
            _hashCode += getPartnerCode().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getReportAccount() != null) {
            _hashCode += getReportAccount().hashCode();
        }
        if (getTime() != null) {
            _hashCode += getTime().hashCode();
        }
        if (getToVisaOld() != null) {
            _hashCode += getToVisaOld().hashCode();
        }
        if (getTypeDLTAccount() != null) {
            _hashCode += getTypeDLTAccount().hashCode();
        }
        _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccountModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:account.model.hyframe.hyperlogy", "AccountModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_activeDateStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "_activeDateStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acctId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acctId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeDateStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "activeDateStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowedHours");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allowedHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowedTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allowedTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customer"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:customer.model.hyframe.hyperlogy", "CustomerModel"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dltId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dltId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifiedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastModifiedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberPage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numberPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partnerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "partnerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reportAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:report.model.hyframe.hyperlogy", "ReportAccountModel"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time");
        elemField.setXmlName(new javax.xml.namespace.QName("", "time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toVisaOld");
        elemField.setXmlName(new javax.xml.namespace.QName("", "toVisaOld"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeDLTAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "typeDLTAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
