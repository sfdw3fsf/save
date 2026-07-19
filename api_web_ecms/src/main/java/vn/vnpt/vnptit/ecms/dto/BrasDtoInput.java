package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class BrasDtoInput {
    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("bras_id")
    private long brasId;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("bras_vdc_id")
    private long brasVdcId;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("ip")
    private String ip;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 100, message = ModelValidation.INVALID)
    @JsonProperty("ten_bras")
    private String tenBras;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Size(min= 1, max = 10, message = ModelValidation.INVALID)
    @JsonProperty("so_slot")
    private String soSlot;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Max(value = 99,message = ModelValidation.MAX + " 99")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("so_port")
    private int soPort;

    @NotNull(message = ModelValidation.NOT_NULL)
    @Max(value = 99,message = ModelValidation.MAX + "99")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("used")
    private int used;

    public long getBrasId() {
        return brasId;
    }

    public void setBrasId(long brasId) {
        this.brasId = brasId;
    }

    public long getBrasVdcId() {
        return brasVdcId;
    }

    public void setBrasVdcId(long brasVdcId) {
        this.brasVdcId = brasVdcId;
    }

    public String getIp() {
        return ip;
    }

    public String getTenBras() {
        return tenBras;
    }

    public void setTenBras(String tenBras) {
        this.tenBras = tenBras;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSoSlot() {
        return soSlot;
    }

    public void setSoSlot(String soSlot) {
        this.soSlot = soSlot;
    }

    public int getSoPort() {
        return soPort;
    }

    public void setSoPort(int soPort) {
        this.soPort = soPort;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
