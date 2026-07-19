package vn.vnptit.sapi.oneapp.model.response

import com.fasterxml.jackson.annotation.JsonProperty

public class DVITCareHIENTHITHONGTINDVCNTTResponse {

    public String getsFieldName() {
        return sFieldName;
    }

    public void setsFieldName(String sFieldName) {
        this.sFieldName = sFieldName;
    }

    public String getsCaption() {
        return sCaption;
    }

    public void setsCaption(String sCaption) {
        this.sCaption = sCaption;
    }

    public Byte getiAtt() {
        return iAtt;
    }

    public void setiAtt(Byte iAtt) {
        this.iAtt = iAtt;
    }

    public Byte getiDataType() {
        return iDataType;
    }

    public void setiDataType(Byte iDataType) {
        this.iDataType = iDataType;
    }

    public String getsMaxValue() {
        return sMaxValue;
    }

    public void setsMaxValue(String sMaxValue) {
        this.sMaxValue = sMaxValue;
    }

    public String getsMinvalue() {
        return sMinvalue;
    }

    public void setsMinvalue(String sMinvalue) {
        this.sMinvalue = sMinvalue;
    }

    public String getsValue() {
        return sValue;
    }

    public void setsValue(String sValue) {
        this.sValue = sValue;
    }

    public Integer getnFieldLength() {
        return nFieldLength;
    }

    public void setnFieldLength(Integer nFieldLength) {
        this.nFieldLength = nFieldLength;
    }

    public Boolean getbIsNotNull() {
        return bIsNotNull;
    }

    public void setbIsNotNull(Boolean bIsNotNull) {
        this.bIsNotNull = bIsNotNull;
    }

    public Boolean getbIsUnique() {
        return bIsUnique;
    }

    public void setbIsUnique(Boolean bIsUnique) {
        this.bIsUnique = bIsUnique;
    }

    public String getsLookUpTableName() {
        return sLookUpTableName;
    }

    public void setsLookUpTableName(String sLookUpTableName) {
        this.sLookUpTableName = sLookUpTableName;
    }

    public String getsLookUpKeyField() {
        return sLookUpKeyField;
    }

    public void setsLookUpKeyField(String sLookUpKeyField) {
        this.sLookUpKeyField = sLookUpKeyField;
    }

    public String getsLookUpValueField() {
        return sLookUpValueField;
    }

    public void setsLookUpValueField(String sLookUpValueField) {
        this.sLookUpValueField = sLookUpValueField;
    }

    public String getStrDepandField() {
        return strDepandField;
    }

    public void setStrDepandField(String strDepandField) {
        this.strDepandField = strDepandField;
    }

    public String getStrSql() {
        return StrSql;
    }

    public void setStrSql(String strSql) {
        StrSql = strSql;
    }

    public String getCbb_value() {
        return cbb_value;
    }

    public void setCbb_value(String cbb_value) {
        this.cbb_value = cbb_value;
    }

    public String getCbb_display() {
        return cbb_display;
    }

    public void setCbb_display(String cbb_display) {
        this.cbb_display = cbb_display;
    }

    public Boolean getShowGT() {
        return showGT;
    }

    public void setShowGT(Boolean showGT) {
        this.showGT = showGT;
    }

    public Boolean getbIsEnable() {
        return bIsEnable;
    }

    public void setbIsEnable(Boolean bIsEnable) {
        this.bIsEnable = bIsEnable;
    }

    @JsonProperty("sFieldName")
    private String sFieldName;
    @JsonProperty("sCaption")
    private String sCaption;
    @JsonProperty("iAtt")
    private Byte iAtt;
    @JsonProperty("iDataType")
    private Byte iDataType;
    @JsonProperty("sMaxValue")
    private String sMaxValue;
    @JsonProperty("sMinvalue")
    private String sMinvalue;
    @JsonProperty("sValue")
    private String sValue;
    @JsonProperty("nFieldLength")
    private Integer nFieldLength;
    @JsonProperty("bIsNotNull")
    private Boolean bIsNotNull;
    @JsonProperty("bIsUnique")
    private Boolean bIsUnique;
    @JsonProperty("sLookUpTableName")
    private String sLookUpTableName;
    @JsonProperty("sLookUpKeyField")
    private String sLookUpKeyField;
    @JsonProperty("sLookUpValueField")
    private String sLookUpValueField;
    @JsonProperty("strDepandField")
    private String strDepandField;
    @JsonProperty("StrSql")
    private String StrSql;
    @JsonProperty("cbb_value")
    private String cbb_value;
    @JsonProperty("cbb_display")
    private String cbb_display;
    @JsonProperty("showGT")
    private Boolean showGT;
    @JsonProperty("bIsEnable")
    private Boolean bIsEnable;
}
