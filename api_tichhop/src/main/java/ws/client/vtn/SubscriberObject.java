/**
 * SubscriberObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SubscriberObject  extends BaseObject  implements java.io.Serializable {
    private String SUBSCR_NO;

    private String ACCOUNT_NO;

    private String ACCOUNT_CODE;

    private String RESELLER_VERSION_ID;

    private String GEO_AREA_ID;

    private String DISPLAY_EXTERNAL_ID_TYPE;

    private String DISPLAY_EXTERNAL_ID;

    private String ID_NO;

    private String ID_ISSUE_DATE;

    private String ID_ISSUE_PLACE;

    private String ID_EXPIRE_DATE;

    private String HOUSEHOLD_NO;

    private String BUSINESS_LICENSE_NO;

    private String TIN_NO;

    private String BIRTHDAY;

    private String ANNIVERSARY;

    private String COUNTRY_CODE;

    private String CITY;

    private String STATE;

    private String COUNTY;

    private String UNIT;

    private String ADDRESS1;

    private String ADDRESS2;

    private String ADDRESS3;

    private String ADDRESS4;

    private String CHG_DT;

    private String CHG_WHO;

    private String CODEWORD;

    private String CURRENCY_CODE;

    private String PRIMARY_OFFER_ID;

    private String EXRATE_CLASS;

    private String RATE_CLASS;

    private String NO_BILL;

    private String SERVICE_NAME_PRE;

    private String SERVICE_FNAME;

    private String SERVICE_MINIT;

    private String SERVICE_LNAME;

    private String SERVICE_NAME_GENERATION;

    private String SERVICE_COMPANY;

    private String SERVICE_PHONE;

    private String SERVICE_PHONE2;

    private String PAYMENT_MODE;

    private String SERVICE_ACTIVE_DT;

    private String SERVICE_INACTIVE_DT;

    private String DEALER_ID;

    private String AGENT_ID;

    private String CLASS_OF_SERVICE_CODE;

    private String SHADOW;

    private String SHADOW_SUBSCR_NO;

    private String STATUS;

    private String PASSWORD;

    private String SERVICE_ACT_STATUS;

    private String ACCESS_DEVICE_CODE;

    private Object[] VT_SUPP_OFFER;

    private Object[] VT_EXTERNAL_ID;

    public SubscriberObject() {
    }

    public SubscriberObject(
           int OBJECT_NEW,
           int OBJECT_CHANGE,
           int OBJECT_DELETE,
           String SUBSCR_NO,
           String ACCOUNT_NO,
           String ACCOUNT_CODE,
           String RESELLER_VERSION_ID,
           String GEO_AREA_ID,
           String DISPLAY_EXTERNAL_ID_TYPE,
           String DISPLAY_EXTERNAL_ID,
           String ID_NO,
           String ID_ISSUE_DATE,
           String ID_ISSUE_PLACE,
           String ID_EXPIRE_DATE,
           String HOUSEHOLD_NO,
           String BUSINESS_LICENSE_NO,
           String TIN_NO,
           String BIRTHDAY,
           String ANNIVERSARY,
           String COUNTRY_CODE,
           String CITY,
           String STATE,
           String COUNTY,
           String UNIT,
           String ADDRESS1,
           String ADDRESS2,
           String ADDRESS3,
           String ADDRESS4,
           String CHG_DT,
           String CHG_WHO,
           String CODEWORD,
           String CURRENCY_CODE,
           String PRIMARY_OFFER_ID,
           String EXRATE_CLASS,
           String RATE_CLASS,
           String NO_BILL,
           String SERVICE_NAME_PRE,
           String SERVICE_FNAME,
           String SERVICE_MINIT,
           String SERVICE_LNAME,
           String SERVICE_NAME_GENERATION,
           String SERVICE_COMPANY,
           String SERVICE_PHONE,
           String SERVICE_PHONE2,
           String PAYMENT_MODE,
           String SERVICE_ACTIVE_DT,
           String SERVICE_INACTIVE_DT,
           String DEALER_ID,
           String AGENT_ID,
           String CLASS_OF_SERVICE_CODE,
           String SHADOW,
           String SHADOW_SUBSCR_NO,
           String STATUS,
           String PASSWORD,
           String SERVICE_ACT_STATUS,
           String ACCESS_DEVICE_CODE,
           Object[] VT_SUPP_OFFER,
           Object[] VT_EXTERNAL_ID) {
        super(
            OBJECT_NEW,
            OBJECT_CHANGE,
            OBJECT_DELETE);
        this.SUBSCR_NO = SUBSCR_NO;
        this.ACCOUNT_NO = ACCOUNT_NO;
        this.ACCOUNT_CODE = ACCOUNT_CODE;
        this.RESELLER_VERSION_ID = RESELLER_VERSION_ID;
        this.GEO_AREA_ID = GEO_AREA_ID;
        this.DISPLAY_EXTERNAL_ID_TYPE = DISPLAY_EXTERNAL_ID_TYPE;
        this.DISPLAY_EXTERNAL_ID = DISPLAY_EXTERNAL_ID;
        this.ID_NO = ID_NO;
        this.ID_ISSUE_DATE = ID_ISSUE_DATE;
        this.ID_ISSUE_PLACE = ID_ISSUE_PLACE;
        this.ID_EXPIRE_DATE = ID_EXPIRE_DATE;
        this.HOUSEHOLD_NO = HOUSEHOLD_NO;
        this.BUSINESS_LICENSE_NO = BUSINESS_LICENSE_NO;
        this.TIN_NO = TIN_NO;
        this.BIRTHDAY = BIRTHDAY;
        this.ANNIVERSARY = ANNIVERSARY;
        this.COUNTRY_CODE = COUNTRY_CODE;
        this.CITY = CITY;
        this.STATE = STATE;
        this.COUNTY = COUNTY;
        this.UNIT = UNIT;
        this.ADDRESS1 = ADDRESS1;
        this.ADDRESS2 = ADDRESS2;
        this.ADDRESS3 = ADDRESS3;
        this.ADDRESS4 = ADDRESS4;
        this.CHG_DT = CHG_DT;
        this.CHG_WHO = CHG_WHO;
        this.CODEWORD = CODEWORD;
        this.CURRENCY_CODE = CURRENCY_CODE;
        this.PRIMARY_OFFER_ID = PRIMARY_OFFER_ID;
        this.EXRATE_CLASS = EXRATE_CLASS;
        this.RATE_CLASS = RATE_CLASS;
        this.NO_BILL = NO_BILL;
        this.SERVICE_NAME_PRE = SERVICE_NAME_PRE;
        this.SERVICE_FNAME = SERVICE_FNAME;
        this.SERVICE_MINIT = SERVICE_MINIT;
        this.SERVICE_LNAME = SERVICE_LNAME;
        this.SERVICE_NAME_GENERATION = SERVICE_NAME_GENERATION;
        this.SERVICE_COMPANY = SERVICE_COMPANY;
        this.SERVICE_PHONE = SERVICE_PHONE;
        this.SERVICE_PHONE2 = SERVICE_PHONE2;
        this.PAYMENT_MODE = PAYMENT_MODE;
        this.SERVICE_ACTIVE_DT = SERVICE_ACTIVE_DT;
        this.SERVICE_INACTIVE_DT = SERVICE_INACTIVE_DT;
        this.DEALER_ID = DEALER_ID;
        this.AGENT_ID = AGENT_ID;
        this.CLASS_OF_SERVICE_CODE = CLASS_OF_SERVICE_CODE;
        this.SHADOW = SHADOW;
        this.SHADOW_SUBSCR_NO = SHADOW_SUBSCR_NO;
        this.STATUS = STATUS;
        this.PASSWORD = PASSWORD;
        this.SERVICE_ACT_STATUS = SERVICE_ACT_STATUS;
        this.ACCESS_DEVICE_CODE = ACCESS_DEVICE_CODE;
        this.VT_SUPP_OFFER = VT_SUPP_OFFER;
        this.VT_EXTERNAL_ID = VT_EXTERNAL_ID;
    }


    /**
     * Gets the SUBSCR_NO value for this SubscriberObject.
     * 
     * @return SUBSCR_NO
     */
    public String getSUBSCR_NO() {
        return SUBSCR_NO;
    }


    /**
     * Sets the SUBSCR_NO value for this SubscriberObject.
     * 
     * @param SUBSCR_NO
     */
    public void setSUBSCR_NO(String SUBSCR_NO) {
        this.SUBSCR_NO = SUBSCR_NO;
    }


    /**
     * Gets the ACCOUNT_NO value for this SubscriberObject.
     * 
     * @return ACCOUNT_NO
     */
    public String getACCOUNT_NO() {
        return ACCOUNT_NO;
    }


    /**
     * Sets the ACCOUNT_NO value for this SubscriberObject.
     * 
     * @param ACCOUNT_NO
     */
    public void setACCOUNT_NO(String ACCOUNT_NO) {
        this.ACCOUNT_NO = ACCOUNT_NO;
    }


    /**
     * Gets the ACCOUNT_CODE value for this SubscriberObject.
     * 
     * @return ACCOUNT_CODE
     */
    public String getACCOUNT_CODE() {
        return ACCOUNT_CODE;
    }


    /**
     * Sets the ACCOUNT_CODE value for this SubscriberObject.
     * 
     * @param ACCOUNT_CODE
     */
    public void setACCOUNT_CODE(String ACCOUNT_CODE) {
        this.ACCOUNT_CODE = ACCOUNT_CODE;
    }


    /**
     * Gets the RESELLER_VERSION_ID value for this SubscriberObject.
     * 
     * @return RESELLER_VERSION_ID
     */
    public String getRESELLER_VERSION_ID() {
        return RESELLER_VERSION_ID;
    }


    /**
     * Sets the RESELLER_VERSION_ID value for this SubscriberObject.
     * 
     * @param RESELLER_VERSION_ID
     */
    public void setRESELLER_VERSION_ID(String RESELLER_VERSION_ID) {
        this.RESELLER_VERSION_ID = RESELLER_VERSION_ID;
    }


    /**
     * Gets the GEO_AREA_ID value for this SubscriberObject.
     * 
     * @return GEO_AREA_ID
     */
    public String getGEO_AREA_ID() {
        return GEO_AREA_ID;
    }


    /**
     * Sets the GEO_AREA_ID value for this SubscriberObject.
     * 
     * @param GEO_AREA_ID
     */
    public void setGEO_AREA_ID(String GEO_AREA_ID) {
        this.GEO_AREA_ID = GEO_AREA_ID;
    }


    /**
     * Gets the DISPLAY_EXTERNAL_ID_TYPE value for this SubscriberObject.
     * 
     * @return DISPLAY_EXTERNAL_ID_TYPE
     */
    public String getDISPLAY_EXTERNAL_ID_TYPE() {
        return DISPLAY_EXTERNAL_ID_TYPE;
    }


    /**
     * Sets the DISPLAY_EXTERNAL_ID_TYPE value for this SubscriberObject.
     * 
     * @param DISPLAY_EXTERNAL_ID_TYPE
     */
    public void setDISPLAY_EXTERNAL_ID_TYPE(String DISPLAY_EXTERNAL_ID_TYPE) {
        this.DISPLAY_EXTERNAL_ID_TYPE = DISPLAY_EXTERNAL_ID_TYPE;
    }


    /**
     * Gets the DISPLAY_EXTERNAL_ID value for this SubscriberObject.
     * 
     * @return DISPLAY_EXTERNAL_ID
     */
    public String getDISPLAY_EXTERNAL_ID() {
        return DISPLAY_EXTERNAL_ID;
    }


    /**
     * Sets the DISPLAY_EXTERNAL_ID value for this SubscriberObject.
     * 
     * @param DISPLAY_EXTERNAL_ID
     */
    public void setDISPLAY_EXTERNAL_ID(String DISPLAY_EXTERNAL_ID) {
        this.DISPLAY_EXTERNAL_ID = DISPLAY_EXTERNAL_ID;
    }


    /**
     * Gets the ID_NO value for this SubscriberObject.
     * 
     * @return ID_NO
     */
    public String getID_NO() {
        return ID_NO;
    }


    /**
     * Sets the ID_NO value for this SubscriberObject.
     * 
     * @param ID_NO
     */
    public void setID_NO(String ID_NO) {
        this.ID_NO = ID_NO;
    }


    /**
     * Gets the ID_ISSUE_DATE value for this SubscriberObject.
     * 
     * @return ID_ISSUE_DATE
     */
    public String getID_ISSUE_DATE() {
        return ID_ISSUE_DATE;
    }


    /**
     * Sets the ID_ISSUE_DATE value for this SubscriberObject.
     * 
     * @param ID_ISSUE_DATE
     */
    public void setID_ISSUE_DATE(String ID_ISSUE_DATE) {
        this.ID_ISSUE_DATE = ID_ISSUE_DATE;
    }


    /**
     * Gets the ID_ISSUE_PLACE value for this SubscriberObject.
     * 
     * @return ID_ISSUE_PLACE
     */
    public String getID_ISSUE_PLACE() {
        return ID_ISSUE_PLACE;
    }


    /**
     * Sets the ID_ISSUE_PLACE value for this SubscriberObject.
     * 
     * @param ID_ISSUE_PLACE
     */
    public void setID_ISSUE_PLACE(String ID_ISSUE_PLACE) {
        this.ID_ISSUE_PLACE = ID_ISSUE_PLACE;
    }


    /**
     * Gets the ID_EXPIRE_DATE value for this SubscriberObject.
     * 
     * @return ID_EXPIRE_DATE
     */
    public String getID_EXPIRE_DATE() {
        return ID_EXPIRE_DATE;
    }


    /**
     * Sets the ID_EXPIRE_DATE value for this SubscriberObject.
     * 
     * @param ID_EXPIRE_DATE
     */
    public void setID_EXPIRE_DATE(String ID_EXPIRE_DATE) {
        this.ID_EXPIRE_DATE = ID_EXPIRE_DATE;
    }


    /**
     * Gets the HOUSEHOLD_NO value for this SubscriberObject.
     * 
     * @return HOUSEHOLD_NO
     */
    public String getHOUSEHOLD_NO() {
        return HOUSEHOLD_NO;
    }


    /**
     * Sets the HOUSEHOLD_NO value for this SubscriberObject.
     * 
     * @param HOUSEHOLD_NO
     */
    public void setHOUSEHOLD_NO(String HOUSEHOLD_NO) {
        this.HOUSEHOLD_NO = HOUSEHOLD_NO;
    }


    /**
     * Gets the BUSINESS_LICENSE_NO value for this SubscriberObject.
     * 
     * @return BUSINESS_LICENSE_NO
     */
    public String getBUSINESS_LICENSE_NO() {
        return BUSINESS_LICENSE_NO;
    }


    /**
     * Sets the BUSINESS_LICENSE_NO value for this SubscriberObject.
     * 
     * @param BUSINESS_LICENSE_NO
     */
    public void setBUSINESS_LICENSE_NO(String BUSINESS_LICENSE_NO) {
        this.BUSINESS_LICENSE_NO = BUSINESS_LICENSE_NO;
    }


    /**
     * Gets the TIN_NO value for this SubscriberObject.
     * 
     * @return TIN_NO
     */
    public String getTIN_NO() {
        return TIN_NO;
    }


    /**
     * Sets the TIN_NO value for this SubscriberObject.
     * 
     * @param TIN_NO
     */
    public void setTIN_NO(String TIN_NO) {
        this.TIN_NO = TIN_NO;
    }


    /**
     * Gets the BIRTHDAY value for this SubscriberObject.
     * 
     * @return BIRTHDAY
     */
    public String getBIRTHDAY() {
        return BIRTHDAY;
    }


    /**
     * Sets the BIRTHDAY value for this SubscriberObject.
     * 
     * @param BIRTHDAY
     */
    public void setBIRTHDAY(String BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }


    /**
     * Gets the ANNIVERSARY value for this SubscriberObject.
     * 
     * @return ANNIVERSARY
     */
    public String getANNIVERSARY() {
        return ANNIVERSARY;
    }


    /**
     * Sets the ANNIVERSARY value for this SubscriberObject.
     * 
     * @param ANNIVERSARY
     */
    public void setANNIVERSARY(String ANNIVERSARY) {
        this.ANNIVERSARY = ANNIVERSARY;
    }


    /**
     * Gets the COUNTRY_CODE value for this SubscriberObject.
     * 
     * @return COUNTRY_CODE
     */
    public String getCOUNTRY_CODE() {
        return COUNTRY_CODE;
    }


    /**
     * Sets the COUNTRY_CODE value for this SubscriberObject.
     * 
     * @param COUNTRY_CODE
     */
    public void setCOUNTRY_CODE(String COUNTRY_CODE) {
        this.COUNTRY_CODE = COUNTRY_CODE;
    }


    /**
     * Gets the CITY value for this SubscriberObject.
     * 
     * @return CITY
     */
    public String getCITY() {
        return CITY;
    }


    /**
     * Sets the CITY value for this SubscriberObject.
     * 
     * @param CITY
     */
    public void setCITY(String CITY) {
        this.CITY = CITY;
    }


    /**
     * Gets the STATE value for this SubscriberObject.
     * 
     * @return STATE
     */
    public String getSTATE() {
        return STATE;
    }


    /**
     * Sets the STATE value for this SubscriberObject.
     * 
     * @param STATE
     */
    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }


    /**
     * Gets the COUNTY value for this SubscriberObject.
     * 
     * @return COUNTY
     */
    public String getCOUNTY() {
        return COUNTY;
    }


    /**
     * Sets the COUNTY value for this SubscriberObject.
     * 
     * @param COUNTY
     */
    public void setCOUNTY(String COUNTY) {
        this.COUNTY = COUNTY;
    }


    /**
     * Gets the UNIT value for this SubscriberObject.
     * 
     * @return UNIT
     */
    public String getUNIT() {
        return UNIT;
    }


    /**
     * Sets the UNIT value for this SubscriberObject.
     * 
     * @param UNIT
     */
    public void setUNIT(String UNIT) {
        this.UNIT = UNIT;
    }


    /**
     * Gets the ADDRESS1 value for this SubscriberObject.
     * 
     * @return ADDRESS1
     */
    public String getADDRESS1() {
        return ADDRESS1;
    }


    /**
     * Sets the ADDRESS1 value for this SubscriberObject.
     * 
     * @param ADDRESS1
     */
    public void setADDRESS1(String ADDRESS1) {
        this.ADDRESS1 = ADDRESS1;
    }


    /**
     * Gets the ADDRESS2 value for this SubscriberObject.
     * 
     * @return ADDRESS2
     */
    public String getADDRESS2() {
        return ADDRESS2;
    }


    /**
     * Sets the ADDRESS2 value for this SubscriberObject.
     * 
     * @param ADDRESS2
     */
    public void setADDRESS2(String ADDRESS2) {
        this.ADDRESS2 = ADDRESS2;
    }


    /**
     * Gets the ADDRESS3 value for this SubscriberObject.
     * 
     * @return ADDRESS3
     */
    public String getADDRESS3() {
        return ADDRESS3;
    }


    /**
     * Sets the ADDRESS3 value for this SubscriberObject.
     * 
     * @param ADDRESS3
     */
    public void setADDRESS3(String ADDRESS3) {
        this.ADDRESS3 = ADDRESS3;
    }


    /**
     * Gets the ADDRESS4 value for this SubscriberObject.
     * 
     * @return ADDRESS4
     */
    public String getADDRESS4() {
        return ADDRESS4;
    }


    /**
     * Sets the ADDRESS4 value for this SubscriberObject.
     * 
     * @param ADDRESS4
     */
    public void setADDRESS4(String ADDRESS4) {
        this.ADDRESS4 = ADDRESS4;
    }


    /**
     * Gets the CHG_DT value for this SubscriberObject.
     * 
     * @return CHG_DT
     */
    public String getCHG_DT() {
        return CHG_DT;
    }


    /**
     * Sets the CHG_DT value for this SubscriberObject.
     * 
     * @param CHG_DT
     */
    public void setCHG_DT(String CHG_DT) {
        this.CHG_DT = CHG_DT;
    }


    /**
     * Gets the CHG_WHO value for this SubscriberObject.
     * 
     * @return CHG_WHO
     */
    public String getCHG_WHO() {
        return CHG_WHO;
    }


    /**
     * Sets the CHG_WHO value for this SubscriberObject.
     * 
     * @param CHG_WHO
     */
    public void setCHG_WHO(String CHG_WHO) {
        this.CHG_WHO = CHG_WHO;
    }


    /**
     * Gets the CODEWORD value for this SubscriberObject.
     * 
     * @return CODEWORD
     */
    public String getCODEWORD() {
        return CODEWORD;
    }


    /**
     * Sets the CODEWORD value for this SubscriberObject.
     * 
     * @param CODEWORD
     */
    public void setCODEWORD(String CODEWORD) {
        this.CODEWORD = CODEWORD;
    }


    /**
     * Gets the CURRENCY_CODE value for this SubscriberObject.
     * 
     * @return CURRENCY_CODE
     */
    public String getCURRENCY_CODE() {
        return CURRENCY_CODE;
    }


    /**
     * Sets the CURRENCY_CODE value for this SubscriberObject.
     * 
     * @param CURRENCY_CODE
     */
    public void setCURRENCY_CODE(String CURRENCY_CODE) {
        this.CURRENCY_CODE = CURRENCY_CODE;
    }


    /**
     * Gets the PRIMARY_OFFER_ID value for this SubscriberObject.
     * 
     * @return PRIMARY_OFFER_ID
     */
    public String getPRIMARY_OFFER_ID() {
        return PRIMARY_OFFER_ID;
    }


    /**
     * Sets the PRIMARY_OFFER_ID value for this SubscriberObject.
     * 
     * @param PRIMARY_OFFER_ID
     */
    public void setPRIMARY_OFFER_ID(String PRIMARY_OFFER_ID) {
        this.PRIMARY_OFFER_ID = PRIMARY_OFFER_ID;
    }


    /**
     * Gets the EXRATE_CLASS value for this SubscriberObject.
     * 
     * @return EXRATE_CLASS
     */
    public String getEXRATE_CLASS() {
        return EXRATE_CLASS;
    }


    /**
     * Sets the EXRATE_CLASS value for this SubscriberObject.
     * 
     * @param EXRATE_CLASS
     */
    public void setEXRATE_CLASS(String EXRATE_CLASS) {
        this.EXRATE_CLASS = EXRATE_CLASS;
    }


    /**
     * Gets the RATE_CLASS value for this SubscriberObject.
     * 
     * @return RATE_CLASS
     */
    public String getRATE_CLASS() {
        return RATE_CLASS;
    }


    /**
     * Sets the RATE_CLASS value for this SubscriberObject.
     * 
     * @param RATE_CLASS
     */
    public void setRATE_CLASS(String RATE_CLASS) {
        this.RATE_CLASS = RATE_CLASS;
    }


    /**
     * Gets the NO_BILL value for this SubscriberObject.
     * 
     * @return NO_BILL
     */
    public String getNO_BILL() {
        return NO_BILL;
    }


    /**
     * Sets the NO_BILL value for this SubscriberObject.
     * 
     * @param NO_BILL
     */
    public void setNO_BILL(String NO_BILL) {
        this.NO_BILL = NO_BILL;
    }


    /**
     * Gets the SERVICE_NAME_PRE value for this SubscriberObject.
     * 
     * @return SERVICE_NAME_PRE
     */
    public String getSERVICE_NAME_PRE() {
        return SERVICE_NAME_PRE;
    }


    /**
     * Sets the SERVICE_NAME_PRE value for this SubscriberObject.
     * 
     * @param SERVICE_NAME_PRE
     */
    public void setSERVICE_NAME_PRE(String SERVICE_NAME_PRE) {
        this.SERVICE_NAME_PRE = SERVICE_NAME_PRE;
    }


    /**
     * Gets the SERVICE_FNAME value for this SubscriberObject.
     * 
     * @return SERVICE_FNAME
     */
    public String getSERVICE_FNAME() {
        return SERVICE_FNAME;
    }


    /**
     * Sets the SERVICE_FNAME value for this SubscriberObject.
     * 
     * @param SERVICE_FNAME
     */
    public void setSERVICE_FNAME(String SERVICE_FNAME) {
        this.SERVICE_FNAME = SERVICE_FNAME;
    }


    /**
     * Gets the SERVICE_MINIT value for this SubscriberObject.
     * 
     * @return SERVICE_MINIT
     */
    public String getSERVICE_MINIT() {
        return SERVICE_MINIT;
    }


    /**
     * Sets the SERVICE_MINIT value for this SubscriberObject.
     * 
     * @param SERVICE_MINIT
     */
    public void setSERVICE_MINIT(String SERVICE_MINIT) {
        this.SERVICE_MINIT = SERVICE_MINIT;
    }


    /**
     * Gets the SERVICE_LNAME value for this SubscriberObject.
     * 
     * @return SERVICE_LNAME
     */
    public String getSERVICE_LNAME() {
        return SERVICE_LNAME;
    }


    /**
     * Sets the SERVICE_LNAME value for this SubscriberObject.
     * 
     * @param SERVICE_LNAME
     */
    public void setSERVICE_LNAME(String SERVICE_LNAME) {
        this.SERVICE_LNAME = SERVICE_LNAME;
    }


    /**
     * Gets the SERVICE_NAME_GENERATION value for this SubscriberObject.
     * 
     * @return SERVICE_NAME_GENERATION
     */
    public String getSERVICE_NAME_GENERATION() {
        return SERVICE_NAME_GENERATION;
    }


    /**
     * Sets the SERVICE_NAME_GENERATION value for this SubscriberObject.
     * 
     * @param SERVICE_NAME_GENERATION
     */
    public void setSERVICE_NAME_GENERATION(String SERVICE_NAME_GENERATION) {
        this.SERVICE_NAME_GENERATION = SERVICE_NAME_GENERATION;
    }


    /**
     * Gets the SERVICE_COMPANY value for this SubscriberObject.
     * 
     * @return SERVICE_COMPANY
     */
    public String getSERVICE_COMPANY() {
        return SERVICE_COMPANY;
    }


    /**
     * Sets the SERVICE_COMPANY value for this SubscriberObject.
     * 
     * @param SERVICE_COMPANY
     */
    public void setSERVICE_COMPANY(String SERVICE_COMPANY) {
        this.SERVICE_COMPANY = SERVICE_COMPANY;
    }


    /**
     * Gets the SERVICE_PHONE value for this SubscriberObject.
     * 
     * @return SERVICE_PHONE
     */
    public String getSERVICE_PHONE() {
        return SERVICE_PHONE;
    }


    /**
     * Sets the SERVICE_PHONE value for this SubscriberObject.
     * 
     * @param SERVICE_PHONE
     */
    public void setSERVICE_PHONE(String SERVICE_PHONE) {
        this.SERVICE_PHONE = SERVICE_PHONE;
    }


    /**
     * Gets the SERVICE_PHONE2 value for this SubscriberObject.
     * 
     * @return SERVICE_PHONE2
     */
    public String getSERVICE_PHONE2() {
        return SERVICE_PHONE2;
    }


    /**
     * Sets the SERVICE_PHONE2 value for this SubscriberObject.
     * 
     * @param SERVICE_PHONE2
     */
    public void setSERVICE_PHONE2(String SERVICE_PHONE2) {
        this.SERVICE_PHONE2 = SERVICE_PHONE2;
    }


    /**
     * Gets the PAYMENT_MODE value for this SubscriberObject.
     * 
     * @return PAYMENT_MODE
     */
    public String getPAYMENT_MODE() {
        return PAYMENT_MODE;
    }


    /**
     * Sets the PAYMENT_MODE value for this SubscriberObject.
     * 
     * @param PAYMENT_MODE
     */
    public void setPAYMENT_MODE(String PAYMENT_MODE) {
        this.PAYMENT_MODE = PAYMENT_MODE;
    }


    /**
     * Gets the SERVICE_ACTIVE_DT value for this SubscriberObject.
     * 
     * @return SERVICE_ACTIVE_DT
     */
    public String getSERVICE_ACTIVE_DT() {
        return SERVICE_ACTIVE_DT;
    }


    /**
     * Sets the SERVICE_ACTIVE_DT value for this SubscriberObject.
     * 
     * @param SERVICE_ACTIVE_DT
     */
    public void setSERVICE_ACTIVE_DT(String SERVICE_ACTIVE_DT) {
        this.SERVICE_ACTIVE_DT = SERVICE_ACTIVE_DT;
    }


    /**
     * Gets the SERVICE_INACTIVE_DT value for this SubscriberObject.
     * 
     * @return SERVICE_INACTIVE_DT
     */
    public String getSERVICE_INACTIVE_DT() {
        return SERVICE_INACTIVE_DT;
    }


    /**
     * Sets the SERVICE_INACTIVE_DT value for this SubscriberObject.
     * 
     * @param SERVICE_INACTIVE_DT
     */
    public void setSERVICE_INACTIVE_DT(String SERVICE_INACTIVE_DT) {
        this.SERVICE_INACTIVE_DT = SERVICE_INACTIVE_DT;
    }


    /**
     * Gets the DEALER_ID value for this SubscriberObject.
     * 
     * @return DEALER_ID
     */
    public String getDEALER_ID() {
        return DEALER_ID;
    }


    /**
     * Sets the DEALER_ID value for this SubscriberObject.
     * 
     * @param DEALER_ID
     */
    public void setDEALER_ID(String DEALER_ID) {
        this.DEALER_ID = DEALER_ID;
    }


    /**
     * Gets the AGENT_ID value for this SubscriberObject.
     * 
     * @return AGENT_ID
     */
    public String getAGENT_ID() {
        return AGENT_ID;
    }


    /**
     * Sets the AGENT_ID value for this SubscriberObject.
     * 
     * @param AGENT_ID
     */
    public void setAGENT_ID(String AGENT_ID) {
        this.AGENT_ID = AGENT_ID;
    }


    /**
     * Gets the CLASS_OF_SERVICE_CODE value for this SubscriberObject.
     * 
     * @return CLASS_OF_SERVICE_CODE
     */
    public String getCLASS_OF_SERVICE_CODE() {
        return CLASS_OF_SERVICE_CODE;
    }


    /**
     * Sets the CLASS_OF_SERVICE_CODE value for this SubscriberObject.
     * 
     * @param CLASS_OF_SERVICE_CODE
     */
    public void setCLASS_OF_SERVICE_CODE(String CLASS_OF_SERVICE_CODE) {
        this.CLASS_OF_SERVICE_CODE = CLASS_OF_SERVICE_CODE;
    }


    /**
     * Gets the SHADOW value for this SubscriberObject.
     * 
     * @return SHADOW
     */
    public String getSHADOW() {
        return SHADOW;
    }


    /**
     * Sets the SHADOW value for this SubscriberObject.
     * 
     * @param SHADOW
     */
    public void setSHADOW(String SHADOW) {
        this.SHADOW = SHADOW;
    }


    /**
     * Gets the SHADOW_SUBSCR_NO value for this SubscriberObject.
     * 
     * @return SHADOW_SUBSCR_NO
     */
    public String getSHADOW_SUBSCR_NO() {
        return SHADOW_SUBSCR_NO;
    }


    /**
     * Sets the SHADOW_SUBSCR_NO value for this SubscriberObject.
     * 
     * @param SHADOW_SUBSCR_NO
     */
    public void setSHADOW_SUBSCR_NO(String SHADOW_SUBSCR_NO) {
        this.SHADOW_SUBSCR_NO = SHADOW_SUBSCR_NO;
    }


    /**
     * Gets the STATUS value for this SubscriberObject.
     * 
     * @return STATUS
     */
    public String getSTATUS() {
        return STATUS;
    }


    /**
     * Sets the STATUS value for this SubscriberObject.
     * 
     * @param STATUS
     */
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }


    /**
     * Gets the PASSWORD value for this SubscriberObject.
     * 
     * @return PASSWORD
     */
    public String getPASSWORD() {
        return PASSWORD;
    }


    /**
     * Sets the PASSWORD value for this SubscriberObject.
     * 
     * @param PASSWORD
     */
    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }


    /**
     * Gets the SERVICE_ACT_STATUS value for this SubscriberObject.
     * 
     * @return SERVICE_ACT_STATUS
     */
    public String getSERVICE_ACT_STATUS() {
        return SERVICE_ACT_STATUS;
    }


    /**
     * Sets the SERVICE_ACT_STATUS value for this SubscriberObject.
     * 
     * @param SERVICE_ACT_STATUS
     */
    public void setSERVICE_ACT_STATUS(String SERVICE_ACT_STATUS) {
        this.SERVICE_ACT_STATUS = SERVICE_ACT_STATUS;
    }


    /**
     * Gets the ACCESS_DEVICE_CODE value for this SubscriberObject.
     * 
     * @return ACCESS_DEVICE_CODE
     */
    public String getACCESS_DEVICE_CODE() {
        return ACCESS_DEVICE_CODE;
    }


    /**
     * Sets the ACCESS_DEVICE_CODE value for this SubscriberObject.
     * 
     * @param ACCESS_DEVICE_CODE
     */
    public void setACCESS_DEVICE_CODE(String ACCESS_DEVICE_CODE) {
        this.ACCESS_DEVICE_CODE = ACCESS_DEVICE_CODE;
    }


    /**
     * Gets the VT_SUPP_OFFER value for this SubscriberObject.
     * 
     * @return VT_SUPP_OFFER
     */
    public Object[] getVT_SUPP_OFFER() {
        return VT_SUPP_OFFER;
    }


    /**
     * Sets the VT_SUPP_OFFER value for this SubscriberObject.
     * 
     * @param VT_SUPP_OFFER
     */
    public void setVT_SUPP_OFFER(Object[] VT_SUPP_OFFER) {
        this.VT_SUPP_OFFER = VT_SUPP_OFFER;
    }

    public Object getVT_SUPP_OFFER(int i) {
        return this.VT_SUPP_OFFER[i];
    }

    public void setVT_SUPP_OFFER(int i, Object _value) {
        this.VT_SUPP_OFFER[i] = _value;
    }


    /**
     * Gets the VT_EXTERNAL_ID value for this SubscriberObject.
     * 
     * @return VT_EXTERNAL_ID
     */
    public Object[] getVT_EXTERNAL_ID() {
        return VT_EXTERNAL_ID;
    }


    /**
     * Sets the VT_EXTERNAL_ID value for this SubscriberObject.
     * 
     * @param VT_EXTERNAL_ID
     */
    public void setVT_EXTERNAL_ID(Object[] VT_EXTERNAL_ID) {
        this.VT_EXTERNAL_ID = VT_EXTERNAL_ID;
    }

    public Object getVT_EXTERNAL_ID(int i) {
        return this.VT_EXTERNAL_ID[i];
    }

    public void setVT_EXTERNAL_ID(int i, Object _value) {
        this.VT_EXTERNAL_ID[i] = _value;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SubscriberObject)) return false;
        SubscriberObject other = (SubscriberObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.SUBSCR_NO==null && other.getSUBSCR_NO()==null) || 
             (this.SUBSCR_NO!=null &&
              this.SUBSCR_NO.equals(other.getSUBSCR_NO()))) &&
            ((this.ACCOUNT_NO==null && other.getACCOUNT_NO()==null) || 
             (this.ACCOUNT_NO!=null &&
              this.ACCOUNT_NO.equals(other.getACCOUNT_NO()))) &&
            ((this.ACCOUNT_CODE==null && other.getACCOUNT_CODE()==null) || 
             (this.ACCOUNT_CODE!=null &&
              this.ACCOUNT_CODE.equals(other.getACCOUNT_CODE()))) &&
            ((this.RESELLER_VERSION_ID==null && other.getRESELLER_VERSION_ID()==null) || 
             (this.RESELLER_VERSION_ID!=null &&
              this.RESELLER_VERSION_ID.equals(other.getRESELLER_VERSION_ID()))) &&
            ((this.GEO_AREA_ID==null && other.getGEO_AREA_ID()==null) || 
             (this.GEO_AREA_ID!=null &&
              this.GEO_AREA_ID.equals(other.getGEO_AREA_ID()))) &&
            ((this.DISPLAY_EXTERNAL_ID_TYPE==null && other.getDISPLAY_EXTERNAL_ID_TYPE()==null) || 
             (this.DISPLAY_EXTERNAL_ID_TYPE!=null &&
              this.DISPLAY_EXTERNAL_ID_TYPE.equals(other.getDISPLAY_EXTERNAL_ID_TYPE()))) &&
            ((this.DISPLAY_EXTERNAL_ID==null && other.getDISPLAY_EXTERNAL_ID()==null) || 
             (this.DISPLAY_EXTERNAL_ID!=null &&
              this.DISPLAY_EXTERNAL_ID.equals(other.getDISPLAY_EXTERNAL_ID()))) &&
            ((this.ID_NO==null && other.getID_NO()==null) || 
             (this.ID_NO!=null &&
              this.ID_NO.equals(other.getID_NO()))) &&
            ((this.ID_ISSUE_DATE==null && other.getID_ISSUE_DATE()==null) || 
             (this.ID_ISSUE_DATE!=null &&
              this.ID_ISSUE_DATE.equals(other.getID_ISSUE_DATE()))) &&
            ((this.ID_ISSUE_PLACE==null && other.getID_ISSUE_PLACE()==null) || 
             (this.ID_ISSUE_PLACE!=null &&
              this.ID_ISSUE_PLACE.equals(other.getID_ISSUE_PLACE()))) &&
            ((this.ID_EXPIRE_DATE==null && other.getID_EXPIRE_DATE()==null) || 
             (this.ID_EXPIRE_DATE!=null &&
              this.ID_EXPIRE_DATE.equals(other.getID_EXPIRE_DATE()))) &&
            ((this.HOUSEHOLD_NO==null && other.getHOUSEHOLD_NO()==null) || 
             (this.HOUSEHOLD_NO!=null &&
              this.HOUSEHOLD_NO.equals(other.getHOUSEHOLD_NO()))) &&
            ((this.BUSINESS_LICENSE_NO==null && other.getBUSINESS_LICENSE_NO()==null) || 
             (this.BUSINESS_LICENSE_NO!=null &&
              this.BUSINESS_LICENSE_NO.equals(other.getBUSINESS_LICENSE_NO()))) &&
            ((this.TIN_NO==null && other.getTIN_NO()==null) || 
             (this.TIN_NO!=null &&
              this.TIN_NO.equals(other.getTIN_NO()))) &&
            ((this.BIRTHDAY==null && other.getBIRTHDAY()==null) || 
             (this.BIRTHDAY!=null &&
              this.BIRTHDAY.equals(other.getBIRTHDAY()))) &&
            ((this.ANNIVERSARY==null && other.getANNIVERSARY()==null) || 
             (this.ANNIVERSARY!=null &&
              this.ANNIVERSARY.equals(other.getANNIVERSARY()))) &&
            ((this.COUNTRY_CODE==null && other.getCOUNTRY_CODE()==null) || 
             (this.COUNTRY_CODE!=null &&
              this.COUNTRY_CODE.equals(other.getCOUNTRY_CODE()))) &&
            ((this.CITY==null && other.getCITY()==null) || 
             (this.CITY!=null &&
              this.CITY.equals(other.getCITY()))) &&
            ((this.STATE==null && other.getSTATE()==null) || 
             (this.STATE!=null &&
              this.STATE.equals(other.getSTATE()))) &&
            ((this.COUNTY==null && other.getCOUNTY()==null) || 
             (this.COUNTY!=null &&
              this.COUNTY.equals(other.getCOUNTY()))) &&
            ((this.UNIT==null && other.getUNIT()==null) || 
             (this.UNIT!=null &&
              this.UNIT.equals(other.getUNIT()))) &&
            ((this.ADDRESS1==null && other.getADDRESS1()==null) || 
             (this.ADDRESS1!=null &&
              this.ADDRESS1.equals(other.getADDRESS1()))) &&
            ((this.ADDRESS2==null && other.getADDRESS2()==null) || 
             (this.ADDRESS2!=null &&
              this.ADDRESS2.equals(other.getADDRESS2()))) &&
            ((this.ADDRESS3==null && other.getADDRESS3()==null) || 
             (this.ADDRESS3!=null &&
              this.ADDRESS3.equals(other.getADDRESS3()))) &&
            ((this.ADDRESS4==null && other.getADDRESS4()==null) || 
             (this.ADDRESS4!=null &&
              this.ADDRESS4.equals(other.getADDRESS4()))) &&
            ((this.CHG_DT==null && other.getCHG_DT()==null) || 
             (this.CHG_DT!=null &&
              this.CHG_DT.equals(other.getCHG_DT()))) &&
            ((this.CHG_WHO==null && other.getCHG_WHO()==null) || 
             (this.CHG_WHO!=null &&
              this.CHG_WHO.equals(other.getCHG_WHO()))) &&
            ((this.CODEWORD==null && other.getCODEWORD()==null) || 
             (this.CODEWORD!=null &&
              this.CODEWORD.equals(other.getCODEWORD()))) &&
            ((this.CURRENCY_CODE==null && other.getCURRENCY_CODE()==null) || 
             (this.CURRENCY_CODE!=null &&
              this.CURRENCY_CODE.equals(other.getCURRENCY_CODE()))) &&
            ((this.PRIMARY_OFFER_ID==null && other.getPRIMARY_OFFER_ID()==null) || 
             (this.PRIMARY_OFFER_ID!=null &&
              this.PRIMARY_OFFER_ID.equals(other.getPRIMARY_OFFER_ID()))) &&
            ((this.EXRATE_CLASS==null && other.getEXRATE_CLASS()==null) || 
             (this.EXRATE_CLASS!=null &&
              this.EXRATE_CLASS.equals(other.getEXRATE_CLASS()))) &&
            ((this.RATE_CLASS==null && other.getRATE_CLASS()==null) || 
             (this.RATE_CLASS!=null &&
              this.RATE_CLASS.equals(other.getRATE_CLASS()))) &&
            ((this.NO_BILL==null && other.getNO_BILL()==null) || 
             (this.NO_BILL!=null &&
              this.NO_BILL.equals(other.getNO_BILL()))) &&
            ((this.SERVICE_NAME_PRE==null && other.getSERVICE_NAME_PRE()==null) || 
             (this.SERVICE_NAME_PRE!=null &&
              this.SERVICE_NAME_PRE.equals(other.getSERVICE_NAME_PRE()))) &&
            ((this.SERVICE_FNAME==null && other.getSERVICE_FNAME()==null) || 
             (this.SERVICE_FNAME!=null &&
              this.SERVICE_FNAME.equals(other.getSERVICE_FNAME()))) &&
            ((this.SERVICE_MINIT==null && other.getSERVICE_MINIT()==null) || 
             (this.SERVICE_MINIT!=null &&
              this.SERVICE_MINIT.equals(other.getSERVICE_MINIT()))) &&
            ((this.SERVICE_LNAME==null && other.getSERVICE_LNAME()==null) || 
             (this.SERVICE_LNAME!=null &&
              this.SERVICE_LNAME.equals(other.getSERVICE_LNAME()))) &&
            ((this.SERVICE_NAME_GENERATION==null && other.getSERVICE_NAME_GENERATION()==null) || 
             (this.SERVICE_NAME_GENERATION!=null &&
              this.SERVICE_NAME_GENERATION.equals(other.getSERVICE_NAME_GENERATION()))) &&
            ((this.SERVICE_COMPANY==null && other.getSERVICE_COMPANY()==null) || 
             (this.SERVICE_COMPANY!=null &&
              this.SERVICE_COMPANY.equals(other.getSERVICE_COMPANY()))) &&
            ((this.SERVICE_PHONE==null && other.getSERVICE_PHONE()==null) || 
             (this.SERVICE_PHONE!=null &&
              this.SERVICE_PHONE.equals(other.getSERVICE_PHONE()))) &&
            ((this.SERVICE_PHONE2==null && other.getSERVICE_PHONE2()==null) || 
             (this.SERVICE_PHONE2!=null &&
              this.SERVICE_PHONE2.equals(other.getSERVICE_PHONE2()))) &&
            ((this.PAYMENT_MODE==null && other.getPAYMENT_MODE()==null) || 
             (this.PAYMENT_MODE!=null &&
              this.PAYMENT_MODE.equals(other.getPAYMENT_MODE()))) &&
            ((this.SERVICE_ACTIVE_DT==null && other.getSERVICE_ACTIVE_DT()==null) || 
             (this.SERVICE_ACTIVE_DT!=null &&
              this.SERVICE_ACTIVE_DT.equals(other.getSERVICE_ACTIVE_DT()))) &&
            ((this.SERVICE_INACTIVE_DT==null && other.getSERVICE_INACTIVE_DT()==null) || 
             (this.SERVICE_INACTIVE_DT!=null &&
              this.SERVICE_INACTIVE_DT.equals(other.getSERVICE_INACTIVE_DT()))) &&
            ((this.DEALER_ID==null && other.getDEALER_ID()==null) || 
             (this.DEALER_ID!=null &&
              this.DEALER_ID.equals(other.getDEALER_ID()))) &&
            ((this.AGENT_ID==null && other.getAGENT_ID()==null) || 
             (this.AGENT_ID!=null &&
              this.AGENT_ID.equals(other.getAGENT_ID()))) &&
            ((this.CLASS_OF_SERVICE_CODE==null && other.getCLASS_OF_SERVICE_CODE()==null) || 
             (this.CLASS_OF_SERVICE_CODE!=null &&
              this.CLASS_OF_SERVICE_CODE.equals(other.getCLASS_OF_SERVICE_CODE()))) &&
            ((this.SHADOW==null && other.getSHADOW()==null) || 
             (this.SHADOW!=null &&
              this.SHADOW.equals(other.getSHADOW()))) &&
            ((this.SHADOW_SUBSCR_NO==null && other.getSHADOW_SUBSCR_NO()==null) || 
             (this.SHADOW_SUBSCR_NO!=null &&
              this.SHADOW_SUBSCR_NO.equals(other.getSHADOW_SUBSCR_NO()))) &&
            ((this.STATUS==null && other.getSTATUS()==null) || 
             (this.STATUS!=null &&
              this.STATUS.equals(other.getSTATUS()))) &&
            ((this.PASSWORD==null && other.getPASSWORD()==null) || 
             (this.PASSWORD!=null &&
              this.PASSWORD.equals(other.getPASSWORD()))) &&
            ((this.SERVICE_ACT_STATUS==null && other.getSERVICE_ACT_STATUS()==null) || 
             (this.SERVICE_ACT_STATUS!=null &&
              this.SERVICE_ACT_STATUS.equals(other.getSERVICE_ACT_STATUS()))) &&
            ((this.ACCESS_DEVICE_CODE==null && other.getACCESS_DEVICE_CODE()==null) || 
             (this.ACCESS_DEVICE_CODE!=null &&
              this.ACCESS_DEVICE_CODE.equals(other.getACCESS_DEVICE_CODE()))) &&
            ((this.VT_SUPP_OFFER==null && other.getVT_SUPP_OFFER()==null) || 
             (this.VT_SUPP_OFFER!=null &&
              java.util.Arrays.equals(this.VT_SUPP_OFFER, other.getVT_SUPP_OFFER()))) &&
            ((this.VT_EXTERNAL_ID==null && other.getVT_EXTERNAL_ID()==null) || 
             (this.VT_EXTERNAL_ID!=null &&
              java.util.Arrays.equals(this.VT_EXTERNAL_ID, other.getVT_EXTERNAL_ID())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getSUBSCR_NO() != null) {
            _hashCode += getSUBSCR_NO().hashCode();
        }
        if (getACCOUNT_NO() != null) {
            _hashCode += getACCOUNT_NO().hashCode();
        }
        if (getACCOUNT_CODE() != null) {
            _hashCode += getACCOUNT_CODE().hashCode();
        }
        if (getRESELLER_VERSION_ID() != null) {
            _hashCode += getRESELLER_VERSION_ID().hashCode();
        }
        if (getGEO_AREA_ID() != null) {
            _hashCode += getGEO_AREA_ID().hashCode();
        }
        if (getDISPLAY_EXTERNAL_ID_TYPE() != null) {
            _hashCode += getDISPLAY_EXTERNAL_ID_TYPE().hashCode();
        }
        if (getDISPLAY_EXTERNAL_ID() != null) {
            _hashCode += getDISPLAY_EXTERNAL_ID().hashCode();
        }
        if (getID_NO() != null) {
            _hashCode += getID_NO().hashCode();
        }
        if (getID_ISSUE_DATE() != null) {
            _hashCode += getID_ISSUE_DATE().hashCode();
        }
        if (getID_ISSUE_PLACE() != null) {
            _hashCode += getID_ISSUE_PLACE().hashCode();
        }
        if (getID_EXPIRE_DATE() != null) {
            _hashCode += getID_EXPIRE_DATE().hashCode();
        }
        if (getHOUSEHOLD_NO() != null) {
            _hashCode += getHOUSEHOLD_NO().hashCode();
        }
        if (getBUSINESS_LICENSE_NO() != null) {
            _hashCode += getBUSINESS_LICENSE_NO().hashCode();
        }
        if (getTIN_NO() != null) {
            _hashCode += getTIN_NO().hashCode();
        }
        if (getBIRTHDAY() != null) {
            _hashCode += getBIRTHDAY().hashCode();
        }
        if (getANNIVERSARY() != null) {
            _hashCode += getANNIVERSARY().hashCode();
        }
        if (getCOUNTRY_CODE() != null) {
            _hashCode += getCOUNTRY_CODE().hashCode();
        }
        if (getCITY() != null) {
            _hashCode += getCITY().hashCode();
        }
        if (getSTATE() != null) {
            _hashCode += getSTATE().hashCode();
        }
        if (getCOUNTY() != null) {
            _hashCode += getCOUNTY().hashCode();
        }
        if (getUNIT() != null) {
            _hashCode += getUNIT().hashCode();
        }
        if (getADDRESS1() != null) {
            _hashCode += getADDRESS1().hashCode();
        }
        if (getADDRESS2() != null) {
            _hashCode += getADDRESS2().hashCode();
        }
        if (getADDRESS3() != null) {
            _hashCode += getADDRESS3().hashCode();
        }
        if (getADDRESS4() != null) {
            _hashCode += getADDRESS4().hashCode();
        }
        if (getCHG_DT() != null) {
            _hashCode += getCHG_DT().hashCode();
        }
        if (getCHG_WHO() != null) {
            _hashCode += getCHG_WHO().hashCode();
        }
        if (getCODEWORD() != null) {
            _hashCode += getCODEWORD().hashCode();
        }
        if (getCURRENCY_CODE() != null) {
            _hashCode += getCURRENCY_CODE().hashCode();
        }
        if (getPRIMARY_OFFER_ID() != null) {
            _hashCode += getPRIMARY_OFFER_ID().hashCode();
        }
        if (getEXRATE_CLASS() != null) {
            _hashCode += getEXRATE_CLASS().hashCode();
        }
        if (getRATE_CLASS() != null) {
            _hashCode += getRATE_CLASS().hashCode();
        }
        if (getNO_BILL() != null) {
            _hashCode += getNO_BILL().hashCode();
        }
        if (getSERVICE_NAME_PRE() != null) {
            _hashCode += getSERVICE_NAME_PRE().hashCode();
        }
        if (getSERVICE_FNAME() != null) {
            _hashCode += getSERVICE_FNAME().hashCode();
        }
        if (getSERVICE_MINIT() != null) {
            _hashCode += getSERVICE_MINIT().hashCode();
        }
        if (getSERVICE_LNAME() != null) {
            _hashCode += getSERVICE_LNAME().hashCode();
        }
        if (getSERVICE_NAME_GENERATION() != null) {
            _hashCode += getSERVICE_NAME_GENERATION().hashCode();
        }
        if (getSERVICE_COMPANY() != null) {
            _hashCode += getSERVICE_COMPANY().hashCode();
        }
        if (getSERVICE_PHONE() != null) {
            _hashCode += getSERVICE_PHONE().hashCode();
        }
        if (getSERVICE_PHONE2() != null) {
            _hashCode += getSERVICE_PHONE2().hashCode();
        }
        if (getPAYMENT_MODE() != null) {
            _hashCode += getPAYMENT_MODE().hashCode();
        }
        if (getSERVICE_ACTIVE_DT() != null) {
            _hashCode += getSERVICE_ACTIVE_DT().hashCode();
        }
        if (getSERVICE_INACTIVE_DT() != null) {
            _hashCode += getSERVICE_INACTIVE_DT().hashCode();
        }
        if (getDEALER_ID() != null) {
            _hashCode += getDEALER_ID().hashCode();
        }
        if (getAGENT_ID() != null) {
            _hashCode += getAGENT_ID().hashCode();
        }
        if (getCLASS_OF_SERVICE_CODE() != null) {
            _hashCode += getCLASS_OF_SERVICE_CODE().hashCode();
        }
        if (getSHADOW() != null) {
            _hashCode += getSHADOW().hashCode();
        }
        if (getSHADOW_SUBSCR_NO() != null) {
            _hashCode += getSHADOW_SUBSCR_NO().hashCode();
        }
        if (getSTATUS() != null) {
            _hashCode += getSTATUS().hashCode();
        }
        if (getPASSWORD() != null) {
            _hashCode += getPASSWORD().hashCode();
        }
        if (getSERVICE_ACT_STATUS() != null) {
            _hashCode += getSERVICE_ACT_STATUS().hashCode();
        }
        if (getACCESS_DEVICE_CODE() != null) {
            _hashCode += getACCESS_DEVICE_CODE().hashCode();
        }
        if (getVT_SUPP_OFFER() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVT_SUPP_OFFER());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getVT_SUPP_OFFER(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVT_EXTERNAL_ID() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVT_EXTERNAL_ID());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getVT_EXTERNAL_ID(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SubscriberObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.crm.telsoft/", "subscriberObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SUBSCR_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SUBSCR_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACCOUNT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCOUNT_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACCOUNT_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCOUNT_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RESELLER_VERSION_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RESELLER_VERSION_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GEO_AREA_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GEO_AREA_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DISPLAY_EXTERNAL_ID_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DISPLAY_EXTERNAL_ID_TYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DISPLAY_EXTERNAL_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DISPLAY_EXTERNAL_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID_ISSUE_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID_ISSUE_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID_ISSUE_PLACE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID_ISSUE_PLACE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID_EXPIRE_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID_EXPIRE_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("HOUSEHOLD_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "HOUSEHOLD_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BUSINESS_LICENSE_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BUSINESS_LICENSE_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TIN_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TIN_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BIRTHDAY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BIRTHDAY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ANNIVERSARY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ANNIVERSARY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("COUNTRY_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "COUNTRY_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CITY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CITY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("COUNTY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "COUNTY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UNIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UNIT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ADDRESS1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ADDRESS1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ADDRESS2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ADDRESS2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ADDRESS3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ADDRESS3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ADDRESS4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ADDRESS4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CHG_DT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CHG_DT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CHG_WHO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CHG_WHO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CODEWORD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CODEWORD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CURRENCY_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CURRENCY_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PRIMARY_OFFER_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PRIMARY_OFFER_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EXRATE_CLASS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EXRATE_CLASS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RATE_CLASS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RATE_CLASS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NO_BILL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NO_BILL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_NAME_PRE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_NAME_PRE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_FNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_FNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_MINIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_MINIT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_LNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_LNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_NAME_GENERATION");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_NAME_GENERATION"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_COMPANY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_COMPANY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_PHONE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_PHONE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_PHONE2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_PHONE2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PAYMENT_MODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PAYMENT_MODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_ACTIVE_DT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_ACTIVE_DT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_INACTIVE_DT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_INACTIVE_DT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEALER_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEALER_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("AGENT_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AGENT_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CLASS_OF_SERVICE_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CLASS_OF_SERVICE_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SHADOW");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SHADOW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SHADOW_SUBSCR_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SHADOW_SUBSCR_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STATUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PASSWORD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PASSWORD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SERVICE_ACT_STATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SERVICE_ACT_STATUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACCESS_DEVICE_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCESS_DEVICE_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VT_SUPP_OFFER");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VT_SUPP_OFFER"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VT_EXTERNAL_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VT_EXTERNAL_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
