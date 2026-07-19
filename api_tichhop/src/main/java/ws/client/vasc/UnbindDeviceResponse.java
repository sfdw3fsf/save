/**
 * UnbindDeviceResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UnbindDeviceResponse  implements java.io.Serializable {
    private ChangeSTBVO unbindDeviceResult;

    public UnbindDeviceResponse() {
    }

    public UnbindDeviceResponse(
           ChangeSTBVO unbindDeviceResult) {
           this.unbindDeviceResult = unbindDeviceResult;
    }


    /**
     * Gets the unbindDeviceResult value for this UnbindDeviceResponse.
     * 
     * @return unbindDeviceResult
     */
    public ChangeSTBVO getUnbindDeviceResult() {
        return unbindDeviceResult;
    }


    /**
     * Sets the unbindDeviceResult value for this UnbindDeviceResponse.
     * 
     * @param unbindDeviceResult
     */
    public void setUnbindDeviceResult(ChangeSTBVO unbindDeviceResult) {
        this.unbindDeviceResult = unbindDeviceResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UnbindDeviceResponse)) return false;
        UnbindDeviceResponse other = (UnbindDeviceResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.unbindDeviceResult==null && other.getUnbindDeviceResult()==null) || 
             (this.unbindDeviceResult!=null &&
              this.unbindDeviceResult.equals(other.getUnbindDeviceResult())));
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
        if (getUnbindDeviceResult() != null) {
            _hashCode += getUnbindDeviceResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UnbindDeviceResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UnbindDeviceResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unbindDeviceResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UnbindDeviceResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSTBVO"));
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
