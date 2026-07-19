package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Giao phiếu báo hỏng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class HoSoKN {
    @JsonProperty("stt")
    private String stt = null;
    @JsonProperty("hoSoId")
    private Long hoSoId = null;
    @JsonProperty("tenFile")
    private String tenFile = null;
    @JsonProperty("serverUrl")
    private String serverUrl = null;
    @JsonProperty("clientUrl")
    private String clientUrl = null;
    @JsonProperty("ipServer")
    private String ipServer = null;
    @JsonProperty("ngayHs")
    private String ngayHs = null;
    @JsonProperty("nguoiCn")
    private String nguoiCn = null;

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public Long getHoSoId() {
        return hoSoId;
    }

    public void setHoSoId(Long hoSoId) {
        this.hoSoId = hoSoId;
    }

    public String getTenFile() {
        return tenFile;
    }

    public void setTenFile(String tenFile) {
        this.tenFile = tenFile;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public String getNgayHs() {
        return ngayHs;
    }

    public void setNgayHs(String ngayHs) {
        this.ngayHs = ngayHs;
    }

    public String getNguoiCn() {
        return nguoiCn;
    }

    public void setNguoiCn(String nguoiCn) {
        this.nguoiCn = nguoiCn;
    }
}
