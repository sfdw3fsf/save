/**
 * UnbindKplusResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UnbindKplusResponse  implements java.io.Serializable {
    private String unbindKplusResult;

    public UnbindKplusResponse() {
    }

    public UnbindKplusResponse(
           String unbindKplusResult) {
           this.unbindKplusResult = unbindKplusResult;
    }


    /**
     * Gets the unbindKplusResult value for this UnbindKplusResponse.
     * 
     * @return unbindKplusResult
     */
    public String getUnbindKplusResult() {
        return unbindKplusResult;
    }


    /**
     * Sets the unbindKplusResult value for this UnbindKplusResponse.
     * 
     * @param unbindKplusResult
     */
    public void setUnbindKplusResult(String unbindKplusResult) {
        this.unbindKplusResult = unbindKplusResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UnbindKplusResponse)) return false;
        UnbindKplusResponse other = (UnbindKplusResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.unbindKplusResult==null && other.getUnbindKplusResult()==null) || 
             (this.unbindKplusResult!=null &&
              this.unbindKplusResult.equals(other.getUnbindKplusResult())));
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
        if (getUnbindKplusResult() != null) {
            _hashCode += getUnbindKplusResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UnbindKplusResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UnbindKplusResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unbindKplusResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UnbindKplusResult"));
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
