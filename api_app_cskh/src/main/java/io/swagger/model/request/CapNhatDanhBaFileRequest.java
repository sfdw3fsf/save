package io.swagger.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * CapNhatDanhBaFileRequest
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class CapNhatDanhBaFileRequest {
    @JsonProperty("pStorageFile")
    private String pStorageFile;
    @JsonProperty("pDbId")
    private String pDbId;
    @JsonProperty("pUrls")
    private String pUrls;
    @JsonProperty("pLoaiFileId")
    private String pLoaiFileId;
    @JsonProperty("pNoiThem")
    private Integer pNoiThem;

    public CapNhatDanhBaFileRequest(String pStorageFile, String pDbId, String pUrls, String pLoaiFileId, Integer pNoiThem) {
        this.pStorageFile = pStorageFile;
        this.pDbId = pDbId;
        this.pUrls = pUrls;
        this.pLoaiFileId = pLoaiFileId;
        this.pNoiThem = pNoiThem;
    }

    public CapNhatDanhBaFileRequest() {
    }

    public String getpStorageFile() {
        return pStorageFile;
    }

    public void setpStorageFile(String pStorageFile) {
        this.pStorageFile = pStorageFile;
    }

    public String getpDbId() {
        return pDbId;
    }

    public void setpDbId(String pDbId) {
        this.pDbId = pDbId;
    }

    public String getpUrls() {
        return pUrls;
    }

    public void setpUrls(String pUrls) {
        this.pUrls = pUrls;
    }

    public String getpLoaiFileId() {
        return pLoaiFileId;
    }

    public void setpLoaiFileId(String pLoaiFileId) {
        this.pLoaiFileId = pLoaiFileId;
    }

    public Integer getpNoiThem() {
        return pNoiThem;
    }

    public void setpNoiThem(Integer pNoiThem) {
        this.pNoiThem = pNoiThem;
    }
}
