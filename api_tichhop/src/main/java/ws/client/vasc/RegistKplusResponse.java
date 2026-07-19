/**
 * RegistKplusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class RegistKplusResponse  implements java.io.Serializable {
    private CommonVO registKplusResult;

    public RegistKplusResponse() {
    }

    public RegistKplusResponse(
           CommonVO registKplusResult) {
           this.registKplusResult = registKplusResult;
    }


    /**
     * Gets the registKplusResult value for this RegistKplusResponse.
     * 
     * @return registKplusResult
     */
    public CommonVO getRegistKplusResult() {
        return registKplusResult;
    }


    /**
     * Sets the registKplusResult value for this RegistKplusResponse.
     * 
     * @param registKplusResult
     */
    public void setRegistKplusResult(CommonVO registKplusResult) {
        this.registKplusResult = registKplusResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof RegistKplusResponse)) return false;
        RegistKplusResponse other = (RegistKplusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.registKplusResult==null && other.getRegistKplusResult()==null) || 
             (this.registKplusResult!=null &&
              this.registKplusResult.equals(other.getRegistKplusResult())));
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
        if (getRegistKplusResult() != null) {
            _hashCode += getRegistKplusResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RegistKplusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">RegistKplusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registKplusResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "RegistKplusResult"));
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
