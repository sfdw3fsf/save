package vn.vnpt.vnptit.ecms.dto.hatang;

public class ThietBiPortKhaiBao_DTO {
    private Number id = 0;
    private Number thietbi_id;
    private Number phanloai;
    private Number loaicong_id;
    private Number so_cong;
    private Number bat_dau;
    private String port_fname;
    private Number mucdichsudung_cong_id;
    private Number bangthongcong_id;
    private Number thongtinmodule_id;
    private Number ketnoi;

    public Number getId() {
        return id;
    }

    public Number getThietbi_id() {
        return thietbi_id;
    }

    public Number getPhanloai() {
        return phanloai;
    }

    public Number getLoaicong_id() {
        return loaicong_id;
    }

    public Number getSo_cong() {
        return so_cong;
    }

    public Number getBat_dau() {
        return bat_dau;
    }

    public Number getMucdichsudung_cong_id() {
        return mucdichsudung_cong_id;
    }

    public Number getBangthongcong_id() {
        return bangthongcong_id;
    }

    public Number getThongtinmodule_id() {
        return thongtinmodule_id;
    }

    public Number getKetnoi() {
        return ketnoi;
    }

    public String getPort_fname() {
        return port_fname;
    }
}
