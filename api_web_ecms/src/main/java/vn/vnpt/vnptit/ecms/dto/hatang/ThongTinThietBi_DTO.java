package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

public class ThongTinThietBi_DTO {
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
    private List<ThongTinThietBi_DTO> cards;

    public Number getId() {
        return id;
    }

    public @NotEmpty(message = ModelValidation.NOT_EMPTY) String getTen() {
        return ten;
    }

    public String getKyhieu() {
        return kyhieu;
    }

    public String getMathietbi_infra() {
        return mathietbi_infra;
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

    public Number getLoaikhachhang() {
        return loaikhachhang;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public String getModel() {
        return model;
    }

    public String getPart_number() {
        return part_number;
    }

    public Number getHsm() {
        return hsm;
    }

    public Number getHieu_luc() {
        return hieu_luc;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public int[] getIp_ids() {
        return ip_ids;
    }

    public Number getHangsx_id() {
        return hangsx_id;
    }

    public Number getNamsx() {
        return namsx;
    }

    public Number getNhacungcap_id() {
        return nhacungcap_id;
    }

    public Number getCs_dinhdanh() {
        return cs_dinhdanh;
    }

    public Number getCs_thucte() {
        return cs_thucte;
    }

    public Number getHedieuhanh_id() {
        return hedieuhanh_id;
    }

    public Number getTrangthai_sd() {
        return trangthai_sd;
    }

    public LocalDate getNgay_lapdat() {
        return ngay_lapdat;
    }

    public LocalDate getNgay_baohanh() {
        return ngay_baohanh;
    }

    public LocalDate getNgay_sudung() {
        return ngay_sudung;
    }

    public Number getCpu_sokhe() {
        return cpu_sokhe;
    }

    public Number getCpu_dacam() {
        return cpu_dacam;
    }

    public Number getCpu_total() {
        return cpu_total;
    }

    public Number getHdd_sokhe() {
        return hdd_sokhe;
    }

    public Number getHdd_dacam() {
        return hdd_dacam;
    }

    public Number getHdd_total() {
        return hdd_total;
    }

    public Number getRam_sokhe() {
        return ram_sokhe;
    }

    public Number getRam_dacam() {
        return ram_dacam;
    }

    public Number getRam_total() {
        return ram_total;
    }

    public String getLoaitudia_id() {
        return loaitudia_id;
    }

    public Number getBodieukhien_qty() {
        return bodieukhien_qty;
    }

    public Number getBodieukhien_dau() {
        return bodieukhien_dau;
    }

    public String getBodieukhien_fname() {
        return bodieukhien_fname;
    }

    public Number getKhaydia_qty() {
        return khaydia_qty;
    }

    public Number getKhaydia_dau() {
        return khaydia_dau;
    }

    public String getKhaydia_fname() {
        return khaydia_fname;
    }

    public Number getHdd_tong() {
        return hdd_tong;
    }

    public Number getHdd_nangluc() {
        return hdd_nangluc;
    }

    public Number getHdd_capphat() {
        return hdd_capphat;
    }

    public Number getSsd_tong() {
        return ssd_tong;
    }

    public Number getSsd_nangluc() {
        return ssd_nangluc;
    }

    public Number getSsd_capphat() {
        return ssd_capphat;
    }

    public Number getIops_tong() {
        return iops_tong;
    }

    public Number getIops_nangluc() {
        return iops_nangluc;
    }

    public Number getIops_capphat() {
        return iops_capphat;
    }

    public Number getMem_cache() {
        return mem_cache;
    }

    public Number getFlash_cache() {
        return flash_cache;
    }

    public String getInterface_fname() {
        return interface_fname;
    }

    public String getPower_fname() {
        return power_fname;
    }

    public String getConsole_fname() {
        return console_fname;
    }

    public Number getIdc_id() {
        return idc_id;
    }

    public Number getBuilding_id() {
        return building_id;
    }

    public Number getFloor_id() {
        return floor_id;
    }

    public Number getZone_id() {
        return zone_id;
    }

    public Number getRack_id() {
        return rack_id;
    }

    public Number getUnit_qty() {
        return unit_qty;
    }

    public Number getUp_unit_id() {
        return up_unit_id;
    }

    public Number getDown_unit_id() {
        return down_unit_id;
    }

    public Number getProject_id() {
        return project_id;
    }

    public String getProject_code() {
        return project_code;
    }

    public String getSo_the_ts() {
        return so_the_ts;
    }

    public Number getDonviqly_id() {
        return donviqly_id;
    }

    public Number getPhongbanqly_id() {
        return phongbanqly_id;
    }

    public Number getNguoiqly_id() {
        return nguoiqly_id;
    }

    public Number getSo_linecard() {
        return so_linecard;
    }

    public Number getSo_port_interface() {
        return so_port_interface;
    }

    public Number getSo_interface() {
        return so_interface;
    }

    public Number getInterface_start() {
        return interface_start;
    }

    public String getInterface_name() {
        return interface_fname;
    }

    public Number getSo_power() {
        return so_power;
    }

    public Number getSo_port_power() {
        return so_port_power;
    }

    public Number getSo_powerslot() {
        return so_powerslot;
    }

    public Number getPower_start() {
        return power_start;
    }

    public String getPower_name() {
        return power_fname;
    }

    public Number getSo_controlcard() {
        return so_controlcard;
    }

    public Number getSo_port_console() {
        return so_port_console;
    }

    public Number getSo_console() {
        return so_console;
    }

    public Number getConsole_start() {
        return console_start;
    }

    public String getConsole_name() {
        return console_fname;
    }

    public ThietBiPortKhaiBao_DTO[] getKhaibao_port() {
        return khaibao_port;
    }

    public Number getChungloaithietbi_id() {
        return chungloaithietbi_id;
    }

    public int[] getVaitrothietbi_ids() {
        return vaitrothietbi_ids;
    }

    public Number getPsu_qty_in() {
        return psu_qty_in;
    }

    public Number getPsu_qty_out() {
        return psu_qty_out;
    }

    public Number getCau_hinh() {
        return cau_hinh;
    }

    public Number getIs_interface() {
        return is_interface;
    }

    public Number getIs_power() {
        return is_power;
    }

    public Number getIs_console() {
        return is_console;
    }

    public Number getIs_controller() {
        return is_controller;
    }

    public Number getIs_khaydia() {
        return is_khaydia;
    }

    public List<ThongTinThietBi_DTO> getCards() {
        return cards;
    }
}
