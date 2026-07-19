/**
 * ReportAccountModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class ReportAccountModel  implements java.io.Serializable {
    private String account;

    private String addressUsing;

    private String addressUsingOld;

    private String attributeInfo;

    private long brasId;

    private String brasIp4;

    private String brasIp6;

    private String brasName;

    private long createdBy;

    private java.util.Calendar createdDate;

    private String createdName;

    private String customerCode;

    private String customerCodeOrganization;

    private String customerName;

    private String description;

    private String dslamCode;

    private long dslamId;

    private String dslamName;

    private String dslamParamInfo;

    private String dslamType;

    private String dslamTypeName;

    private String email;

    private String[] fields;

    private long groupPackageId;

    private long hisId;

    private long id;

    private String ipCidr;

    private int ipNumber;

    private String organizationCode;

    private long organizationId;

    private String organizationName;

    private String packageCode;

    private String packageCodeOld;

    private long packageCustomerId;

    private long packageId;

    private String packageName;

    private String packageNameOld;

    private String paymentType;

    private String phone;

    private String reason;

    private long reportActionId;

    private long serviceInfoId;

    private String serviceProvInfo;

    private String serviceSysCode;

    private long serviceSysId;

    private String serviceSysName;

    private String serviceVas;

    private java.util.Calendar startDate;

    private int status;

    private int statusPre;

    private int type;

    private int useType;

    public ReportAccountModel() {
    }

    public ReportAccountModel(
           String account,
           String addressUsing,
           String addressUsingOld,
           String attributeInfo,
           long brasId,
           String brasIp4,
           String brasIp6,
           String brasName,
           long createdBy,
           java.util.Calendar createdDate,
           String createdName,
           String customerCode,
           String customerCodeOrganization,
           String customerName,
           String description,
           String dslamCode,
           long dslamId,
           String dslamName,
           String dslamParamInfo,
           String dslamType,
           String dslamTypeName,
           String email,
           String[] fields,
           long groupPackageId,
           long hisId,
           long id,
           String ipCidr,
           int ipNumber,
           String organizationCode,
           long organizationId,
           String organizationName,
           String packageCode,
           String packageCodeOld,
           long packageCustomerId,
           long packageId,
           String packageName,
           String packageNameOld,
           String paymentType,
           String phone,
           String reason,
           long reportActionId,
           long serviceInfoId,
           String serviceProvInfo,
           String serviceSysCode,
           long serviceSysId,
           String serviceSysName,
           String serviceVas,
           java.util.Calendar startDate,
           int status,
           int statusPre,
           int type,
           int useType) {
           this.account = account;
           this.addressUsing = addressUsing;
           this.addressUsingOld = addressUsingOld;
           this.attributeInfo = attributeInfo;
           this.brasId = brasId;
           this.brasIp4 = brasIp4;
           this.brasIp6 = brasIp6;
           this.brasName = brasName;
           this.createdBy = createdBy;
           this.createdDate = createdDate;
           this.createdName = createdName;
           this.customerCode = customerCode;
           this.customerCodeOrganization = customerCodeOrganization;
           this.customerName = customerName;
           this.description = description;
           this.dslamCode = dslamCode;
           this.dslamId = dslamId;
           this.dslamName = dslamName;
           this.dslamParamInfo = dslamParamInfo;
           this.dslamType = dslamType;
           this.dslamTypeName = dslamTypeName;
           this.email = email;
           this.fields = fields;
           this.groupPackageId = groupPackageId;
           this.hisId = hisId;
           this.id = id;
           this.ipCidr = ipCidr;
           this.ipNumber = ipNumber;
           this.organizationCode = organizationCode;
           this.organizationId = organizationId;
           this.organizationName = organizationName;
           this.packageCode = packageCode;
           this.packageCodeOld = packageCodeOld;
           this.packageCustomerId = packageCustomerId;
           this.packageId = packageId;
           this.packageName = packageName;
           this.packageNameOld = packageNameOld;
           this.paymentType = paymentType;
           this.phone = phone;
           this.reason = reason;
           this.reportActionId = reportActionId;
           this.serviceInfoId = serviceInfoId;
           this.serviceProvInfo = serviceProvInfo;
           this.serviceSysCode = serviceSysCode;
           this.serviceSysId = serviceSysId;
           this.serviceSysName = serviceSysName;
           this.serviceVas = serviceVas;
           this.startDate = startDate;
           this.status = status;
           this.statusPre = statusPre;
           this.type = type;
           this.useType = useType;
    }


    /**
     * Gets the account value for this ReportAccountModel.
     * 
     * @return account
     */
    public String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this ReportAccountModel.
     * 
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }


    /**
     * Gets the addressUsing value for this ReportAccountModel.
     * 
     * @return addressUsing
     */
    public String getAddressUsing() {
        return addressUsing;
    }


    /**
     * Sets the addressUsing value for this ReportAccountModel.
     * 
     * @param addressUsing
     */
    public void setAddressUsing(String addressUsing) {
        this.addressUsing = addressUsing;
    }


    /**
     * Gets the addressUsingOld value for this ReportAccountModel.
     * 
     * @return addressUsingOld
     */
    public String getAddressUsingOld() {
        return addressUsingOld;
    }


    /**
     * Sets the addressUsingOld value for this ReportAccountModel.
     * 
     * @param addressUsingOld
     */
    public void setAddressUsingOld(String addressUsingOld) {
        this.addressUsingOld = addressUsingOld;
    }


    /**
     * Gets the attributeInfo value for this ReportAccountModel.
     * 
     * @return attributeInfo
     */
    public String getAttributeInfo() {
        return attributeInfo;
    }


    /**
     * Sets the attributeInfo value for this ReportAccountModel.
     * 
     * @param attributeInfo
     */
    public void setAttributeInfo(String attributeInfo) {
        this.attributeInfo = attributeInfo;
    }


    /**
     * Gets the brasId value for this ReportAccountModel.
     * 
     * @return brasId
     */
    public long getBrasId() {
        return brasId;
    }


    /**
     * Sets the brasId value for this ReportAccountModel.
     * 
     * @param brasId
     */
    public void setBrasId(long brasId) {
        this.brasId = brasId;
    }


    /**
     * Gets the brasIp4 value for this ReportAccountModel.
     * 
     * @return brasIp4
     */
    public String getBrasIp4() {
        return brasIp4;
    }


    /**
     * Sets the brasIp4 value for this ReportAccountModel.
     * 
     * @param brasIp4
     */
    public void setBrasIp4(String brasIp4) {
        this.brasIp4 = brasIp4;
    }


    /**
     * Gets the brasIp6 value for this ReportAccountModel.
     * 
     * @return brasIp6
     */
    public String getBrasIp6() {
        return brasIp6;
    }


    /**
     * Sets the brasIp6 value for this ReportAccountModel.
     * 
     * @param brasIp6
     */
    public void setBrasIp6(String brasIp6) {
        this.brasIp6 = brasIp6;
    }


    /**
     * Gets the brasName value for this ReportAccountModel.
     * 
     * @return brasName
     */
    public String getBrasName() {
        return brasName;
    }


    /**
     * Sets the brasName value for this ReportAccountModel.
     * 
     * @param brasName
     */
    public void setBrasName(String brasName) {
        this.brasName = brasName;
    }


    /**
     * Gets the createdBy value for this ReportAccountModel.
     * 
     * @return createdBy
     */
    public long getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the createdBy value for this ReportAccountModel.
     * 
     * @param createdBy
     */
    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }


    /**
     * Gets the createdDate value for this ReportAccountModel.
     * 
     * @return createdDate
     */
    public java.util.Calendar getCreatedDate() {
        return createdDate;
    }


    /**
     * Sets the createdDate value for this ReportAccountModel.
     * 
     * @param createdDate
     */
    public void setCreatedDate(java.util.Calendar createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets the createdName value for this ReportAccountModel.
     * 
     * @return createdName
     */
    public String getCreatedName() {
        return createdName;
    }


    /**
     * Sets the createdName value for this ReportAccountModel.
     * 
     * @param createdName
     */
    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }


    /**
     * Gets the customerCode value for this ReportAccountModel.
     * 
     * @return customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }


    /**
     * Sets the customerCode value for this ReportAccountModel.
     * 
     * @param customerCode
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }


    /**
     * Gets the customerCodeOrganization value for this ReportAccountModel.
     * 
     * @return customerCodeOrganization
     */
    public String getCustomerCodeOrganization() {
        return customerCodeOrganization;
    }


    /**
     * Sets the customerCodeOrganization value for this ReportAccountModel.
     * 
     * @param customerCodeOrganization
     */
    public void setCustomerCodeOrganization(String customerCodeOrganization) {
        this.customerCodeOrganization = customerCodeOrganization;
    }


    /**
     * Gets the customerName value for this ReportAccountModel.
     * 
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }


    /**
     * Sets the customerName value for this ReportAccountModel.
     * 
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    /**
     * Gets the description value for this ReportAccountModel.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this ReportAccountModel.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Gets the dslamCode value for this ReportAccountModel.
     * 
     * @return dslamCode
     */
    public String getDslamCode() {
        return dslamCode;
    }


    /**
     * Sets the dslamCode value for this ReportAccountModel.
     * 
     * @param dslamCode
     */
    public void setDslamCode(String dslamCode) {
        this.dslamCode = dslamCode;
    }


    /**
     * Gets the dslamId value for this ReportAccountModel.
     * 
     * @return dslamId
     */
    public long getDslamId() {
        return dslamId;
    }


    /**
     * Sets the dslamId value for this ReportAccountModel.
     * 
     * @param dslamId
     */
    public void setDslamId(long dslamId) {
        this.dslamId = dslamId;
    }


    /**
     * Gets the dslamName value for this ReportAccountModel.
     * 
     * @return dslamName
     */
    public String getDslamName() {
        return dslamName;
    }


    /**
     * Sets the dslamName value for this ReportAccountModel.
     * 
     * @param dslamName
     */
    public void setDslamName(String dslamName) {
        this.dslamName = dslamName;
    }


    /**
     * Gets the dslamParamInfo value for this ReportAccountModel.
     * 
     * @return dslamParamInfo
     */
    public String getDslamParamInfo() {
        return dslamParamInfo;
    }


    /**
     * Sets the dslamParamInfo value for this ReportAccountModel.
     * 
     * @param dslamParamInfo
     */
    public void setDslamParamInfo(String dslamParamInfo) {
        this.dslamParamInfo = dslamParamInfo;
    }


    /**
     * Gets the dslamType value for this ReportAccountModel.
     * 
     * @return dslamType
     */
    public String getDslamType() {
        return dslamType;
    }


    /**
     * Sets the dslamType value for this ReportAccountModel.
     * 
     * @param dslamType
     */
    public void setDslamType(String dslamType) {
        this.dslamType = dslamType;
    }


    /**
     * Gets the dslamTypeName value for this ReportAccountModel.
     * 
     * @return dslamTypeName
     */
    public String getDslamTypeName() {
        return dslamTypeName;
    }


    /**
     * Sets the dslamTypeName value for this ReportAccountModel.
     * 
     * @param dslamTypeName
     */
    public void setDslamTypeName(String dslamTypeName) {
        this.dslamTypeName = dslamTypeName;
    }


    /**
     * Gets the email value for this ReportAccountModel.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this ReportAccountModel.
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the fields value for this ReportAccountModel.
     * 
     * @return fields
     */
    public String[] getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this ReportAccountModel.
     * 
     * @param fields
     */
    public void setFields(String[] fields) {
        this.fields = fields;
    }


    /**
     * Gets the groupPackageId value for this ReportAccountModel.
     * 
     * @return groupPackageId
     */
    public long getGroupPackageId() {
        return groupPackageId;
    }


    /**
     * Sets the groupPackageId value for this ReportAccountModel.
     * 
     * @param groupPackageId
     */
    public void setGroupPackageId(long groupPackageId) {
        this.groupPackageId = groupPackageId;
    }


    /**
     * Gets the hisId value for this ReportAccountModel.
     * 
     * @return hisId
     */
    public long getHisId() {
        return hisId;
    }


    /**
     * Sets the hisId value for this ReportAccountModel.
     * 
     * @param hisId
     */
    public void setHisId(long hisId) {
        this.hisId = hisId;
    }


    /**
     * Gets the id value for this ReportAccountModel.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this ReportAccountModel.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the ipCidr value for this ReportAccountModel.
     * 
     * @return ipCidr
     */
    public String getIpCidr() {
        return ipCidr;
    }


    /**
     * Sets the ipCidr value for this ReportAccountModel.
     * 
     * @param ipCidr
     */
    public void setIpCidr(String ipCidr) {
        this.ipCidr = ipCidr;
    }


    /**
     * Gets the ipNumber value for this ReportAccountModel.
     * 
     * @return ipNumber
     */
    public int getIpNumber() {
        return ipNumber;
    }


    /**
     * Sets the ipNumber value for this ReportAccountModel.
     * 
     * @param ipNumber
     */
    public void setIpNumber(int ipNumber) {
        this.ipNumber = ipNumber;
    }


    /**
     * Gets the organizationCode value for this ReportAccountModel.
     * 
     * @return organizationCode
     */
    public String getOrganizationCode() {
        return organizationCode;
    }


    /**
     * Sets the organizationCode value for this ReportAccountModel.
     * 
     * @param organizationCode
     */
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }


    /**
     * Gets the organizationId value for this ReportAccountModel.
     * 
     * @return organizationId
     */
    public long getOrganizationId() {
        return organizationId;
    }


    /**
     * Sets the organizationId value for this ReportAccountModel.
     * 
     * @param organizationId
     */
    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }


    /**
     * Gets the organizationName value for this ReportAccountModel.
     * 
     * @return organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }


    /**
     * Sets the organizationName value for this ReportAccountModel.
     * 
     * @param organizationName
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }


    /**
     * Gets the packageCode value for this ReportAccountModel.
     * 
     * @return packageCode
     */
    public String getPackageCode() {
        return packageCode;
    }


    /**
     * Sets the packageCode value for this ReportAccountModel.
     * 
     * @param packageCode
     */
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }


    /**
     * Gets the packageCodeOld value for this ReportAccountModel.
     * 
     * @return packageCodeOld
     */
    public String getPackageCodeOld() {
        return packageCodeOld;
    }


    /**
     * Sets the packageCodeOld value for this ReportAccountModel.
     * 
     * @param packageCodeOld
     */
    public void setPackageCodeOld(String packageCodeOld) {
        this.packageCodeOld = packageCodeOld;
    }


    /**
     * Gets the packageCustomerId value for this ReportAccountModel.
     * 
     * @return packageCustomerId
     */
    public long getPackageCustomerId() {
        return packageCustomerId;
    }


    /**
     * Sets the packageCustomerId value for this ReportAccountModel.
     * 
     * @param packageCustomerId
     */
    public void setPackageCustomerId(long packageCustomerId) {
        this.packageCustomerId = packageCustomerId;
    }


    /**
     * Gets the packageId value for this ReportAccountModel.
     * 
     * @return packageId
     */
    public long getPackageId() {
        return packageId;
    }


    /**
     * Sets the packageId value for this ReportAccountModel.
     * 
     * @param packageId
     */
    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }


    /**
     * Gets the packageName value for this ReportAccountModel.
     * 
     * @return packageName
     */
    public String getPackageName() {
        return packageName;
    }


    /**
     * Sets the packageName value for this ReportAccountModel.
     * 
     * @param packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    /**
     * Gets the packageNameOld value for this ReportAccountModel.
     * 
     * @return packageNameOld
     */
    public String getPackageNameOld() {
        return packageNameOld;
    }


    /**
     * Sets the packageNameOld value for this ReportAccountModel.
     * 
     * @param packageNameOld
     */
    public void setPackageNameOld(String packageNameOld) {
        this.packageNameOld = packageNameOld;
    }


    /**
     * Gets the paymentType value for this ReportAccountModel.
     * 
     * @return paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }


    /**
     * Sets the paymentType value for this ReportAccountModel.
     * 
     * @param paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    /**
     * Gets the phone value for this ReportAccountModel.
     * 
     * @return phone
     */
    public String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this ReportAccountModel.
     * 
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     * Gets the reason value for this ReportAccountModel.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this ReportAccountModel.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


    /**
     * Gets the reportActionId value for this ReportAccountModel.
     * 
     * @return reportActionId
     */
    public long getReportActionId() {
        return reportActionId;
    }


    /**
     * Sets the reportActionId value for this ReportAccountModel.
     * 
     * @param reportActionId
     */
    public void setReportActionId(long reportActionId) {
        this.reportActionId = reportActionId;
    }


    /**
     * Gets the serviceInfoId value for this ReportAccountModel.
     * 
     * @return serviceInfoId
     */
    public long getServiceInfoId() {
        return serviceInfoId;
    }


    /**
     * Sets the serviceInfoId value for this ReportAccountModel.
     * 
     * @param serviceInfoId
     */
    public void setServiceInfoId(long serviceInfoId) {
        this.serviceInfoId = serviceInfoId;
    }


    /**
     * Gets the serviceProvInfo value for this ReportAccountModel.
     * 
     * @return serviceProvInfo
     */
    public String getServiceProvInfo() {
        return serviceProvInfo;
    }


    /**
     * Sets the serviceProvInfo value for this ReportAccountModel.
     * 
     * @param serviceProvInfo
     */
    public void setServiceProvInfo(String serviceProvInfo) {
        this.serviceProvInfo = serviceProvInfo;
    }


    /**
     * Gets the serviceSysCode value for this ReportAccountModel.
     * 
     * @return serviceSysCode
     */
    public String getServiceSysCode() {
        return serviceSysCode;
    }


    /**
     * Sets the serviceSysCode value for this ReportAccountModel.
     * 
     * @param serviceSysCode
     */
    public void setServiceSysCode(String serviceSysCode) {
        this.serviceSysCode = serviceSysCode;
    }


    /**
     * Gets the serviceSysId value for this ReportAccountModel.
     * 
     * @return serviceSysId
     */
    public long getServiceSysId() {
        return serviceSysId;
    }


    /**
     * Sets the serviceSysId value for this ReportAccountModel.
     * 
     * @param serviceSysId
     */
    public void setServiceSysId(long serviceSysId) {
        this.serviceSysId = serviceSysId;
    }


    /**
     * Gets the serviceSysName value for this ReportAccountModel.
     * 
     * @return serviceSysName
     */
    public String getServiceSysName() {
        return serviceSysName;
    }


    /**
     * Sets the serviceSysName value for this ReportAccountModel.
     * 
     * @param serviceSysName
     */
    public void setServiceSysName(String serviceSysName) {
        this.serviceSysName = serviceSysName;
    }


    /**
     * Gets the serviceVas value for this ReportAccountModel.
     * 
     * @return serviceVas
     */
    public String getServiceVas() {
        return serviceVas;
    }


    /**
     * Sets the serviceVas value for this ReportAccountModel.
     * 
     * @param serviceVas
     */
    public void setServiceVas(String serviceVas) {
        this.serviceVas = serviceVas;
    }


    /**
     * Gets the startDate value for this ReportAccountModel.
     * 
     * @return startDate
     */
    public java.util.Calendar getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this ReportAccountModel.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Calendar startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the status value for this ReportAccountModel.
     * 
     * @return status
     */
    public int getStatus() {
        return status;
    }


    /**
     * Sets the status value for this ReportAccountModel.
     * 
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * Gets the statusPre value for this ReportAccountModel.
     * 
     * @return statusPre
     */
    public int getStatusPre() {
        return statusPre;
    }


    /**
     * Sets the statusPre value for this ReportAccountModel.
     * 
     * @param statusPre
     */
    public void setStatusPre(int statusPre) {
        this.statusPre = statusPre;
    }


    /**
     * Gets the type value for this ReportAccountModel.
     * 
     * @return type
     */
    public int getType() {
        return type;
    }


    /**
     * Sets the type value for this ReportAccountModel.
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets the useType value for this ReportAccountModel.
     * 
     * @return useType
     */
    public int getUseType() {
        return useType;
    }


    /**
     * Sets the useType value for this ReportAccountModel.
     * 
     * @param useType
     */
    public void setUseType(int useType) {
        this.useType = useType;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ReportAccountModel)) return false;
        ReportAccountModel other = (ReportAccountModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.account==null && other.getAccount()==null) || 
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            ((this.addressUsing==null && other.getAddressUsing()==null) || 
             (this.addressUsing!=null &&
              this.addressUsing.equals(other.getAddressUsing()))) &&
            ((this.addressUsingOld==null && other.getAddressUsingOld()==null) || 
             (this.addressUsingOld!=null &&
              this.addressUsingOld.equals(other.getAddressUsingOld()))) &&
            ((this.attributeInfo==null && other.getAttributeInfo()==null) || 
             (this.attributeInfo!=null &&
              this.attributeInfo.equals(other.getAttributeInfo()))) &&
            this.brasId == other.getBrasId() &&
            ((this.brasIp4==null && other.getBrasIp4()==null) || 
             (this.brasIp4!=null &&
              this.brasIp4.equals(other.getBrasIp4()))) &&
            ((this.brasIp6==null && other.getBrasIp6()==null) || 
             (this.brasIp6!=null &&
              this.brasIp6.equals(other.getBrasIp6()))) &&
            ((this.brasName==null && other.getBrasName()==null) || 
             (this.brasName!=null &&
              this.brasName.equals(other.getBrasName()))) &&
            this.createdBy == other.getCreatedBy() &&
            ((this.createdDate==null && other.getCreatedDate()==null) || 
             (this.createdDate!=null &&
              this.createdDate.equals(other.getCreatedDate()))) &&
            ((this.createdName==null && other.getCreatedName()==null) || 
             (this.createdName!=null &&
              this.createdName.equals(other.getCreatedName()))) &&
            ((this.customerCode==null && other.getCustomerCode()==null) || 
             (this.customerCode!=null &&
              this.customerCode.equals(other.getCustomerCode()))) &&
            ((this.customerCodeOrganization==null && other.getCustomerCodeOrganization()==null) || 
             (this.customerCodeOrganization!=null &&
              this.customerCodeOrganization.equals(other.getCustomerCodeOrganization()))) &&
            ((this.customerName==null && other.getCustomerName()==null) || 
             (this.customerName!=null &&
              this.customerName.equals(other.getCustomerName()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.dslamCode==null && other.getDslamCode()==null) || 
             (this.dslamCode!=null &&
              this.dslamCode.equals(other.getDslamCode()))) &&
            this.dslamId == other.getDslamId() &&
            ((this.dslamName==null && other.getDslamName()==null) || 
             (this.dslamName!=null &&
              this.dslamName.equals(other.getDslamName()))) &&
            ((this.dslamParamInfo==null && other.getDslamParamInfo()==null) || 
             (this.dslamParamInfo!=null &&
              this.dslamParamInfo.equals(other.getDslamParamInfo()))) &&
            ((this.dslamType==null && other.getDslamType()==null) || 
             (this.dslamType!=null &&
              this.dslamType.equals(other.getDslamType()))) &&
            ((this.dslamTypeName==null && other.getDslamTypeName()==null) || 
             (this.dslamTypeName!=null &&
              this.dslamTypeName.equals(other.getDslamTypeName()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.fields==null && other.getFields()==null) || 
             (this.fields!=null &&
              java.util.Arrays.equals(this.fields, other.getFields()))) &&
            this.groupPackageId == other.getGroupPackageId() &&
            this.hisId == other.getHisId() &&
            this.id == other.getId() &&
            ((this.ipCidr==null && other.getIpCidr()==null) || 
             (this.ipCidr!=null &&
              this.ipCidr.equals(other.getIpCidr()))) &&
            this.ipNumber == other.getIpNumber() &&
            ((this.organizationCode==null && other.getOrganizationCode()==null) || 
             (this.organizationCode!=null &&
              this.organizationCode.equals(other.getOrganizationCode()))) &&
            this.organizationId == other.getOrganizationId() &&
            ((this.organizationName==null && other.getOrganizationName()==null) || 
             (this.organizationName!=null &&
              this.organizationName.equals(other.getOrganizationName()))) &&
            ((this.packageCode==null && other.getPackageCode()==null) || 
             (this.packageCode!=null &&
              this.packageCode.equals(other.getPackageCode()))) &&
            ((this.packageCodeOld==null && other.getPackageCodeOld()==null) || 
             (this.packageCodeOld!=null &&
              this.packageCodeOld.equals(other.getPackageCodeOld()))) &&
            this.packageCustomerId == other.getPackageCustomerId() &&
            this.packageId == other.getPackageId() &&
            ((this.packageName==null && other.getPackageName()==null) || 
             (this.packageName!=null &&
              this.packageName.equals(other.getPackageName()))) &&
            ((this.packageNameOld==null && other.getPackageNameOld()==null) || 
             (this.packageNameOld!=null &&
              this.packageNameOld.equals(other.getPackageNameOld()))) &&
            ((this.paymentType==null && other.getPaymentType()==null) || 
             (this.paymentType!=null &&
              this.paymentType.equals(other.getPaymentType()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            this.reportActionId == other.getReportActionId() &&
            this.serviceInfoId == other.getServiceInfoId() &&
            ((this.serviceProvInfo==null && other.getServiceProvInfo()==null) || 
             (this.serviceProvInfo!=null &&
              this.serviceProvInfo.equals(other.getServiceProvInfo()))) &&
            ((this.serviceSysCode==null && other.getServiceSysCode()==null) || 
             (this.serviceSysCode!=null &&
              this.serviceSysCode.equals(other.getServiceSysCode()))) &&
            this.serviceSysId == other.getServiceSysId() &&
            ((this.serviceSysName==null && other.getServiceSysName()==null) || 
             (this.serviceSysName!=null &&
              this.serviceSysName.equals(other.getServiceSysName()))) &&
            ((this.serviceVas==null && other.getServiceVas()==null) || 
             (this.serviceVas!=null &&
              this.serviceVas.equals(other.getServiceVas()))) &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            this.status == other.getStatus() &&
            this.statusPre == other.getStatusPre() &&
            this.type == other.getType() &&
            this.useType == other.getUseType();
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
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        if (getAddressUsing() != null) {
            _hashCode += getAddressUsing().hashCode();
        }
        if (getAddressUsingOld() != null) {
            _hashCode += getAddressUsingOld().hashCode();
        }
        if (getAttributeInfo() != null) {
            _hashCode += getAttributeInfo().hashCode();
        }
        _hashCode += new Long(getBrasId()).hashCode();
        if (getBrasIp4() != null) {
            _hashCode += getBrasIp4().hashCode();
        }
        if (getBrasIp6() != null) {
            _hashCode += getBrasIp6().hashCode();
        }
        if (getBrasName() != null) {
            _hashCode += getBrasName().hashCode();
        }
        _hashCode += new Long(getCreatedBy()).hashCode();
        if (getCreatedDate() != null) {
            _hashCode += getCreatedDate().hashCode();
        }
        if (getCreatedName() != null) {
            _hashCode += getCreatedName().hashCode();
        }
        if (getCustomerCode() != null) {
            _hashCode += getCustomerCode().hashCode();
        }
        if (getCustomerCodeOrganization() != null) {
            _hashCode += getCustomerCodeOrganization().hashCode();
        }
        if (getCustomerName() != null) {
            _hashCode += getCustomerName().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getDslamCode() != null) {
            _hashCode += getDslamCode().hashCode();
        }
        _hashCode += new Long(getDslamId()).hashCode();
        if (getDslamName() != null) {
            _hashCode += getDslamName().hashCode();
        }
        if (getDslamParamInfo() != null) {
            _hashCode += getDslamParamInfo().hashCode();
        }
        if (getDslamType() != null) {
            _hashCode += getDslamType().hashCode();
        }
        if (getDslamTypeName() != null) {
            _hashCode += getDslamTypeName().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFields());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getGroupPackageId()).hashCode();
        _hashCode += new Long(getHisId()).hashCode();
        _hashCode += new Long(getId()).hashCode();
        if (getIpCidr() != null) {
            _hashCode += getIpCidr().hashCode();
        }
        _hashCode += getIpNumber();
        if (getOrganizationCode() != null) {
            _hashCode += getOrganizationCode().hashCode();
        }
        _hashCode += new Long(getOrganizationId()).hashCode();
        if (getOrganizationName() != null) {
            _hashCode += getOrganizationName().hashCode();
        }
        if (getPackageCode() != null) {
            _hashCode += getPackageCode().hashCode();
        }
        if (getPackageCodeOld() != null) {
            _hashCode += getPackageCodeOld().hashCode();
        }
        _hashCode += new Long(getPackageCustomerId()).hashCode();
        _hashCode += new Long(getPackageId()).hashCode();
        if (getPackageName() != null) {
            _hashCode += getPackageName().hashCode();
        }
        if (getPackageNameOld() != null) {
            _hashCode += getPackageNameOld().hashCode();
        }
        if (getPaymentType() != null) {
            _hashCode += getPaymentType().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        _hashCode += new Long(getReportActionId()).hashCode();
        _hashCode += new Long(getServiceInfoId()).hashCode();
        if (getServiceProvInfo() != null) {
            _hashCode += getServiceProvInfo().hashCode();
        }
        if (getServiceSysCode() != null) {
            _hashCode += getServiceSysCode().hashCode();
        }
        _hashCode += new Long(getServiceSysId()).hashCode();
        if (getServiceSysName() != null) {
            _hashCode += getServiceSysName().hashCode();
        }
        if (getServiceVas() != null) {
            _hashCode += getServiceVas().hashCode();
        }
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        _hashCode += getStatus();
        _hashCode += getStatusPre();
        _hashCode += getType();
        _hashCode += getUseType();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReportAccountModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://report.model.hyframe.hyperlogy", "ReportAccountModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("", "account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressUsing");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addressUsing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addressUsingOld");
        elemField.setXmlName(new javax.xml.namespace.QName("", "addressUsingOld"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attributeInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brasId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brasId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brasIp4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brasIp4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brasIp6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brasIp6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brasName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "brasName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdBy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createdBy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createdDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createdName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createdName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerCodeOrganization");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerCodeOrganization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dslamCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dslamCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dslamId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dslamId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dslamName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dslamName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dslamParamInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dslamParamInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dslamType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dslamType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dslamTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dslamTypeName"));
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
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupPackageId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupPackageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hisId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hisId"));
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
        elemField.setFieldName("ipCidr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipCidr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organizationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organizationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organizationId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organizationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organizationName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organizationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageCodeOld");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageCodeOld"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageCustomerId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageCustomerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageNameOld");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packageNameOld"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paymentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phone");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phone"));
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
        elemField.setFieldName("reportActionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reportActionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceInfoId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceInfoId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceProvInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceProvInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceSysCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceSysCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceSysId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceSysId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceSysName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceSysName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceVas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceVas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusPre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusPre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("useType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "useType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
