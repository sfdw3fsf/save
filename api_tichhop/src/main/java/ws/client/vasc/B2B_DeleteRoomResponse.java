/**
 * B2B_DeleteRoomResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class B2B_DeleteRoomResponse  implements java.io.Serializable {
    private B2BCommonVO b2B_DeleteRoomResult;

    public B2B_DeleteRoomResponse() {
    }

    public B2B_DeleteRoomResponse(
           B2BCommonVO b2B_DeleteRoomResult) {
           this.b2B_DeleteRoomResult = b2B_DeleteRoomResult;
    }


    /**
     * Gets the b2B_DeleteRoomResult value for this B2B_DeleteRoomResponse.
     * 
     * @return b2B_DeleteRoomResult
     */
    public B2BCommonVO getB2B_DeleteRoomResult() {
        return b2B_DeleteRoomResult;
    }


    /**
     * Sets the b2B_DeleteRoomResult value for this B2B_DeleteRoomResponse.
     * 
     * @param b2B_DeleteRoomResult
     */
    public void setB2B_DeleteRoomResult(B2BCommonVO b2B_DeleteRoomResult) {
        this.b2B_DeleteRoomResult = b2B_DeleteRoomResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof B2B_DeleteRoomResponse)) return false;
        B2B_DeleteRoomResponse other = (B2B_DeleteRoomResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.b2B_DeleteRoomResult==null && other.getB2B_DeleteRoomResult()==null) || 
             (this.b2B_DeleteRoomResult!=null &&
              this.b2B_DeleteRoomResult.equals(other.getB2B_DeleteRoomResult())));
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
        if (getB2B_DeleteRoomResult() != null) {
            _hashCode += getB2B_DeleteRoomResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(B2B_DeleteRoomResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">B2B_DeleteRoomResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("b2B_DeleteRoomResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "B2B_DeleteRoomResult"));
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
