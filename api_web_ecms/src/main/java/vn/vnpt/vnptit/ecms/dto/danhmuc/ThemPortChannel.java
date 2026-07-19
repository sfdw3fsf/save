package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.Message;
import vn.vnpt.message.ModelValidation;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class ThemPortChannel {
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ma;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long tramTbId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long dslamId;
    @Valid
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private List<KenhPort> kenhPorts;

    public void setTen(String ten) {
        this.ten = ten;
    }
    public String getTen() {
        return ten;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }
    public String getMa() {
        return ma;
    }

    public void setTramTbId(Long tramTbId) {
        this.tramTbId = tramTbId;
    }
    public Long getTramTbId() {
        return tramTbId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }
    public Long getDslamId() {
        return dslamId;
    }

    public void setKenhPorts(List<KenhPort> kenhPorts) {
        this.kenhPorts = kenhPorts;
    }
    public List<KenhPort> getKenhPorts() {
        return kenhPorts;
    }
}
