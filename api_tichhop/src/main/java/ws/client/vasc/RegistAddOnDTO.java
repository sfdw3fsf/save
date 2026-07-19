/**
 * RegistAddOnDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegistAddOnDTO  implements java.io.Serializable {
    private String username;

    private String zipcode;

    private String service_cd;

    private String pack_cd;

    private String subpack_cd;

    private int unit_price;

    private String reason;

    private String payment_type;

    private String pre_period;

    private short pre_value;

    private String pre_expire_time;

    public RegistAddOnDTO() {
    }

    public RegistAddOnDTO(
           String username,
           String zipcode,
           String service_cd,
           String pack_cd,
           String subpack_cd,
           int unit_price,
           String reason,
           String payment_type,
           String pre_period,
           short pre_value,
           String pre_expire_time) {
           this.username = username;
           this.zipcode = zipcode;
           this.service_cd = service_cd;
           this.pack_cd = pack_cd;
           this.subpack_cd = subpack_cd;
           this.unit_price = unit_price;
           this.reason = reason;
           this.payment_type = payment_type;
           this.pre_period = pre_period;
           this.pre_value = pre_value;
           this.pre_expire_time = pre_expire_time;
    }


    /**
     * Gets the username value for this RegistAddOnDTO.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this RegistAddOnDTO.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the zipcode value for this RegistAddOnDTO.
     * 
     * @return zipcode
     */
    public String getZipcode() {
        return zipcode;
    }


    /**
     * Sets the zipcode value for this RegistAddOnDTO.
     * 
     * @param zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    /**
     * Gets the service_cd value for this RegistAddOnDTO.
     * 
     * @return service_cd
     */
    public String getService_cd() {
        return service_cd;
    }


    /**
     * Sets the service_cd value for this RegistAddOnDTO.
     * 
     * @param service_cd
     */
    public void setService_cd(String service_cd) {
        this.service_cd = service_cd;
    }


    /**
     * Gets the pack_cd value for this RegistAddOnDTO.
     * 
     * @return pack_cd
     */
    public String getPack_cd() {
        return pack_cd;
    }


    /**
     * Sets the pack_cd value for this RegistAddOnDTO.
     * 
     * @param pack_cd
     */
    public void setPack_cd(String pack_cd) {
        this.pack_cd = pack_cd;
    }


    /**
     * Gets the subpack_cd value for this RegistAddOnDTO.
     * 
     * @return subpack_cd
     */
    public String getSubpack_cd() {
        return subpack_cd;
    }


    /**
     * Sets the subpack_cd value for this RegistAddOnDTO.
     * 
     * @param subpack_cd
     */
    public void setSubpack_cd(String subpack_cd) {
        this.subpack_cd = subpack_cd;
    }


    /**
     * Gets the unit_price value for this RegistAddOnDTO.
     * 
     * @return unit_price
     */
    public int getUnit_price() {
        return unit_price;
    }


    /**
     * Sets the unit_price value for this RegistAddOnDTO.
     * 
     * @param unit_price
     */
    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }


    /**
     * Gets the reason value for this RegistAddOnDTO.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this RegistAddOnDTO.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


    /**
     * Gets the payment_type value for this RegistAddOnDTO.
     * 
     * @return payment_type
     */
    public String getPayment_type() {
        return payment_type;
    }


    /**
     * Sets the payment_type value for this RegistAddOnDTO.
     * 
     * @param payment_type
     */
    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }


    /**
     * Gets the pre_period value for this RegistAddOnDTO.
     * 
     * @return pre_period
     */
    public String getPre_period() {
        return pre_period;
    }


    /**
     * Sets the pre_period value for this RegistAddOnDTO.
     * 
     * @param pre_period
     */
    public void setPre_period(String pre_period) {
        this.pre_period = pre_period;
    }


    /**
     * Gets the pre_value value for this RegistAddOnDTO.
     * 
     * @return pre_value
     */
    public short getPre_value() {
        return pre_value;
    }


    /**
     * Sets the pre_value value for this RegistAddOnDTO.
     * 
     * @param pre_value
     */
    public void setPre_value(short pre_value) {
        this.pre_value = pre_value;
    }


    /**
     * Gets the pre_expire_time value for this RegistAddOnDTO.
     * 
     * @return pre_expire_time
     */
    public String getPre_expire_time() {
        return pre_expire_time;
    }


    /**
     * Sets the pre_expire_time value for this RegistAddOnDTO.
     * 
     * @param pre_expire_time
     */
    public void setPre_expire_time(String pre_expire_time) {
        this.pre_expire_time = pre_expire_time;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegistAddOnDTO)) return false;
        RegistAddOnDTO other = (RegistAddOnDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.zipcode==null && other.getZipcode()==null) || 
             (this.zipcode!=null &&
              this.zipcode.equals(other.getZipcode()))) &&
            ((this.service_cd==null && other.getService_cd()==null) || 
             (this.service_cd!=null &&
              this.service_cd.equals(other.getService_cd()))) &&
            ((this.pack_cd==null && other.getPack_cd()==null) || 
             (this.pack_cd!=null &&
              this.pack_cd.equals(other.getPack_cd()))) &&
            ((this.subpack_cd==null && other.getSubpack_cd()==null) || 
             (this.subpack_cd!=null &&
              this.subpack_cd.equals(other.getSubpack_cd()))) &&
            this.unit_price == other.getUnit_price() &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.payment_type==null && other.getPayment_type()==null) || 
             (this.payment_type!=null &&
              this.payment_type.equals(other.getPayment_type()))) &&
            ((this.pre_period==null && other.getPre_period()==null) || 
             (this.pre_period!=null &&
              this.pre_period.equals(other.getPre_period()))) &&
            this.pre_value == other.getPre_value() &&
            ((this.pre_expire_time==null && other.getPre_expire_time()==null) || 
             (this.pre_expire_time!=null &&
              this.pre_expire_time.equals(other.getPre_expire_time())));
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
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getZipcode() != null) {
            _hashCode += getZipcode().hashCode();
        }
        if (getService_cd() != null) {
            _hashCode += getService_cd().hashCode();
        }
        if (getPack_cd() != null) {
            _hashCode += getPack_cd().hashCode();
        }
        if (getSubpack_cd() != null) {
            _hashCode += getSubpack_cd().hashCode();
        }
        _hashCode += getUnit_price();
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getPayment_type() != null) {
            _hashCode += getPayment_type().hashCode();
        }
        if (getPre_period() != null) {
            _hashCode += getPre_period().hashCode();
        }
        _hashCode += getPre_value();
        if (getPre_expire_time() != null) {
            _hashCode += getPre_expire_time().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistAddOnDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "RegistAddOnDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zipcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "zipcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("service_cd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "service_cd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pack_cd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "pack_cd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subpack_cd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "subpack_cd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unit_price");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "unit_price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payment_type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "payment_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pre_period");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "pre_period"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pre_value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "pre_value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pre_expire_time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "pre_expire_time"));
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
