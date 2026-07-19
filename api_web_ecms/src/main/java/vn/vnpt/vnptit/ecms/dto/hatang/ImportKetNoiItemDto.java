package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO cho từng hạng mục kết nối khi import")
public class ImportKetNoiItemDto {

    @Schema(description = "Số thứ tự")
    @JsonProperty("stt")
    private Integer stt;

    @Schema(description = "Mã tài sản thiết bị nguồn (MA_TS)")
    @JsonProperty("ma_tb_nguon")
    private String maTbNguon;

    @Schema(description = "Tên port nguồn (Hiển thị)")
    @JsonProperty("ten_port_nguon")
    private String tenPortNguon;


    @Schema(description = "Mô tả switch")
    @JsonProperty("description_switch")
    private String descriptionSwitch;

    @Schema(description = "Mã tài sản thiết bị đích (MA_TS)")
    @JsonProperty("ma_tb_dich")
    private String maTbDich;

    @Schema(description = "Tên port đích (Hiển thị)")
    @JsonProperty("ten_port_dich")
    private String tenPortDich;


    @Schema(description = "ID loại kết nối")
    @JsonProperty("loai_ketnoi_id")
    private Long loaiKetNoiId;

    @Schema(description = "ID lớp kết nối")
    @JsonProperty("lop_ketnoi_id")
    private Long lopKetNoiId;

    @Schema(description = "ID loại cổng nguồn")
    @JsonProperty("loai_cong_nguon_id")
    private Long loaiCongNguonId;

    @Schema(description = "ID loại cổng đích")
    @JsonProperty("loai_cong_dich_id")
    private Long loaiCongDichId;

    @Schema(description = "Ghi chú")
    @JsonProperty("ghi_chu")
    private String ghiChu;

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public String getMaTbNguon() {
        return maTbNguon;
    }

    public void setMaTbNguon(String maTbNguon) {
        this.maTbNguon = maTbNguon;
    }

    public String getTenPortNguon() {
        return tenPortNguon;
    }

    public void setTenPortNguon(String tenPortNguon) {
        this.tenPortNguon = tenPortNguon;
    }

    public String getDescriptionSwitch() {
        return descriptionSwitch;
    }

    public void setDescriptionSwitch(String descriptionSwitch) {
        this.descriptionSwitch = descriptionSwitch;
    }

    public String getMaTbDich() {
        return maTbDich;
    }

    public void setMaTbDich(String maTbDich) {
        this.maTbDich = maTbDich;
    }

    public String getTenPortDich() {
        return tenPortDich;
    }

    public void setTenPortDich(String tenPortDich) {
        this.tenPortDich = tenPortDich;
    }

    public Long getLoaiKetNoiId() {
        return loaiKetNoiId;
    }

    public void setLoaiKetNoiId(Long loaiKetNoiId) {
        this.loaiKetNoiId = loaiKetNoiId;
    }

    public Long getLopKetNoiId() {
        return lopKetNoiId;
    }

    public void setLopKetNoiId(Long lopKetNoiId) {
        this.lopKetNoiId = lopKetNoiId;
    }

    public Long getLoaiCongNguonId() {
        return loaiCongNguonId;
    }

    public void setLoaiCongNguonId(Long loaiCongNguonId) {
        this.loaiCongNguonId = loaiCongNguonId;
    }

    public Long getLoaiCongDichId() {
        return loaiCongDichId;
    }

    public void setLoaiCongDichId(Long loaiCongDichId) {
        this.loaiCongDichId = loaiCongDichId;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}
