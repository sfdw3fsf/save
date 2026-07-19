/**
 * ChangeDistrictSubscriberVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeDistrictSubscriberVO  implements java.io.Serializable {
    private String responseTime;

    private int result;

    private String errordesc;

    public ChangeDistrictSubscriberVO() {
    }

    public ChangeDistrictSubscriberVO(
           String responseTime,
           int result,
           String errordesc) {
           this.responseTime = responseTime;
           this.result = result;
           this.errordesc = errordesc;
    }


    /**
     * Gets the responseTime value for this ChangeDistrictSubscriberVO.
     * 
     * @return responseTime
     */
    public String getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this ChangeDistrictSubscriberVO.
     * 
     * @param responseTime
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this ChangeDistrictSubscriberVO.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this ChangeDistrictSubscriberVO.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the errordesc value for this ChangeDistrictSubscriberVO.
     * 
     * @return errordesc
     */
    public String getErrordesc() {
        return errordesc;
    }


    /**
     * Sets the errordesc value for this ChangeDistrictSubscriberVO.
     * 
     * @param errordesc
     */
    public void setErrordesc(String errordesc) {
        this.errordesc = errordesc;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeDistrictSubscriberVO)) return false;
        ChangeDistrictSubscriberVO other = (ChangeDistrictSubscriberVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.responseTime==null && other.getResponseTime()==null) || 
             (this.responseTime!=null &&
              this.responseTime.equals(other.getResponseTime()))) &&
            this.result == other.getResult() &&
            ((this.errordesc==null && other.getErrordesc()==null) || 
             (this.errordesc!=null &&
              this.errordesc.equals(other.getErrordesc())));
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
        if (getResponseTime() != null) {
            _hashCode += getResponseTime().hashCode();
        }
        _hashCode += getResult();
        if (getErrordesc() != null) {
            _hashCode += getErrordesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeDistrictSubscriberVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeDistrictSubscriberVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ResponseTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errordesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Errordesc"));
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
