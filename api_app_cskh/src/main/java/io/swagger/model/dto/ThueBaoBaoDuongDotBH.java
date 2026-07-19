package io.swagger.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Thuê bao bảo dưỡng đợt bán hàng
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ThueBaoBaoDuongDotBH {

    private Long dotBhId;
    private Long baoHongId;
    private String maTb;
    private String diaChi;

    public ThueBaoBaoDuongDotBH convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            dotBhId = rs.getLong("DOTBH_ID");
            baoHongId = rs.getLong("BAOHONG_ID");
            maTb = rs.getString("MA_TB");
            diaChi = rs.getString("DIACHI");
            return this;
        } else {
            return null;
        }
    }

    public Long getDotBhId() {
        return dotBhId;
    }

    public void setDotBhId(Long dotBhId) {
        this.dotBhId = dotBhId;
    }

    public Long getBaoHongId() {
        return baoHongId;
    }

    public void setBaoHongId(Long baoHongId) {
        this.baoHongId = baoHongId;
    }

    public String getMaTb() {
        return maTb;
    }

    public void setMaTb(String maTb) {
        this.maTb = maTb;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
