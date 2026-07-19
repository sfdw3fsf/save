package vn.vnpt.vnptit.ecms.dto.hatang;

import java.io.Serializable;

public class CumSpdvTheoDoiTuongDto implements Serializable {

    private Long id;
    private String type;

    public CumSpdvTheoDoiTuongDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
