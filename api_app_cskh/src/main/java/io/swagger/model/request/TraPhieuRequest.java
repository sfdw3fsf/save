package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Generated;
import java.sql.Date;

/**
 * Trả phiếu
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class TraPhieuRequest {

    @JsonProperty("phieuId")
    private Long phieuId;

    @JsonProperty("ndTra")
    private String ndTra;

    @JsonProperty("lyDoTraId")
    private Long lyDoTraId;

//    @JsonProperty("nhanVienTraId")
//    private Long nhanVienTraId;

//    @JsonFormat(pattern = "dd/MM/yyyy")
//    @JsonProperty("ngayCn")
//    protected Date ngayCn;
//
//    @JsonProperty("mayCn")
//    protected String mayCn;
//
//    @JsonProperty("nguoiCn")
//    protected String nguoiCn;
//
//    @JsonProperty("ipCn")
//    protected String ipCn;

    public Long getPhieuId() {
        return phieuId;
    }

    public void setPhieuId(Long phieuId) {
        this.phieuId = phieuId;
    }

    public String getNdTra() {
        return ndTra;
    }

    public void setNdTra(String ndTra) {
        this.ndTra = ndTra;
    }

    public Long getLyDoTraId() {
        return lyDoTraId;
    }

    public void setLyDoTraId(Long lyDoTraId) {
        this.lyDoTraId = lyDoTraId;
    }

//    public Long getNhanVienTraId() {
//        return nhanVienTraId;
//    }
//
//    public void setNhanVienTraId(Long nhanVienTraId) {
//        this.nhanVienTraId = nhanVienTraId;
//    }

//    public Date getNgayCn() {
//        return ngayCn;
//    }
//
//    public void setNgayCn(Date ngayCn) {
//        this.ngayCn = ngayCn;
//    }
//
//    public String getMayCn() {
//        return mayCn;
//    }
//
//    public void setMayCn(String mayCn) {
//        this.mayCn = mayCn;
//    }
//
//    public String getNguoiCn() {
//        return nguoiCn;
//    }
//
//    public void setNguoiCn(String nguoiCn) {
//        this.nguoiCn = nguoiCn;
//    }
//
//    public String getIpCn() {
//        return ipCn;
//    }
//
//    public void setIpCn(String ipCn) {
//        this.ipCn = ipCn;
//    }
}
