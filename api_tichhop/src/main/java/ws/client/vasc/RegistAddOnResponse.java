/**
 * RegistAddOnResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegistAddOnResponse  implements java.io.Serializable {
    private CommonVO registAddOnResult;

    public RegistAddOnResponse() {
    }

    public RegistAddOnResponse(
           CommonVO registAddOnResult) {
           this.registAddOnResult = registAddOnResult;
    }


    /**
     * Gets the registAddOnResult value for this RegistAddOnResponse.
     * 
     * @return registAddOnResult
     */
    public CommonVO getRegistAddOnResult() {
        return registAddOnResult;
    }


    /**
     * Sets the registAddOnResult value for this RegistAddOnResponse.
     * 
     * @param registAddOnResult
     */
    public void setRegistAddOnResult(CommonVO registAddOnResult) {
        this.registAddOnResult = registAddOnResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegistAddOnResponse)) return false;
        RegistAddOnResponse other = (RegistAddOnResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registAddOnResult==null && other.getRegistAddOnResult()==null) || 
             (this.registAddOnResult!=null &&
              this.registAddOnResult.equals(other.getRegistAddOnResult())));
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
        if (getRegistAddOnResult() != null) {
            _hashCode += getRegistAddOnResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistAddOnResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegistAddOnResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registAddOnResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegistAddOnResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CommonVO"));
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
