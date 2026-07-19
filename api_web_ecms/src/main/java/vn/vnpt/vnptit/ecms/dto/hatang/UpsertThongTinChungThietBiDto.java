package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho thông tin chung thiết bị (Insert/Update)")
public class UpsertThongTinChungThietBiDto {

    @Schema(description = "ID thiết bị (cho update, null cho insert)")
    @JsonProperty("id")
    private Number id;

    @Schema(description = "ID mạng thiết bị")
    @JsonProperty("mangtbi_id")
    private Number mangtbiId;

    @Schema(description = "ID hệ thống thiết bị")
    @JsonProperty("hethongtbi_id")
    private Number hethongtbiId;

    @Schema(description = "ID loại thiết bị")
    @JsonProperty("loaithietbi_id")
    private Number loaithietbiId;

    @Schema(description = "ID chủng loại thiết bị")
    @JsonProperty("chungloaithietbi_id")
    private Number chungloaithietbiId;

    @Schema(description = "ID phân loại thiết bị")
    @JsonProperty("phanloaitb_id")
    private Number phanloaitbId;

    @Schema(description = "ID thiết bị cha")
    @JsonProperty("thietbicha_id")
    private Number thietbichaId;

    @Schema(description = "ID chủ quản")
    @JsonProperty("chuquan_id")
    private Number chuquanId;

    @Schema(description = "Mã thiết bị")
    @JsonProperty("mathietbi")
    private String mathietbi;

    @Schema(description = "Mã thiết bị infra")
    @JsonProperty("mathietbi_infra")
    private String mathietbiInfra;

    @Schema(description = "Tên thiết bị")
    @JsonProperty("ten")
    private String ten;

    @Schema(description = "IP quản trị")
    @JsonProperty("ip_quantri")
    private String ipQuantri;

    @Schema(description = "Ký hiệu")
    @JsonProperty("kyhieu")
    private String kyhieu;

    @Schema(description = "Số serial")
    @JsonProperty("serial_number")
    private String serialNumber;

    @Schema(description = "Model")
    @JsonProperty("model")
    private String model;

    @Schema(description = "Part number")
    @JsonProperty("part_number")
    private String partNumber;

    @Schema(description = "ID trạng thái vận hành")
    @JsonProperty("trangthaivh_id")
    private Number trangthaivhId;

    @Schema(description = "ID loại tủ đĩa")
    @JsonProperty("loaitudia_id")
    private Number loaitudiaId;

    @Schema(description = "ID thiết bị IDC")
    @JsonProperty("thietbi_idc")
    private Number thietbiIdc;

    @Schema(description = "Hiệu lực")
    @JsonProperty("hieu_luc")
    private Number hieuLuc;

    @Schema(description = "Full depth")
    @JsonProperty("full_depth")
    private Number fullDepth;

    @Schema(description = "Ghi chú")
    @JsonProperty("ghi_chu")
    private String ghiChu;

    @Schema(description = "ID hãng sản xuất")
    @JsonProperty("hangsx_id")
    private Number hangsxId;

    @Schema(description = "Năm sản xuất")
    @JsonProperty("namsx")
    private Number namsx;

    @Schema(description = "Công suất định danh")
    @JsonProperty("cs_dinhdanh")
    private String csDinhdanh;

    @Schema(description = "ID vai trò thiết bị")
    @JsonProperty("vaitrothietbi_id")
    private Number vaitrothietbiId;

    @Schema(description = "ID nhà cung cấp")
    @JsonProperty("nhacc_id")
    private Number nhaccId;

    @Schema(description = "Công suất thực tế")
    @JsonProperty("cs_thucte")
    private String csThucte;

    @Schema(description = "ID hệ điều hành")
    @JsonProperty("hedieuhanh_id")
    private Number hedieuhanhId;

    @Schema(description = "Số lượng rack unit")
    @JsonProperty("sl_rackunit")
    private Number slRackunit;

    @Schema(description = "Is interface")
    @JsonProperty("is_interface")
    private Number isInterface;

    @Schema(description = "Is power")
    @JsonProperty("is_power")
    private Number isPower;

    @Schema(description = "Is console")
    @JsonProperty("is_console")
    private Number isConsole;

    @Schema(description = "Người cập nhật")
    @JsonProperty("nguoi_cn")
    private String nguoiCn;

    @Schema(description = "ID tỉnh")
    @JsonProperty("tinh_id")
    private Number tinhId;

    @Schema(description = "ID IDC")
    @JsonProperty("idc_id")
    private Number idcId;

    @Schema(description = "Chiều dài")
    @JsonProperty("chieu_dai")
    private Number chieuDai;

    @Schema(description = "Chiều rộng")
    @JsonProperty("chieu_rong")
    private Number chieuRong;

    @Schema(description = "Chiều cao")
    @JsonProperty("chieu_cao")
    private Number chieuCao;

    @Schema(description = "Cấu hình")
    @JsonProperty("cauhinh")
    private String cauhinh;

    @Schema(description = "Tỷ lệ sử dụng")
    @JsonProperty("tyle_sd")
    private Number tyleSd;

    @Schema(description = "Hệ số cos phi")
    @JsonProperty("hs_cosphi")
    private Number hsCosphi;

    @Schema(description = "Điện danh định thực tế")
    @JsonProperty("dd_thucte")
    private Number ddThucte;

    @Schema(description = "Loại máy (1: Hạ áp; 2: Tăng áp)")
    @JsonProperty("loaimay")
    private Number loaimay;

    @Schema(description = "Dòng định danh")
    @JsonProperty("da_dinhdanh")
    private Number daDinhdanh;

    @Schema(description = "Điện định danh")
    @JsonProperty("dd_dinhdanh")
    private Number ddDinhdanh;

    @Schema(description = "Loại nguồn điện")
    @JsonProperty("loai_ndien")
    private Number loaiNdien;

    @Schema(description = "Kiểu lắp đặt (1: dọc, 2: ngang)")
    @JsonProperty("kieu_ld")
    private Number kieuLd;

    // Getters and Setters
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Number getMangtbiId() {
        return mangtbiId;
    }

    public void setMangtbiId(Number mangtbiId) {
        this.mangtbiId = mangtbiId;
    }

    public Number getHethongtbiId() {
        return hethongtbiId;
    }

    public void setHethongtbiId(Number hethongtbiId) {
        this.hethongtbiId = hethongtbiId;
    }

    public Number getLoaithietbiId() {
        return loaithietbiId;
    }

    public void setLoaithietbiId(Number loaithietbiId) {
        this.loaithietbiId = loaithietbiId;
    }

    public Number getChungloaithietbiId() {
        return chungloaithietbiId;
    }

    public void setChungloaithietbiId(Number chungloaithietbiId) {
        this.chungloaithietbiId = chungloaithietbiId;
    }

    public Number getPhanloaitbId() {
        return phanloaitbId;
    }

    public void setPhanloaitbId(Number phanloaitbId) {
        this.phanloaitbId = phanloaitbId;
    }

    public Number getThietbichaId() {
        return thietbichaId;
    }

    public void setThietbichaId(Number thietbichaId) {
        this.thietbichaId = thietbichaId;
    }

    public Number getChuquanId() {
        return chuquanId;
    }

    public void setChuquanId(Number chuquanId) {
        this.chuquanId = chuquanId;
    }

    public String getMathietbi() {
        return mathietbi;
    }

    public void setMathietbi(String mathietbi) {
        this.mathietbi = mathietbi;
    }

    public String getMathietbiInfra() {
        return mathietbiInfra;
    }

    public void setMathietbiInfra(String mathietbiInfra) {
        this.mathietbiInfra = mathietbiInfra;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getIpQuantri() {
        return ipQuantri;
    }

    public void setIpQuantri(String ipQuantri) {
        this.ipQuantri = ipQuantri;
    }

    public String getKyhieu() {
        return kyhieu;
    }

    public void setKyhieu(String kyhieu) {
        this.kyhieu = kyhieu;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Number getTrangthaivhId() {
        return trangthaivhId;
    }

    public void setTrangthaivhId(Number trangthaivhId) {
        this.trangthaivhId = trangthaivhId;
    }

    public Number getLoaitudiaId() {
        return loaitudiaId;
    }

    public void setLoaitudiaId(Number loaitudiaId) {
        this.loaitudiaId = loaitudiaId;
    }

    public Number getThietbiIdc() {
        return thietbiIdc;
    }

    public void setThietbiIdc(Number thietbiIdc) {
        this.thietbiIdc = thietbiIdc;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Number hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public Number getFullDepth() {
        return fullDepth;
    }

    public void setFullDepth(Number fullDepth) {
        this.fullDepth = fullDepth;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Number getHangsxId() {
        return hangsxId;
    }

    public void setHangsxId(Number hangsxId) {
        this.hangsxId = hangsxId;
    }

    public Number getNamsx() {
        return namsx;
    }

    public void setNamsx(Number namsx) {
        this.namsx = namsx;
    }

    public String getCsDinhdanh() {
        return csDinhdanh;
    }

    public void setCsDinhdanh(String csDinhdanh) {
        this.csDinhdanh = csDinhdanh;
    }

    public Number getVaitrothietbiId() {
        return vaitrothietbiId;
    }

    public void setVaitrothietbiId(Number vaitrothietbiId) {
        this.vaitrothietbiId = vaitrothietbiId;
    }

    public Number getNhaccId() {
        return nhaccId;
    }

    public void setNhaccId(Number nhaccId) {
        this.nhaccId = nhaccId;
    }

    public String getCsThucte() {
        return csThucte;
    }

    public void setCsThucte(String csThucte) {
        this.csThucte = csThucte;
    }

    public Number getHedieuhanhId() {
        return hedieuhanhId;
    }

    public void setHedieuhanhId(Number hedieuhanhId) {
        this.hedieuhanhId = hedieuhanhId;
    }

    public Number getSlRackunit() {
        return slRackunit;
    }

    public void setSlRackunit(Number slRackunit) {
        this.slRackunit = slRackunit;
    }

    public Number getIsInterface() {
        return isInterface;
    }

    public void setIsInterface(Number isInterface) {
        this.isInterface = isInterface;
    }

    public Number getIsPower() {
        return isPower;
    }

    public void setIsPower(Number isPower) {
        this.isPower = isPower;
    }

    public Number getIsConsole() {
        return isConsole;
    }

    public void setIsConsole(Number isConsole) {
        this.isConsole = isConsole;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public Number getTinhId() {
        return tinhId;
    }

    public void setTinhId(Number tinhId) {
        this.tinhId = tinhId;
    }

    public Number getIdcId() {
        return idcId;
    }

    public void setIdcId(Number idcId) {
        this.idcId = idcId;
    }

    public Number getChieuDai() {
        return chieuDai;
    }

    public void setChieuDai(Number chieuDai) {
        this.chieuDai = chieuDai;
    }

    public Number getChieuRong() {
        return chieuRong;
    }

    public void setChieuRong(Number chieuRong) {
        this.chieuRong = chieuRong;
    }

    public Number getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(Number chieuCao) {
        this.chieuCao = chieuCao;
    }

    public String getCauhinh() {
        return cauhinh;
    }

    public void setCauhinh(String cauhinh) {
        this.cauhinh = cauhinh;
    }

    public Number getTyleSd() {
        return tyleSd;
    }

    public void setTyleSd(Number tyleSd) {
        this.tyleSd = tyleSd;
    }

    public Number getHsCosphi() {
        return hsCosphi;
    }

    public void setHsCosphi(Number hsCosphi) {
        this.hsCosphi = hsCosphi;
    }

    public Number getDdThucte() {
        return ddThucte;
    }

    public void setDdThucte(Number ddThucte) {
        this.ddThucte = ddThucte;
    }

    public Number getLoaimay() {
        return loaimay;
    }

    public void setLoaimay(Number loaimay) {
        this.loaimay = loaimay;
    }

    public Number getDaDinhdanh() {
        return daDinhdanh;
    }

    public void setDaDinhdanh(Number daDinhdanh) {
        this.daDinhdanh = daDinhdanh;
    }

    public Number getDdDinhdanh() {
        return ddDinhdanh;
    }

    public void setDdDinhdanh(Number ddDinhdanh) {
        this.ddDinhdanh = ddDinhdanh;
    }

    public Number getLoaiNdien() {
        return loaiNdien;
    }

    public void setLoaiNdien(Number loaiNdien) {
        this.loaiNdien = loaiNdien;
    }

    public Number getKieuLd() {
        return kieuLd;
    }

    public void setKieuLd(Number kieuLd) {
        this.kieuLd = kieuLd;
    }
}

