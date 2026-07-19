
package vn.vnptit.sapi.oneapp.models.smcs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import vn.vnptit.sapi.oneapp.models.smcs.response.SmcsBaseResponse;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class OrderDetail {

    @SerializedName("p_deliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("p_OrderNo")
    @Expose
    private String orderNo;
    @SerializedName("p_StatusOrder")
    @Expose
    private String statusOrder;
    @SerializedName("p_StatusTrans")
    @Expose
    private String statusTrans;
    @SerializedName("p_AmountInTax")
    @Expose
    private String amountInTax;
    @SerializedName("p_Discount")
    @Expose
    private String discount;
    @SerializedName("p_PayAmount")
    @Expose
    private String payAmount;
    @SerializedName("p_PayMethod")
    @Expose
    private String payMethod;
    @SerializedName("p_Reason")
    @Expose
    private String reason;
    @SerializedName("result")
    @Expose
    private List<ProductOrder> result;

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getStatusTrans() {
        return statusTrans;
    }

    public void setStatusTrans(String statusTrans) {
        this.statusTrans = statusTrans;
    }

    public String getAmountInTax() {
        return amountInTax;
    }

    public void setAmountInTax(String amountInTax) {
        this.amountInTax = amountInTax;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ProductOrder> getResult() {
        return result;
    }

    public void setResult(List<ProductOrder> result) {
        this.result = result;
    }
}
