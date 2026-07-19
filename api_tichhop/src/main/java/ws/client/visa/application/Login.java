/**
 * Login.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class Login  implements java.io.Serializable {
    private long companyId;

    private String email;

    private String firstName;

    private Object[] functionKeys;

    private long id;

    private String languagePref;

    private String lastName;

    private java.util.Calendar loginTime;

    private boolean on;

    private String password;

    private Object[] rightCodes;

    private java.util.HashMap roleRightCodesMap;

    private String sessionId;

    private String status;

    private Object[] subCompanyIds;

    private User user;

    private String userId;

    public Login() {
    }

    public Login(
           long companyId,
           String email,
           String firstName,
           Object[] functionKeys,
           long id,
           String languagePref,
           String lastName,
           java.util.Calendar loginTime,
           boolean on,
           String password,
           Object[] rightCodes,
           java.util.HashMap roleRightCodesMap,
           String sessionId,
           String status,
           Object[] subCompanyIds,
           User user,
           String userId) {
           this.companyId = companyId;
           this.email = email;
           this.firstName = firstName;
           this.functionKeys = functionKeys;
           this.id = id;
           this.languagePref = languagePref;
           this.lastName = lastName;
           this.loginTime = loginTime;
           this.on = on;
           this.password = password;
           this.rightCodes = rightCodes;
           this.roleRightCodesMap = roleRightCodesMap;
           this.sessionId = sessionId;
           this.status = status;
           this.subCompanyIds = subCompanyIds;
           this.user = user;
           this.userId = userId;
    }


    /**
     * Gets the companyId value for this Login.
     * 
     * @return companyId
     */
    public long getCompanyId() {
        return companyId;
    }


    /**
     * Sets the companyId value for this Login.
     * 
     * @param companyId
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }


    /**
     * Gets the email value for this Login.
     * 
     * @return email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this Login.
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Gets the firstName value for this Login.
     * 
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this Login.
     * 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the functionKeys value for this Login.
     * 
     * @return functionKeys
     */
    public Object[] getFunctionKeys() {
        return functionKeys;
    }


    /**
     * Sets the functionKeys value for this Login.
     * 
     * @param functionKeys
     */
    public void setFunctionKeys(Object[] functionKeys) {
        this.functionKeys = functionKeys;
    }


    /**
     * Gets the id value for this Login.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this Login.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the languagePref value for this Login.
     * 
     * @return languagePref
     */
    public String getLanguagePref() {
        return languagePref;
    }


    /**
     * Sets the languagePref value for this Login.
     * 
     * @param languagePref
     */
    public void setLanguagePref(String languagePref) {
        this.languagePref = languagePref;
    }


    /**
     * Gets the lastName value for this Login.
     * 
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this Login.
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the loginTime value for this Login.
     * 
     * @return loginTime
     */
    public java.util.Calendar getLoginTime() {
        return loginTime;
    }


    /**
     * Sets the loginTime value for this Login.
     * 
     * @param loginTime
     */
    public void setLoginTime(java.util.Calendar loginTime) {
        this.loginTime = loginTime;
    }


    /**
     * Gets the on value for this Login.
     * 
     * @return on
     */
    public boolean isOn() {
        return on;
    }


    /**
     * Sets the on value for this Login.
     * 
     * @param on
     */
    public void setOn(boolean on) {
        this.on = on;
    }


    /**
     * Gets the password value for this Login.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Login.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Gets the rightCodes value for this Login.
     * 
     * @return rightCodes
     */
    public Object[] getRightCodes() {
        return rightCodes;
    }


    /**
     * Sets the rightCodes value for this Login.
     * 
     * @param rightCodes
     */
    public void setRightCodes(Object[] rightCodes) {
        this.rightCodes = rightCodes;
    }


    /**
     * Gets the roleRightCodesMap value for this Login.
     * 
     * @return roleRightCodesMap
     */
    public java.util.HashMap getRoleRightCodesMap() {
        return roleRightCodesMap;
    }


    /**
     * Sets the roleRightCodesMap value for this Login.
     * 
     * @param roleRightCodesMap
     */
    public void setRoleRightCodesMap(java.util.HashMap roleRightCodesMap) {
        this.roleRightCodesMap = roleRightCodesMap;
    }


    /**
     * Gets the sessionId value for this Login.
     * 
     * @return sessionId
     */
    public String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this Login.
     * 
     * @param sessionId
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the status value for this Login.
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Login.
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Gets the subCompanyIds value for this Login.
     * 
     * @return subCompanyIds
     */
    public Object[] getSubCompanyIds() {
        return subCompanyIds;
    }


    /**
     * Sets the subCompanyIds value for this Login.
     * 
     * @param subCompanyIds
     */
    public void setSubCompanyIds(Object[] subCompanyIds) {
        this.subCompanyIds = subCompanyIds;
    }


    /**
     * Gets the user value for this Login.
     * 
     * @return user
     */
    public User getUser() {
        return user;
    }


    /**
     * Sets the user value for this Login.
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Gets the userId value for this Login.
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this Login.
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Login)) return false;
        Login other = (Login) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.companyId == other.getCompanyId() &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.functionKeys==null && other.getFunctionKeys()==null) || 
             (this.functionKeys!=null &&
              java.util.Arrays.equals(this.functionKeys, other.getFunctionKeys()))) &&
            this.id == other.getId() &&
            ((this.languagePref==null && other.getLanguagePref()==null) || 
             (this.languagePref!=null &&
              this.languagePref.equals(other.getLanguagePref()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.loginTime==null && other.getLoginTime()==null) || 
             (this.loginTime!=null &&
              this.loginTime.equals(other.getLoginTime()))) &&
            this.on == other.isOn() &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.rightCodes==null && other.getRightCodes()==null) || 
             (this.rightCodes!=null &&
              java.util.Arrays.equals(this.rightCodes, other.getRightCodes()))) &&
            ((this.roleRightCodesMap==null && other.getRoleRightCodesMap()==null) || 
             (this.roleRightCodesMap!=null &&
              this.roleRightCodesMap.equals(other.getRoleRightCodesMap()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.subCompanyIds==null && other.getSubCompanyIds()==null) || 
             (this.subCompanyIds!=null &&
              java.util.Arrays.equals(this.subCompanyIds, other.getSubCompanyIds()))) &&
            ((this.user==null && other.getUser()==null) || 
             (this.user!=null &&
              this.user.equals(other.getUser()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId())));
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
        _hashCode += new Long(getCompanyId()).hashCode();
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getFunctionKeys() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFunctionKeys());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getFunctionKeys(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Long(getId()).hashCode();
        if (getLanguagePref() != null) {
            _hashCode += getLanguagePref().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getLoginTime() != null) {
            _hashCode += getLoginTime().hashCode();
        }
        _hashCode += (isOn() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getRightCodes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRightCodes());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getRightCodes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRoleRightCodesMap() != null) {
            _hashCode += getRoleRightCodesMap().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSubCompanyIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubCompanyIds());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getSubCompanyIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getUser() != null) {
            _hashCode += getUser().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Login.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:security.model.hyframe.hyperlogy", "login"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "companyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "firstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("functionKeys");
        elemField.setXmlName(new javax.xml.namespace.QName("", "functionKeys"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languagePref");
        elemField.setXmlName(new javax.xml.namespace.QName("", "languagePref"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "loginTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("on");
        elemField.setXmlName(new javax.xml.namespace.QName("", "on"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rightCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rightCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("roleRightCodesMap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "roleRightCodesMap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Map"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subCompanyIds");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subCompanyIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("user");
        elemField.setXmlName(new javax.xml.namespace.QName("", "user"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:security.model.hyframe.hyperlogy", "user"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
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
