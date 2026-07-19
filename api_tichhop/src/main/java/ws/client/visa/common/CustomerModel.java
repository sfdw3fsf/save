/**
 * CustomerModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.common;

public class CustomerModel  implements java.io.Serializable {
    private String accType;

    private String accountName;

    private long acctId;

    private java.util.Calendar activeDate;

    private String address;

    private long agentId;

    private String bankAccount;

    private long bankCode;

    private int beginRow;

    private long bigCustomer;

    private String birthDay;

    private long candidateId;

    private long channelId;

    private String city;

    private java.util.Calendar createDate;

    private int customerType;

    private String description;

    private String district;

    private String email;

    private int endRow;

    private String firstName;

    private long groupId;

    private long hstId;

    private long id;

    private String identityNumber;

    private int jobId;

    private java.util.Calendar lastModifyDate;

    private String lastName;

    private String mobile;

    private int numberPage;

    private String phone;

    private String postCode;

    private int sex;

    private String status;

    private String stringQuery;

    private String taxCode;

    private java.util.Calendar time;

    private String userId;

    private String valueColumn;

    private String ward;

    public CustomerModel() {
    }

    public CustomerModel(
           String accType,
           String accountName,
           long acctId,
           java.util.Calendar activeDate,
           String address,
           long agentId,
           String bankAccount,
           long bankCode,
           int beginRow,
           long bigCustomer,
           String birthDay,
           long candidateId,
           long channelId,
           String city,
           java.util.Calendar createDate,
           int customerType,
           String description,
           String district,
           String email,
           int endRow,
           String firstName,
           long groupId,
           long hstId,
           long id,
           String identityNumber,
           int jobId,
           java.util.Calendar lastModifyDate,
           String lastName,
           String mobile,
           int numberPage,
           String phone,
           String postCode,
           int sex,
           String status,
           String stringQuery,
           String taxCode,
           java.util.Calendar time,
           String userId,
           String valueColumn,
           String ward) {
           this.accType = accType;
           this.accountName = accountName;
           this.acctId = acctId;
           this.activeDate = activeDate;
           this.address = address;
           this.agentId = agentId;
           this.bankAccount = bankAccount;
           this.bankCode = bankCode;
           this.beginRow = beginRow;
           this.bigCustomer = bigCustomer;
           this.birthDay = birthDay;
           this.candidateId = candidateId;
           this.channelId = channelId;
           this.city = city;
           this.createDate = createDate;
           this.customerType = customerType;
           this.description = description;
           this.district = district;
           this.email = email;
           this.endRow = endRow;
           this.firstName = firstName;
           this.groupId = groupId;
           this.hstId = hstId;
           this.id = id;
           this.identityNumber = identityNumber;
           this.jobId = jobId;
           this.lastModifyDate = lastModifyDate;
           this.lastName = lastName;
           this.mobile = mobile;
           this.numberPage = numberPage;
           this.phone = phone;
           this.postCode = postCode;
           this.sex = sex;
           this.status = status;
           this.stringQuery = stringQuery;
           this.taxCode = taxCode;
           this.time = time;
           this.userId = userId;
           this.valueColumn = valueColumn;
           this.ward = ward;
    }


    /**
     * Gets the accType value for this CustomerModel.
     * 
     * @return accType
     */
    public String getAccType() {
        return accType;
    }


    /**
     * Sets the accType value for this CustomerModel.
     * 
     * @param accType
     */
    public void setAccType(String accType) {
        this.accType = accType;
    }


    /**
     * Gets the accountName value for this CustomerModel.
     * 
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }


    /**
     * Sets the accountName value for this CustomerModel.
     * 
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    /**
     * Gets the acctId value for this CustomerModel.
     * 
     * @return acctId
     */
    public long getAcctId() {
        return acctId;
    }


    /**
     * Sets the acctId value for this CustomerModel.
     * 
     * @param acctId
     */
    public void setAcctId(long acctId) {
        this.acctId = acctId;
    }


    /**
     * Gets the activeDate value for this CustomerModel.
     * 
     * @return activeDate
     */
    public java.util.Calendar getActiveDate() {
        return activeDate;
    }


    /**
     * Sets the activeDate value for this CustomerModel.
     * 
     * @param activeDate
     */
    public void setActiveDate(java.util.Calendar activeDate) {
        this.activeDate = activeDate;
    }


    /**
     * Gets the address value for this CustomerModel.
     * 
     * @return address
     */
    public String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this CustomerModel.
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * Gets the agentId value for this CustomerModel.
     * 
     * @return agentId
     */
    public long getAgentId() {
        return agentId;
    }


    /**
     * Sets the agentId value for this CustomerModel.
     * 
     * @param agentId
     */
    public void setAgentId(long agentId) {
        this.agentId = agentId;
    }


    /**
     * Gets the bankAccount value for this CustomerModel.
     * 
     * @return bankAccount
     */
    public String getBankAccount() {
        return bankAccount;
    }


    /**
     * Sets the bankAccount value for this CustomerModel.
     * 
     * @param bankAccount
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }


    /**
     * Gets the bankCode value for this CustomerModel.
     * 
     * @return bankCode
     */
    public long getBankCode() {
        return bankCode;
    }


    /**
     * Sets the bankCode value for this CustomerModel.
     * 
     * @param bankCode
     */
    public void setBankCode(long bankCode) {
        this.bankCode = bankCode;
    }


    /**
     * Gets the beginRow value for this CustomerModel.
     * 
     * @return beginRow
     */
    public int getBeginRow() {
        return beginRow;
    }


    /**
     * Sets the beginRow value for this CustomerModel.
     * 
     * @param beginRow
     */
    public void setBeginRow(int beginRow) {
        this.beginRow = beginRow;
    }


    /**
     * Gets the bigCustomer value for this CustomerModel.
     * 
     * @return bigCustomer
     */
    public long getBigCustomer() {
        return bigCustomer;
    }


    /**
     * Sets the bigCustomer value for this CustomerModel.
     * 
     * @param bigCustomer
     */
    public void setBigCustomer(long bigCustomer) {
        this.bigCustomer = bigCustomer;
    }


    /**
     * Gets the birthDay value for this CustomerModel.
     * 
     * @return birthDay
     */
    public String getBirthDay() {
        return birthDay;
    }


    /**
     * Sets the birthDay value for this CustomerModel.
     * 
     * @param birthDay
     */
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }


    /**
     * Gets the candidateId value for this CustomerModel.
     * 
     * @return candidateId
     */
    public long getCandidateId() {
        return candidateId;
    }


    /**
     * Sets the candidateId value for this CustomerModel.
     * 
     * @param candidateId
     */
    public void setCandidateId(long candidateId) {
        this.candidateId = candidateId;
    }


    /**
     * Gets the channelId value for this CustomerModel.
     * 
     * @return channelId
     */
    public long getChannelId() {
        return channelId;
    }


    /**
     * Sets the channelId value for this CustomerModel.
     * 
     * @param channelId
     */
    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }


    /**
     * Gets the city value for this CustomerModel.
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }


    /**
     * Sets the city value for this CustomerModel.
     * 
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Gets the createDate value for this CustomerModel.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this CustomerModel.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the customerType value for this CustomerModel.
     * 
     * @return customerType
     */
    public int getCustomerType() {
        return customerType;
    }


    /**
     * Sets the customerType value for this CustomerModel.
     * 
     * @param customerType
     */
    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }


    /**
     * Gets the description value for this CustomerModel.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this CustomerModel.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Gets the district value for this CustomerModel.
     * 
     * @return district
     */
    public String getDistrict() {
        return district;
    }


    /**
     * Sets the district value for this CustomerModel.
     * 
     * @param district
     */
    public void setDistrict(String district) {
        this.district = district;
    }


    /**
     * Gets the email value for this CustomerModel.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this CustomerModel.
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the endRow value for this CustomerModel.
     * 
     * @return endRow
     */
    public int getEndRow() {
        return endRow;
    }


    /**
     * Sets the endRow value for this CustomerModel.
     * 
     * @param endRow
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }


    /**
     * Gets the firstName value for this CustomerModel.
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this CustomerModel.
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the groupId value for this CustomerModel.
     * 
     * @return groupId
     */
    public long getGroupId() {
        return groupId;
    }


    /**
     * Sets the groupId value for this CustomerModel.
     * 
     * @param groupId
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }


    /**
     * Gets the hstId value for this CustomerModel.
     * 
     * @return hstId
     */
    public long getHstId() {
        return hstId;
    }


    /**
     * Sets the hstId value for this CustomerModel.
     * 
     * @param hstId
     */
    public void setHstId(long hstId) {
        this.hstId = hstId;
    }


    /**
     * Gets the id value for this CustomerModel.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this CustomerModel.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the identityNumber value for this CustomerModel.
     * 
     * @return identityNumber
     */
    public String getIdentityNumber() {
        return identityNumber;
    }


    /**
     * Sets the identityNumber value for this CustomerModel.
     * 
     * @param identityNumber
     */
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }


    /**
     * Gets the jobId value for this CustomerModel.
     * 
     * @return jobId
     */
    public int getJobId() {
        return jobId;
    }


    /**
     * Sets the jobId value for this CustomerModel.
     * 
     * @param jobId
     */
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }


    /**
     * Gets the lastModifyDate value for this CustomerModel.
     * 
     * @return lastModifyDate
     */
    public java.util.Calendar getLastModifyDate() {
        return lastModifyDate;
    }


    /**
     * Sets the lastModifyDate value for this CustomerModel.
     * 
     * @param lastModifyDate
     */
    public void setLastModifyDate(java.util.Calendar lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }


    /**
     * Gets the lastName value for this CustomerModel.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this CustomerModel.
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the mobile value for this CustomerModel.
     * 
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this CustomerModel.
     * 
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the numberPage value for this CustomerModel.
     * 
     * @return numberPage
     */
    public int getNumberPage() {
        return numberPage;
    }


    /**
     * Sets the numberPage value for this CustomerModel.
     * 
     * @param numberPage
     */
    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }


    /**
     * Gets the phone value for this CustomerModel.
     * 
     * @return phone
     */
    public String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this CustomerModel.
     * 
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     * Gets the postCode value for this CustomerModel.
     * 
     * @return postCode
     */
    public String getPostCode() {
        return postCode;
    }


    /**
     * Sets the postCode value for this CustomerModel.
     * 
     * @param postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    /**
     * Gets the sex value for this CustomerModel.
     * 
     * @return sex
     */
    public int getSex() {
        return sex;
    }


    /**
     * Sets the sex value for this CustomerModel.
     * 
     * @param sex
     */
    public void setSex(int sex) {
        this.sex = sex;
    }


    /**
     * Gets the status value for this CustomerModel.
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this CustomerModel.
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Gets the stringQuery value for this CustomerModel.
     * 
     * @return stringQuery
     */
    public String getStringQuery() {
        return stringQuery;
    }


    /**
     * Sets the stringQuery value for this CustomerModel.
     * 
     * @param stringQuery
     */
    public void setStringQuery(String stringQuery) {
        this.stringQuery = stringQuery;
    }


    /**
     * Gets the taxCode value for this CustomerModel.
     * 
     * @return taxCode
     */
    public String getTaxCode() {
        return taxCode;
    }


    /**
     * Sets the taxCode value for this CustomerModel.
     * 
     * @param taxCode
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }


    /**
     * Gets the time value for this CustomerModel.
     * 
     * @return time
     */
    public java.util.Calendar getTime() {
        return time;
    }


    /**
     * Sets the time value for this CustomerModel.
     * 
     * @param time
     */
    public void setTime(java.util.Calendar time) {
        this.time = time;
    }


    /**
     * Gets the userId value for this CustomerModel.
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this CustomerModel.
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }


    /**
     * Gets the valueColumn value for this CustomerModel.
     * 
     * @return valueColumn
     */
    public String getValueColumn() {
        return valueColumn;
    }


    /**
     * Sets the valueColumn value for this CustomerModel.
     * 
     * @param valueColumn
     */
    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }


    /**
     * Gets the ward value for this CustomerModel.
     * 
     * @return ward
     */
    public String getWard() {
        return ward;
    }


    /**
     * Sets the ward value for this CustomerModel.
     * 
     * @param ward
     */
    public void setWard(String ward) {
        this.ward = ward;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CustomerModel)) return false;
        CustomerModel other = (CustomerModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accType==null && other.getAccType()==null) || 
             (this.accType!=null &&
              this.accType.equals(other.getAccType()))) &&
            ((this.accountName==null && other.getAccountName()==null) || 
             (this.accountName!=null &&
              this.accountName.equals(other.getAccountName()))) &&
            this.acctId == other.getAcctId() &&
            ((this.activeDate==null && other.getActiveDate()==null) || 
             (this.activeDate!=null &&
              this.activeDate.equals(other.getActiveDate()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            this.agentId == other.getAgentId() &&
            ((this.bankAccount==null && other.getBankAccount()==null) || 
             (this.bankAccount!=null &&
              this.bankAccount.equals(other.getBankAccount()))) &&
            this.bankCode == other.getBankCode() &&
            this.beginRow == other.getBeginRow() &&
            this.bigCustomer == other.getBigCustomer() &&
            ((this.birthDay==null && other.getBirthDay()==null) || 
             (this.birthDay!=null &&
              this.birthDay.equals(other.getBirthDay()))) &&
            this.candidateId == other.getCandidateId() &&
            this.channelId == other.getChannelId() &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            this.customerType == other.getCustomerType() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.district==null && other.getDistrict()==null) || 
             (this.district!=null &&
              this.district.equals(other.getDistrict()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            this.endRow == other.getEndRow() &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            this.groupId == other.getGroupId() &&
            this.hstId == other.getHstId() &&
            this.id == other.getId() &&
            ((this.identityNumber==null && other.getIdentityNumber()==null) || 
             (this.identityNumber!=null &&
              this.identityNumber.equals(other.getIdentityNumber()))) &&
            this.jobId == other.getJobId() &&
            ((this.lastModifyDate==null && other.getLastModifyDate()==null) || 
             (this.lastModifyDate!=null &&
              this.lastModifyDate.equals(other.getLastModifyDate()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            this.numberPage == other.getNumberPage() &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.postCode==null && other.getPostCode()==null) || 
             (this.postCode!=null &&
              this.postCode.equals(other.getPostCode()))) &&
            this.sex == other.getSex() &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.stringQuery==null && other.getStringQuery()==null) || 
             (this.stringQuery!=null &&
              this.stringQuery.equals(other.getStringQuery()))) &&
            ((this.taxCode==null && other.getTaxCode()==null) || 
             (this.taxCode!=null &&
              this.taxCode.equals(other.getTaxCode()))) &&
            ((this.time==null && other.getTime()==null) || 
             (this.time!=null &&
              this.time.equals(other.getTime()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.valueColumn==null && other.getValueColumn()==null) || 
             (this.valueColumn!=null &&
              this.valueColumn.equals(other.getValueColumn()))) &&
            ((this.ward==null && other.getWard()==null) || 
             (this.ward!=null &&
              this.ward.equals(other.getWard())));
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
        if (getAccType() != null) {
            _hashCode += getAccType().hashCode();
        }
        if (getAccountName() != null) {
            _hashCode += getAccountName().hashCode();
        }
        _hashCode += new Long(getAcctId()).hashCode();
        if (getActiveDate() != null) {
            _hashCode += getActiveDate().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        _hashCode += new Long(getAgentId()).hashCode();
        if (getBankAccount() != null) {
            _hashCode += getBankAccount().hashCode();
        }
        _hashCode += new Long(getBankCode()).hashCode();
        _hashCode += getBeginRow();
        _hashCode += new Long(getBigCustomer()).hashCode();
        if (getBirthDay() != null) {
            _hashCode += getBirthDay().hashCode();
        }
        _hashCode += new Long(getCandidateId()).hashCode();
        _hashCode += new Long(getChannelId()).hashCode();
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        _hashCode += getCustomerType();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getDistrict() != null) {
            _hashCode += getDistrict().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        _hashCode += getEndRow();
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        _hashCode += new Long(getGroupId()).hashCode();
        _hashCode += new Long(getHstId()).hashCode();
        _hashCode += new Long(getId()).hashCode();
        if (getIdentityNumber() != null) {
            _hashCode += getIdentityNumber().hashCode();
        }
        _hashCode += getJobId();
        if (getLastModifyDate() != null) {
            _hashCode += getLastModifyDate().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        _hashCode += getNumberPage();
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getPostCode() != null) {
            _hashCode += getPostCode().hashCode();
        }
        _hashCode += getSex();
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getStringQuery() != null) {
            _hashCode += getStringQuery().hashCode();
        }
        if (getTaxCode() != null) {
            _hashCode += getTaxCode().hashCode();
        }
        if (getTime() != null) {
            _hashCode += getTime().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getValueColumn() != null) {
            _hashCode += getValueColumn().hashCode();
        }
        if (getWard() != null) {
            _hashCode += getWard().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomerModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:customer.model.hyframe.hyperlogy", "CustomerModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountName"));
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
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bankCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginRow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginRow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bigCustomer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bigCustomer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDay");
        elemField.setXmlName(new javax.xml.namespace.QName("", "birthDay"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("candidateId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "candidateId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("channelId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "channelId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("", "city"));
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
        elemField.setFieldName("customerType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("district");
        elemField.setXmlName(new javax.xml.namespace.QName("", "district"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endRow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endRow"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hstId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hstId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identityNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jobId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jobId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastModifyDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastModifyDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberPage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numberPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stringQuery");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stringQuery"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "taxCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time");
        elemField.setXmlName(new javax.xml.namespace.QName("", "time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueColumn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valueColumn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ward");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ward"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
