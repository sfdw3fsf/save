package vn.vnpt.vnptit.ecms.dto.idc;

public class DeleteKetQuaCongSuatDien {
    private Long thiCongDienId;
    private String loai; // "CAPPHAT" ,"THUHOI","CHUYENDOI"
    private Long thietBiId;
    private Long oCamId;
    private Long oCamIdCu;
    private Long oCamIdMoi;

    public Long getThiCongDienId() {
        return thiCongDienId;
    }

    public void setThiCongDienId(Long thiCongDienId) {
        this.thiCongDienId = thiCongDienId;
    }

    public Long getoCamIdMoi() {
        return oCamIdMoi;
    }

    public void setoCamIdMoi(Long oCamIdMoi) {
        this.oCamIdMoi = oCamIdMoi;
    }

    public Long getoCamIdCu() {
        return oCamIdCu;
    }

    public void setoCamIdCu(Long oCamIdCu) {
        this.oCamIdCu = oCamIdCu;
    }

    public Long getoCamId() {
        return oCamId;
    }

    public void setoCamId(Long oCamId) {
        this.oCamId = oCamId;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }
}