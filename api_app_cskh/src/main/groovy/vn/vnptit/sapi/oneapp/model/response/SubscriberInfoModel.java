package vn.vnptit.sapi.oneapp.model.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Subscriber info model
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class SubscriberInfoModel {

    @JsonProperty("b2BFlg")
    private String b2BFlg;

    @JsonProperty("fromSys")
    private String fromSys;

    @JsonProperty("stbinfor")
    private String stbinfor;

    @JsonProperty("kplusInfo")
    private String kplusInfo;

    @JsonProperty("last_Login")
    private String last_Login;

    @JsonProperty("mac")
    private String mac;

    @JsonProperty("sexID")
    private String sexID;

    @JsonProperty("packageID")
    private String packageID;

    @JsonProperty("serviceName")
    private String serviceName;

    @JsonProperty("serviceID")
    private String serviceID;

    @JsonProperty("serviceVASID")
    private String serviceVASID;

    @JsonProperty("statusID")
    private String statusID;

    @JsonProperty("cateName")
    private String cateName;

    @JsonProperty("cateID")
    private String cateID;

    @JsonProperty("errordesc")
    private String errordesc;

    @JsonProperty("dateBill")
    private String dateBill;

    @JsonProperty("b2BFlgSpecified")
    private boolean b2BFlgSpecified;

    @JsonProperty("fromSysSpecified")
    private boolean fromSysSpecified;

    @JsonProperty("stbinforSpecified")
    private boolean stbinforSpecified;

    @JsonProperty("kplusInfoSpecified")
    private boolean kplusInfoSpecified;

    @JsonProperty("last_LoginSpecified")
    private boolean last_LoginSpecified;

    @JsonProperty("macspecified")
    private boolean macspecified;

    @JsonProperty("zipCodeSpecified")
    private boolean zipCodeSpecified;

    @JsonProperty("sexIDSpecified")
    private boolean sexIDSpecified;

    @JsonProperty("areaNameSpecified")
    private boolean areaNameSpecified;

    @JsonProperty("packageNameSpecified")
    private boolean packageNameSpecified;

    @JsonProperty("packageIDSpecified")
    private boolean packageIDSpecified;

    @JsonProperty("serviceNameSpecified")
    private boolean serviceNameSpecified;

    @JsonProperty("serviceIDSpecified")
    private boolean serviceIDSpecified;

    @JsonProperty("serviceVASIDSpecified")
    private boolean serviceVASIDSpecified;

    @JsonProperty("statusIDSpecified")
    private boolean statusIDSpecified;

    @JsonProperty("cateNameSpecified")
    private boolean cateNameSpecified;

    @JsonProperty("cateIDSpecified")
    private boolean cateIDSpecified;

    @JsonProperty("responseTime")
    private String responseTime;

    @JsonProperty("errordescSpecified")
    private boolean errordescSpecified;

    @JsonProperty("dateBillSpecified")
    private boolean dateBillSpecified;

    @JsonProperty("result")
    private int result;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("packageName")
    private String packageName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("email")
    private String email;

    @JsonProperty("iptvaccountSpecified")
    private boolean iptvaccountSpecified;

    @JsonProperty("stbid")
    private String stbid;

    @JsonProperty("statusSpecified")
    private boolean statusSpecified;

    @JsonProperty("fixDateSpecified")
    private boolean fixDateSpecified;

    @JsonProperty("fixDate")
    private String fixDate;

    @JsonProperty("notFixReasonSpecified")
    private boolean notFixReasonSpecified;

    @JsonProperty("notFixReason")
    private String notFixReason;

    @JsonProperty("contractSignDateSpecified")
    private boolean contractSignDateSpecified;

    @JsonProperty("contractSignDate")
    private String contractSignDate;

    @JsonProperty("loginAccountSpecified")
    private boolean loginAccountSpecified;

    @JsonProperty("loginAccount")
    private String loginAccount;

    @JsonProperty("billAccountSpecified")
    private boolean billAccountSpecified;

    @JsonProperty("billAccount")
    private String billAccount;

    @JsonProperty("nameSpecified")
    private boolean nameSpecified;

    @JsonProperty("birthdaySpecified")
    private boolean birthdaySpecified;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("sexSpecified")
    private boolean sexSpecified;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("firstAddSpecified")
    private boolean firstAddSpecified;

    @JsonProperty("firstAdd")
    private String firstAdd;

    @JsonProperty("secondAddSpecified")
    private boolean secondAddSpecified;

    @JsonProperty("secondAdd")
    private String secondAdd;

    @JsonProperty("idnoSpecified")
    private boolean idnoSpecified;

    @JsonProperty("idno")
    private String idno;

    @JsonProperty("telNoSpecified")
    private boolean telNoSpecified;

    @JsonProperty("telNo")
    private String telNo;

    @JsonProperty("cellNoSpecified")
    private boolean cellNoSpecified;

    @JsonProperty("cellNo")
    private String cellNo;

    @JsonProperty("emailSpecified")
    private boolean emailSpecified;

    @JsonProperty("ymspecified")
    private boolean ymspecified;

    @JsonProperty("ym")
    private String ym;

    @JsonProperty("websiteSpecified")
    private boolean websiteSpecified;

    @JsonProperty("website")
    private String website;

    @JsonProperty("faxNoSpecified")
    private boolean faxNoSpecified;

    @JsonProperty("faxNo")
    private String faxNo;

    @JsonProperty("contractCodeSpecified")
    private boolean contractCodeSpecified;

    @JsonProperty("contractCode")
    private String contractCode;

    @JsonProperty("iptvpwspecified")
    private boolean iptvpwspecified;

    @JsonProperty("iptvpw")
    private String iptvpw;

    @JsonProperty("serviceVASCodeSpecified")
    private boolean serviceVASCodeSpecified;

    @JsonProperty("serviceVASCode")
    private String serviceVASCode;

    @JsonProperty("stbidspecified")
    private boolean stbidspecified;

    @JsonProperty("iptvaccount")
    private String iptvaccount;

    @JsonProperty("portDSL")
    private String portDSL;

    @JsonProperty("portDSLSpecified")
    private boolean portDSLSpecified;

    @JsonProperty("areaName")
    private String areaName;

    @JsonProperty("miservice")
    private String miservice;

    @JsonProperty("feePrepaidSpecified")
    private boolean feePrepaidSpecified;

    @JsonProperty("feePrepaid")
    private String feePrepaid;

    @JsonProperty("deviceNameSpecified")
    private boolean deviceNameSpecified;

    @JsonProperty("deviceName")
    private String deviceName;

    @JsonProperty("miserviceSpecified")
    private boolean miserviceSpecified;

    @JsonProperty("ipspecified")
    private boolean ipspecified;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("name")
    private String name;

    public String getB2BFlg() {
        return b2BFlg;
    }

    public void setB2BFlg(String b2BFlg) {
        this.b2BFlg = b2BFlg;
    }

    public String getFromSys() {
        return fromSys;
    }

    public void setFromSys(String fromSys) {
        this.fromSys = fromSys;
    }

    public String getStbinfor() {
        return stbinfor;
    }

    public void setStbinfor(String stbinfor) {
        this.stbinfor = stbinfor;
    }

    public String getKplusInfo() {
        return kplusInfo;
    }

    public void setKplusInfo(String kplusInfo) {
        this.kplusInfo = kplusInfo;
    }

    public String getLast_Login() {
        return last_Login;
    }

    public void setLast_Login(String last_Login) {
        this.last_Login = last_Login;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSexID() {
        return sexID;
    }

    public void setSexID(String sexID) {
        this.sexID = sexID;
    }

    public String getPackageID() {
        return packageID;
    }

    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceVASID() {
        return serviceVASID;
    }

    public void setServiceVASID(String serviceVASID) {
        this.serviceVASID = serviceVASID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateID() {
        return cateID;
    }

    public void setCateID(String cateID) {
        this.cateID = cateID;
    }

    public String getErrordesc() {
        return errordesc;
    }

    public void setErrordesc(String errordesc) {
        this.errordesc = errordesc;
    }

    public String getDateBill() {
        return dateBill;
    }

    public void setDateBill(String dateBill) {
        this.dateBill = dateBill;
    }

    public boolean isB2BFlgSpecified() {
        return b2BFlgSpecified;
    }

    public void setB2BFlgSpecified(boolean b2BFlgSpecified) {
        this.b2BFlgSpecified = b2BFlgSpecified;
    }

    public boolean isFromSysSpecified() {
        return fromSysSpecified;
    }

    public void setFromSysSpecified(boolean fromSysSpecified) {
        this.fromSysSpecified = fromSysSpecified;
    }

    public boolean isStbinforSpecified() {
        return stbinforSpecified;
    }

    public void setStbinforSpecified(boolean stbinforSpecified) {
        this.stbinforSpecified = stbinforSpecified;
    }

    public boolean isKplusInfoSpecified() {
        return kplusInfoSpecified;
    }

    public void setKplusInfoSpecified(boolean kplusInfoSpecified) {
        this.kplusInfoSpecified = kplusInfoSpecified;
    }

    public boolean isLast_LoginSpecified() {
        return last_LoginSpecified;
    }

    public void setLast_LoginSpecified(boolean last_LoginSpecified) {
        this.last_LoginSpecified = last_LoginSpecified;
    }

    public boolean isMacspecified() {
        return macspecified;
    }

    public void setMacspecified(boolean macspecified) {
        this.macspecified = macspecified;
    }

    public boolean isZipCodeSpecified() {
        return zipCodeSpecified;
    }

    public void setZipCodeSpecified(boolean zipCodeSpecified) {
        this.zipCodeSpecified = zipCodeSpecified;
    }

    public boolean isSexIDSpecified() {
        return sexIDSpecified;
    }

    public void setSexIDSpecified(boolean sexIDSpecified) {
        this.sexIDSpecified = sexIDSpecified;
    }

    public boolean isAreaNameSpecified() {
        return areaNameSpecified;
    }

    public void setAreaNameSpecified(boolean areaNameSpecified) {
        this.areaNameSpecified = areaNameSpecified;
    }

    public boolean isPackageNameSpecified() {
        return packageNameSpecified;
    }

    public void setPackageNameSpecified(boolean packageNameSpecified) {
        this.packageNameSpecified = packageNameSpecified;
    }

    public boolean isPackageIDSpecified() {
        return packageIDSpecified;
    }

    public void setPackageIDSpecified(boolean packageIDSpecified) {
        this.packageIDSpecified = packageIDSpecified;
    }

    public boolean isServiceNameSpecified() {
        return serviceNameSpecified;
    }

    public void setServiceNameSpecified(boolean serviceNameSpecified) {
        this.serviceNameSpecified = serviceNameSpecified;
    }

    public boolean isServiceIDSpecified() {
        return serviceIDSpecified;
    }

    public void setServiceIDSpecified(boolean serviceIDSpecified) {
        this.serviceIDSpecified = serviceIDSpecified;
    }

    public boolean isServiceVASIDSpecified() {
        return serviceVASIDSpecified;
    }

    public void setServiceVASIDSpecified(boolean serviceVASIDSpecified) {
        this.serviceVASIDSpecified = serviceVASIDSpecified;
    }

    public boolean isStatusIDSpecified() {
        return statusIDSpecified;
    }

    public void setStatusIDSpecified(boolean statusIDSpecified) {
        this.statusIDSpecified = statusIDSpecified;
    }

    public boolean isCateNameSpecified() {
        return cateNameSpecified;
    }

    public void setCateNameSpecified(boolean cateNameSpecified) {
        this.cateNameSpecified = cateNameSpecified;
    }

    public boolean isCateIDSpecified() {
        return cateIDSpecified;
    }

    public void setCateIDSpecified(boolean cateIDSpecified) {
        this.cateIDSpecified = cateIDSpecified;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public boolean isErrordescSpecified() {
        return errordescSpecified;
    }

    public void setErrordescSpecified(boolean errordescSpecified) {
        this.errordescSpecified = errordescSpecified;
    }

    public boolean isDateBillSpecified() {
        return dateBillSpecified;
    }

    public void setDateBillSpecified(boolean dateBillSpecified) {
        this.dateBillSpecified = dateBillSpecified;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIptvaccountSpecified() {
        return iptvaccountSpecified;
    }

    public void setIptvaccountSpecified(boolean iptvaccountSpecified) {
        this.iptvaccountSpecified = iptvaccountSpecified;
    }

    public String getStbid() {
        return stbid;
    }

    public void setStbid(String stbid) {
        this.stbid = stbid;
    }

    public boolean isStatusSpecified() {
        return statusSpecified;
    }

    public void setStatusSpecified(boolean statusSpecified) {
        this.statusSpecified = statusSpecified;
    }

    public boolean isFixDateSpecified() {
        return fixDateSpecified;
    }

    public void setFixDateSpecified(boolean fixDateSpecified) {
        this.fixDateSpecified = fixDateSpecified;
    }

    public String getFixDate() {
        return fixDate;
    }

    public void setFixDate(String fixDate) {
        this.fixDate = fixDate;
    }

    public boolean isNotFixReasonSpecified() {
        return notFixReasonSpecified;
    }

    public void setNotFixReasonSpecified(boolean notFixReasonSpecified) {
        this.notFixReasonSpecified = notFixReasonSpecified;
    }

    public String getNotFixReason() {
        return notFixReason;
    }

    public void setNotFixReason(String notFixReason) {
        this.notFixReason = notFixReason;
    }

    public boolean isContractSignDateSpecified() {
        return contractSignDateSpecified;
    }

    public void setContractSignDateSpecified(boolean contractSignDateSpecified) {
        this.contractSignDateSpecified = contractSignDateSpecified;
    }

    public String getContractSignDate() {
        return contractSignDate;
    }

    public void setContractSignDate(String contractSignDate) {
        this.contractSignDate = contractSignDate;
    }

    public boolean isLoginAccountSpecified() {
        return loginAccountSpecified;
    }

    public void setLoginAccountSpecified(boolean loginAccountSpecified) {
        this.loginAccountSpecified = loginAccountSpecified;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public boolean isBillAccountSpecified() {
        return billAccountSpecified;
    }

    public void setBillAccountSpecified(boolean billAccountSpecified) {
        this.billAccountSpecified = billAccountSpecified;
    }

    public String getBillAccount() {
        return billAccount;
    }

    public void setBillAccount(String billAccount) {
        this.billAccount = billAccount;
    }

    public boolean isNameSpecified() {
        return nameSpecified;
    }

    public void setNameSpecified(boolean nameSpecified) {
        this.nameSpecified = nameSpecified;
    }

    public boolean isBirthdaySpecified() {
        return birthdaySpecified;
    }

    public void setBirthdaySpecified(boolean birthdaySpecified) {
        this.birthdaySpecified = birthdaySpecified;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isSexSpecified() {
        return sexSpecified;
    }

    public void setSexSpecified(boolean sexSpecified) {
        this.sexSpecified = sexSpecified;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isFirstAddSpecified() {
        return firstAddSpecified;
    }

    public void setFirstAddSpecified(boolean firstAddSpecified) {
        this.firstAddSpecified = firstAddSpecified;
    }

    public String getFirstAdd() {
        return firstAdd;
    }

    public void setFirstAdd(String firstAdd) {
        this.firstAdd = firstAdd;
    }

    public boolean isSecondAddSpecified() {
        return secondAddSpecified;
    }

    public void setSecondAddSpecified(boolean secondAddSpecified) {
        this.secondAddSpecified = secondAddSpecified;
    }

    public String getSecondAdd() {
        return secondAdd;
    }

    public void setSecondAdd(String secondAdd) {
        this.secondAdd = secondAdd;
    }

    public boolean isIdnoSpecified() {
        return idnoSpecified;
    }

    public void setIdnoSpecified(boolean idnoSpecified) {
        this.idnoSpecified = idnoSpecified;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public boolean isTelNoSpecified() {
        return telNoSpecified;
    }

    public void setTelNoSpecified(boolean telNoSpecified) {
        this.telNoSpecified = telNoSpecified;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public boolean isCellNoSpecified() {
        return cellNoSpecified;
    }

    public void setCellNoSpecified(boolean cellNoSpecified) {
        this.cellNoSpecified = cellNoSpecified;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public boolean isEmailSpecified() {
        return emailSpecified;
    }

    public void setEmailSpecified(boolean emailSpecified) {
        this.emailSpecified = emailSpecified;
    }

    public boolean isYmspecified() {
        return ymspecified;
    }

    public void setYmspecified(boolean ymspecified) {
        this.ymspecified = ymspecified;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

    public boolean isWebsiteSpecified() {
        return websiteSpecified;
    }

    public void setWebsiteSpecified(boolean websiteSpecified) {
        this.websiteSpecified = websiteSpecified;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isFaxNoSpecified() {
        return faxNoSpecified;
    }

    public void setFaxNoSpecified(boolean faxNoSpecified) {
        this.faxNoSpecified = faxNoSpecified;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public boolean isContractCodeSpecified() {
        return contractCodeSpecified;
    }

    public void setContractCodeSpecified(boolean contractCodeSpecified) {
        this.contractCodeSpecified = contractCodeSpecified;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public boolean isIptvpwspecified() {
        return iptvpwspecified;
    }

    public void setIptvpwspecified(boolean iptvpwspecified) {
        this.iptvpwspecified = iptvpwspecified;
    }

    public String getIptvpw() {
        return iptvpw;
    }

    public void setIptvpw(String iptvpw) {
        this.iptvpw = iptvpw;
    }

    public boolean isServiceVASCodeSpecified() {
        return serviceVASCodeSpecified;
    }

    public void setServiceVASCodeSpecified(boolean serviceVASCodeSpecified) {
        this.serviceVASCodeSpecified = serviceVASCodeSpecified;
    }

    public String getServiceVASCode() {
        return serviceVASCode;
    }

    public void setServiceVASCode(String serviceVASCode) {
        this.serviceVASCode = serviceVASCode;
    }

    public boolean isStbidspecified() {
        return stbidspecified;
    }

    public void setStbidspecified(boolean stbidspecified) {
        this.stbidspecified = stbidspecified;
    }

    public String getIptvaccount() {
        return iptvaccount;
    }

    public void setIptvaccount(String iptvaccount) {
        this.iptvaccount = iptvaccount;
    }

    public String getPortDSL() {
        return portDSL;
    }

    public void setPortDSL(String portDSL) {
        this.portDSL = portDSL;
    }

    public boolean isPortDSLSpecified() {
        return portDSLSpecified;
    }

    public void setPortDSLSpecified(boolean portDSLSpecified) {
        this.portDSLSpecified = portDSLSpecified;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getMiservice() {
        return miservice;
    }

    public void setMiservice(String miservice) {
        this.miservice = miservice;
    }

    public boolean isFeePrepaidSpecified() {
        return feePrepaidSpecified;
    }

    public void setFeePrepaidSpecified(boolean feePrepaidSpecified) {
        this.feePrepaidSpecified = feePrepaidSpecified;
    }

    public String getFeePrepaid() {
        return feePrepaid;
    }

    public void setFeePrepaid(String feePrepaid) {
        this.feePrepaid = feePrepaid;
    }

    public boolean isDeviceNameSpecified() {
        return deviceNameSpecified;
    }

    public void setDeviceNameSpecified(boolean deviceNameSpecified) {
        this.deviceNameSpecified = deviceNameSpecified;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public boolean isMiserviceSpecified() {
        return miserviceSpecified;
    }

    public void setMiserviceSpecified(boolean miserviceSpecified) {
        this.miserviceSpecified = miserviceSpecified;
    }

    public boolean isIpspecified() {
        return ipspecified;
    }

    public void setIpspecified(boolean ipspecified) {
        this.ipspecified = ipspecified;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
