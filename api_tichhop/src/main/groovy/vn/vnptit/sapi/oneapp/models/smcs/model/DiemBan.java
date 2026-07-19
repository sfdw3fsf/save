
package vn.vnptit.sapi.oneapp.models.smcs.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class DiemBan {

    @SerializedName("CareCount")
    @Expose
    private String careCount;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Reseller")
    @Expose
    private String reseller;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("StaffCode")
    @Expose
    private String staffCode;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("AgentType")
    @Expose
    private String agentType;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("AgentCode")
    @Expose
    private String agentCode;
    @SerializedName("StaHour")
    @Expose
    private String staHour;
    @SerializedName("ParAgentCode")
    @Expose
    private String parAgentCode;
    @SerializedName("EndHour")
    @Expose
    private String endHour;
    @SerializedName("AgentName")
    @Expose
    private String agentName;

    @SerializedName("MaDVQHNS")
    @Expose
    private String ma_dvqhns;

    @SerializedName("SoCCCD")
    @Expose
    private String so_cccd;

    public String getMa_dvqhns() {
        return ma_dvqhns;
    }

    public void setMa_dvqhns(String ma_dvqhns) {
        this.ma_dvqhns = ma_dvqhns;
    }

    public String getSo_cccd() {
        return so_cccd;
    }

    public void setSo_cccd(String so_cccd) {
        this.so_cccd = so_cccd;
    }

    public String getCareCount() {
        return careCount;
    }

    public void setCareCount(String careCount) {
        this.careCount = careCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReseller() {
        return reseller;
    }

    public void setReseller(String reseller) {
        this.reseller = reseller;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getStaHour() {
        return staHour;
    }

    public void setStaHour(String staHour) {
        this.staHour = staHour;
    }

    public String getParAgentCode() {
        return parAgentCode;
    }

    public void setParAgentCode(String parAgentCode) {
        this.parAgentCode = parAgentCode;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

}
