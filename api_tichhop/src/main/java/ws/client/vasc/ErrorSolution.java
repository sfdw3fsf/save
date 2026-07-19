/**
 * ErrorSolution.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ErrorSolution  implements java.io.Serializable {
    private String error_code;

    private String error_keyword;

    public ErrorSolution() {
    }

    public ErrorSolution(
           String error_code,
           String error_keyword) {
           this.error_code = error_code;
           this.error_keyword = error_keyword;
    }


    /**
     * Gets the error_code value for this ErrorSolution.
     * 
     * @return error_code
     */
    public String getError_code() {
        return error_code;
    }


    /**
     * Sets the error_code value for this ErrorSolution.
     * 
     * @param error_code
     */
    public void setError_code(String error_code) {
        this.error_code = error_code;
    }


    /**
     * Gets the error_keyword value for this ErrorSolution.
     * 
     * @return error_keyword
     */
    public String getError_keyword() {
        return error_keyword;
    }


    /**
     * Sets the error_keyword value for this ErrorSolution.
     * 
     * @param error_keyword
     */
    public void setError_keyword(String error_keyword) {
        this.error_keyword = error_keyword;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ErrorSolution)) return false;
        ErrorSolution other = (ErrorSolution) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.error_code==null && other.getError_code()==null) || 
             (this.error_code!=null &&
              this.error_code.equals(other.getError_code()))) &&
            ((this.error_keyword==null && other.getError_keyword()==null) || 
             (this.error_keyword!=null &&
              this.error_keyword.equals(other.getError_keyword())));
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
        if (getError_code() != null) {
            _hashCode += getError_code().hashCode();
        }
        if (getError_keyword() != null) {
            _hashCode += getError_keyword().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ErrorSolution.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ErrorSolution"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error_code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "error_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error_keyword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "error_keyword"));
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
