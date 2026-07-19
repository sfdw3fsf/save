package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class LoaiCVRequestDto implements Serializable {
    private Long nhomcvId;

    public LoaiCVRequestDto() {
    }

    public Long getNhomcvId() {
        return nhomcvId;
    }

    public void setNhomcvId(Long nhomcvId) {
        this.nhomcvId = nhomcvId;
    }
}
