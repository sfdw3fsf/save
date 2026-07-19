package io.swagger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;
import java.util.List;

/**
 * DanhMuc
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class ChuDeKN extends BaseDTO {
    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("group2")
    private String group2 = null;
    @JsonProperty("group2id")
    private String group2id = null;
    @JsonProperty("group1id")
    private String group1id = null;
    @JsonProperty("group1")
    private String group1 = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup2() {
        return group2;
    }

    public void setGroup2(String group2) {
        this.group2 = group2;
    }

    public String getGroup2id() {
        return group2id;
    }

    public void setGroup2id(String group2id) {
        this.group2id = group2id;
    }

    public String getGroup1id() {
        return group1id;
    }

    public void setGroup1id(String group1id) {
        this.group1id = group1id;
    }

    public String getGroup1() {
        return group1;
    }

    public void setGroup1(String group1) {
        this.group1 = group1;
    }
}
