package vn.vnpt.vnptit.qlsc.dto.cntt;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class LuuBuocThuTucInput {

    @JsonProperty("p_id_giaophieu_thutuc")
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long p_id_giaophieu_thutuc;

    @JsonProperty("p_thanhcong")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer p_thanhcong;

    @JsonProperty("p_id_lydo")
    private Long p_id_lydo;

    @JsonProperty("p_ghichu")
    private String p_ghichu;

    public Long getP_id_giaophieu_thutuc() { return p_id_giaophieu_thutuc; }
    public Integer getP_thanhcong() { return p_thanhcong; }
    public Long getP_id_lydo() { return p_id_lydo; }
    public String getP_ghichu() { return p_ghichu; }
}
