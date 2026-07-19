package vn.vnpt.vnptit.ecms.dto.idc;


public class SearchDanhMucBladeServerDto {
    private String maVt;              // Mã vật tư
    private String modelThietBi;      // Model thiết bị
    private Long hangSxId;            // Hãng sản xuất ID
    private Integer bladeSoKhe;       // Số khe blade
    private String dpLoaiCong;        // Loại cổng Data Port
    private Long dpTtModuleId;        // Module ID (Data Port)
    private Long dpMucDichSdId;       // Mục đích sử dụng (Data Port)
    private Integer ppLoaiNd;          // Loại nguồn điện (Power Port)
    private Long ppChuanDnId;         // Chuẩn đầu nối ID
    private String cpLoaiCong;        // Loại cổng (Control/Console Port)

    public String getMaVt() {
        return maVt;
    }

    public void setMaVt(String maVt) {
        this.maVt = maVt;
    }

    public String getModelThietBi() {
        return modelThietBi;
    }

    public void setModelThietBi(String modelThietBi) {
        this.modelThietBi = modelThietBi;
    }

    public Long getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Long hangSxId) {
        this.hangSxId = hangSxId;
    }

    public Integer getBladeSoKhe() {
        return bladeSoKhe;
    }

    public void setBladeSoKhe(Integer bladeSoKhe) {
        this.bladeSoKhe = bladeSoKhe;
    }

    public Long getDpTtModuleId() {
        return dpTtModuleId;
    }

    public void setDpTtModuleId(Long dpTtModuleId) {
        this.dpTtModuleId = dpTtModuleId;
    }

    public String getDpLoaiCong() {
        return dpLoaiCong;
    }

    public void setDpLoaiCong(String dpLoaiCong) {
        this.dpLoaiCong = dpLoaiCong;
    }

    public Long getDpMucDichSdId() {
        return dpMucDichSdId;
    }

    public void setDpMucDichSdId(Long dpMucDichSdId) {
        this.dpMucDichSdId = dpMucDichSdId;
    }

    public Integer getPpLoaiNd() {
        return ppLoaiNd;
    }

    public void setPpLoaiNd(Integer ppLoaiNd) {
        this.ppLoaiNd = ppLoaiNd;
    }

    public Long getPpChuanDnId() {
        return ppChuanDnId;
    }

    public void setPpChuanDnId(Long ppChuanDnId) {
        this.ppChuanDnId = ppChuanDnId;
    }

    public String getCpLoaiCong() {
        return cpLoaiCong;
    }

    public void setCpLoaiCong(String cpLoaiCong) {
        this.cpLoaiCong = cpLoaiCong;
    }

}