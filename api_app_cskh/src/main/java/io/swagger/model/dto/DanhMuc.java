package io.swagger.model.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class DanhMuc {
    @JsonProperty("ID")
    private Long ID = null;
    @JsonProperty("NAME")
    private String NAME = null;
    @JsonProperty("CODE")
    private String CODE = null;
    @JsonProperty("DESCRIPTION")
    private String DESCRIPTION = null;

    @JsonIgnore
    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    @JsonIgnore
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    @JsonIgnore
    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    @JsonIgnore
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public DanhMuc NAME(String NAME) {
        this.setNAME(NAME);
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DanhMuc danhMuc = (DanhMuc) o;
        return Objects.equals(this.getID(), danhMuc.getID()) &&
                Objects.equals(this.getNAME(), danhMuc.getNAME()) &&
                Objects.equals(this.getCODE(), danhMuc.getCODE());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getNAME(), getCODE());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DanhMuc {\n");
        sb.append("    id: ").append(toIndentedString(getID())).append("\n");
        sb.append("    name: ").append(toIndentedString(getNAME())).append("\n");
        sb.append("    code: ").append(toIndentedString(getCODE())).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        } else {
            return o.toString();
        }
    }
}
