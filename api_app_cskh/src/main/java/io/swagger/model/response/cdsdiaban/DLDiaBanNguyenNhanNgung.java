
package io.swagger.model.response.cdsdiaban;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class DLDiaBanNguyenNhanNgung {

    @SerializedName("dichVu")
    @Expose
    private String dichVu;
    @SerializedName("nguyenNhan")
    @Expose
    private String nguyenNhan;
    @SerializedName("soLan")
    @Expose
    private String soLan;

    public String getDichVu() {
        return dichVu;
    }

    public void setDichVu(String dichVu) {
        this.dichVu = dichVu;
    }

    public String getNguyenNhan() {
        return nguyenNhan;
    }

    public void setNguyenNhan(String nguyenNhan) {
        this.nguyenNhan = nguyenNhan;
    }

    public String getSoLan() {
        return soLan;
    }

    public void setSoLan(String soLan) {
        this.soLan = soLan;
    }
}
