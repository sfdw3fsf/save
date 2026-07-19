/**
 * ChangeDateBillResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangeDateBillResponse  implements java.io.Serializable {
    private ChangeDateBillVO changeDateBillResult;

    public ChangeDateBillResponse() {
    }

    public ChangeDateBillResponse(
           ChangeDateBillVO changeDateBillResult) {
           this.changeDateBillResult = changeDateBillResult;
    }


    /**
     * Gets the changeDateBillResult value for this ChangeDateBillResponse.
     * 
     * @return changeDateBillResult
     */
    public ChangeDateBillVO getChangeDateBillResult() {
        return changeDateBillResult;
    }


    /**
     * Sets the changeDateBillResult value for this ChangeDateBillResponse.
     * 
     * @param changeDateBillResult
     */
    public void setChangeDateBillResult(ChangeDateBillVO changeDateBillResult) {
        this.changeDateBillResult = changeDateBillResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeDateBillResponse)) return false;
        ChangeDateBillResponse other = (ChangeDateBillResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changeDateBillResult==null && other.getChangeDateBillResult()==null) || 
             (this.changeDateBillResult!=null &&
              this.changeDateBillResult.equals(other.getChangeDateBillResult())));
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
        if (getChangeDateBillResult() != null) {
            _hashCode += getChangeDateBillResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeDateBillResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangeDateBillResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeDateBillResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeDateBillResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeDateBillVO"));
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
