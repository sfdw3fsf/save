package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.List;

public class UpsertMayAoDtoV2 {
    private String clusterCode;
    // bảng MAYAO
    private MayAoDto mayAo;
    // bảng NANGLUC_MAYAO
    private NangLucMayAoDto nangLuc;
    // bảng OCUNG_MAYAO
    private List<OcungMayAoDto> disks;

    public String getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(String clusterCode) {
        this.clusterCode = clusterCode;
    }

    public MayAoDto getMayAo() {
        return mayAo;
    }

    public void setMayAo(MayAoDto mayAo) {
        this.mayAo = mayAo;
    }

    public NangLucMayAoDto getNangLuc() {
        return nangLuc;
    }

    public void setNangLuc(NangLucMayAoDto nangLuc) {
        this.nangLuc = nangLuc;
    }

    public List<OcungMayAoDto> getDisks() {
        return disks;
    }

    public void setDisks(List<OcungMayAoDto> disks) {
        this.disks = disks;
    }

    @Override
    public String toString() {
        return "UpsertMayAoDtoV2 [clusterCode=" + clusterCode
                + ", mayAo=" + mayAo
                + ", nangLuc=" + nangLuc
                + ", disks=" + disks + "]";
    }
}
