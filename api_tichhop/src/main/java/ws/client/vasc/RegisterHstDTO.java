/**
 * RegisterHstDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterHstDTO  implements java.io.Serializable {
    private String product_code;

    private String username;

    private String password;

    private String mobile_hst;

    private String product_code_hst;

    private String product_type_hst;

    private String charge_type;

    private String create_time;

    private String expire_time;

    private String json_param;

    public RegisterHstDTO() {
    }

    public RegisterHstDTO(
           String product_code,
           String username,
           String password,
           String mobile_hst,
           String product_code_hst,
           String product_type_hst,
           String charge_type,
           String create_time,
           String expire_time,
           String json_param) {
           this.product_code = product_code;
           this.username = username;
           this.password = password;
           this.mobile_hst = mobile_hst;
           this.product_code_hst = product_code_hst;
           this.product_type_hst = product_type_hst;
           this.charge_type = charge_type;
           this.create_time = create_time;
           this.expire_time = expire_time;
           this.json_param = json_param;
    }


    /**
     * Gets the product_code value for this RegisterHstDTO.
     * 
     * @return product_code
     */
    public String getProduct_code() {
        return product_code;
    }


    /**
     * Sets the product_code value for this RegisterHstDTO.
     * 
     * @param product_code
     */
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }


    /**
     * Gets the username value for this RegisterHstDTO.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this RegisterHstDTO.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the password value for this RegisterHstDTO.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this RegisterHstDTO.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * Gets the mobile_hst value for this RegisterHstDTO.
     * 
     * @return mobile_hst
     */
    public String getMobile_hst() {
        return mobile_hst;
    }


    /**
     * Sets the mobile_hst value for this RegisterHstDTO.
     * 
     * @param mobile_hst
     */
    public void setMobile_hst(String mobile_hst) {
        this.mobile_hst = mobile_hst;
    }


    /**
     * Gets the product_code_hst value for this RegisterHstDTO.
     * 
     * @return product_code_hst
     */
    public String getProduct_code_hst() {
        return product_code_hst;
    }


    /**
     * Sets the product_code_hst value for this RegisterHstDTO.
     * 
     * @param product_code_hst
     */
    public void setProduct_code_hst(String product_code_hst) {
        this.product_code_hst = product_code_hst;
    }


    /**
     * Gets the product_type_hst value for this RegisterHstDTO.
     * 
     * @return product_type_hst
     */
    public String getProduct_type_hst() {
        return product_type_hst;
    }


    /**
     * Sets the product_type_hst value for this RegisterHstDTO.
     * 
     * @param product_type_hst
     */
    public void setProduct_type_hst(String product_type_hst) {
        this.product_type_hst = product_type_hst;
    }


    /**
     * Gets the charge_type value for this RegisterHstDTO.
     * 
     * @return charge_type
     */
    public String getCharge_type() {
        return charge_type;
    }


    /**
     * Sets the charge_type value for this RegisterHstDTO.
     * 
     * @param charge_type
     */
    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }


    /**
     * Gets the create_time value for this RegisterHstDTO.
     * 
     * @return create_time
     */
    public String getCreate_time() {
        return create_time;
    }


    /**
     * Sets the create_time value for this RegisterHstDTO.
     * 
     * @param create_time
     */
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


    /**
     * Gets the expire_time value for this RegisterHstDTO.
     * 
     * @return expire_time
     */
    public String getExpire_time() {
        return expire_time;
    }


    /**
     * Sets the expire_time value for this RegisterHstDTO.
     * 
     * @param expire_time
     */
    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }


    /**
     * Gets the json_param value for this RegisterHstDTO.
     * 
     * @return json_param
     */
    public String getJson_param() {
        return json_param;
    }


    /**
     * Sets the json_param value for this RegisterHstDTO.
     * 
     * @param json_param
     */
    public void setJson_param(String json_param) {
        this.json_param = json_param;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterHstDTO)) return false;
        RegisterHstDTO other = (RegisterHstDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.product_code==null && other.getProduct_code()==null) || 
             (this.product_code!=null &&
              this.product_code.equals(other.getProduct_code()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.mobile_hst==null && other.getMobile_hst()==null) || 
             (this.mobile_hst!=null &&
              this.mobile_hst.equals(other.getMobile_hst()))) &&
            ((this.product_code_hst==null && other.getProduct_code_hst()==null) || 
             (this.product_code_hst!=null &&
              this.product_code_hst.equals(other.getProduct_code_hst()))) &&
            ((this.product_type_hst==null && other.getProduct_type_hst()==null) || 
             (this.product_type_hst!=null &&
              this.product_type_hst.equals(other.getProduct_type_hst()))) &&
            ((this.charge_type==null && other.getCharge_type()==null) || 
             (this.charge_type!=null &&
              this.charge_type.equals(other.getCharge_type()))) &&
            ((this.create_time==null && other.getCreate_time()==null) || 
             (this.create_time!=null &&
              this.create_time.equals(other.getCreate_time()))) &&
            ((this.expire_time==null && other.getExpire_time()==null) || 
             (this.expire_time!=null &&
              this.expire_time.equals(other.getExpire_time()))) &&
            ((this.json_param==null && other.getJson_param()==null) || 
             (this.json_param!=null &&
              this.json_param.equals(other.getJson_param())));
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
        if (getProduct_code() != null) {
            _hashCode += getProduct_code().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getMobile_hst() != null) {
            _hashCode += getMobile_hst().hashCode();
        }
        if (getProduct_code_hst() != null) {
            _hashCode += getProduct_code_hst().hashCode();
        }
        if (getProduct_type_hst() != null) {
            _hashCode += getProduct_type_hst().hashCode();
        }
        if (getCharge_type() != null) {
            _hashCode += getCharge_type().hashCode();
        }
        if (getCreate_time() != null) {
            _hashCode += getCreate_time().hashCode();
        }
        if (getExpire_time() != null) {
            _hashCode += getExpire_time().hashCode();
        }
        if (getJson_param() != null) {
            _hashCode += getJson_param().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterHstDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "RegisterHstDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "product_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobile_hst");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "mobile_hst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product_code_hst");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "product_code_hst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("product_type_hst");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "product_type_hst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("charge_type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "charge_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("create_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "create_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expire_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "expire_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("json_param");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "json_param"));
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
