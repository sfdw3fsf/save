package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;
import java.util.List;

public class CongKetNoiThietBiDtoV2 implements Serializable {

    private Long thietBiId;
    private PortsDto portsDto;
    private LineCardsDto lineCardsDto;

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public PortsDto getPortsDto() {
        return portsDto;
    }

    public void setPortsDto(PortsDto portsDto) {
        this.portsDto = portsDto;
    }

    public LineCardsDto getLineCardsDto() {
        return lineCardsDto;
    }

    public void setLineCardsDto(LineCardsDto lineCardsDto) {
        this.lineCardsDto = lineCardsDto;
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
        private Integer soThuTu;
        private String ten;
        private Long chuanDnId;
        private Long ttPortId;
        private Long trangThaiKnId;
        private Long thietBiKnId;
        private String diaChiIp;
        private Long loaiNdienId;
        private String daDinhDanh;
        private String ddDinhDanh;
        private Long loaiCongId;
        private String bangThong;
        private Long moduleId;
        private Long mucDichSdId;

        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        public Integer getSoThuTu() {
            return soThuTu;
        }

        public void setSoThuTu(Integer soThuTu) {
            this.soThuTu = soThuTu;
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

        public Long getTtPortId() {
            return ttPortId;
        }

        public void setTtPortId(Long ttPortId) {
            this.ttPortId = ttPortId;
        }

        public Long getTrangThaiKnId() {
            return trangThaiKnId;
        }

        public void setTrangThaiKnId(Long trangThaiKnId) {
            this.trangThaiKnId = trangThaiKnId;
        }

        public Long getThietBiKnId() {
            return thietBiKnId;
        }

        public void setThietBiKnId(Long thietBiKnId) {
            this.thietBiKnId = thietBiKnId;
        }

        public String getDiaChiIp() {
            return diaChiIp;
        }

        public void setDiaChiIp(String diaChiIp) {
            this.diaChiIp = diaChiIp;
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

        public Long getMucDichSdId() {
            return mucDichSdId;
        }

        public void setMucDichSdId(Long mucDichSdId) {
            this.mucDichSdId = mucDichSdId;
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
        private Integer soThuTu;
        private String ten;
        private Long trangThaiKnId;
        private Long thietBiKnId;
        private Long loaiCongId;

        public String getPortId() {
            return portId;
        }

        public void setPortId(String portId) {
            this.portId = portId;
        }

        public Integer getSoThuTu() {
            return soThuTu;
        }

        public void setSoThuTu(Integer soThuTu) {
            this.soThuTu = soThuTu;
        }

        public String getTen() {
            return ten;
        }

        public void setTen(String ten) {
            this.ten = ten;
        }

        public Long getTrangThaiKnId() {
            return trangThaiKnId;
        }

        public void setTrangThaiKnId(Long trangThaiKnId) {
            this.trangThaiKnId = trangThaiKnId;
        }

        public Long getThietBiKnId() {
            return thietBiKnId;
        }

        public void setThietBiKnId(Long thietBiKnId) {
            this.thietBiKnId = thietBiKnId;
        }

        public Long getLoaiCongId() {
            return loaiCongId;
        }

        public void setLoaiCongId(Long loaiCongId) {
            this.loaiCongId = loaiCongId;
        }
    }

}