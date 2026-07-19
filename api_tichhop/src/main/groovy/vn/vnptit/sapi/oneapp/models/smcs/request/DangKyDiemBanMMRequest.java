
package vn.vnptit.sapi.oneapp.models.smcs.request;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DangKyDiemBanMMRequest {

    @SerializedName("contact_name")
    @Expose
    private String contactName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("contact_phone")
    @Expose
    private String contactPhone;
    @SerializedName("state_code")
    @Expose
    private String stateCode;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("agent_type")
    @Expose
    private String agentType;
    @SerializedName("hrmCode")
    @Expose
    private String hrmCode;
    @SerializedName("is_mc")
    @Expose
    private String isMc;
    @SerializedName("is_mm")
    @Expose
    private String isMm;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("eload_number")
    @Expose
    private String eloadNumber;
    @SerializedName("parentId")
    @Expose
    private String parentId;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("account")
    @Expose
    private String account;

    @SerializedName("maDvqhns")
    @Expose
    private String maDvqhns;

    @SerializedName("soCccd")
    @Expose
    private String soCccd;

    @SerializedName("province2025")
    @Expose
    private String province2025;

    @SerializedName("county2025")
    @Expose
    private String county2025;

    public String getProvince2025() {
        return province2025;
    }

    public void setProvince2025(String province2025) {
        this.province2025 = province2025;
    }

    public String getCounty2025() {
        return county2025;
    }

    public void setCounty2025(String county2025) {
        this.county2025 = county2025;
    }

    public String getMaDvqhns() {
        return maDvqhns;
    }

    public void setMaDvqhns(String maDvqhns) {
        this.maDvqhns = maDvqhns;
    }

    public String getSoCccd() {
        return soCccd;
    }

    public void setSoCccd(String soCccd) {
        this.soCccd = soCccd;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getHrmCode() {
        return hrmCode;
    }

    public void setHrmCode(String hrmCode) {
        this.hrmCode = hrmCode;
    }

    public String getIsMc() {
        return isMc;
    }

    public void setIsMc(String isMc) {
        this.isMc = isMc;
    }

    public String getIsMm() {
        return isMm;
    }

    public void setIsMm(String isMm) {
        this.isMm = isMm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEloadNumber() {
        return eloadNumber;
    }

    public void setEloadNumber(String eloadNumber) {
        this.eloadNumber = eloadNumber;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
