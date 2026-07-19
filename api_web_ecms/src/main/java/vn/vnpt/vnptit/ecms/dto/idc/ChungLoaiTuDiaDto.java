package vn.vnpt.vnptit.ecms.dto.idc;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho danh mục chủng loại tủ đĩa")
public class ChungLoaiTuDiaDto {

    @Schema(description = "ID chủng loại tủ đĩa (cho update/upsert)")
    private Long chungloaitdId;

    @Schema(description = "Mã vật tư")
    private String maVt;

    @Schema(description = "ID loại tủ đĩa")
    private Long loaitudiaId;

    @Schema(description = "Model thiết bị")
    private String modelThietbi;

    @Schema(description = "ID hãng sản xuất")
    private Long hangsxId;

    @Schema(description = "Công suất")
    private Long congsuat;

    @Schema(description = "ID loại vai trò")
    private Long loaivaitroId;

    @Schema(description = "Full depth")
    private Long fullDepth;

    @Schema(description = "Bo điều khiển - Số lượng")
    private Long bodkSl;

    @Schema(description = "Bo điều khiển - CPU")
    private Long bodkCpu;

    @Schema(description = "Bo điều khiển - RAM")
    private Long bodkRam;

    @Schema(description = "Bo điều khiển - Mã vật tư")
    private String bodkMaVt;

    @Schema(description = "Data Card - Số cổng")
    private Long dcSocong;

    @Schema(description = "Data Card - Tên cổng")
    private String dcTencong;

    @Schema(description = "Data Card - Loại cổng")
    private String dcLoaicong;

    @Schema(description = "Data Card - Băng thông")
    private Long dcBangthong;

    @Schema(description = "Data Card - ID thông tin module")
    private Long dcTtmoduleId;

    @Schema(description = "Data Card - ID mục đích sử dụng")
    private Long dcMucdichsdId;

    @Schema(description = "Khối đĩa - Số lượng")
    private Long kdSl;

    @Schema(description = "Khối đĩa - Mã tủ đĩa")
    private String kdMaTudia;

    @Schema(description = "Khối đĩa - STT")
    private Long kdStt;

    @Schema(description = "Khối đĩa - Số lượng SSD")
    private Long kdSlSsd;

    @Schema(description = "Khối đĩa - Số lượng HDD")
    private Long kdSlHdd;

    @Schema(description = "Khối đĩa - Số lượng NVMe")
    private Long kdSlNvme;

    @Schema(description = "Power Port - Số cổng")
    private Long ppSocong;

    @Schema(description = "Power Port - Tên cổng")
    private String ppTencong;

    @Schema(description = "Power Port - Dòng điện")
    private Long ppDongdien;

    @Schema(description = "Power Port - Điện áp")
    private Long ppDienap;

    @Schema(description = "Power Port - Loại nguồn điện")
    private Long ppLoaind;

    @Schema(description = "Power Port - ID chuẩn đấu nối")
    private Long ppChuandnId;

    @Schema(description = "Số lượng U lắp đặt")
    private Long soluongULd;

    @Schema(description = "Sử dụng (1: Hiệu lực, 0: Không hiệu lực)")
    private Integer sudung;

    @Schema(description = "Ghi chú")
    private String ghichu;

    @Schema(description = "Người cập nhật")
    private String nguoiCn;

    // Getters and Setters
    public Long getChungloaitdId() { return chungloaitdId; }
    public void setChungloaitdId(Long chungloaitdId) { this.chungloaitdId = chungloaitdId; }

    public String getMaVt() { return maVt; }
    public void setMaVt(String maVt) { this.maVt = maVt; }

    public Long getLoaitudiaId() { return loaitudiaId; }
    public void setLoaitudiaId(Long loaitudiaId) { this.loaitudiaId = loaitudiaId; }

    public String getModelThietbi() { return modelThietbi; }
    public void setModelThietbi(String modelThietbi) { this.modelThietbi = modelThietbi; }

    public Long getHangsxId() { return hangsxId; }
    public void setHangsxId(Long hangsxId) { this.hangsxId = hangsxId; }

    public Long getCongsuat() { return congsuat; }
    public void setCongsuat(Long congsuat) { this.congsuat = congsuat; }

    public Long getLoaivaitroId() { return loaivaitroId; }
    public void setLoaivaitroId(Long loaivaitroId) { this.loaivaitroId = loaivaitroId; }

    public Long getFullDepth() { return fullDepth; }
    public void setFullDepth(Long fullDepth) { this.fullDepth = fullDepth; }

    public Long getBodkSl() { return bodkSl; }
    public void setBodkSl(Long bodkSl) { this.bodkSl = bodkSl; }

    public Long getBodkCpu() { return bodkCpu; }
    public void setBodkCpu(Long bodkCpu) { this.bodkCpu = bodkCpu; }

    public Long getBodkRam() { return bodkRam; }
    public void setBodkRam(Long bodkRam) { this.bodkRam = bodkRam; }

    public String getBodkMaVt() { return bodkMaVt; }
    public void setBodkMaVt(String bodkMaVt) { this.bodkMaVt = bodkMaVt; }

    public Long getDcSocong() { return dcSocong; }
    public void setDcSocong(Long dcSocong) { this.dcSocong = dcSocong; }

    public String getDcTencong() { return dcTencong; }
    public void setDcTencong(String dcTencong) { this.dcTencong = dcTencong; }

    public String getDcLoaicong() { return dcLoaicong; }
    public void setDcLoaicong(String dcLoaicong) { this.dcLoaicong = dcLoaicong; }

    public Long getDcBangthong() { return dcBangthong; }
    public void setDcBangthong(Long dcBangthong) { this.dcBangthong = dcBangthong; }

    public Long getDcTtmoduleId() { return dcTtmoduleId; }
    public void setDcTtmoduleId(Long dcTtmoduleId) { this.dcTtmoduleId = dcTtmoduleId; }

    public Long getDcMucdichsdId() { return dcMucdichsdId; }
    public void setDcMucdichsdId(Long dcMucdichsdId) { this.dcMucdichsdId = dcMucdichsdId; }

    public Long getKdSl() { return kdSl; }
    public void setKdSl(Long kdSl) { this.kdSl = kdSl; }

    public String getKdMaTudia() { return kdMaTudia; }
    public void setKdMaTudia(String kdMaTudia) { this.kdMaTudia = kdMaTudia; }

    public Long getKdStt() { return kdStt; }
    public void setKdStt(Long kdStt) { this.kdStt = kdStt; }

    public Long getKdSlSsd() { return kdSlSsd; }
    public void setKdSlSsd(Long kdSlSsd) { this.kdSlSsd = kdSlSsd; }

    public Long getKdSlHdd() { return kdSlHdd; }
    public void setKdSlHdd(Long kdSlHdd) { this.kdSlHdd = kdSlHdd; }

    public Long getKdSlNvme() { return kdSlNvme; }
    public void setKdSlNvme(Long kdSlNvme) { this.kdSlNvme = kdSlNvme; }

    public Long getPpSocong() { return ppSocong; }
    public void setPpSocong(Long ppSocong) { this.ppSocong = ppSocong; }

    public String getPpTencong() { return ppTencong; }
    public void setPpTencong(String ppTencong) { this.ppTencong = ppTencong; }

    public Long getPpDongdien() { return ppDongdien; }
    public void setPpDongdien(Long ppDongdien) { this.ppDongdien = ppDongdien; }

    public Long getPpDienap() { return ppDienap; }
    public void setPpDienap(Long ppDienap) { this.ppDienap = ppDienap; }

    public Long getPpLoaind() { return ppLoaind; }
    public void setPpLoaind(Long ppLoaind) { this.ppLoaind = ppLoaind; }

    public Long getPpChuandnId() { return ppChuandnId; }
    public void setPpChuandnId(Long ppChuandnId) { this.ppChuandnId = ppChuandnId; }

    public Long getSoluongULd() { return soluongULd; }
    public void setSoluongULd(Long soluongULd) { this.soluongULd = soluongULd; }

    public Integer getSudung() { return sudung; }
    public void setSudung(Integer sudung) { this.sudung = sudung; }

    public String getGhichu() { return ghichu; }
    public void setGhichu(String ghichu) { this.ghichu = ghichu; }

    public String getNguoiCn() { return nguoiCn; }
    public void setNguoiCn(String nguoiCn) { this.nguoiCn = nguoiCn; }
}


