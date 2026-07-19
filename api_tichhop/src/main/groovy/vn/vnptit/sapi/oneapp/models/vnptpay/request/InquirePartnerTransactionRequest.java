package vn.vnptit.sapi.oneapp.models.vnptpay.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

/**
 * CancelTransactionRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
public class InquirePartnerTransactionRequest {
    @JsonProperty("paymentCode")
    private String paymentCode = null;
    @JsonProperty("identifycode")
    private String identifycode = null;
    @JsonProperty("bill_amount")
    private String bill_amount = null;
    @JsonProperty("payment_method")
    private String payment_method = null;
    @JsonProperty("phanvung_id")
    private String phanvung_id = null;
    @JsonProperty("user")
    private String user = null;
    @JsonProperty("agencyId")
    private String agencyId = null;
    @JsonProperty("employeeId")
    private String employeeId = null;
    @JsonProperty("contractType")
    private String contractType = null;
    @JsonProperty("tong_tien")
    private String tong_tien = null;
    @JsonProperty("groupId")
    private String groupId = null;
    @JsonProperty("sellingDepartmentId")
    private String sellingDepartmentId = null;
    @JsonProperty("requestId")
    private String requestId = null;
    @JsonProperty("requestDate")
    private String requestDate = null;
    @JsonProperty("kh_nv")
    private String kh_nv = null;
    @JsonProperty("thanh_toan")
    private String thanh_toan = null;

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getIdentifycode() {
        return identifycode;
    }

    public void setIdentifycode(String identifycode) {
        this.identifycode = identifycode;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(String phanvung_id) {
        this.phanvung_id = phanvung_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(String tong_tien) {
        this.tong_tien = tong_tien;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSellingDepartmentId() {
        return sellingDepartmentId;
    }

    public void setSellingDepartmentId(String sellingDepartmentId) {
        this.sellingDepartmentId = sellingDepartmentId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getKh_nv() {
        return kh_nv;
    }

    public void setKh_nv(String kh_nv) {
        this.kh_nv = kh_nv;
    }

    public String getThanh_toan() {
        return thanh_toan;
    }

    public void setThanh_toan(String thanh_toan) {
        this.thanh_toan = thanh_toan;
    }
}
