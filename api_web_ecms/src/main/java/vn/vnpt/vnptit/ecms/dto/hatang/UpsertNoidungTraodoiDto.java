package vn.vnpt.vnptit.ecms.dto.hatang;

/**
 * DTO upsert nội dung trao đổi theo phiếu yêu cầu.
 * Tương ứng với tham số của SP_UPSERT_NOIDUNG_TRAODOI.
 */
public class UpsertNoidungTraodoiDto {

    /** ID bản ghi trao đổi; null => insert, có giá trị => update */
    private Long ndTraodoiId;

    /** ID phiếu yêu cầu (bắt buộc) */
    private String phieuycId;

    /** Nội dung trao đổi (CLOB) */
    private String noiDung;

    /** ID nhân viên gửi */
    private Long nhanvienId;

    // ===== Getters & Setters =====

    public Long getNdTraodoiId() {
        return ndTraodoiId;
    }

    public void setNdTraodoiId(Long ndTraodoiId) {
        this.ndTraodoiId = ndTraodoiId;
    }

    public String getPhieuycId() {
        return phieuycId;
    }

    public void setPhieuycId(String phieuycId) {
        this.phieuycId = phieuycId;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Long getNhanvienId() {
        return nhanvienId;
    }

    public void setNhanvienId(Long nhanvienId) {
        this.nhanvienId = nhanvienId;
    }
}
