/**
 * B2B_Global_AddAddOnResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_Global_AddAddOnResponse  implements java.io.Serializable {
    private B2BCommonVO b2B_Global_AddAddOnResult;

    public B2B_Global_AddAddOnResponse() {
    }

    public B2B_Global_AddAddOnResponse(
           B2BCommonVO b2B_Global_AddAddOnResult) {
           this.b2B_Global_AddAddOnResult = b2B_Global_AddAddOnResult;
    }


    /**
     * Gets the b2B_Global_AddAddOnResult value for this B2B_Global_AddAddOnResponse.
     * 
     * @return b2B_Global_AddAddOnResult
     */
    public B2BCommonVO getB2B_Global_AddAddOnResult() {
        return b2B_Global_AddAddOnResult;
    }


    /**
     * Sets the b2B_Global_AddAddOnResult value for this B2B_Global_AddAddOnResponse.
     * 
     * @param b2B_Global_AddAddOnResult
     */
    public void setB2B_Global_AddAddOnResult(B2BCommonVO b2B_Global_AddAddOnResult) {
        this.b2B_Global_AddAddOnResult = b2B_Global_AddAddOnResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_Global_AddAddOnResponse)) return false;
        B2B_Global_AddAddOnResponse other = (B2B_Global_AddAddOnResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.b2B_Global_AddAddOnResult==null && other.getB2B_Global_AddAddOnResult()==null) || 
             (this.b2B_Global_AddAddOnResult!=null &&
              this.b2B_Global_AddAddOnResult.equals(other.getB2B_Global_AddAddOnResult())));
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
        if (getB2B_Global_AddAddOnResult() != null) {
            _hashCode += getB2B_Global_AddAddOnResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_Global_AddAddOnResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">B2B_Global_AddAddOnResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("b2B_Global_AddAddOnResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_Global_AddAddOnResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "b2bCommonVO"));
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
