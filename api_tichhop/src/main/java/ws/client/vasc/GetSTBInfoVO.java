/**
 * GetSTBInfoVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSTBInfoVO  implements java.io.Serializable {
    private String lastlogin;

    private String serialSTB;

    private String STBMAC;

    private String STBIP;

    private java.util.Calendar responseTime;

    private int result;

    private String errorDesc;

    public GetSTBInfoVO() {
    }

    public GetSTBInfoVO(
           String lastlogin,
           String serialSTB,
           String STBMAC,
           String STBIP,
           java.util.Calendar responseTime,
           int result,
           String errorDesc) {
           this.lastlogin = lastlogin;
           this.serialSTB = serialSTB;
           this.STBMAC = STBMAC;
           this.STBIP = STBIP;
           this.responseTime = responseTime;
           this.result = result;
           this.errorDesc = errorDesc;
    }


    /**
     * Gets the lastlogin value for this GetSTBInfoVO.
     * 
     * @return lastlogin
     */
    public String getLastlogin() {
        return lastlogin;
    }


    /**
     * Sets the lastlogin value for this GetSTBInfoVO.
     * 
     * @param lastlogin
     */
    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }


    /**
     * Gets the serialSTB value for this GetSTBInfoVO.
     * 
     * @return serialSTB
     */
    public String getSerialSTB() {
        return serialSTB;
    }


    /**
     * Sets the serialSTB value for this GetSTBInfoVO.
     * 
     * @param serialSTB
     */
    public void setSerialSTB(String serialSTB) {
        this.serialSTB = serialSTB;
    }


    /**
     * Gets the STBMAC value for this GetSTBInfoVO.
     * 
     * @return STBMAC
     */
    public String getSTBMAC() {
        return STBMAC;
    }


    /**
     * Sets the STBMAC value for this GetSTBInfoVO.
     * 
     * @param STBMAC
     */
    public void setSTBMAC(String STBMAC) {
        this.STBMAC = STBMAC;
    }


    /**
     * Gets the STBIP value for this GetSTBInfoVO.
     * 
     * @return STBIP
     */
    public String getSTBIP() {
        return STBIP;
    }


    /**
     * Sets the STBIP value for this GetSTBInfoVO.
     * 
     * @param STBIP
     */
    public void setSTBIP(String STBIP) {
        this.STBIP = STBIP;
    }


    /**
     * Gets the responseTime value for this GetSTBInfoVO.
     * 
     * @return responseTime
     */
    public java.util.Calendar getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this GetSTBInfoVO.
     * 
     * @param responseTime
     */
    public void setResponseTime(java.util.Calendar responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this GetSTBInfoVO.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this GetSTBInfoVO.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the errorDesc value for this GetSTBInfoVO.
     * 
     * @return errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }


    /**
     * Sets the errorDesc value for this GetSTBInfoVO.
     * 
     * @param errorDesc
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSTBInfoVO)) return false;
        GetSTBInfoVO other = (GetSTBInfoVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lastlogin==null && other.getLastlogin()==null) || 
             (this.lastlogin!=null &&
              this.lastlogin.equals(other.getLastlogin()))) &&
            ((this.serialSTB==null && other.getSerialSTB()==null) || 
             (this.serialSTB!=null &&
              this.serialSTB.equals(other.getSerialSTB()))) &&
            ((this.STBMAC==null && other.getSTBMAC()==null) || 
             (this.STBMAC!=null &&
              this.STBMAC.equals(other.getSTBMAC()))) &&
            ((this.STBIP==null && other.getSTBIP()==null) || 
             (this.STBIP!=null &&
              this.STBIP.equals(other.getSTBIP()))) &&
            ((this.responseTime==null && other.getResponseTime()==null) || 
             (this.responseTime!=null &&
              this.responseTime.equals(other.getResponseTime()))) &&
            this.result == other.getResult() &&
            ((this.errorDesc==null && other.getErrorDesc()==null) || 
             (this.errorDesc!=null &&
              this.errorDesc.equals(other.getErrorDesc())));
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
        if (getLastlogin() != null) {
            _hashCode += getLastlogin().hashCode();
        }
        if (getSerialSTB() != null) {
            _hashCode += getSerialSTB().hashCode();
        }
        if (getSTBMAC() != null) {
            _hashCode += getSTBMAC().hashCode();
        }
        if (getSTBIP() != null) {
            _hashCode += getSTBIP().hashCode();
        }
        if (getResponseTime() != null) {
            _hashCode += getResponseTime().hashCode();
        }
        _hashCode += getResult();
        if (getErrorDesc() != null) {
            _hashCode += getErrorDesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSTBInfoVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetSTBInfoVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastlogin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Lastlogin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialSTB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SerialSTB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STBMAC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "STBMAC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("STBIP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "STBIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responseTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ResponseTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ErrorDesc"));
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
