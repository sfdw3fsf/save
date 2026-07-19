package vn.vnpt.vnptit.ecms.dto;

import org.hibernate.validator.constraints.Range;
import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotNull;

public class CapNhatHuongKetNoiDTO {
    @NotNull(message = ModelValidation.NOT_NULL)
    private long port;
    @Range(min = 1, max = 2, message = "1: Hướng chính; 2: Hướng phụ")
    private int huong_ket_noi;

    public long getPort() {
        return port;
    }
    public void setPort(long port) {
        this.port = port;
    }

    public int getHuong_ket_noi() {
        return huong_ket_noi;
    }
    public void setHuong_ket_noi(int huong_ket_noi) {
        this.huong_ket_noi = huong_ket_noi;
    }
}
