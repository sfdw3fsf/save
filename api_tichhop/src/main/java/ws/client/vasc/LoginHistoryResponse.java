/**
 * LoginHistoryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class LoginHistoryResponse  implements java.io.Serializable {
    private String loginHistoryResult;

    public LoginHistoryResponse() {
    }

    public LoginHistoryResponse(
           String loginHistoryResult) {
           this.loginHistoryResult = loginHistoryResult;
    }


    /**
     * Gets the loginHistoryResult value for this LoginHistoryResponse.
     * 
     * @return loginHistoryResult
     */
    public String getLoginHistoryResult() {
        return loginHistoryResult;
    }


    /**
     * Sets the loginHistoryResult value for this LoginHistoryResponse.
     * 
     * @param loginHistoryResult
     */
    public void setLoginHistoryResult(String loginHistoryResult) {
        this.loginHistoryResult = loginHistoryResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof LoginHistoryResponse)) return false;
        LoginHistoryResponse other = (LoginHistoryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.loginHistoryResult==null && other.getLoginHistoryResult()==null) || 
             (this.loginHistoryResult!=null &&
              this.loginHistoryResult.equals(other.getLoginHistoryResult())));
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
        if (getLoginHistoryResult() != null) {
            _hashCode += getLoginHistoryResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LoginHistoryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">LoginHistoryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loginHistoryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "LoginHistoryResult"));
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
