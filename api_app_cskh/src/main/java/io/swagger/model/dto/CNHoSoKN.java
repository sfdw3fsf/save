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
public class CNHoSoKN {
    @JsonProperty("tenFile")
    private String tenFile = null;
    @JsonProperty("khieuNaiId")
    private Long khieuNaiId = null;
    @JsonProperty("hoSoKNId")
    private Long hoSoKNId = null;
    @JsonProperty("serverUrl")
    private String serverUrl = null;
    @JsonProperty("ipServer")
    private String ipServer = null;

    public String getTenFile() {
        return tenFile;
    }

    public void setTenFile(String tenFile) {
        this.tenFile = tenFile;
    }

    public Long getKhieuNaiId() {
        return khieuNaiId;
    }

    public void setKhieuNaiId(Long khieuNaiId) {
        this.khieuNaiId = khieuNaiId;
    }

    public Long getHoSoKNId() {
        return hoSoKNId;
    }

    public void setHoSoKNId(Long hoSoKNId) {
        this.hoSoKNId = hoSoKNId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }
}
