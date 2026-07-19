
package vn.vnptit.sapi.oneapp.models.smcs.request;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AddDiemBanRequest {

    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("agentName")
    @Expose
    private String agentName;
    @SerializedName("agentCode")
    @Expose
    private String agentCode;
    @SerializedName("parAgentCode")
    @Expose
    private String parAgentCode;
    @SerializedName("contactName")
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
    @SerializedName("contactPhone")
    @Expose
    private String contactPhone;
    @SerializedName("contactEmail")
    @Expose
    private String contactEmail;
    @SerializedName("eloadNumber")
    @Expose
    private String eloadNumber;
    @SerializedName("msin")
    @Expose
    private String msin;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getParAgentCode() {
        return parAgentCode;
    }

    public void setParAgentCode(String parAgentCode) {
        this.parAgentCode = parAgentCode;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getEloadNumber() {
        return eloadNumber;
    }

    public void setEloadNumber(String eloadNumber) {
        this.eloadNumber = eloadNumber;
    }

    public String getMsin() {
        return msin;
    }

    public void setMsin(String msin) {
        this.msin = msin;
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
}
