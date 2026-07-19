
package vn.vnptit.sapi.oneapp.models.smcs.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Product {

    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("ma_tinh")
    @Expose
    private String maTinh;
    @SerializedName("stock_name")
    @Expose
    private String stockName;
    @SerializedName("from_serial")
    @Expose
    private String fromSerial;
    @SerializedName("product_status")
    @Expose
    private String productStatus;
    @SerializedName("active_date")
    @Expose
    private String activeDate;
    @SerializedName("product_code")
    @Expose
    private String productCode;
    @SerializedName("stock_id")
    @Expose
    private String stockId;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMaTinh() {
        return maTinh;
    }

    public void setMaTinh(String maTinh) {
        this.maTinh = maTinh;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

}
