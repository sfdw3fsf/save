package vn.vnpt.vnptit.ecms.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY, getterVisibility=JsonAutoDetect.Visibility.NONE, setterVisibility=JsonAutoDetect.Visibility.NONE, creatorVisibility=JsonAutoDetect.Visibility.NONE)
public class CapNhatGanCongCatChuyen {
    private Long Kieu;
    private Long HsccId;
    private String Json_list;


    public Long getKieu() {
        return Kieu;
    }

    public void setKieu(Long kieu) {
        Kieu = kieu;
    }

    public Long getHsccId() {
        return HsccId;
    }

    public void setHsccId(Long hsccId) {
        HsccId = hsccId;
    }

    public String getJson_list() {
        return Json_list;
    }

    public void setJson_list(String json_list) {
        Json_list = json_list;
    }
}
