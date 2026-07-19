package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Thông tin visa
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class Vtn {

    private String nodeMtas;

    private String nodeHss;

    private String nodeIpw;

    private String tenThueBao;

    private String ngayTao;

    private String loaiHinhTb;

    private String diaChiLd;

    private String pass;

    private String status;

    public String getNodeMtas() {
        return nodeMtas;
    }

    public void setNodeMtas(String nodeMtas) {
        this.nodeMtas = nodeMtas;
    }

    public String getNodeHss() {
        return nodeHss;
    }

    public void setNodeHss(String nodeHss) {
        this.nodeHss = nodeHss;
    }

    public String getNodeIpw() {
        return nodeIpw;
    }

    public void setNodeIpw(String nodeIpw) {
        this.nodeIpw = nodeIpw;
    }

    public String getTenThueBao() {
        return tenThueBao;
    }

    public void setTenThueBao(String tenThueBao) {
        this.tenThueBao = tenThueBao;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getLoaiHinhTb() {
        return loaiHinhTb;
    }

    public void setLoaiHinhTb(String loaiHinhTb) {
        this.loaiHinhTb = loaiHinhTb;
    }

    public String getDiaChiLd() {
        return diaChiLd;
    }

    public void setDiaChiLd(String diaChiLd) {
        this.diaChiLd = diaChiLd;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
