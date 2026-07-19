package com.vn.vnptit.Oneapp_Com.model;

import com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu.HoaDonDienTuAdjustInvActionInput;

public class AdjustInvoiceActionInput {
    private String xmlInvData;
    private String fkey;
    private String attachFile;
    private String convert;
    private String pattern;
    private String serial;

    public String getXmlInvData() {
        return xmlInvData;
    }

    public void setXmlInvData(String xmlInvData) {
        this.xmlInvData = xmlInvData;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getConvert() {
        return convert;
    }

    public void setConvert(String convert) {
        this.convert = convert;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public AdjustInvoiceActionInput(HoaDonDienTuAdjustInvActionInput input) {
        this.xmlInvData = input.toXmlV2();
        this.fkey = input.getFkey();
        this.attachFile = input.getAttachFile();
        this.convert = String.valueOf(input.getConvert());
        this.pattern = input.getPattern();
        this.serial = input.getSerial();
    }
}
