package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;
import java.util.Date;

public class CumSPDVDto implements Serializable {
    
    // Parameters for SP_GET_DS_CUM_SPDV
    private String tenCumSpdv;
    private Long hinhthucCungcapId;
    private Long trangthaihdId;
    private Long hieulucId;
    private Long tenspdvId;
    private Long hatangTrienkhaiId;
    private Long donviQlId;
    private Long chuquanDvId;
    private Date ngaytaoTu;
    private Date ngaytaoDen;
    private String canhanQl;
    private Integer checkQuyen = 0; // 1: áp dụng phân quyền, 0: bỏ qua phân quyền (xem tất cả)
    
    // Parameters for SP_XOA_CUM_SPDV
    private Long cumspdvId;
    
    // Parameters for SP_GET_DS_SPDV
    private Long id1;
    private Long id2;
    
    public CumSPDVDto() {}

    public String getTenCumSpdv() {
        return tenCumSpdv;
    }

    public void setTenCumSpdv(String tenCumSpdv) {
        this.tenCumSpdv = tenCumSpdv;
    }

    public Long getHinhthucCungcapId() {
        return hinhthucCungcapId;
    }

    public void setHinhthucCungcapId(Long hinhthucCungcapId) {
        this.hinhthucCungcapId = hinhthucCungcapId;
    }

    public Long getTrangthaihdId() {
        return trangthaihdId;
    }

    public void setTrangthaihdId(Long trangthaihdId) {
        this.trangthaihdId = trangthaihdId;
    }

    public Long getHieulucId() {
        return hieulucId;
    }

    public void setHieulucId(Long hieulucId) {
        this.hieulucId = hieulucId;
    }

    public Long getTenspdvId() {
        return tenspdvId;
    }

    public void setTenspdvId(Long tenspdvId) {
        this.tenspdvId = tenspdvId;
    }

    public Long getHatangTrienkhaiId() {
        return hatangTrienkhaiId;
    }

    public void setHatangTrienkhaiId(Long hatangTrienkhaiId) {
        this.hatangTrienkhaiId = hatangTrienkhaiId;
    }

    public Long getDonviQlId() {
        return donviQlId;
    }

    public void setDonviQlId(Long donviQlId) {
        this.donviQlId = donviQlId;
    }

    public Long getChuquanDvId() {
        return chuquanDvId;
    }

    public void setChuquanDvId(Long chuquanDvId) {
        this.chuquanDvId = chuquanDvId;
    }

    public Date getNgaytaoTu() {
        return ngaytaoTu;
    }

    public void setNgaytaoTu(Date ngaytaoTu) {
        this.ngaytaoTu = ngaytaoTu;
    }

    public Date getNgaytaoDen() {
        return ngaytaoDen;
    }

    public void setNgaytaoDen(Date ngaytaoDen) {
        this.ngaytaoDen = ngaytaoDen;
    }

    public String getCanhanQl() {
        return canhanQl;
    }

    public void setCanhanQl(String canhanQl) {
        this.canhanQl = canhanQl;
    }

    public Integer getCheckQuyen() {
        return checkQuyen;
    }

    public void setCheckQuyen(Integer checkQuyen) {
        this.checkQuyen = checkQuyen;
    }

    public Long getCumspdvId() {
        return cumspdvId;
    }

    public void setCumspdvId(Long cumspdvId) {
        this.cumspdvId = cumspdvId;
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }
}
