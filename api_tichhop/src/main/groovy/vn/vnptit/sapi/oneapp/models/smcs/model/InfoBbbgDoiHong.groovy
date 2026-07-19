package vn.vnptit.sapi.oneapp.models.smcs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import javax.annotation.Generated

@Generated("jsonschema2pojo")
class InfoBbbgDoiHong {

    @SerializedName("stock_trans_id")
    @Expose
    private String stock_trans_id;

    @SerializedName("trans_no")
    @Expose
    private String trans_no;

    @SerializedName("trans_date")
    @Expose
    private String trans_date;

    @SerializedName("reseller_name")
    @Expose
    private String reseller_name;

    @SerializedName("reseller_address")
    @Expose
    private String reseller_address;

    @SerializedName("reseller_contact_name")
    @Expose
    private String reseller_contact_name;

    @SerializedName("refreseller_contact_name")
    @Expose
    private String refreseller_contact_name;

    @SerializedName("reseller_contact_phone")
    @Expose
    private String reseller_contact_phone;

    @SerializedName("ref_reseller_id")
    @Expose
    private String ref_reseller_id;

    @SerializedName("short_refreseller")
    @Expose
    private String short_refreseller;

    @SerializedName("refreseller_name")
    @Expose
    private String refreseller_name;

    @SerializedName("storekeeper_name")
    @Expose
    private String storekeeper_name;

    @SerializedName("receiver_name")
    @Expose
    private String receiver_name;

    @SerializedName("city_name")
    @Expose
    private String city_name;

    @SerializedName("staff_name")
    @Expose
    private String staff_name;

    @SerializedName("list_detail")
    @Expose
    private List<ListInfoBBBGDoiHongDetail> list_detail;

    String getStock_trans_id() {
        return stock_trans_id
    }

    void setStock_trans_id(String stock_trans_id) {
        this.stock_trans_id = stock_trans_id
    }

    String getTrans_no() {
        return trans_no
    }

    void setTrans_no(String trans_no) {
        this.trans_no = trans_no
    }

    String getTrans_date() {
        return trans_date
    }

    void setTrans_date(String trans_date) {
        this.trans_date = trans_date
    }

    String getReseller_name() {
        return reseller_name
    }

    void setReseller_name(String reseller_name) {
        this.reseller_name = reseller_name
    }

    String getReseller_address() {
        return reseller_address
    }

    void setReseller_address(String reseller_address) {
        this.reseller_address = reseller_address
    }

    String getReseller_contact_name() {
        return reseller_contact_name
    }

    void setReseller_contact_name(String reseller_contact_name) {
        this.reseller_contact_name = reseller_contact_name
    }

    String getRefreseller_contact_name() {
        return refreseller_contact_name
    }

    void setRefreseller_contact_name(String refreseller_contact_name) {
        this.refreseller_contact_name = refreseller_contact_name
    }

    String getReseller_contact_phone() {
        return reseller_contact_phone
    }

    void setReseller_contact_phone(String reseller_contact_phone) {
        this.reseller_contact_phone = reseller_contact_phone
    }

    String getRef_reseller_id() {
        return ref_reseller_id
    }

    void setRef_reseller_id(String ref_reseller_id) {
        this.ref_reseller_id = ref_reseller_id
    }

    String getShort_refreseller() {
        return short_refreseller
    }

    void setShort_refreseller(String short_refreseller) {
        this.short_refreseller = short_refreseller
    }

    String getRefreseller_name() {
        return refreseller_name
    }

    void setRefreseller_name(String refreseller_name) {
        this.refreseller_name = refreseller_name
    }

    String getStorekeeper_name() {
        return storekeeper_name
    }

    void setStorekeeper_name(String storekeeper_name) {
        this.storekeeper_name = storekeeper_name
    }

    String getReceiver_name() {
        return receiver_name
    }

    void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name
    }

    String getCity_name() {
        return city_name
    }

    void setCity_name(String city_name) {
        this.city_name = city_name
    }

    String getStaff_name() {
        return staff_name
    }

    void setStaff_name(String staff_name) {
        this.staff_name = staff_name
    }

    List<ListInfoBBBGDoiHongDetail> getList_detail() {
        return list_detail
    }

    void setList_detail(List<ListInfoBBBGDoiHongDetail> list_detail) {
        this.list_detail = list_detail
    }
}
