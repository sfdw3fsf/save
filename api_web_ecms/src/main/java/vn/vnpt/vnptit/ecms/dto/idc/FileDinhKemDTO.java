package vn.vnpt.vnptit.ecms.dto.idc;

import vn.vnpt.common.Utils;
import vn.vnpt.common.exception.BadRequestExceptionMessage;

public class FileDinhKemDTO {
    private String ten;
    private String path;

    public void valid() {
        if (Utils.isEmpty(this.ten) || Utils.isEmpty(this.path)) {
            throw new BadRequestExceptionMessage("Dữ liệu đầu vào không hợp lệ!");
        }
    }

    public String getPath() {
        return path;
    }

    public String getTen() {
        return ten;
    }
}
