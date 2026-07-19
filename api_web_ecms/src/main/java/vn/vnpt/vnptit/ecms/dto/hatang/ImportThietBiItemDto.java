package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho từng thiết bị khi import")
public class ImportThietBiItemDto {

    @Schema(description = "Số thứ tự")
    @JsonProperty("stt")
    private Number stt;

    @Schema(description = "ID loại thiết bị")
    @JsonProperty("loaithietbi_id")
    private Number loaithietbiId;

    @Schema(description = "Hiệu lực")
    @JsonProperty("hieu_luc")
    private Number hieuLuc;

    @Schema(description = "Số thẻ tài sản")
    @JsonProperty("so_the_ts")
    private String soTheTs;

    @Schema(description = "Mã dự án")
    @JsonProperty("project_code")
    private String projectCode;

    @Schema(description = "Mã tài sản")
    @JsonProperty("ma_ts")
    private String maTs;

    @Schema(description = "Tên thiết bị")
    @JsonProperty("ten")
    private String ten;

    @Schema(description = "ID mạng thiết bị")
    @JsonProperty("mangtbi_id")
    private Number mangtbiId;

    @Schema(description = "ID phân loại thiết bị")
    @JsonProperty("phanloaitb_id")
    private Number phanloaitbId;

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
    @JsonProperty("trang_thai_vh_id")
    private Number trangThaiVhId;

    @Schema(description = "ID hãng sản xuất")
    @JsonProperty("hangsx_id")
    private Number hangsxId;

    @Schema(description = "Năm sản xuất")
    @JsonProperty("namsx")
    private Number namsx;

    @Schema(description = "Công suất định danh")
    @JsonProperty("cs_dinhdanh")
    private Number csDinhdanh;

    @Schema(description = "Dòng điện định danh")
    @JsonProperty("da_dinhdanh")
    private Number daDinhdanh;

    @Schema(description = "Điện áp định danh")
    @JsonProperty("dd_dinhdanh")
    private Number ddDinhdanh;

    @Schema(description = "Loại nguồn điện")
    @JsonProperty("loai_ndien")
    private Number loaiNdien;

    @Schema(description = "Ngày lắp đặt (dd/MM/yyyy)")
    @JsonProperty("ngay_lapdat")
    private String ngayLapdat;

    @Schema(description = "Ngày sử dụng (dd/MM/yyyy)")
    @JsonProperty("ngay_sudung")
    private String ngaySudung;

    @Schema(description = "Số CPU")
    @JsonProperty("cpu")
    private Number cpu;

    @Schema(description = "Dung lượng RAM")
    @JsonProperty("ram")
    private Number ram;

    @Schema(description = "Dung lượng HDD")
    @JsonProperty("hdd")
    private Number hdd;

    @Schema(description = "Hệ số CosPhi")
    @JsonProperty("hs_cosphi")
    private Number hsCosphi;

    @Schema(description = "Tỷ lệ sử dụng")
    @JsonProperty("tyle_sd")
    private Number tyleSd;

    @Schema(description = "Loại máy (1: Hạ áp; 2: Tăng áp)")
    @JsonProperty("loai_may")
    private Number loaimay;

    @Schema(description = "Số FAN")
    @JsonProperty("fan")
    private Number fan;

    @Schema(description = "Dung lượng SSD")
    @JsonProperty("ssd")
    private Number ssd;

    @Schema(description = "Dung lượng NVME")
    @JsonProperty("nvme")
    private Number nvme;

    @Schema(description = "Số lượng cổng nguồn")
    @JsonProperty("sl_congnguon")
    private Number slCongnguon;

    @Schema(description = "Số lượng cổng dữ liệu")
    @JsonProperty("sl_congdl")
    private Number slCongdl;

    @Schema(description = "Số lượng cổng điều khiển")
    @JsonProperty("sl_congdkh")
    private Number slCongdkh;

    @Schema(description = "Số lượng cổng nguồn vào")
    @JsonProperty("sl_congnguon_vao")
    private Number slCongnguonVao;

    @Schema(description = "Số lượng cổng nguồn ra")
    @JsonProperty("sl_congnguon_ra")
    private Number slCongnguonRa;

    @Schema(description = "Số lượng khe cắm blade")
    @JsonProperty("sl_khe_blade")
    private Number slKheBlade;

    @Schema(description = "Không gian lắp đặt (1: Front, 2: Back)")
    @JsonProperty("khong_gian_lap_dat")
    private Number khongGianLapDat;

    @Schema(description = "Mã IDC (ví dụ: 'NTL')")
    @JsonProperty("idc_id")
    private String idcId;

    @Schema(description = "Mã Rack (ví dụ: 'BV52')")
    @JsonProperty("rack_id")
    private String rackId;

    @Schema(description = "Unit index dạng chuỗi (ví dụ: '5-20' hoặc '3'), sẽ được tách thành up_unit_id và down_unit_id")
    @JsonProperty("unit_index")
    private String unitIndex;

    // Computed fields after parsing unit_index
    private Number upUnitId;
    private Number downUnitId;

    @Schema(description = "Lỗi validate (nếu có)")
    @JsonProperty("validation_error")
    private String validationError;

    @Schema(description = "Mô tả")
    @JsonProperty("mota")
    private String mota;

    @Schema(description = "Mã tầng/mặt sàn")
    @JsonProperty("matsan_id")
    private String matsanId;

    @Schema(description = "Mã phòng/zone")
    @JsonProperty("phong_id")
    private String phongId;

    @Schema(description = "GPU")
    @JsonProperty("gpu")
    private Number gpu;

    @Schema(description = "Tọa độ X mặt sàn")
    @JsonProperty("toado_x")
    private Number toadoX;

    @Schema(description = "Tọa độ Y mặt sàn")
    @JsonProperty("toado_y")
    private Number toadoY;

    @Schema(description = "Mặt rack unit (NULL: 2 mặt, 1: mặt trước, 2: mặt sau)")
    @JsonProperty("mat_rackunit")
    private Number matRackunit;

    @Schema(description = "Cạnh rack unit (NULL: 2 cạnh, 1: cạnh phải, 2: cạnh trái)")
    @JsonProperty("canh_rackunit")
    private Number canhRackunit;

    @Schema(description = "Kiểu lắp đặt (1: dọc, 2: ngang)")
    @JsonProperty("kieu_ld")
    private Number kieuLd;

    @Schema(description = "ID đơn vị quản lý")
    @JsonProperty("donviqly_id")
    private Number donviqlyId;

    @Schema(description = "Mã vật tư")
    @JsonProperty("ma_vtu")
    private String maVtu;

    @Schema(description = "Tên nhân viên quản lý")
    @JsonProperty("nguoiqly_id")
    private String nguoiqlyName;

    @Schema(description = "Mã thiết bị Server Blade")
    @JsonProperty("ma_tbi_server_blade")
    private String maTbiServerBlade;

    @Schema(description = "Mã thiết bị tủ đĩa")
    @JsonProperty("ma_tbi_tu_dia")
    private String maTbiTuDia;

    @Schema(description = "Mã thiết bị network")
    @JsonProperty("ma_tbi_network")
    private String maTbiNetwork;

    @Schema(description = "ID Chủ quản")
    @JsonProperty("chuquan_id")
    private Number chuquanId;

    // Computed field after resolving parent device
    private Number thietbichaId;

    // Computed field after resolving nguoiqlyName
    private Number resolvedNguoiqlyId;

    // Getters and Setters
    public Number getStt() {
        return stt;
    }

    public void setStt(Number stt) {
        this.stt = stt;
    }

    public Number getLoaithietbiId() {
        return loaithietbiId;
    }

    public void setLoaithietbiId(Number loaithietbiId) {
        this.loaithietbiId = loaithietbiId;
    }

    public Number getHieuLuc() {
        return hieuLuc;
    }

    public void setHieuLuc(Number hieuLuc) {
        this.hieuLuc = hieuLuc;
    }

    public String getSoTheTs() {
        return soTheTs;
    }

    public void setSoTheTs(String soTheTs) {
        this.soTheTs = soTheTs;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getMaTs() {
        return maTs;
    }

    public void setMaTs(String maTs) {
        this.maTs = maTs;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Number getMangtbiId() {
        return mangtbiId;
    }

    public void setMangtbiId(Number mangtbiId) {
        this.mangtbiId = mangtbiId;
    }

    public Number getPhanloaitbId() {
        return phanloaitbId;
    }

    public void setPhanloaitbId(Number phanloaitbId) {
        this.phanloaitbId = phanloaitbId;
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

    public Number getTrangThaiVhId() {
        return trangThaiVhId;
    }

    public void setTrangThaiVhId(Number trangThaiVhId) {
        this.trangThaiVhId = trangThaiVhId;
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

    public Number getCsDinhdanh() {
        return csDinhdanh;
    }

    public void setCsDinhdanh(Number csDinhdanh) {
        this.csDinhdanh = csDinhdanh;
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

    public String getNgayLapdat() {
        return ngayLapdat;
    }

    public void setNgayLapdat(String ngayLapdat) {
        this.ngayLapdat = ngayLapdat;
    }

    public String getNgaySudung() {
        return ngaySudung;
    }

    public void setNgaySudung(String ngaySudung) {
        this.ngaySudung = ngaySudung;
    }

    public Number getCpu() {
        return cpu;
    }

    public void setCpu(Number cpu) {
        this.cpu = cpu;
    }

    public Number getRam() {
        return ram;
    }

    public void setRam(Number ram) {
        this.ram = ram;
    }

    public Number getHdd() {
        return hdd;
    }

    public void setHdd(Number hdd) {
        this.hdd = hdd;
    }

    public Number getFan() {
        return fan;
    }

    public void setFan(Number fan) {
        this.fan = fan;
    }

    public Number getSsd() {
        return ssd;
    }

    public void setSsd(Number ssd) {
        this.ssd = ssd;
    }

    public Number getNvme() {
        return nvme;
    }

    public void setNvme(Number nvme) {
        this.nvme = nvme;
    }

    public Number getSlCongnguon() {
        return slCongnguon;
    }

    public void setSlCongnguon(Number slCongnguon) {
        this.slCongnguon = slCongnguon;
    }

    public Number getSlCongdl() {
        return slCongdl;
    }

    public void setSlCongdl(Number slCongdl) {
        this.slCongdl = slCongdl;
    }

    public Number getSlCongdkh() {
        return slCongdkh;
    }

    public void setSlCongdkh(Number slCongdkh) {
        this.slCongdkh = slCongdkh;
    }

    public Number getSlCongnguonVao() {
        return slCongnguonVao;
    }

    public void setSlCongnguonVao(Number slCongnguonVao) {
        this.slCongnguonVao = slCongnguonVao;
    }

    public Number getSlCongnguonRa() {
        return slCongnguonRa;
    }

    public void setSlCongnguonRa(Number slCongnguonRa) {
        this.slCongnguonRa = slCongnguonRa;
    }

    public Number getSlKheBlade() {
        return slKheBlade;
    }

    public void setSlKheBlade(Number slKheBlade) {
        this.slKheBlade = slKheBlade;
    }

    public Number getKhongGianLapDat() {
        return khongGianLapDat;
    }

    public void setKhongGianLapDat(Number khongGianLapDat) {
        this.khongGianLapDat = khongGianLapDat;
    }

    public String getIdcId() {
        return idcId;
    }

    public void setIdcId(String idcId) {
        this.idcId = idcId;
    }

    public String getRackId() {
        return rackId;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getUnitIndex() {
        return unitIndex;
    }

    public void setUnitIndex(String unitIndex) {
        this.unitIndex = unitIndex;
    }

    public Number getUpUnitId() {
        return upUnitId;
    }

    public void setUpUnitId(Number upUnitId) {
        this.upUnitId = upUnitId;
    }

    public Number getDownUnitId() {
        return downUnitId;
    }

    public void setDownUnitId(Number downUnitId) {
        this.downUnitId = downUnitId;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public Number getGpu() {
        return gpu;
    }

    public void setGpu(Number gpu) {
        this.gpu = gpu;
    }

    public String getMatsanId() {
        return matsanId;
    }

    public void setMatsanId(String matsanId) {
        this.matsanId = matsanId;
    }

    public String getPhongId() {
        return phongId;
    }

    public void setPhongId(String phongId) {
        this.phongId = phongId;
    }

    public Number getHsCosphi() {
        return hsCosphi;
    }

    public void setHsCosphi(Number hsCosphi) {
        this.hsCosphi = hsCosphi;
    }

    public Number getTyleSd() {
        return tyleSd;
    }

    public void setTyleSd(Number tyleSd) {
        this.tyleSd = tyleSd;
    }

    public Number getLoaimay() {
        return loaimay;
    }

    public void setLoaimay(Number loaimay) {
        this.loaimay = loaimay;
    }

    public Number getToadoX() {
        return toadoX;
    }

    public void setToadoX(Number toadoX) {
        this.toadoX = toadoX;
    }

    public Number getToadoY() {
        return toadoY;
    }

    public void setToadoY(Number toadoY) {
        this.toadoY = toadoY;
    }

    public Number getMatRackunit() {
        return matRackunit;
    }

    public void setMatRackunit(Number matRackunit) {
        this.matRackunit = matRackunit;
    }

    public Number getCanhRackunit() {
        return canhRackunit;
    }

    public void setCanhRackunit(Number canhRackunit) {
        this.canhRackunit = canhRackunit;
    }

    public Number getKieuLd() {
        return kieuLd;
    }

    public void setKieuLd(Number kieuLd) {
        this.kieuLd = kieuLd;
    }

    public Number getDonviqlyId() {
        return donviqlyId;
    }

    public void setDonviqlyId(Number donviqlyId) {
        this.donviqlyId = donviqlyId;
    }

    public String getMaVtu() {
        return maVtu;
    }

    public void setMaVtu(String maVtu) {
        this.maVtu = maVtu;
    }

    public String getNguoiqlyName() {
        return nguoiqlyName;
    }

    public void setNguoiqlyName(String nguoiqlyName) {
        this.nguoiqlyName = nguoiqlyName;
    }

    public Number getResolvedNguoiqlyId() {
        return resolvedNguoiqlyId;
    }

    public void setResolvedNguoiqlyId(Number resolvedNguoiqlyId) {
        this.resolvedNguoiqlyId = resolvedNguoiqlyId;
    }

    public String getMaTbiServerBlade() {
        return maTbiServerBlade;
    }

    public void setMaTbiServerBlade(String maTbiServerBlade) {
        this.maTbiServerBlade = maTbiServerBlade;
    }

    public String getMaTbiTuDia() {
        return maTbiTuDia;
    }

    public void setMaTbiTuDia(String maTbiTuDia) {
        this.maTbiTuDia = maTbiTuDia;
    }

    public String getMaTbiNetwork() {
        return maTbiNetwork;
    }

    public void setMaTbiNetwork(String maTbiNetwork) {
        this.maTbiNetwork = maTbiNetwork;
    }

    public Number getChuquanId() {
        return chuquanId;
    }

    public void setChuquanId(Number chuquanId) {
        this.chuquanId = chuquanId;
    }

    public Number getThietbichaId() {
        return thietbichaId;
    }

    public void setThietbichaId(Number thietbichaId) {
        this.thietbichaId = thietbichaId;
    }

    /**
     * Parse unit_index string and set upUnitId and downUnitId
     * Supports formats: "5" (single unit) or "5-20" (range)
     * Example: "5-20" -> upUnitId=5, downUnitId=20
     */
    public void parseAndSetUnitIds() {
        if (unitIndex == null || unitIndex.trim().isEmpty()) {
            return;
        }
        
        String trimmedUnit = unitIndex.trim();
        
        if (trimmedUnit.contains("-")) {
            // Format: "5-20" -> upUnitId=5, downUnitId=20
            String[] parts = trimmedUnit.split("-");
            if (parts.length == 2) {
                try {
                    int start = Integer.parseInt(parts[0].trim());
                    int end = Integer.parseInt(parts[1].trim());
                    // Upunit always bigger than down unit
                    this.upUnitId = Math.max(start, end);
                    this.downUnitId = Math.min(start, end);
                } catch (NumberFormatException e) {
                    // Keep null if parsing fails
                }
            }
        } else {
            // Single value: "5" -> upUnitId=5, downUnitId=5
            try {
                int singleUnit = Integer.parseInt(trimmedUnit);
                this.upUnitId = singleUnit;
                this.downUnitId = singleUnit;
            } catch (NumberFormatException e) {
                // Keep null if parsing fails
            }
        }
    }
}
