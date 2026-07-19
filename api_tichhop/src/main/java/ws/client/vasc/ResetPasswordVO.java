/**
 * ResetPasswordVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ResetPasswordVO  implements java.io.Serializable {
    private String IPTVACCOUNT;

    private String responseTime;

    private int result;

    private String errordesc;

    private String passwordOld;

    public ResetPasswordVO() {
    }

    public ResetPasswordVO(
           String IPTVACCOUNT,
           String responseTime,
           int result,
           String errordesc,
           String passwordOld) {
           this.IPTVACCOUNT = IPTVACCOUNT;
           this.responseTime = responseTime;
           this.result = result;
           this.errordesc = errordesc;
           this.passwordOld = passwordOld;
    }


    /**
     * Gets the IPTVACCOUNT value for this ResetPasswordVO.
     * 
     * @return IPTVACCOUNT
     */
    public String getIPTVACCOUNT() {
        return IPTVACCOUNT;
    }


    /**
     * Sets the IPTVACCOUNT value for this ResetPasswordVO.
     * 
     * @param IPTVACCOUNT
     */
    public void setIPTVACCOUNT(String IPTVACCOUNT) {
        this.IPTVACCOUNT = IPTVACCOUNT;
    }


    /**
     * Gets the responseTime value for this ResetPasswordVO.
     * 
     * @return responseTime
     */
    public String getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this ResetPasswordVO.
     * 
     * @param responseTime
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this ResetPasswordVO.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this ResetPasswordVO.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the errordesc value for this ResetPasswordVO.
     * 
     * @return errordesc
     */
    public String getErrordesc() {
        return errordesc;
    }


    /**
     * Sets the errordesc value for this ResetPasswordVO.
     * 
     * @param errordesc
     */
    public void setErrordesc(String errordesc) {
        this.errordesc = errordesc;
    }


    /**
     * Gets the passwordOld value for this ResetPasswordVO.
     * 
     * @return passwordOld
     */
    public String getPasswordOld() {
        return passwordOld;
    }


    /**
     * Sets the passwordOld value for this ResetPasswordVO.
     * 
     * @param passwordOld
     */
    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ResetPasswordVO)) return false;
        ResetPasswordVO other = (ResetPasswordVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IPTVACCOUNT==null && other.getIPTVACCOUNT()==null) || 
             (this.IPTVACCOUNT!=null &&
              this.IPTVACCOUNT.equals(other.getIPTVACCOUNT()))) &&
            ((this.responseTime==null && other.getResponseTime()==null) || 
             (this.responseTime!=null &&
              this.responseTime.equals(other.getResponseTime()))) &&
            this.result == other.getResult() &&
            ((this.errordesc==null && other.getErrordesc()==null) || 
             (this.errordesc!=null &&
              this.errordesc.equals(other.getErrordesc()))) &&
            ((this.passwordOld==null && other.getPasswordOld()==null) || 
             (this.passwordOld!=null &&
              this.passwordOld.equals(other.getPasswordOld())));
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
        if (getIPTVACCOUNT() != null) {
            _hashCode += getIPTVACCOUNT().hashCode();
        }
        if (getResponseTime() != null) {
            _hashCode += getResponseTime().hashCode();
        }
        _hashCode += getResult();
        if (getErrordesc() != null) {
            _hashCode += getErrordesc().hashCode();
        }
        if (getPasswordOld() != null) {
            _hashCode += getPasswordOld().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResetPasswordVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ResetPasswordVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVACCOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVACCOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passwordOld");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PasswordOld"));
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
