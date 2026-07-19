/**
 * CompanyModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.common;

public class CompanyModel  extends AbstractActorModel  implements java.io.Serializable {
    private String actorName;

    private String address;

    private String agentOf;

    private String aliasName;

    private String areaCode;

    private String city;

    private String companyDesc;

    private long companyId;

    private String country;

    private String email;

    private String fullName;

    private String homepage;

    private long id;

    private long parentCompanyId;

    private String parentCompanyName;

    private String phoneNo;

    private String postOffice;

    private String shortName;

    private String state;

    private String zipCode;

    public CompanyModel() {
    }

    public CompanyModel(
           long byActorId,
           String type,
           String actorName,
           String address,
           String agentOf,
           String aliasName,
           String areaCode,
           String city,
           String companyDesc,
           long companyId,
           String country,
           String email,
           String fullName,
           String homepage,
           long id,
           long parentCompanyId,
           String parentCompanyName,
           String phoneNo,
           String postOffice,
           String shortName,
           String state,
           String zipCode) {
        super(
            byActorId,
            type);
        this.actorName = actorName;
        this.address = address;
        this.agentOf = agentOf;
        this.aliasName = aliasName;
        this.areaCode = areaCode;
        this.city = city;
        this.companyDesc = companyDesc;
        this.companyId = companyId;
        this.country = country;
        this.email = email;
        this.fullName = fullName;
        this.homepage = homepage;
        this.id = id;
        this.parentCompanyId = parentCompanyId;
        this.parentCompanyName = parentCompanyName;
        this.phoneNo = phoneNo;
        this.postOffice = postOffice;
        this.shortName = shortName;
        this.state = state;
        this.zipCode = zipCode;
    }


    /**
     * Gets the actorName value for this CompanyModel.
     * 
     * @return actorName
     */
    public String getActorName() {
        return actorName;
    }


    /**
     * Sets the actorName value for this CompanyModel.
     * 
     * @param actorName
     */
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }


    /**
     * Gets the address value for this CompanyModel.
     * 
     * @return address
     */
    public String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this CompanyModel.
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * Gets the agentOf value for this CompanyModel.
     * 
     * @return agentOf
     */
    public String getAgentOf() {
        return agentOf;
    }


    /**
     * Sets the agentOf value for this CompanyModel.
     * 
     * @param agentOf
     */
    public void setAgentOf(String agentOf) {
        this.agentOf = agentOf;
    }


    /**
     * Gets the aliasName value for this CompanyModel.
     * 
     * @return aliasName
     */
    public String getAliasName() {
        return aliasName;
    }


    /**
     * Sets the aliasName value for this CompanyModel.
     * 
     * @param aliasName
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }


    /**
     * Gets the areaCode value for this CompanyModel.
     * 
     * @return areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this CompanyModel.
     * 
     * @param areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the city value for this CompanyModel.
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }


    /**
     * Sets the city value for this CompanyModel.
     * 
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Gets the companyDesc value for this CompanyModel.
     * 
     * @return companyDesc
     */
    public String getCompanyDesc() {
        return companyDesc;
    }


    /**
     * Sets the companyDesc value for this CompanyModel.
     * 
     * @param companyDesc
     */
    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }


    /**
     * Gets the companyId value for this CompanyModel.
     * 
     * @return companyId
     */
    public long getCompanyId() {
        return companyId;
    }


    /**
     * Sets the companyId value for this CompanyModel.
     * 
     * @param companyId
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }


    /**
     * Gets the country value for this CompanyModel.
     * 
     * @return country
     */
    public String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this CompanyModel.
     * 
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }


    /**
     * Gets the email value for this CompanyModel.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this CompanyModel.
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the fullName value for this CompanyModel.
     * 
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }


    /**
     * Sets the fullName value for this CompanyModel.
     * 
     * @param fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    /**
     * Gets the homepage value for this CompanyModel.
     * 
     * @return homepage
     */
    public String getHomepage() {
        return homepage;
    }


    /**
     * Sets the homepage value for this CompanyModel.
     * 
     * @param homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }


    /**
     * Gets the id value for this CompanyModel.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this CompanyModel.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the parentCompanyId value for this CompanyModel.
     * 
     * @return parentCompanyId
     */
    public long getParentCompanyId() {
        return parentCompanyId;
    }


    /**
     * Sets the parentCompanyId value for this CompanyModel.
     * 
     * @param parentCompanyId
     */
    public void setParentCompanyId(long parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }


    /**
     * Gets the parentCompanyName value for this CompanyModel.
     * 
     * @return parentCompanyName
     */
    public String getParentCompanyName() {
        return parentCompanyName;
    }


    /**
     * Sets the parentCompanyName value for this CompanyModel.
     * 
     * @param parentCompanyName
     */
    public void setParentCompanyName(String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }


    /**
     * Gets the phoneNo value for this CompanyModel.
     * 
     * @return phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }


    /**
     * Sets the phoneNo value for this CompanyModel.
     * 
     * @param phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    /**
     * Gets the postOffice value for this CompanyModel.
     * 
     * @return postOffice
     */
    public String getPostOffice() {
        return postOffice;
    }


    /**
     * Sets the postOffice value for this CompanyModel.
     * 
     * @param postOffice
     */
    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }


    /**
     * Gets the shortName value for this CompanyModel.
     * 
     * @return shortName
     */
    public String getShortName() {
        return shortName;
    }


    /**
     * Sets the shortName value for this CompanyModel.
     * 
     * @param shortName
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }


    /**
     * Gets the state value for this CompanyModel.
     * 
     * @return state
     */
    public String getState() {
        return state;
    }


    /**
     * Sets the state value for this CompanyModel.
     * 
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }


    /**
     * Gets the zipCode value for this CompanyModel.
     * 
     * @return zipCode
     */
    public String getZipCode() {
        return zipCode;
    }


    /**
     * Sets the zipCode value for this CompanyModel.
     * 
     * @param zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CompanyModel)) return false;
        CompanyModel other = (CompanyModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.actorName==null && other.getActorName()==null) || 
             (this.actorName!=null &&
              this.actorName.equals(other.getActorName()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.agentOf==null && other.getAgentOf()==null) || 
             (this.agentOf!=null &&
              this.agentOf.equals(other.getAgentOf()))) &&
            ((this.aliasName==null && other.getAliasName()==null) || 
             (this.aliasName!=null &&
              this.aliasName.equals(other.getAliasName()))) &&
            ((this.areaCode==null && other.getAreaCode()==null) || 
             (this.areaCode!=null &&
              this.areaCode.equals(other.getAreaCode()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.companyDesc==null && other.getCompanyDesc()==null) || 
             (this.companyDesc!=null &&
              this.companyDesc.equals(other.getCompanyDesc()))) &&
            this.companyId == other.getCompanyId() &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.fullName==null && other.getFullName()==null) || 
             (this.fullName!=null &&
              this.fullName.equals(other.getFullName()))) &&
            ((this.homepage==null && other.getHomepage()==null) || 
             (this.homepage!=null &&
              this.homepage.equals(other.getHomepage()))) &&
            this.id == other.getId() &&
            this.parentCompanyId == other.getParentCompanyId() &&
            ((this.parentCompanyName==null && other.getParentCompanyName()==null) || 
             (this.parentCompanyName!=null &&
              this.parentCompanyName.equals(other.getParentCompanyName()))) &&
            ((this.phoneNo==null && other.getPhoneNo()==null) || 
             (this.phoneNo!=null &&
              this.phoneNo.equals(other.getPhoneNo()))) &&
            ((this.postOffice==null && other.getPostOffice()==null) || 
             (this.postOffice!=null &&
              this.postOffice.equals(other.getPostOffice()))) &&
            ((this.shortName==null && other.getShortName()==null) || 
             (this.shortName!=null &&
              this.shortName.equals(other.getShortName()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.zipCode==null && other.getZipCode()==null) || 
             (this.zipCode!=null &&
              this.zipCode.equals(other.getZipCode())));
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
        if (getActorName() != null) {
            _hashCode += getActorName().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getAgentOf() != null) {
            _hashCode += getAgentOf().hashCode();
        }
        if (getAliasName() != null) {
            _hashCode += getAliasName().hashCode();
        }
        if (getAreaCode() != null) {
            _hashCode += getAreaCode().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getCompanyDesc() != null) {
            _hashCode += getCompanyDesc().hashCode();
        }
        _hashCode += new Long(getCompanyId()).hashCode();
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getFullName() != null) {
            _hashCode += getFullName().hashCode();
        }
        if (getHomepage() != null) {
            _hashCode += getHomepage().hashCode();
        }
        _hashCode += new Long(getId()).hashCode();
        _hashCode += new Long(getParentCompanyId()).hashCode();
        if (getParentCompanyName() != null) {
            _hashCode += getParentCompanyName().hashCode();
        }
        if (getPhoneNo() != null) {
            _hashCode += getPhoneNo().hashCode();
        }
        if (getPostOffice() != null) {
            _hashCode += getPostOffice().hashCode();
        }
        if (getShortName() != null) {
            _hashCode += getShortName().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getZipCode() != null) {
            _hashCode += getZipCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompanyModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://security.model.hyframe.hyperlogy", "CompanyModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actorName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actorName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentOf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentOf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aliasName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aliasName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("", "city"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "companyDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "companyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fullName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homepage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "homepage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentCompanyId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentCompanyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentCompanyName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentCompanyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phoneNo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phoneNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "postOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "shortName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zipCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
