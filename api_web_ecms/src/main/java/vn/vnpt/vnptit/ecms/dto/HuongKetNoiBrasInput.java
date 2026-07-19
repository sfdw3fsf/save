package vn.vnpt.vnptit.ecms.dto;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.*;

public class HuongKetNoiBrasInput {

    public static class HuongKetNoiBrasInputLayDsHuongKetNoi {

        @NotNull(message = ModelValidation.NOT_NULL)
        @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
        @Max(9)
        Long vKieu;

        Long vVungId;

        public HuongKetNoiBrasInputLayDsHuongKetNoi(Long vKieu, Long vVungId, Long phanVungId) {
            this.vKieu = vKieu;
            this.vVungId = vVungId;
        }

        public HuongKetNoiBrasInputLayDsHuongKetNoi() {
        }

        public Long getvKieu() {
            return vKieu;
        }

        public void setvKieu(Long vKieu) {
            this.vKieu = vKieu;
        }

        public Long getvVungId() {
            return vVungId;
        }

        public void setvVungId(Long vVungId) {
            this.vVungId = vVungId;
        }
    }

    public static class HuongKetNoiBrasInputLayDsHuongKetNoiUpd extends HuongKetNoiBrasInput {

        @NotNull(message = ModelValidation.NOT_NULL)
        private Long huongknId;

        public Long getHuongknId() {
            return huongknId;
        }

        public void setHuongknId(Long huongknId) {
            this.huongknId = huongknId;
        }

    }

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @Max(value = 9, message = ModelValidation.MAX)
    Long loaiknId;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(max = 150, message = ModelValidation.MAX)
    String tenHuong;

    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    @Size(max = 50, message = ModelValidation.MAX)
    String thietBi;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    @Max(value = 9, message = ModelValidation.MAX)
    Long kieu;

    @NotNull(message = ModelValidation.NOT_NULL)
    @PositiveOrZero(message = ModelValidation.POSITIVE_OR_ZERO)
    Long dslamId;

    public Long getLoaiknId() {
        return loaiknId;
    }

    public void setLoaiknId(Long loaiknId) {
        this.loaiknId = loaiknId;
    }

    public String getTenHuong() {
        return tenHuong;
    }

    public void setTenHuong(String tenHuong) {
        this.tenHuong = tenHuong;
    }

    public String getThietBi() {
        return thietBi;
    }

    public void setThietBi(String thietBi) {
        this.thietBi = thietBi;
    }

    public Long getKieu() {
        return kieu;
    }

    public void setKieu(Long kieu) {
        this.kieu = kieu;
    }

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }
}
