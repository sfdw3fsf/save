package vn.vnpt.vnptit.ecms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import vn.vnpt.message.ModelValidation;

public class ThemDslamDtoIn {

    @JsonProperty("tendslam")
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String tenDslam;

    @JsonProperty("bras_id")
    private Integer brasId;

    @JsonProperty("thamso")
    private Integer thamSo;

    @JsonProperty("donvi_id")
    private Integer donViId;

    @JsonProperty("dslam_cha_id")
    private Integer dslamChaId;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("system")
    private String system;

    @JsonProperty("svlan")
    private String svlan;

    @JsonProperty("loai_dslam_id")
    private Integer loaiDslamId;

    @JsonProperty("card_bd")
    private Integer cardBd;

    @JsonProperty("vci_bd")
    private Integer vciBd;

    @JsonProperty("nguoi_cn")
    private String nguoiCn;

    @JsonProperty("may_cn")
    private String mayCn;

    @JsonProperty("ghichu")
    private String ghiChu;

    @JsonProperty("dslam_vdc_id")
    private Integer dslamVdcId;

    @JsonProperty("kieudslam_id")
    private Integer kieuDslamId;

    @JsonProperty("loaitbi_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer loaiTbiId;

    @JsonProperty("hangsx_id")
    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer hangSxId;

    @JsonProperty("so_port")
//    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer soPort;

    @JsonProperty("so_slot")
    private Integer soSlot;

    @JsonProperty("port_bd")
//    @NotNull(message = ModelValidation.NOT_NULL)
    private Integer portBd;

    @JsonProperty("vlanmytv")
    private String vlanMytv;

    @JsonProperty("vlan_int")
    private Integer vlanInt;

    @JsonProperty("vlan_gnms")
    private Integer vlanGnms;

    @JsonProperty("vlan_ims")
    private Integer vlanIms;

    @JsonProperty("slot_bras")
    private Integer slotBras;

    @JsonProperty("port_bras")
    private Integer portBras;

    @JsonProperty("dothu")
    private Integer doThu;

    @JsonProperty("toanha_id")
    private Integer toaNhaId;

    @JsonProperty("doitac_id")
    private Integer doiTacId;

    @JsonProperty("sub_slot")
    private Integer subSlot;

    @JsonProperty("mdf_bd")
    private Integer mdfBd;

    @JsonProperty("thongtin_ae")
    private String thongTinAe;

    @JsonProperty("tu_dslam")
    @NotBlank(message = ModelValidation.NOT_EMPTY)
    private String tuDslam;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("giaothuc_kn")
    private Integer giaoThucKN;

    public int getGiaoThucKN() {
        return giaoThucKN==null?23:giaoThucKN;  //default telnet = 23; 22:SSH
    }

    public void setGiaoThucKN(int giaoThucKN) {
        this.giaoThucKN = giaoThucKN;
    }

    public String getTenDslam() {
        return tenDslam;
    }

    public void setTenDslam(String tenDslam) {
        this.tenDslam = tenDslam;
    }

    public Integer getBrasId() {
        return brasId;
    }

    public void setBrasId(Integer brasId) {
        this.brasId = brasId;
    }

    public Integer getThamSo() {
        return thamSo;
    }

    public void setThamSo(Integer thamSo) {
        this.thamSo = thamSo;
    }

    public Integer getDonViId() {
        return donViId;
    }

    public void setDonViId(Integer donViId) {
        this.donViId = donViId;
    }

    public Integer getDslamChaId() {
        return dslamChaId;
    }

    public void setDslamChaId(Integer dslamChaId) {
        this.dslamChaId = dslamChaId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSvlan() {
        return svlan;
    }

    public void setSvlan(String svlan) {
        this.svlan = svlan;
    }

    public Integer getLoaiDslamId() {
        return loaiDslamId;
    }

    public void setLoaiDslamId(Integer loaiDslamId) {
        this.loaiDslamId = loaiDslamId;
    }

    public Integer getCardBd() {
        return cardBd;
    }

    public void setCardBd(Integer cardBd) {
        this.cardBd = cardBd;
    }

    public Integer getVciBd() {
        return vciBd;
    }

    public void setVciBd(Integer vciBd) {
        this.vciBd = vciBd;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }

    public String getMayCn() {
        return mayCn;
    }

    public void setMayCn(String mayCn) {
        this.mayCn = mayCn;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getDslamVdcId() {
        return dslamVdcId;
    }

    public void setDslamVdcId(Integer dslamVdcId) {
        this.dslamVdcId = dslamVdcId;
    }

    public Integer getKieuDslamId() {
        return kieuDslamId;
    }

    public void setKieuDslamId(Integer kieuDslamId) {
        this.kieuDslamId = kieuDslamId;
    }

    public Integer getLoaiTbiId() {
        return loaiTbiId;
    }

    public void setLoaiTbiId(Integer loaiTbiId) {
        this.loaiTbiId = loaiTbiId;
    }

    public Integer getHangSxId() {
        return hangSxId;
    }

    public void setHangSxId(Integer hangSxId) {
        this.hangSxId = hangSxId;
    }

    public Integer getSoPort() {
        return soPort;
    }

    public void setSoPort(Integer soPort) {
        this.soPort = soPort;
    }

    public Integer getSoSlot() {
        return soSlot;
    }

    public void setSoSlot(Integer soSlot) {
        this.soSlot = soSlot;
    }

    public Integer getPortBd() {
        return portBd;
    }

    public void setPortBd(Integer portBd) {
        this.portBd = portBd;
    }

    public String getVlanMytv() {
        return vlanMytv;
    }

    public void setVlanMytv(String vlanMytv) {
        this.vlanMytv = vlanMytv;
    }

    public Integer getVlanInt() {
        return vlanInt;
    }

    public void setVlanInt(Integer vlanInt) {
        this.vlanInt = vlanInt;
    }

    public Integer getVlanGnms() {
        return vlanGnms;
    }

    public void setVlanGnms(Integer vlanGnms) {
        this.vlanGnms = vlanGnms;
    }

    public Integer getVlanIms() {
        return vlanIms;
    }

    public void setVlanIms(Integer vlanIms) {
        this.vlanIms = vlanIms;
    }

    public Integer getSlotBras() {
        return slotBras;
    }

    public void setSlotBras(Integer slotBras) {
        this.slotBras = slotBras;
    }

    public Integer getPortBras() {
        return portBras;
    }

    public void setPortBras(Integer portBras) {
        this.portBras = portBras;
    }

    public Integer getDoThu() {
        return doThu;
    }

    public void setDoThu(Integer doThu) {
        this.doThu = doThu;
    }

    public Integer getToaNhaId() {
        return toaNhaId;
    }

    public void setToaNhaId(Integer toaNhaId) {
        this.toaNhaId = toaNhaId;
    }

    public Integer getDoiTacId() {
        return doiTacId;
    }

    public void setDoiTacId(Integer doiTacId) {
        this.doiTacId = doiTacId;
    }

    public Integer getSubSlot() {
        return subSlot;
    }

    public void setSubSlot(Integer subSlot) {
        this.subSlot = subSlot;
    }

    public Integer getMdfBd() {
        return mdfBd;
    }

    public void setMdfBd(Integer mdfBd) {
        this.mdfBd = mdfBd;
    }

    public String getThongTinAe() {
        return thongTinAe;
    }

    public void setThongTinAe(String thongTinAe) {
        this.thongTinAe = thongTinAe;
    }

    public String getTuDslam() {
        return tuDslam;
    }

    public void setTuDslam(String tuDslam) {
        this.tuDslam = tuDslam;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
