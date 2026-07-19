package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


// (P_ID IN NUMBER,
//                               P_THIETBI_ID     IN NUMBER,
//                               P_MATHIETBI     IN VARCHAR2,
//                               P_SOTHUTU IN NUMBER,
//                               P_TEN IN VARCHAR2,
//                               P_KYHIEU IN VARCHAR2,
//                               P_SERIAL_NUMBER IN VARCHAR2,
//                               P_IP_MGMT_ID IN NUMBER,
//                               P_IP_CONTROLLER_ID IN NUMBER,
//                                P_GHICHU IN VARCHAR2,
//                                P_HIEULUC IN NUMBER,
//                                P_HDD_TONG IN NUMBER,
//                                P_HDD_CAPPHAT IN NUMBER,
//                                P_SSD_TONG IN NUMBER,
//                                 P_SSD_CAPPHAT IN NUMBER,
//                                 P_TRANGTHAISD IN NUMBER,
//                                 P_MADUAN IN VARCHAR2,
//                                 P_TENDUAN IN VARCHAR2,
//                                 P_SOTHE IN VARCHAR2,
//                                 P_DONVIQLY_ID IN NUMBER,
//                                 P_PHONGBANQLY_ID IN NUMBER,
//                                P_NGUOIQLY_ID IN NUMBER)
public class KhayDiaTrongTuDia {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("thietbi_id")
    private Long thietbi_id;
    @JsonProperty("ma_thietbi")
    private String ma_thietbi;
    @JsonProperty("so_thutu")
    private Long so_thutu;
    @JsonProperty("ten")
    private String ten;
    @JsonProperty("ky_hieu")
    private String ky_hieu;
    @JsonProperty("serial_number")
    private String serial_number;
    @JsonProperty("ip_mgmt_id")
    private Long ip_mgmt_id;
    @JsonProperty("ip_controller_id")
    private Long ip_controller_id;
    @JsonProperty("ghi_chu")
    private String ghi_chu;
    @JsonProperty("hieu_luc")
    @Min(value = 0, message = "Hiệu lực chỉ được phép là 0 hoặc 1")
    @Max(value = 1, message = "Hiệu lực chỉ được phép là 0 hoặc 1")
    private int hieu_luc;
    @JsonProperty("hdd_tong")
    private Long hdd_tong;  
    @JsonProperty("hdd_capphat")
    private Long hdd_capphat;
    @JsonProperty("ssd_tong")
    private Long ssd_tong;
    @JsonProperty("ssd_capphat")
    private Long ssd_capphat;
    @JsonProperty("trangthai_sd")
    @Min(value = 0, message = "Trạng thái chỉ được phép là 0 hoặc 1")
    @Max(value = 1, message = "Trạng thái chỉ được phép là 0 hoặc 1")
    private Long trangthai_sd;
    @JsonProperty("ma_duan")
    private String ma_duan;
    @JsonProperty("ten_duan")
    private String ten_duan;
    @JsonProperty("so_the")
    private String so_the;
    @JsonProperty("donvi_qly_id")
    private Long donvi_qly_id;
    @JsonProperty("phongban_qly_id")
    private Long phongban_qly_id;
    @JsonProperty("nguoi_qly_id")
    private Long nguoi_qly_id;
    

    public KhayDiaTrongTuDia() {
    }

    public KhayDiaTrongTuDia(Long id, Long thietbi_id, String ma_thietbi, Long so_thutu, String ten, String ky_hieu, String serial_number, Long ip_mgmt_id, Long ip_controller_id, String ghi_chu, int hieu_luc, Long hdd_tong, Long hdd_capphat, Long ssd_tong, Long ssd_capphat, Long trangthai_sd, String ma_duan, String ten_duan, String so_the, Long donvi_qly_id, Long phongban_qly_id, Long nguoi_qly_id) {
        this.id = id;
        this.thietbi_id = thietbi_id;
        this.ma_thietbi = ma_thietbi;
        this.so_thutu = so_thutu;
        this.ten = ten;
        this.ky_hieu = ky_hieu;
        this.serial_number = serial_number;
        this.ip_mgmt_id = ip_mgmt_id;
        this.ip_controller_id = ip_controller_id;
        this.ghi_chu = ghi_chu;
        this.hieu_luc = hieu_luc;
        this.hdd_tong = hdd_tong;
        this.hdd_capphat = hdd_capphat;
        this.ssd_tong = ssd_tong;
        this.ssd_capphat = ssd_capphat;
        this.trangthai_sd = trangthai_sd;
        this.ma_duan = ma_duan;
        this.ten_duan = ten_duan;
        this.so_the = so_the;
        this.donvi_qly_id = donvi_qly_id;
        this.phongban_qly_id = phongban_qly_id;
        this.nguoi_qly_id = nguoi_qly_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(Long thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public String getMa_thietbi() {
        return ma_thietbi;
    }

    public void setMa_thietbi(String ma_thietbi) {
        this.ma_thietbi = ma_thietbi;
    }

    public Long getSo_thutu() {
        return so_thutu;
    }


    public void setSo_thutu(Long so_thutu) {
        this.so_thutu = so_thutu;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getKy_hieu() {
        return ky_hieu;
    }

    public void setKy_hieu(String ky_hieu) {
        this.ky_hieu = ky_hieu;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public Long getIp_mgmt_id() {
        return ip_mgmt_id;
    }

    public void setIp_mgmt_id(Long ip_mgmt_id) {
        this.ip_mgmt_id = ip_mgmt_id;
    }

    public Long getIp_controller_id() {
        return ip_controller_id;
    }

    public void setIp_controller_id(Long ip_controller_id) {
        this.ip_controller_id = ip_controller_id;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

    public int getHieu_luc() {
        return hieu_luc;
    }

    public void setHieu_luc(int hieu_luc) {
        this.hieu_luc = hieu_luc;
    }

    public Long getHdd_tong() {
        return hdd_tong;
    }

    public void setHdd_tong(Long hdd_tong) {
        this.hdd_tong = hdd_tong;
    }

    public Long getHdd_capphat() {
        return hdd_capphat;
    }

    public void setHdd_capphat(Long hdd_capphat) {
        this.hdd_capphat = hdd_capphat;
    }

    public Long getSsd_tong() {
        return ssd_tong;
    }

    public void setSsd_tong(Long ssd_tong) {
        this.ssd_tong = ssd_tong;
    }

    public Long getSsd_capphat() {
        return ssd_capphat;
    }

    public void setSsd_capphat(Long ssd_capphat) {
        this.ssd_capphat = ssd_capphat;
    }

    public Long getTrangthai_sd() {
        return trangthai_sd;
    }

    public void setTrangthai_sd(Long trangthai_sd) {
        this.trangthai_sd = trangthai_sd;
    }

    public String getMa_duan() {
        return ma_duan;
    }

    public void setMa_duan(String ma_duan) {
        this.ma_duan = ma_duan;
    }

    public String getTen_duan() {
        return ten_duan;
    }

    public void setTen_duan(String ten_duan) {
        this.ten_duan = ten_duan;
    }

    public String getSo_the() {
        return so_the;
    }

    public void setSo_the(String so_the) {
        this.so_the = so_the;
    }

    public Long getDonvi_qly_id() {
        return donvi_qly_id;
    }

    public void setDonvi_qly_id(Long donvi_qly_id) {

        this.donvi_qly_id = donvi_qly_id;
    }

    public Long getPhongban_qly_id() {
        return phongban_qly_id;
    }

    public void setPhongban_qly_id(Long phongban_qly_id) {

        this.phongban_qly_id = phongban_qly_id;
    }

    public Long getNguoi_qly_id() {
        return nguoi_qly_id;
    }

    public void setNguoi_qly_id(Long nguoi_qly_id) {

        this.nguoi_qly_id = nguoi_qly_id;
    }
}
