package io.swagger.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Generated;

/**
 * Thông tin port
 */
@Validated
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Schema
public class Port {

    private String status;

    private String account;

    private String soAo;

    private String dsLamSw;

    private String port;

    private String onuType;

    private String onuSerial;

    private String onuRxTx;

    private String oltRxTx;

    private String speed;

    private String chatLuong;

    private String moTa;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSoAo() {
        return soAo;
    }

    public void setSoAo(String soAo) {
        this.soAo = soAo;
    }

    public String getDsLamSw() {
        return dsLamSw;
    }

    public void setDsLamSw(String dsLamSw) {
        this.dsLamSw = dsLamSw;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOnuType() {
        return onuType;
    }

    public void setOnuType(String onuType) {
        this.onuType = onuType;
    }

    public String getOnuSerial() {
        return onuSerial;
    }

    public void setOnuSerial(String onuSerial) {
        this.onuSerial = onuSerial;
    }

    public String getOnuRxTx() {
        return onuRxTx;
    }

    public void setOnuRxTx(String onuRxTx) {
        this.onuRxTx = onuRxTx;
    }

    public String getOltRxTx() {
        return oltRxTx;
    }

    public void setOltRxTx(String oltRxTx) {
        this.oltRxTx = oltRxTx;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getChatLuong() {
        return chatLuong;
    }

    public void setChatLuong(String chatLuong) {
        this.chatLuong = chatLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
