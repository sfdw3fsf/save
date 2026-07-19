/**
 * ValidPasswordResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ValidPasswordResponse  implements java.io.Serializable {
    private ValidPasswordVO validPasswordResult;

    public ValidPasswordResponse() {
    }

    public ValidPasswordResponse(
           ValidPasswordVO validPasswordResult) {
           this.validPasswordResult = validPasswordResult;
    }


    /**
     * Gets the validPasswordResult value for this ValidPasswordResponse.
     * 
     * @return validPasswordResult
     */
    public ValidPasswordVO getValidPasswordResult() {
        return validPasswordResult;
    }


    /**
     * Sets the validPasswordResult value for this ValidPasswordResponse.
     * 
     * @param validPasswordResult
     */
    public void setValidPasswordResult(ValidPasswordVO validPasswordResult) {
        this.validPasswordResult = validPasswordResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ValidPasswordResponse)) return false;
        ValidPasswordResponse other = (ValidPasswordResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.validPasswordResult==null && other.getValidPasswordResult()==null) || 
             (this.validPasswordResult!=null &&
              this.validPasswordResult.equals(other.getValidPasswordResult())));
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
        if (getValidPasswordResult() != null) {
            _hashCode += getValidPasswordResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValidPasswordResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ValidPasswordResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validPasswordResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ValidPasswordResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ValidPasswordVO"));
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
