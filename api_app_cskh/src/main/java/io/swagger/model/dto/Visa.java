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
public class Visa {

    private String loaiTaiKhoan;

    private String accountNumber;

    private String accountName;

    private String email;

    private String ngayTao;

    private String pass;

    private String tinh;

    private String tenKh;

    private String diaChiKh;

    private String sdt;

    private String status;

    private String service;

    private String serviceId;

    private Long dslamId;

    private String jnprIpv6DelegatedPoolName;

    private Long brasId;

    private String framedIpv6Pool;

    private String jnprIpv6EgressPolicyName;

    private String typeId;


    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTinh() {
        return tinh;
    }

    public void setTinh(String tinh) {
        this.tinh = tinh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getDiaChiKh() {
        return diaChiKh;
    }

    public void setDiaChiKh(String diaChiKh) {
        this.diaChiKh = diaChiKh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDslamId() {
        return dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public String getJnprIpv6DelegatedPoolName() {
        return jnprIpv6DelegatedPoolName;
    }

    public void setJnprIpv6DelegatedPoolName(String jnprIpv6DelegatedPoolName) {
        this.jnprIpv6DelegatedPoolName = jnprIpv6DelegatedPoolName;
    }

    public Long getBrasId() {
        return brasId;
    }

    public void setBrasId(Long brasId) {
        this.brasId = brasId;
    }

    public String getFramedIpv6Pool() {
        return framedIpv6Pool;
    }

    public void setFramedIpv6Pool(String framedIpv6Pool) {
        this.framedIpv6Pool = framedIpv6Pool;
    }

    public String getJnprIpv6EgressPolicyName() {
        return jnprIpv6EgressPolicyName;
    }

    public void setJnprIpv6EgressPolicyName(String jnprIpv6EgressPolicyName) {
        this.jnprIpv6EgressPolicyName = jnprIpv6EgressPolicyName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
