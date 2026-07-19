package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CapNhatTienTrinhRequest extends BaseDTO {
    @JsonProperty("tienTrinhScId")
    private Long tienTrinhScId = null;
    @JsonProperty("donViId")
    private Long donViId = null;
    @JsonProperty("nhanVienId")
    private Long nhanVienId = null;
    @JsonProperty("noiDung")
    private String noiDung = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;
    @JsonProperty("mayCn")
    private String mayCn = null;
    @JsonProperty("ipCn")
    private String ipCn = null;

    public Long getTienTrinhScId() {
        return tienTrinhScId;
    }

    public void setTienTrinhScId(Long tienTrinhScId) {
        this.tienTrinhScId = tienTrinhScId;
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public Long getNhanVienId() {
        return nhanVienId;
    }

    public void setNhanVienId(Long nhanVienId) {
        this.nhanVienId = nhanVienId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getMayCn() {
        return mayCn;
    }

    public void setMayCn(String mayCn) {
        this.mayCn = mayCn;
    }

    public String getIpCn() {
        return ipCn;
    }

    public void setIpCn(String ipCn) {
        this.ipCn = ipCn;
    }
}
