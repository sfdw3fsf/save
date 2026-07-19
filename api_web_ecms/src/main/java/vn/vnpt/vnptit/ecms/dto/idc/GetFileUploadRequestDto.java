package vn.vnpt.vnptit.ecms.dto.idc;

import java.io.Serializable;

public class GetFileUploadRequestDto implements Serializable {
    private Long hosoId;
    private Long fileId;

    public GetFileUploadRequestDto() {
    }

    public Long getHosoId() {
        return hosoId;
    }

    public void setHosoId(Long hosoId) {
        this.hosoId = hosoId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
