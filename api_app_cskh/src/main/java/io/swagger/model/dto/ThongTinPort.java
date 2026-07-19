package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Thông tin port
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThongTinPort {

    @JsonProperty("tt")
    private Long tt;

    @JsonProperty("maTb")
    private String maTb;

    @JsonProperty("tenTb")
    private String tenTb;

    @JsonProperty("hdtbId")
    private Long hdtbId;

    @JsonProperty("maLt")
    private String maLt;

    @JsonProperty("tenDsLam")
    private String tenDsLam;

    @JsonProperty("thueBaoId")
    private Long thueBaoId;

    @JsonProperty("loaiTbId")
    private Long loaiTbId;

    @JsonProperty("port")
    private String port;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("portAdsl")
    private String portAdsl;

    @JsonProperty("portVisa")
    private String portVisa;

    @JsonProperty("portVisaXtest")
    private String portVisaXtest;

    @JsonProperty("onuId")
    private Long onuId;

    @JsonProperty("account")
    private String account;

    @JsonProperty("loaiDsLamId")
    private Long loaiDsLamId;

    @JsonProperty("portId")
    private Long portId;

    @JsonProperty("trangThaiTb")
    private String trangThaiTb;

    @JsonProperty("rack")
    private String rack;

    @JsonProperty("shelf")
    private String shelf;

    @JsonProperty("slot")
    private String slot;

    @JsonProperty("sport")
    private String sport;

    @JsonProperty("vpi")
    private String vpi;

    @JsonProperty("vci")
    private String vci;

    @JsonProperty("system")
    private String system;

    @JsonProperty("frame")
    private String frame;

    @JsonProperty("card")
    private String card;

    @JsonProperty("portVl")
    private String portVl;

    @JsonProperty("thamSo")
    private String thamSo;

    @JsonProperty("portInfo")
    private String portInfo;

    @JsonProperty("syncStatus")
    private String syncStatus;

    @JsonProperty("port9Ts")
    private String port9Ts;

    public ThongTinPort convertDataFromDatabase(ResultSet rs) throws SQLException {
        if(rs != null) {
            tt = rs.getLong("TT");
            maTb = rs.getString("MA_TB");
            tenTb = rs.getString("TEN_TB");
            hdtbId = rs.getLong("HDTB_ID");
            maLt = rs.getString("MA_LT");
            tenDsLam = rs.getString("TENDSLAM");
            thueBaoId = rs.getLong("THUEBAO_ID");
            loaiTbId = rs.getLong("LOAITBI_ID");
            port = rs.getString("PORT");
            ip = rs.getString("IP");
            portAdsl = rs.getString("PORT_ADSL");
            portVisa = rs.getString("PORT_VISA");
            portVisaXtest = rs.getString("PORT_VISA_XTEST");
            onuId = rs.getLong("ONU_ID");
            account = rs.getString("ACCOUNT");
            loaiDsLamId = rs.getLong("LOAI_DSLAM_ID");
            portId = rs.getLong("PORT_ID");
            trangThaiTb = rs.getString("TRANGTHAI_TB");
            rack = rs.getString("RACK");
            shelf = rs.getString("SHELF");
            slot = rs.getString("SLOT");
            sport = rs.getString("SPORT");
            vpi = rs.getString("VPI");
            vci = rs.getString("VCI");
            system = rs.getString("SYSTEM");
            frame = rs.getString("FRAME");
            card = rs.getString("CARD");
            portVl = rs.getString("PORTVL");
            thamSo = rs.getString("THAMSO");
            portInfo = rs.getString("PORTINFO");
            syncStatus = rs.getString("SYNC_STATUS");
//            port9Ts = rs.getString("port_9ts");
            return this;
        } else {
            return null;
        }
    }

    public Long getTt() {
        return tt;
    }

    public void setTt(Long tt) {
        this.tt = tt;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getTenTb() {
        return tenTb;
    }

    public void setTenTb(String tenTb) {
        this.tenTb = tenTb;
    }

    public Long getHdtbId() {
        return hdtbId;
    }

    public void setHdtbId(Long hdtbId) {
        this.hdtbId = hdtbId;
    }

    public String getMaLt() {
        return maLt;
    }

    public void setMaLt(String maLt) {
        this.maLt = maLt;
    }

    public String getTenDsLam() {
        return tenDsLam;
    }

    public void setTenDsLam(String tenDsLam) {
        this.tenDsLam = tenDsLam;
    }

    public Long getThueBaoId() {
        return thueBaoId;
    }

    public void setThueBaoId(Long thueBaoId) {
        this.thueBaoId = thueBaoId;
    }

    public Long getLoaiTbId() {
        return loaiTbId;
    }

    public void setLoaiTbId(Long loaiTbId) {
        this.loaiTbId = loaiTbId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPortAdsl() {
        return portAdsl;
    }

    public void setPortAdsl(String portAdsl) {
        this.portAdsl = portAdsl;
    }

    public String getPortVisa() {
        return portVisa;
    }

    public void setPortVisa(String portVisa) {
        this.portVisa = portVisa;
    }

    public String getPortVisaXtest() {
        return portVisaXtest;
    }

    public void setPortVisaXtest(String portVisaXtest) {
        this.portVisaXtest = portVisaXtest;
    }

    public Long getOnuId() {
        return onuId;
    }

    public void setOnuId(Long onuId) {
        this.onuId = onuId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Long getLoaiDsLamId() {
        return loaiDsLamId;
    }

    public void setLoaiDsLamId(Long loaiDsLamId) {
        this.loaiDsLamId = loaiDsLamId;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public String getTrangThaiTb() {
        return trangThaiTb;
    }

    public void setTrangThaiTb(String trangThaiTb) {
        this.trangThaiTb = trangThaiTb;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getVpi() {
        return vpi;
    }

    public void setVpi(String vpi) {
        this.vpi = vpi;
    }

    public String getVci() {
        return vci;
    }

    public void setVci(String vci) {
        this.vci = vci;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPortVl() {
        return portVl;
    }

    public void setPortVl(String portVl) {
        this.portVl = portVl;
    }

    public String getThamSo() {
        return thamSo;
    }

    public void setThamSo(String thamSo) {
        this.thamSo = thamSo;
    }

    public String getPortInfo() {
        return portInfo;
    }

    public void setPortInfo(String portInfo) {
        this.portInfo = portInfo;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getPort9Ts() {
        return port9Ts;
    }

    public void setPort9Ts(String port9Ts) {
        this.port9Ts = port9Ts;
    }
}
