package io.swagger.model.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.annotation.Generated;

/**
 * Thêm mới khu vực tác chiến request
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThemKhuVucTacChienRequest {

    @JsonProperty("khuVucId")
    private Long khuVucId;

    @JsonProperty("dotBhId")
    private Long dotBhId;

    @JsonProperty("ngayBd")
    private String ngayBd;

    @JsonProperty("ngayKt")
    private String ngayKt;

    public Long getKhuVucId() {
        return khuVucId;
    }

    public void setKhuVucId(Long khuVucId) {
        this.khuVucId = khuVucId;
    }

    public Long getDotBhId() {
        return dotBhId;
    }

    public void setDotBhId(Long dotBhId) {
        this.dotBhId = dotBhId;
    }

    public String getNgayBd() {
        return ngayBd;
    }

    public void setNgayBd(String ngayBd) {
        this.ngayBd = ngayBd;
    }

    public String getNgayKt() {
        return ngayKt;
    }

    public void setNgayKt(String ngayKt) {
        this.ngayKt = ngayKt;
    }
}
