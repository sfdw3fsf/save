/**
 * B2B_Room_RegistAddOnResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_Room_RegistAddOnResponse  implements java.io.Serializable {
    private B2BCommonVO b2B_Room_RegistAddOnResult;

    public B2B_Room_RegistAddOnResponse() {
    }

    public B2B_Room_RegistAddOnResponse(
           B2BCommonVO b2B_Room_RegistAddOnResult) {
           this.b2B_Room_RegistAddOnResult = b2B_Room_RegistAddOnResult;
    }


    /**
     * Gets the b2B_Room_RegistAddOnResult value for this B2B_Room_RegistAddOnResponse.
     * 
     * @return b2B_Room_RegistAddOnResult
     */
    public B2BCommonVO getB2B_Room_RegistAddOnResult() {
        return b2B_Room_RegistAddOnResult;
    }


    /**
     * Sets the b2B_Room_RegistAddOnResult value for this B2B_Room_RegistAddOnResponse.
     * 
     * @param b2B_Room_RegistAddOnResult
     */
    public void setB2B_Room_RegistAddOnResult(B2BCommonVO b2B_Room_RegistAddOnResult) {
        this.b2B_Room_RegistAddOnResult = b2B_Room_RegistAddOnResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_Room_RegistAddOnResponse)) return false;
        B2B_Room_RegistAddOnResponse other = (B2B_Room_RegistAddOnResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.b2B_Room_RegistAddOnResult==null && other.getB2B_Room_RegistAddOnResult()==null) || 
             (this.b2B_Room_RegistAddOnResult!=null &&
              this.b2B_Room_RegistAddOnResult.equals(other.getB2B_Room_RegistAddOnResult())));
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
        if (getB2B_Room_RegistAddOnResult() != null) {
            _hashCode += getB2B_Room_RegistAddOnResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_Room_RegistAddOnResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">B2B_Room_RegistAddOnResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("b2B_Room_RegistAddOnResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_Room_RegistAddOnResult"));
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
