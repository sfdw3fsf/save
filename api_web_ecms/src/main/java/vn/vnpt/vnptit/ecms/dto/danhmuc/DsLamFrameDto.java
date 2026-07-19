package vn.vnpt.vnptit.ecms.dto.danhmuc;

import vn.vnpt.message.ModelValidation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DsLamFrameDto {
    static public class DslamFrameUpd  extends  DslamFrameIns{
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer frameDslId;
        public Integer getFrameDslId() {
            return frameDslId;
        }

        public void setFrameDslId(Integer frameDslId) {
            this.frameDslId = frameDslId;
        }
    }
    static public class DslamFrameIns {
        @NotEmpty(message = ModelValidation.NOT_EMPTY)
        private String tenFrame;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer dsLamId;
        @NotNull(message = ModelValidation.NOT_NULL)
        private Integer viTri;

        public String getTenFrame() {
            return tenFrame;
        }

        public void setTenFrame(String tenFrame) {
            this.tenFrame = tenFrame;
        }

        public Integer getDsLamId() {
            return dsLamId;
        }

        public void setDsLamId(Integer dsLamId) {
            this.dsLamId = dsLamId;
        }

        public Integer getViTri() {
            return viTri;
        }

        public void setViTri(Integer viTri) {
            this.viTri = viTri;
        }
    }

}
