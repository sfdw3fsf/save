package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class ThemDslamModule {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long cardDslId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long loaiModuleId;
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer viTri;
    private String serial;
//    @NotEmpty(message = ModelValidation.NOT_EMPTY) //lvhquan: được phép null
    private String partNumber;

    public void setCardDslId(Long cardDslId) {
        this.cardDslId = cardDslId;
    }
    public Long getCardDslId() {
        return cardDslId;
    }

    public void setLoaiModuleId(Long loaiModuleId) {
        this.loaiModuleId = loaiModuleId;
    }
    public Long getLoaiModuleId() {
        return loaiModuleId;
    }

    public void setViTri(Integer viTri) {
        this.viTri = viTri;
    }
    public Integer getViTri() {
        return viTri;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
    public String getSerial() {
        return serial;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }
    public String getPartNumber() {
        return partNumber;
    }
}
