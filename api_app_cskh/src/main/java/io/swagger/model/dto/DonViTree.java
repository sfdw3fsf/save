package io.swagger.model.dto;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Đơn vị tree
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DonViTree extends BaseDTO {

    private Long donViId;
    private String tenDv;
    private Long donViChaId;
    private int unitLevel;

    public DonViTree convertDataFromDatabase(ResultSet rs) throws SQLException {
        if (rs != null) {
            donViId = rs.getLong("DONVI_ID");
            tenDv = rs.getString("TEN_DV");
            donViChaId = rs.getLong("DONVI_CHA_ID");
            unitLevel = rs.getInt("UNITLEVEL");
            return this;
        } else {
            return null;
        }
    }

    public Long getDonViId() {
        return donViId;
    }

    public void setDonViId(Long donViId) {
        this.donViId = donViId;
    }

    public String getTenDv() {
        return tenDv;
    }

    public void setTenDv(String tenDv) {
        this.tenDv = tenDv;
    }

    public Long getDonViChaId() {
        return donViChaId;
    }

    public void setDonViChaId(Long donViChaId) {
        this.donViChaId = donViChaId;
    }

    public int getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }
}
