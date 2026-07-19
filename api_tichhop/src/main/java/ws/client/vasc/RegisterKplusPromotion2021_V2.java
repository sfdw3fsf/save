/**
 * RegisterKplusPromotion2021_V2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegisterKplusPromotion2021_V2  implements java.io.Serializable {
    private String username;

    private String mobile;

    private String area_code;

    private String pack_cd;

    private String addon_cd;

    private String pack_name;

    private boolean renew;

    public RegisterKplusPromotion2021_V2() {
    }

    public RegisterKplusPromotion2021_V2(
           String username,
           String mobile,
           String area_code,
           String pack_cd,
           String addon_cd,
           String pack_name,
           boolean renew) {
           this.username = username;
           this.mobile = mobile;
           this.area_code = area_code;
           this.pack_cd = pack_cd;
           this.addon_cd = addon_cd;
           this.pack_name = pack_name;
           this.renew = renew;
    }


    /**
     * Gets the username value for this RegisterKplusPromotion2021_V2.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this RegisterKplusPromotion2021_V2.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the mobile value for this RegisterKplusPromotion2021_V2.
     * 
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this RegisterKplusPromotion2021_V2.
     * 
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the area_code value for this RegisterKplusPromotion2021_V2.
     * 
     * @return area_code
     */
    public String getArea_code() {
        return area_code;
    }


    /**
     * Sets the area_code value for this RegisterKplusPromotion2021_V2.
     * 
     * @param area_code
     */
    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }


    /**
     * Gets the pack_cd value for this RegisterKplusPromotion2021_V2.
     * 
     * @return pack_cd
     */
    public String getPack_cd() {
        return pack_cd;
    }


    /**
     * Sets the pack_cd value for this RegisterKplusPromotion2021_V2.
     * 
     * @param pack_cd
     */
    public void setPack_cd(String pack_cd) {
        this.pack_cd = pack_cd;
    }


    /**
     * Gets the addon_cd value for this RegisterKplusPromotion2021_V2.
     * 
     * @return addon_cd
     */
    public String getAddon_cd() {
        return addon_cd;
    }


    /**
     * Sets the addon_cd value for this RegisterKplusPromotion2021_V2.
     * 
     * @param addon_cd
     */
    public void setAddon_cd(String addon_cd) {
        this.addon_cd = addon_cd;
    }


    /**
     * Gets the pack_name value for this RegisterKplusPromotion2021_V2.
     * 
     * @return pack_name
     */
    public String getPack_name() {
        return pack_name;
    }


    /**
     * Sets the pack_name value for this RegisterKplusPromotion2021_V2.
     * 
     * @param pack_name
     */
    public void setPack_name(String pack_name) {
        this.pack_name = pack_name;
    }


    /**
     * Gets the renew value for this RegisterKplusPromotion2021_V2.
     * 
     * @return renew
     */
    public boolean isRenew() {
        return renew;
    }


    /**
     * Sets the renew value for this RegisterKplusPromotion2021_V2.
     * 
     * @param renew
     */
    public void setRenew(boolean renew) {
        this.renew = renew;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegisterKplusPromotion2021_V2)) return false;
        RegisterKplusPromotion2021_V2 other = (RegisterKplusPromotion2021_V2) obj;
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
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            ((this.area_code==null && other.getArea_code()==null) || 
             (this.area_code!=null &&
              this.area_code.equals(other.getArea_code()))) &&
            ((this.pack_cd==null && other.getPack_cd()==null) || 
             (this.pack_cd!=null &&
              this.pack_cd.equals(other.getPack_cd()))) &&
            ((this.addon_cd==null && other.getAddon_cd()==null) || 
             (this.addon_cd!=null &&
              this.addon_cd.equals(other.getAddon_cd()))) &&
            ((this.pack_name==null && other.getPack_name()==null) || 
             (this.pack_name!=null &&
              this.pack_name.equals(other.getPack_name()))) &&
            this.renew == other.isRenew();
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
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        if (getArea_code() != null) {
            _hashCode += getArea_code().hashCode();
        }
        if (getPack_cd() != null) {
            _hashCode += getPack_cd().hashCode();
        }
        if (getAddon_cd() != null) {
            _hashCode += getAddon_cd().hashCode();
        }
        if (getPack_name() != null) {
            _hashCode += getPack_name().hashCode();
        }
        _hashCode += (isRenew() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegisterKplusPromotion2021_V2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegisterKplusPromotion2021_V2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "mobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "area_code"));
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
        elemField.setFieldName("addon_cd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "addon_cd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pack_name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "pack_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("renew");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "renew"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
