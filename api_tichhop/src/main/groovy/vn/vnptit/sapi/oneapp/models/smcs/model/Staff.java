
package vn.vnptit.sapi.oneapp.models.smcs.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Staff {

    @SerializedName("sales_type")
    @Expose
    private String salesType;
    @SerializedName("hrm_code")
    @Expose
    private String hrmCode;
    @SerializedName("staff_code")
    @Expose
    private String staffCode;
    @SerializedName("reseller_code")
    @Expose
    private String resellerCode;
    @SerializedName("staff_pre_post")
    @Expose
    private String staffPrePost;
    @SerializedName("eload_no")
    @Expose
    private String eloadNo;
    @SerializedName("hrm_province_id")
    @Expose
    private String hrmProvinceId;
    @SerializedName("staff_type")
    @Expose
    private String staffType;
    @SerializedName("province")
    @Expose
    private Area province;
    @SerializedName("province_id")
    @Expose
    private String provinceId;
    @SerializedName("staff_id")
    @Expose
    private String staffId;
    @SerializedName("hrm_province")
    @Expose
    private String hrmProvince;
    @SerializedName("reseller_id")
    @Expose
    private String resellerId;
    @SerializedName("new_province_id")
    @Expose
    private String newProvinceId;
    @SerializedName("new_province_code")
    @Expose
    private String newProvinceCode;
    @SerializedName("new_province_name")
    @Expose
    private String newProvinceName;
    private Area newProvince;

    public String getNewProvinceId() {
        return newProvinceId;
    }

    public void setNewProvinceId(String newProvinceId) {
        this.newProvinceId = newProvinceId;
    }

    public String getNewProvinceCode() {
        return newProvinceCode;
    }

    public void setNewProvinceCode(String newProvinceCode) {
        this.newProvinceCode = newProvinceCode;
    }

    public String getNewProvinceName() {
        return newProvinceName;
    }

    public void setNewProvinceName(String newProvinceName) {
        this.newProvinceName = newProvinceName;
    }

    public Area getNewProvince() {
        return newProvince;
    }

    public void setNewProvince(Area newProvince) {
        this.newProvince = newProvince;
    }

    private String tinhId;

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getHrmCode() {
        return hrmCode;
    }

    public void setHrmCode(String hrmCode) {
        this.hrmCode = hrmCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getResellerCode() {
        return resellerCode;
    }

    public void setResellerCode(String resellerCode) {
        this.resellerCode = resellerCode;
    }

    public String getStaffPrePost() {
        return staffPrePost;
    }

    public void setStaffPrePost(String staffPrePost) {
        this.staffPrePost = staffPrePost;
    }

    public String getEloadNo() {
        return eloadNo;
    }

    public void setEloadNo(String eloadNo) {
        this.eloadNo = eloadNo;
    }

    public String getHrmProvinceId() {
        return hrmProvinceId;
    }

    public void setHrmProvinceId(String hrmProvinceId) {
        this.hrmProvinceId = hrmProvinceId;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }

    public Area getProvince() {
        return province;
    }

    public void setProvince(Area province) {
        this.province = province;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getHrmProvince() {
        return hrmProvince;
    }

    public void setHrmProvince(String hrmProvince) {
        this.hrmProvince = hrmProvince;
    }

    public String getResellerId() {
        return resellerId;
    }

    public void setResellerId(String resellerId) {
        this.resellerId = resellerId;
    }

    public String getTinhId() {
        return tinhId;
    }

    public void setTinhId(String tinhId) {
        this.tinhId = tinhId;
    }
}
