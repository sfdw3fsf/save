/**
 * Smart_OTTGetMemberPendingRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class Smart_OTTGetMemberPendingRequest  implements java.io.Serializable {
    private String area_code;

    private String mobile;

    private int number;

    private int page;

    private String fromDate;

    private String toDate;

    public Smart_OTTGetMemberPendingRequest() {
    }

    public Smart_OTTGetMemberPendingRequest(
           String area_code,
           String mobile,
           int number,
           int page,
           String fromDate,
           String toDate) {
           this.area_code = area_code;
           this.mobile = mobile;
           this.number = number;
           this.page = page;
           this.fromDate = fromDate;
           this.toDate = toDate;
    }


    /**
     * Gets the area_code value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @return area_code
     */
    public String getArea_code() {
        return area_code;
    }


    /**
     * Sets the area_code value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @param area_code
     */
    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }


    /**
     * Gets the mobile value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the number value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @return number
     */
    public int getNumber() {
        return number;
    }


    /**
     * Sets the number value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }


    /**
     * Gets the page value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @return page
     */
    public int getPage() {
        return page;
    }


    /**
     * Sets the page value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }


    /**
     * Gets the fromDate value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @return fromDate
     */
    public String getFromDate() {
        return fromDate;
    }


    /**
     * Sets the fromDate value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @param fromDate
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }


    /**
     * Gets the toDate value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @return toDate
     */
    public String getToDate() {
        return toDate;
    }


    /**
     * Sets the toDate value for this Smart_OTTGetMemberPendingRequest.
     * 
     * @param toDate
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Smart_OTTGetMemberPendingRequest)) return false;
        Smart_OTTGetMemberPendingRequest other = (Smart_OTTGetMemberPendingRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.area_code==null && other.getArea_code()==null) || 
             (this.area_code!=null &&
              this.area_code.equals(other.getArea_code()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            this.number == other.getNumber() &&
            this.page == other.getPage() &&
            ((this.fromDate==null && other.getFromDate()==null) || 
             (this.fromDate!=null &&
              this.fromDate.equals(other.getFromDate()))) &&
            ((this.toDate==null && other.getToDate()==null) || 
             (this.toDate!=null &&
              this.toDate.equals(other.getToDate())));
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
        if (getArea_code() != null) {
            _hashCode += getArea_code().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        _hashCode += getNumber();
        _hashCode += getPage();
        if (getFromDate() != null) {
            _hashCode += getFromDate().hashCode();
        }
        if (getToDate() != null) {
            _hashCode += getToDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Smart_OTTGetMemberPendingRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "Smart_OTTGetMemberPendingRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("area_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "area_code"));
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
        elemField.setFieldName("number");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "number"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("page");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "page"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "fromDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "toDate"));
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
