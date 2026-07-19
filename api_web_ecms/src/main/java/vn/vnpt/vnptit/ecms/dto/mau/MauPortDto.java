package vn.vnpt.vnptit.ecms.dto.mau;

import vn.vnpt.vnptit.ecms.dto.idc.CongKetNoiThietBiDtoV2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO cho bảng MAU_PORT (Port mẫu của thiết bị)
 */
public class MauPortDto {
    private Long mauportId;             // MAUPORT_ID
    private Long mautbiId;               // MAUTBI_ID (FK)
    private String ten;                  // TEN
    private Long loaicongId;             // LOAICONG_ID
    private Long bangthong;              // BANGTHONG
    private Long ddDinhdanh;             // DD_DINHDANH
    private Long daDinhdanh;             // DA_DINHDANH
    private Long loaindienId;             // LOAINDIEN_ID
    private Long chuandnId;              // CHUANDN_ID
    private String ghichu;               // GHICHU
    private String mota;                 // MOTA
    private String nguoiCn;              // NGUOI_CN
    private LocalDateTime ngayCn;        // NGAY_CN
    private PortsDto portsDto;
    private LineCardsDto lineCardsDto;

    public PortsDto getPortsDto() {
        return portsDto;
    }

    public LineCardsDto getLineCardsDto() {
        return lineCardsDto;
    }

    // Getters and Setters
    public Long getMauportId() {
        return mauportId;
    }

    public void setMauportId(Long mauportId) {
        this.mauportId = mauportId;
    }

    public Long getMautbiId() {
        return mautbiId;
    }

    public void setMautbiId(Long mautbiId) {
        this.mautbiId = mautbiId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Long getLoaicongId() {
        return loaicongId;
    }

    public void setLoaicongId(Long loaicongId) {
        this.loaicongId = loaicongId;
    }

    public Long getBangthong() {
        return bangthong;
    }

    public void setBangthong(Long bangthong) {
        this.bangthong = bangthong;
    }

    public Long getDdDinhdanh() {
        return ddDinhdanh;
    }

    public void setDdDinhdanh(Long ddDinhdanh) {
        this.ddDinhdanh = ddDinhdanh;
    }

    public Long getDaDinhdanh() {
        return daDinhdanh;
    }

    public void setDaDinhdanh(Long daDinhdanh) {
        this.daDinhdanh = daDinhdanh;
    }

    public Long getLoaindienId() {
        return loaindienId;
    }

    public void setLoaindienId(Long loaindienId) {
        this.loaindienId = loaindienId;
    }

    public Long getChuandnId() {
        return chuandnId;
    }

    public void setChuandnId(Long chuandnId) {
        this.chuandnId = chuandnId;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public LocalDateTime getNgayCn() {
        return ngayCn;
    }

    public void setNgayCn(LocalDateTime ngayCn) {
        this.ngayCn = ngayCn;
    }

    public static class PortsDto implements Serializable {
        private Integer phanLoai;
        private Integer soCong;
        private Integer batDauTu;
        private String dinhDangTen;
        private List<PortDto> ports;

        public Integer getPhanLoai() {
            return phanLoai;
        }

        public void setPhanLoai(Integer phanLoai) {
            this.phanLoai = phanLoai;
        }

        public Integer getSoCong() {
            return soCong;
        }

        public void setSoCong(Integer soCong) {
            this.soCong = soCong;
        }

        public Integer getBatDauTu() {
            return batDauTu;
        }

        public void setBatDauTu(Integer batDauTu) {
            this.batDauTu = batDauTu;
        }

        public String getDinhDangTen() {
            return dinhDangTen;
        }

        public void setDinhDangTen(String dinhDangTen) {
            this.dinhDangTen = dinhDangTen;
        }

        public List<PortDto> getPorts() {
            return ports;
        }

        public void setPorts(List<PortDto> ports) {
            this.ports = ports;
        }
    }
    public static class PortDto implements Serializable {
        private String portId;
        private String ten;
        private Long chuanDnId;
        private Long loaiNdienId;
        private String daDinhDanh;
        private String ddDinhDanh;
        private Long loaiCongId;
        private String bangThong;
        private Long moduleId;

        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        public String getTen() {
            return ten;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public Long getChuanDnId() {
            return chuanDnId;
        }

        public void setChuanDnId(Long chuanDnId) {
            this.chuanDnId = chuanDnId;
        }

        public Long getLoaiNdienId() {
            return loaiNdienId;
        }

        public void setLoaiNdienId(Long loaiNdienId) {
            this.loaiNdienId = loaiNdienId;
        }

        public String getDaDinhDanh() {
            return daDinhDanh;
        }

        public void setDaDinhDanh(String daDinhDanh) {
            this.daDinhDanh = daDinhDanh;
        }

        public String getDdDinhDanh() {
            return ddDinhDanh;
        }

        public void setDdDinhDanh(String ddDinhDanh) {
            this.ddDinhDanh = ddDinhDanh;
        }

        public Long getLoaiCongId() {
            return loaiCongId;
        }

        public void setLoaiCongId(Long loaiCongId) {
            this.loaiCongId = loaiCongId;
        }

        public String getBangThong() {
            return bangThong;
        }

        public void setBangThong(String bangThong) {
            this.bangThong = bangThong;
        }

        public Long getModuleId() {
            return moduleId;
        }

        public void setModuleId(Long moduleId) {
            this.moduleId = moduleId;
        }
    }

    public static class LineCardsDto implements Serializable {
        private Integer phanLoai;
        private Integer soCong;
        private Integer batDauTu;
        private String dinhDangTen;
        private List<LineCardDto> ports;

        public Integer getPhanLoai() {
            return phanLoai;
        }

        public void setPhanLoai(Integer phanLoai) {
            this.phanLoai = phanLoai;
        }

        public Integer getSoCong() {
            return soCong;
        }

        public void setSoCong(Integer soCong) {
            this.soCong = soCong;
        }

        public Integer getBatDauTu() {
            return batDauTu;
        }

        public void setBatDauTu(Integer batDauTu) {
            this.batDauTu = batDauTu;
        }

        public String getDinhDangTen() {
            return dinhDangTen;
        }

        public void setDinhDangTen(String dinhDangTen) {
            this.dinhDangTen = dinhDangTen;
        }

        public List<LineCardDto> getPorts() {
            return ports;
        }

        public void setPorts(List<LineCardDto> ports) {
            this.ports = ports;
        }
    }

    public static class LineCardDto implements Serializable {
        private String portId;
        private String ten;
        private Long loaiCongId;

        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        public String getTen() {
            return ten;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public Long getLoaiCongId() {
            return loaiCongId;
        }

        public void setLoaiCongId(Long loaiCongId) {
            this.loaiCongId = loaiCongId;
        }
    }
}

