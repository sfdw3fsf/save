package vn.vnpt.vnptit.ecms.dto;

public class NapDanhSachLuoiVciVpiDtoIn {
    private Long dslamId;
    private Long quyhoachId;
    private Integer kieuKn;
    private Integer trangThaiId;
    private Long vciVpiId;

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getQuyhoachId() {
        return quyhoachId;
    }

    public void setQuyhoachId(Long quyhoachId) {
        this.quyhoachId = quyhoachId;
    }

    public Integer getKieuKn() {
        return kieuKn;
    }

    public void setKieuKn(Integer kieuKn) {
        this.kieuKn = kieuKn;
    }

    public Integer getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Integer trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Long getVciVpiId() {
        return vciVpiId;
    }

    public void setVciVpiId(Long vciVpiId) {
        this.vciVpiId = vciVpiId;
    }
}
