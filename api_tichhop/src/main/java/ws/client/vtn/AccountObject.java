/**
 * AccountObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class AccountObject  extends BaseObject  implements java.io.Serializable {
    private String ACCOUNT_NO;

    private String ACCOUNT_CODE;

    private String CONTRACT_NO;

    private String CONTRACT_DATE;

    private String PARENT_ACCOUNT_NO;

    private String PARENT_ACCOUNT_CODE;

    private String RESELLER_VERSION_ID;

    private String CUST_GEO_AREA_ID;

    private String TIE_CODE;

    private String BILL_SEQUENCE_NUM;

    private String CURRENCY_CODE;

    private String LANGUAGE_CODE;

    private String ACCOUNT_TYPE;

    private String ACCOUNT_CATEGORY;

    private String PREV_CUTOFF_DATE;

    private String PREV_BILL_DATE;

    private String NEXT_BILL_DATE;

    private String PREV_BILL_REFNO;

    private String PREV_BILL_REF_RESETS;

    private String PREV_BALANCE_REFNO;

    private String PREV_BALANCE_REF_RESETS;

    private String BILL_NAME_PRE;

    private String BILL_FNAME;

    private String BILL_MINIT;

    private String BILL_LNAME;

    private String BILL_NAME_GENERATION;

    private String BILL_TITLE;

    private String BILL_COMPANY;

    private String CUST_ADDRESS1;

    private String CUST_ADDRESS2;

    private String CUST_ADDRESS3;

    private String CUST_ADDRESS4;

    private String CUST_CITY;

    private String CUST_STATE;

    private String CUST_COUNTY;

    private String CUST_UNIT;

    private String CUST_ZIP;

    private String CUST_COUNTRY_CODE;

    private String CUST_FRANCHISE_TAX_CODE;

    private String CUST_PHONE1;

    private String CUST_PHONE2;

    private String BILL_ADDRESS1;

    private String BILL_ADDRESS2;

    private String BILL_ADDRESS3;

    private String BILL_ADDRESS4;

    private String BILL_CITY;

    private String BILL_STATE;

    private String BILL_ZIP;

    private String BILL_COUNTRY_CODE;

    private String BILL_FRANCHISE_TAX_CODE;

    private String BILL_COUNTY;

    private String BILL_UNIT;

    private String SSN;

    private String CONTACT1_NAME;

    private String CONTACT2_NAME;

    private String CONTACT1_PHONE;

    private String CONTACT2_PHONE;

    private String CUST_FAXNO;

    private String STATEMENT_TO_FAXNO;

    private String CREDIT_THRESH;

    private String CRED_STATUS;

    private String CREDIT_RATING;

    private String DISCONNECT_REASON;

    private String DISC_RCV_OPT;

    private String THRESH_REF;

    private String BILL_PERIOD;

    private String BILL_DISP_METH;

    private String BILLING_FREQUENCY;

    private String RATE_CLASS_DEFAULT;

    private String EXRATE_CLASS;

    private String ACCOUNT_STATUS;

    private String ACCOUNT_STATUS_DT;

    private String DISTR_CHAN;

    private String SALES_CODE;

    private String MKT_CODE;

    private String SIC_CODE;

    private String IN_USE;

    private String NO_BILL;

    private String VIP_CODE;

    private String CODEWORD;

    private String REMARK;

    private String CUST_EMAIL;

    private String STATEMENT_TO_EMAIL;

    private String CHG_WHO;

    private String CHG_DATE;

    private String DATE_CREATED;

    private String DATE_ACTIVE;

    private String DATE_INACTIVE;

    private String CHARGE_THRESHOLD;

    private String THRESHOLD;

    private String CYCLICAL_THRESHOLD;

    private String USG_AMT_LIMIT;

    private String USG_UNITS_LIMIT;

    private String REGULATORY_ID;

    private String GLOBAL_CONTRACT_STATUS;

    private String ALT_LNAME;

    private String ALT_FNAME;

    private String ALT_COMPANY_NAME;

    private String GENDER;

    private String DEPT;

    private String TITLE;

    private String PAYMENT_PROFILE_ID;

    private String IS_ACTIVATED;

    private String IS_TML_ENABLED;

    private String TML_LIMIT;

    private String DEALER_ID;

    private String AGENT_ID;

    private String UPSELL_TEMPLATE_ID;

    private String LATEST_QUOTE_IMAGE;

    private String IS_CSS_REGISTERED;

    private String PRIMARY_SUBSCR_NO;

    private String PRIMARY_SUBSCR_NO_RESETS;

    private String ACCT_SEG_ID;

    private String NOTIFICATION_LANGUAGE_ID;

    private String NOTIFICATION_LEVEL;

    private String PAYMENT_METHOD;

    private String BNK_ACCOUNT_HOLDER;

    private String BNK_ACCOUNT_NO;

    private String BANK_ID;

    private String TIN_NO;

    public AccountObject() {
    }

    public AccountObject(
           int OBJECT_NEW,
           int OBJECT_CHANGE,
           int OBJECT_DELETE,
           String ACCOUNT_NO,
           String ACCOUNT_CODE,
           String CONTRACT_NO,
           String CONTRACT_DATE,
           String PARENT_ACCOUNT_NO,
           String PARENT_ACCOUNT_CODE,
           String RESELLER_VERSION_ID,
           String CUST_GEO_AREA_ID,
           String TIE_CODE,
           String BILL_SEQUENCE_NUM,
           String CURRENCY_CODE,
           String LANGUAGE_CODE,
           String ACCOUNT_TYPE,
           String ACCOUNT_CATEGORY,
           String PREV_CUTOFF_DATE,
           String PREV_BILL_DATE,
           String NEXT_BILL_DATE,
           String PREV_BILL_REFNO,
           String PREV_BILL_REF_RESETS,
           String PREV_BALANCE_REFNO,
           String PREV_BALANCE_REF_RESETS,
           String BILL_NAME_PRE,
           String BILL_FNAME,
           String BILL_MINIT,
           String BILL_LNAME,
           String BILL_NAME_GENERATION,
           String BILL_TITLE,
           String BILL_COMPANY,
           String CUST_ADDRESS1,
           String CUST_ADDRESS2,
           String CUST_ADDRESS3,
           String CUST_ADDRESS4,
           String CUST_CITY,
           String CUST_STATE,
           String CUST_COUNTY,
           String CUST_UNIT,
           String CUST_ZIP,
           String CUST_COUNTRY_CODE,
           String CUST_FRANCHISE_TAX_CODE,
           String CUST_PHONE1,
           String CUST_PHONE2,
           String BILL_ADDRESS1,
           String BILL_ADDRESS2,
           String BILL_ADDRESS3,
           String BILL_ADDRESS4,
           String BILL_CITY,
           String BILL_STATE,
           String BILL_ZIP,
           String BILL_COUNTRY_CODE,
           String BILL_FRANCHISE_TAX_CODE,
           String BILL_COUNTY,
           String BILL_UNIT,
           String SSN,
           String CONTACT1_NAME,
           String CONTACT2_NAME,
           String CONTACT1_PHONE,
           String CONTACT2_PHONE,
           String CUST_FAXNO,
           String STATEMENT_TO_FAXNO,
           String CREDIT_THRESH,
           String CRED_STATUS,
           String CREDIT_RATING,
           String DISCONNECT_REASON,
           String DISC_RCV_OPT,
           String THRESH_REF,
           String BILL_PERIOD,
           String BILL_DISP_METH,
           String BILLING_FREQUENCY,
           String RATE_CLASS_DEFAULT,
           String EXRATE_CLASS,
           String ACCOUNT_STATUS,
           String ACCOUNT_STATUS_DT,
           String DISTR_CHAN,
           String SALES_CODE,
           String MKT_CODE,
           String SIC_CODE,
           String IN_USE,
           String NO_BILL,
           String VIP_CODE,
           String CODEWORD,
           String REMARK,
           String CUST_EMAIL,
           String STATEMENT_TO_EMAIL,
           String CHG_WHO,
           String CHG_DATE,
           String DATE_CREATED,
           String DATE_ACTIVE,
           String DATE_INACTIVE,
           String CHARGE_THRESHOLD,
           String THRESHOLD,
           String CYCLICAL_THRESHOLD,
           String USG_AMT_LIMIT,
           String USG_UNITS_LIMIT,
           String REGULATORY_ID,
           String GLOBAL_CONTRACT_STATUS,
           String ALT_LNAME,
           String ALT_FNAME,
           String ALT_COMPANY_NAME,
           String GENDER,
           String DEPT,
           String TITLE,
           String PAYMENT_PROFILE_ID,
           String IS_ACTIVATED,
           String IS_TML_ENABLED,
           String TML_LIMIT,
           String DEALER_ID,
           String AGENT_ID,
           String UPSELL_TEMPLATE_ID,
           String LATEST_QUOTE_IMAGE,
           String IS_CSS_REGISTERED,
           String PRIMARY_SUBSCR_NO,
           String PRIMARY_SUBSCR_NO_RESETS,
           String ACCT_SEG_ID,
           String NOTIFICATION_LANGUAGE_ID,
           String NOTIFICATION_LEVEL,
           String PAYMENT_METHOD,
           String BNK_ACCOUNT_HOLDER,
           String BNK_ACCOUNT_NO,
           String BANK_ID,
           String TIN_NO) {
        super(
            OBJECT_NEW,
            OBJECT_CHANGE,
            OBJECT_DELETE);
        this.ACCOUNT_NO = ACCOUNT_NO;
        this.ACCOUNT_CODE = ACCOUNT_CODE;
        this.CONTRACT_NO = CONTRACT_NO;
        this.CONTRACT_DATE = CONTRACT_DATE;
        this.PARENT_ACCOUNT_NO = PARENT_ACCOUNT_NO;
        this.PARENT_ACCOUNT_CODE = PARENT_ACCOUNT_CODE;
        this.RESELLER_VERSION_ID = RESELLER_VERSION_ID;
        this.CUST_GEO_AREA_ID = CUST_GEO_AREA_ID;
        this.TIE_CODE = TIE_CODE;
        this.BILL_SEQUENCE_NUM = BILL_SEQUENCE_NUM;
        this.CURRENCY_CODE = CURRENCY_CODE;
        this.LANGUAGE_CODE = LANGUAGE_CODE;
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
        this.ACCOUNT_CATEGORY = ACCOUNT_CATEGORY;
        this.PREV_CUTOFF_DATE = PREV_CUTOFF_DATE;
        this.PREV_BILL_DATE = PREV_BILL_DATE;
        this.NEXT_BILL_DATE = NEXT_BILL_DATE;
        this.PREV_BILL_REFNO = PREV_BILL_REFNO;
        this.PREV_BILL_REF_RESETS = PREV_BILL_REF_RESETS;
        this.PREV_BALANCE_REFNO = PREV_BALANCE_REFNO;
        this.PREV_BALANCE_REF_RESETS = PREV_BALANCE_REF_RESETS;
        this.BILL_NAME_PRE = BILL_NAME_PRE;
        this.BILL_FNAME = BILL_FNAME;
        this.BILL_MINIT = BILL_MINIT;
        this.BILL_LNAME = BILL_LNAME;
        this.BILL_NAME_GENERATION = BILL_NAME_GENERATION;
        this.BILL_TITLE = BILL_TITLE;
        this.BILL_COMPANY = BILL_COMPANY;
        this.CUST_ADDRESS1 = CUST_ADDRESS1;
        this.CUST_ADDRESS2 = CUST_ADDRESS2;
        this.CUST_ADDRESS3 = CUST_ADDRESS3;
        this.CUST_ADDRESS4 = CUST_ADDRESS4;
        this.CUST_CITY = CUST_CITY;
        this.CUST_STATE = CUST_STATE;
        this.CUST_COUNTY = CUST_COUNTY;
        this.CUST_UNIT = CUST_UNIT;
        this.CUST_ZIP = CUST_ZIP;
        this.CUST_COUNTRY_CODE = CUST_COUNTRY_CODE;
        this.CUST_FRANCHISE_TAX_CODE = CUST_FRANCHISE_TAX_CODE;
        this.CUST_PHONE1 = CUST_PHONE1;
        this.CUST_PHONE2 = CUST_PHONE2;
        this.BILL_ADDRESS1 = BILL_ADDRESS1;
        this.BILL_ADDRESS2 = BILL_ADDRESS2;
        this.BILL_ADDRESS3 = BILL_ADDRESS3;
        this.BILL_ADDRESS4 = BILL_ADDRESS4;
        this.BILL_CITY = BILL_CITY;
        this.BILL_STATE = BILL_STATE;
        this.BILL_ZIP = BILL_ZIP;
        this.BILL_COUNTRY_CODE = BILL_COUNTRY_CODE;
        this.BILL_FRANCHISE_TAX_CODE = BILL_FRANCHISE_TAX_CODE;
        this.BILL_COUNTY = BILL_COUNTY;
        this.BILL_UNIT = BILL_UNIT;
        this.SSN = SSN;
        this.CONTACT1_NAME = CONTACT1_NAME;
        this.CONTACT2_NAME = CONTACT2_NAME;
        this.CONTACT1_PHONE = CONTACT1_PHONE;
        this.CONTACT2_PHONE = CONTACT2_PHONE;
        this.CUST_FAXNO = CUST_FAXNO;
        this.STATEMENT_TO_FAXNO = STATEMENT_TO_FAXNO;
        this.CREDIT_THRESH = CREDIT_THRESH;
        this.CRED_STATUS = CRED_STATUS;
        this.CREDIT_RATING = CREDIT_RATING;
        this.DISCONNECT_REASON = DISCONNECT_REASON;
        this.DISC_RCV_OPT = DISC_RCV_OPT;
        this.THRESH_REF = THRESH_REF;
        this.BILL_PERIOD = BILL_PERIOD;
        this.BILL_DISP_METH = BILL_DISP_METH;
        this.BILLING_FREQUENCY = BILLING_FREQUENCY;
        this.RATE_CLASS_DEFAULT = RATE_CLASS_DEFAULT;
        this.EXRATE_CLASS = EXRATE_CLASS;
        this.ACCOUNT_STATUS = ACCOUNT_STATUS;
        this.ACCOUNT_STATUS_DT = ACCOUNT_STATUS_DT;
        this.DISTR_CHAN = DISTR_CHAN;
        this.SALES_CODE = SALES_CODE;
        this.MKT_CODE = MKT_CODE;
        this.SIC_CODE = SIC_CODE;
        this.IN_USE = IN_USE;
        this.NO_BILL = NO_BILL;
        this.VIP_CODE = VIP_CODE;
        this.CODEWORD = CODEWORD;
        this.REMARK = REMARK;
        this.CUST_EMAIL = CUST_EMAIL;
        this.STATEMENT_TO_EMAIL = STATEMENT_TO_EMAIL;
        this.CHG_WHO = CHG_WHO;
        this.CHG_DATE = CHG_DATE;
        this.DATE_CREATED = DATE_CREATED;
        this.DATE_ACTIVE = DATE_ACTIVE;
        this.DATE_INACTIVE = DATE_INACTIVE;
        this.CHARGE_THRESHOLD = CHARGE_THRESHOLD;
        this.THRESHOLD = THRESHOLD;
        this.CYCLICAL_THRESHOLD = CYCLICAL_THRESHOLD;
        this.USG_AMT_LIMIT = USG_AMT_LIMIT;
        this.USG_UNITS_LIMIT = USG_UNITS_LIMIT;
        this.REGULATORY_ID = REGULATORY_ID;
        this.GLOBAL_CONTRACT_STATUS = GLOBAL_CONTRACT_STATUS;
        this.ALT_LNAME = ALT_LNAME;
        this.ALT_FNAME = ALT_FNAME;
        this.ALT_COMPANY_NAME = ALT_COMPANY_NAME;
        this.GENDER = GENDER;
        this.DEPT = DEPT;
        this.TITLE = TITLE;
        this.PAYMENT_PROFILE_ID = PAYMENT_PROFILE_ID;
        this.IS_ACTIVATED = IS_ACTIVATED;
        this.IS_TML_ENABLED = IS_TML_ENABLED;
        this.TML_LIMIT = TML_LIMIT;
        this.DEALER_ID = DEALER_ID;
        this.AGENT_ID = AGENT_ID;
        this.UPSELL_TEMPLATE_ID = UPSELL_TEMPLATE_ID;
        this.LATEST_QUOTE_IMAGE = LATEST_QUOTE_IMAGE;
        this.IS_CSS_REGISTERED = IS_CSS_REGISTERED;
        this.PRIMARY_SUBSCR_NO = PRIMARY_SUBSCR_NO;
        this.PRIMARY_SUBSCR_NO_RESETS = PRIMARY_SUBSCR_NO_RESETS;
        this.ACCT_SEG_ID = ACCT_SEG_ID;
        this.NOTIFICATION_LANGUAGE_ID = NOTIFICATION_LANGUAGE_ID;
        this.NOTIFICATION_LEVEL = NOTIFICATION_LEVEL;
        this.PAYMENT_METHOD = PAYMENT_METHOD;
        this.BNK_ACCOUNT_HOLDER = BNK_ACCOUNT_HOLDER;
        this.BNK_ACCOUNT_NO = BNK_ACCOUNT_NO;
        this.BANK_ID = BANK_ID;
        this.TIN_NO = TIN_NO;
    }


    /**
     * Gets the ACCOUNT_NO value for this AccountObject.
     * 
     * @return ACCOUNT_NO
     */
    public String getACCOUNT_NO() {
        return ACCOUNT_NO;
    }


    /**
     * Sets the ACCOUNT_NO value for this AccountObject.
     * 
     * @param ACCOUNT_NO
     */
    public void setACCOUNT_NO(String ACCOUNT_NO) {
        this.ACCOUNT_NO = ACCOUNT_NO;
    }


    /**
     * Gets the ACCOUNT_CODE value for this AccountObject.
     * 
     * @return ACCOUNT_CODE
     */
    public String getACCOUNT_CODE() {
        return ACCOUNT_CODE;
    }


    /**
     * Sets the ACCOUNT_CODE value for this AccountObject.
     * 
     * @param ACCOUNT_CODE
     */
    public void setACCOUNT_CODE(String ACCOUNT_CODE) {
        this.ACCOUNT_CODE = ACCOUNT_CODE;
    }


    /**
     * Gets the CONTRACT_NO value for this AccountObject.
     * 
     * @return CONTRACT_NO
     */
    public String getCONTRACT_NO() {
        return CONTRACT_NO;
    }


    /**
     * Sets the CONTRACT_NO value for this AccountObject.
     * 
     * @param CONTRACT_NO
     */
    public void setCONTRACT_NO(String CONTRACT_NO) {
        this.CONTRACT_NO = CONTRACT_NO;
    }


    /**
     * Gets the CONTRACT_DATE value for this AccountObject.
     * 
     * @return CONTRACT_DATE
     */
    public String getCONTRACT_DATE() {
        return CONTRACT_DATE;
    }


    /**
     * Sets the CONTRACT_DATE value for this AccountObject.
     * 
     * @param CONTRACT_DATE
     */
    public void setCONTRACT_DATE(String CONTRACT_DATE) {
        this.CONTRACT_DATE = CONTRACT_DATE;
    }


    /**
     * Gets the PARENT_ACCOUNT_NO value for this AccountObject.
     * 
     * @return PARENT_ACCOUNT_NO
     */
    public String getPARENT_ACCOUNT_NO() {
        return PARENT_ACCOUNT_NO;
    }


    /**
     * Sets the PARENT_ACCOUNT_NO value for this AccountObject.
     * 
     * @param PARENT_ACCOUNT_NO
     */
    public void setPARENT_ACCOUNT_NO(String PARENT_ACCOUNT_NO) {
        this.PARENT_ACCOUNT_NO = PARENT_ACCOUNT_NO;
    }


    /**
     * Gets the PARENT_ACCOUNT_CODE value for this AccountObject.
     * 
     * @return PARENT_ACCOUNT_CODE
     */
    public String getPARENT_ACCOUNT_CODE() {
        return PARENT_ACCOUNT_CODE;
    }


    /**
     * Sets the PARENT_ACCOUNT_CODE value for this AccountObject.
     * 
     * @param PARENT_ACCOUNT_CODE
     */
    public void setPARENT_ACCOUNT_CODE(String PARENT_ACCOUNT_CODE) {
        this.PARENT_ACCOUNT_CODE = PARENT_ACCOUNT_CODE;
    }


    /**
     * Gets the RESELLER_VERSION_ID value for this AccountObject.
     * 
     * @return RESELLER_VERSION_ID
     */
    public String getRESELLER_VERSION_ID() {
        return RESELLER_VERSION_ID;
    }


    /**
     * Sets the RESELLER_VERSION_ID value for this AccountObject.
     * 
     * @param RESELLER_VERSION_ID
     */
    public void setRESELLER_VERSION_ID(String RESELLER_VERSION_ID) {
        this.RESELLER_VERSION_ID = RESELLER_VERSION_ID;
    }


    /**
     * Gets the CUST_GEO_AREA_ID value for this AccountObject.
     * 
     * @return CUST_GEO_AREA_ID
     */
    public String getCUST_GEO_AREA_ID() {
        return CUST_GEO_AREA_ID;
    }


    /**
     * Sets the CUST_GEO_AREA_ID value for this AccountObject.
     * 
     * @param CUST_GEO_AREA_ID
     */
    public void setCUST_GEO_AREA_ID(String CUST_GEO_AREA_ID) {
        this.CUST_GEO_AREA_ID = CUST_GEO_AREA_ID;
    }


    /**
     * Gets the TIE_CODE value for this AccountObject.
     * 
     * @return TIE_CODE
     */
    public String getTIE_CODE() {
        return TIE_CODE;
    }


    /**
     * Sets the TIE_CODE value for this AccountObject.
     * 
     * @param TIE_CODE
     */
    public void setTIE_CODE(String TIE_CODE) {
        this.TIE_CODE = TIE_CODE;
    }


    /**
     * Gets the BILL_SEQUENCE_NUM value for this AccountObject.
     * 
     * @return BILL_SEQUENCE_NUM
     */
    public String getBILL_SEQUENCE_NUM() {
        return BILL_SEQUENCE_NUM;
    }


    /**
     * Sets the BILL_SEQUENCE_NUM value for this AccountObject.
     * 
     * @param BILL_SEQUENCE_NUM
     */
    public void setBILL_SEQUENCE_NUM(String BILL_SEQUENCE_NUM) {
        this.BILL_SEQUENCE_NUM = BILL_SEQUENCE_NUM;
    }


    /**
     * Gets the CURRENCY_CODE value for this AccountObject.
     * 
     * @return CURRENCY_CODE
     */
    public String getCURRENCY_CODE() {
        return CURRENCY_CODE;
    }


    /**
     * Sets the CURRENCY_CODE value for this AccountObject.
     * 
     * @param CURRENCY_CODE
     */
    public void setCURRENCY_CODE(String CURRENCY_CODE) {
        this.CURRENCY_CODE = CURRENCY_CODE;
    }


    /**
     * Gets the LANGUAGE_CODE value for this AccountObject.
     * 
     * @return LANGUAGE_CODE
     */
    public String getLANGUAGE_CODE() {
        return LANGUAGE_CODE;
    }


    /**
     * Sets the LANGUAGE_CODE value for this AccountObject.
     * 
     * @param LANGUAGE_CODE
     */
    public void setLANGUAGE_CODE(String LANGUAGE_CODE) {
        this.LANGUAGE_CODE = LANGUAGE_CODE;
    }


    /**
     * Gets the ACCOUNT_TYPE value for this AccountObject.
     * 
     * @return ACCOUNT_TYPE
     */
    public String getACCOUNT_TYPE() {
        return ACCOUNT_TYPE;
    }


    /**
     * Sets the ACCOUNT_TYPE value for this AccountObject.
     * 
     * @param ACCOUNT_TYPE
     */
    public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
    }


    /**
     * Gets the ACCOUNT_CATEGORY value for this AccountObject.
     * 
     * @return ACCOUNT_CATEGORY
     */
    public String getACCOUNT_CATEGORY() {
        return ACCOUNT_CATEGORY;
    }


    /**
     * Sets the ACCOUNT_CATEGORY value for this AccountObject.
     * 
     * @param ACCOUNT_CATEGORY
     */
    public void setACCOUNT_CATEGORY(String ACCOUNT_CATEGORY) {
        this.ACCOUNT_CATEGORY = ACCOUNT_CATEGORY;
    }


    /**
     * Gets the PREV_CUTOFF_DATE value for this AccountObject.
     * 
     * @return PREV_CUTOFF_DATE
     */
    public String getPREV_CUTOFF_DATE() {
        return PREV_CUTOFF_DATE;
    }


    /**
     * Sets the PREV_CUTOFF_DATE value for this AccountObject.
     * 
     * @param PREV_CUTOFF_DATE
     */
    public void setPREV_CUTOFF_DATE(String PREV_CUTOFF_DATE) {
        this.PREV_CUTOFF_DATE = PREV_CUTOFF_DATE;
    }


    /**
     * Gets the PREV_BILL_DATE value for this AccountObject.
     * 
     * @return PREV_BILL_DATE
     */
    public String getPREV_BILL_DATE() {
        return PREV_BILL_DATE;
    }


    /**
     * Sets the PREV_BILL_DATE value for this AccountObject.
     * 
     * @param PREV_BILL_DATE
     */
    public void setPREV_BILL_DATE(String PREV_BILL_DATE) {
        this.PREV_BILL_DATE = PREV_BILL_DATE;
    }


    /**
     * Gets the NEXT_BILL_DATE value for this AccountObject.
     * 
     * @return NEXT_BILL_DATE
     */
    public String getNEXT_BILL_DATE() {
        return NEXT_BILL_DATE;
    }


    /**
     * Sets the NEXT_BILL_DATE value for this AccountObject.
     * 
     * @param NEXT_BILL_DATE
     */
    public void setNEXT_BILL_DATE(String NEXT_BILL_DATE) {
        this.NEXT_BILL_DATE = NEXT_BILL_DATE;
    }


    /**
     * Gets the PREV_BILL_REFNO value for this AccountObject.
     * 
     * @return PREV_BILL_REFNO
     */
    public String getPREV_BILL_REFNO() {
        return PREV_BILL_REFNO;
    }


    /**
     * Sets the PREV_BILL_REFNO value for this AccountObject.
     * 
     * @param PREV_BILL_REFNO
     */
    public void setPREV_BILL_REFNO(String PREV_BILL_REFNO) {
        this.PREV_BILL_REFNO = PREV_BILL_REFNO;
    }


    /**
     * Gets the PREV_BILL_REF_RESETS value for this AccountObject.
     * 
     * @return PREV_BILL_REF_RESETS
     */
    public String getPREV_BILL_REF_RESETS() {
        return PREV_BILL_REF_RESETS;
    }


    /**
     * Sets the PREV_BILL_REF_RESETS value for this AccountObject.
     * 
     * @param PREV_BILL_REF_RESETS
     */
    public void setPREV_BILL_REF_RESETS(String PREV_BILL_REF_RESETS) {
        this.PREV_BILL_REF_RESETS = PREV_BILL_REF_RESETS;
    }


    /**
     * Gets the PREV_BALANCE_REFNO value for this AccountObject.
     * 
     * @return PREV_BALANCE_REFNO
     */
    public String getPREV_BALANCE_REFNO() {
        return PREV_BALANCE_REFNO;
    }


    /**
     * Sets the PREV_BALANCE_REFNO value for this AccountObject.
     * 
     * @param PREV_BALANCE_REFNO
     */
    public void setPREV_BALANCE_REFNO(String PREV_BALANCE_REFNO) {
        this.PREV_BALANCE_REFNO = PREV_BALANCE_REFNO;
    }


    /**
     * Gets the PREV_BALANCE_REF_RESETS value for this AccountObject.
     * 
     * @return PREV_BALANCE_REF_RESETS
     */
    public String getPREV_BALANCE_REF_RESETS() {
        return PREV_BALANCE_REF_RESETS;
    }


    /**
     * Sets the PREV_BALANCE_REF_RESETS value for this AccountObject.
     * 
     * @param PREV_BALANCE_REF_RESETS
     */
    public void setPREV_BALANCE_REF_RESETS(String PREV_BALANCE_REF_RESETS) {
        this.PREV_BALANCE_REF_RESETS = PREV_BALANCE_REF_RESETS;
    }


    /**
     * Gets the BILL_NAME_PRE value for this AccountObject.
     * 
     * @return BILL_NAME_PRE
     */
    public String getBILL_NAME_PRE() {
        return BILL_NAME_PRE;
    }


    /**
     * Sets the BILL_NAME_PRE value for this AccountObject.
     * 
     * @param BILL_NAME_PRE
     */
    public void setBILL_NAME_PRE(String BILL_NAME_PRE) {
        this.BILL_NAME_PRE = BILL_NAME_PRE;
    }


    /**
     * Gets the BILL_FNAME value for this AccountObject.
     * 
     * @return BILL_FNAME
     */
    public String getBILL_FNAME() {
        return BILL_FNAME;
    }


    /**
     * Sets the BILL_FNAME value for this AccountObject.
     * 
     * @param BILL_FNAME
     */
    public void setBILL_FNAME(String BILL_FNAME) {
        this.BILL_FNAME = BILL_FNAME;
    }


    /**
     * Gets the BILL_MINIT value for this AccountObject.
     * 
     * @return BILL_MINIT
     */
    public String getBILL_MINIT() {
        return BILL_MINIT;
    }


    /**
     * Sets the BILL_MINIT value for this AccountObject.
     * 
     * @param BILL_MINIT
     */
    public void setBILL_MINIT(String BILL_MINIT) {
        this.BILL_MINIT = BILL_MINIT;
    }


    /**
     * Gets the BILL_LNAME value for this AccountObject.
     * 
     * @return BILL_LNAME
     */
    public String getBILL_LNAME() {
        return BILL_LNAME;
    }


    /**
     * Sets the BILL_LNAME value for this AccountObject.
     * 
     * @param BILL_LNAME
     */
    public void setBILL_LNAME(String BILL_LNAME) {
        this.BILL_LNAME = BILL_LNAME;
    }


    /**
     * Gets the BILL_NAME_GENERATION value for this AccountObject.
     * 
     * @return BILL_NAME_GENERATION
     */
    public String getBILL_NAME_GENERATION() {
        return BILL_NAME_GENERATION;
    }


    /**
     * Sets the BILL_NAME_GENERATION value for this AccountObject.
     * 
     * @param BILL_NAME_GENERATION
     */
    public void setBILL_NAME_GENERATION(String BILL_NAME_GENERATION) {
        this.BILL_NAME_GENERATION = BILL_NAME_GENERATION;
    }


    /**
     * Gets the BILL_TITLE value for this AccountObject.
     * 
     * @return BILL_TITLE
     */
    public String getBILL_TITLE() {
        return BILL_TITLE;
    }


    /**
     * Sets the BILL_TITLE value for this AccountObject.
     * 
     * @param BILL_TITLE
     */
    public void setBILL_TITLE(String BILL_TITLE) {
        this.BILL_TITLE = BILL_TITLE;
    }


    /**
     * Gets the BILL_COMPANY value for this AccountObject.
     * 
     * @return BILL_COMPANY
     */
    public String getBILL_COMPANY() {
        return BILL_COMPANY;
    }


    /**
     * Sets the BILL_COMPANY value for this AccountObject.
     * 
     * @param BILL_COMPANY
     */
    public void setBILL_COMPANY(String BILL_COMPANY) {
        this.BILL_COMPANY = BILL_COMPANY;
    }


    /**
     * Gets the CUST_ADDRESS1 value for this AccountObject.
     * 
     * @return CUST_ADDRESS1
     */
    public String getCUST_ADDRESS1() {
        return CUST_ADDRESS1;
    }


    /**
     * Sets the CUST_ADDRESS1 value for this AccountObject.
     * 
     * @param CUST_ADDRESS1
     */
    public void setCUST_ADDRESS1(String CUST_ADDRESS1) {
        this.CUST_ADDRESS1 = CUST_ADDRESS1;
    }


    /**
     * Gets the CUST_ADDRESS2 value for this AccountObject.
     * 
     * @return CUST_ADDRESS2
     */
    public String getCUST_ADDRESS2() {
        return CUST_ADDRESS2;
    }


    /**
     * Sets the CUST_ADDRESS2 value for this AccountObject.
     * 
     * @param CUST_ADDRESS2
     */
    public void setCUST_ADDRESS2(String CUST_ADDRESS2) {
        this.CUST_ADDRESS2 = CUST_ADDRESS2;
    }


    /**
     * Gets the CUST_ADDRESS3 value for this AccountObject.
     * 
     * @return CUST_ADDRESS3
     */
    public String getCUST_ADDRESS3() {
        return CUST_ADDRESS3;
    }


    /**
     * Sets the CUST_ADDRESS3 value for this AccountObject.
     * 
     * @param CUST_ADDRESS3
     */
    public void setCUST_ADDRESS3(String CUST_ADDRESS3) {
        this.CUST_ADDRESS3 = CUST_ADDRESS3;
    }


    /**
     * Gets the CUST_ADDRESS4 value for this AccountObject.
     * 
     * @return CUST_ADDRESS4
     */
    public String getCUST_ADDRESS4() {
        return CUST_ADDRESS4;
    }


    /**
     * Sets the CUST_ADDRESS4 value for this AccountObject.
     * 
     * @param CUST_ADDRESS4
     */
    public void setCUST_ADDRESS4(String CUST_ADDRESS4) {
        this.CUST_ADDRESS4 = CUST_ADDRESS4;
    }


    /**
     * Gets the CUST_CITY value for this AccountObject.
     * 
     * @return CUST_CITY
     */
    public String getCUST_CITY() {
        return CUST_CITY;
    }


    /**
     * Sets the CUST_CITY value for this AccountObject.
     * 
     * @param CUST_CITY
     */
    public void setCUST_CITY(String CUST_CITY) {
        this.CUST_CITY = CUST_CITY;
    }


    /**
     * Gets the CUST_STATE value for this AccountObject.
     * 
     * @return CUST_STATE
     */
    public String getCUST_STATE() {
        return CUST_STATE;
    }


    /**
     * Sets the CUST_STATE value for this AccountObject.
     * 
     * @param CUST_STATE
     */
    public void setCUST_STATE(String CUST_STATE) {
        this.CUST_STATE = CUST_STATE;
    }


    /**
     * Gets the CUST_COUNTY value for this AccountObject.
     * 
     * @return CUST_COUNTY
     */
    public String getCUST_COUNTY() {
        return CUST_COUNTY;
    }


    /**
     * Sets the CUST_COUNTY value for this AccountObject.
     * 
     * @param CUST_COUNTY
     */
    public void setCUST_COUNTY(String CUST_COUNTY) {
        this.CUST_COUNTY = CUST_COUNTY;
    }


    /**
     * Gets the CUST_UNIT value for this AccountObject.
     * 
     * @return CUST_UNIT
     */
    public String getCUST_UNIT() {
        return CUST_UNIT;
    }


    /**
     * Sets the CUST_UNIT value for this AccountObject.
     * 
     * @param CUST_UNIT
     */
    public void setCUST_UNIT(String CUST_UNIT) {
        this.CUST_UNIT = CUST_UNIT;
    }


    /**
     * Gets the CUST_ZIP value for this AccountObject.
     * 
     * @return CUST_ZIP
     */
    public String getCUST_ZIP() {
        return CUST_ZIP;
    }


    /**
     * Sets the CUST_ZIP value for this AccountObject.
     * 
     * @param CUST_ZIP
     */
    public void setCUST_ZIP(String CUST_ZIP) {
        this.CUST_ZIP = CUST_ZIP;
    }


    /**
     * Gets the CUST_COUNTRY_CODE value for this AccountObject.
     * 
     * @return CUST_COUNTRY_CODE
     */
    public String getCUST_COUNTRY_CODE() {
        return CUST_COUNTRY_CODE;
    }


    /**
     * Sets the CUST_COUNTRY_CODE value for this AccountObject.
     * 
     * @param CUST_COUNTRY_CODE
     */
    public void setCUST_COUNTRY_CODE(String CUST_COUNTRY_CODE) {
        this.CUST_COUNTRY_CODE = CUST_COUNTRY_CODE;
    }


    /**
     * Gets the CUST_FRANCHISE_TAX_CODE value for this AccountObject.
     * 
     * @return CUST_FRANCHISE_TAX_CODE
     */
    public String getCUST_FRANCHISE_TAX_CODE() {
        return CUST_FRANCHISE_TAX_CODE;
    }


    /**
     * Sets the CUST_FRANCHISE_TAX_CODE value for this AccountObject.
     * 
     * @param CUST_FRANCHISE_TAX_CODE
     */
    public void setCUST_FRANCHISE_TAX_CODE(String CUST_FRANCHISE_TAX_CODE) {
        this.CUST_FRANCHISE_TAX_CODE = CUST_FRANCHISE_TAX_CODE;
    }


    /**
     * Gets the CUST_PHONE1 value for this AccountObject.
     * 
     * @return CUST_PHONE1
     */
    public String getCUST_PHONE1() {
        return CUST_PHONE1;
    }


    /**
     * Sets the CUST_PHONE1 value for this AccountObject.
     * 
     * @param CUST_PHONE1
     */
    public void setCUST_PHONE1(String CUST_PHONE1) {
        this.CUST_PHONE1 = CUST_PHONE1;
    }


    /**
     * Gets the CUST_PHONE2 value for this AccountObject.
     * 
     * @return CUST_PHONE2
     */
    public String getCUST_PHONE2() {
        return CUST_PHONE2;
    }


    /**
     * Sets the CUST_PHONE2 value for this AccountObject.
     * 
     * @param CUST_PHONE2
     */
    public void setCUST_PHONE2(String CUST_PHONE2) {
        this.CUST_PHONE2 = CUST_PHONE2;
    }


    /**
     * Gets the BILL_ADDRESS1 value for this AccountObject.
     * 
     * @return BILL_ADDRESS1
     */
    public String getBILL_ADDRESS1() {
        return BILL_ADDRESS1;
    }


    /**
     * Sets the BILL_ADDRESS1 value for this AccountObject.
     * 
     * @param BILL_ADDRESS1
     */
    public void setBILL_ADDRESS1(String BILL_ADDRESS1) {
        this.BILL_ADDRESS1 = BILL_ADDRESS1;
    }


    /**
     * Gets the BILL_ADDRESS2 value for this AccountObject.
     * 
     * @return BILL_ADDRESS2
     */
    public String getBILL_ADDRESS2() {
        return BILL_ADDRESS2;
    }


    /**
     * Sets the BILL_ADDRESS2 value for this AccountObject.
     * 
     * @param BILL_ADDRESS2
     */
    public void setBILL_ADDRESS2(String BILL_ADDRESS2) {
        this.BILL_ADDRESS2 = BILL_ADDRESS2;
    }


    /**
     * Gets the BILL_ADDRESS3 value for this AccountObject.
     * 
     * @return BILL_ADDRESS3
     */
    public String getBILL_ADDRESS3() {
        return BILL_ADDRESS3;
    }


    /**
     * Sets the BILL_ADDRESS3 value for this AccountObject.
     * 
     * @param BILL_ADDRESS3
     */
    public void setBILL_ADDRESS3(String BILL_ADDRESS3) {
        this.BILL_ADDRESS3 = BILL_ADDRESS3;
    }


    /**
     * Gets the BILL_ADDRESS4 value for this AccountObject.
     * 
     * @return BILL_ADDRESS4
     */
    public String getBILL_ADDRESS4() {
        return BILL_ADDRESS4;
    }


    /**
     * Sets the BILL_ADDRESS4 value for this AccountObject.
     * 
     * @param BILL_ADDRESS4
     */
    public void setBILL_ADDRESS4(String BILL_ADDRESS4) {
        this.BILL_ADDRESS4 = BILL_ADDRESS4;
    }


    /**
     * Gets the BILL_CITY value for this AccountObject.
     * 
     * @return BILL_CITY
     */
    public String getBILL_CITY() {
        return BILL_CITY;
    }


    /**
     * Sets the BILL_CITY value for this AccountObject.
     * 
     * @param BILL_CITY
     */
    public void setBILL_CITY(String BILL_CITY) {
        this.BILL_CITY = BILL_CITY;
    }


    /**
     * Gets the BILL_STATE value for this AccountObject.
     * 
     * @return BILL_STATE
     */
    public String getBILL_STATE() {
        return BILL_STATE;
    }


    /**
     * Sets the BILL_STATE value for this AccountObject.
     * 
     * @param BILL_STATE
     */
    public void setBILL_STATE(String BILL_STATE) {
        this.BILL_STATE = BILL_STATE;
    }


    /**
     * Gets the BILL_ZIP value for this AccountObject.
     * 
     * @return BILL_ZIP
     */
    public String getBILL_ZIP() {
        return BILL_ZIP;
    }


    /**
     * Sets the BILL_ZIP value for this AccountObject.
     * 
     * @param BILL_ZIP
     */
    public void setBILL_ZIP(String BILL_ZIP) {
        this.BILL_ZIP = BILL_ZIP;
    }


    /**
     * Gets the BILL_COUNTRY_CODE value for this AccountObject.
     * 
     * @return BILL_COUNTRY_CODE
     */
    public String getBILL_COUNTRY_CODE() {
        return BILL_COUNTRY_CODE;
    }


    /**
     * Sets the BILL_COUNTRY_CODE value for this AccountObject.
     * 
     * @param BILL_COUNTRY_CODE
     */
    public void setBILL_COUNTRY_CODE(String BILL_COUNTRY_CODE) {
        this.BILL_COUNTRY_CODE = BILL_COUNTRY_CODE;
    }


    /**
     * Gets the BILL_FRANCHISE_TAX_CODE value for this AccountObject.
     * 
     * @return BILL_FRANCHISE_TAX_CODE
     */
    public String getBILL_FRANCHISE_TAX_CODE() {
        return BILL_FRANCHISE_TAX_CODE;
    }


    /**
     * Sets the BILL_FRANCHISE_TAX_CODE value for this AccountObject.
     * 
     * @param BILL_FRANCHISE_TAX_CODE
     */
    public void setBILL_FRANCHISE_TAX_CODE(String BILL_FRANCHISE_TAX_CODE) {
        this.BILL_FRANCHISE_TAX_CODE = BILL_FRANCHISE_TAX_CODE;
    }


    /**
     * Gets the BILL_COUNTY value for this AccountObject.
     * 
     * @return BILL_COUNTY
     */
    public String getBILL_COUNTY() {
        return BILL_COUNTY;
    }


    /**
     * Sets the BILL_COUNTY value for this AccountObject.
     * 
     * @param BILL_COUNTY
     */
    public void setBILL_COUNTY(String BILL_COUNTY) {
        this.BILL_COUNTY = BILL_COUNTY;
    }


    /**
     * Gets the BILL_UNIT value for this AccountObject.
     * 
     * @return BILL_UNIT
     */
    public String getBILL_UNIT() {
        return BILL_UNIT;
    }


    /**
     * Sets the BILL_UNIT value for this AccountObject.
     * 
     * @param BILL_UNIT
     */
    public void setBILL_UNIT(String BILL_UNIT) {
        this.BILL_UNIT = BILL_UNIT;
    }


    /**
     * Gets the SSN value for this AccountObject.
     * 
     * @return SSN
     */
    public String getSSN() {
        return SSN;
    }


    /**
     * Sets the SSN value for this AccountObject.
     * 
     * @param SSN
     */
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }


    /**
     * Gets the CONTACT1_NAME value for this AccountObject.
     * 
     * @return CONTACT1_NAME
     */
    public String getCONTACT1_NAME() {
        return CONTACT1_NAME;
    }


    /**
     * Sets the CONTACT1_NAME value for this AccountObject.
     * 
     * @param CONTACT1_NAME
     */
    public void setCONTACT1_NAME(String CONTACT1_NAME) {
        this.CONTACT1_NAME = CONTACT1_NAME;
    }


    /**
     * Gets the CONTACT2_NAME value for this AccountObject.
     * 
     * @return CONTACT2_NAME
     */
    public String getCONTACT2_NAME() {
        return CONTACT2_NAME;
    }


    /**
     * Sets the CONTACT2_NAME value for this AccountObject.
     * 
     * @param CONTACT2_NAME
     */
    public void setCONTACT2_NAME(String CONTACT2_NAME) {
        this.CONTACT2_NAME = CONTACT2_NAME;
    }


    /**
     * Gets the CONTACT1_PHONE value for this AccountObject.
     * 
     * @return CONTACT1_PHONE
     */
    public String getCONTACT1_PHONE() {
        return CONTACT1_PHONE;
    }


    /**
     * Sets the CONTACT1_PHONE value for this AccountObject.
     * 
     * @param CONTACT1_PHONE
     */
    public void setCONTACT1_PHONE(String CONTACT1_PHONE) {
        this.CONTACT1_PHONE = CONTACT1_PHONE;
    }


    /**
     * Gets the CONTACT2_PHONE value for this AccountObject.
     * 
     * @return CONTACT2_PHONE
     */
    public String getCONTACT2_PHONE() {
        return CONTACT2_PHONE;
    }


    /**
     * Sets the CONTACT2_PHONE value for this AccountObject.
     * 
     * @param CONTACT2_PHONE
     */
    public void setCONTACT2_PHONE(String CONTACT2_PHONE) {
        this.CONTACT2_PHONE = CONTACT2_PHONE;
    }


    /**
     * Gets the CUST_FAXNO value for this AccountObject.
     * 
     * @return CUST_FAXNO
     */
    public String getCUST_FAXNO() {
        return CUST_FAXNO;
    }


    /**
     * Sets the CUST_FAXNO value for this AccountObject.
     * 
     * @param CUST_FAXNO
     */
    public void setCUST_FAXNO(String CUST_FAXNO) {
        this.CUST_FAXNO = CUST_FAXNO;
    }


    /**
     * Gets the STATEMENT_TO_FAXNO value for this AccountObject.
     * 
     * @return STATEMENT_TO_FAXNO
     */
    public String getSTATEMENT_TO_FAXNO() {
        return STATEMENT_TO_FAXNO;
    }


    /**
     * Sets the STATEMENT_TO_FAXNO value for this AccountObject.
     * 
     * @param STATEMENT_TO_FAXNO
     */
    public void setSTATEMENT_TO_FAXNO(String STATEMENT_TO_FAXNO) {
        this.STATEMENT_TO_FAXNO = STATEMENT_TO_FAXNO;
    }


    /**
     * Gets the CREDIT_THRESH value for this AccountObject.
     * 
     * @return CREDIT_THRESH
     */
    public String getCREDIT_THRESH() {
        return CREDIT_THRESH;
    }


    /**
     * Sets the CREDIT_THRESH value for this AccountObject.
     * 
     * @param CREDIT_THRESH
     */
    public void setCREDIT_THRESH(String CREDIT_THRESH) {
        this.CREDIT_THRESH = CREDIT_THRESH;
    }


    /**
     * Gets the CRED_STATUS value for this AccountObject.
     * 
     * @return CRED_STATUS
     */
    public String getCRED_STATUS() {
        return CRED_STATUS;
    }


    /**
     * Sets the CRED_STATUS value for this AccountObject.
     * 
     * @param CRED_STATUS
     */
    public void setCRED_STATUS(String CRED_STATUS) {
        this.CRED_STATUS = CRED_STATUS;
    }


    /**
     * Gets the CREDIT_RATING value for this AccountObject.
     * 
     * @return CREDIT_RATING
     */
    public String getCREDIT_RATING() {
        return CREDIT_RATING;
    }


    /**
     * Sets the CREDIT_RATING value for this AccountObject.
     * 
     * @param CREDIT_RATING
     */
    public void setCREDIT_RATING(String CREDIT_RATING) {
        this.CREDIT_RATING = CREDIT_RATING;
    }


    /**
     * Gets the DISCONNECT_REASON value for this AccountObject.
     * 
     * @return DISCONNECT_REASON
     */
    public String getDISCONNECT_REASON() {
        return DISCONNECT_REASON;
    }


    /**
     * Sets the DISCONNECT_REASON value for this AccountObject.
     * 
     * @param DISCONNECT_REASON
     */
    public void setDISCONNECT_REASON(String DISCONNECT_REASON) {
        this.DISCONNECT_REASON = DISCONNECT_REASON;
    }


    /**
     * Gets the DISC_RCV_OPT value for this AccountObject.
     * 
     * @return DISC_RCV_OPT
     */
    public String getDISC_RCV_OPT() {
        return DISC_RCV_OPT;
    }


    /**
     * Sets the DISC_RCV_OPT value for this AccountObject.
     * 
     * @param DISC_RCV_OPT
     */
    public void setDISC_RCV_OPT(String DISC_RCV_OPT) {
        this.DISC_RCV_OPT = DISC_RCV_OPT;
    }


    /**
     * Gets the THRESH_REF value for this AccountObject.
     * 
     * @return THRESH_REF
     */
    public String getTHRESH_REF() {
        return THRESH_REF;
    }


    /**
     * Sets the THRESH_REF value for this AccountObject.
     * 
     * @param THRESH_REF
     */
    public void setTHRESH_REF(String THRESH_REF) {
        this.THRESH_REF = THRESH_REF;
    }


    /**
     * Gets the BILL_PERIOD value for this AccountObject.
     * 
     * @return BILL_PERIOD
     */
    public String getBILL_PERIOD() {
        return BILL_PERIOD;
    }


    /**
     * Sets the BILL_PERIOD value for this AccountObject.
     * 
     * @param BILL_PERIOD
     */
    public void setBILL_PERIOD(String BILL_PERIOD) {
        this.BILL_PERIOD = BILL_PERIOD;
    }


    /**
     * Gets the BILL_DISP_METH value for this AccountObject.
     * 
     * @return BILL_DISP_METH
     */
    public String getBILL_DISP_METH() {
        return BILL_DISP_METH;
    }


    /**
     * Sets the BILL_DISP_METH value for this AccountObject.
     * 
     * @param BILL_DISP_METH
     */
    public void setBILL_DISP_METH(String BILL_DISP_METH) {
        this.BILL_DISP_METH = BILL_DISP_METH;
    }


    /**
     * Gets the BILLING_FREQUENCY value for this AccountObject.
     * 
     * @return BILLING_FREQUENCY
     */
    public String getBILLING_FREQUENCY() {
        return BILLING_FREQUENCY;
    }


    /**
     * Sets the BILLING_FREQUENCY value for this AccountObject.
     * 
     * @param BILLING_FREQUENCY
     */
    public void setBILLING_FREQUENCY(String BILLING_FREQUENCY) {
        this.BILLING_FREQUENCY = BILLING_FREQUENCY;
    }


    /**
     * Gets the RATE_CLASS_DEFAULT value for this AccountObject.
     * 
     * @return RATE_CLASS_DEFAULT
     */
    public String getRATE_CLASS_DEFAULT() {
        return RATE_CLASS_DEFAULT;
    }


    /**
     * Sets the RATE_CLASS_DEFAULT value for this AccountObject.
     * 
     * @param RATE_CLASS_DEFAULT
     */
    public void setRATE_CLASS_DEFAULT(String RATE_CLASS_DEFAULT) {
        this.RATE_CLASS_DEFAULT = RATE_CLASS_DEFAULT;
    }


    /**
     * Gets the EXRATE_CLASS value for this AccountObject.
     * 
     * @return EXRATE_CLASS
     */
    public String getEXRATE_CLASS() {
        return EXRATE_CLASS;
    }


    /**
     * Sets the EXRATE_CLASS value for this AccountObject.
     * 
     * @param EXRATE_CLASS
     */
    public void setEXRATE_CLASS(String EXRATE_CLASS) {
        this.EXRATE_CLASS = EXRATE_CLASS;
    }


    /**
     * Gets the ACCOUNT_STATUS value for this AccountObject.
     * 
     * @return ACCOUNT_STATUS
     */
    public String getACCOUNT_STATUS() {
        return ACCOUNT_STATUS;
    }


    /**
     * Sets the ACCOUNT_STATUS value for this AccountObject.
     * 
     * @param ACCOUNT_STATUS
     */
    public void setACCOUNT_STATUS(String ACCOUNT_STATUS) {
        this.ACCOUNT_STATUS = ACCOUNT_STATUS;
    }


    /**
     * Gets the ACCOUNT_STATUS_DT value for this AccountObject.
     * 
     * @return ACCOUNT_STATUS_DT
     */
    public String getACCOUNT_STATUS_DT() {
        return ACCOUNT_STATUS_DT;
    }


    /**
     * Sets the ACCOUNT_STATUS_DT value for this AccountObject.
     * 
     * @param ACCOUNT_STATUS_DT
     */
    public void setACCOUNT_STATUS_DT(String ACCOUNT_STATUS_DT) {
        this.ACCOUNT_STATUS_DT = ACCOUNT_STATUS_DT;
    }


    /**
     * Gets the DISTR_CHAN value for this AccountObject.
     * 
     * @return DISTR_CHAN
     */
    public String getDISTR_CHAN() {
        return DISTR_CHAN;
    }


    /**
     * Sets the DISTR_CHAN value for this AccountObject.
     * 
     * @param DISTR_CHAN
     */
    public void setDISTR_CHAN(String DISTR_CHAN) {
        this.DISTR_CHAN = DISTR_CHAN;
    }


    /**
     * Gets the SALES_CODE value for this AccountObject.
     * 
     * @return SALES_CODE
     */
    public String getSALES_CODE() {
        return SALES_CODE;
    }


    /**
     * Sets the SALES_CODE value for this AccountObject.
     * 
     * @param SALES_CODE
     */
    public void setSALES_CODE(String SALES_CODE) {
        this.SALES_CODE = SALES_CODE;
    }


    /**
     * Gets the MKT_CODE value for this AccountObject.
     * 
     * @return MKT_CODE
     */
    public String getMKT_CODE() {
        return MKT_CODE;
    }


    /**
     * Sets the MKT_CODE value for this AccountObject.
     * 
     * @param MKT_CODE
     */
    public void setMKT_CODE(String MKT_CODE) {
        this.MKT_CODE = MKT_CODE;
    }


    /**
     * Gets the SIC_CODE value for this AccountObject.
     * 
     * @return SIC_CODE
     */
    public String getSIC_CODE() {
        return SIC_CODE;
    }


    /**
     * Sets the SIC_CODE value for this AccountObject.
     * 
     * @param SIC_CODE
     */
    public void setSIC_CODE(String SIC_CODE) {
        this.SIC_CODE = SIC_CODE;
    }


    /**
     * Gets the IN_USE value for this AccountObject.
     * 
     * @return IN_USE
     */
    public String getIN_USE() {
        return IN_USE;
    }


    /**
     * Sets the IN_USE value for this AccountObject.
     * 
     * @param IN_USE
     */
    public void setIN_USE(String IN_USE) {
        this.IN_USE = IN_USE;
    }


    /**
     * Gets the NO_BILL value for this AccountObject.
     * 
     * @return NO_BILL
     */
    public String getNO_BILL() {
        return NO_BILL;
    }


    /**
     * Sets the NO_BILL value for this AccountObject.
     * 
     * @param NO_BILL
     */
    public void setNO_BILL(String NO_BILL) {
        this.NO_BILL = NO_BILL;
    }


    /**
     * Gets the VIP_CODE value for this AccountObject.
     * 
     * @return VIP_CODE
     */
    public String getVIP_CODE() {
        return VIP_CODE;
    }


    /**
     * Sets the VIP_CODE value for this AccountObject.
     * 
     * @param VIP_CODE
     */
    public void setVIP_CODE(String VIP_CODE) {
        this.VIP_CODE = VIP_CODE;
    }


    /**
     * Gets the CODEWORD value for this AccountObject.
     * 
     * @return CODEWORD
     */
    public String getCODEWORD() {
        return CODEWORD;
    }


    /**
     * Sets the CODEWORD value for this AccountObject.
     * 
     * @param CODEWORD
     */
    public void setCODEWORD(String CODEWORD) {
        this.CODEWORD = CODEWORD;
    }


    /**
     * Gets the REMARK value for this AccountObject.
     * 
     * @return REMARK
     */
    public String getREMARK() {
        return REMARK;
    }


    /**
     * Sets the REMARK value for this AccountObject.
     * 
     * @param REMARK
     */
    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }


    /**
     * Gets the CUST_EMAIL value for this AccountObject.
     * 
     * @return CUST_EMAIL
     */
    public String getCUST_EMAIL() {
        return CUST_EMAIL;
    }


    /**
     * Sets the CUST_EMAIL value for this AccountObject.
     * 
     * @param CUST_EMAIL
     */
    public void setCUST_EMAIL(String CUST_EMAIL) {
        this.CUST_EMAIL = CUST_EMAIL;
    }


    /**
     * Gets the STATEMENT_TO_EMAIL value for this AccountObject.
     * 
     * @return STATEMENT_TO_EMAIL
     */
    public String getSTATEMENT_TO_EMAIL() {
        return STATEMENT_TO_EMAIL;
    }


    /**
     * Sets the STATEMENT_TO_EMAIL value for this AccountObject.
     * 
     * @param STATEMENT_TO_EMAIL
     */
    public void setSTATEMENT_TO_EMAIL(String STATEMENT_TO_EMAIL) {
        this.STATEMENT_TO_EMAIL = STATEMENT_TO_EMAIL;
    }


    /**
     * Gets the CHG_WHO value for this AccountObject.
     * 
     * @return CHG_WHO
     */
    public String getCHG_WHO() {
        return CHG_WHO;
    }


    /**
     * Sets the CHG_WHO value for this AccountObject.
     * 
     * @param CHG_WHO
     */
    public void setCHG_WHO(String CHG_WHO) {
        this.CHG_WHO = CHG_WHO;
    }


    /**
     * Gets the CHG_DATE value for this AccountObject.
     * 
     * @return CHG_DATE
     */
    public String getCHG_DATE() {
        return CHG_DATE;
    }


    /**
     * Sets the CHG_DATE value for this AccountObject.
     * 
     * @param CHG_DATE
     */
    public void setCHG_DATE(String CHG_DATE) {
        this.CHG_DATE = CHG_DATE;
    }


    /**
     * Gets the DATE_CREATED value for this AccountObject.
     * 
     * @return DATE_CREATED
     */
    public String getDATE_CREATED() {
        return DATE_CREATED;
    }


    /**
     * Sets the DATE_CREATED value for this AccountObject.
     * 
     * @param DATE_CREATED
     */
    public void setDATE_CREATED(String DATE_CREATED) {
        this.DATE_CREATED = DATE_CREATED;
    }


    /**
     * Gets the DATE_ACTIVE value for this AccountObject.
     * 
     * @return DATE_ACTIVE
     */
    public String getDATE_ACTIVE() {
        return DATE_ACTIVE;
    }


    /**
     * Sets the DATE_ACTIVE value for this AccountObject.
     * 
     * @param DATE_ACTIVE
     */
    public void setDATE_ACTIVE(String DATE_ACTIVE) {
        this.DATE_ACTIVE = DATE_ACTIVE;
    }


    /**
     * Gets the DATE_INACTIVE value for this AccountObject.
     * 
     * @return DATE_INACTIVE
     */
    public String getDATE_INACTIVE() {
        return DATE_INACTIVE;
    }


    /**
     * Sets the DATE_INACTIVE value for this AccountObject.
     * 
     * @param DATE_INACTIVE
     */
    public void setDATE_INACTIVE(String DATE_INACTIVE) {
        this.DATE_INACTIVE = DATE_INACTIVE;
    }


    /**
     * Gets the CHARGE_THRESHOLD value for this AccountObject.
     * 
     * @return CHARGE_THRESHOLD
     */
    public String getCHARGE_THRESHOLD() {
        return CHARGE_THRESHOLD;
    }


    /**
     * Sets the CHARGE_THRESHOLD value for this AccountObject.
     * 
     * @param CHARGE_THRESHOLD
     */
    public void setCHARGE_THRESHOLD(String CHARGE_THRESHOLD) {
        this.CHARGE_THRESHOLD = CHARGE_THRESHOLD;
    }


    /**
     * Gets the THRESHOLD value for this AccountObject.
     * 
     * @return THRESHOLD
     */
    public String getTHRESHOLD() {
        return THRESHOLD;
    }


    /**
     * Sets the THRESHOLD value for this AccountObject.
     * 
     * @param THRESHOLD
     */
    public void setTHRESHOLD(String THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }


    /**
     * Gets the CYCLICAL_THRESHOLD value for this AccountObject.
     * 
     * @return CYCLICAL_THRESHOLD
     */
    public String getCYCLICAL_THRESHOLD() {
        return CYCLICAL_THRESHOLD;
    }


    /**
     * Sets the CYCLICAL_THRESHOLD value for this AccountObject.
     * 
     * @param CYCLICAL_THRESHOLD
     */
    public void setCYCLICAL_THRESHOLD(String CYCLICAL_THRESHOLD) {
        this.CYCLICAL_THRESHOLD = CYCLICAL_THRESHOLD;
    }


    /**
     * Gets the USG_AMT_LIMIT value for this AccountObject.
     * 
     * @return USG_AMT_LIMIT
     */
    public String getUSG_AMT_LIMIT() {
        return USG_AMT_LIMIT;
    }


    /**
     * Sets the USG_AMT_LIMIT value for this AccountObject.
     * 
     * @param USG_AMT_LIMIT
     */
    public void setUSG_AMT_LIMIT(String USG_AMT_LIMIT) {
        this.USG_AMT_LIMIT = USG_AMT_LIMIT;
    }


    /**
     * Gets the USG_UNITS_LIMIT value for this AccountObject.
     * 
     * @return USG_UNITS_LIMIT
     */
    public String getUSG_UNITS_LIMIT() {
        return USG_UNITS_LIMIT;
    }


    /**
     * Sets the USG_UNITS_LIMIT value for this AccountObject.
     * 
     * @param USG_UNITS_LIMIT
     */
    public void setUSG_UNITS_LIMIT(String USG_UNITS_LIMIT) {
        this.USG_UNITS_LIMIT = USG_UNITS_LIMIT;
    }


    /**
     * Gets the REGULATORY_ID value for this AccountObject.
     * 
     * @return REGULATORY_ID
     */
    public String getREGULATORY_ID() {
        return REGULATORY_ID;
    }


    /**
     * Sets the REGULATORY_ID value for this AccountObject.
     * 
     * @param REGULATORY_ID
     */
    public void setREGULATORY_ID(String REGULATORY_ID) {
        this.REGULATORY_ID = REGULATORY_ID;
    }


    /**
     * Gets the GLOBAL_CONTRACT_STATUS value for this AccountObject.
     * 
     * @return GLOBAL_CONTRACT_STATUS
     */
    public String getGLOBAL_CONTRACT_STATUS() {
        return GLOBAL_CONTRACT_STATUS;
    }


    /**
     * Sets the GLOBAL_CONTRACT_STATUS value for this AccountObject.
     * 
     * @param GLOBAL_CONTRACT_STATUS
     */
    public void setGLOBAL_CONTRACT_STATUS(String GLOBAL_CONTRACT_STATUS) {
        this.GLOBAL_CONTRACT_STATUS = GLOBAL_CONTRACT_STATUS;
    }


    /**
     * Gets the ALT_LNAME value for this AccountObject.
     * 
     * @return ALT_LNAME
     */
    public String getALT_LNAME() {
        return ALT_LNAME;
    }


    /**
     * Sets the ALT_LNAME value for this AccountObject.
     * 
     * @param ALT_LNAME
     */
    public void setALT_LNAME(String ALT_LNAME) {
        this.ALT_LNAME = ALT_LNAME;
    }


    /**
     * Gets the ALT_FNAME value for this AccountObject.
     * 
     * @return ALT_FNAME
     */
    public String getALT_FNAME() {
        return ALT_FNAME;
    }


    /**
     * Sets the ALT_FNAME value for this AccountObject.
     * 
     * @param ALT_FNAME
     */
    public void setALT_FNAME(String ALT_FNAME) {
        this.ALT_FNAME = ALT_FNAME;
    }


    /**
     * Gets the ALT_COMPANY_NAME value for this AccountObject.
     * 
     * @return ALT_COMPANY_NAME
     */
    public String getALT_COMPANY_NAME() {
        return ALT_COMPANY_NAME;
    }


    /**
     * Sets the ALT_COMPANY_NAME value for this AccountObject.
     * 
     * @param ALT_COMPANY_NAME
     */
    public void setALT_COMPANY_NAME(String ALT_COMPANY_NAME) {
        this.ALT_COMPANY_NAME = ALT_COMPANY_NAME;
    }


    /**
     * Gets the GENDER value for this AccountObject.
     * 
     * @return GENDER
     */
    public String getGENDER() {
        return GENDER;
    }


    /**
     * Sets the GENDER value for this AccountObject.
     * 
     * @param GENDER
     */
    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }


    /**
     * Gets the DEPT value for this AccountObject.
     * 
     * @return DEPT
     */
    public String getDEPT() {
        return DEPT;
    }


    /**
     * Sets the DEPT value for this AccountObject.
     * 
     * @param DEPT
     */
    public void setDEPT(String DEPT) {
        this.DEPT = DEPT;
    }


    /**
     * Gets the TITLE value for this AccountObject.
     * 
     * @return TITLE
     */
    public String getTITLE() {
        return TITLE;
    }


    /**
     * Sets the TITLE value for this AccountObject.
     * 
     * @param TITLE
     */
    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }


    /**
     * Gets the PAYMENT_PROFILE_ID value for this AccountObject.
     * 
     * @return PAYMENT_PROFILE_ID
     */
    public String getPAYMENT_PROFILE_ID() {
        return PAYMENT_PROFILE_ID;
    }


    /**
     * Sets the PAYMENT_PROFILE_ID value for this AccountObject.
     * 
     * @param PAYMENT_PROFILE_ID
     */
    public void setPAYMENT_PROFILE_ID(String PAYMENT_PROFILE_ID) {
        this.PAYMENT_PROFILE_ID = PAYMENT_PROFILE_ID;
    }


    /**
     * Gets the IS_ACTIVATED value for this AccountObject.
     * 
     * @return IS_ACTIVATED
     */
    public String getIS_ACTIVATED() {
        return IS_ACTIVATED;
    }


    /**
     * Sets the IS_ACTIVATED value for this AccountObject.
     * 
     * @param IS_ACTIVATED
     */
    public void setIS_ACTIVATED(String IS_ACTIVATED) {
        this.IS_ACTIVATED = IS_ACTIVATED;
    }


    /**
     * Gets the IS_TML_ENABLED value for this AccountObject.
     * 
     * @return IS_TML_ENABLED
     */
    public String getIS_TML_ENABLED() {
        return IS_TML_ENABLED;
    }


    /**
     * Sets the IS_TML_ENABLED value for this AccountObject.
     * 
     * @param IS_TML_ENABLED
     */
    public void setIS_TML_ENABLED(String IS_TML_ENABLED) {
        this.IS_TML_ENABLED = IS_TML_ENABLED;
    }


    /**
     * Gets the TML_LIMIT value for this AccountObject.
     * 
     * @return TML_LIMIT
     */
    public String getTML_LIMIT() {
        return TML_LIMIT;
    }


    /**
     * Sets the TML_LIMIT value for this AccountObject.
     * 
     * @param TML_LIMIT
     */
    public void setTML_LIMIT(String TML_LIMIT) {
        this.TML_LIMIT = TML_LIMIT;
    }


    /**
     * Gets the DEALER_ID value for this AccountObject.
     * 
     * @return DEALER_ID
     */
    public String getDEALER_ID() {
        return DEALER_ID;
    }


    /**
     * Sets the DEALER_ID value for this AccountObject.
     * 
     * @param DEALER_ID
     */
    public void setDEALER_ID(String DEALER_ID) {
        this.DEALER_ID = DEALER_ID;
    }


    /**
     * Gets the AGENT_ID value for this AccountObject.
     * 
     * @return AGENT_ID
     */
    public String getAGENT_ID() {
        return AGENT_ID;
    }


    /**
     * Sets the AGENT_ID value for this AccountObject.
     * 
     * @param AGENT_ID
     */
    public void setAGENT_ID(String AGENT_ID) {
        this.AGENT_ID = AGENT_ID;
    }


    /**
     * Gets the UPSELL_TEMPLATE_ID value for this AccountObject.
     * 
     * @return UPSELL_TEMPLATE_ID
     */
    public String getUPSELL_TEMPLATE_ID() {
        return UPSELL_TEMPLATE_ID;
    }


    /**
     * Sets the UPSELL_TEMPLATE_ID value for this AccountObject.
     * 
     * @param UPSELL_TEMPLATE_ID
     */
    public void setUPSELL_TEMPLATE_ID(String UPSELL_TEMPLATE_ID) {
        this.UPSELL_TEMPLATE_ID = UPSELL_TEMPLATE_ID;
    }


    /**
     * Gets the LATEST_QUOTE_IMAGE value for this AccountObject.
     * 
     * @return LATEST_QUOTE_IMAGE
     */
    public String getLATEST_QUOTE_IMAGE() {
        return LATEST_QUOTE_IMAGE;
    }


    /**
     * Sets the LATEST_QUOTE_IMAGE value for this AccountObject.
     * 
     * @param LATEST_QUOTE_IMAGE
     */
    public void setLATEST_QUOTE_IMAGE(String LATEST_QUOTE_IMAGE) {
        this.LATEST_QUOTE_IMAGE = LATEST_QUOTE_IMAGE;
    }


    /**
     * Gets the IS_CSS_REGISTERED value for this AccountObject.
     * 
     * @return IS_CSS_REGISTERED
     */
    public String getIS_CSS_REGISTERED() {
        return IS_CSS_REGISTERED;
    }


    /**
     * Sets the IS_CSS_REGISTERED value for this AccountObject.
     * 
     * @param IS_CSS_REGISTERED
     */
    public void setIS_CSS_REGISTERED(String IS_CSS_REGISTERED) {
        this.IS_CSS_REGISTERED = IS_CSS_REGISTERED;
    }


    /**
     * Gets the PRIMARY_SUBSCR_NO value for this AccountObject.
     * 
     * @return PRIMARY_SUBSCR_NO
     */
    public String getPRIMARY_SUBSCR_NO() {
        return PRIMARY_SUBSCR_NO;
    }


    /**
     * Sets the PRIMARY_SUBSCR_NO value for this AccountObject.
     * 
     * @param PRIMARY_SUBSCR_NO
     */
    public void setPRIMARY_SUBSCR_NO(String PRIMARY_SUBSCR_NO) {
        this.PRIMARY_SUBSCR_NO = PRIMARY_SUBSCR_NO;
    }


    /**
     * Gets the PRIMARY_SUBSCR_NO_RESETS value for this AccountObject.
     * 
     * @return PRIMARY_SUBSCR_NO_RESETS
     */
    public String getPRIMARY_SUBSCR_NO_RESETS() {
        return PRIMARY_SUBSCR_NO_RESETS;
    }


    /**
     * Sets the PRIMARY_SUBSCR_NO_RESETS value for this AccountObject.
     * 
     * @param PRIMARY_SUBSCR_NO_RESETS
     */
    public void setPRIMARY_SUBSCR_NO_RESETS(String PRIMARY_SUBSCR_NO_RESETS) {
        this.PRIMARY_SUBSCR_NO_RESETS = PRIMARY_SUBSCR_NO_RESETS;
    }


    /**
     * Gets the ACCT_SEG_ID value for this AccountObject.
     * 
     * @return ACCT_SEG_ID
     */
    public String getACCT_SEG_ID() {
        return ACCT_SEG_ID;
    }


    /**
     * Sets the ACCT_SEG_ID value for this AccountObject.
     * 
     * @param ACCT_SEG_ID
     */
    public void setACCT_SEG_ID(String ACCT_SEG_ID) {
        this.ACCT_SEG_ID = ACCT_SEG_ID;
    }


    /**
     * Gets the NOTIFICATION_LANGUAGE_ID value for this AccountObject.
     * 
     * @return NOTIFICATION_LANGUAGE_ID
     */
    public String getNOTIFICATION_LANGUAGE_ID() {
        return NOTIFICATION_LANGUAGE_ID;
    }


    /**
     * Sets the NOTIFICATION_LANGUAGE_ID value for this AccountObject.
     * 
     * @param NOTIFICATION_LANGUAGE_ID
     */
    public void setNOTIFICATION_LANGUAGE_ID(String NOTIFICATION_LANGUAGE_ID) {
        this.NOTIFICATION_LANGUAGE_ID = NOTIFICATION_LANGUAGE_ID;
    }


    /**
     * Gets the NOTIFICATION_LEVEL value for this AccountObject.
     * 
     * @return NOTIFICATION_LEVEL
     */
    public String getNOTIFICATION_LEVEL() {
        return NOTIFICATION_LEVEL;
    }


    /**
     * Sets the NOTIFICATION_LEVEL value for this AccountObject.
     * 
     * @param NOTIFICATION_LEVEL
     */
    public void setNOTIFICATION_LEVEL(String NOTIFICATION_LEVEL) {
        this.NOTIFICATION_LEVEL = NOTIFICATION_LEVEL;
    }


    /**
     * Gets the PAYMENT_METHOD value for this AccountObject.
     * 
     * @return PAYMENT_METHOD
     */
    public String getPAYMENT_METHOD() {
        return PAYMENT_METHOD;
    }


    /**
     * Sets the PAYMENT_METHOD value for this AccountObject.
     * 
     * @param PAYMENT_METHOD
     */
    public void setPAYMENT_METHOD(String PAYMENT_METHOD) {
        this.PAYMENT_METHOD = PAYMENT_METHOD;
    }


    /**
     * Gets the BNK_ACCOUNT_HOLDER value for this AccountObject.
     * 
     * @return BNK_ACCOUNT_HOLDER
     */
    public String getBNK_ACCOUNT_HOLDER() {
        return BNK_ACCOUNT_HOLDER;
    }


    /**
     * Sets the BNK_ACCOUNT_HOLDER value for this AccountObject.
     * 
     * @param BNK_ACCOUNT_HOLDER
     */
    public void setBNK_ACCOUNT_HOLDER(String BNK_ACCOUNT_HOLDER) {
        this.BNK_ACCOUNT_HOLDER = BNK_ACCOUNT_HOLDER;
    }


    /**
     * Gets the BNK_ACCOUNT_NO value for this AccountObject.
     * 
     * @return BNK_ACCOUNT_NO
     */
    public String getBNK_ACCOUNT_NO() {
        return BNK_ACCOUNT_NO;
    }


    /**
     * Sets the BNK_ACCOUNT_NO value for this AccountObject.
     * 
     * @param BNK_ACCOUNT_NO
     */
    public void setBNK_ACCOUNT_NO(String BNK_ACCOUNT_NO) {
        this.BNK_ACCOUNT_NO = BNK_ACCOUNT_NO;
    }


    /**
     * Gets the BANK_ID value for this AccountObject.
     * 
     * @return BANK_ID
     */
    public String getBANK_ID() {
        return BANK_ID;
    }


    /**
     * Sets the BANK_ID value for this AccountObject.
     * 
     * @param BANK_ID
     */
    public void setBANK_ID(String BANK_ID) {
        this.BANK_ID = BANK_ID;
    }


    /**
     * Gets the TIN_NO value for this AccountObject.
     * 
     * @return TIN_NO
     */
    public String getTIN_NO() {
        return TIN_NO;
    }


    /**
     * Sets the TIN_NO value for this AccountObject.
     * 
     * @param TIN_NO
     */
    public void setTIN_NO(String TIN_NO) {
        this.TIN_NO = TIN_NO;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AccountObject)) return false;
        AccountObject other = (AccountObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.ACCOUNT_NO==null && other.getACCOUNT_NO()==null) || 
             (this.ACCOUNT_NO!=null &&
              this.ACCOUNT_NO.equals(other.getACCOUNT_NO()))) &&
            ((this.ACCOUNT_CODE==null && other.getACCOUNT_CODE()==null) || 
             (this.ACCOUNT_CODE!=null &&
              this.ACCOUNT_CODE.equals(other.getACCOUNT_CODE()))) &&
            ((this.CONTRACT_NO==null && other.getCONTRACT_NO()==null) || 
             (this.CONTRACT_NO!=null &&
              this.CONTRACT_NO.equals(other.getCONTRACT_NO()))) &&
            ((this.CONTRACT_DATE==null && other.getCONTRACT_DATE()==null) || 
             (this.CONTRACT_DATE!=null &&
              this.CONTRACT_DATE.equals(other.getCONTRACT_DATE()))) &&
            ((this.PARENT_ACCOUNT_NO==null && other.getPARENT_ACCOUNT_NO()==null) || 
             (this.PARENT_ACCOUNT_NO!=null &&
              this.PARENT_ACCOUNT_NO.equals(other.getPARENT_ACCOUNT_NO()))) &&
            ((this.PARENT_ACCOUNT_CODE==null && other.getPARENT_ACCOUNT_CODE()==null) || 
             (this.PARENT_ACCOUNT_CODE!=null &&
              this.PARENT_ACCOUNT_CODE.equals(other.getPARENT_ACCOUNT_CODE()))) &&
            ((this.RESELLER_VERSION_ID==null && other.getRESELLER_VERSION_ID()==null) || 
             (this.RESELLER_VERSION_ID!=null &&
              this.RESELLER_VERSION_ID.equals(other.getRESELLER_VERSION_ID()))) &&
            ((this.CUST_GEO_AREA_ID==null && other.getCUST_GEO_AREA_ID()==null) || 
             (this.CUST_GEO_AREA_ID!=null &&
              this.CUST_GEO_AREA_ID.equals(other.getCUST_GEO_AREA_ID()))) &&
            ((this.TIE_CODE==null && other.getTIE_CODE()==null) || 
             (this.TIE_CODE!=null &&
              this.TIE_CODE.equals(other.getTIE_CODE()))) &&
            ((this.BILL_SEQUENCE_NUM==null && other.getBILL_SEQUENCE_NUM()==null) || 
             (this.BILL_SEQUENCE_NUM!=null &&
              this.BILL_SEQUENCE_NUM.equals(other.getBILL_SEQUENCE_NUM()))) &&
            ((this.CURRENCY_CODE==null && other.getCURRENCY_CODE()==null) || 
             (this.CURRENCY_CODE!=null &&
              this.CURRENCY_CODE.equals(other.getCURRENCY_CODE()))) &&
            ((this.LANGUAGE_CODE==null && other.getLANGUAGE_CODE()==null) || 
             (this.LANGUAGE_CODE!=null &&
              this.LANGUAGE_CODE.equals(other.getLANGUAGE_CODE()))) &&
            ((this.ACCOUNT_TYPE==null && other.getACCOUNT_TYPE()==null) || 
             (this.ACCOUNT_TYPE!=null &&
              this.ACCOUNT_TYPE.equals(other.getACCOUNT_TYPE()))) &&
            ((this.ACCOUNT_CATEGORY==null && other.getACCOUNT_CATEGORY()==null) || 
             (this.ACCOUNT_CATEGORY!=null &&
              this.ACCOUNT_CATEGORY.equals(other.getACCOUNT_CATEGORY()))) &&
            ((this.PREV_CUTOFF_DATE==null && other.getPREV_CUTOFF_DATE()==null) || 
             (this.PREV_CUTOFF_DATE!=null &&
              this.PREV_CUTOFF_DATE.equals(other.getPREV_CUTOFF_DATE()))) &&
            ((this.PREV_BILL_DATE==null && other.getPREV_BILL_DATE()==null) || 
             (this.PREV_BILL_DATE!=null &&
              this.PREV_BILL_DATE.equals(other.getPREV_BILL_DATE()))) &&
            ((this.NEXT_BILL_DATE==null && other.getNEXT_BILL_DATE()==null) || 
             (this.NEXT_BILL_DATE!=null &&
              this.NEXT_BILL_DATE.equals(other.getNEXT_BILL_DATE()))) &&
            ((this.PREV_BILL_REFNO==null && other.getPREV_BILL_REFNO()==null) || 
             (this.PREV_BILL_REFNO!=null &&
              this.PREV_BILL_REFNO.equals(other.getPREV_BILL_REFNO()))) &&
            ((this.PREV_BILL_REF_RESETS==null && other.getPREV_BILL_REF_RESETS()==null) || 
             (this.PREV_BILL_REF_RESETS!=null &&
              this.PREV_BILL_REF_RESETS.equals(other.getPREV_BILL_REF_RESETS()))) &&
            ((this.PREV_BALANCE_REFNO==null && other.getPREV_BALANCE_REFNO()==null) || 
             (this.PREV_BALANCE_REFNO!=null &&
              this.PREV_BALANCE_REFNO.equals(other.getPREV_BALANCE_REFNO()))) &&
            ((this.PREV_BALANCE_REF_RESETS==null && other.getPREV_BALANCE_REF_RESETS()==null) || 
             (this.PREV_BALANCE_REF_RESETS!=null &&
              this.PREV_BALANCE_REF_RESETS.equals(other.getPREV_BALANCE_REF_RESETS()))) &&
            ((this.BILL_NAME_PRE==null && other.getBILL_NAME_PRE()==null) || 
             (this.BILL_NAME_PRE!=null &&
              this.BILL_NAME_PRE.equals(other.getBILL_NAME_PRE()))) &&
            ((this.BILL_FNAME==null && other.getBILL_FNAME()==null) || 
             (this.BILL_FNAME!=null &&
              this.BILL_FNAME.equals(other.getBILL_FNAME()))) &&
            ((this.BILL_MINIT==null && other.getBILL_MINIT()==null) || 
             (this.BILL_MINIT!=null &&
              this.BILL_MINIT.equals(other.getBILL_MINIT()))) &&
            ((this.BILL_LNAME==null && other.getBILL_LNAME()==null) || 
             (this.BILL_LNAME!=null &&
              this.BILL_LNAME.equals(other.getBILL_LNAME()))) &&
            ((this.BILL_NAME_GENERATION==null && other.getBILL_NAME_GENERATION()==null) || 
             (this.BILL_NAME_GENERATION!=null &&
              this.BILL_NAME_GENERATION.equals(other.getBILL_NAME_GENERATION()))) &&
            ((this.BILL_TITLE==null && other.getBILL_TITLE()==null) || 
             (this.BILL_TITLE!=null &&
              this.BILL_TITLE.equals(other.getBILL_TITLE()))) &&
            ((this.BILL_COMPANY==null && other.getBILL_COMPANY()==null) || 
             (this.BILL_COMPANY!=null &&
              this.BILL_COMPANY.equals(other.getBILL_COMPANY()))) &&
            ((this.CUST_ADDRESS1==null && other.getCUST_ADDRESS1()==null) || 
             (this.CUST_ADDRESS1!=null &&
              this.CUST_ADDRESS1.equals(other.getCUST_ADDRESS1()))) &&
            ((this.CUST_ADDRESS2==null && other.getCUST_ADDRESS2()==null) || 
             (this.CUST_ADDRESS2!=null &&
              this.CUST_ADDRESS2.equals(other.getCUST_ADDRESS2()))) &&
            ((this.CUST_ADDRESS3==null && other.getCUST_ADDRESS3()==null) || 
             (this.CUST_ADDRESS3!=null &&
              this.CUST_ADDRESS3.equals(other.getCUST_ADDRESS3()))) &&
            ((this.CUST_ADDRESS4==null && other.getCUST_ADDRESS4()==null) || 
             (this.CUST_ADDRESS4!=null &&
              this.CUST_ADDRESS4.equals(other.getCUST_ADDRESS4()))) &&
            ((this.CUST_CITY==null && other.getCUST_CITY()==null) || 
             (this.CUST_CITY!=null &&
              this.CUST_CITY.equals(other.getCUST_CITY()))) &&
            ((this.CUST_STATE==null && other.getCUST_STATE()==null) || 
             (this.CUST_STATE!=null &&
              this.CUST_STATE.equals(other.getCUST_STATE()))) &&
            ((this.CUST_COUNTY==null && other.getCUST_COUNTY()==null) || 
             (this.CUST_COUNTY!=null &&
              this.CUST_COUNTY.equals(other.getCUST_COUNTY()))) &&
            ((this.CUST_UNIT==null && other.getCUST_UNIT()==null) || 
             (this.CUST_UNIT!=null &&
              this.CUST_UNIT.equals(other.getCUST_UNIT()))) &&
            ((this.CUST_ZIP==null && other.getCUST_ZIP()==null) || 
             (this.CUST_ZIP!=null &&
              this.CUST_ZIP.equals(other.getCUST_ZIP()))) &&
            ((this.CUST_COUNTRY_CODE==null && other.getCUST_COUNTRY_CODE()==null) || 
             (this.CUST_COUNTRY_CODE!=null &&
              this.CUST_COUNTRY_CODE.equals(other.getCUST_COUNTRY_CODE()))) &&
            ((this.CUST_FRANCHISE_TAX_CODE==null && other.getCUST_FRANCHISE_TAX_CODE()==null) || 
             (this.CUST_FRANCHISE_TAX_CODE!=null &&
              this.CUST_FRANCHISE_TAX_CODE.equals(other.getCUST_FRANCHISE_TAX_CODE()))) &&
            ((this.CUST_PHONE1==null && other.getCUST_PHONE1()==null) || 
             (this.CUST_PHONE1!=null &&
              this.CUST_PHONE1.equals(other.getCUST_PHONE1()))) &&
            ((this.CUST_PHONE2==null && other.getCUST_PHONE2()==null) || 
             (this.CUST_PHONE2!=null &&
              this.CUST_PHONE2.equals(other.getCUST_PHONE2()))) &&
            ((this.BILL_ADDRESS1==null && other.getBILL_ADDRESS1()==null) || 
             (this.BILL_ADDRESS1!=null &&
              this.BILL_ADDRESS1.equals(other.getBILL_ADDRESS1()))) &&
            ((this.BILL_ADDRESS2==null && other.getBILL_ADDRESS2()==null) || 
             (this.BILL_ADDRESS2!=null &&
              this.BILL_ADDRESS2.equals(other.getBILL_ADDRESS2()))) &&
            ((this.BILL_ADDRESS3==null && other.getBILL_ADDRESS3()==null) || 
             (this.BILL_ADDRESS3!=null &&
              this.BILL_ADDRESS3.equals(other.getBILL_ADDRESS3()))) &&
            ((this.BILL_ADDRESS4==null && other.getBILL_ADDRESS4()==null) || 
             (this.BILL_ADDRESS4!=null &&
              this.BILL_ADDRESS4.equals(other.getBILL_ADDRESS4()))) &&
            ((this.BILL_CITY==null && other.getBILL_CITY()==null) || 
             (this.BILL_CITY!=null &&
              this.BILL_CITY.equals(other.getBILL_CITY()))) &&
            ((this.BILL_STATE==null && other.getBILL_STATE()==null) || 
             (this.BILL_STATE!=null &&
              this.BILL_STATE.equals(other.getBILL_STATE()))) &&
            ((this.BILL_ZIP==null && other.getBILL_ZIP()==null) || 
             (this.BILL_ZIP!=null &&
              this.BILL_ZIP.equals(other.getBILL_ZIP()))) &&
            ((this.BILL_COUNTRY_CODE==null && other.getBILL_COUNTRY_CODE()==null) || 
             (this.BILL_COUNTRY_CODE!=null &&
              this.BILL_COUNTRY_CODE.equals(other.getBILL_COUNTRY_CODE()))) &&
            ((this.BILL_FRANCHISE_TAX_CODE==null && other.getBILL_FRANCHISE_TAX_CODE()==null) || 
             (this.BILL_FRANCHISE_TAX_CODE!=null &&
              this.BILL_FRANCHISE_TAX_CODE.equals(other.getBILL_FRANCHISE_TAX_CODE()))) &&
            ((this.BILL_COUNTY==null && other.getBILL_COUNTY()==null) || 
             (this.BILL_COUNTY!=null &&
              this.BILL_COUNTY.equals(other.getBILL_COUNTY()))) &&
            ((this.BILL_UNIT==null && other.getBILL_UNIT()==null) || 
             (this.BILL_UNIT!=null &&
              this.BILL_UNIT.equals(other.getBILL_UNIT()))) &&
            ((this.SSN==null && other.getSSN()==null) || 
             (this.SSN!=null &&
              this.SSN.equals(other.getSSN()))) &&
            ((this.CONTACT1_NAME==null && other.getCONTACT1_NAME()==null) || 
             (this.CONTACT1_NAME!=null &&
              this.CONTACT1_NAME.equals(other.getCONTACT1_NAME()))) &&
            ((this.CONTACT2_NAME==null && other.getCONTACT2_NAME()==null) || 
             (this.CONTACT2_NAME!=null &&
              this.CONTACT2_NAME.equals(other.getCONTACT2_NAME()))) &&
            ((this.CONTACT1_PHONE==null && other.getCONTACT1_PHONE()==null) || 
             (this.CONTACT1_PHONE!=null &&
              this.CONTACT1_PHONE.equals(other.getCONTACT1_PHONE()))) &&
            ((this.CONTACT2_PHONE==null && other.getCONTACT2_PHONE()==null) || 
             (this.CONTACT2_PHONE!=null &&
              this.CONTACT2_PHONE.equals(other.getCONTACT2_PHONE()))) &&
            ((this.CUST_FAXNO==null && other.getCUST_FAXNO()==null) || 
             (this.CUST_FAXNO!=null &&
              this.CUST_FAXNO.equals(other.getCUST_FAXNO()))) &&
            ((this.STATEMENT_TO_FAXNO==null && other.getSTATEMENT_TO_FAXNO()==null) || 
             (this.STATEMENT_TO_FAXNO!=null &&
              this.STATEMENT_TO_FAXNO.equals(other.getSTATEMENT_TO_FAXNO()))) &&
            ((this.CREDIT_THRESH==null && other.getCREDIT_THRESH()==null) || 
             (this.CREDIT_THRESH!=null &&
              this.CREDIT_THRESH.equals(other.getCREDIT_THRESH()))) &&
            ((this.CRED_STATUS==null && other.getCRED_STATUS()==null) || 
             (this.CRED_STATUS!=null &&
              this.CRED_STATUS.equals(other.getCRED_STATUS()))) &&
            ((this.CREDIT_RATING==null && other.getCREDIT_RATING()==null) || 
             (this.CREDIT_RATING!=null &&
              this.CREDIT_RATING.equals(other.getCREDIT_RATING()))) &&
            ((this.DISCONNECT_REASON==null && other.getDISCONNECT_REASON()==null) || 
             (this.DISCONNECT_REASON!=null &&
              this.DISCONNECT_REASON.equals(other.getDISCONNECT_REASON()))) &&
            ((this.DISC_RCV_OPT==null && other.getDISC_RCV_OPT()==null) || 
             (this.DISC_RCV_OPT!=null &&
              this.DISC_RCV_OPT.equals(other.getDISC_RCV_OPT()))) &&
            ((this.THRESH_REF==null && other.getTHRESH_REF()==null) || 
             (this.THRESH_REF!=null &&
              this.THRESH_REF.equals(other.getTHRESH_REF()))) &&
            ((this.BILL_PERIOD==null && other.getBILL_PERIOD()==null) || 
             (this.BILL_PERIOD!=null &&
              this.BILL_PERIOD.equals(other.getBILL_PERIOD()))) &&
            ((this.BILL_DISP_METH==null && other.getBILL_DISP_METH()==null) || 
             (this.BILL_DISP_METH!=null &&
              this.BILL_DISP_METH.equals(other.getBILL_DISP_METH()))) &&
            ((this.BILLING_FREQUENCY==null && other.getBILLING_FREQUENCY()==null) || 
             (this.BILLING_FREQUENCY!=null &&
              this.BILLING_FREQUENCY.equals(other.getBILLING_FREQUENCY()))) &&
            ((this.RATE_CLASS_DEFAULT==null && other.getRATE_CLASS_DEFAULT()==null) || 
             (this.RATE_CLASS_DEFAULT!=null &&
              this.RATE_CLASS_DEFAULT.equals(other.getRATE_CLASS_DEFAULT()))) &&
            ((this.EXRATE_CLASS==null && other.getEXRATE_CLASS()==null) || 
             (this.EXRATE_CLASS!=null &&
              this.EXRATE_CLASS.equals(other.getEXRATE_CLASS()))) &&
            ((this.ACCOUNT_STATUS==null && other.getACCOUNT_STATUS()==null) || 
             (this.ACCOUNT_STATUS!=null &&
              this.ACCOUNT_STATUS.equals(other.getACCOUNT_STATUS()))) &&
            ((this.ACCOUNT_STATUS_DT==null && other.getACCOUNT_STATUS_DT()==null) || 
             (this.ACCOUNT_STATUS_DT!=null &&
              this.ACCOUNT_STATUS_DT.equals(other.getACCOUNT_STATUS_DT()))) &&
            ((this.DISTR_CHAN==null && other.getDISTR_CHAN()==null) || 
             (this.DISTR_CHAN!=null &&
              this.DISTR_CHAN.equals(other.getDISTR_CHAN()))) &&
            ((this.SALES_CODE==null && other.getSALES_CODE()==null) || 
             (this.SALES_CODE!=null &&
              this.SALES_CODE.equals(other.getSALES_CODE()))) &&
            ((this.MKT_CODE==null && other.getMKT_CODE()==null) || 
             (this.MKT_CODE!=null &&
              this.MKT_CODE.equals(other.getMKT_CODE()))) &&
            ((this.SIC_CODE==null && other.getSIC_CODE()==null) || 
             (this.SIC_CODE!=null &&
              this.SIC_CODE.equals(other.getSIC_CODE()))) &&
            ((this.IN_USE==null && other.getIN_USE()==null) || 
             (this.IN_USE!=null &&
              this.IN_USE.equals(other.getIN_USE()))) &&
            ((this.NO_BILL==null && other.getNO_BILL()==null) || 
             (this.NO_BILL!=null &&
              this.NO_BILL.equals(other.getNO_BILL()))) &&
            ((this.VIP_CODE==null && other.getVIP_CODE()==null) || 
             (this.VIP_CODE!=null &&
              this.VIP_CODE.equals(other.getVIP_CODE()))) &&
            ((this.CODEWORD==null && other.getCODEWORD()==null) || 
             (this.CODEWORD!=null &&
              this.CODEWORD.equals(other.getCODEWORD()))) &&
            ((this.REMARK==null && other.getREMARK()==null) || 
             (this.REMARK!=null &&
              this.REMARK.equals(other.getREMARK()))) &&
            ((this.CUST_EMAIL==null && other.getCUST_EMAIL()==null) || 
             (this.CUST_EMAIL!=null &&
              this.CUST_EMAIL.equals(other.getCUST_EMAIL()))) &&
            ((this.STATEMENT_TO_EMAIL==null && other.getSTATEMENT_TO_EMAIL()==null) || 
             (this.STATEMENT_TO_EMAIL!=null &&
              this.STATEMENT_TO_EMAIL.equals(other.getSTATEMENT_TO_EMAIL()))) &&
            ((this.CHG_WHO==null && other.getCHG_WHO()==null) || 
             (this.CHG_WHO!=null &&
              this.CHG_WHO.equals(other.getCHG_WHO()))) &&
            ((this.CHG_DATE==null && other.getCHG_DATE()==null) || 
             (this.CHG_DATE!=null &&
              this.CHG_DATE.equals(other.getCHG_DATE()))) &&
            ((this.DATE_CREATED==null && other.getDATE_CREATED()==null) || 
             (this.DATE_CREATED!=null &&
              this.DATE_CREATED.equals(other.getDATE_CREATED()))) &&
            ((this.DATE_ACTIVE==null && other.getDATE_ACTIVE()==null) || 
             (this.DATE_ACTIVE!=null &&
              this.DATE_ACTIVE.equals(other.getDATE_ACTIVE()))) &&
            ((this.DATE_INACTIVE==null && other.getDATE_INACTIVE()==null) || 
             (this.DATE_INACTIVE!=null &&
              this.DATE_INACTIVE.equals(other.getDATE_INACTIVE()))) &&
            ((this.CHARGE_THRESHOLD==null && other.getCHARGE_THRESHOLD()==null) || 
             (this.CHARGE_THRESHOLD!=null &&
              this.CHARGE_THRESHOLD.equals(other.getCHARGE_THRESHOLD()))) &&
            ((this.THRESHOLD==null && other.getTHRESHOLD()==null) || 
             (this.THRESHOLD!=null &&
              this.THRESHOLD.equals(other.getTHRESHOLD()))) &&
            ((this.CYCLICAL_THRESHOLD==null && other.getCYCLICAL_THRESHOLD()==null) || 
             (this.CYCLICAL_THRESHOLD!=null &&
              this.CYCLICAL_THRESHOLD.equals(other.getCYCLICAL_THRESHOLD()))) &&
            ((this.USG_AMT_LIMIT==null && other.getUSG_AMT_LIMIT()==null) || 
             (this.USG_AMT_LIMIT!=null &&
              this.USG_AMT_LIMIT.equals(other.getUSG_AMT_LIMIT()))) &&
            ((this.USG_UNITS_LIMIT==null && other.getUSG_UNITS_LIMIT()==null) || 
             (this.USG_UNITS_LIMIT!=null &&
              this.USG_UNITS_LIMIT.equals(other.getUSG_UNITS_LIMIT()))) &&
            ((this.REGULATORY_ID==null && other.getREGULATORY_ID()==null) || 
             (this.REGULATORY_ID!=null &&
              this.REGULATORY_ID.equals(other.getREGULATORY_ID()))) &&
            ((this.GLOBAL_CONTRACT_STATUS==null && other.getGLOBAL_CONTRACT_STATUS()==null) || 
             (this.GLOBAL_CONTRACT_STATUS!=null &&
              this.GLOBAL_CONTRACT_STATUS.equals(other.getGLOBAL_CONTRACT_STATUS()))) &&
            ((this.ALT_LNAME==null && other.getALT_LNAME()==null) || 
             (this.ALT_LNAME!=null &&
              this.ALT_LNAME.equals(other.getALT_LNAME()))) &&
            ((this.ALT_FNAME==null && other.getALT_FNAME()==null) || 
             (this.ALT_FNAME!=null &&
              this.ALT_FNAME.equals(other.getALT_FNAME()))) &&
            ((this.ALT_COMPANY_NAME==null && other.getALT_COMPANY_NAME()==null) || 
             (this.ALT_COMPANY_NAME!=null &&
              this.ALT_COMPANY_NAME.equals(other.getALT_COMPANY_NAME()))) &&
            ((this.GENDER==null && other.getGENDER()==null) || 
             (this.GENDER!=null &&
              this.GENDER.equals(other.getGENDER()))) &&
            ((this.DEPT==null && other.getDEPT()==null) || 
             (this.DEPT!=null &&
              this.DEPT.equals(other.getDEPT()))) &&
            ((this.TITLE==null && other.getTITLE()==null) || 
             (this.TITLE!=null &&
              this.TITLE.equals(other.getTITLE()))) &&
            ((this.PAYMENT_PROFILE_ID==null && other.getPAYMENT_PROFILE_ID()==null) || 
             (this.PAYMENT_PROFILE_ID!=null &&
              this.PAYMENT_PROFILE_ID.equals(other.getPAYMENT_PROFILE_ID()))) &&
            ((this.IS_ACTIVATED==null && other.getIS_ACTIVATED()==null) || 
             (this.IS_ACTIVATED!=null &&
              this.IS_ACTIVATED.equals(other.getIS_ACTIVATED()))) &&
            ((this.IS_TML_ENABLED==null && other.getIS_TML_ENABLED()==null) || 
             (this.IS_TML_ENABLED!=null &&
              this.IS_TML_ENABLED.equals(other.getIS_TML_ENABLED()))) &&
            ((this.TML_LIMIT==null && other.getTML_LIMIT()==null) || 
             (this.TML_LIMIT!=null &&
              this.TML_LIMIT.equals(other.getTML_LIMIT()))) &&
            ((this.DEALER_ID==null && other.getDEALER_ID()==null) || 
             (this.DEALER_ID!=null &&
              this.DEALER_ID.equals(other.getDEALER_ID()))) &&
            ((this.AGENT_ID==null && other.getAGENT_ID()==null) || 
             (this.AGENT_ID!=null &&
              this.AGENT_ID.equals(other.getAGENT_ID()))) &&
            ((this.UPSELL_TEMPLATE_ID==null && other.getUPSELL_TEMPLATE_ID()==null) || 
             (this.UPSELL_TEMPLATE_ID!=null &&
              this.UPSELL_TEMPLATE_ID.equals(other.getUPSELL_TEMPLATE_ID()))) &&
            ((this.LATEST_QUOTE_IMAGE==null && other.getLATEST_QUOTE_IMAGE()==null) || 
             (this.LATEST_QUOTE_IMAGE!=null &&
              this.LATEST_QUOTE_IMAGE.equals(other.getLATEST_QUOTE_IMAGE()))) &&
            ((this.IS_CSS_REGISTERED==null && other.getIS_CSS_REGISTERED()==null) || 
             (this.IS_CSS_REGISTERED!=null &&
              this.IS_CSS_REGISTERED.equals(other.getIS_CSS_REGISTERED()))) &&
            ((this.PRIMARY_SUBSCR_NO==null && other.getPRIMARY_SUBSCR_NO()==null) || 
             (this.PRIMARY_SUBSCR_NO!=null &&
              this.PRIMARY_SUBSCR_NO.equals(other.getPRIMARY_SUBSCR_NO()))) &&
            ((this.PRIMARY_SUBSCR_NO_RESETS==null && other.getPRIMARY_SUBSCR_NO_RESETS()==null) || 
             (this.PRIMARY_SUBSCR_NO_RESETS!=null &&
              this.PRIMARY_SUBSCR_NO_RESETS.equals(other.getPRIMARY_SUBSCR_NO_RESETS()))) &&
            ((this.ACCT_SEG_ID==null && other.getACCT_SEG_ID()==null) || 
             (this.ACCT_SEG_ID!=null &&
              this.ACCT_SEG_ID.equals(other.getACCT_SEG_ID()))) &&
            ((this.NOTIFICATION_LANGUAGE_ID==null && other.getNOTIFICATION_LANGUAGE_ID()==null) || 
             (this.NOTIFICATION_LANGUAGE_ID!=null &&
              this.NOTIFICATION_LANGUAGE_ID.equals(other.getNOTIFICATION_LANGUAGE_ID()))) &&
            ((this.NOTIFICATION_LEVEL==null && other.getNOTIFICATION_LEVEL()==null) || 
             (this.NOTIFICATION_LEVEL!=null &&
              this.NOTIFICATION_LEVEL.equals(other.getNOTIFICATION_LEVEL()))) &&
            ((this.PAYMENT_METHOD==null && other.getPAYMENT_METHOD()==null) || 
             (this.PAYMENT_METHOD!=null &&
              this.PAYMENT_METHOD.equals(other.getPAYMENT_METHOD()))) &&
            ((this.BNK_ACCOUNT_HOLDER==null && other.getBNK_ACCOUNT_HOLDER()==null) || 
             (this.BNK_ACCOUNT_HOLDER!=null &&
              this.BNK_ACCOUNT_HOLDER.equals(other.getBNK_ACCOUNT_HOLDER()))) &&
            ((this.BNK_ACCOUNT_NO==null && other.getBNK_ACCOUNT_NO()==null) || 
             (this.BNK_ACCOUNT_NO!=null &&
              this.BNK_ACCOUNT_NO.equals(other.getBNK_ACCOUNT_NO()))) &&
            ((this.BANK_ID==null && other.getBANK_ID()==null) || 
             (this.BANK_ID!=null &&
              this.BANK_ID.equals(other.getBANK_ID()))) &&
            ((this.TIN_NO==null && other.getTIN_NO()==null) || 
             (this.TIN_NO!=null &&
              this.TIN_NO.equals(other.getTIN_NO())));
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
        if (getACCOUNT_NO() != null) {
            _hashCode += getACCOUNT_NO().hashCode();
        }
        if (getACCOUNT_CODE() != null) {
            _hashCode += getACCOUNT_CODE().hashCode();
        }
        if (getCONTRACT_NO() != null) {
            _hashCode += getCONTRACT_NO().hashCode();
        }
        if (getCONTRACT_DATE() != null) {
            _hashCode += getCONTRACT_DATE().hashCode();
        }
        if (getPARENT_ACCOUNT_NO() != null) {
            _hashCode += getPARENT_ACCOUNT_NO().hashCode();
        }
        if (getPARENT_ACCOUNT_CODE() != null) {
            _hashCode += getPARENT_ACCOUNT_CODE().hashCode();
        }
        if (getRESELLER_VERSION_ID() != null) {
            _hashCode += getRESELLER_VERSION_ID().hashCode();
        }
        if (getCUST_GEO_AREA_ID() != null) {
            _hashCode += getCUST_GEO_AREA_ID().hashCode();
        }
        if (getTIE_CODE() != null) {
            _hashCode += getTIE_CODE().hashCode();
        }
        if (getBILL_SEQUENCE_NUM() != null) {
            _hashCode += getBILL_SEQUENCE_NUM().hashCode();
        }
        if (getCURRENCY_CODE() != null) {
            _hashCode += getCURRENCY_CODE().hashCode();
        }
        if (getLANGUAGE_CODE() != null) {
            _hashCode += getLANGUAGE_CODE().hashCode();
        }
        if (getACCOUNT_TYPE() != null) {
            _hashCode += getACCOUNT_TYPE().hashCode();
        }
        if (getACCOUNT_CATEGORY() != null) {
            _hashCode += getACCOUNT_CATEGORY().hashCode();
        }
        if (getPREV_CUTOFF_DATE() != null) {
            _hashCode += getPREV_CUTOFF_DATE().hashCode();
        }
        if (getPREV_BILL_DATE() != null) {
            _hashCode += getPREV_BILL_DATE().hashCode();
        }
        if (getNEXT_BILL_DATE() != null) {
            _hashCode += getNEXT_BILL_DATE().hashCode();
        }
        if (getPREV_BILL_REFNO() != null) {
            _hashCode += getPREV_BILL_REFNO().hashCode();
        }
        if (getPREV_BILL_REF_RESETS() != null) {
            _hashCode += getPREV_BILL_REF_RESETS().hashCode();
        }
        if (getPREV_BALANCE_REFNO() != null) {
            _hashCode += getPREV_BALANCE_REFNO().hashCode();
        }
        if (getPREV_BALANCE_REF_RESETS() != null) {
            _hashCode += getPREV_BALANCE_REF_RESETS().hashCode();
        }
        if (getBILL_NAME_PRE() != null) {
            _hashCode += getBILL_NAME_PRE().hashCode();
        }
        if (getBILL_FNAME() != null) {
            _hashCode += getBILL_FNAME().hashCode();
        }
        if (getBILL_MINIT() != null) {
            _hashCode += getBILL_MINIT().hashCode();
        }
        if (getBILL_LNAME() != null) {
            _hashCode += getBILL_LNAME().hashCode();
        }
        if (getBILL_NAME_GENERATION() != null) {
            _hashCode += getBILL_NAME_GENERATION().hashCode();
        }
        if (getBILL_TITLE() != null) {
            _hashCode += getBILL_TITLE().hashCode();
        }
        if (getBILL_COMPANY() != null) {
            _hashCode += getBILL_COMPANY().hashCode();
        }
        if (getCUST_ADDRESS1() != null) {
            _hashCode += getCUST_ADDRESS1().hashCode();
        }
        if (getCUST_ADDRESS2() != null) {
            _hashCode += getCUST_ADDRESS2().hashCode();
        }
        if (getCUST_ADDRESS3() != null) {
            _hashCode += getCUST_ADDRESS3().hashCode();
        }
        if (getCUST_ADDRESS4() != null) {
            _hashCode += getCUST_ADDRESS4().hashCode();
        }
        if (getCUST_CITY() != null) {
            _hashCode += getCUST_CITY().hashCode();
        }
        if (getCUST_STATE() != null) {
            _hashCode += getCUST_STATE().hashCode();
        }
        if (getCUST_COUNTY() != null) {
            _hashCode += getCUST_COUNTY().hashCode();
        }
        if (getCUST_UNIT() != null) {
            _hashCode += getCUST_UNIT().hashCode();
        }
        if (getCUST_ZIP() != null) {
            _hashCode += getCUST_ZIP().hashCode();
        }
        if (getCUST_COUNTRY_CODE() != null) {
            _hashCode += getCUST_COUNTRY_CODE().hashCode();
        }
        if (getCUST_FRANCHISE_TAX_CODE() != null) {
            _hashCode += getCUST_FRANCHISE_TAX_CODE().hashCode();
        }
        if (getCUST_PHONE1() != null) {
            _hashCode += getCUST_PHONE1().hashCode();
        }
        if (getCUST_PHONE2() != null) {
            _hashCode += getCUST_PHONE2().hashCode();
        }
        if (getBILL_ADDRESS1() != null) {
            _hashCode += getBILL_ADDRESS1().hashCode();
        }
        if (getBILL_ADDRESS2() != null) {
            _hashCode += getBILL_ADDRESS2().hashCode();
        }
        if (getBILL_ADDRESS3() != null) {
            _hashCode += getBILL_ADDRESS3().hashCode();
        }
        if (getBILL_ADDRESS4() != null) {
            _hashCode += getBILL_ADDRESS4().hashCode();
        }
        if (getBILL_CITY() != null) {
            _hashCode += getBILL_CITY().hashCode();
        }
        if (getBILL_STATE() != null) {
            _hashCode += getBILL_STATE().hashCode();
        }
        if (getBILL_ZIP() != null) {
            _hashCode += getBILL_ZIP().hashCode();
        }
        if (getBILL_COUNTRY_CODE() != null) {
            _hashCode += getBILL_COUNTRY_CODE().hashCode();
        }
        if (getBILL_FRANCHISE_TAX_CODE() != null) {
            _hashCode += getBILL_FRANCHISE_TAX_CODE().hashCode();
        }
        if (getBILL_COUNTY() != null) {
            _hashCode += getBILL_COUNTY().hashCode();
        }
        if (getBILL_UNIT() != null) {
            _hashCode += getBILL_UNIT().hashCode();
        }
        if (getSSN() != null) {
            _hashCode += getSSN().hashCode();
        }
        if (getCONTACT1_NAME() != null) {
            _hashCode += getCONTACT1_NAME().hashCode();
        }
        if (getCONTACT2_NAME() != null) {
            _hashCode += getCONTACT2_NAME().hashCode();
        }
        if (getCONTACT1_PHONE() != null) {
            _hashCode += getCONTACT1_PHONE().hashCode();
        }
        if (getCONTACT2_PHONE() != null) {
            _hashCode += getCONTACT2_PHONE().hashCode();
        }
        if (getCUST_FAXNO() != null) {
            _hashCode += getCUST_FAXNO().hashCode();
        }
        if (getSTATEMENT_TO_FAXNO() != null) {
            _hashCode += getSTATEMENT_TO_FAXNO().hashCode();
        }
        if (getCREDIT_THRESH() != null) {
            _hashCode += getCREDIT_THRESH().hashCode();
        }
        if (getCRED_STATUS() != null) {
            _hashCode += getCRED_STATUS().hashCode();
        }
        if (getCREDIT_RATING() != null) {
            _hashCode += getCREDIT_RATING().hashCode();
        }
        if (getDISCONNECT_REASON() != null) {
            _hashCode += getDISCONNECT_REASON().hashCode();
        }
        if (getDISC_RCV_OPT() != null) {
            _hashCode += getDISC_RCV_OPT().hashCode();
        }
        if (getTHRESH_REF() != null) {
            _hashCode += getTHRESH_REF().hashCode();
        }
        if (getBILL_PERIOD() != null) {
            _hashCode += getBILL_PERIOD().hashCode();
        }
        if (getBILL_DISP_METH() != null) {
            _hashCode += getBILL_DISP_METH().hashCode();
        }
        if (getBILLING_FREQUENCY() != null) {
            _hashCode += getBILLING_FREQUENCY().hashCode();
        }
        if (getRATE_CLASS_DEFAULT() != null) {
            _hashCode += getRATE_CLASS_DEFAULT().hashCode();
        }
        if (getEXRATE_CLASS() != null) {
            _hashCode += getEXRATE_CLASS().hashCode();
        }
        if (getACCOUNT_STATUS() != null) {
            _hashCode += getACCOUNT_STATUS().hashCode();
        }
        if (getACCOUNT_STATUS_DT() != null) {
            _hashCode += getACCOUNT_STATUS_DT().hashCode();
        }
        if (getDISTR_CHAN() != null) {
            _hashCode += getDISTR_CHAN().hashCode();
        }
        if (getSALES_CODE() != null) {
            _hashCode += getSALES_CODE().hashCode();
        }
        if (getMKT_CODE() != null) {
            _hashCode += getMKT_CODE().hashCode();
        }
        if (getSIC_CODE() != null) {
            _hashCode += getSIC_CODE().hashCode();
        }
        if (getIN_USE() != null) {
            _hashCode += getIN_USE().hashCode();
        }
        if (getNO_BILL() != null) {
            _hashCode += getNO_BILL().hashCode();
        }
        if (getVIP_CODE() != null) {
            _hashCode += getVIP_CODE().hashCode();
        }
        if (getCODEWORD() != null) {
            _hashCode += getCODEWORD().hashCode();
        }
        if (getREMARK() != null) {
            _hashCode += getREMARK().hashCode();
        }
        if (getCUST_EMAIL() != null) {
            _hashCode += getCUST_EMAIL().hashCode();
        }
        if (getSTATEMENT_TO_EMAIL() != null) {
            _hashCode += getSTATEMENT_TO_EMAIL().hashCode();
        }
        if (getCHG_WHO() != null) {
            _hashCode += getCHG_WHO().hashCode();
        }
        if (getCHG_DATE() != null) {
            _hashCode += getCHG_DATE().hashCode();
        }
        if (getDATE_CREATED() != null) {
            _hashCode += getDATE_CREATED().hashCode();
        }
        if (getDATE_ACTIVE() != null) {
            _hashCode += getDATE_ACTIVE().hashCode();
        }
        if (getDATE_INACTIVE() != null) {
            _hashCode += getDATE_INACTIVE().hashCode();
        }
        if (getCHARGE_THRESHOLD() != null) {
            _hashCode += getCHARGE_THRESHOLD().hashCode();
        }
        if (getTHRESHOLD() != null) {
            _hashCode += getTHRESHOLD().hashCode();
        }
        if (getCYCLICAL_THRESHOLD() != null) {
            _hashCode += getCYCLICAL_THRESHOLD().hashCode();
        }
        if (getUSG_AMT_LIMIT() != null) {
            _hashCode += getUSG_AMT_LIMIT().hashCode();
        }
        if (getUSG_UNITS_LIMIT() != null) {
            _hashCode += getUSG_UNITS_LIMIT().hashCode();
        }
        if (getREGULATORY_ID() != null) {
            _hashCode += getREGULATORY_ID().hashCode();
        }
        if (getGLOBAL_CONTRACT_STATUS() != null) {
            _hashCode += getGLOBAL_CONTRACT_STATUS().hashCode();
        }
        if (getALT_LNAME() != null) {
            _hashCode += getALT_LNAME().hashCode();
        }
        if (getALT_FNAME() != null) {
            _hashCode += getALT_FNAME().hashCode();
        }
        if (getALT_COMPANY_NAME() != null) {
            _hashCode += getALT_COMPANY_NAME().hashCode();
        }
        if (getGENDER() != null) {
            _hashCode += getGENDER().hashCode();
        }
        if (getDEPT() != null) {
            _hashCode += getDEPT().hashCode();
        }
        if (getTITLE() != null) {
            _hashCode += getTITLE().hashCode();
        }
        if (getPAYMENT_PROFILE_ID() != null) {
            _hashCode += getPAYMENT_PROFILE_ID().hashCode();
        }
        if (getIS_ACTIVATED() != null) {
            _hashCode += getIS_ACTIVATED().hashCode();
        }
        if (getIS_TML_ENABLED() != null) {
            _hashCode += getIS_TML_ENABLED().hashCode();
        }
        if (getTML_LIMIT() != null) {
            _hashCode += getTML_LIMIT().hashCode();
        }
        if (getDEALER_ID() != null) {
            _hashCode += getDEALER_ID().hashCode();
        }
        if (getAGENT_ID() != null) {
            _hashCode += getAGENT_ID().hashCode();
        }
        if (getUPSELL_TEMPLATE_ID() != null) {
            _hashCode += getUPSELL_TEMPLATE_ID().hashCode();
        }
        if (getLATEST_QUOTE_IMAGE() != null) {
            _hashCode += getLATEST_QUOTE_IMAGE().hashCode();
        }
        if (getIS_CSS_REGISTERED() != null) {
            _hashCode += getIS_CSS_REGISTERED().hashCode();
        }
        if (getPRIMARY_SUBSCR_NO() != null) {
            _hashCode += getPRIMARY_SUBSCR_NO().hashCode();
        }
        if (getPRIMARY_SUBSCR_NO_RESETS() != null) {
            _hashCode += getPRIMARY_SUBSCR_NO_RESETS().hashCode();
        }
        if (getACCT_SEG_ID() != null) {
            _hashCode += getACCT_SEG_ID().hashCode();
        }
        if (getNOTIFICATION_LANGUAGE_ID() != null) {
            _hashCode += getNOTIFICATION_LANGUAGE_ID().hashCode();
        }
        if (getNOTIFICATION_LEVEL() != null) {
            _hashCode += getNOTIFICATION_LEVEL().hashCode();
        }
        if (getPAYMENT_METHOD() != null) {
            _hashCode += getPAYMENT_METHOD().hashCode();
        }
        if (getBNK_ACCOUNT_HOLDER() != null) {
            _hashCode += getBNK_ACCOUNT_HOLDER().hashCode();
        }
        if (getBNK_ACCOUNT_NO() != null) {
            _hashCode += getBNK_ACCOUNT_NO().hashCode();
        }
        if (getBANK_ID() != null) {
            _hashCode += getBANK_ID().hashCode();
        }
        if (getTIN_NO() != null) {
            _hashCode += getTIN_NO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AccountObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.crm.telsoft/", "accountObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("CONTRACT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CONTRACT_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CONTRACT_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CONTRACT_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PARENT_ACCOUNT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PARENT_ACCOUNT_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PARENT_ACCOUNT_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PARENT_ACCOUNT_CODE"));
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
        elemField.setFieldName("CUST_GEO_AREA_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_GEO_AREA_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TIE_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TIE_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_SEQUENCE_NUM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_SEQUENCE_NUM"));
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
        elemField.setFieldName("LANGUAGE_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LANGUAGE_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACCOUNT_TYPE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCOUNT_TYPE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACCOUNT_CATEGORY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCOUNT_CATEGORY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PREV_CUTOFF_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PREV_CUTOFF_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PREV_BILL_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PREV_BILL_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NEXT_BILL_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NEXT_BILL_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PREV_BILL_REFNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PREV_BILL_REFNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PREV_BILL_REF_RESETS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PREV_BILL_REF_RESETS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PREV_BALANCE_REFNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PREV_BALANCE_REFNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PREV_BALANCE_REF_RESETS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PREV_BALANCE_REF_RESETS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_NAME_PRE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_NAME_PRE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_FNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_FNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_MINIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_MINIT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_LNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_LNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_NAME_GENERATION");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_NAME_GENERATION"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_TITLE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_TITLE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_COMPANY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_COMPANY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_ADDRESS1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_ADDRESS1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_ADDRESS2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_ADDRESS2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_ADDRESS3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_ADDRESS3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_ADDRESS4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_ADDRESS4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_CITY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_CITY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_STATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_STATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_COUNTY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_COUNTY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_UNIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_UNIT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_ZIP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_ZIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_COUNTRY_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_COUNTRY_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_FRANCHISE_TAX_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_FRANCHISE_TAX_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_PHONE1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_PHONE1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_PHONE2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_PHONE2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_ADDRESS1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_ADDRESS1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_ADDRESS2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_ADDRESS2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_ADDRESS3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_ADDRESS3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_ADDRESS4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_ADDRESS4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_CITY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_CITY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_STATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_STATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_ZIP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_ZIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_COUNTRY_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_COUNTRY_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_FRANCHISE_TAX_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_FRANCHISE_TAX_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_COUNTY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_COUNTY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_UNIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_UNIT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SSN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CONTACT1_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CONTACT1_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CONTACT2_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CONTACT2_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CONTACT1_PHONE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CONTACT1_PHONE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CONTACT2_PHONE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CONTACT2_PHONE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_FAXNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_FAXNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATEMENT_TO_FAXNO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STATEMENT_TO_FAXNO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CREDIT_THRESH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CREDIT_THRESH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CRED_STATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CRED_STATUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CREDIT_RATING");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CREDIT_RATING"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DISCONNECT_REASON");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DISCONNECT_REASON"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DISC_RCV_OPT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DISC_RCV_OPT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("THRESH_REF");
        elemField.setXmlName(new javax.xml.namespace.QName("", "THRESH_REF"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_PERIOD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_PERIOD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILL_DISP_METH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILL_DISP_METH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BILLING_FREQUENCY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BILLING_FREQUENCY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RATE_CLASS_DEFAULT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RATE_CLASS_DEFAULT"));
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
        elemField.setFieldName("ACCOUNT_STATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCOUNT_STATUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACCOUNT_STATUS_DT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCOUNT_STATUS_DT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DISTR_CHAN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DISTR_CHAN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SALES_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SALES_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MKT_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MKT_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SIC_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SIC_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IN_USE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IN_USE"));
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
        elemField.setFieldName("VIP_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VIP_CODE"));
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
        elemField.setFieldName("REMARK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "REMARK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CUST_EMAIL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CUST_EMAIL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STATEMENT_TO_EMAIL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "STATEMENT_TO_EMAIL"));
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
        elemField.setFieldName("CHG_DATE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CHG_DATE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DATE_CREATED");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DATE_CREATED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DATE_ACTIVE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DATE_ACTIVE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DATE_INACTIVE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DATE_INACTIVE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CHARGE_THRESHOLD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CHARGE_THRESHOLD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("THRESHOLD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "THRESHOLD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("CYCLICAL_THRESHOLD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "CYCLICAL_THRESHOLD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USG_AMT_LIMIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USG_AMT_LIMIT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USG_UNITS_LIMIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "USG_UNITS_LIMIT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("REGULATORY_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "REGULATORY_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GLOBAL_CONTRACT_STATUS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GLOBAL_CONTRACT_STATUS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ALT_LNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ALT_LNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ALT_FNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ALT_FNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ALT_COMPANY_NAME");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ALT_COMPANY_NAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("GENDER");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GENDER"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DEPT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DEPT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TITLE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TITLE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PAYMENT_PROFILE_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PAYMENT_PROFILE_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IS_ACTIVATED");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IS_ACTIVATED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IS_TML_ENABLED");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IS_TML_ENABLED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TML_LIMIT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TML_LIMIT"));
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
        elemField.setFieldName("UPSELL_TEMPLATE_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UPSELL_TEMPLATE_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LATEST_QUOTE_IMAGE");
        elemField.setXmlName(new javax.xml.namespace.QName("", "LATEST_QUOTE_IMAGE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IS_CSS_REGISTERED");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IS_CSS_REGISTERED"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PRIMARY_SUBSCR_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PRIMARY_SUBSCR_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PRIMARY_SUBSCR_NO_RESETS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PRIMARY_SUBSCR_NO_RESETS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACCT_SEG_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACCT_SEG_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOTIFICATION_LANGUAGE_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NOTIFICATION_LANGUAGE_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOTIFICATION_LEVEL");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NOTIFICATION_LEVEL"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PAYMENT_METHOD");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PAYMENT_METHOD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BNK_ACCOUNT_HOLDER");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BNK_ACCOUNT_HOLDER"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BNK_ACCOUNT_NO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BNK_ACCOUNT_NO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("BANK_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BANK_ID"));
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
