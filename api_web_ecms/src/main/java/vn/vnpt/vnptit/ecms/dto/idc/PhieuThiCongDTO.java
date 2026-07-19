package vn.vnpt.vnptit.ecms.dto.idc;

import java.util.ArrayList;
import java.util.List;

import vn.vnpt.common.Utils;
import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class PhieuThiCongDTO {
    private Long pPhieuTCId;
    private Long pPhieuTCChaId;
    private Long pLoaiCVId;
    private Long pDonViId;
    private Long pMatSanId;
    private String pTenCV;
    private String pCanCuYC;
    private String pNoiDung; // Hạng mục triển khai
    private Integer pGiaTriKhaiBao;
    private Long pNhanVienTHId;
    private String pNgayYCHT;
    private String pMoTa;
    private Long pLoaiTNId;
    private String pChiTiet;
    private List<FileDinhKemDTO> pDSFile = new ArrayList<>();

    private Number pCongViecId;
    private Number pLoaiPhId;
    private Number pTinhId;
    private Number pHdtbId;
    private Number pThueBaoId;
    private String pMaTb;
    private String pMaGd;
    private String pTenKh;
    private String pSoHd;
    private Number pGoiCuocId;
    private String pDsNvPhoiHop;
    private String pDsPhieuTCCon;

    public void valid() {
        if (pLoaiCVId == null || pDonViId == null || pNhanVienTHId == null
                || pGiaTriKhaiBao == null || pGiaTriKhaiBao <= 0
                || pMatSanId == null || Utils.isEmpty(pCanCuYC)
                || Utils.isEmpty(pNgayYCHT) || !Utils.isDate(pNgayYCHT, "dd/MM/yyyy")) {
            throw new BadRequestExceptionMessage("Tham số đầu vào không hợp lệ!");
        }
        if (!pDSFile.isEmpty())
            pDSFile.forEach(FileDinhKemDTO::valid);
    }

    public Long getpPhieuTCId() {
        return pPhieuTCId;
    }

    public void setpPhieuTCId(Long pPhieuTCId) {
        this.pPhieuTCId = pPhieuTCId;
    }

    public Long getpPhieuTCChaId() {
        return pPhieuTCChaId;
    }

    public void setpPhieuTCChaId(Long pPhieuTCChaId) {
        this.pPhieuTCChaId = pPhieuTCChaId;
    }

    public Long getpLoaiCVId() {
        return pLoaiCVId;
    }

    public void setpLoaiCVId(Long pLoaiCVId) {
        this.pLoaiCVId = pLoaiCVId;
    }

    public Long getpDonViId() {
        return pDonViId;
    }

    public void setpDonViId(Long pDonViId) {
        this.pDonViId = pDonViId;
    }

    public Long getpMatSanId() {
        return pMatSanId;
    }

    public void setpMatSanId(Long pMatSanId) {
        this.pMatSanId = pMatSanId;
    }

    public String getpTenCV() {
        return pTenCV;
    }

    public void setpTenCV(String pTenCV) {
        this.pTenCV = pTenCV;
    }

    public String getpCanCuYC() {
        return pCanCuYC;
    }

    public void setpCanCuYC(String pCanCuYC) {
        this.pCanCuYC = pCanCuYC;
    }

    public String getpNoiDung() {
        return pNoiDung;
    }

    public void setpNoiDung(String pNoiDung) {
        this.pNoiDung = pNoiDung;
    }

    public Integer getpGiaTriKhaiBao() {
        return pGiaTriKhaiBao;
    }

    public void setpGiaTriKhaiBao(Integer pGiaTriKhaiBao) {
        this.pGiaTriKhaiBao = pGiaTriKhaiBao;
    }

    public Long getpNhanVienTHId() {
        return pNhanVienTHId;
    }

    public void setpNhanVienTHId(Long pNhanVienTHId) {
        this.pNhanVienTHId = pNhanVienTHId;
    }

    public String getpNgayYCHT() {
        return pNgayYCHT;
    }

    public void setpNgayYCHT(String pNgayYCHT) {
        this.pNgayYCHT = pNgayYCHT;
    }

    public String getpMoTa() {
        return pMoTa;
    }

    public void setpMoTa(String pMoTa) {
        this.pMoTa = pMoTa;
    }

    public Long getpLoaiTNId() {
        return pLoaiTNId;
    }

    public void setpLoaiTNId(Long pLoaiTNId) {
        this.pLoaiTNId = pLoaiTNId;
    }

    public String getpChiTiet() {
        return pChiTiet;
    }

    public void setpChiTiet(String pChiTiet) {
        this.pChiTiet = pChiTiet;
    }

    public List<FileDinhKemDTO> getpDSFile() {
        return pDSFile;
    }

    public void setpDSFile(List<FileDinhKemDTO> pDSFile) {
        this.pDSFile = pDSFile;
    }

    public Number getpCongViecId() {
        return pCongViecId;
    }

    public void setpCongViecId(Number pCongViecId) {
        this.pCongViecId = pCongViecId;
    }

    public Number getpLoaiPhId() {
        return pLoaiPhId;
    }

    public void setpLoaiPhId(Number pLoaiPhId) {
        this.pLoaiPhId = pLoaiPhId;
    }

    public Number getpTinhId() {
        return pTinhId;
    }

    public void setpTinhId(Number pTinhId) {
        this.pTinhId = pTinhId;
    }

    public Number getpHdtbId() {
        return pHdtbId;
    }

    public void setpHdtbId(Number pHdtbId) {
        this.pHdtbId = pHdtbId;
    }

    public Number getpThueBaoId() {
        return pThueBaoId;
    }

    public void setpThueBaoId(Number pThueBaoId) {
        this.pThueBaoId = pThueBaoId;
    }

    public String getpMaTb() {
        return pMaTb;
    }

    public void setpMaTb(String pMaTb) {
        this.pMaTb = pMaTb;
    }

    public String getpMaGd() {
        return pMaGd;
    }

    public void setpMaGd(String pMaGd) {
        this.pMaGd = pMaGd;
    }

    public String getpTenKh() {
        return pTenKh;
    }

    public void setpTenKh(String pTenKh) {
        this.pTenKh = pTenKh;
    }

    public String getpSoHd() {
        return pSoHd;
    }

    public void setpSoHd(String pSoHd) {
        this.pSoHd = pSoHd;
    }

    public Number getpGoiCuocId() {
        return pGoiCuocId;
    }

    public void setpGoiCuocId(Number pGoiCuocId) {
        this.pGoiCuocId = pGoiCuocId;
    }

    public String getpDsNvPhoiHop() {
        return pDsNvPhoiHop;
    }

    public void setpDsNvPhoiHop(String pDsNvPhoiHop) {
        this.pDsNvPhoiHop = pDsNvPhoiHop;
    }

    public String getpDsPhieuTCCon() {
        return pDsPhieuTCCon;
    }

    public void setpDsPhieuTCCon(String pDsPhieuTCCon) {
        this.pDsPhieuTCCon = pDsPhieuTCCon;
    }
}
