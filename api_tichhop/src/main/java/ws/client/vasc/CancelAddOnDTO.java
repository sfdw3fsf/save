/**
 * CancelAddOnDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CancelAddOnDTO  implements java.io.Serializable {
    private String username;

    private String zipcode;

    private String service_cd;

    private String pack_cd;

    private String reason;

    public CancelAddOnDTO() {
    }

    public CancelAddOnDTO(
           String username,
           String zipcode,
           String service_cd,
           String pack_cd,
           String reason) {
           this.username = username;
           this.zipcode = zipcode;
           this.service_cd = service_cd;
           this.pack_cd = pack_cd;
           this.reason = reason;
    }


    /**
     * Gets the username value for this CancelAddOnDTO.
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this CancelAddOnDTO.
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Gets the zipcode value for this CancelAddOnDTO.
     * 
     * @return zipcode
     */
    public String getZipcode() {
        return zipcode;
    }


    /**
     * Sets the zipcode value for this CancelAddOnDTO.
     * 
     * @param zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    /**
     * Gets the service_cd value for this CancelAddOnDTO.
     * 
     * @return service_cd
     */
    public String getService_cd() {
        return service_cd;
    }


    /**
     * Sets the service_cd value for this CancelAddOnDTO.
     * 
     * @param service_cd
     */
    public void setService_cd(String service_cd) {
        this.service_cd = service_cd;
    }


    /**
     * Gets the pack_cd value for this CancelAddOnDTO.
     * 
     * @return pack_cd
     */
    public String getPack_cd() {
        return pack_cd;
    }


    /**
     * Sets the pack_cd value for this CancelAddOnDTO.
     * 
     * @param pack_cd
     */
    public void setPack_cd(String pack_cd) {
        this.pack_cd = pack_cd;
    }


    /**
     * Gets the reason value for this CancelAddOnDTO.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this CancelAddOnDTO.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CancelAddOnDTO)) return false;
        CancelAddOnDTO other = (CancelAddOnDTO) obj;
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
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason())));
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
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CancelAddOnDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CancelAddOnDTO"));
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
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "reason"));
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
