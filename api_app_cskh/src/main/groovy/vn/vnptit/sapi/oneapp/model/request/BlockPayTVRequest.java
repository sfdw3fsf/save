package vn.vnptit.sapi.oneapp.model.request;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Block PayTV request
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class BlockPayTVRequest {
    ObjectRequest objRequest;

    public ObjectRequest getObjRequest() {
        return objRequest;
    }

    public void setObjRequest(ObjectRequest objRequest) {
        this.objRequest = objRequest;
    }
}

class ObjectRequest {
    private String allService;
    private String binhChon;
    private String caNhac;
    private String ceeme;
    private String cloudGame;
    private String danet;
    private String daoTaoTuXa;
    private String docTruyen;
    private String fafilmFLG;
    private String fimPlus;
    private String giaoDucDaoTao;
    private String hboflg;
    private String karaoke;
    private String ketQuaXoSo;
    private String kplusFLG;
    private String myTVID;
    private String myTVShow;
    private String nhipCauMyTV;
    private Integer operator;
    private String phimTruyen;
    private String phongCachSong;
    private String reason;
    private String sucKhoeLamDep;
    private String theThao;
    private String thieuNhi;
    private String troChoi;
    private String vtvcabFlg;

    public String getAllService() {
        return allService;
    }

    public void setAllService(String allService) {
        this.allService = allService;
    }

    public String getBinhChon() {
        return binhChon;
    }

    public void setBinhChon(String binhChon) {
        this.binhChon = binhChon;
    }

    public String getCaNhac() {
        return caNhac;
    }

    public void setCaNhac(String caNhac) {
        this.caNhac = caNhac;
    }

    public String getCeeme() {
        return ceeme;
    }

    public void setCeeme(String ceeme) {
        this.ceeme = ceeme;
    }

    public String getCloudGame() {
        return cloudGame;
    }

    public void setCloudGame(String cloudGame) {
        this.cloudGame = cloudGame;
    }

    public String getDanet() {
        return danet;
    }

    public void setDanet(String danet) {
        this.danet = danet;
    }

    public String getDaoTaoTuXa() {
        return daoTaoTuXa;
    }

    public void setDaoTaoTuXa(String daoTaoTuXa) {
        this.daoTaoTuXa = daoTaoTuXa;
    }

    public String getDocTruyen() {
        return docTruyen;
    }

    public void setDocTruyen(String docTruyen) {
        this.docTruyen = docTruyen;
    }

    public String getFafilmFLG() {
        return fafilmFLG;
    }

    public void setFafilmFLG(String fafilmFLG) {
        this.fafilmFLG = fafilmFLG;
    }

    public String getFimPlus() {
        return fimPlus;
    }

    public void setFimPlus(String fimPlus) {
        this.fimPlus = fimPlus;
    }

    public String getGiaoDucDaoTao() {
        return giaoDucDaoTao;
    }

    public void setGiaoDucDaoTao(String giaoDucDaoTao) {
        this.giaoDucDaoTao = giaoDucDaoTao;
    }

    public String getHboflg() {
        return hboflg;
    }

    public void setHboflg(String hboflg) {
        this.hboflg = hboflg;
    }

    public String getKaraoke() {
        return karaoke;
    }

    public void setKaraoke(String karaoke) {
        this.karaoke = karaoke;
    }

    public String getKetQuaXoSo() {
        return ketQuaXoSo;
    }

    public void setKetQuaXoSo(String ketQuaXoSo) {
        this.ketQuaXoSo = ketQuaXoSo;
    }

    public String getKplusFLG() {
        return kplusFLG;
    }

    public void setKplusFLG(String kplusFLG) {
        this.kplusFLG = kplusFLG;
    }

    public String getMyTVID() {
        return myTVID;
    }

    public void setMyTVID(String myTVID) {
        this.myTVID = myTVID;
    }

    public String getMyTVShow() {
        return myTVShow;
    }

    public void setMyTVShow(String myTVShow) {
        this.myTVShow = myTVShow;
    }

    public String getNhipCauMyTV() {
        return nhipCauMyTV;
    }

    public void setNhipCauMyTV(String nhipCauMyTV) {
        this.nhipCauMyTV = nhipCauMyTV;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getPhimTruyen() {
        return phimTruyen;
    }

    public void setPhimTruyen(String phimTruyen) {
        this.phimTruyen = phimTruyen;
    }

    public String getPhongCachSong() {
        return phongCachSong;
    }

    public void setPhongCachSong(String phongCachSong) {
        this.phongCachSong = phongCachSong;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSucKhoeLamDep() {
        return sucKhoeLamDep;
    }

    public void setSucKhoeLamDep(String sucKhoeLamDep) {
        this.sucKhoeLamDep = sucKhoeLamDep;
    }

    public String getTheThao() {
        return theThao;
    }

    public void setTheThao(String theThao) {
        this.theThao = theThao;
    }

    public String getThieuNhi() {
        return thieuNhi;
    }

    public void setThieuNhi(String thieuNhi) {
        this.thieuNhi = thieuNhi;
    }

    public String getTroChoi() {
        return troChoi;
    }

    public void setTroChoi(String troChoi) {
        this.troChoi = troChoi;
    }

    public String getVtvcabFlg() {
        return vtvcabFlg;
    }

    public void setVtvcabFlg(String vtvcabFlg) {
        this.vtvcabFlg = vtvcabFlg;
    }
}
