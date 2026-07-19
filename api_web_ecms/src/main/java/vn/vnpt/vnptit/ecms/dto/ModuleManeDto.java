package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class ModuleManeDto {

    @JsonProperty("cardmane_id")
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    private Long cardManeId;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("vitri")
    private Long viTri;


    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("serial")
    private String serial;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @JsonProperty("partnumber")
    private String partNumber;

    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @JsonProperty("loaimodule_id")
    private Long loaiModuleId;

    public Long getCardManeId() {
        return cardManeId;
    }

    public void setCardManeId(Long cardManeId) {
        this.cardManeId = cardManeId;
    }

    public Long getViTri() {
        return viTri;
    }

    public void setViTri(Long viTri) {
        this.viTri = viTri;
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

    public Long getLoaiModuleId() {
        return loaiModuleId;
    }

    public void setLoaiModuleId(Long loaiModuleId) {
        this.loaiModuleId = loaiModuleId;
    }

    static public class ModuleManeUpdDto extends  ModuleManeDto {
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @JsonProperty("modulemane_id")
        private Long moduleManeId;

        public Long getModuleManeId() {
            return moduleManeId;
        }

        public void setModuleManeId(Long moduleManeId) {
            this.moduleManeId = moduleManeId;
        }
    }
}
