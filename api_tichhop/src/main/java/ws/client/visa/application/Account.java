/**
 * Account.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class Account  implements java.io.Serializable {
    private long accountId;

    private String accountName;

    private String accountPassword;

    private long accountServicePk;

    private String action;

    private String areaCode;

    private long customerId;

    private long deletedId;

    private String fullAction;

    private Object[] historyIds;

    private String newAccountName;

    private Object[] services;

    private String status;

    private long userId;

    public Account() {
    }

    public Account(
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
           long userId) {
           this.accountId = accountId;
           this.accountName = accountName;
           this.accountPassword = accountPassword;
           this.accountServicePk = accountServicePk;
           this.action = action;
           this.areaCode = areaCode;
           this.customerId = customerId;
           this.deletedId = deletedId;
           this.fullAction = fullAction;
           this.historyIds = historyIds;
           this.newAccountName = newAccountName;
           this.services = services;
           this.status = status;
           this.userId = userId;
    }


    /**
     * Gets the accountId value for this Account.
     * 
     * @return accountId
     */
    public long getAccountId() {
        return accountId;
    }


    /**
     * Sets the accountId value for this Account.
     * 
     * @param accountId
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }


    /**
     * Gets the accountName value for this Account.
     * 
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }


    /**
     * Sets the accountName value for this Account.
     * 
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    /**
     * Gets the accountPassword value for this Account.
     * 
     * @return accountPassword
     */
    public String getAccountPassword() {
        return accountPassword;
    }


    /**
     * Sets the accountPassword value for this Account.
     * 
     * @param accountPassword
     */
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }


    /**
     * Gets the accountServicePk value for this Account.
     * 
     * @return accountServicePk
     */
    public long getAccountServicePk() {
        return accountServicePk;
    }


    /**
     * Sets the accountServicePk value for this Account.
     * 
     * @param accountServicePk
     */
    public void setAccountServicePk(long accountServicePk) {
        this.accountServicePk = accountServicePk;
    }


    /**
     * Gets the action value for this Account.
     * 
     * @return action
     */
    public String getAction() {
        return action;
    }


    /**
     * Sets the action value for this Account.
     * 
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }


    /**
     * Gets the areaCode value for this Account.
     * 
     * @return areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this Account.
     * 
     * @param areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the customerId value for this Account.
     * 
     * @return customerId
     */
    public long getCustomerId() {
        return customerId;
    }


    /**
     * Sets the customerId value for this Account.
     * 
     * @param customerId
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


    /**
     * Gets the deletedId value for this Account.
     * 
     * @return deletedId
     */
    public long getDeletedId() {
        return deletedId;
    }


    /**
     * Sets the deletedId value for this Account.
     * 
     * @param deletedId
     */
    public void setDeletedId(long deletedId) {
        this.deletedId = deletedId;
    }


    /**
     * Gets the fullAction value for this Account.
     * 
     * @return fullAction
     */
    public String getFullAction() {
        return fullAction;
    }


    /**
     * Sets the fullAction value for this Account.
     * 
     * @param fullAction
     */
    public void setFullAction(String fullAction) {
        this.fullAction = fullAction;
    }


    /**
     * Gets the historyIds value for this Account.
     * 
     * @return historyIds
     */
    public Object[] getHistoryIds() {
        return historyIds;
    }


    /**
     * Sets the historyIds value for this Account.
     * 
     * @param historyIds
     */
    public void setHistoryIds(Object[] historyIds) {
        this.historyIds = historyIds;
    }


    /**
     * Gets the newAccountName value for this Account.
     * 
     * @return newAccountName
     */
    public String getNewAccountName() {
        return newAccountName;
    }


    /**
     * Sets the newAccountName value for this Account.
     * 
     * @param newAccountName
     */
    public void setNewAccountName(String newAccountName) {
        this.newAccountName = newAccountName;
    }


    /**
     * Gets the services value for this Account.
     * 
     * @return services
     */
    public Object[] getServices() {
        return services;
    }


    /**
     * Sets the services value for this Account.
     * 
     * @param services
     */
    public void setServices(Object[] services) {
        this.services = services;
    }


    /**
     * Gets the status value for this Account.
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Account.
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Gets the userId value for this Account.
     * 
     * @return userId
     */
    public long getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this Account.
     * 
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.accountId == other.getAccountId() &&
            ((this.accountName==null && other.getAccountName()==null) || 
             (this.accountName!=null &&
              this.accountName.equals(other.getAccountName()))) &&
            ((this.accountPassword==null && other.getAccountPassword()==null) || 
             (this.accountPassword!=null &&
              this.accountPassword.equals(other.getAccountPassword()))) &&
            this.accountServicePk == other.getAccountServicePk() &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.areaCode==null && other.getAreaCode()==null) || 
             (this.areaCode!=null &&
              this.areaCode.equals(other.getAreaCode()))) &&
            this.customerId == other.getCustomerId() &&
            this.deletedId == other.getDeletedId() &&
            ((this.fullAction==null && other.getFullAction()==null) || 
             (this.fullAction!=null &&
              this.fullAction.equals(other.getFullAction()))) &&
            ((this.historyIds==null && other.getHistoryIds()==null) || 
             (this.historyIds!=null &&
              java.util.Arrays.equals(this.historyIds, other.getHistoryIds()))) &&
            ((this.newAccountName==null && other.getNewAccountName()==null) || 
             (this.newAccountName!=null &&
              this.newAccountName.equals(other.getNewAccountName()))) &&
            ((this.services==null && other.getServices()==null) || 
             (this.services!=null &&
              java.util.Arrays.equals(this.services, other.getServices()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            this.userId == other.getUserId();
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
        _hashCode += new Long(getAccountId()).hashCode();
        if (getAccountName() != null) {
            _hashCode += getAccountName().hashCode();
        }
        if (getAccountPassword() != null) {
            _hashCode += getAccountPassword().hashCode();
        }
        _hashCode += new Long(getAccountServicePk()).hashCode();
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getAreaCode() != null) {
            _hashCode += getAreaCode().hashCode();
        }
        _hashCode += new Long(getCustomerId()).hashCode();
        _hashCode += new Long(getDeletedId()).hashCode();
        if (getFullAction() != null) {
            _hashCode += getFullAction().hashCode();
        }
        if (getHistoryIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHistoryIds());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getHistoryIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNewAccountName() != null) {
            _hashCode += getNewAccountName().hashCode();
        }
        if (getServices() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getServices());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getServices(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        _hashCode += new Long(getUserId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Account.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://prov.model.hyframe.hyperlogy", "Account"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountPassword");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountPassword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountServicePk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountServicePk"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deletedId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deletedId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullAction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fullAction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("historyIds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "historyIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newAccountName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "newAccountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("services");
        elemField.setXmlName(new javax.xml.namespace.QName("", "services"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
