/**
 * GetPackageNameVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetPackageNameVO  implements java.io.Serializable {
    private String serviceCode;

    private String serviceVAS;

    private String packageName;

    private java.util.Calendar responseTime;

    private int result;

    private String errorDesc;

    public GetPackageNameVO() {
    }

    public GetPackageNameVO(
           String serviceCode,
           String serviceVAS,
           String packageName,
           java.util.Calendar responseTime,
           int result,
           String errorDesc) {
           this.serviceCode = serviceCode;
           this.serviceVAS = serviceVAS;
           this.packageName = packageName;
           this.responseTime = responseTime;
           this.result = result;
           this.errorDesc = errorDesc;
    }


    /**
     * Gets the serviceCode value for this GetPackageNameVO.
     * 
     * @return serviceCode
     */
    public String getServiceCode() {
        return serviceCode;
    }


    /**
     * Sets the serviceCode value for this GetPackageNameVO.
     * 
     * @param serviceCode
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }


    /**
     * Gets the serviceVAS value for this GetPackageNameVO.
     * 
     * @return serviceVAS
     */
    public String getServiceVAS() {
        return serviceVAS;
    }


    /**
     * Sets the serviceVAS value for this GetPackageNameVO.
     * 
     * @param serviceVAS
     */
    public void setServiceVAS(String serviceVAS) {
        this.serviceVAS = serviceVAS;
    }


    /**
     * Gets the packageName value for this GetPackageNameVO.
     * 
     * @return packageName
     */
    public String getPackageName() {
        return packageName;
    }


    /**
     * Sets the packageName value for this GetPackageNameVO.
     * 
     * @param packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


    /**
     * Gets the responseTime value for this GetPackageNameVO.
     * 
     * @return responseTime
     */
    public java.util.Calendar getResponseTime() {
        return responseTime;
    }


    /**
     * Sets the responseTime value for this GetPackageNameVO.
     * 
     * @param responseTime
     */
    public void setResponseTime(java.util.Calendar responseTime) {
        this.responseTime = responseTime;
    }


    /**
     * Gets the result value for this GetPackageNameVO.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this GetPackageNameVO.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the errorDesc value for this GetPackageNameVO.
     * 
     * @return errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }


    /**
     * Sets the errorDesc value for this GetPackageNameVO.
     * 
     * @param errorDesc
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetPackageNameVO)) return false;
        GetPackageNameVO other = (GetPackageNameVO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceCode==null && other.getServiceCode()==null) || 
             (this.serviceCode!=null &&
              this.serviceCode.equals(other.getServiceCode()))) &&
            ((this.serviceVAS==null && other.getServiceVAS()==null) || 
             (this.serviceVAS!=null &&
              this.serviceVAS.equals(other.getServiceVAS()))) &&
            ((this.packageName==null && other.getPackageName()==null) || 
             (this.packageName!=null &&
              this.packageName.equals(other.getPackageName()))) &&
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
        if (getServiceCode() != null) {
            _hashCode += getServiceCode().hashCode();
        }
        if (getServiceVAS() != null) {
            _hashCode += getServiceVAS().hashCode();
        }
        if (getPackageName() != null) {
            _hashCode += getPackageName().hashCode();
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
        new org.apache.axis.description.TypeDesc(GetPackageNameVO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetPackageNameVO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ServiceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceVAS");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ServiceVAS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PackageName"));
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
