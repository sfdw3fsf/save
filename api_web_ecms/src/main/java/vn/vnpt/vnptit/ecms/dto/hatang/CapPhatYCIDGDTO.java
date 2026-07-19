package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CapPhatYCIDGDTO {
    private Number id;
    private Number capphat_yc_id;
    private Number sothutu;
    private Number thanhphan;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ngaytao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate batdau;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ketthuc;
    private String ma_rabbitmq;
    private Number idg_object;
    private String ma_kafka;
    private String ma_api_gateway;
    private String ma_mongo;
    private String ma_mysql;
    private String ma_search_engine;

    public Number getId() {
        return id;
    }

    public Number getCapphat_yc_id() {
        return capphat_yc_id;
    }

    public Number getSothutu() {
        return sothutu;
    }

    public Number getThanhphan() {
        return thanhphan;
    }

    public LocalDate getNgaytao() {
        return ngaytao;
    }

    public LocalDate getBatdau() {
        return batdau;
    }

    public LocalDate getKetthuc() {
        return ketthuc;
    }

    public String getMa_rabbitmq() {
        return ma_rabbitmq;
    }

    public Number getIdg_object() {
        return idg_object;
    }

    public String getMa_kafka() {
        return ma_kafka;
    }

    public String getMa_api_gateway() {
        return ma_api_gateway;
    }

    public String getMa_mongo() {
        return ma_mongo;
    }

    public String getMa_mysql() {
        return ma_mysql;
    }

    public String getMa_search_engine() {
        return ma_search_engine;
    }
}
