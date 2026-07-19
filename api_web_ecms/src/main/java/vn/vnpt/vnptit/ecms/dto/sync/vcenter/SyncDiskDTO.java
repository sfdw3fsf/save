package vn.vnpt.vnptit.ecms.dto.sync.vcenter;

public class SyncDiskDTO {
    private String diskCode;      // Lấy API -> Map để query ra ID
    private String diskName;      // Lấy API -> Map để lưu tên
    private Long dungLuong;     // ERD: MAYAO_VUNGDL.DUNG_LUONG
    private Long maVungDuLieu;    // ERD: MAYAO_VUNGDL.VUNGDL_ID => Datastore => Chưa mapping được

    public String getDiskCode() {
        return diskCode;
    }

    public void setDiskCode(String diskCode) {
        this.diskCode = diskCode;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public Long getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(Long dungLuong) {
        this.dungLuong = dungLuong;
    }

    public Long getMaVungDuLieu() {
        return maVungDuLieu;
    }

    public void setMaVungDuLieu(Long maVungDuLieu) {
        this.maVungDuLieu = maVungDuLieu;
    }
}
