package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ThongTinLoaiKiemTra {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("parentId")
    private Long parentId;

    @JsonProperty("ngayTao")
    private String ngayTao;

    @JsonProperty("stt")
    private Integer stt;

    @JsonProperty("loaiKiemTra")
    private String loaiKiemTra;

    @JsonProperty("systemName")
    private String systemName;

    @JsonProperty("cardName")
    private String cardName;

    @JsonProperty("portName")
    private String portName;

    @JsonProperty("txValue")
    private String txValue;

    @JsonProperty("rxValue")
    private String rxValue;

    @JsonProperty("rxTrongNguong")
    private String rxTrongNguong;

    @JsonProperty("rxNgoaiNguong")
    private String rxNgoaiNguong;

    @JsonProperty("crcResult")
    private String crcResult;

    @JsonProperty("luuLuongPortResult")
    private String luuLuongPortResult;

    @JsonProperty("ketQua")
    private String ketQua;

    @JsonProperty("ghiChu")
    private String ghiChu;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public String getLoaiKiemTra() {
        return loaiKiemTra;
    }

    public void setLoaiKiemTra(String loaiKiemTra) {
        this.loaiKiemTra = loaiKiemTra;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getTxValue() {
        return txValue;
    }

    public void setTxValue(String txValue) {
        this.txValue = txValue;
    }

    public String getRxValue() {
        return rxValue;
    }

    public void setRxValue(String rxValue) {
        this.rxValue = rxValue;
    }

    public String getRxTrongNguong() {
        return rxTrongNguong;
    }

    public void setRxTrongNguong(String rxTrongNguong) {
        this.rxTrongNguong = rxTrongNguong;
    }

    public String getRxNgoaiNguong() {
        return rxNgoaiNguong;
    }

    public void setRxNgoaiNguong(String rxNgoaiNguong) {
        this.rxNgoaiNguong = rxNgoaiNguong;
    }

    public String getCrcResult() {
        return crcResult;
    }

    public void setCrcResult(String crcResult) {
        this.crcResult = crcResult;
    }

    public String getLuuLuongPortResult() {
        return luuLuongPortResult;
    }

    public void setLuuLuongPortResult(String luuLuongPortResult) {
        this.luuLuongPortResult = luuLuongPortResult;
    }

    public String getKetQua() {
        return ketQua;
    }

    public void setKetQua(String ketQua) {
        this.ketQua = ketQua;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
