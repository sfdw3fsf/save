/**
 * GetDeviceKplusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetDeviceKplusResponse  implements java.io.Serializable {
    private String getDeviceKplusResult;

    public GetDeviceKplusResponse() {
    }

    public GetDeviceKplusResponse(
           String getDeviceKplusResult) {
           this.getDeviceKplusResult = getDeviceKplusResult;
    }


    /**
     * Gets the getDeviceKplusResult value for this GetDeviceKplusResponse.
     * 
     * @return getDeviceKplusResult
     */
    public String getGetDeviceKplusResult() {
        return getDeviceKplusResult;
    }


    /**
     * Sets the getDeviceKplusResult value for this GetDeviceKplusResponse.
     * 
     * @param getDeviceKplusResult
     */
    public void setGetDeviceKplusResult(String getDeviceKplusResult) {
        this.getDeviceKplusResult = getDeviceKplusResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetDeviceKplusResponse)) return false;
        GetDeviceKplusResponse other = (GetDeviceKplusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getDeviceKplusResult==null && other.getGetDeviceKplusResult()==null) || 
             (this.getDeviceKplusResult!=null &&
              this.getDeviceKplusResult.equals(other.getGetDeviceKplusResult())));
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
        if (getGetDeviceKplusResult() != null) {
            _hashCode += getGetDeviceKplusResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetDeviceKplusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetDeviceKplusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getDeviceKplusResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetDeviceKplusResult"));
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
