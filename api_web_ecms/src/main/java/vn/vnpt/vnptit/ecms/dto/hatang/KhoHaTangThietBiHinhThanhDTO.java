package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class KhoHaTangThietBiHinhThanhDTO {
    private int id;     //ha_tang_id
    private int thietbi_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date ngay_cn;

    public int getId() {
        return id;
    }

    public int getThietbi_id() {
        return thietbi_id;
    }

    public Date getNgay_cn() {
        return ngay_cn;
    }
}
