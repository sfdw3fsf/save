package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

public class ThongTinThietBiV2Dto {
    //    Thông tin chung
    private Number id = 0;
    @NotEmpty(message = ModelValidation.NOT_EMPTY)
    private String ten;
    private String kyhieu;
    private String mathietbi_infra;
    private Number thietbicha_id;
    private Number loaithietbi_id;
    private Number loaikhachhang;
    private String serial_number;
    private String model;
    private String part_number;
    private Number hsm;
    private Number hieu_luc;
    private String ghi_chu;
    private String loaitudia_id;
    private int[] ip_ids;
    private Number chungloaithietbi_id;

    //    Đặc tính
    private Number hangsx_id;
    private Number namsx;
    private Number nhacungcap_id;
    private Number cs_dinhdanh;
    private Number cs_thucte;
    private Number hedieuhanh_id;
    private Number trangthai_sd;
    private LocalDate ngay_lapdat;
    private LocalDate ngay_baohanh;
    private LocalDate ngay_sudung;
    //  Thiet bi May chu
    private Number cpu_sokhe;
    private Number cpu_dacam;
    private Number cpu_total;
    private Number hdd_sokhe;
    private Number hdd_dacam;
    private Number hdd_total;
    private Number ram_sokhe;
    private Number ram_dacam;
    private Number ram_total;
    //  Thiet bi phu tro
    private Number psu_qty_in;
    private Number psu_qty_out;
    private Number cau_hinh;
    //  Thiet bi tu dia
    private Number bodieukhien_qty;
    private Number bodieukhien_dau;
    private String bodieukhien_fname;
    private Number khaydia_qty;
    private Number khaydia_dau;
    private String khaydia_fname;
    private int[] vaitrothietbi_ids;

    // Năng lực lưu trữ
    private Number hdd_tong;
    private Number hdd_nangluc;
    private Number hdd_capphat;
    private Number ssd_tong;
    private Number ssd_nangluc;
    private Number ssd_capphat;
    private Number iops_tong;
    private Number iops_nangluc;
    private Number iops_capphat;
    private Number mem_cache;
    private Number flash_cache;

    //    Vị trí lắp đặt TB
    private Number idc_id;
    private Number building_id;
    private Number floor_id;
    private Number zone_id;
    private Number rack_id;
    private Number unit_qty;
    private Number up_unit_id;
    private Number down_unit_id;

    //    Thông tin kiểm kê tài sản
    private Number project_id;
    private String project_code;
    private String so_the_ts;
    private Number donviqly_id;
    private Number phongbanqly_id;
    private Number nguoiqly_id;

    //    Thông tin cổng TB
    //    Interface
    private Number so_linecard;
    private Number so_port_interface;
    private Number so_interface;
    private Number interface_start;
    private String interface_fname;

    //    Power
    private Number so_power;
    private Number so_port_power;
    private Number so_powerslot;
    private Number power_start;
    private String power_fname;

    //    Console
    private Number so_controlcard;
    private Number so_port_console;
    private Number so_console;
    private Number console_start;
    private String console_fname;

    // Phan loai card
    private Number is_interface;
    private Number is_power;
    private Number is_console;
    private Number is_controller;
    private Number is_khaydia;

    // Khai bao port va card module
    private ThietBiPortKhaiBao_DTO[] khaibao_port;
    private List<ThongTinThietBiV2Dto> cards;

    // Getters and Setters
    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public @NotEmpty(message = ModelValidation.NOT_EMPTY) String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getKyhieu() {
        return kyhieu;
    }

    public void setKyhieu(String kyhieu) {
        this.kyhieu = kyhieu;
    }

    public String getMathietbi_infra() {
        return mathietbi_infra;
    }

    public void setMathietbi_infra(String mathietbi_infra) {
        this.mathietbi_infra = mathietbi_infra;
    }

    public Number getThietbicha_id() {
        return thietbicha_id;
    }

    public void setThietbicha_id(Number thietbicha_id) {
        this.thietbicha_id = thietbicha_id;
    }

    public Number getLoaithietbi_id() {
        return loaithietbi_id;
    }

    public void setLoaithietbi_id(Number loaithietbi_id) {
        this.loaithietbi_id = loaithietbi_id;
    }

    public Number getLoaikhachhang() {
        return loaikhachhang;
    }

    public void setLoaikhachhang(Number loaikhachhang) {
        this.loaikhachhang = loaikhachhang;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public Number getHsm() {
        return hsm;
    }

    public void setHsm(Number hsm) {
        this.hsm = hsm;
    }

    public Number getHieu_luc() {
        return hieu_luc;
    }

    public void setHieu_luc(Number hieu_luc) {
        this.hieu_luc = hieu_luc;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public String getLoaitudia_id() {
        return loaitudia_id;
    }

    public void setLoaitudia_id(String loaitudia_id) {
        this.loaitudia_id = loaitudia_id;
    }

    public int[] getIp_ids() {
        return ip_ids;
    }

    public void setIp_ids(int[] ip_ids) {
        this.ip_ids = ip_ids;
    }

    public Number getChungloaithietbi_id() {
        return chungloaithietbi_id;
    }

    public void setChungloaithietbi_id(Number chungloaithietbi_id) {
        this.chungloaithietbi_id = chungloaithietbi_id;
    }

    public Number getHangsx_id() {
        return hangsx_id;
    }

    public void setHangsx_id(Number hangsx_id) {
        this.hangsx_id = hangsx_id;
    }

    public Number getNamsx() {
        return namsx;
    }

    public void setNamsx(Number namsx) {
        this.namsx = namsx;
    }

    public Number getNhacungcap_id() {
        return nhacungcap_id;
    }

    public void setNhacungcap_id(Number nhacungcap_id) {
        this.nhacungcap_id = nhacungcap_id;
    }

    public Number getCs_dinhdanh() {
        return cs_dinhdanh;
    }

    public void setCs_dinhdanh(Number cs_dinhdanh) {
        this.cs_dinhdanh = cs_dinhdanh;
    }

    public Number getCs_thucte() {
        return cs_thucte;
    }

    public void setCs_thucte(Number cs_thucte) {
        this.cs_thucte = cs_thucte;
    }

    public Number getHedieuhanh_id() {
        return hedieuhanh_id;
    }

    public void setHedieuhanh_id(Number hedieuhanh_id) {
        this.hedieuhanh_id = hedieuhanh_id;
    }

    public Number getTrangthai_sd() {
        return trangthai_sd;
    }

    public void setTrangthai_sd(Number trangthai_sd) {
        this.trangthai_sd = trangthai_sd;
    }

    public LocalDate getNgay_lapdat() {
        return ngay_lapdat;
    }

    public void setNgay_lapdat(LocalDate ngay_lapdat) {
        this.ngay_lapdat = ngay_lapdat;
    }

    public LocalDate getNgay_baohanh() {
        return ngay_baohanh;
    }

    public void setNgay_baohanh(LocalDate ngay_baohanh) {
        this.ngay_baohanh = ngay_baohanh;
    }

    public LocalDate getNgay_sudung() {
        return ngay_sudung;
    }

    public void setNgay_sudung(LocalDate ngay_sudung) {
        this.ngay_sudung = ngay_sudung;
    }

    public Number getCpu_sokhe() {
        return cpu_sokhe;
    }

    public void setCpu_sokhe(Number cpu_sokhe) {
        this.cpu_sokhe = cpu_sokhe;
    }

    public Number getCpu_dacam() {
        return cpu_dacam;
    }

    public void setCpu_dacam(Number cpu_dacam) {
        this.cpu_dacam = cpu_dacam;
    }

    public Number getCpu_total() {
        return cpu_total;
    }

    public void setCpu_total(Number cpu_total) {
        this.cpu_total = cpu_total;
    }

    public Number getHdd_sokhe() {
        return hdd_sokhe;
    }

    public void setHdd_sokhe(Number hdd_sokhe) {
        this.hdd_sokhe = hdd_sokhe;
    }

    public Number getHdd_dacam() {
        return hdd_dacam;
    }

    public void setHdd_dacam(Number hdd_dacam) {
        this.hdd_dacam = hdd_dacam;
    }

    public Number getHdd_total() {
        return hdd_total;
    }

    public void setHdd_total(Number hdd_total) {
        this.hdd_total = hdd_total;
    }

    public Number getRam_sokhe() {
        return ram_sokhe;
    }

    public void setRam_sokhe(Number ram_sokhe) {
        this.ram_sokhe = ram_sokhe;
    }

    public Number getRam_dacam() {
        return ram_dacam;
    }

    public void setRam_dacam(Number ram_dacam) {
        this.ram_dacam = ram_dacam;
    }

    public Number getRam_total() {
        return ram_total;
    }

    public void setRam_total(Number ram_total) {
        this.ram_total = ram_total;
    }

    public Number getPsu_qty_in() {
        return psu_qty_in;
    }

    public void setPsu_qty_in(Number psu_qty_in) {
        this.psu_qty_in = psu_qty_in;
    }

    public Number getPsu_qty_out() {
        return psu_qty_out;
    }

    public void setPsu_qty_out(Number psu_qty_out) {
        this.psu_qty_out = psu_qty_out;
    }

    public Number getCau_hinh() {
        return cau_hinh;
    }

    public void setCau_hinh(Number cau_hinh) {
        this.cau_hinh = cau_hinh;
    }

    public Number getBodieukhien_qty() {
        return bodieukhien_qty;
    }

    public void setBodieukhien_qty(Number bodieukhien_qty) {
        this.bodieukhien_qty = bodieukhien_qty;
    }

    public Number getBodieukhien_dau() {
        return bodieukhien_dau;
    }

    public void setBodieukhien_dau(Number bodieukhien_dau) {
        this.bodieukhien_dau = bodieukhien_dau;
    }

    public String getBodieukhien_fname() {
        return bodieukhien_fname;
    }

    public void setBodieukhien_fname(String bodieukhien_fname) {
        this.bodieukhien_fname = bodieukhien_fname;
    }

    public Number getKhaydia_qty() {
        return khaydia_qty;
    }

    public void setKhaydia_qty(Number khaydia_qty) {
        this.khaydia_qty = khaydia_qty;
    }

    public Number getKhaydia_dau() {
        return khaydia_dau;
    }

    public void setKhaydia_dau(Number khaydia_dau) {
        this.khaydia_dau = khaydia_dau;
    }

    public String getKhaydia_fname() {
        return khaydia_fname;
    }

    public void setKhaydia_fname(String khaydia_fname) {
        this.khaydia_fname = khaydia_fname;
    }

    public int[] getVaitrothietbi_ids() {
        return vaitrothietbi_ids;
    }

    public void setVaitrothietbi_ids(int[] vaitrothietbi_ids) {
        this.vaitrothietbi_ids = vaitrothietbi_ids;
    }

    public Number getHdd_tong() {
        return hdd_tong;
    }

    public void setHdd_tong(Number hdd_tong) {
        this.hdd_tong = hdd_tong;
    }

    public Number getHdd_nangluc() {
        return hdd_nangluc;
    }

    public void setHdd_nangluc(Number hdd_nangluc) {
        this.hdd_nangluc = hdd_nangluc;
    }

    public Number getHdd_capphat() {
        return hdd_capphat;
    }

    public void setHdd_capphat(Number hdd_capphat) {
        this.hdd_capphat = hdd_capphat;
    }

    public Number getSsd_tong() {
        return ssd_tong;
    }

    public void setSsd_tong(Number ssd_tong) {
        this.ssd_tong = ssd_tong;
    }

    public Number getSsd_nangluc() {
        return ssd_nangluc;
    }

    public void setSsd_nangluc(Number ssd_nangluc) {
        this.ssd_nangluc = ssd_nangluc;
    }

    public Number getSsd_capphat() {
        return ssd_capphat;
    }

    public void setSsd_capphat(Number ssd_capphat) {
        this.ssd_capphat = ssd_capphat;
    }

    public Number getIops_tong() {
        return iops_tong;
    }

    public void setIops_tong(Number iops_tong) {
        this.iops_tong = iops_tong;
    }

    public Number getIops_nangluc() {
        return iops_nangluc;
    }

    public void setIops_nangluc(Number iops_nangluc) {
        this.iops_nangluc = iops_nangluc;
    }

    public Number getIops_capphat() {
        return iops_capphat;
    }

    public void setIops_capphat(Number iops_capphat) {
        this.iops_capphat = iops_capphat;
    }

    public Number getMem_cache() {
        return mem_cache;
    }

    public void setMem_cache(Number mem_cache) {
        this.mem_cache = mem_cache;
    }

    public Number getFlash_cache() {
        return flash_cache;
    }

    public void setFlash_cache(Number flash_cache) {
        this.flash_cache = flash_cache;
    }

    public Number getIdc_id() {
        return idc_id;
    }

    public void setIdc_id(Number idc_id) {
        this.idc_id = idc_id;
    }

    public Number getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(Number building_id) {
        this.building_id = building_id;
    }

    public Number getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(Number floor_id) {
        this.floor_id = floor_id;
    }

    public Number getZone_id() {
        return zone_id;
    }

    public void setZone_id(Number zone_id) {
        this.zone_id = zone_id;
    }

    public Number getRack_id() {
        return rack_id;
    }

    public void setRack_id(Number rack_id) {
        this.rack_id = rack_id;
    }

    public Number getUnit_qty() {
        return unit_qty;
    }

    public void setUnit_qty(Number unit_qty) {
        this.unit_qty = unit_qty;
    }

    public Number getUp_unit_id() {
        return up_unit_id;
    }

    public void setUp_unit_id(Number up_unit_id) {
        this.up_unit_id = up_unit_id;
    }

    public Number getDown_unit_id() {
        return down_unit_id;
    }

    public void setDown_unit_id(Number down_unit_id) {
        this.down_unit_id = down_unit_id;
    }

    public Number getProject_id() {
        return project_id;
    }

    public void setProject_id(Number project_id) {
        this.project_id = project_id;
    }

    public String getProject_code() {
        return project_code;
    }

    public void setProject_code(String project_code) {
        this.project_code = project_code;
    }

    public String getSo_the_ts() {
        return so_the_ts;
    }

    public void setSo_the_ts(String so_the_ts) {
        this.so_the_ts = so_the_ts;
    }

    public Number getDonviqly_id() {
        return donviqly_id;
    }

    public void setDonviqly_id(Number donviqly_id) {
        this.donviqly_id = donviqly_id;
    }

    public Number getPhongbanqly_id() {
        return phongbanqly_id;
    }

    public void setPhongbanqly_id(Number phongbanqly_id) {
        this.phongbanqly_id = phongbanqly_id;
    }

    public Number getNguoiqly_id() {
        return nguoiqly_id;
    }

    public void setNguoiqly_id(Number nguoiqly_id) {
        this.nguoiqly_id = nguoiqly_id;
    }

    public Number getSo_linecard() {
        return so_linecard;
    }

    public void setSo_linecard(Number so_linecard) {
        this.so_linecard = so_linecard;
    }

    public Number getSo_port_interface() {
        return so_port_interface;
    }

    public void setSo_port_interface(Number so_port_interface) {
        this.so_port_interface = so_port_interface;
    }

    public Number getSo_interface() {
        return so_interface;
    }

    public void setSo_interface(Number so_interface) {
        this.so_interface = so_interface;
    }

    public Number getInterface_start() {
        return interface_start;
    }

    public void setInterface_start(Number interface_start) {
        this.interface_start = interface_start;
    }

    public String getInterface_fname() {
        return interface_fname;
    }

    public void setInterface_fname(String interface_fname) {
        this.interface_fname = interface_fname;
    }

    public Number getSo_power() {
        return so_power;
    }

    public void setSo_power(Number so_power) {
        this.so_power = so_power;
    }

    public Number getSo_port_power() {
        return so_port_power;
    }

    public void setSo_port_power(Number so_port_power) {
        this.so_port_power = so_port_power;
    }

    public Number getSo_powerslot() {
        return so_powerslot;
    }

    public void setSo_powerslot(Number so_powerslot) {
        this.so_powerslot = so_powerslot;
    }

    public Number getPower_start() {
        return power_start;
    }

    public void setPower_start(Number power_start) {
        this.power_start = power_start;
    }

    public String getPower_fname() {
        return power_fname;
    }

    public void setPower_fname(String power_fname) {
        this.power_fname = power_fname;
    }

    public Number getSo_controlcard() {
        return so_controlcard;
    }

    public void setSo_controlcard(Number so_controlcard) {
        this.so_controlcard = so_controlcard;
    }

    public Number getSo_port_console() {
        return so_port_console;
    }

    public void setSo_port_console(Number so_port_console) {
        this.so_port_console = so_port_console;
    }

    public Number getSo_console() {
        return so_console;
    }

    public void setSo_console(Number so_console) {
        this.so_console = so_console;
    }

    public Number getConsole_start() {
        return console_start;
    }

    public void setConsole_start(Number console_start) {
        this.console_start = console_start;
    }

    public String getConsole_fname() {
        return console_fname;
    }

    public void setConsole_fname(String console_fname) {
        this.console_fname = console_fname;
    }

    public Number getIs_interface() {
        return is_interface;
    }

    public void setIs_interface(Number is_interface) {
        this.is_interface = is_interface;
    }

    public Number getIs_power() {
        return is_power;
    }

    public void setIs_power(Number is_power) {
        this.is_power = is_power;
    }

    public Number getIs_console() {
        return is_console;
    }

    public void setIs_console(Number is_console) {
        this.is_console = is_console;
    }

    public Number getIs_controller() {
        return is_controller;
    }

    public void setIs_controller(Number is_controller) {
        this.is_controller = is_controller;
    }

    public Number getIs_khaydia() {
        return is_khaydia;
    }

    public void setIs_khaydia(Number is_khaydia) {
        this.is_khaydia = is_khaydia;
    }

    public ThietBiPortKhaiBao_DTO[] getKhaibao_port() {
        return khaibao_port;
    }

    public void setKhaibao_port(ThietBiPortKhaiBao_DTO[] khaibao_port) {
        this.khaibao_port = khaibao_port;
    }

    public List<ThongTinThietBiV2Dto> getCards() {
        return cards;
    }

    public void setCards(List<ThongTinThietBiV2Dto> cards) {
        this.cards = cards;
    }
}

