/**
 * OTTGetMemberPendingResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class OTTGetMemberPendingResult  implements java.io.Serializable {
    private String id;

    private String mobile;

    private String area_code;

    private String status;

    private String otp_status;

    private String otp;

    private String create_date;

    private String modify_date;

    private String manufacturer_id;

    public OTTGetMemberPendingResult() {
    }

    public OTTGetMemberPendingResult(
           String id,
           String mobile,
           String area_code,
           String status,
           String otp_status,
           String otp,
           String create_date,
           String modify_date,
           String manufacturer_id) {
           this.id = id;
           this.mobile = mobile;
           this.area_code = area_code;
           this.status = status;
           this.otp_status = otp_status;
           this.otp = otp;
           this.create_date = create_date;
           this.modify_date = modify_date;
           this.manufacturer_id = manufacturer_id;
    }


    /**
     * Gets the id value for this OTTGetMemberPendingResult.
     * 
     * @return id
     */
    public String getId() {
        return id;
    }


    /**
     * Sets the id value for this OTTGetMemberPendingResult.
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Gets the mobile value for this OTTGetMemberPendingResult.
     * 
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this OTTGetMemberPendingResult.
     * 
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the area_code value for this OTTGetMemberPendingResult.
     * 
     * @return area_code
     */
    public String getArea_code() {
        return area_code;
    }


    /**
     * Sets the area_code value for this OTTGetMemberPendingResult.
     * 
     * @param area_code
     */
    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }


    /**
     * Gets the status value for this OTTGetMemberPendingResult.
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this OTTGetMemberPendingResult.
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Gets the otp_status value for this OTTGetMemberPendingResult.
     * 
     * @return otp_status
     */
    public String getOtp_status() {
        return otp_status;
    }


    /**
     * Sets the otp_status value for this OTTGetMemberPendingResult.
     * 
     * @param otp_status
     */
    public void setOtp_status(String otp_status) {
        this.otp_status = otp_status;
    }


    /**
     * Gets the otp value for this OTTGetMemberPendingResult.
     * 
     * @return otp
     */
    public String getOtp() {
        return otp;
    }


    /**
     * Sets the otp value for this OTTGetMemberPendingResult.
     * 
     * @param otp
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }


    /**
     * Gets the create_date value for this OTTGetMemberPendingResult.
     * 
     * @return create_date
     */
    public String getCreate_date() {
        return create_date;
    }


    /**
     * Sets the create_date value for this OTTGetMemberPendingResult.
     * 
     * @param create_date
     */
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }


    /**
     * Gets the modify_date value for this OTTGetMemberPendingResult.
     * 
     * @return modify_date
     */
    public String getModify_date() {
        return modify_date;
    }


    /**
     * Sets the modify_date value for this OTTGetMemberPendingResult.
     * 
     * @param modify_date
     */
    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }


    /**
     * Gets the manufacturer_id value for this OTTGetMemberPendingResult.
     * 
     * @return manufacturer_id
     */
    public String getManufacturer_id() {
        return manufacturer_id;
    }


    /**
     * Sets the manufacturer_id value for this OTTGetMemberPendingResult.
     * 
     * @param manufacturer_id
     */
    public void setManufacturer_id(String manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof OTTGetMemberPendingResult)) return false;
        OTTGetMemberPendingResult other = (OTTGetMemberPendingResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            ((this.area_code==null && other.getArea_code()==null) || 
             (this.area_code!=null &&
              this.area_code.equals(other.getArea_code()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.otp_status==null && other.getOtp_status()==null) || 
             (this.otp_status!=null &&
              this.otp_status.equals(other.getOtp_status()))) &&
            ((this.otp==null && other.getOtp()==null) || 
             (this.otp!=null &&
              this.otp.equals(other.getOtp()))) &&
            ((this.create_date==null && other.getCreate_date()==null) || 
             (this.create_date!=null &&
              this.create_date.equals(other.getCreate_date()))) &&
            ((this.modify_date==null && other.getModify_date()==null) || 
             (this.modify_date!=null &&
              this.modify_date.equals(other.getModify_date()))) &&
            ((this.manufacturer_id==null && other.getManufacturer_id()==null) || 
             (this.manufacturer_id!=null &&
              this.manufacturer_id.equals(other.getManufacturer_id())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        if (getArea_code() != null) {
            _hashCode += getArea_code().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getOtp_status() != null) {
            _hashCode += getOtp_status().hashCode();
        }
        if (getOtp() != null) {
            _hashCode += getOtp().hashCode();
        }
        if (getCreate_date() != null) {
            _hashCode += getCreate_date().hashCode();
        }
        if (getModify_date() != null) {
            _hashCode += getModify_date().hashCode();
        }
        if (getManufacturer_id() != null) {
            _hashCode += getManufacturer_id().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OTTGetMemberPendingResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "OTTGetMemberPendingResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "id"));
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
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otp_status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "otp_status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("otp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "otp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("create_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "create_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modify_date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "modify_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manufacturer_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "manufacturer_id"));
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
