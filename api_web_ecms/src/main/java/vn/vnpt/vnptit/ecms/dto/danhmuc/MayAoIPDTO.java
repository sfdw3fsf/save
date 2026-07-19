package vn.vnpt.vnptit.ecms.dto.danhmuc;

import java.io.Serializable;

public class MayAoIPDTO implements Serializable {
    private Number id;
    private String ip_address_id_list;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getIp_address_id_list() {
        return ip_address_id_list;
    }

    public void setIp_address_id_list(String ip_address_id_list) {
        this.ip_address_id_list = ip_address_id_list;
    }
}
