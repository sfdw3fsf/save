package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class CardTDDtoInput {
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaicard_id;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long frametd_id;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer vitri;

    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer portbd;

    private String serial;

    private String partNumber;

    public Long getLoaicard_id() {
        return loaicard_id;
    }

    public void setLoaicard_id(Long loaicard_id) {
        this.loaicard_id = loaicard_id;
    }

    public Long getFrametd_id() {
        return frametd_id;
    }

    public void setFrametd_id(Long frametd_id) {
        this.frametd_id = frametd_id;
    }

    public Integer getVitri() {
        return vitri;
    }

    public void setVitri(Integer vitri) {
        this.vitri = vitri;
    }

    public Integer getPortbd() {
        return portbd;
    }

    public void setPortbd(Integer portbd) {
        this.portbd = portbd;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
}
