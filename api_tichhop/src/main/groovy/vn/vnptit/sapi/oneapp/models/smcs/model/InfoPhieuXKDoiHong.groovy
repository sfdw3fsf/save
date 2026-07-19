package vn.vnptit.sapi.oneapp.models.smcs.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import javax.annotation.Generated

@Generated("jsonschema2pojo")
class InfoPhieuXKDoiHong {

    @SerializedName("reseller")
    @Expose
    private String reseller;

    @SerializedName("tin")
    @Expose
    private String tin;

    @SerializedName("reseller_address")
    @Expose
    private String reseller_address;

    @SerializedName("trans_no")
    @Expose
    private String trans_no;

    @SerializedName("stock")
    @Expose
    private String stock;

    @SerializedName("refstock")
    @Expose
    private String refstock;

    @SerializedName("ref_reseller")
    @Expose
    private String ref_reseller;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("day_ref")
    @Expose
    private String day_ref;

    @SerializedName("month_ref")
    @Expose
    private String month_ref;

    @SerializedName("year_ref")
    @Expose
    private String year_ref;

    @SerializedName("sum_quantity")
    @Expose
    private String sum_quantity;

    @SerializedName("sum_actual_quantity")
    @Expose
    private String sum_actual_quantity;

    @SerializedName("sum_amount")
    @Expose
    private String sum_amount;

    @SerializedName("list_detail")
    @Expose
    private List<ListInfoPhieuXKDoiHongDetail> list_detail;

    String getReseller() {
        return reseller
    }

    void setReseller(String reseller) {
        this.reseller = reseller
    }

    String getTin() {
        return tin
    }

    void setTin(String tin) {
        this.tin = tin
    }

    String getReseller_address() {
        return reseller_address
    }

    void setReseller_address(String reseller_address) {
        this.reseller_address = reseller_address
    }

    String getTrans_no() {
        return trans_no
    }

    void setTrans_no(String trans_no) {
        this.trans_no = trans_no
    }

    String getStock() {
        return stock
    }

    void setStock(String stock) {
        this.stock = stock
    }

    String getRefstock() {
        return refstock
    }

    void setRefstock(String refstock) {
        this.refstock = refstock
    }

    String getRef_reseller() {
        return ref_reseller
    }

    void setRef_reseller(String ref_reseller) {
        this.ref_reseller = ref_reseller
    }

    String getDay() {
        return day
    }

    void setDay(String day) {
        this.day = day
    }

    String getMonth() {
        return month
    }

    void setMonth(String month) {
        this.month = month
    }

    String getYear() {
        return year
    }

    void setYear(String year) {
        this.year = year
    }

    String getDay_ref() {
        return day_ref
    }

    void setDay_ref(String day_ref) {
        this.day_ref = day_ref
    }

    String getMonth_ref() {
        return month_ref
    }

    void setMonth_ref(String month_ref) {
        this.month_ref = month_ref
    }

    String getYear_ref() {
        return year_ref
    }

    void setYear_ref(String year_ref) {
        this.year_ref = year_ref
    }

    String getSum_quantity() {
        return sum_quantity
    }

    void setSum_quantity(String sum_quantity) {
        this.sum_quantity = sum_quantity
    }

    String getSum_actual_quantity() {
        return sum_actual_quantity
    }

    void setSum_actual_quantity(String sum_actual_quantity) {
        this.sum_actual_quantity = sum_actual_quantity
    }

    String getSum_amount() {
        return sum_amount
    }

    void setSum_amount(String sum_amount) {
        this.sum_amount = sum_amount
    }

    List<ListInfoPhieuXKDoiHongDetail> getList_detail() {
        return list_detail
    }

    void setList_detail(List<ListInfoPhieuXKDoiHongDetail> list_detail) {
        this.list_detail = list_detail
    }
}
