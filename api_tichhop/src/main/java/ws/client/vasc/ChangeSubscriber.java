/**
 * ChangeSubscriber.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeSubscriber  implements java.io.Serializable {
    private String name;

    private String birthday;

    private String sex;

    private String firstAdd;

    private String secondAdd;

    private String IDNo;

    private String telNo;

    private String cellNo;

    private String areaCode;

    private String email;

    private String YM;

    private String website;

    private String faxNo;

    private String IPTVAccount;

    public ChangeSubscriber() {
    }

    public ChangeSubscriber(
           String name,
           String birthday,
           String sex,
           String firstAdd,
           String secondAdd,
           String IDNo,
           String telNo,
           String cellNo,
           String areaCode,
           String email,
           String YM,
           String website,
           String faxNo,
           String IPTVAccount) {
           this.name = name;
           this.birthday = birthday;
           this.sex = sex;
           this.firstAdd = firstAdd;
           this.secondAdd = secondAdd;
           this.IDNo = IDNo;
           this.telNo = telNo;
           this.cellNo = cellNo;
           this.areaCode = areaCode;
           this.email = email;
           this.YM = YM;
           this.website = website;
           this.faxNo = faxNo;
           this.IPTVAccount = IPTVAccount;
    }


    /**
     * Gets the name value for this ChangeSubscriber.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name value for this ChangeSubscriber.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the birthday value for this ChangeSubscriber.
     * 
     * @return birthday
     */
    public String getBirthday() {
        return birthday;
    }


    /**
     * Sets the birthday value for this ChangeSubscriber.
     * 
     * @param birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    /**
     * Gets the sex value for this ChangeSubscriber.
     * 
     * @return sex
     */
    public String getSex() {
        return sex;
    }


    /**
     * Sets the sex value for this ChangeSubscriber.
     * 
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;
    }


    /**
     * Gets the firstAdd value for this ChangeSubscriber.
     * 
     * @return firstAdd
     */
    public String getFirstAdd() {
        return firstAdd;
    }


    /**
     * Sets the firstAdd value for this ChangeSubscriber.
     * 
     * @param firstAdd
     */
    public void setFirstAdd(String firstAdd) {
        this.firstAdd = firstAdd;
    }


    /**
     * Gets the secondAdd value for this ChangeSubscriber.
     * 
     * @return secondAdd
     */
    public String getSecondAdd() {
        return secondAdd;
    }


    /**
     * Sets the secondAdd value for this ChangeSubscriber.
     * 
     * @param secondAdd
     */
    public void setSecondAdd(String secondAdd) {
        this.secondAdd = secondAdd;
    }


    /**
     * Gets the IDNo value for this ChangeSubscriber.
     * 
     * @return IDNo
     */
    public String getIDNo() {
        return IDNo;
    }


    /**
     * Sets the IDNo value for this ChangeSubscriber.
     * 
     * @param IDNo
     */
    public void setIDNo(String IDNo) {
        this.IDNo = IDNo;
    }


    /**
     * Gets the telNo value for this ChangeSubscriber.
     * 
     * @return telNo
     */
    public String getTelNo() {
        return telNo;
    }


    /**
     * Sets the telNo value for this ChangeSubscriber.
     * 
     * @param telNo
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }


    /**
     * Gets the cellNo value for this ChangeSubscriber.
     * 
     * @return cellNo
     */
    public String getCellNo() {
        return cellNo;
    }


    /**
     * Sets the cellNo value for this ChangeSubscriber.
     * 
     * @param cellNo
     */
    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }


    /**
     * Gets the areaCode value for this ChangeSubscriber.
     * 
     * @return areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this ChangeSubscriber.
     * 
     * @param areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the email value for this ChangeSubscriber.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this ChangeSubscriber.
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the YM value for this ChangeSubscriber.
     * 
     * @return YM
     */
    public String getYM() {
        return YM;
    }


    /**
     * Sets the YM value for this ChangeSubscriber.
     * 
     * @param YM
     */
    public void setYM(String YM) {
        this.YM = YM;
    }


    /**
     * Gets the website value for this ChangeSubscriber.
     * 
     * @return website
     */
    public String getWebsite() {
        return website;
    }


    /**
     * Sets the website value for this ChangeSubscriber.
     * 
     * @param website
     */
    public void setWebsite(String website) {
        this.website = website;
    }


    /**
     * Gets the faxNo value for this ChangeSubscriber.
     * 
     * @return faxNo
     */
    public String getFaxNo() {
        return faxNo;
    }


    /**
     * Sets the faxNo value for this ChangeSubscriber.
     * 
     * @param faxNo
     */
    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }


    /**
     * Gets the IPTVAccount value for this ChangeSubscriber.
     * 
     * @return IPTVAccount
     */
    public String getIPTVAccount() {
        return IPTVAccount;
    }


    /**
     * Sets the IPTVAccount value for this ChangeSubscriber.
     * 
     * @param IPTVAccount
     */
    public void setIPTVAccount(String IPTVAccount) {
        this.IPTVAccount = IPTVAccount;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeSubscriber)) return false;
        ChangeSubscriber other = (ChangeSubscriber) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.birthday==null && other.getBirthday()==null) || 
             (this.birthday!=null &&
              this.birthday.equals(other.getBirthday()))) &&
            ((this.sex==null && other.getSex()==null) || 
             (this.sex!=null &&
              this.sex.equals(other.getSex()))) &&
            ((this.firstAdd==null && other.getFirstAdd()==null) || 
             (this.firstAdd!=null &&
              this.firstAdd.equals(other.getFirstAdd()))) &&
            ((this.secondAdd==null && other.getSecondAdd()==null) || 
             (this.secondAdd!=null &&
              this.secondAdd.equals(other.getSecondAdd()))) &&
            ((this.IDNo==null && other.getIDNo()==null) || 
             (this.IDNo!=null &&
              this.IDNo.equals(other.getIDNo()))) &&
            ((this.telNo==null && other.getTelNo()==null) || 
             (this.telNo!=null &&
              this.telNo.equals(other.getTelNo()))) &&
            ((this.cellNo==null && other.getCellNo()==null) || 
             (this.cellNo!=null &&
              this.cellNo.equals(other.getCellNo()))) &&
            ((this.areaCode==null && other.getAreaCode()==null) || 
             (this.areaCode!=null &&
              this.areaCode.equals(other.getAreaCode()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.YM==null && other.getYM()==null) || 
             (this.YM!=null &&
              this.YM.equals(other.getYM()))) &&
            ((this.website==null && other.getWebsite()==null) || 
             (this.website!=null &&
              this.website.equals(other.getWebsite()))) &&
            ((this.faxNo==null && other.getFaxNo()==null) || 
             (this.faxNo!=null &&
              this.faxNo.equals(other.getFaxNo()))) &&
            ((this.IPTVAccount==null && other.getIPTVAccount()==null) || 
             (this.IPTVAccount!=null &&
              this.IPTVAccount.equals(other.getIPTVAccount())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getBirthday() != null) {
            _hashCode += getBirthday().hashCode();
        }
        if (getSex() != null) {
            _hashCode += getSex().hashCode();
        }
        if (getFirstAdd() != null) {
            _hashCode += getFirstAdd().hashCode();
        }
        if (getSecondAdd() != null) {
            _hashCode += getSecondAdd().hashCode();
        }
        if (getIDNo() != null) {
            _hashCode += getIDNo().hashCode();
        }
        if (getTelNo() != null) {
            _hashCode += getTelNo().hashCode();
        }
        if (getCellNo() != null) {
            _hashCode += getCellNo().hashCode();
        }
        if (getAreaCode() != null) {
            _hashCode += getAreaCode().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getYM() != null) {
            _hashCode += getYM().hashCode();
        }
        if (getWebsite() != null) {
            _hashCode += getWebsite().hashCode();
        }
        if (getFaxNo() != null) {
            _hashCode += getFaxNo().hashCode();
        }
        if (getIPTVAccount() != null) {
            _hashCode += getIPTVAccount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeSubscriber.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeSubscriber"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthday");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Birthday"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Sex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstAdd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "FirstAdd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("secondAdd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SecondAdd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IDNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IDNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TelNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cellNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "CellNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "AreaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("YM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "YM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("website");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Website"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faxNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "FaxNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVAccount"));
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
