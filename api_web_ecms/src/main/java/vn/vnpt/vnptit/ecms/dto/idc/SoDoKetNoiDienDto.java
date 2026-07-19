package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class SoDoKetNoiDienDto implements Serializable {

    private Long idcId;
    private Long thietBiId;
    private Long thietbiKetnoiId;
    private Long portThietbiId;
    private Long portThietbiKetnoiId;
    private Float khoangCachKetNoi;
    private Long loaiKetNoiId;
    private Long trangThaiId;
    private Long toaNhaId;
    private Long matSanId;
    private Long phongId;
    private Long rackId;
    private String tenThietBi;
    private String type;

    private Integer external;
    private Integer depth;
    private Long scopeId;

    public SoDoKetNoiDienDto() {
    }

    public SoDoKetNoiDienDto(Long idcId, Long thietBiId, Long thietbiKetnoiId, Long portThietbiId,
            Long portThietbiKetnoiId, Float khoangCachKetNoi, Long loaiKetNoiId, Long trangThaiId, Long toaNhaId,
            Long matSanId, Long phongId, Long rackId, String tenThietBi, String type, Integer external, Integer depth,
            Long scopeId) {
        this.idcId = idcId;
        this.thietBiId = thietBiId;
        this.thietbiKetnoiId = thietbiKetnoiId;
        this.portThietbiId = portThietbiId;
        this.portThietbiKetnoiId = portThietbiKetnoiId;
        this.khoangCachKetNoi = khoangCachKetNoi;
        this.loaiKetNoiId = loaiKetNoiId;
        this.trangThaiId = trangThaiId;
        this.toaNhaId = toaNhaId;
        this.matSanId = matSanId;
        this.phongId = phongId;
        this.rackId = rackId;
        this.tenThietBi = tenThietBi;
        this.type = type;
        this.external = external;
        this.depth = depth;
        this.scopeId = scopeId;
    }

    public Long getIdcId() {
        return idcId;
    }

    public void setIdcId(Long idcId) {
        this.idcId = idcId;
    }

    public Long getThietBiId() {
        return thietBiId;
    }

    public void setThietBiId(Long thietBiId) {
        this.thietBiId = thietBiId;
    }

    public Long getThietbiKetnoiId() {
        return thietbiKetnoiId;
    }

    public void setThietbiKetnoiId(Long thietbiKetnoiId) {
        this.thietbiKetnoiId = thietbiKetnoiId;
    }

    public Long getPortThietbiId() {
        return portThietbiId;
    }

    public void setPortThietbiId(Long portThietbiId) {
        this.portThietbiId = portThietbiId;
    }

    public Long getPortThietbiKetnoiId() {
        return portThietbiKetnoiId;
    }

    public void setPortThietbiKetnoiId(Long portThietbiKetnoiId) {
        this.portThietbiKetnoiId = portThietbiKetnoiId;
    }

    public Float getKhoangCachKetNoi() {
        return khoangCachKetNoi;
    }

    public void setKhoangCachKetNoi(Float khoangCachKetNoi) {
        this.khoangCachKetNoi = khoangCachKetNoi;
    }

    public Long getLoaiKetNoiId() {
        return loaiKetNoiId;
    }

    public void setLoaiKetNoiId(Long loaiKetNoiId) {
        this.loaiKetNoiId = loaiKetNoiId;
    }

    public Long getTrangThaiId() {
        return trangThaiId;
    }

    public void setTrangThaiId(Long trangThaiId) {
        this.trangThaiId = trangThaiId;
    }

    public Long getToaNhaId() {
        return toaNhaId;
    }

    public void setToaNhaId(Long toaNhaId) {
        this.toaNhaId = toaNhaId;
    }

    public Long getMatSanId() {
        return matSanId;
    }

    public void setMatSanId(Long matSanId) {
        this.matSanId = matSanId;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getExternal() {
        return external;
    }

    public void setExternal(Integer external) {
        this.external = external;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

}
